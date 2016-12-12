package com.stfalcon.chatkit.features.messages.adapters.holders;

import android.view.View;
import android.widget.TextView;

import com.stfalcon.chatkit.R;
import com.stfalcon.chatkit.features.messages.models.IMessage;

/*
 * Created by troy379 on 09.12.16.
 */
public class DefaultIncomingMessageViewHolder
        extends MessageViewHolder<IMessage> {

    private TextView text;

    public DefaultIncomingMessageViewHolder(View itemView) {
        super(itemView);
        text = (TextView) itemView.findViewById(R.id.text);
    }

    @Override
    public void onBind(IMessage message) {
        // TODO: 09.12.16 fill views
        text.setText(isSelected() ? "selected" : message.getText());
    }
}
