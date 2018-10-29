package com.mvopo.chatapp.Interface;

import com.mvopo.chatapp.Model.ChatMessage;

import java.util.ArrayList;

public class ChatContract {

    public interface chatView{
        String getUsername();
        String getMessage();

        void initAdapter();

        void notifyAdapter();

        void addMessage(ChatMessage message);
        void setMessage(ArrayList<ChatMessage> messages);

        void showHomeFragment();

        void scrollToBottom();

        void clearMessageArea();
    }

    public interface chatAction{
        void readMessageFromDB(int start);

        void onLogoutClick();
        void onSendClick();

        void writeMessageToDB(ChatMessage message);
    }
}
