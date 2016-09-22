package com.gymnast.data.personal;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Cymbi on 2016/8/27.
 */
public class DynamicData implements Serializable {
    //动态id
    public int id;
    //创建时间
    public Long createTime;
    //1为个人动态，2为活动动态，3为明星动态，4为圈子动态
    public int type;
    //动态图片
    public String topicTitle;
    //内容
    public String topicContent;
    //图片地址
    public ArrayList<String> imgUrl;
    //视频直播
    public String videoUrl;
    //可见范围0所有可见，1好友可见
    public int topicVisible;
    //
    public int pageviews;//多少人浏览
    public int getPageviews() {
        return pageviews;
    }
    public void setPageviews(int pageviews) {
        this.pageviews = pageviews;
    }
    public int fromId;
    //动态发起人的id
    public int fromType;
    //动态状态，-2未屏蔽，-1为删除,0为正常
    public int state;
    //用户姓名
    public String nickName;
    //用户头像
    public String avatar;
    //认证
    public int authenticate;
    //赞
    public int zanCounts;
    //回复
    public int commentCounts;
    //用户类型
    public String authInfo;
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getAuthInfo() {
        return authInfo;
    }
    public void setAuthInfo(String authInfo) {
        this.authInfo = authInfo;
    }
    public Long getCreateTime() {
        return createTime;
    }
    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }
    public int getType() {
        return type;
    }
    public void setType(int type) {
        this.type = type;
    }
    public String getTopicTitle() {
        return topicTitle;
    }
    public void setTopicTitle(String topicTitle) {
        this.topicTitle = topicTitle;
    }
    public String getTopicContent() {
        return topicContent;
    }
    public void setTopicContent(String topicContent) {
        this.topicContent = topicContent;
    }
    public ArrayList<String> getImgUrl() {
        return imgUrl;
    }
    public void setImgUrl(ArrayList<String> imgUrl) {
        this.imgUrl = imgUrl;
    }
    public String getVideoUrl() {
        return videoUrl;
    }
    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }
    public int getTopicVisible() {
        return topicVisible;
    }
    public void setTopicVisible(int topicVisible) {
        this.topicVisible = topicVisible;
    }
    public int getFromId() {
        return fromId;
    }
    public void setFromId(int fromId) {
        this.fromId = fromId;
    }
    public int getFromType() {
        return fromType;
    }
    public void setFromType(int fromType) {
        this.fromType = fromType;
    }
    public int getState() {
        return state;
    }
    public void setState(int state) {
        this.state = state;
    }
    public String getNickName() {
        return nickName;
    }
    public void setNickName(String nickName) {
        this.nickName = nickName;
    }
    public String getAvatar() {
        return avatar;
    }
    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
    public int getAuthenticate() {
        return authenticate;
    }
    public void setAuthenticate(int authenticate) {
        this.authenticate = authenticate;
    }
    public int getZanCounts() {
        return zanCounts;
    }
    public void setZanCounts(int zanCounts) {
        this.zanCounts = zanCounts;
    }
    public int getCommentCounts() {
        return commentCounts;
    }
    public void setCommentCounts(int commentCounts) {
        this.commentCounts = commentCounts;
    }
}
