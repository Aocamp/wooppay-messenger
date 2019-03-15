package com.andrey.wooppaymessenger.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class User {
    private static User instance;

    private User(){

    }

    public static User getInstance() {
        if (instance == null) {
            instance = new User();
        }
        return instance;
    }

    @SerializedName("parent_login")
    @Expose
    private Object parentLogin;
    @SerializedName("subject_type")
    @Expose
    private int subjectType;
    @SerializedName("country")
    @Expose
    private int country;
    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("login")
    @Expose
    private String login;
    @SerializedName("status")
    @Expose
    private int status;
    @SerializedName("roles")
    @Expose
    private List<String> roles = null;
    @SerializedName("assignments")
    @Expose
    private Object assignments;
    @SerializedName("token")
    @Expose
    private String token;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("created_at")
    @Expose
    private String createdAt;

    public Object getParentLogin() {
        return parentLogin;
    }

    public void setParentLogin(Object parentLogin) {
        this.parentLogin = parentLogin;
    }

    public int getSubjectType() {
        return subjectType;
    }

    public void setSubjectType(int subjectType) {
        this.subjectType = subjectType;
    }

    public int getCountry() {
        return country;
    }

    public void setCountry(int country) {
        this.country = country;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }

    public Object getAssignments() {
        return assignments;
    }

    public void setAssignments(Object assignments) {
        this.assignments = assignments;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return login+"" +email;
    }

}
