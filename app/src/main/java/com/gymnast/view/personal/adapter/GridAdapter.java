package com.gymnast.view.personal.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import com.gymnast.R;
import com.gymnast.utils.PicUtil;
import com.gymnast.utils.PicassoUtil;
import com.gymnast.view.personal.activity.ImageActivity;

import java.util.ArrayList;
/**
 * Created by Cymbi on 2016/8/31.
 */
public class GridAdapter extends BaseAdapter {
    Context context;
    ArrayList<String> datas;
    public GridAdapter(Context context, ArrayList<String> datas) {
        this.context = context;
        if (datas.size()!=0){
            this.datas = datas;
        }else {
            this.datas=new ArrayList<>();
        }
    }
    @Override
    public int getCount() {
        return datas.size();
    }
    @Override
    public Object getItem(int position) {
        return datas.get(position);
    }
    @Override
    public long getItemId(int position) {
        return position;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MyViewHolder holder=null;
        if (convertView==null){
            convertView=View.inflate(context, R.layout.dynamic_gridview_item,null);
            holder=new MyViewHolder();
            convertView.setTag(holder);
        }else {
            holder= (MyViewHolder) convertView.getTag();
        }
        holder.ivMain= (ImageView) convertView.findViewById(R.id.ivMain);
        final String url=datas.get(position);
        PicassoUtil.handlePic(context, PicUtil.getImageUrlDetail(context, url, 226, 226),holder.ivMain,226,226);
        holder.ivMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(context,ImageActivity.class);
                i.putExtra("IMAGE",url);
                context.startActivity(i);
            }
        });
        return convertView;
    }
    class MyViewHolder{
        ImageView ivMain;
    }
}
