package com.stfalcon.chatkit.features.messages.widgets;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SimpleItemAnimator;
import android.util.AttributeSet;

import com.stfalcon.chatkit.commons.models.IMessage;
import com.stfalcon.chatkit.features.messages.adapters.MessagesAdapter;
import com.stfalcon.chatkit.features.utils.RecyclerScrollMoreListener;

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

    public <MESSAGE extends IMessage>
    void setAdapter(MessagesAdapter<MESSAGE> adapter) {
        SimpleItemAnimator itemAnimator = new DefaultItemAnimator();
        itemAnimator.setSupportsChangeAnimations(false);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(),
                LinearLayoutManager.VERTICAL, true);

        setItemAnimator(itemAnimator);
        setLayoutManager(layoutManager);

        addOnScrollListener(new RecyclerScrollMoreListener(layoutManager, adapter));
        super.setAdapter(adapter);
    }
}
