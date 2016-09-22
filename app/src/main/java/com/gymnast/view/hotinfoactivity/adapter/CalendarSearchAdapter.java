package com.gymnast.view.hotinfoactivity.adapter;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.gymnast.R;
import com.gymnast.data.hotinfo.NewActivityItemDevas;
import com.gymnast.utils.PicUtil;
import com.gymnast.utils.TimeUtil;
import com.gymnast.view.hotinfoactivity.activity.ActivityDetailsActivity;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by zzqybyb19860112 on 2016/9/10.
 */
public class CalendarSearchAdapter extends RecyclerView.Adapter{
   List<NewActivityItemDevas> mValues ;
    private Activity activity;
    private static final int VIEW_TYPE = -1;
    public CalendarSearchAdapter(Activity activity, List<NewActivityItemDevas> mValues) {
        this.activity = activity;
        if (mValues.size() == 0) {
            this.mValues = new ArrayList<>();
        } else {
            this.mValues = mValues;
        }
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        View itemview = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recyleview_recent_activity, null);
        itemview.setLayoutParams(lp);
        if (viewType == VIEW_TYPE) {
            View view=LayoutInflater.from(activity).inflate(R.layout.empty_view, parent, false);
            view.setLayoutParams(lp);
            return new empty(view);
        }
        return  new PersonViewHolder(itemview);
    }
    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof PersonViewHolder){
            PersonViewHolder viewHolder = (PersonViewHolder)holder;
            final NewActivityItemDevas newActivityItemDevas=  mValues.get(position);
            Picasso.with(activity).load(PicUtil.getImageUrl(activity, newActivityItemDevas.getImgUrls())).into(viewHolder.ivImage);
            viewHolder.tvaddress.setText(newActivityItemDevas.getAddress());
            viewHolder.tvcollection.setText(TimeUtil.setLoveNum(newActivityItemDevas.getCollection())+"人收藏");
            viewHolder.tvnickname.setText(newActivityItemDevas.getNickname());
            viewHolder.tvstartTime.setText(TimeUtil.checkTime(newActivityItemDevas.getStartTime()));
            viewHolder.tvTitle.setText(newActivityItemDevas.getTitle());
            if(newActivityItemDevas.getPrice()==0){
                viewHolder.tvprice.setBackground(activity.getResources().getDrawable(R.drawable.border_radius_cornner_green));
                viewHolder.tvprice.setTextColor(activity.getResources().getColor(R.color.green));
            }else{
                viewHolder.tvprice.setBackground(activity.getResources().getDrawable(R.drawable.border_radius_cornner_red));
                viewHolder.tvprice.setTextColor(activity.getResources().getColor(R.color.login_btn_normal_color));
            }
            if (newActivityItemDevas.getPrice()==0){
                viewHolder.tvprice.setText("免费");
            }else {
                viewHolder.tvprice.setText("￥"+newActivityItemDevas.getPrice());
            }
            viewHolder.llItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent=new Intent(activity, ActivityDetailsActivity.class);
                    intent.putExtra("NewActivityItemDevas", newActivityItemDevas);
                    activity.startActivity(intent);
                }
            });
        }
    }
    @Override
    public int getItemViewType(int position) {
        if (mValues.size() <= 0) {
            return VIEW_TYPE;
        }
        return super.getItemViewType(position);
    }
    @Override
    public int getItemCount() {
        return mValues.size() > 0 ? mValues.size() : 1;
    }
    class empty extends RecyclerView.ViewHolder{
        private final TextView text_empty;
        public empty(View itemView) {
            super(itemView);
            text_empty=(TextView) itemView.findViewById(R.id.text_empty);
        }
    }
    class PersonViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.llitem) LinearLayout llItem;
        @BindView(R.id.ivImage)ImageView ivImage;
        @BindView(R.id.tvTitle)TextView tvTitle;
        @BindView(R.id.tvnickname) TextView tvnickname;
        @BindView(R.id.tvaddress) TextView tvaddress;
        @BindView(R.id.tvstartTime) TextView tvstartTime;
        @BindView(R.id.tvcollection) TextView tvcollection;
        @BindView(R.id.tvprice) TextView tvprice;
        public PersonViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
