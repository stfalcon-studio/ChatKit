package com.stfalcon.chatkit.sample.messages;

import android.view.View;

import com.stfalcon.chatkit.messages.MessagesListAdapter;
import com.stfalcon.chatkit.sample.R;
import com.stfalcon.chatkit.sample.fixtures.MessagesListFixtures;
import com.stfalcon.chatkit.sample.models.DefaultUser;

public class CustomIncomingMessageViewHolder
        extends MessagesListAdapter.IncomingMessageViewHolder<MessagesListFixtures.Message> {
    private View onlineView;

    public CustomIncomingMessageViewHolder(View itemView) {
        super(itemView);
        onlineView = itemView.findViewById(R.id.online);
    }

    @Override
    public void onBind(MessagesListFixtures.Message message) {
        super.onBind(message);

        boolean isOnline = ((DefaultUser) message.getUser()).isOnline();
        if (isOnline) {
            onlineView.setBackgroundResource(R.drawable.shape_bubble_online);
        } else {
            onlineView.setBackgroundResource(R.drawable.shape_bubble_offline);
        }
    }
}
