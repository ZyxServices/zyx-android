package com.gymnast.view.personal.activity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.gymnast.R;
import com.gymnast.data.hotinfo.ConcerDevas;
import com.gymnast.data.net.API;
import com.gymnast.data.personal.DynamicData;
import com.gymnast.utils.GetUtil;
import com.gymnast.utils.PicUtil;
import com.gymnast.utils.PicassoUtil;
import com.gymnast.utils.StringUtil;
import com.gymnast.view.ImmersiveActivity;
import com.gymnast.view.personal.adapter.GridAdapter;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

/**
 * Created by Cymbi on 2016/9/2.
 */
public class PersonalDynamicDetailActivity extends ImmersiveActivity implements View.OnClickListener {


    private ImageView mPersonal_menu,personal_back,mDynamic_head,ivClose;
    private TextView mDynamic_name,mDynamic_time,mDynamic_Title,mDynamic_context,star_type,tvCollect,tvReport,tvDelete, tvSpacial,tvTop;
    private GridView mGridview;
    private Dialog cameradialog;
    private DynamicData mDynamicData;
    LinearLayout llShareToFriends,llShareToWeiChat,llShareToQQ,llShareToQQZone,llShareToMicroBlog;
    private int id;
    public static int shareNumber=0;//分享次数
    private SimpleDateFormat sdf =new SimpleDateFormat("MM月-dd日 HH:mm");
    View view;
    int userID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dynamic_details);
        settview();
        setdata();
    }
    private void settview() {
        mPersonal_menu=(ImageView)findViewById(R.id.personal_menu);
        personal_back=(ImageView)findViewById(R.id.personal_back);
        mDynamic_head=(ImageView)findViewById(R.id.dynamic_head);
        mDynamic_name=(TextView)findViewById(R.id.dynamic_name);
        mDynamic_time=(TextView)findViewById(R.id.dynamic_time);
        mDynamic_Title=(TextView)findViewById(R.id.dynamic_Title);
        mDynamic_context=(TextView)findViewById(R.id.dynamic_context);
        star_type=(TextView)findViewById(R.id.star_type);
        mGridview=(GridView)findViewById(R.id.gridview);
        view = getLayoutInflater().inflate(R.layout.share_dialog, null);
        llShareToFriends= (LinearLayout) view.findViewById(R.id.llShareToFriends);
        llShareToWeiChat= (LinearLayout) view.findViewById(R.id.llShareToWeiChat);
        llShareToQQ= (LinearLayout) view.findViewById(R.id.llShareToQQ);
        llShareToQQZone= (LinearLayout) view.findViewById(R.id.llShareToQQZone);
        llShareToMicroBlog= (LinearLayout) view.findViewById(R.id.llShareToMicroBlog);
        tvCollect= (TextView) view.findViewById(R.id.tvCollect);
        tvReport= (TextView) view.findViewById(R.id.tvReport);
        tvDelete= (TextView) view.findViewById(R.id.tvDelete);
        tvTop= (TextView) view.findViewById(R.id.tvTop);
        tvSpacial= (TextView) view.findViewById(R.id.tvSpacial);
        cameradialog = new Dialog(this,R.style.Dialog_Fullscreen);
        ivClose=(ImageView) view.findViewById(R.id.ivClose);
        mPersonal_menu.setOnClickListener(this);
        personal_back.setOnClickListener(this);
        mDynamic_head.setOnClickListener(this);
        mDynamic_name.setOnClickListener(this);
        mDynamic_time.setOnClickListener(this);
        mDynamic_Title.setOnClickListener(this);
        llShareToFriends.setOnClickListener(this);
        llShareToWeiChat.setOnClickListener(this);
        llShareToQQ.setOnClickListener(this);
        llShareToQQZone.setOnClickListener(this);
        llShareToMicroBlog.setOnClickListener(this);
        tvCollect.setOnClickListener(this);
        tvReport.setOnClickListener(this);
        tvDelete.setOnClickListener(this);
        tvSpacial.setOnClickListener(this);
        tvTop.setOnClickListener(this);
        ivClose.setOnClickListener(this);
    }
    private void setdata() {
        ConcerDevas dynamic = (ConcerDevas) getIntent().getSerializableExtra("ConcerDevas");
        if (dynamic == null) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    Intent datas = getIntent();
                    mDynamicData = (DynamicData) datas.getSerializableExtra("item");
                    id = mDynamicData.getId();
                    String uri = API.BASE_URL + "/v1/concern/getOne";
                    HashMap<String, String> params = new HashMap<String, String>();
                    params.put("concernId", id + "");
                    String result = GetUtil.sendGetMessage(uri, params);
                    try {
                        JSONObject object = new JSONObject(result);
                        if (object.getInt("state") == 200) {
                            final ArrayList<String> imageURL = new ArrayList<String>();
                            JSONObject data = object.getJSONObject("data");
                            JSONObject concern = data.getJSONObject("concern");
                            userID=concern.getInt("userId");
                            JSONObject userVo = concern.getJSONObject("userVo");
                            String isCollection = data.getString("isCollection");
                            final long createTime = concern.getLong("createTime");
                            final String topicTitle = concern.getString("topicTitle");
                            final String topicContent = concern.getString("topicContent");
                            final String nickName = userVo.getString("nickName");
                            String zanCounts = concern.getString("zanCounts");
                            String commentCounts = concern.getString("commentCounts");
                            String urls = concern.getString("imgUrl");
                            final String avatar = userVo.getString("avatar");
                            if (urls == null | urls.equals("null") | urls.equals("")) {

                            } else {
                                String[] imageUrls = urls.split(",");
                                for (int j = 0; j < imageUrls.length; j++) {
                                    imageURL.add(API.IMAGE_URL + imageUrls[j]);
                                }
                            }
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    PicassoUtil.handlePic(PersonalDynamicDetailActivity.this, PicUtil.getImageUrlDetail(PersonalDynamicDetailActivity.this, StringUtil.isNullAvatar(avatar), 320, 320), mDynamic_head, 320, 320);
                                    mDynamic_name.setText(nickName);
                                    mDynamic_time.setText(sdf.format(new Date(createTime)) + "");
                                 //   mDynamic_Title.setText(topicTitle);
                                    mDynamic_context.setText(topicContent);
                                    GridAdapter adapter = new GridAdapter(PersonalDynamicDetailActivity.this, imageURL);
                                    mGridview.setAdapter(adapter);
                                    adapter.notifyDataSetChanged();
                                }
                            });
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        } else {
            final int id = dynamic.id;
            new Thread(new Runnable() {
                @Override
                public void run() {
                    String uri = API.BASE_URL + "/v1/concern/getOne";
                    HashMap<String, String> params = new HashMap<String, String>();
                    params.put("concernId", id + "");
                    String result = GetUtil.sendGetMessage(uri, params);
                    try {
                        JSONObject object = new JSONObject(result);
                        if (object.getInt("state") == 200) {
                            final ArrayList<String> imageURL = new ArrayList<String>();
                            JSONObject data = object.getJSONObject("data");
                            JSONObject concern = data.getJSONObject("concern");
                            userID=concern.getInt("userId");
                            JSONObject userVo = concern.getJSONObject("userVo");
                            //  JSONObject userAuthVo= concern.getJSONObject("userAuthVo");
                            // String authInfo= userAuthVo.getString("authInfo");
                            String isCollection = data.getString("isCollection");
                            final long createTime = concern.getLong("createTime");
                            final String topicTitle = concern.getString("topicTitle");
                            final String topicContent = concern.getString("topicContent");
                            final String nickName = userVo.getString("nickName");
                            String zanCounts = concern.getString("zanCounts");
                            String commentCounts = concern.getString("commentCounts");
                            String urls = concern.getString("imgUrl");
                            final String avatar = userVo.getString("avatar");
                            if (urls == null | urls.equals("null") | urls.equals("")) {

                            } else {
                                String[] imageUrls = urls.split(",");
                                for (int j = 0; j < imageUrls.length; j++) {
                                    imageURL.add(API.IMAGE_URL + imageUrls[j]);
                                }
                            }
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    PicassoUtil.handlePic(PersonalDynamicDetailActivity.this, PicUtil.getImageUrlDetail(PersonalDynamicDetailActivity.this, StringUtil.isNullAvatar(avatar), 320, 320), mDynamic_head, 320, 320);
                                    mDynamic_name.setText(nickName);
                                    mDynamic_time.setText(sdf.format(new Date(createTime)) + "");
                                 //   mDynamic_Title.setText(topicTitle);
                                    mDynamic_context.setText(topicContent);
                                    GridAdapter adapter = new GridAdapter(PersonalDynamicDetailActivity.this, imageURL);
                                    mGridview.setAdapter(adapter);
                                    adapter.notifyDataSetChanged();
                                }
                            });
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }
    }
                     @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.personal_back:
                finish();
                break;
            case R.id.personal_menu:
                showdialog();
                break;
            case R.id.dynamic_head:
                Intent intent=new Intent(PersonalDynamicDetailActivity.this,PersonalOtherHomeActivity.class);
                intent.putExtra("UserID",userID);
                PersonalDynamicDetailActivity.this.startActivity(intent);
                break;
            case R.id.llShareToFriends:
                shareToFriends();
                break;
            case R.id.llShareToWeiChat:
                shareToWeiChat();
                break;
            case R.id.llShareToQQ:
                shareToQQ();
                break;
            case R.id.llShareToQQZone:
                shareToQQZone();
                break;
            case R.id.llShareToMicroBlog:
                shareToMicroBlog();
                break;
            case R.id.tvCollect:
                collect();
                break;
            case R.id.tvReport:
                report();
                break;
            case R.id.tvDelete:
                delete();
                break;
            case R.id.tvSpacial:
                spacial();
                break;
            case R.id.tvTop:
                toTop();
                break;
            case R.id.ivClose:
                cameradialog.dismiss();
                break;
        }
    }
    private void showdialog() {
        cameradialog.setContentView(view, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
        Window window = cameradialog.getWindow();
        // 设置显示动画
        window.setWindowAnimations(R.style.main_menu_animstyle);
        WindowManager.LayoutParams wl = window.getAttributes();
        wl.x = 0;
        wl.y = getWindowManager().getDefaultDisplay().getHeight();
        // 以下这两句是为了保证按钮可以水平满屏
        wl.width = ViewGroup.LayoutParams.MATCH_PARENT;
        wl.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        // 设置显示位置
        cameradialog.onWindowAttributesChanged(wl);
        // 设置点击外围解散
        cameradialog.setCanceledOnTouchOutside(true);
           cameradialog.show();

    }
    private void shareToFriends() {
        Toast.makeText(this, "分享到朋友圈！", Toast.LENGTH_SHORT).show();
        shareNumber++;
    }
    private void shareToWeiChat() {
        Toast.makeText(this,"分享到微信！",Toast.LENGTH_SHORT).show();
        shareNumber++;
    }
    private void shareToQQ() {
        Toast.makeText(this,"分享到QQ！",Toast.LENGTH_SHORT).show();
        shareNumber++;
    }
    private void shareToQQZone() {
        Toast.makeText(this,"分享到QQ空间！",Toast.LENGTH_SHORT).show();
        shareNumber++;
    }
    private void shareToMicroBlog() {
        Toast.makeText(this,"分享到微博！",Toast.LENGTH_SHORT).show();
        shareNumber++;
    }
    private void collect() {
        Toast.makeText(this,"已收藏！",Toast.LENGTH_SHORT).show();
    }
    private void report() {
        Toast.makeText(this,"已举报！",Toast.LENGTH_SHORT).show();
    }
    private void delete() {
        Toast.makeText(this,"已删除！",Toast.LENGTH_SHORT).show();
    }
    private void spacial() {
        Toast.makeText(this,"已加精！",Toast.LENGTH_SHORT).show();
    }
    private void toTop() {
        Toast.makeText(this,"已置顶！",Toast.LENGTH_SHORT).show();
    }
}
