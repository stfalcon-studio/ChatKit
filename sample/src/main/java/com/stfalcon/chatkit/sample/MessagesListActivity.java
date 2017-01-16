package com.stfalcon.chatkit.sample;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.stfalcon.chatkit.commons.ImageLoader;
import com.stfalcon.chatkit.messages.MessageInput;
import com.stfalcon.chatkit.messages.MessagesList;
import com.stfalcon.chatkit.messages.MessagesListAdapter;
import com.stfalcon.chatkit.sample.fixtures.MessagesListFixtures;

import java.util.ArrayList;

public class MessagesListActivity extends AppCompatActivity
        implements MessagesListAdapter.SelectionListener {

    private MessagesList messagesList;
    private MessagesListAdapter<MessagesListFixtures.Message> adapter;

    private MessageInput input;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messages_list);

        messagesList = (MessagesList) findViewById(R.id.messagesList);
        initMessagesAdapter();

        input = (MessageInput) findViewById(R.id.input);
        input.setInputListener(new MessageInput.InputListener() {
            @Override
            public boolean onSubmit(CharSequence input) {
                adapter.add(new MessagesListFixtures.Message(input.toString()), true);
                return true;
            }
        });
    }

    @Override
    public void onSelectionChanged(int count) {

    }

    private void initMessagesAdapter() {
//        MessagesListAdapter.HoldersConfig holdersConfig = new MessagesListAdapter.HoldersConfig();
//        holdersConfig.setIncoming(CustomIncomingMessageViewHolder.class, R.layout.item_custom_incoming_message);
//        MessagesListAdapter<Demo.Message> adapter = new MessagesListAdapter<>(holdersConfig, "0");

        adapter = new MessagesListAdapter<>("0", new ImageLoader() {
            @Override
            public void loadImage(ImageView imageView, String url) {
                Picasso.with(MessagesListActivity.this).load(url).into(imageView);
            }
        });
        adapter.enableSelectionMode(this);

        adapter.add(new MessagesListFixtures.Message(), false);

        adapter.setLoadMoreListener(new MessagesListAdapter.OnLoadMoreListener() {
            @Override
            public void onLoadMore(int page, int totalItemsCount) {
                if (totalItemsCount < 50) {
                    new Handler().postDelayed(new Runnable() { //imitation of slow connection
                        @Override
                        public void run() {
                            ArrayList<MessagesListFixtures.Message> messages = new ArrayList<>();
                            for (int i = 0; i < 10; i++) {
                                messages.add(new MessagesListFixtures.Message());
                            }
                            adapter.add(messages, true);
                        }
                    }, 2000);
                }
            }
        });

        messagesList.setAdapter(adapter);
    }
}
