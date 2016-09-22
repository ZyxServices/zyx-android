package com.gymnast.data.personal;

import java.io.Serializable;
/**
 * 我创建的圈子列表
 * Created by Cymbi on 2016/8/25.
 */
public class CircleData implements Serializable {
    //标题,名称
    public String title;
    //类型
    public String details;
    //圈子头像
    public String headImgUrl;
    //圈子数量
    public int circleItemCount;
    //圈子id
    public int id;
    boolean concerned;
    public int ismeet;

    public int getIsmeet() {
        return ismeet;
    }

    public void setIsmeet(int ismeet) {
        this.ismeet = ismeet;
    }

    public boolean isConcerned() {
        return concerned;
    }
    public void setConcerned(boolean concerned) {
        this.concerned = concerned;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getDetails() {
        return details;
    }
    public void setDetails(String details) {
        this.details = details;
    }
    public String getHeadImgUrl() {
        return headImgUrl;
    }
    public void setHeadImgUrl(String headImgUrl) {
        this.headImgUrl = headImgUrl;
    }
    public int getCircleItemCount() {
        return circleItemCount;
    }
    public void setCircleItemCount(int circleItemCount) {
        this.circleItemCount = circleItemCount;
    }
}
