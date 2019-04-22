package com.andrey.wooppaymessenger.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class User {
    protected Long id;

    protected String userLogin;

    protected int support;

    public User(){

    }

    public User(Long id, String userLogin, int support) {
        this.id = id;
        this.userLogin = userLogin;
        this.support = support;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserLogin() {
        return userLogin;
    }

    public void setUserLogin(String userLogin) {
        this.userLogin = userLogin;
    }

    public int getSupport() {
        return support;
    }

    public void setSupport(int support) {
        this.support = support;
    }

}
