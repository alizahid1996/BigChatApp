package com.example.bigchatapp.Models;

import java.util.ArrayList;

public class UserStatusModel {
    private String name, profileImage;
    private long lastUpdated;
    private ArrayList<StatusModel> status;

    public UserStatusModel(){

    }

    public UserStatusModel(String name, String profileImage, long lastUpdated, ArrayList<StatusModel> status) {
        this.name = name;
        this.profileImage = profileImage;
        this.lastUpdated = lastUpdated;
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    public long getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(long lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public ArrayList<StatusModel> getStatus() {
        return status;
    }

    public void setStatus(ArrayList<StatusModel> status) {
        this.status = status;
    }
}
