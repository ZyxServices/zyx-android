package com.gymnast.view.home.fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.BindView;
import com.gymnast.R;
import com.gymnast.utils.PicUtil;
import com.gymnast.view.BaseFragment;
import com.gymnast.view.live.activity.LiveActivity;
import com.gymnast.view.live.activity.MoreLiveActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
public class StandFragment extends BaseFragment implements View.OnClickListener{
  @BindView(R.id.stand_tab)  TabLayout tab;
  @BindView(R.id.stand_vp)   ViewPager vp;
  ImageView ivBigPic1,ivBigPic2,ivBigPic3,ivBigPic4;
  TextView tvLiveNumber,tvTitle,stand_more;
  LinearLayout llYuGao;
  int []bigPics={R.mipmap.ic_test_0,R.mipmap.ic_test_1,R.mipmap.ic_test_2,R.mipmap.ic_test_3};
  public static final int HANDLE_PICS=1;
  int k=0;
  Handler handler=new Handler(){
    @Override
    public void handleMessage(Message msg) {
      super.handleMessage(msg);
      Drawable drawable;
      switch (msg.what){
        case HANDLE_PICS:
           drawable = getActivity().getResources().getDrawable(bigPics[a + j == 4 ? 0:a+j]);
          ivBigPic1.setImageDrawable(drawable);
          ivBigPic1.setDrawingCacheEnabled(true);
          ivBigPic1.invalidate();
          drawable = getActivity().getResources().getDrawable(bigPics[b + j == 4 ? 0 : b + j]);
          ivBigPic2.setImageDrawable(drawable);
          ivBigPic2.setDrawingCacheEnabled(true);
          ivBigPic2.invalidate();
          drawable = getActivity().getResources().getDrawable(bigPics[c + j == 4 ? 0 : c + j]);
          ivBigPic3.setImageDrawable(drawable);
          ivBigPic3.setDrawingCacheEnabled(true);
          ivBigPic3.invalidate();
          drawable = getActivity().getResources().getDrawable(bigPics[d + j == 4 ? 0 : d + j]);
          ivBigPic4.setImageDrawable(drawable);
          ivBigPic4.setDrawingCacheEnabled(true);
          ivBigPic4.invalidate();
          a=a+j;
          b=b+j;
          c=c+j;
          d=d+j;
          if (a==4)a=0;
          if (b==4)b=0;
          if (c==4)c=0;
          if (d==4)d=0;
            llYuGao.setVisibility(View.GONE);
          llYuGao.invalidate();
          break;
      }
    }
  };
  String[] titles = new String[]{"广场", "大咖", "世界杯", "NBA", "WTA", "冠军杯", "亚冠"};
  int a=0,b=1,c=2,d=3;
  int j=1;
  TimerTask task=new TimerTask() {
    @Override
    public void run() {
      k+=1;
      Message msg=new Message();
      msg.what=HANDLE_PICS;
      handler.sendMessage(msg);
    }
  };
  private Timer timer;
  public static FragmentStatePagerAdapter pa;
   StandSquareFragment standSquareFragment;
  ACupFragment aCupFragment;
  AsiaCupFragment asiaCupFragment;
  BigCupFragment bigCupFragment;
  NBAFragment nbaFragment;
  WorldCupFragment worldCupFragment;
  WTAFragment wtaFragment;
  FragmentManager manager;
  public static Fragment mCurrentFragment;
  @Override public void onActivityCreated(Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);
    tab.setTabGravity(TabLayout.GRAVITY_FILL);
    tab.setTabMode(TabLayout.MODE_SCROLLABLE);
    if(savedInstanceState==null){
      standSquareFragment = new StandSquareFragment();
      bigCupFragment = new BigCupFragment();
      worldCupFragment = new WorldCupFragment();
      nbaFragment = new NBAFragment();
      wtaFragment = new WTAFragment();
      aCupFragment = new ACupFragment();
      asiaCupFragment = new AsiaCupFragment();
      manager= getChildFragmentManager();
      FragmentTransaction transaction=manager.beginTransaction();
      transaction.add(standSquareFragment,""+1);
      transaction.add(bigCupFragment, "" + 2);
      transaction.add(worldCupFragment, "" + 3);
      transaction.add(nbaFragment, "" + 4);
      transaction.add(wtaFragment, "" + 5);
      transaction.add(aCupFragment, "" + 6);
      transaction.add(asiaCupFragment, "" + 7);
      transaction.commit();
    }
     pa = new FragmentStatePagerAdapter(manager) {
      @Override public Fragment getItem(int position) {
          if (position==0){
            return standSquareFragment.newInstance("测试", "" + 1, getActivity());
          }else if (position==1){
            return bigCupFragment.newInstance("测试", "" + 2,getActivity());
          }else if (position==2){
            return worldCupFragment.newInstance("测试", "" + 3,getActivity());
          }else if (position==3){
            return nbaFragment.newInstance("测试", "" + 4,getActivity());
          }else if (position==4){
            return wtaFragment.newInstance("测试", "" + 5,getActivity());
          }else if (position==5){
            return aCupFragment.newInstance("测试", "" + 6,getActivity());
          }else if (position==6){
            return asiaCupFragment.newInstance("测试", "" + 7,getActivity());
          }
        return null;
      }
       @Override
       public void destroyItem(ViewGroup container, int position, Object object) {
       }
       @Override public int getCount() {
        return titles.length;
      }
      @Override public CharSequence getPageTitle(int position) {
        return titles[position];
      }
       @Override
       public void setPrimaryItem(ViewGroup container, int position, Object object) {
          mCurrentFragment = (Fragment)object;
          super.setPrimaryItem(container, position, object);
          }
     };
    vp.setAdapter(pa);
    vp.setOffscreenPageLimit(0);
    tab.setupWithViewPager(vp);
    timer=new Timer();
    timer.schedule(task, 0, 5000);
  }
  public static Fragment getCurrentFragment() {
    return mCurrentFragment;
  }
  @Override protected int getLayout() {
    return R.layout.fragment_stand;
  }
  @Override protected void initViews(Bundle savedInstanceState) {
    ivBigPic1= (ImageView) getActivity().findViewById(R.id.ivBigPic1);
    ivBigPic2= (ImageView) getActivity().findViewById(R.id.ivBigPic2);
    ivBigPic3= (ImageView) getActivity().findViewById(R.id.ivBigPic3);
    ivBigPic4= (ImageView) getActivity().findViewById(R.id.ivBigPic4);
    tvLiveNumber= (TextView) getActivity().findViewById(R.id.tvLiveNumber);
    stand_more= (TextView) getActivity().findViewById(R.id.stand_more);
    tvTitle= (TextView) getActivity().findViewById(R.id.tvTitle);
    llYuGao= (LinearLayout) getActivity().findViewById(R.id.llYuGao);
  }
  @Override protected void initListeners() {
//    ivBigPic1.setOnClickListener(this);
//    ivBigPic2.setOnClickListener(this);
//    ivBigPic3.setOnClickListener(this);
//    ivBigPic4.setOnClickListener(this);
    stand_more.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        getActivity().startActivity(new Intent(getActivity(), MoreLiveActivity.class));
      }
    });
  }
   protected void initData() {
  }
  @Override
  public void onClick(View v) {
    Intent intent1=new Intent(getActivity(), LiveActivity.class);
    int type=LiveActivity.USER_OTHER;
    int number=Integer.valueOf(tvLiveNumber.getText().toString());
    String title=tvTitle.getText().toString();
    Bundle bundle=new Bundle();
    bundle.putParcelable("bitmapBigPic", ivBigPic1.getDrawingCache());
    Bitmap bitmapPhoto= BitmapFactory.decodeResource(getActivity().getResources(),R.mipmap.koulan);
    bitmapPhoto= PicUtil.compress(bitmapPhoto, 100, 100);
    bundle.putParcelable("bitmapSmallPhoto", bitmapPhoto);
    bundle.putInt("number", number);
    bundle.putInt("type", type);
    bundle.putString("title", title);
    Bitmap bitmap1=null;
    switch (v.getId()) {
      case R.id.ivBigPic1:
        bitmap1=ivBigPic1.getDrawingCache();
        bitmap1= PicUtil.compress(bitmap1, 100, 100);
        bundle.putParcelable("bitmapBigPic",bitmap1);
        break;
      case R.id.ivBigPic2:
        bitmap1=ivBigPic2.getDrawingCache();
        bitmap1= PicUtil.compress(bitmap1, 100, 100);
        bundle.putParcelable("bitmapBigPic",bitmap1);
        break;
      case R.id.ivBigPic3:
        bitmap1=ivBigPic3.getDrawingCache();
        bitmap1= PicUtil.compress(bitmap1, 100, 100);
        bundle.putParcelable("bitmapBigPic",bitmap1);
        break;
      case R.id.ivBigPic4:
        bitmap1=ivBigPic4.getDrawingCache();
        bitmap1= PicUtil.compress(bitmap1, 100, 100);
        bundle.putParcelable("bitmapBigPic",bitmap1);
        break;
    }
    intent1.putExtras(bundle);
    getActivity().startActivity(intent1);
  }
  @Override
  public void onDestroy() {
    handler.removeMessages(HANDLE_PICS);
    task.cancel();
    super.onDestroy();
  }
}
