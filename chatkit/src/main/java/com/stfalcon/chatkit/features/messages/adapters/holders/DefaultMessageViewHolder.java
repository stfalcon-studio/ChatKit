package com.stfalcon.chatkit.features.messages.adapters.holders;

import android.view.View;

import com.stfalcon.chatkit.features.messages.models.DefaultMessage;

import java.util.Date;

/*
 * Created by troy379 on 09.12.16.
 */
public class DefaultMessageViewHolder extends MessageViewHolder<DefaultMessage> {

    private Date createdAt;

    public DefaultMessageViewHolder(View itemView) {
        super(itemView);
    }

    @Override
    public void onBind(DefaultMessage defaultMessage) {
        this.createdAt = defaultMessage.getCreatedAt();
    }

    @Override
    public Date getCreatedAt() {
        return this.createdAt;
    }
}
