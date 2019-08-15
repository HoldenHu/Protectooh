package com.scu.holden.protectooth.localdatbase;

/**
 * Created by mirrorssssssss on 5/10/17.
 */

public class User {
    private String mName;
    private String mPassword;
    private String mPhone;
    private String nickname;

    public User() {

    }

    public User(String name, String password) {
        mPhone = name;
        mPassword = password;
        mName=name;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getPassword() {
        return mPassword;
    }

    public void setPassword(String password) {
        mPassword = password;
    }

    public String getPhone() {
        return mPhone;
    }

    public void setPhone(String phone) {
        mPhone = phone;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

}
