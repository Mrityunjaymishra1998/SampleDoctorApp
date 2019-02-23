package com.example.souravmunjal.doctorapp;

public class Message {
    public static final int TYPE_DOC = 1;
    public static final int TYPE_USER = 0;

    int userType;
    String message;

    public Message() {

    }

    public Message(int userType, String message) {

        this.userType = userType;
        this.message = message;
    }

    public int getUserType() {
        return userType;
    }

    public void setUserType(int userType) {
        this.userType = userType;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
