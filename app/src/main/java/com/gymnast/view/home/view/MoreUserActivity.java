package com.gymnast.view.home.view;

import android.os.Handler;
import android.os.Message;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import com.gymnast.R;
import com.gymnast.data.net.API;
import com.gymnast.utils.PostUtil;
import com.gymnast.utils.StringUtil;
import com.gymnast.view.ImmersiveActivity;
import com.gymnast.view.home.adapter.SearchUserAdapter;
import com.gymnast.data.user.SearchUserEntity;
import org.json.JSONArray;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
public class MoreUserActivity extends ImmersiveActivity {
    private EditText etSearch;
    private ImageView ivMoreBack;
    private RecyclerView recyclerView;
    List<SearchUserEntity> entities;
    SearchUserAdapter adapter;
    public static final int HANDLE_DATA=1;
    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if(msg.what==HANDLE_DATA){
                adapter=new SearchUserAdapter(MoreUserActivity.this,entities);
                adapter.setFriends(entities);
                recyclerView.setAdapter(adapter);
                adapter.getFilter().filter(etSearch.getText().toString());
                adapter.notifyDataSetChanged();
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_more_common);
        ivMoreBack= (ImageView) findViewById(R.id.ivMoreBack);
        TextView tvTabTitle= (TextView) findViewById(R.id.tvTabTitle);
        tvTabTitle.setText("更多用户");
        etSearch= (EditText) findViewById(R.id.etSearch);
        recyclerView = (RecyclerView) findViewById(R.id.recycleView);
        recyclerView.setHasFixedSize(false);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        setData();
        setListener();
    }
    private void setListener() {
        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                adapter.getFilter().filter(charSequence);
            }
            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
        ivMoreBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
    private void setData() {
        entities=new ArrayList<>();
        new Thread(){
            @Override
            public void run() {
                try {
                    String uri= API.BASE_URL+"/v1/search/model";
                    HashMap<String,String> params=new HashMap<String, String>();
                    params.put("model","1");
                    params.put("pageNumber","100");
                    params.put("pages","1");
                    String result= PostUtil.sendPostMessage(uri, params);
                    Log.i("tag", "User" + result);
                    JSONObject jsonObject=new JSONObject(result);
                    JSONArray data=jsonObject.getJSONArray("data");
                    for (int i=0;i<data.length();i++){
                        JSONObject object=data.getJSONObject(i);
                        SearchUserEntity entity=new SearchUserEntity();
                        entity.setName(object.getString("nickName"));
                        entity.setId(object.getInt("id"));
                        entity.setPhotoUrl(object.getString("avatar"));
                        entity.setAuthenticate(object.getInt("authenticate"));
                        String authTemp= StringUtil.isNullAuth(object.getString("auth"));
                        if (!authTemp.equals("")){
                            JSONObject auth=  new JSONObject(authTemp);
                            entity.setType(auth.getString("authinfo"));
                        }
                        entities.add(entity);
                    }
                    handler.sendEmptyMessage(HANDLE_DATA);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }.start();
    }
}
