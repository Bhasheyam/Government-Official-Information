package com.example.bhash.govermentinfo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class Info extends AppCompatActivity {
    private TextView t;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
        t=(TextView)findViewById(R.id.t);
        t.setText("GovermentInfo"+"\n"+"© August 2017 Bhasheyam "+"\n"+"Version 1.0");

    }
}
