package com.stfalcon.chatkit.sample;

import android.view.View;
import android.widget.TextView;

import com.stfalcon.chatkit.features.messages.adapters.holders.MessageViewHolder;

/*
 * Created by troy379 on 12.12.16.
 */
public class CustomIncomingMessageViewHolder extends MessageViewHolder<Demo.Message> {

    private TextView text;

    public CustomIncomingMessageViewHolder(View itemView) {
        super(itemView);
        text = (TextView) itemView.findViewById(R.id.text);
    }

    @Override
    public void onBind(Demo.Message message) {
        text.setText(message.getText().concat(" (from custom holder)"));
    }
}
