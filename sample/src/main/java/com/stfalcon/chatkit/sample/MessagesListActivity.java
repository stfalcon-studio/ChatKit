package com.stfalcon.chatkit.sample;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.stfalcon.chatkit.features.messages.adapters.MessagesAdapter;
import com.stfalcon.chatkit.features.messages.widgets.MessagesList;

import java.util.ArrayList;

public class MessagesListActivity extends AppCompatActivity
        implements MessagesAdapter.SelectionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messages_list);

        MessagesList messagesList = (MessagesList) findViewById(R.id.messagesList);

//        MessagesAdapter.HoldersConfig holdersConfig = new MessagesAdapter.HoldersConfig();
//        holdersConfig.setIncoming(CustomIncomingMessageViewHolder.class, R.layout.item_custom_incoming_message);
//        MessagesAdapter<Demo.Message> adapter = new MessagesAdapter<>(holdersConfig, "0");

        final MessagesAdapter<Demo.Message> adapter = new MessagesAdapter<>("0");
        adapter.enableSelectionMode(this);

        int count = 0;
        for (int i = 0; i < 100; i++) {
            adapter.add(new Demo.Message(count++));
        }

        messagesList.setAdapter(adapter);

        final int tempCount = count;

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                int newCount = tempCount;
                ArrayList<Demo.Message> messages = new ArrayList<>();
                messages.add(new Demo.Message(newCount++, "new messages"));
                messages.add(new Demo.Message(newCount++, "new messages"));
                messages.add(new Demo.Message(newCount++, "new messages"));
                messages.add(new Demo.Message(newCount++, "new messages"));
                messages.add(new Demo.Message(newCount++, "new messages"));
                messages.add(new Demo.Message(newCount++, "new messages"));
                messages.add(new Demo.Message(newCount++, "new messages"));
                messages.add(new Demo.Message(newCount++, "new messages"));
                messages.add(new Demo.Message(newCount++, "new messages"));
                messages.add(new Demo.Message(newCount++, "new messages"));
                messages.add(new Demo.Message(newCount++, "new messages"));
                messages.add(new Demo.Message(newCount++, "new messages"));
                messages.add(new Demo.Message(newCount++, "new messages"));
                messages.add(new Demo.Message(newCount++, "new messages"));
                messages.add(new Demo.Message(newCount++, "new messages"));
                messages.add(new Demo.Message(newCount++, "new messages"));
                messages.add(new Demo.Message(newCount++, "new messages"));
                messages.add(new Demo.Message(newCount++, "new messages"));
                messages.add(new Demo.Message(newCount++, "new messages"));
                messages.add(new Demo.Message(newCount++, "new messages"));
                adapter.add(messages);
            }
        }, 5000);
    }

    @Override
    public void onSelectionChanged(int count) {

    }
}
