package com.example.bhash.govermentinfo;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.util.Linkify;
import android.widget.ImageButton;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.io.Serializable;

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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offical_info);
        Intent i=getIntent();
         Data= (OfficialData)i.getSerializableExtra("value");
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
        t1.setText(loc);
        t2.setText(Data.getPosition());
        t3.setText(Data.getName());
        t4.setText("("+Data.getParty()+")");
        if(Data.getAddress()!=null){
            t5.setText(Data.getAddress());
        Linkify.addLinks(t5,Linkify.MAP_ADDRESSES);}
        else
            t5.setText("-");
        if(Data.getPhone()!=null){
            t6.setText(Data.getPhone());
        Linkify.addLinks(t6,Linkify.PHONE_NUMBERS);}
        else
            t6.setText("-");
        if(Data.getEmail()!=null){
            t7.setText(Data.getEmail());
        Linkify.addLinks(t7,Linkify.EMAIL_ADDRESSES);}
        else
            t7.setText("-");
        if(Data.getSite()!=null){
            t8.setText(Data.getSite());
        Linkify.addLinks(t8,Linkify.WEB_URLS);}
        else
            t8.setText("-");
        if (Data.getPhoto() != null) {
            Picasso picasso = new Picasso.Builder(this).listener(new Picasso.Listener() {
                @Override
                public void onImageLoadFailed(Picasso picasso, Uri uri, Exception exception) {
// Here we try https if the http image attempt failed
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
        }


    }
    public void OnImage(){
        Intent i=new Intent(getApplicationContext(),PersonalDetails.class);
        i.putExtra("value",(Serializable)Data);
        i.putExtra("head",loc);
        startActivity(i);
    }
}
