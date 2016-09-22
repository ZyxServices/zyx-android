package com.gymnast.data.personal;

import java.io.Serializable;

/**
 * Created by Cymbi on 2016/9/21.
 */
public class CircleMainData implements Serializable {
    public String avatar;
    public String nickname;
    public int userId;
    public int circleId;
    public Integer circleMasterId;
    public String adminIds;

    public Integer getCircleMasterId() {
        return circleMasterId;
    }

    public void setCircleMasterId(Integer circleMasterId) {
        this.circleMasterId = circleMasterId;
    }

    public String getAdminIds() {
        return adminIds;
    }

    public void setAdminIds(String adminIds) {
        this.adminIds = adminIds;
    }

    public int getCircleId() {
        return circleId;
    }

    public void setCircleId(int circleId) {
        this.circleId = circleId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
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
}
