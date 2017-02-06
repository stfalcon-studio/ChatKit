package com.stfalcon.chatkit.sample.messages;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.stfalcon.chatkit.messages.MessagesListAdapter;
import com.stfalcon.chatkit.sample.R;
import com.stfalcon.chatkit.sample.fixtures.MessagesListFixtures;

public class CustomOutcomingMessageViewHolder
        extends MessagesListAdapter.OutcomingMessageViewHolder<MessagesListFixtures.Message> {

    public CustomOutcomingMessageViewHolder(View itemView) {
        super(itemView);
    }

    @Override
    public void onBind(MessagesListFixtures.Message message) {
        super.onBind(message);

        time.setText(message.getStatus() + " " + time.getText());
    }
}
