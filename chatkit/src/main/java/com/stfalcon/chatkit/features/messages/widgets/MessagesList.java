package com.stfalcon.chatkit.features.messages.widgets;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

/*
 * Created by troy379 on 09.12.16.
 */
public class MessagesList extends RecyclerView {

    public MessagesList(Context context) {
        super(context);
    }

    public MessagesList(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public MessagesList(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public void setAdapter(Adapter adapter) {
        throw new IllegalArgumentException("You can't set adapter to MessagesList, because it has own adapter");
    }

    public void init() {
//        super.setAdapter();
    }
}
