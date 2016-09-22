package com.gymnast.view.personal.activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.gymnast.R;
import com.gymnast.data.net.API;
import com.gymnast.utils.PostUtil;
import com.gymnast.view.ImmersiveActivity;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.HashMap;
import java.util.Map;
/**
 * Created by Cymbi on 2016/8/16.
 */
public class PersonalSignatureActivity extends ImmersiveActivity {
    private EditText mSignature;
    private String token;
    private String id;
    private TextView save;
    private ImageView back;
    private String signature;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_signature);
        SharedPreferences share = getSharedPreferences("UserInfo",MODE_PRIVATE);
        token= share.getString("Token","");
        id= share.getString("UserId","");
        setView();
        setListeners();
    }
    private void setListeners() {
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setData();
            }


        });
    }
    private void setData() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String uri= API.BASE_URL+"/v1/account/info/edit";
                    Map<String, String> params=new HashMap<>();
                    params.put("token",token);
                    params.put("account_id",id);
                    signature = mSignature.getText().toString();
                    params.put("signature",signature);
                    String result= PostUtil.sendPostMessage(uri,params);
                    JSONObject json =  new JSONObject(result);
                    if(json.getInt("state")==200){
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                finish();
                                Toast.makeText(PersonalSignatureActivity.this,"签名修改成功",Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                    if(json.getInt("state")==301){
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(PersonalSignatureActivity.this,"请填写签名~",Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
    private void setView() {
       mSignature=(EditText) findViewById(R.id.et_activity_signature);
        save=(TextView) findViewById(R.id.save);
        back=(ImageView) findViewById(R.id.personal_back);
    }
}
