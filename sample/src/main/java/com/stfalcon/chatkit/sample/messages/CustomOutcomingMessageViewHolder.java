package com.stfalcon.chatkit.sample.messages;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.stfalcon.chatkit.messages.MessagesListAdapter;
import com.stfalcon.chatkit.sample.R;
import com.stfalcon.chatkit.sample.fixtures.MessagesListFixtures;

public class CustomOutcomingMessageViewHolder
        extends MessagesListAdapter.MessageViewHolder<MessagesListFixtures.Message> {

    private TextView text;
    private TextView status;
    private ImageView avatar;


    public CustomOutcomingMessageViewHolder(View itemView) {
        super(itemView);
        text = (TextView) itemView.findViewById(R.id.messageText);
        status = (TextView) itemView.findViewById(R.id.tvStatus);
        avatar = (ImageView) itemView.findViewById(R.id.messageUserAvatar);
    }

    @Override
    public void onBind(MessagesListFixtures.Message message) {
        text.setText(message.getText());
        status.setText(message.getStatus());
        if (getImageLoader() != null) {
            getImageLoader().loadImage(avatar, message.getUser().getAvatar());
        }
    }
}
