package com.mvopo.chatapp.Helper;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.mvopo.chatapp.Model.ChatMessage;
import com.mvopo.chatapp.R;

import java.util.List;

public class ChatAdapter extends ArrayAdapter {
    private Context context;
    private int layoutID;
    private List<ChatMessage> messages;
    private String username;

    public ChatAdapter(@NonNull Context context, int resource, @NonNull List messages, String username) {
        super(context, resource, messages);

        this.context = context;
        this.layoutID = resource;
        this.messages = messages;
        this.username = username;
    }

    @Override
    public int getCount() {
        int count = 0;

        if (messages != null) count = messages.size();

        return count;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        String message = messages.get(position).getMessage();
        String sender = messages.get(position).getSender();

        if (username.equalsIgnoreCase(sender)) {
            sender = "You";
            convertView = LayoutInflater.from(context).inflate(R.layout.message_out_item, parent, false);
        } else {
            sender = sender.substring(0,1).toUpperCase() + sender.substring(1);
            convertView = LayoutInflater.from(context).inflate(R.layout.message_in_item, parent, false);
        }

        TextView tvMessage = convertView.findViewById(R.id.message_item_text);
        TextView tvName = convertView.findViewById(R.id.message_item_name);

        tvMessage.setText(message);
        tvName.setText(sender);

        return convertView;
    }
}
