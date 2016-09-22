package com.gymnast.view.live.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.gymnast.R;
import com.gymnast.data.hotinfo.LiveMessage;
import com.gymnast.utils.PicUtil;
import com.gymnast.utils.PicassoUtil;
import com.gymnast.utils.StringUtil;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Administrator on 2016/7/29.
 */
public class MessageAdapter extends RecyclerView.Adapter {
    Context context;
    ArrayList<LiveMessage> messageList;
    public MessageAdapter(Context context,ArrayList<LiveMessage> messageList){
        this.context=context;
        if (messageList.size()==0){
            this.messageList=new ArrayList<>();
        }else {
            this.messageList=messageList;
        }
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View viewItem = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recyleview_recent_activity_message, null);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        viewItem.setLayoutParams(lp);
            return new MessageViewHolder(viewItem);
    }
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        MessageViewHolder viewHolder= (MessageViewHolder) holder;
        LiveMessage message=messageList.get(position);
         Picasso.with(context).load(message.getIconUrl()).into(viewHolder.civPhoto);
        String content=message.getContent();
        if (!content.equals("")){
            viewHolder.ivPic.setVisibility(View.GONE);
            viewHolder.tvContent.setVisibility(View.VISIBLE);
        }else {
            viewHolder.ivPic.setVisibility(View.VISIBLE);
            viewHolder.tvContent.setVisibility(View.GONE);
            String url= PicUtil.getImageUrl(context, StringUtil.isNullAvatar(message.getPictureUrl()));
            url= PicUtil.getImageUrlDetail(context, url, 328, 122);
            String begin=message.getPictureUrl().substring(0,4);
            if (begin.equals("http")){
                PicassoUtil.handlePic(context,message.getPictureUrl(),viewHolder.ivPic,328,122);
            }else {
                PicassoUtil.handlePic(context,url, viewHolder.ivPic, 328, 122);
            }
        }
        if (position==0){
            viewHolder.tvContent.setTextColor(Color.RED);
        }
        viewHolder.tvContent.setText(message.getContent());
        viewHolder.tvTimeUtilNow.setText(message.getTimeUntilNow());
        viewHolder.tvTimeUtilNow.setBackgroundColor(context.getResources().getColor(R.color.common_bg));
        viewHolder.tvTimeUtilNow.setTextColor(context.getResources().getColor(R.color.day_edit_hit_color));
    }
    @Override
    public int getItemCount() {
        return messageList.size();
    }
    class MessageViewHolder extends RecyclerView.ViewHolder{
        CircleImageView civPhoto;
        ImageView ivPic;
        TextView tvTimeUtilNow,tvContent;
        public MessageViewHolder(View itemView) {
            super(itemView);
            civPhoto= (CircleImageView) itemView.findViewById(R.id.civPhoto);
            ivPic= (ImageView) itemView.findViewById(R.id.ivPic);
            tvTimeUtilNow= (TextView) itemView.findViewById(R.id.tvTimeUtilNow);
            tvContent= (TextView) itemView.findViewById(R.id.tvContent);
        }
    }
}
