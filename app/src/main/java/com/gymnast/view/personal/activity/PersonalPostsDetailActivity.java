package com.gymnast.view.personal.activity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.gymnast.R;
import com.gymnast.data.hotinfo.CirleDevas;
import com.gymnast.data.personal.PostsData;
import com.gymnast.utils.PicUtil;
import com.gymnast.utils.PicassoUtil;
import com.gymnast.utils.StringUtil;
import com.gymnast.view.ImmersiveActivity;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Cymbi on 2016/9/2.
 */
public class PersonalPostsDetailActivity extends ImmersiveActivity implements View.OnClickListener{
    private SimpleDateFormat sdr;
    private ImageView circle_head,back,personal_menu,ivClose;
    private TextView nickName;
    private TextView title,time,attention,tvCollect,tvReport,tvDelete, tvSpacial,tvTop;
    private WebView webView;
    private PostsData mPostsData;
    private Dialog cameradialog;
    LinearLayout llShareToFriends,llShareToWeiChat,llShareToQQ,llShareToQQZone,llShareToMicroBlog;
    public static int shareNumber=0;//分享次数
    View view;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        setContentView(R.layout.activity_posts_details);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        setView();
        getData();
        initView();
        setListeners();
    }
    private void setListeners() {
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
    private void initView() {
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        personal_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showdialog();
            }
        });
    }
    private void getData() {
        CirleDevas cirleDevas= (CirleDevas) getIntent().getSerializableExtra("CirleDevas");
        Intent data= getIntent();
        sdr = new SimpleDateFormat("yyyy-MM-dd");
        mPostsData = (PostsData)data.getSerializableExtra("item");
        if(cirleDevas==null){
        PicassoUtil.handlePic(PersonalPostsDetailActivity.this, PicUtil.getImageUrlDetail(PersonalPostsDetailActivity.this, StringUtil.isNullAvatar(mPostsData.getAvatar()), 58, 58),circle_head,58,58);
        nickName.setText(mPostsData.getNickname());
        time.setText(sdr.format(new Date(mPostsData.getCreateTime()))+"");
        title.setText(mPostsData.getTitle());
        WindowManager wm = (WindowManager) PersonalPostsDetailActivity.this.getSystemService(Context.WINDOW_SERVICE);
        int width = wm.getDefaultDisplay().getWidth();
        if(width > 520){
            this.webView.setInitialScale(160);
        }else if(width > 450){
            this.webView.setInitialScale(140);
        }else if(width > 300){
            this.webView.setInitialScale(120);
        }else{
            this.webView.setInitialScale(100);
        }
        webView.loadDataWithBaseURL(null, mPostsData.getContent() , "text/html", "utf-8", null);
        webView.getSettings().setJavaScriptEnabled(true);
        // 设置启动缓存 ;
        webView.getSettings().setAppCacheEnabled(true);
        webView.setWebChromeClient(new WebChromeClient());
        webView.getSettings().setJavaScriptEnabled(true);
    }else {
            PicassoUtil.handlePic(PersonalPostsDetailActivity.this, PicUtil.getImageUrlDetail(PersonalPostsDetailActivity.this, StringUtil.isNullAvatar(cirleDevas.avatar), 58, 58),circle_head,58,58);
            nickName.setText(cirleDevas.nickname);
            time.setText(sdr.format(new Date(cirleDevas.createTime))+"");
            title.setText(cirleDevas.title);
            WindowManager wm = (WindowManager) PersonalPostsDetailActivity.this.getSystemService(Context.WINDOW_SERVICE);
            int width = wm.getDefaultDisplay().getWidth();
            if(width > 520){
                this.webView.setInitialScale(160);
            }else if(width > 450){
                this.webView.setInitialScale(140);
            }else if(width > 300){
                this.webView.setInitialScale(120);
            }else{
                this.webView.setInitialScale(100);
            }
            webView.loadDataWithBaseURL(null, cirleDevas.content , "text/html", "utf-8", null);
            webView.getSettings().setJavaScriptEnabled(true);
            // 设置启动缓存 ;
            webView.getSettings().setAppCacheEnabled(true);
            webView.setWebChromeClient(new WebChromeClient());
            webView.getSettings().setJavaScriptEnabled(true);
        }
    }
    private void setView() {
        back= (ImageView) findViewById(R.id.personal_back);
        circle_head=(ImageView) findViewById(R.id.circle_head);
        personal_menu=(ImageView) findViewById(R.id.personal_menu);
        nickName=(TextView) findViewById(R.id.nickname);
        attention=(TextView) findViewById(R.id.attention);
        time=(TextView) findViewById(R.id.time);
        title = (TextView) findViewById(R.id.Title);
        webView=(WebView)findViewById(R.id.webview);
        webView.setHorizontalScrollBarEnabled(false);//水平不显示
        webView.setVerticalScrollBarEnabled(false); //垂直不显示
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
        ivClose= (ImageView) view.findViewById(R.id.ivClose);
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
    private void showdialog() {
        cameradialog = new Dialog(this,R.style.Dialog_Fullscreen);
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
    @Override
    public void onClick(View view) {
        switch (view.getId()){
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
}
