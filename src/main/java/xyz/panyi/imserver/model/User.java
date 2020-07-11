package xyz.panyi.imserver.model;

import java.util.ArrayList;
import java.util.List;

/**
 *  用户信息
 */
public class User {
    public static final int SEX_FEMALE =0;
    public static final int SEX_MALE =1;

    private long uid;
    private String pwd;
    private String account;
    private String displayName;
    private String avator;
    private int sex; // 性别 1男  0女
    private String desc;//描述

    private List<Long> friends = new ArrayList<Long>();

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public long getUid() {
        return uid;
    }

    public void setUid(long uid) {
        this.uid = uid;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getAvator() {
        return avator;
    }

    public void setAvator(String avator) {
        this.avator = avator;
    }

    public List<Long> getFriends() {
        return friends;
    }

    public void setFriends(List<Long> friends) {
        this.friends = friends;
    }
}//end class
