package com.gymnast.view.home.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import com.gymnast.R;
import com.gymnast.data.net.API;
import com.gymnast.utils.DialogUtil;
import com.gymnast.utils.LiveUtil;
import com.gymnast.utils.PicUtil;
import com.gymnast.utils.PostUtil;
import com.gymnast.utils.RefreshUtil;
import com.gymnast.utils.StringUtil;
import com.gymnast.view.home.adapter.StandSquareLiveRecyclerViewAdapter;
import com.gymnast.view.live.activity.LiveActivity;
import com.gymnast.view.live.entity.LiveItem;
import org.json.JSONArray;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
public class StandSquareFragment extends Fragment implements  SwipeRefreshLayout.OnRefreshListener {
  SwipeRefreshLayout reflesh;
  RecyclerView liveList;
  static List<LiveItem> liveItems = new ArrayList<>();
  static Bitmap mainPhoto;
  private   StandSquareFragment instance=null;
  public  StandSquareLiveRecyclerViewAdapter standLiveAdapter;
    static   LiveItem liveItem;
    public static final int UPDATE_STATE_OK=1;
    public static final int MAINUSER_IN_OK=2;
    public static final int MAINUSER_IN_ERROR=3;
    public static final int OTHERUSER_IN_OK=4;
    public static final int OTHERUSER_IN_ERROR=5;
    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case UPDATE_STATE_OK:
                    Toast.makeText(getActivity(), "您开启了直播", Toast.LENGTH_SHORT).show();
                    LiveUtil.doNext(getActivity(), liveItem);
                    break;
                case MAINUSER_IN_OK:
                    Toast.makeText(getActivity(),"您开启了直播",Toast.LENGTH_SHORT).show();
                    LiveUtil.doNext(getActivity(), liveItem);
                    break;
                case MAINUSER_IN_ERROR:
                    DialogUtil.goBackToLogin(getActivity(), "是否重新登陆？", "账号在其他地方登陆,您被迫下线！！！");
                    break;
                case OTHERUSER_IN_OK:
                    Toast.makeText(getActivity(),"您已进入直播室",Toast.LENGTH_SHORT).show();
                    LiveUtil.doNext(getActivity(), liveItem);
                    break;
                case OTHERUSER_IN_ERROR:
                    DialogUtil.goBackToLogin(getActivity(), "是否重新登陆？", "账号在其他地方登陆,您被迫下线！！！");
                    break;
            }
        }
    };
  public StandSquareFragment() {
  }
  public  StandSquareFragment newInstance(String param1, String param2,Context context) {
    mainPhoto= BitmapFactory.decodeResource(context.getResources(), R.mipmap.ic_test_3);
    mainPhoto= PicUtil.compress(mainPhoto, 100, 100);
    if(instance==null){
        initData(context);
      instance=new StandSquareFragment();
      }
    return instance;
  }
    private  void initData(final Context context) {
    new Thread(){
      @Override
      public void run() {
          if (liveItems.size()!=0){
              liveItems.clear();
          }
        SharedPreferences share = context.getSharedPreferences("UserInfo", Context.MODE_PRIVATE);
        String token = share.getString("Token", "");
        String userId = share.getString("UserId", "");
        HashMap<String, String> params = new HashMap<>();
        params.put("token", token);
        params.put("pageNo", 1 + "");
        params.put("pageSize", 6 + "");
        params.put("lab", "1");
        String uri = API.BASE_URL + "/v1/live/list/lab";
        String result = PostUtil.sendPostMessage(uri, params);
          Log.i("tag","StandSquare-----"+result);
        try {
          JSONObject object = new JSONObject(result);
            String state = object.getString("state");
            if (state.equals("302")) {
                return;
            }
          JSONArray array = object.getJSONArray("data");
          for (int j = 0; j < array.length(); j++) {
            JSONObject jsonObject = array.getJSONObject(j);
              LiveItem liveItem = new LiveItem();
              liveItem.setBigPictureUrl(StringUtil.isNullAvatar(jsonObject.getString("bgmUrl")));
              liveItem.setLiveId(jsonObject.getInt("id"));
              liveItem.setGroupId(StringUtil.isNullGroupId(jsonObject.getString("groupId")));
              liveItem.setCurrentNum(Integer.valueOf(StringUtil.isNullDATA(jsonObject.getString("watchNumber"))));
              liveItem.setLiveState(jsonObject.getInt("state"));
              liveItem.setStartTime(jsonObject.getLong("startTime"));
              liveItem.setTitle(jsonObject.getString("title"));
              JSONObject userObj = jsonObject.getJSONObject("userIconVo");
              int user = userObj.getInt("id");
              if (userId.equals(user + "")) {
                  liveItem.setUserType(LiveActivity.USER_MAIN);
              } else {
                  liveItem.setUserType(LiveActivity.USER_OTHER);
              }
              liveItem.setLiveOwnerId(user + "");
              liveItem.setMainPhotoUrl(StringUtil.isNullAvatar(userObj.getString("avatar")));
              liveItems.add(liveItem);
          }
        } catch (Exception e) {
          e.printStackTrace();
        }
      }
    }.start();
  }
    View rootView=null;
  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
    if (rootView==null){
      rootView = inflater.inflate(R.layout.fragment_stand_square, container, false);
      liveList = (RecyclerView) rootView.findViewById(R.id.stand_square_live_list);
      reflesh = (SwipeRefreshLayout) rootView.findViewById(R.id.srlReflesh);
      RefreshUtil.refresh(reflesh, getActivity());
    }
    return rootView;
  }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if(rootView!=null&&rootView.getParent()!=null){
            ((ViewGroup)rootView.getParent()).removeView(rootView);
        }
    }
    @Override public void onActivityCreated( Bundle savedInstanceState) {
             super.onActivityCreated(savedInstanceState);
        if (savedInstanceState==null){
            GridLayoutManager dynamicLayout = new GridLayoutManager(getActivity(), 2);
            standLiveAdapter = new StandSquareLiveRecyclerViewAdapter(getActivity(), liveItems);
            liveList.setLayoutManager(dynamicLayout);
            liveList.setAdapter(standLiveAdapter);
            reflesh.setRefreshing(false);
            standLiveAdapter.setOnItemClickListener(new StandSquareLiveRecyclerViewAdapter.OnItemClickListener() {
                @Override
                public void OnBigPhotoClick(View view, LiveItem liveItem) {
                    StandSquareFragment.liveItem = liveItem;
                    LiveUtil.doIntoLive(getActivity(), handler, liveItem);
                }
            });
            reflesh.setOnRefreshListener(this);
        }
  }
    @Override
    public void onRefresh() {
        initData(getActivity());
        new Handler().postDelayed(new Runnable() {
            @Override public void run() {
                // 停止刷新
                reflesh.setRefreshing(false);
            }
        }, 1000);
        if (liveItems.size()==0){
            Toast  toast = Toast.makeText(getActivity(),"暂时没有数据哦！",Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 300);
            toast.show();
        }
    }
}
