package com.example.contactapp.models;

public class MessageModel {
    String message_name;
    String message_text;

    public MessageModel() {

    }

    public MessageModel(String message_name, String message_text) {
        this.message_name = message_name;
        this.message_text = message_text;
    }

    public String getMessage_name() {
        return message_name;
    }

    public String getMessage_text() {
        return message_text;
    }

    public void setMessage_name(String message_name) {
        this.message_name = message_name;
    }

    public void setMessage_text(String message_text) {
        this.message_text = message_text;
    }
}
