package com.gymnast.view.personal.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.gymnast.data.personal.CircleMainData;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Cymbi on 2016/9/22.
 */
public class CircleAdminAdapter extends RecyclerView.Adapter {
    Context context;
    List<CircleMainData> mValue;

    public CircleAdminAdapter(Context context, List<CircleMainData> mValue) {
        this.context = context;
        if(mValue.size()==0){
            this.mValue=new ArrayList<>();
        }else {
            this.mValue = mValue;
        }
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
       LayoutInflater inflater= LayoutInflater.from(context);

        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return mValue.size();
    }
}
