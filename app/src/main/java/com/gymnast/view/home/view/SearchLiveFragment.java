package com.gymnast.view.home.view;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import com.gymnast.R;
import com.gymnast.data.net.API;
import com.gymnast.utils.DialogUtil;
import com.gymnast.utils.LiveUtil;
import com.gymnast.utils.PostUtil;
import com.gymnast.utils.StringUtil;
import com.gymnast.view.home.adapter.SearchLiveAdapter;
import com.gymnast.view.live.activity.LiveActivity;
import com.gymnast.view.live.entity.LiveItem;
import com.gymnast.view.personal.activity.PersonalOtherHomeActivity;
import com.gymnast.view.user.LoginActivity;
import org.json.JSONArray;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
/**
 * Created by zzqybyb19860112 on 2016/9/4.
 */
public class SearchLiveFragment extends Fragment {
    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    List<LiveItem> list=new ArrayList<>();
    SearchLiveAdapter adapter;
    LiveItem liveItem;
    public static final  int HANDLE_DATA=6;
    public static final int UPDATE_STATE_OK=1;
    public static final int MAINUSER_IN_OK=2;
    public static final int MAINUSER_IN_ERROR=3;
    public static final int OTHERUSER_IN_OK=4;
    public static final int OTHERUSER_IN_ERROR=5;
    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case HANDLE_DATA:
                    adapter=new SearchLiveAdapter(getActivity(),list);
                    adapter.setFriends(list);
                    recyclerView.setAdapter(adapter);
                    adapter.getFilter().filter(HomeSearchResultAcitivity.getSearchText());
                    adapter.notifyDataSetChanged();
                    adapter.setOnItemClickListener(new SearchLiveAdapter.OnItemClickListener() {
                        @Override
                        public void OnItemPhotoClick(View view, LiveItem live) {
                            if (view.getId() == R.id.rivSearchPhoto){
                                liveItem = live;
                                Intent intent1 = new Intent(getActivity(), PersonalOtherHomeActivity.class);
                                int userDevasID=Integer.valueOf(liveItem.getLiveOwnerId());
                                intent1.putExtra("UserID", userDevasID);
                                getActivity().startActivity(intent1);
                            }else  if (view.getId() == R.id.ivSearchBig){
                                liveItem = live;
                                LiveUtil.doIntoLive(getActivity(),handler,liveItem);
                            }
                        }
                    });
                    break;
                case UPDATE_STATE_OK:
                    Toast.makeText(getActivity(),"您开启了直播",Toast.LENGTH_SHORT).show();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
       View view = inflater.inflate(R.layout.search_live_fragment, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.recycleView);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        setData();
        return  view;
    }
    private void setData() {
        new Thread(){
            @Override
            public void run() {
                try{
                    String uri= API.BASE_URL+"/v1/search/model";
                    HashMap<String,String> params=new HashMap<String, String>();
                    params.put("model","4");
                    params.put("pageNumber","100");
                    params.put("pages", "1");
                    String result = PostUtil.sendPostMessage(uri, params);
                    Log.i("tag","Live"+result);
                    JSONObject json = new JSONObject(result);
                    final SharedPreferences share = getActivity().getSharedPreferences("UserInfo", Context.MODE_PRIVATE);
                    final String userId = share.getString("UserId", "");
                    if (json.getInt("state")==200){
                        JSONArray data = json.getJSONArray("data");
                        for (int i=0;i<data.length();i++){
                            JSONObject object=data.getJSONObject(i);
                            LiveItem live=new LiveItem();
                            String imageUrl=StringUtil.isNullImageUrl(object.getString("bgmUrl"));
                             String title=object.getString("title");
                            String groupId=StringUtil.isNullGroupId(object.getString("groupId"));
                           int liveId=object.getInt("id");
                            long startTime=object.getLong("createTime");
                            int state=object.getInt("state");
                            JSONObject account=object.getJSONObject("account");
                            String authTemp=StringUtil.isNullAuth(account.getString("auth"));
                            String authInfoTemp="";
                            if (authTemp!=null&&!authTemp.equals("")){
                                JSONObject auth=new JSONObject(authTemp);
                                authInfoTemp=auth.getString("authinfo");
                            }
                            String nickName=account.getString("nickName");
                            String avatar=StringUtil.isNullAvatar(account.getString("avatar"));
                            String liveOwnerId=account.getString("id");
                            int number=object.getString("number")==null?0:object.getInt("number");
                            live.setUserType(liveOwnerId.equals(userId) ? LiveActivity.USER_MAIN : LiveActivity.USER_OTHER);
                            live.setBigPictureUrl(imageUrl);
                            live.setLiveId(liveId);
                            live.setTitle(title);
                            live.setGroupId(groupId);
                            live.setMainPhotoUrl(API.IMAGE_URL + avatar);
                            live.setCurrentNum(number);
                            live.setLiveOwnerId(liveOwnerId);
                            live.setStartTime(startTime);
                            live.setAuthInfo(authInfoTemp);
                            live.setNickName(nickName);
                            live.setLiveState(state);
                            list.add(live);
                        }
                    }else{
                        list=new ArrayList<LiveItem>();
                    }
                   handler.sendEmptyMessage(HANDLE_DATA);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }.start();
    }
}
