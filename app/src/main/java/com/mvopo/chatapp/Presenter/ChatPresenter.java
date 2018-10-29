package com.mvopo.chatapp.Presenter;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.mvopo.chatapp.Interface.ChatContract;
import com.mvopo.chatapp.Model.ChatMessage;

public class ChatPresenter implements ChatContract.chatAction {
    private ChatContract.chatView chatView;

    private FirebaseDatabase database;
    private DatabaseReference messageRef;

    public ChatPresenter(ChatContract.chatView chatView) {
        this.chatView = chatView;

        database = FirebaseDatabase.getInstance();
        messageRef = database.getReference("messages");
    }

    @Override
    public void readMessageFromDB(int start) {
        chatView.initAdapter();
        messageRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                ChatMessage message = dataSnapshot.getValue(ChatMessage.class);
                chatView.addMessage(message);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onLogoutClick() {
        chatView.showHomeFragment();
    }

    @Override
    public void onSendClick() {
        String inputMessage = chatView.getMessage();

        if (inputMessage.isEmpty()) return;

        String sender = chatView.getUsername();
        ChatMessage message = new ChatMessage(inputMessage, sender);
        writeMessageToDB(message);

        chatView.clearMessageArea();
    }

    @Override
    public void writeMessageToDB(ChatMessage message) {
        messageRef.push().setValue(message);
    }
}
