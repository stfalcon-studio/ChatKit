package com.stfalcon.chatkit.sample.messages;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.stfalcon.chatkit.commons.ImageLoader;
import com.stfalcon.chatkit.messages.MessageInput;
import com.stfalcon.chatkit.messages.MessagesList;
import com.stfalcon.chatkit.messages.MessagesListAdapter;
import com.stfalcon.chatkit.sample.ChatSamplesListAdapter;
import com.stfalcon.chatkit.sample.R;
import com.stfalcon.chatkit.sample.fixtures.MessagesListFixtures;

import java.util.ArrayList;

public class MessagesListActivity extends AppCompatActivity
        implements MessagesListAdapter.SelectionListener,
        Toolbar.OnMenuItemClickListener {

    private static final String ARG_TYPE = "type";

    private MessagesList messagesList;
    private MessagesListAdapter<MessagesListFixtures.Message> adapter;
    private MessageInput input;
    private int selectionCount;

    private Menu menu;
    private ChatSamplesListAdapter.ChatSample.Type type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        type = (ChatSamplesListAdapter.ChatSample.Type) getIntent().getExtras().getSerializable(ARG_TYPE);
        switch (type) {
            case CUSTOM_ATTR:
                setContentView(R.layout.activity_messages_list_attr);
                break;
            case CUSTOM_LAYOUT:
                setContentView(R.layout.activity_messages_list_layout);
                break;
            default:
                setContentView(R.layout.activity_messages_list_default);
        }

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
    public boolean onCreateOptionsMenu(Menu menu) {
        this.menu = menu;
        getMenuInflater().inflate(R.menu.chat_actions_menu, menu);
        onSelectionChanged(0);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_delete:
                adapter.deleteSelectedMessages();
                break;
        }
        return true;
    }

    @Override
    public void onSelectionChanged(int count) {
        this.selectionCount = count;
        menu.findItem(R.id.action_delete).setVisible(count > 0);
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_delete:
                adapter.deleteSelectedMessages();
                break;
        }
        return false;
    }

    @Override
    public void onBackPressed() {
        if (selectionCount == 0) {
            super.onBackPressed();
        } else {
            adapter.unselectAllItems();
        }
    }

    private void initMessagesAdapter() {
        ImageLoader imageLoader = new ImageLoader() {
            @Override
            public void loadImage(ImageView imageView, String url) {
                Picasso.with(MessagesListActivity.this).load(url).into(imageView);
            }
        };

        if (type == ChatSamplesListAdapter.ChatSample.Type.CUSTOM_LAYOUT) {
            MessagesListAdapter.HoldersConfig holdersConfig = new MessagesListAdapter.HoldersConfig();
            holdersConfig.setIncoming(MessagesListAdapter.DefaultIncomingMessageViewHolder.class,
                    R.layout.item_custom_incoming_message);
            holdersConfig.setOutcoming(MessagesListAdapter.DefaultOutcomingMessageViewHolder.class,
                    R.layout.item_custom_outcoming_message);
            adapter = new MessagesListAdapter<>("0", holdersConfig, imageLoader);
            adapter.setOnLongClickListener(new MessagesListAdapter.OnLongClickListener<MessagesListFixtures.Message>() {
                @Override
                public void onMessageLongClick(MessagesListFixtures.Message message) {
                    //Yor custom long click handler
                    Toast.makeText(MessagesListActivity.this,
                            R.string.on_log_click_message, Toast.LENGTH_SHORT).show();
                }
            });
        } else if (type == ChatSamplesListAdapter.ChatSample.Type.CUSTOM_VIEW_HOLDER) {
            MessagesListAdapter.HoldersConfig holdersConfig = new MessagesListAdapter.HoldersConfig();
            holdersConfig.setIncoming(CustomIncomingMessageViewHolder.class, R.layout.item_custom_holder_incoming_message);
            holdersConfig.setOutcoming(CustomOutcomingMessageViewHolder.class, R.layout.item_custom_holder_outcoming_message);
            adapter = new MessagesListAdapter<>("0", holdersConfig, imageLoader);
            adapter.setOnLongClickListener(new MessagesListAdapter.OnLongClickListener<MessagesListFixtures.Message>() {
                @Override
                public void onMessageLongClick(MessagesListFixtures.Message message) {
                    //Yor custom long click handler
                    Toast.makeText(MessagesListActivity.this,
                            R.string.on_log_click_message, Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            adapter = new MessagesListAdapter<>("0", imageLoader);
            adapter.enableSelectionMode(this);
        }

        adapter.add(new MessagesListFixtures.Message(), false);

        adapter.setLoadMoreListener(new MessagesListAdapter.OnLoadMoreListener() {
            @Override
            public void onLoadMore(int page, int totalItemsCount) {
                if (totalItemsCount < 50) {
                    loadMessages();
                }
            }
        });

        messagesList.setAdapter(adapter);
    }

    public static void open(Activity activity, ChatSamplesListAdapter.ChatSample.Type type) {
        Intent intent = new Intent(activity, MessagesListActivity.class);
        intent.putExtra(ARG_TYPE, type);
        activity.startActivity(intent);
    }

    private void loadMessages() {
        new Handler().postDelayed(new Runnable() { //imitation of internet connection
            @Override
            public void run() {
                ArrayList<MessagesListFixtures.Message> messages = MessagesListFixtures.getMessages();
                adapter.add(messages, true);
            }
        }, 1000);
    }
}
