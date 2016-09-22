package com.gymnast.view.live.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import com.gymnast.R;
import com.gymnast.view.ImmersiveActivity;
import com.gymnast.view.home.HomeActivity;
import com.hyphenate.chat.EMClient;

import java.util.Random;
import de.hdodenhof.circleimageview.CircleImageView;

public class CloseLiveActivity extends ImmersiveActivity {
    FrameLayout rlMain;
    CircleImageView civPhoto;
    TextView tvMainName,tvTotalTime,tvPeopleNumber,tvShareNumber,tvPriseNumber,tvMoney;
    ImageView ivClose;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_close_live);
        Intent intent=getIntent();
        rlMain= (FrameLayout) findViewById(R.id.rlMain);
        rlMain.getBackground().setAlpha(50);
        civPhoto= (CircleImageView) findViewById(R.id.civPhoto);
        tvMainName= (TextView) findViewById(R.id.tvMainName);
        tvTotalTime= (TextView) findViewById(R.id.tvTotalTime);
        tvPeopleNumber= (TextView) findViewById(R.id.tvPeopleNumber);
        tvShareNumber= (TextView) findViewById(R.id.tvShareNumber);
        tvPriseNumber= (TextView) findViewById(R.id.tvPriseNumber);
        tvMoney= (TextView) findViewById(R.id.tvMoney);
        ivClose= (ImageView) findViewById(R.id.ivClose);
        String totalTime=intent.getStringExtra("totalTime");
        String nickName=intent.getStringExtra("nickName");
        int peopleNumber=intent.getIntExtra("peopleNumber", 0);
        int shareNumber=intent.getIntExtra("shareNumber",0);
        int priseNumber=intent.getIntExtra("priseNumber",0);
        Bitmap bitmapSmallPhoto=intent.getParcelableExtra("bitmapSmallPhoto");
        civPhoto.setImageBitmap(bitmapSmallPhoto);
        tvMainName.setText(nickName);
        tvTotalTime.setText(totalTime);
        tvPeopleNumber.setText(peopleNumber + "");
        tvShareNumber.setText(shareNumber + "");
        tvPriseNumber.setText(priseNumber + "");
        tvMoney.setText("直播收益："+new Random().nextInt(100)+"金币");
        ivClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CloseLiveActivity.this, HomeActivity.class));
                finish();
            }
        });
       final String groupId=intent.getStringExtra("groupID");
       new Thread(){
            @Override
            public void run() {
                try{
                    EMClient.getInstance().groupManager().destroyGroup(groupId);//需异步处理
                    Log.i("tag", "send_end_ok---" + groupId);
                }catch (Exception e){
                    e.printStackTrace();
                    Log.i("tag", "send_end_error---" + groupId);
                    Log.i("tag","1"+e.toString());
                }
            }
        }.start();
    }
}
