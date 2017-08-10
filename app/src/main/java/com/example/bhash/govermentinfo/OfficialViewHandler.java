package com.example.bhash.govermentinfo;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * Created by bhash on 04-08-2017.
 */

public class OfficialViewHandler extends RecyclerView.Adapter<OfficalViewDisplayer> {


    ArrayList<OfficialData> temp;
    HomeLocation access;
    OfficialData tempd;
    private static final String TAG = "OfficialViewHandler";
    public OfficialViewHandler(HomeLocation h,ArrayList<OfficialData> d){
        access=h;
        temp=d;
    }
    @Override
    public OfficalViewDisplayer onCreateViewHolder(ViewGroup parent, int viewType) {
        View cards= LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_name_card,parent,false);
        cards.setOnClickListener(access);
        cards.setOnLongClickListener(access);
        Log.d(TAG, "onCreateViewHolder: Wokring to begin");
        return new OfficalViewDisplayer(cards);
    }

    @Override
    public void onBindViewHolder(OfficalViewDisplayer holder, int position) {
        tempd=temp.get(position);
        holder.t1.setText(tempd.getPosition());
        Log.d(TAG, "onBindViewHolder: Workingfine");
        holder.t2.setText(tempd.getName());
    }

    @Override
    public int getItemCount() {
        return temp.size();
    }
}
