package com.gymnast.view.home.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.ButterKnife;
import com.gymnast.R;
import com.gymnast.utils.PicUtil;
import com.gymnast.utils.PicassoUtil;
import com.gymnast.view.live.entity.LiveItem;
import com.makeramen.roundedimageview.RoundedImageView;
import java.util.List;
public class StandSquareLiveRecyclerViewAdapter extends RecyclerView.Adapter {
  private final List<LiveItem> mValues;
  private Activity activity;
  public StandSquareLiveRecyclerViewAdapter(Activity activity, List<LiveItem> items) {
    mValues = items;
    this.activity = activity;
  }
  private OnItemClickListener onItemClickListener;
  public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
    this.onItemClickListener = onItemClickListener;
  }
  public interface OnItemClickListener {
    void OnBigPhotoClick(View view, LiveItem liveItem);
  }
  @Override public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    LayoutInflater inflater = LayoutInflater.from(parent.getContext());
    View view =inflater.inflate(R.layout.fragment_stand_live, parent, false);
    return new MyViewHolder(view);
  }
  @Override
  public void onBindViewHolder(final RecyclerView.ViewHolder viewHolder, final int position) {
    if (viewHolder instanceof MyViewHolder ){
    final MyViewHolder holder=(MyViewHolder)viewHolder;
    final LiveItem live=mValues.get(position);
    PicassoUtil.handlePic(activity, PicUtil.getImageUrl(activity, live.getBigPictureUrl()), holder.ivItemBig, 494, 368);
    holder.tvTitleSmall.setText(live.title);
    holder.tvNowNumber.setText(live.currentNum + "");
    if(onItemClickListener!=null){
      holder.ivItemBig.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
          //注意，这里的position不要用上面参数中的position，会出现位置错乱\
          onItemClickListener.OnBigPhotoClick(holder.ivItemBig,live);
        }
      });
    }
    }
  }
  @Override public int getItemCount() {
    return mValues.size();
  }
  public class MyViewHolder extends RecyclerView.ViewHolder {
    com.makeramen.roundedimageview.RoundedImageView rivPhotoSmall;
    ImageView ivItemBig;
    TextView tvNowNumber;
    TextView tvTitleSmall;
    public MyViewHolder(View view) {
      super(view);
      ButterKnife.bind(this, view);
      ivItemBig= (ImageView) view.findViewById(R.id.ivItemBig);
      rivPhotoSmall= (RoundedImageView) view.findViewById(R.id.rivPhotoSmall);
      tvNowNumber= (TextView) view.findViewById(R.id.tvNowNumber);
      tvTitleSmall= (TextView) view.findViewById(R.id.tvTitleSmall);
    }
  }
}
