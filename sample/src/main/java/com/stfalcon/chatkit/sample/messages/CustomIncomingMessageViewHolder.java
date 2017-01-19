package com.stfalcon.chatkit.sample.messages;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.stfalcon.chatkit.messages.MessagesListAdapter;
import com.stfalcon.chatkit.sample.R;
import com.stfalcon.chatkit.sample.fixtures.MessagesListFixtures;

public class CustomIncomingMessageViewHolder
        extends MessagesListAdapter.MessageViewHolder<MessagesListFixtures.Message> {

    private TextView text;
    private TextView online;
    private ImageView avatar;


    public CustomIncomingMessageViewHolder(View itemView) {
        super(itemView);
        text = (TextView) itemView.findViewById(R.id.messageText);
        online = (TextView) itemView.findViewById(R.id.tvStatusOnline);
        avatar = (ImageView) itemView.findViewById(R.id.messageUserAvatar);
    }

    @Override
    public void onBind(MessagesListFixtures.Message message) {
        text.setText(message.getText());
        online.setText(message.getUser().getOnline());
        if (getImageLoader() != null) {
            getImageLoader().loadImage(avatar, message.getUser().getAvatar());
        }
    }
}
