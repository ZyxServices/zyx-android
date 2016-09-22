package com.gymnast.data.hotinfo;

import com.gymnast.utils.StringUtil;
import java.io.Serializable;

/**
 * Created by fldyown on 16/7/4.
 */
public class CirleDevas implements Serializable{
  public int id;
  public long createTime;
  public int circleId;
  public int createId;
  public String title;
  public String content;
  public String hot;
  public String top;
  public int state;
  public String imgUrl;
  public String nickname;
  public String avatar;
  public int zanCount;
  public int meetCount;
  public String circleTitle;
  public String authInfo;
  public String getHeadImgUrl() {
    return  StringUtil.isNullAvatar(avatar);
  }
}
