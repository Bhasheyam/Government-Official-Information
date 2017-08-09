package com.example.bhash.govermentinfo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.io.Serializable;

import static android.R.attr.data;

public class OfficalInfo extends AppCompatActivity {
    private  OfficialData Data;
    String loc;
    private TextView t1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offical_info);
        Intent i=getIntent();
         Data= (OfficialData)i.getSerializableExtra("value");
        loc=i.getStringExtra("head");
        t1=(TextView)findViewById(R.id.t1);
        t1.setText(loc);





    }
    public void OnImage(){
        Intent i=new Intent(getApplicationContext(),PersonalDetails.class);
        i.putExtra("value",(Serializable)Data);
        startActivity(i);
    }
}
