package com.stfalcon.chatkit.sample.features.demo.custom.holder.holders.messages;

import android.view.View;

import com.stfalcon.chatkit.messages.MessageHolders;
import com.stfalcon.chatkit.sample.common.data.model.Message;

/*
 * Created by troy379 on 05.04.17.
 */
public class CustomOutcomingImageMessageViewHolder
        extends MessageHolders.OutcomingImageMessageViewHolder<Message> {

    public CustomOutcomingImageMessageViewHolder(View itemView, Object payload) {
        super(itemView, payload);
    }

    @Override
    public void onBind(Message message) {
        super.onBind(message);

        time.setText(message.getStatus() + " " + time.getText());
    }

    //Override this method to pass custom data in ImageLoader.
    @Override
    protected void loadImage(Message message) {
        imageLoader.loadImage(image, message.getImageUrl(), null);
    }
}