package com.gymnast.view.user;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;
import butterknife.BindView;
import com.gymnast.R;
import com.gymnast.data.net.Result;
import com.gymnast.data.user.UserService;
import com.gymnast.data.user.UserServiceImpl;
import com.gymnast.data.user.VerifyCode;
import com.gymnast.view.BaseToolbarActivity;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class ForgetPasswordActivity extends BaseToolbarActivity {
  UserService userService = new UserServiceImpl();
  //verify
  @BindView(R.id.forget_password_phone) EditText forgetPasswordPhone;
  @BindView(R.id.forget_password_get_verify_code) Button forgetPasswordGetVerifyCode;
  @BindView(R.id.forget_password_verify_code) EditText forgetPasswordVerifyCode;
  @BindView(R.id.forget_password_next_btn) Button forgetPasswordNextBtn;
  @BindView(R.id.forget_password_retrieve) EditText forgetPasswordRetrieve;
  @BindView(R.id.forget_password_retrieve2) EditText forgetPasswordRetrieve2;
  @BindView(R.id.forget_password_checkbox) CheckBox forgetPasswordCheckbox;
  int time = 60;
  private static String phone;
  private static String verifyCode;
  private static String password;
  private static String password2;
  Handler handler = new Handler() {
    @Override public void handleMessage(Message msg) {
      super.handleMessage(msg);
      switch (msg.what) {
        case 1:
          if (time >= 0) {
            forgetPasswordGetVerifyCode.setText(time + " 秒后重试");
            time--;
            sendEmptyMessageDelayed(1, 1000);
          } else {
            removeMessages(1);
            time = 60;
            forgetPasswordGetVerifyCode.setClickable(true);
            forgetPasswordGetVerifyCode.setText(R.string.register_get_code);
          }
          break;
        case 2:
          retrievePassword();
          break;
      }
    }
  };
  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
  }
  @Override protected int getLayout() {
    return R.layout.activity_forget_password;
  }
  @Override protected void initViews(Bundle savedInstanceState) {
  }
  @Override protected void initListeners() {
    forgetPasswordGetVerifyCode.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        forgetPasswordGetVerifyCode.setClickable(false);
        phone = forgetPasswordPhone.getText().toString();
        if (phone != null && !"".equals(phone)) {
          Subscription s = userService.getVerifyCode(phone).subscribeOn(Schedulers.io())//
              .observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<Result>() {
                @Override public void onCompleted() {
                  Log.i("fldy", "===>:onCompleted");
                }
                @Override public void onError(Throwable e) {
                  Toast.makeText(ForgetPasswordActivity.this, "获取验证码失败：" + e.getMessage(),
                      Toast.LENGTH_SHORT).show();
                }
                @Override public void onNext(Result result) {
                  if (result.state == 200) {
                    Toast.makeText(ForgetPasswordActivity.this, "验证码已发送", Toast.LENGTH_SHORT)
                        .show();
                  }
                }
              });
          mCompositeSubscription.add(s);
          handler.sendEmptyMessage(1);
        } else {
          Toast.makeText(ForgetPasswordActivity.this, "手机号不能为空", Toast.LENGTH_SHORT).show();
        }
      }
    });
    forgetPasswordNextBtn.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        attempt();
      }
    });
    forgetPasswordCheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
      @Override public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (isChecked) {
          forgetPasswordRetrieve.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
          forgetPasswordRetrieve2.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
        } else {
          forgetPasswordRetrieve.setTransformationMethod(PasswordTransformationMethod.getInstance());
          forgetPasswordRetrieve2.setTransformationMethod(PasswordTransformationMethod.getInstance());
        }
      }
    });
  }
  private void attempt() {
    forgetPasswordVerifyCode.setError(null);
    forgetPasswordRetrieve.setError(null);
    forgetPasswordRetrieve2.setError(null);
    verifyCode = forgetPasswordVerifyCode.getText().toString();
    password = forgetPasswordRetrieve.getText().toString();
    password2 = forgetPasswordRetrieve2.getText().toString();
    if (verifyCode == null || "".equals(verifyCode)) {
      forgetPasswordVerifyCode.setError("请输入验证码");
      forgetPasswordVerifyCode.requestFocus();
      return;
    }
    if (password == null || "".equals(password)) {
      forgetPasswordVerifyCode.setError("请输入新密码");
      forgetPasswordVerifyCode.requestFocus();
      return;
    }
    if (!password.equals(password2)) {
      forgetPasswordVerifyCode.setError("两次密码不一致");
      forgetPasswordVerifyCode.requestFocus();
      return;
    }
    Subscription s = userService.verifyPhone(phone, verifyCode).subscribeOn(Schedulers.io())//
        .observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<Result<VerifyCode>>() {
          @Override public void onCompleted() {
            Log.i("fldy", "===>:onCompleted");
          }
          @Override public void onError(Throwable e) {
            Toast.makeText(ForgetPasswordActivity.this, "网络错误请稍后再试" + e.getMessage(),
                Toast.LENGTH_SHORT).show();
          }
          @Override public void onNext(Result<VerifyCode> result) {
            if (result.state == 200) {
              handler.sendEmptyMessage(2);
            } else {
              forgetPasswordVerifyCode.setError("验证码不匹配");
              forgetPasswordVerifyCode.requestFocus();
            }
          }
        });
    mCompositeSubscription.add(s);
  }
  private void retrievePassword() {
    Subscription s =
        userService.retrievePassword(phone, password, password2).subscribeOn(Schedulers.io())//
            .observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<Result>() {
          @Override public void onCompleted() {
            Log.i("fldy", "===>:onCompleted");
          }
          @Override public void onError(Throwable e) {
            Toast.makeText(ForgetPasswordActivity.this, "网络错误请稍后再试" + e.getMessage(),
                Toast.LENGTH_SHORT).show();
          }
          @Override public void onNext(Result result) {
            if (result.state == 200) {
              Toast.makeText(ForgetPasswordActivity.this, "密码已重置，请重新登录", Toast.LENGTH_SHORT).show();
              finish();
            } else {
              forgetPasswordVerifyCode.setError("验证码不匹配");
              forgetPasswordVerifyCode.requestFocus();
            }
          }
        });
    mCompositeSubscription.add(s);
  }
  @Override protected void initData() {
  }
}
