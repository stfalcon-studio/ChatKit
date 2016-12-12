package com.stfalcon.chatkit.sample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.stfalcon.chatkit.features.messages.adapters.MessagesAdapter;
import com.stfalcon.chatkit.features.messages.widgets.MessagesList;

public class MessagesListActivity extends AppCompatActivity {

    private MessagesList messagesList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messages_list);

        messagesList = (MessagesList) findViewById(R.id.messagesList);

        MessagesAdapter.HoldersConfig holdersConfig = new MessagesAdapter.HoldersConfig();
        holdersConfig.setIncoming(CustomIncomingMessageViewHolder.class, R.layout.item_custom_incoming_message);

        MessagesAdapter<Demo.Message> adapter = new MessagesAdapter<>(holdersConfig, "0");

        int count = 0;
        for (int i = 0; i < 100; i++) {
            adapter.add(new Demo.Message(count++));
        }

        messagesList.setAdapter(adapter);
    }
}
