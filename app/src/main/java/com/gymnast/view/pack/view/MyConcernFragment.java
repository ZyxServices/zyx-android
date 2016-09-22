package com.gymnast.view.pack.view;

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
import com.gymnast.data.personal.DynamicData;
import com.gymnast.utils.GetUtil;
import com.gymnast.utils.StringUtil;
import com.gymnast.view.personal.activity.PersonalDynamicDetailActivity;
import com.gymnast.view.personal.adapter.DynamicAdapter;
import com.gymnast.view.user.LoginActivity;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
public class MyConcernFragment extends Fragment {
    RecyclerView rvMyConcern;
    List<DynamicData> activityList=new ArrayList<>();
    private View view;
    private String token;
    private int start=0;
    private int pageSize=100;
    private LinearLayout llMyConcernLogin;
    private TextView tvMyConcernLogin;
    private String userId;
    private String authInfo;
    public static final int HANFLE_DATA_UPDATE=1;
    private DynamicAdapter adapter;
    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case HANFLE_DATA_UPDATE:
                    adapter = new DynamicAdapter(getActivity(),activityList);
                    rvMyConcern.setAdapter(adapter);
                    adapter.setOnItemClickListener(new DynamicAdapter.OnItemClickListener() {
                        @Override
                        public void OnItemClickListener(View view, int position) {
                            if(activityList.size()!=0){
                                DynamicData item= activityList.get(position);
                                Intent i=new Intent(getActivity(),PersonalDynamicDetailActivity.class);
                                i.putExtra("item",item);
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
        view = inflater.inflate(R.layout.fragment_recyclerview, container, false);
        SharedPreferences share = getActivity().getSharedPreferences("UserInfo", Context.MODE_PRIVATE);
        token = share.getString("Token",null);
        userId = share.getString("UserId",null);
        setview();
        initView();
        getdata();
        return view;
    }
    private void initView() {
        if(TextUtils.isEmpty(token)){
            llMyConcernLogin.setVisibility(View.VISIBLE);
            rvMyConcern.setVisibility(View.GONE);
        }else {
            llMyConcernLogin.setVisibility(View.GONE);
            rvMyConcern.setVisibility(View.VISIBLE);
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
    private void setview() {
        rvMyConcern = (RecyclerView) view.findViewById(R.id.rvMyConcern);
        llMyConcernLogin= (LinearLayout) view.findViewById(R.id.llMyConcernLogin);
        tvMyConcernLogin= (TextView) view.findViewById(R.id.tvMyConcernLogin);
    }
    public void getdata() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String uri= API.BASE_URL+"/v1/concern/getFollow/"+token+"/"+userId+"/"+start+"/"+pageSize;
                    HashMap<String,String> params=new HashMap<String, String>();
                    String result= GetUtil.sendGetMessage(uri,params);
                    JSONObject json=new JSONObject(result);
                    JSONArray data = json.getJSONArray("data");
                    for (int i=0;i<data.length();i++){
                        ArrayList<String> imageURL=new ArrayList<String>();
                        JSONObject  object=  data.getJSONObject(i);
                        JSONObject  userVo= object.getJSONObject("userVo");
                        String tempAuth= StringUtil.isNullAuth(object.getString("userAuthVo"));
                        if(!tempAuth.equals("")){
                            JSONObject accountAuth=new JSONObject(tempAuth);
                            authInfo=accountAuth.getString("authInfo");
                        }
                        String  returnNickName=userVo.getString("nickName");
                        String returnAvatar=userVo.getString("avatar");
                        int returnAuthenticate=userVo.getInt("authenticate");
                        int returnId=object.getInt("id");
                        long returnCreateTime= object.getLong("createTime");
                        int returnType=object.getInt("type");
                        String  returnTopicTitle= object.getString("topicTitle");
                        String returnTopicContent=object.getString("topicContent");
                        int  returnZanCounts=object.getInt("zanCounts");
                        int returnCommentCounts=object.getInt("commentCounts");
                        String returnVideoUrl=object.getString("videoUrl");
                        int  returnState=object.getInt("state");
                        String urls= object.getString("imgUrl");
                        if (urls==null|urls.equals("null")|urls.equals("")){
                        }else {
                            String [] imageUrls=urls.split(",");
                            for (int j=0;j<imageUrls.length;j++){
                                imageURL.add(API.IMAGE_URL+imageUrls[j]);
                            }
                        }
                        DynamicData data1=new DynamicData();
                        data1.setId(returnId);
                        data1.setType(returnType);
                        data1.setImgUrl(imageURL);
                        data1.setNickName(returnNickName);
                        data1.setTopicContent(returnTopicContent);
                        data1.setTopicTitle(returnTopicTitle);
                        data1.setAuthenticate(returnAuthenticate);
                        data1.setAvatar(returnAvatar);
                        data1.setCommentCounts(returnCommentCounts);
                        data1.setZanCounts(returnZanCounts);
                        data1.setCreateTime(returnCreateTime);
                        if(!tempAuth.equals("")){
                            data1.setAuthInfo(authInfo);
                        }
                        data1.setState(returnState);
                        data1.setAuthInfo("");
                        //data1.setTopicVisible(return_topicVisible);
                        activityList.add(data1);
                    }
                    handler.sendEmptyMessage(HANFLE_DATA_UPDATE);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}