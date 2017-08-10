package com.example.bhash.govermentinfo;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.util.Linkify;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.util.regex.Pattern;

import static android.R.attr.color;
import static android.R.attr.data;

public class OfficalInfo extends AppCompatActivity {
    private  OfficialData Data;
    private String loc;
    private TextView t1;
    private TextView t2;
    private TextView t3;
    private TextView t4;
    private TextView t5;
    private  TextView t6;
    private TextView t7;
    private  TextView t8;
    private ImageButton i1;
    private ImageButton i2;
    private ImageButton i3;
    private ImageButton i4;
    private ImageButton i5;
    private ConstraintLayout l1;
    private static final String TAG = "OfficalInfo";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offical_info);
        Intent i=getIntent();
         Data= (OfficialData)i.getSerializableExtra("value");
        Log.d(TAG, "onCreate: "+Data.getPosition());
        loc=i.getStringExtra("head");
        t1=(TextView)findViewById(R.id.t1);
        t2=(TextView)findViewById(R.id.t2);
        t3=(TextView)findViewById(R.id.t3);
        t4=(TextView)findViewById(R.id.t4);
        t5=(TextView)findViewById(R.id.t9a2);
        t6=(TextView)findViewById(R.id.t10p2);
        t7=(TextView)findViewById(R.id.t11e2);
        t8=(TextView)findViewById(R.id.t12w1);
        i1=(ImageButton)findViewById(R.id.i1b1);
        i2=(ImageButton)findViewById(R.id.i2b2);
        i3=(ImageButton)findViewById(R.id.i3b3);
        i4=(ImageButton)findViewById(R.id.i4b4);
        i5=(ImageButton)findViewById(R.id.i5b5);

        l1=(ConstraintLayout) findViewById(R.id.activity_offical_info);
        if(Data.getParty()==null ) {
            l1.setBackgroundColor(Color.BLACK);
        }

        else if(Data.getParty().equals("Democratic")){
            l1.setBackgroundColor(Color.BLUE);

        }
        else if(Data.getParty().equals("Republican")){
            l1.setBackgroundColor(Color.RED);
        }
        else{
            l1.setBackgroundColor(Color.BLACK);
        }
        t1.setText(loc);
        t2.setText(Data.getPosition());
        t3.setText(Data.getName());
        if(Data.getParty()!=null){
            t4.setText("("+Data.getParty()+")");
        }
        else{
            t4.setText("("+"Unknown"+")");
        }

        if(Data.getAddress()!=null){
            Pattern pattern = Pattern.compile(".*", Pattern.DOTALL);
            t5.setText(Data.getAddress());
            Linkify.addLinks(t5, pattern, "geo:0,0?q=");
        }
        else
            t5.setText("No Data Provided");
        if(Data.getPhone()!=null){
            t6.setText(Data.getPhone());
        Linkify.addLinks(t6,Linkify.PHONE_NUMBERS);}
        else
            t6.setText("No Data Provided");
        if(Data.getEmail()!=null){
            t7.setText(Data.getEmail());
            Linkify.addLinks(t7,Linkify.EMAIL_ADDRESSES);}
        else
        {
            Log.d(TAG, "onCreate: "+Data.getEmail());
            t7.setText("No Data Provided");}
        if(Data.getSite()!=null){
            t8.setText(Data.getSite());
        Linkify.addLinks(t8,Linkify.WEB_URLS);}
        else
            t8.setText("No Data Provided");
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
            Log.d(TAG, "onCreate: Came image");
        i1.setBackgroundResource(R.drawable.missingimage);
    }
   if(Data.getFb()!=null){
       Log.d(TAG, "onCreate: camef");
       i2.setBackgroundResource(R.drawable.facebook);
       i2.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               String FACEBOOK_URL = "https://www.facebook.com/" +Data.getFb();
               String urlToUse;
               PackageManager packageManager = getPackageManager();
               try {
                   int versionCode = packageManager.getPackageInfo("com.facebook.katana", 0).versionCode;
                   if (versionCode >= 3002850) { //newer versions of fb app
                       urlToUse = "fb://facewebmodal/f?href=" + FACEBOOK_URL;
                   } else { //older versions of fb app
                       urlToUse = "fb://page/" + Data.getFb();
                   }
               } catch (PackageManager.NameNotFoundException e) {
                   urlToUse = FACEBOOK_URL; //normal web url
               }
               Intent facebookIntent = new Intent(Intent.ACTION_VIEW);
               facebookIntent.setData(Uri.parse(urlToUse));
               startActivity(facebookIntent);

           }
       });
   }
        else{
       i2.setBackground(null);
   }
   if(Data.getYouTube()!=null) {
       Log.d(TAG, "onCreate: camey");
    i5.setBackgroundResource(R.drawable.youtube);
    i5.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String name = Data.getYouTube();
            Intent intent = null;
            try {
                intent = new Intent(Intent.ACTION_VIEW);
                intent.setPackage("com.google.android.youtube");
                intent.setData(Uri.parse("https://www.youtube.com/" + name));
                startActivity(intent);
            } catch (ActivityNotFoundException e) {
                startActivity(new Intent(Intent.ACTION_VIEW,
                        Uri.parse("https://www.youtube.com/" + name)));

            }
        }
    });
    }else{
       i5.setBackground(null);
   }
        if(Data.getTwitter()!=null){
            Log.d(TAG, "onCreate: camet");
            i3.setBackgroundResource(R.drawable.twitter);
            i3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = null;
                    String name = Data.getTwitter();
                    try {
                        // get the Twitter app if possible
                        getPackageManager().getPackageInfo("com.twitter.android", 0);
                        intent = new Intent(Intent.ACTION_VIEW, Uri.parse("twitter://user?screen_name=" + name));
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    } catch (Exception e) {
                        // no Twitter app, revert to browser
                        intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://twitter.com/" + name));
                    }
                    startActivity(intent);

                }
            });
        }
        else{
            i3.setBackground(null);
        }
        if(Data.getGplus()!=null){
            Log.d(TAG, "onCreate: cameg");
            i4.setBackgroundResource(R.drawable.googleplus);
            i4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String name = Data.getGplus();
                    Intent intent = null;
                    try {
                        intent = new Intent(Intent.ACTION_VIEW);
                        intent.setClassName("com.google.android.apps.plus",
                                "com.google.android.apps.plus.phone.UrlGatewayActivity");
                        intent.putExtra("customAppUri", name);
                        startActivity(intent);
                    } catch (ActivityNotFoundException e) {
                        startActivity(new Intent(Intent.ACTION_VIEW,
                                Uri.parse("https://plus.google.com/" + name)));
                    }
                }
            });
        }
        else{
            i4.setBackground(null);
        }
        i1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i2=new Intent(getApplicationContext(),PersonalDetails.class);
                i2.putExtra("value",(Serializable)Data);
                i2.putExtra("head",loc);
                startActivity(i2);
            }
        });
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

}
