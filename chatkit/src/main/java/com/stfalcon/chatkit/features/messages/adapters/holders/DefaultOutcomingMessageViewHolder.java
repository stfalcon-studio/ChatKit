package com.stfalcon.chatkit.features.messages.adapters.holders;

import android.view.View;
import android.widget.TextView;

import com.stfalcon.chatkit.R;
import com.stfalcon.chatkit.commons.models.IMessage;

/*
 * Created by troy379 on 12.12.16.
 */
public class DefaultOutcomingMessageViewHolder
        extends MessageViewHolder<IMessage> {

    private TextView text;

    public DefaultOutcomingMessageViewHolder(View itemView) {
        super(itemView);
        text = (TextView) itemView.findViewById(R.id.text);
    }

    @Override
    public void onBind(IMessage message) {
        // TODO: 09.12.16 fill views
        text.setText(isSelected() ? "selected" : message.getText());
    }
}
