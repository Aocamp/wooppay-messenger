package com.andrey.wooppaymessenger.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.andrey.wooppaymessenger.R;
import com.andrey.wooppaymessenger.database.model.Message;
import com.andrey.wooppaymessenger.model.ChatMessage;

import java.text.DateFormat;
import java.util.Collection;
import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MessageViewHolder> {

    class MessageViewHolder extends RecyclerView.ViewHolder {
        TextView messageUser;
        TextView messageTime;
        TextView messageText;

        public MessageViewHolder(View v) {
            super(v);
            messageUser = v.findViewById(R.id.message_user);
            messageTime = v.findViewById(R.id.message_time);
            messageText = v.findViewById(R.id.message_text);
        }
    }

    private List<ChatMessage> messageList;

    private final LayoutInflater mInflater;

    public MessageAdapter(Context context, List<ChatMessage> mMessages) {
        messageList = mMessages;
        mInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public MessageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.message, parent, false);
        return new MessageViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MessageViewHolder holder, int position) {
//        DateFormat df = DateFormat.getTimeInstance();
//        String time = df.format(messageList.get(position).getMessageDate());
        holder.messageUser.setText(messageList.get(position).getUserLogin());
        holder.messageTime.setText(messageList.get(position).getMessageDate());
        holder.messageText.setText(messageList.get(position).getMessageText());
    }

    public void setItem(Collection<ChatMessage> messages) {
        messageList.addAll(messages);
        notifyDataSetChanged();
    }

    public void clearItem() {
        messageList.clear();
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (messageList != null)
            return messageList.size();
        else return 0;
    }
}
