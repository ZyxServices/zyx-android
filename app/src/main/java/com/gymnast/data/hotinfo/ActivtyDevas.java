package com.gymnast.data.hotinfo;

import com.gymnast.data.net.API;
/**
 * Created by fldyown on 16/7/1.
 */
public class ActivtyDevas {
  public int id;
  public long createTime;
  public int userId;
  public String title;
  public String imgUrls;
  public int visible;
  public String phone;
  public String descContent;
  public long startTime;
  public long endTime;
  public long lastTime;
  public int maxPeople;
  public double price;
  public int type;
  public String targetUrl;
  public String address;
  public int activityType;
  public int examine;
  public String memberTemplate;
  public int mask;
  public String getImgUrls() {
    return API.IMAGE_URL + imgUrls;
  }
}
