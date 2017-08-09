package com.example.bhash.govermentinfo;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;

public class HomeLocation extends AppCompatActivity implements View.OnLongClickListener, View.OnClickListener {
    private TextView location;
    private RecyclerView r1;
    ArrayList Maindata;
    OfficialViewHandler Adapter;
    private static final String TAG = "HomeLocation";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_location);
        location=(TextView)findViewById(R.id.t1);
        r1=(RecyclerView)findViewById(R.id.r);
        Maindata=new ArrayList<>();
        Adapter=new OfficialViewHandler(this,Maindata);
        r1.setLayoutManager(new LinearLayoutManager(this));
        r1.setAdapter(Adapter);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater i=getMenuInflater();
        i.inflate(R.menu.location,menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    protected void onResume() {
        if(isConnected()) {
//location Service
            startit("60504");
            }

        else{
            noconnection();
        }
        super.onResume();
    }
    public void startit(String se){
        DataGeneration d=new DataGeneration(this);
        d.execute(se);
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
    public void noconnection(){
        Log.d(TAG, "noconnection: came");
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {

            }
        });
        Log.d(TAG, "noconnection: came1");
        builder.setMessage("App Requires Internet ");
        builder.setTitle("No Network");
        AlertDialog dialog = builder.create();
        dialog.show();

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.i1:
                Intent i=new Intent(getApplicationContext(),Info.class);
                startActivity(i);
                break;
            case R.id.i2:
                AlertDialog.Builder d=new AlertDialog.Builder(this);
              final  EditText et=(EditText)new EditText(this);
                et.setInputType(InputType.TYPE_CLASS_TEXT);
                et.setGravity(Gravity.CENTER_HORIZONTAL);
                d.setView(et);

                d.setPositiveButton("Select",new DialogInterface.OnClickListener(){

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                     startit(et.getText().toString());
                    }
                });
                d.setNegativeButton("Cancel",new DialogInterface.OnClickListener(){

                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                d.setTitle("Enter Zipcode");
                d.setMessage("Enter the ZipCode of the location");
                Dialog zipit=d.create();
                zipit.show();
                break;
            default:
                Log.d(TAG, "onOptionsItemSelected: no option selected");


        }
        return super.onOptionsItemSelected(item);

    }
    public void wrongentry(){
        AlertDialog.Builder d=new AlertDialog.Builder(this);
        d.setTitle("Incorrect");
        d.setMessage("Please enter an valid location");
        Dialog zipit=d.create();
        zipit.show();

    }

    @Override
    public void onClick(View v) {
        final int i1=r1.getChildLayoutPosition(v);
        Intent i=new Intent(getApplicationContext(),OfficalInfo.class);
        i.putExtra("head",location.getText().toString());
        i.putExtra("value", (Serializable) Maindata.get(i1));
        startActivity(i);
    }

    @Override
    public boolean onLongClick(View v) {

        Toast.makeText(this,"No Functionality for a long press",Toast.LENGTH_SHORT).show();
        return false;
    }

    public void updateTaks(String[] h, ArrayList<OfficialData> officialDatas) {
       for(int i=0;i<officialDatas.size();i++){
           Maindata.add(officialDatas.get(i));
       }
        location.setText(h[0]+","+h[1]+" "+h[2]);
        Adapter.notifyDataSetChanged();

    }
}
