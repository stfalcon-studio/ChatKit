package com.stfalcon.customChatkit.sample.features.demo.custom.holder.holders.messages;

import android.view.View;

import com.stfalcon.customChatkit.messages.MessageHolders;
import com.stfalcon.customChatkit.sample.common.data.model.Message;

public class CustomOutcomingTextMessageViewHolder
        extends MessageHolders.OutcomingTextMessageViewHolder<Message> {

    public CustomOutcomingTextMessageViewHolder(View itemView, Object payload) {
        super(itemView, payload);
    }

    @Override
    public void onBind(Message message) {
        super.onBind(message);

        time.setText(message.getStatus() + " " + time.getText());
    }
}
