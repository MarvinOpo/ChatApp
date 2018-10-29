package com.mvopo.chatapp.View;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.mvopo.chatapp.Helper.ChatAdapter;
import com.mvopo.chatapp.Interface.ChatContract;
import com.mvopo.chatapp.Model.ChatMessage;
import com.mvopo.chatapp.Presenter.ChatPresenter;
import com.mvopo.chatapp.R;

import java.util.ArrayList;

public class ChatFragment extends Fragment implements ChatContract.chatView {

    private Button btnLogout, btnSend;
    private ListView listView;
    private EditText edtxMessage;

    private ArrayList<ChatMessage> messages;
    private ChatAdapter adapter;

    private String username;

    private ChatPresenter chatPresenter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chat, container, false);

        username = getArguments().getString("username");
        messages = new ArrayList<>();
        chatPresenter = new ChatPresenter(this);

        int messageCount = messages.size();

        Log.e("QWEQWE1",messageCount+"");

        btnLogout = view.findViewById(R.id.header_logout);
        btnLogout.setVisibility(View.VISIBLE);
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chatPresenter.onLogoutClick();
            }
        });

        btnSend = view.findViewById(R.id.chat_send_btn);
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chatPresenter.onSendClick();
            }
        });

        listView = view.findViewById(R.id.chat_lv);
        edtxMessage = view.findViewById(R.id.chat_message_edtx);

        chatPresenter.readMessageFromDB(messageCount);

        return view;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public String getMessage() {
        return edtxMessage.getText().toString().trim();
    }

    @Override
    public void initAdapter() {
        adapter = new ChatAdapter(getContext(), R.layout.fragment_chat, messages, username);
        listView.setAdapter(adapter);
        notifyAdapter();
    }

    @Override
    public void notifyAdapter() {
        adapter.notifyDataSetChanged();
    }

    @Override
    public void showHomeFragment() {
        getActivity().getSupportFragmentManager().popBackStack();
    }

    @Override
    public void scrollToBottom() {
        listView.setSelection(adapter.getCount() - 1);
    }

    @Override
    public void clearMessageArea() {
        edtxMessage.setText("");
    }

    @Override
    public void addMessage(ChatMessage message) {
        messages.add(message);
        notifyAdapter();
        scrollToBottom();
    }

    @Override
    public void setMessage(ArrayList<ChatMessage> messages) {
        this.messages.addAll(0, messages);
        initAdapter();
    }
}
