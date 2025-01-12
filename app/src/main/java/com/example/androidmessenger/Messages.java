package com.example.androidmessenger;

public class Messages {
    public static  String  SENT_BY_ME = "me";
    public static  String SENT_BY_BOT = "bot";

    String message;
    String SentBy;

    public String getSentBy() {
        return SentBy;
    }

    public void setSentBy(String sentBy) {
        SentBy = sentBy;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Messages(String message, String sentBy) {
        this.message = message;
        SentBy = sentBy;
    }
}
