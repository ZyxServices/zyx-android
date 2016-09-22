package com.gymnast.view.home.view;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.gymnast.R;
import com.gymnast.data.net.API;
import com.gymnast.data.personal.CircleData;
import com.gymnast.utils.PostUtil;
import com.gymnast.utils.StringUtil;
import com.gymnast.view.home.adapter.SearchCircleAdapter;
import org.json.JSONArray;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by zzqybyb19860112 on 2016/9/4.
 */
public class SearchCircleFragment extends Fragment {
    private RecyclerView listitem;
    private View view;
    List<CircleData> activityList=new ArrayList<>();;
     SearchCircleAdapter adapter;
    public static final int HANFLE_DATA_UPDATE=1;
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case HANFLE_DATA_UPDATE:
                    if (activityList.size()!=0){
                        adapter = new SearchCircleAdapter(getActivity(),activityList);
                        adapter.setFriends(activityList);
                        listitem.setAdapter(adapter);
                        adapter.getFilter().filter(HomeSearchResultAcitivity.getSearchText());
                        adapter.notifyDataSetChanged();
                    }
                    break;
            }
        }
    };
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_recyclerview, container, false);
        setView();
        getData();
        return  view;
    }
    private void setView() {
        listitem = (RecyclerView) view.findViewById(R.id.rvMyConcern);
        listitem.setVisibility(View.VISIBLE);
    }
    public void getData() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String uri= API.BASE_URL+"/v1/search/model";
                HashMap<String,String> params=new HashMap<String, String>();
                params.put("model","2");
                params.put("pageNumber","100");
                params.put("pages","1");
                String result= PostUtil.sendPostMessage(uri, params);
                Log.i("tag", "Circle" + result);
                try {
                    JSONObject jsonObject=new JSONObject(result);
                    if (jsonObject.getInt("state")==200){
                        JSONArray data=jsonObject.getJSONArray("data");
                        if(jsonObject.getInt("state")==200){
                            for(int i=0;i<data.length();i++){
                                JSONObject object = data.getJSONObject(i);
                                CircleData circleData= new CircleData();
                                int id= object.getInt("id");
                                String title= object.getString("title");
                                circleData.setConcerned(object.getBoolean("atten"));
                                circleData.setTitle(title);
                                circleData.setId(id);
                                String headImgUrl= StringUtil.isNullAvatar(object.getString("headImgUrl"));
                                String accountTemp=StringUtil.isNullAuth(object.getString("account"));
                                int circleItemCount=0;
                                if (!accountTemp.equals("")){
                                    JSONObject account=new JSONObject(accountTemp);
                                    circleItemCount= account.getInt("authenticate");
                                    String authTemp= StringUtil.isNullAuth(account.getString("auth"));
                                    if (!authTemp.equals("")&&!authTemp.equals("null")){
                                        JSONObject auth=  new JSONObject(authTemp);
                                        circleData.setDetails(auth.getString("authinfo"));
                                    }else {
                                        circleData.setDetails("");
                                    }
                                }else {
                                    circleData.setDetails("");
                                }
                                circleData.setHeadImgUrl(headImgUrl);
                                circleData.setCircleItemCount(circleItemCount);
                                activityList.add(circleData);
                            }
                    }else {
                        activityList=new ArrayList<CircleData>();
                    }
                        handler.sendEmptyMessage(HANFLE_DATA_UPDATE);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}

