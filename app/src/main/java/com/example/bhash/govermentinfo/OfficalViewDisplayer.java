package com.example.bhash.govermentinfo;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

/**
 * Created by bhash on 04-08-2017.
 */

public class OfficalViewDisplayer extends RecyclerView.ViewHolder {
    public TextView t1;
    public TextView t2;
    public OfficalViewDisplayer(View itemView) {
        super(itemView);
        t1=(TextView)itemView.findViewById(R.id.position);
        t2=(TextView)itemView.findViewById(R.id.name);
    }
}
