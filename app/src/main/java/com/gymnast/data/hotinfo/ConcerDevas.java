package com.gymnast.data.hotinfo;

import com.gymnast.data.net.API;
import java.io.Serializable;

/**
 * Created by fldyown on 16/7/4.
 */
public class ConcerDevas implements Serializable{
  public int id;
  public long createTime;
  public int user_id;
  public int type;
  public String topic_title;
  public String topic_content;
  public String imgUrl;
  public String video_url;
  public String topic_visible;
  public String getImgUrl() {
    return API.IMAGE_URL + imgUrl;
  }
}
