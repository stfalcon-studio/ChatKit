package com.stfalcon.customChatkit.sample.features.demo.styled;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.stfalcon.customChatkit.messages.MessageInput;
import com.stfalcon.customChatkit.messages.MessagesList;
import com.stfalcon.customChatkit.messages.MessagesListAdapter;
import com.stfalcon.customChatkit.sample.R;
import com.stfalcon.customChatkit.sample.common.data.fixtures.MessagesFixtures;
import com.stfalcon.customChatkit.sample.features.demo.DemoMessagesActivity;
import com.stfalcon.customChatkit.utils.DateFormatter;

import java.util.Date;

public class StyledMessagesActivity extends DemoMessagesActivity
        implements MessageInput.InputListener,
        MessageInput.AttachmentsListener,
        DateFormatter.Formatter {

    public static void open(Context context) {
        context.startActivity(new Intent(context, StyledMessagesActivity.class));
    }

    private MessagesList messagesList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_styled_messages);

        messagesList = (MessagesList) findViewById(R.id.messagesList);
        initAdapter();

        MessageInput input = (MessageInput) findViewById(R.id.input);
        input.setInputListener(this);
        input.setAttachmentsListener(this);
    }

    @Override
    public boolean onSubmit(CharSequence input) {
        messagesAdapter.addToStart(
                MessagesFixtures.getTextMessage(input.toString()), true);
        return true;
    }

    @Override
    public void onAddAttachments() {
        messagesAdapter.addToStart(MessagesFixtures.getImageMessage(), true);
    }

    @Override
    public String format(Date date) {
        if (DateFormatter.isToday(date)) {
            return getString(R.string.date_header_today);
        } else if (DateFormatter.isYesterday(date)) {
            return getString(R.string.date_header_yesterday);
        } else {
            return DateFormatter.format(date, DateFormatter.Template.STRING_DAY_MONTH_YEAR);
        }
    }

    private void initAdapter() {
        super.messagesAdapter = new MessagesListAdapter<>(super.senderId, super.imageLoader);
        super.messagesAdapter.enableSelectionMode(this);
        super.messagesAdapter.setLoadMoreListener(this);
        super.messagesAdapter.setDateHeadersFormatter(this);
        messagesList.setAdapter(super.messagesAdapter);
    }
}
