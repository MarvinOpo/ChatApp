package com.mvopo.chatapp.Model;

public class ChatMessage {
    String message, sender;

    public ChatMessage(){}

    public ChatMessage(String message, String sender) {
        this.message = message;
        this.sender = sender;
    }

    public String getMessage() {
        return message;
    }

    public String getSender() {
        return sender;
    }
}
