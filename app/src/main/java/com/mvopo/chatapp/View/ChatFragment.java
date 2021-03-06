package com.mvopo.chatapp.View;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
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

    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;

    private Button btnLogout, btnSend;
    private ListView listView;
    private EditText edtxMessage;

    private ArrayList<ChatMessage> messages;
    private ChatAdapter adapter;

    private String username;

    private ChatPresenter chatPresenter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        hideKeyboard();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chat, container, false);

        fragmentManager = getActivity().getSupportFragmentManager();
        username = getArguments().getString("username");
        messages = new ArrayList<>();
        chatPresenter = new ChatPresenter(this);

        int messageCount = messages.size();

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
    public void addMessage(ChatMessage message) {
        messages.add(message);
        notifyAdapter();
        scrollToBottom();
    }

    @Override
    public void showSignUpFragment() {
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, new SignUpFragment()).commit();
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
    public void hideKeyboard() {
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), 0);
    }
}
