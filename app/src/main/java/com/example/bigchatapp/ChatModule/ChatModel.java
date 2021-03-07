package com.example.bigchatapp.ChatModule;

public class ChatModel {
    private String messageID, message, senderID;
    private String timeStamp;
    private int feelings = -1;
    private String spec;

    public ChatModel(){

    }

    public ChatModel(String message, String senderID, String timeStamp, String spec) {
        this.message = message;
        this.senderID = senderID;
        this.timeStamp = timeStamp;
        this.spec = spec;
    }

    public String getMessageID() {
        return messageID;
    }

    public void setMessageID(String messageID) {
        this.messageID = messageID;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSenderID() {
        return senderID;
    }

    public void setSenderID(String senderID) {
        this.senderID = senderID;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public int getFeelings() {
        return feelings;
    }

    public void setFeelings(int feelings) {
        this.feelings = feelings;
    }

    public String getSpec() {
        return spec;
    }

    public void setSpec(String spec) {
        this.spec = spec;
    }
}
