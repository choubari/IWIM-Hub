package com.choubapp.iwim_hub.Model;

public class Message {
    private String message;
    private String sender;
    private String receiver;
    private String date;
    private String time;

    public Message(String message, String sender, String receiver, String date, String time) {
        this.message = message;
        this.sender = sender;
        this.receiver = receiver;
        this.date = date;
        this.time = time;
    }

    public Message() {
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
