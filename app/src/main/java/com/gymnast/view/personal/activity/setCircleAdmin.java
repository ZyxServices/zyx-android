package com.gymnast.view.personal.activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.gymnast.R;
import com.gymnast.data.personal.CircleMainData;
import com.gymnast.view.ImmersiveActivity;
import com.gymnast.view.personal.adapter.CircleMainAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Cymbi on 2016/9/22.
 */
public class setCircleAdmin extends ImmersiveActivity {
    private RecyclerView recyclerview;
    private String id,token,master_id;
    private int circleMasterId;
    private String adminIds;
    private int CircleId;
    private List<CircleMainData> list=new ArrayList<>();
    public static final int HANDLER_DATA=1;

    Handler handler =new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case HANDLER_DATA:
                   /* adapter= new CircleMainAdapter(setCircleAdmin.this,list);
                    recyclerview.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                    break;*/
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setcircle_main);
        getInfo();
        setView();
        setData();
    }

    private void getInfo() {
        SharedPreferences share = getSharedPreferences("UserInfo", Context.MODE_PRIVATE);
        id = share.getString("UserId", "");
        token = share.getString("Token", "");
        adminIds=getIntent().getStringExtra("adminIds");
        CircleId=getIntent().getIntExtra("CircleId",0);
        circleMasterId=getIntent().getIntExtra("circleMasterId",0);
    }

    private void setView() {
        ImageView back= (ImageView) findViewById(R.id.ivSettingBack);
        TextView tvTitle=(TextView)findViewById(R.id.tvTitle);
        tvTitle.setText("设置管理员");
        recyclerview= (RecyclerView) findViewById(R.id.recyclerview);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void setData() {

    }
}
