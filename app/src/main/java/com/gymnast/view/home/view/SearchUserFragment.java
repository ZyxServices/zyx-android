package com.gymnast.view.home.view;

import android.content.Context;
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
import com.gymnast.R;
import com.gymnast.data.net.API;
import com.gymnast.utils.GetUtil;
import com.gymnast.utils.PostUtil;
import com.gymnast.utils.StringUtil;
import com.gymnast.view.home.adapter.SearchUserAdapter;
import com.gymnast.data.user.SearchUserEntity;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
/**
 * Created by zzqybyb19860112 on 2016/9/4.
 */
public class SearchUserFragment extends Fragment {
    RecyclerView rvFound;
    List<SearchUserEntity> entities=new ArrayList<>();
    SearchUserAdapter adapter;
    public static final int HANDLE_DATA=1;
    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if(msg.what==HANDLE_DATA){
                adapter=new SearchUserAdapter(getActivity(),entities);
                adapter.setFriends(entities);
                rvFound.setAdapter(adapter);
                adapter.getFilter().filter(HomeSearchResultAcitivity.getSearchText());
                adapter.notifyDataSetChanged();
            }
        }
    };
    private String fromId,token;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater,container,savedInstanceState);
        View view=inflater.inflate(R.layout.search_user_fragment,null);
        rvFound= (RecyclerView) view.findViewById(R.id.recycleView);
        rvFound.setHasFixedSize(false);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        rvFound.setLayoutManager(layoutManager);
        getInfo();
        setData();
        return view;
    }

    private void getInfo() {
        SharedPreferences share= getActivity().getSharedPreferences("UserInfo", Context.MODE_PRIVATE);
        fromId = share.getString("UserId","");
        token = share.getString("Token","");
    }

    private void setData() {
        new Thread(){
            @Override
            public void run() {
                try {
                    String uri= API.BASE_URL+"/v1/search/model";
                    HashMap<String,String> params=new HashMap<String, String>();
                    params.put("userId",fromId);
                    params.put("model","1");
                    params.put("pageNumber","100");
                    params.put("pages","1");
                    String result= PostUtil.sendPostMessage(uri,params);
                    Log.i("tag","User"+result);
                    JSONObject jsonObject=new JSONObject(result);
                    if (jsonObject.getInt("state")==200){
                        JSONArray data=jsonObject.getJSONArray("data");
                        for (int i=0;i<data.length();i++){
                            JSONObject object=data.getJSONObject(i);
                            SearchUserEntity entity=new SearchUserEntity();
                            entity.setName(object.getString("nickName"));
                            entity.setId(object.getInt("id"));
                            entity.setFollowed(object.getBoolean("atten"));
                            entity.setPhotoUrl(object.getString("avatar"));
                            entity.setAuthenticate(object.getInt("authenticate"));
                            String authTemp= StringUtil.isNullAuth(object.getString("auth"));
                            if (!authTemp.equals("")&&!authTemp.equals("null")){
                                JSONObject auth=  new JSONObject(authTemp);
                                entity.setType(auth.getString("authinfo"));
                            }
                            entities.add(entity);
                        }
                    }else {
                        entities=new ArrayList<SearchUserEntity>();
                    }
                    handler.sendEmptyMessage(HANDLE_DATA);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }.start();
    }
}
