package com.example.bhash.govermentinfo;

import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by bhash on 05-08-2017.
 */

public class DataGeneration extends AsyncTask<String,Void,ArrayList<OfficialData>> {
    HomeLocation access;
    private static final String TAG = "DataGeneration";
    private ArrayList<OfficialData> data=new ArrayList<OfficialData>();
    private  String u="https://www.googleapis.com/civicinfo/v2/representatives";
    String res="working";
    String[] h=new String[6];
    public DataGeneration(HomeLocation a){
        access=a;
    }
    @Override
    protected ArrayList<OfficialData> doInBackground(String... params) {
        Uri.Builder base= Uri.parse(u).buildUpon();
        base.appendQueryParameter("key","AIzaSyD8lTgegqRzsr0Y0t1WX4RUD8bKyclb_I4");
        base.appendQueryParameter("address",params[0]);
        String ur=base.toString();
        StringBuilder s=new StringBuilder();
        try{
            URL finnalurl=new URL(ur);
            HttpURLConnection connect=(HttpURLConnection)finnalurl.openConnection();
            connect.setRequestMethod("GET");
            InputStream r=connect.getInputStream();
            BufferedReader br=new BufferedReader(new InputStreamReader(r));
            String emptyline;
            while((emptyline=br.readLine())!=null){
                s.append(emptyline).append("\n");

            }

        }
        catch (Exception e){
            e.getStackTrace();
        }
        ExtractJson(s.toString());
        return data;
    }

    private void ExtractJson(String s) {
        Log.d(TAG, "ExtractJson: "+s);
if(s.contains("error")){
    res="error";

}
        else{
    try {

        JSONObject main = new JSONObject(s);
        JSONObject head=main.getJSONObject("normalizedInput");
        h[0]=head.getString("city");
        h[1]=head.getString("state");
        h[2]=head.getString("zip");
        JSONArray pos=main.getJSONArray("offices");

        for(int i=0;i<pos.length();i++){


            JSONObject tt=(JSONObject)pos.get(i);
            JSONArray tt1=tt.getJSONArray("officialIndices");
            int[] ii=new int[tt1.length()];
            for(int y=0;y<ii.length;y++) {
                OfficialData te=new OfficialData();
                te.setPosition(tt.getString("name"));
                te.setIndex((Integer) tt1.get(y));
                Log.d(TAG, "ExtractJson: " + te.getPosition() + te.getIndex());
                data.add(te);
            }

        }

        Log.d(TAG, "ExtractJson: "+data.size());
        for(int j=0;j<data.size();j++) {
            JSONArray OfInfo = main.getJSONArray("officials");
            Log.d(TAG, "ExtractJson: " + j);
            int index = data.get(j).getIndex();
            Log.d(TAG, "ExtractJson: " + index);
            JSONObject da = (JSONObject) OfInfo.get(index);
            Log.d(TAG, "ExtractJson: " + da.getString("name"));
            if(da.has("name"))
            data.get(j).setName(da.getString("name"));
            if(da.has("party"))
            data.get(j).setParty(da.getString("party"));
            if (da.has("address")) {
                JSONArray ad = da.getJSONArray("address");
                JSONObject ad1 = (JSONObject) ad.get(0);

                if (ad1.has("line2")) {
                    data.get(j).setAddress(ad1.getString("line1") + ", " + ad1.getString("line2") + ", " + ad1.getString("city") + ", " + ad1.getString("state") + ", " + ad1.getString("zip") );
                } else {
                    data.get(j).setAddress(ad1.getString("line1") + ", " + ad1.getString("city") + ", " + ad1.getString("state") + ", " + ad1.getString("zip"));


                }
            }
            Log.d(TAG, "ExtractJson: " + data.get(j).getAddress());

           if(da.has("phones")){ JSONArray p = da.getJSONArray("phones");
            if (p.length() != 0)
                Log.d(TAG, "ExtractJson: came before error");
            data.get(j).setPhone((String) p.get(0));
            Log.d(TAG, "ExtractJson: " + data.get(j).getPhone());
        }
            if(da.has("urls")) {
                JSONArray u = da.getJSONArray("urls");
                if (u.length() != 0)
                    data.get(j).setSite((String) u.get(0));
                Log.d(TAG, "ExtractJson: " + data.get(j).getSite());
            }
            if(da.has("photoUrl"))
            data.get(j).setPhoto(da.getString("photoUrl"));


            Log.d(TAG, "ExtractJson: "+data.get(j).getPhoto());
            if(da.has("channels")){
            JSONArray c=da.getJSONArray("channels");
            if(c.length()!=0) {
                for (int k = 0; k < c.length(); k++) {
                    JSONObject tem = (JSONObject) c.get(k);
                    if (tem.getString("type").equals("GooglePlus")) {
                        data.get(j).setGplus(tem.getString("id"));

                    }
                    if (tem.getString("type").equals("Facebook")) {
                        data.get(j).setFb(tem.getString("id"));
                        Log.d(TAG, "ExtractJson: Came d");
                    }
                    if (tem.getString("type").equals("Twitter")) {
                        data.get(j).setTwitter(tem.getString("id"));
                        Log.d(TAG, "ExtractJson: Came d");
                    }
                    if (tem.getString("type").equals("YouTube")) {
                        data.get(j).setYouTube(tem.getString("id"));
                        Log.d(TAG, "ExtractJson: Came d");
                    }
                }
            }}

        }



    }
    catch (Exception e){
        res="error";
        Log.d(TAG, "ExtractJson: error in json"+s);
    }

        }



    }
// AIzaSyD8lTgegqRzsr0Y0t1WX4RUD8bKyclb_I4

    @Override
    protected void onPostExecute(ArrayList<OfficialData> s) {
        if(res.equals("error")){
            access.wrongentry();

        }
      else {
            Log.d(TAG, "onPostExecute: " + s.get(1).getAddress());
            access.updateTaks(h, s);
        }
    }
}
