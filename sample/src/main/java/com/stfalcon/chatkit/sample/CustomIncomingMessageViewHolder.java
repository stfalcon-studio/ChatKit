package com.stfalcon.chatkit.sample;

import android.view.View;
import android.widget.TextView;

import com.stfalcon.chatkit.messages.MessagesListAdapter;
import com.stfalcon.chatkit.sample.fixtures.MessagesListFixtures;

/*
 * Created by troy379 on 12.12.16.
 */
public class CustomIncomingMessageViewHolder
        extends MessagesListAdapter.MessageViewHolder<MessagesListFixtures.Message> {

    private TextView text;

    public CustomIncomingMessageViewHolder(View itemView) {
        super(itemView);
        text = (TextView) itemView.findViewById(R.id.messageText);
    }

    @Override
    public void onBind(MessagesListFixtures.Message message) {
        text.setText(message.getText().concat(" (from custom holder)"));
    }
}
