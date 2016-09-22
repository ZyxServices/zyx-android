package com.gymnast.data.hotinfo;

import com.gymnast.data.net.API;
import java.io.Serializable;

/**
 * Created by fldyown on 16/7/1.
 */
public class UserDevas implements Serializable {
  public boolean ifSort;
  public String sortProperty;
  public boolean asc;
  public String pageNo;
  public String pageSize;
  public String count;
  public String start;
  public String end;
  public int id;
  public String phone;
  public String password;
  public String nickname;
  public String sex;
  public String avatar;
  public String token;
  public String getAvatar() {
    return API.IMAGE_URL + avatar;
  }
}