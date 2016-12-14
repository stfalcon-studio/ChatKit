package com.stfalcon.chatkit.sample;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.stfalcon.chatkit.features.messages.adapters.MessagesAdapter;
import com.stfalcon.chatkit.features.messages.adapters.holders.MessageViewHolder;
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

        final MessagesAdapter<Demo.Message> adapter = new MessagesAdapter<>("0", new MessageViewHolder.ImageLoader() {
            @Override
            public void loadImage(ImageView imageView, String url) {
                Picasso.with(MessagesListActivity.this).load(url).into(imageView);
            }
        });
        adapter.enableSelectionMode(this);

        adapter.add(new Demo.Message(0));

        adapter.setLoadMoreListener(new MessagesAdapter.OnLoadMoreListener() {
            @Override
            public void onLoadMore(int page, int totalItemsCount) {
                if (totalItemsCount < 50) {
                    new Handler().postDelayed(new Runnable() { //imitation of slow connection
                        @Override
                        public void run() {
                            ArrayList<Demo.Message> messages = new ArrayList<>();
                            for (int i = 0; i < 10; i++) {
                                messages.add(new Demo.Message(0));
                            }
                            adapter.add(messages, true);
                        }
                    }, 2000);
                }
            }
        });

        messagesList.setAdapter(adapter);
    }

    @Override
    public void onSelectionChanged(int count) {

    }
}
