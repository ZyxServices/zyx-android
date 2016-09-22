package com.gymnast.view.personal.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.gymnast.R;
import com.gymnast.data.net.API;
import com.gymnast.data.personal.CircleData;
import com.gymnast.utils.GetUtil;
import com.gymnast.utils.StringUtil;
import com.gymnast.view.personal.activity.PersonalCircleActivity;
import com.gymnast.view.personal.adapter.CircleAdapter;
import com.gymnast.view.user.LoginActivity;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
/**
 * Created by yf928 on 2016/7/18.
 */
public class CircleListFragment extends Fragment {
    private RecyclerView listitem;
    private View view;
    private String token;
    List<CircleData> activityList=new ArrayList<>();;
    private LinearLayout llMyConcernLogin;
    private TextView tvMyConcernLogin;
    private String id;
    private TextView error;
    private CircleAdapter adapter;
    public static final int HANFLE_DATA_UPDATE=1;
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case HANFLE_DATA_UPDATE:
                    adapter = new CircleAdapter(getActivity(),activityList);
                    listitem.setAdapter(adapter);
                    adapter.setOnItemClickListener(new CircleAdapter.OnItemClickListener() {
                        public String dsa;
                        @Override
                        public void OnItemClickListener(View view, int position) {
                            if(activityList.size()!=0){
                                CircleData   item=   activityList.get(position);
                                Intent i=new Intent(getActivity(), PersonalCircleActivity.class);
                                i.putExtra("CircleId",item.getId());
                                startActivity(i);
                            }
                        }
                    });
                    adapter.notifyDataSetChanged();
                    break;
            }
        }
    };
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_recyclerview,container,false);
        SharedPreferences share = getActivity().getSharedPreferences("UserInfo", Context.MODE_PRIVATE);
        token = share.getString("Token","");
        id = share.getString("UserId","");
        setView();
        initView();
        getData();
        return  view;
    }
    private void setView() {
        listitem = (RecyclerView) view.findViewById(R.id.rvMyConcern);
        llMyConcernLogin= (LinearLayout) view.findViewById(R.id.llMyConcernLogin);
        tvMyConcernLogin= (TextView) view.findViewById(R.id.tvMyConcernLogin);
        error= (TextView) view.findViewById(R.id.error);
    }
    private void initView() {
        if(TextUtils.isEmpty(token)){
            llMyConcernLogin.setVisibility(View.VISIBLE);
            listitem.setVisibility(View.GONE);
        }else {
            llMyConcernLogin.setVisibility(View.GONE);
            listitem.setVisibility(View.VISIBLE);
        }
        tvMyConcernLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });
    }
    @Override public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }
    public void getData() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String uri= API.BASE_URL+"/v1/my/circle/list";
                HashMap<String,String> params= new HashMap<String,String>();
                params.put("token",token);
                params.put("accountId",id);
                String result= GetUtil.sendGetMessage(uri,params);
                try {
                    JSONObject  obj=new JSONObject(result);
                    JSONObject data = obj.getJSONObject("data");
                    JSONArray myCreateCircle = data.getJSONArray("myCreateCircle");
                    if(obj.getInt("state")==200){
                        for(int i=0;i<myCreateCircle.length();i++){
                            JSONObject object = myCreateCircle.getJSONObject(i);
                            String return_title= object.getString("title");
                            int return_circleItemCount= object.getInt("circleItemCount");
                            int return_id= object.getInt("id");
                            String return_headImgUrl= StringUtil.isNullAvatar(object.getString("headImgUrl"));// API.IMAGE_URL+ URI.create(object.getString("headImgUrl")).getPath();
                            CircleData circleData= new CircleData();
                            circleData.setTitle(return_title);
                            circleData.setHeadImgUrl(return_headImgUrl);
                            circleData.setCircleItemCount(return_circleItemCount);
                            circleData.setId(return_id);
                            activityList.add(circleData);
                        }
                        handler.sendEmptyMessage(HANFLE_DATA_UPDATE);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    @Override
    public void onResume() {
        super.onResume();
        if(activityList.size()!=0){
            activityList.clear();
            getData();
        }else {

        }

    }
}
