package com.gymnast.view.home.view;

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
import com.gymnast.R;
import com.gymnast.data.hotinfo.NewActivityItemDevas;
import com.gymnast.data.net.API;
import com.gymnast.utils.PostUtil;
import com.gymnast.view.home.adapter.SearchActiveAdapter;
import org.json.JSONArray;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
/**
 * Created by zzqybyb19860112 on 2016/9/4.
 */
public class SearchActiveFragment extends Fragment {
    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    List<NewActivityItemDevas> activityList=new ArrayList<>();
    public static final int HANFLE_DATA_UPDATE=1;
    SearchActiveAdapter adapter;
    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case HANFLE_DATA_UPDATE:
                    adapter = new SearchActiveAdapter(getActivity(),activityList);
                    adapter.setFriends(activityList);
                    recyclerView.setAdapter(adapter);
                    adapter.getFilter().filter(HomeSearchResultAcitivity.getSearchText());
                    adapter.notifyDataSetChanged();
                    break;
            }
        }
    };
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view=inflater.inflate(R.layout.search_active_fragment,null);
        recyclerView= (RecyclerView) view.findViewById(R.id.recycleView);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        initData();
        return view;
    }
    private void initData() {
        new Thread(){
            @Override
            public void run() {
                try {
                    String uri= API.BASE_URL+"/v1/search/model";
                    HashMap<String,String> params=new HashMap<String, String>();
                    params.put("model","3");
                    params.put("pageNumber","100");
                    params.put("pages","1");
                    String result = PostUtil.sendPostMessage(uri, params);
                    Log.i("tag", "Active" + result);
                    JSONObject json = new JSONObject(result);
                    if (json.getInt("state")==200){
                        JSONArray data = json.getJSONArray("data");
                        for (int i=0;i<data.length();i++){
                            JSONObject object=data.getJSONObject(i);
                            NewActivityItemDevas itemDevas=new NewActivityItemDevas();
                            int id=object.getInt("id");
                            String title=object.getString("title");
                            String imgUrls=object.getString("imgUrls");
                            long startTime=object.getLong("startTime");
                            int zanCount=object.getInt("zanCount");
                            int msgCount=object.getInt("msgCount");
                            JSONObject objectUser=object.getJSONObject("user");
                            String nickname=objectUser.getString("nickname");
                            int userID=objectUser.getInt("id");
                            itemDevas.setActiveId(id);
                            itemDevas.setTitle(title);
                            itemDevas.setImgUrls(imgUrls);
                            itemDevas.setStartTime(startTime);
                            itemDevas.setZanCount(zanCount);
                            itemDevas.setMsgCount(msgCount);
                            itemDevas.setNickname(nickname);
                            itemDevas.setUserID(userID);
                            itemDevas.setPageViews(object.getInt("pageviews"));
                            activityList.add(itemDevas);
                        }
                    }else {
                        activityList=new ArrayList<NewActivityItemDevas>();
                    }
                    handler.sendEmptyMessage(HANFLE_DATA_UPDATE);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }.start();
    }
}
