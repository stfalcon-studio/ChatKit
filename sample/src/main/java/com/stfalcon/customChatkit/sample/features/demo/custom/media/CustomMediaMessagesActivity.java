package com.stfalcon.customChatkit.sample.features.demo.custom.media;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import com.stfalcon.customChatkit.messages.MessageHolders;
import com.stfalcon.customChatkit.messages.MessageInput;
import com.stfalcon.customChatkit.messages.MessagesList;
import com.stfalcon.customChatkit.messages.MessagesListAdapter;
import com.stfalcon.customChatkit.sample.R;
import com.stfalcon.customChatkit.sample.common.data.fixtures.MessagesFixtures;
import com.stfalcon.customChatkit.sample.common.data.model.Message;
import com.stfalcon.customChatkit.sample.features.demo.DemoMessagesActivity;
import com.stfalcon.customChatkit.sample.features.demo.custom.media.holders.IncomingVoiceMessageViewHolder;
import com.stfalcon.customChatkit.sample.features.demo.custom.media.holders.OutcomingVoiceMessageViewHolder;

public class CustomMediaMessagesActivity extends DemoMessagesActivity
        implements MessageInput.InputListener,
        MessageInput.AttachmentsListener,
        MessageHolders.ContentChecker<Message>,
        DialogInterface.OnClickListener {

    private static final byte CONTENT_TYPE_VOICE = 1;

    public static void open(Context context) {
        context.startActivity(new Intent(context, CustomMediaMessagesActivity.class));
    }

    private MessagesList messagesList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_media_messages);

        this.messagesList = (MessagesList) findViewById(R.id.messagesList);
        initAdapter();

        MessageInput input = (MessageInput) findViewById(R.id.input);
        input.setInputListener(this);
        input.setAttachmentsListener(this);
    }

    @Override
    public boolean onSubmit(CharSequence input) {
        super.messagesAdapter.addToStart(
                MessagesFixtures.getTextMessage(input.toString()), true);
        return true;
    }

    @Override
    public void onAddAttachments() {
        new AlertDialog.Builder(this)
                .setItems(R.array.view_types_dialog, this)
                .show();
    }

    @Override
    public boolean hasContentFor(Message message, byte type) {
        switch (type) {
            case CONTENT_TYPE_VOICE:
                return message.getVoice() != null
                        && message.getVoice().getUrl() != null
                        && !message.getVoice().getUrl().isEmpty();
        }
        return false;
    }

    @Override
    public void onClick(DialogInterface dialogInterface, int i) {
        switch (i) {
            case 0:
                messagesAdapter.addToStart(MessagesFixtures.getImageMessage(), true);
                break;
            case 1:
                messagesAdapter.addToStart(MessagesFixtures.getVoiceMessage(), true);
                break;
        }
    }

    private void initAdapter() {
        MessageHolders holders = new MessageHolders()
                .registerContentType(
                        CONTENT_TYPE_VOICE,
                        IncomingVoiceMessageViewHolder.class,
                        R.layout.item_custom_incoming_voice_message,
                        OutcomingVoiceMessageViewHolder.class,
                        R.layout.item_custom_outcoming_voice_message,
                        this);


        super.messagesAdapter = new MessagesListAdapter<>(super.senderId, holders, super.imageLoader);
        super.messagesAdapter.enableSelectionMode(this);
        super.messagesAdapter.setLoadMoreListener(this);
        this.messagesList.setAdapter(super.messagesAdapter);
    }
}
