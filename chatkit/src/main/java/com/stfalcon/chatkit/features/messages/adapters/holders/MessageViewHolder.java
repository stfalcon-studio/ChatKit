package com.stfalcon.chatkit.features.messages.adapters.holders;

import android.view.View;

import com.stfalcon.chatkit.commons.adapter.ViewHolder;

import java.util.Date;

/*
 * Created by troy379 on 09.12.16.
 */
public abstract class MessageViewHolder<DATA> extends ViewHolder<DATA> {

    public abstract Date getCreatedAt();

    public MessageViewHolder(View itemView) {
        super(itemView);
    }

}
