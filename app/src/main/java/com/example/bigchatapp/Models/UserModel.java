package com.example.bigchatapp.Models;

public class UserModel {

    String userPic, userName, userPhone, userID, lastMessage;

    public UserModel(String userPic, String userName, String userPhone, String userID, String lastMessage) {
        this.userPic = userPic;
        this.userName = userName;
        this.userPhone = userPhone;
        this.userID = userID;
        this.lastMessage = lastMessage;

    }

    public UserModel(){

    }

    public String getUserPic() {
        return userPic;
    }

    public void setUserPic(String userPic) {
        this.userPic = userPic;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getLastMessage() {
        return lastMessage;
    }

    public void setLastMessage(String lastMessage) {
        this.lastMessage = lastMessage;
    }
}
