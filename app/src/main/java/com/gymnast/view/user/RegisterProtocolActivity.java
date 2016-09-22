package com.gymnast.view.user;

import android.os.Bundle;
import android.webkit.WebView;
import butterknife.BindView;
import com.gymnast.R;
import com.gymnast.view.BaseToolbarActivity;
public class RegisterProtocolActivity extends BaseToolbarActivity {
  @BindView(R.id.register_protocol) WebView registerProtocol;
  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
  }
  @Override protected int getLayout() {
    return R.layout.activity_register_protocol;
  }
  @Override protected void initViews(Bundle savedInstanceState) {
  }
  @Override protected void initListeners() {
  }
  @Override protected void initData() {
    registerProtocol.loadUrl("file:///android_asset/protocol.html");
  }
}
