package com.gymnast.data.personal;

import java.io.Serializable;

/**
 * Created by Cymbi on 2016/9/1.
 */
public class PostsData implements Serializable {
    public String avatar;
    public String nickname;
    public String title;
    public String content;
    public String imgUrl;
    public int id;
    //来自哪个圈子
    public String circleTitle;
    //圈子id
    public int circleId;
    //创建者id
    public int createId;
    //帖子状态,0为正常可以访问，-1为删除，-2为屏蔽如有业务需求，可以再加入
    public int state;
    public int zanCount;
    //回复数量
    public int meetCount;
    public int msgCount;
    //浏览量
    public int pageviews;

    public int getPageviews() {
        return pageviews;
    }

    public void setPageviews(int pageviews) {
        this.pageviews = pageviews;
    }

    public int getMsgCount() {
        return msgCount;
    }
    public void setMsgCount(int msgCount) {
        this.msgCount = msgCount;
    }
    public long createTime;
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public long getCreateTime() {
        return createTime;
    }
    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }
    public String getAvatar() {
        return avatar;
    }
    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
    public String getNickname() {
        return nickname;
    }
    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }
    public String getImgUrl() {
        return imgUrl;
    }
    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }
    public String getCircleTitle() {
        return circleTitle;
    }
    public void setCircleTitle(String circleTitle) {
        this.circleTitle = circleTitle;
    }
    public int getCircleId() {
        return circleId;
    }
    public void setCircleId(int circleId) {
        this.circleId = circleId;
    }
    public int getCreateId() {
        return createId;
    }
    public void setCreateId(int createId) {
        this.createId = createId;
    }
    public int getState() {
        return state;
    }
    public void setState(int state) {
        this.state = state;
    }
    public int getZanCount() {
        return zanCount;
    }
    public void setZanCount(int zanCount) {
        this.zanCount = zanCount;
    }
    public int getMeetCount() {
        return meetCount;
    }
    public void setMeetCount(int meetCount) {
        this.meetCount = meetCount;
    }
}
