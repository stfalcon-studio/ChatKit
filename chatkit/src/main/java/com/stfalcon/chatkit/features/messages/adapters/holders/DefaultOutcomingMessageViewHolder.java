package com.stfalcon.chatkit.features.messages.adapters.holders;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.stfalcon.chatkit.R;
import com.stfalcon.chatkit.commons.models.IMessage;

/*
 * Created by troy379 on 12.12.16.
 */
public class DefaultOutcomingMessageViewHolder
        extends MessageViewHolder<IMessage> {

    private TextView text;
    private ImageView userAvatar;

    public DefaultOutcomingMessageViewHolder(View itemView) {
        super(itemView);
        text = (TextView) itemView.findViewById(R.id.messageText);
        userAvatar = (ImageView) itemView.findViewById(R.id.messageUserAvatar);
    }

    @Override
    public void onBind(IMessage message) {
        text.setText(isSelected() ? "selected" : message.getText());

        boolean isAvatarExists = message.getUser().getAvatar() != null && !message.getUser().getAvatar().isEmpty();
        userAvatar.setVisibility(isAvatarExists ? View.VISIBLE : View.GONE);
        if (isAvatarExists && imageLoader != null) {
            imageLoader.loadImage(userAvatar, message.getUser().getAvatar());
        }
    }
}
