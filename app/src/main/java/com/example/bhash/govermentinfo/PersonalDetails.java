package com.example.bhash.govermentinfo;

import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.io.Serializable;

public class PersonalDetails extends AppCompatActivity {
    private  OfficialData Data;
    private String loc;
    private TextView t1;
    private TextView t2;
    private TextView t3;
    private ImageView i1;
    private ConstraintLayout l1;
    private static final String TAG = "PersonalDetails";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_details);
        Intent i=getIntent();
        Data= (OfficialData)i.getSerializableExtra("value");
        loc=i.getStringExtra("head");
        t1=(TextView)findViewById(R.id.t1);
        t2=(TextView)findViewById(R.id.t2);
        t3=(TextView)findViewById(R.id.t3);
        i1=(ImageView)findViewById(R.id.i1v);
        t1.setText(loc);
        t2.setText(Data.getPosition());
        t3.setText(Data.getName());
        l1=(ConstraintLayout)findViewById(R.id.activity_personal_details);
        if(Data.getParty().equals("Democratic")){
            Log.d(TAG, "onCreate: checked the color");
            l1.setBackgroundColor(Color.BLUE);

        }
        else if(Data.getParty().equals("Republican")){
            Log.d(TAG, "onCreate: checked the color red");
            l1.setBackgroundColor(Color.RED);
        }
        if(isConnected()){
        if (Data.getPhoto() != null) {
            Picasso picasso = new Picasso.Builder(this).listener(new Picasso.Listener() {
                @Override
                public void onImageLoadFailed(Picasso picasso, Uri uri, Exception exception) {

                    final String changedUrl = Data.getPhoto().replace("http:", "https:");
                    picasso.load(changedUrl)
                            .error(R.drawable.brokenimage)
                            .placeholder(R.drawable.placeholder)
                            .into(i1);
                }
            }).build();
            picasso.load(Data.getPhoto())
                    .error(R.drawable.brokenimage)
                    .placeholder(R.drawable.placeholder)
                    .into(i1);
        } else {
            Picasso.with(this).load(Data.getPhoto())
                    .error(R.drawable.brokenimage)
                    .placeholder(R.drawable.missingimage)
                    .into(i1);
        }}
        else{
            Log.d(TAG, "onCreate: CameImage");
            i1.setBackgroundResource(R.drawable.missingimage);
        }

    }
    public  boolean isConnected()
    {
        try{
            ConnectivityManager cm = (ConnectivityManager) getSystemService(this.CONNECTIVITY_SERVICE);
            if (cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isAvailable() && cm.getActiveNetworkInfo().isConnected())
                return true;
            else
                return false;
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return false;
    }
    @Override
    protected void onStop() {
        Intent i2=new Intent(getApplicationContext(),OfficalInfo.class);
        Log.d(TAG, "onStop: ");
        i2.putExtra("value",(Serializable)Data);
        i2.putExtra("head",loc);
        startActivity(i2);
        super.onStop();
    }
}
