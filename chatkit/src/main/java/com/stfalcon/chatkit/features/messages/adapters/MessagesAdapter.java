package com.stfalcon.chatkit.features.messages.adapters;

import android.support.annotation.LayoutRes;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.stfalcon.chatkit.features.messages.adapters.holders.MessageViewHolder;

import java.lang.reflect.Constructor;
import java.util.List;

/*
 * Created by troy379 on 09.12.16.
 */
public class MessagesAdapter<HOLDER extends MessageViewHolder<DATA>, DATA>
        extends RecyclerView.Adapter<HOLDER> {

    private @LayoutRes int layout;
    private Class<HOLDER> holderClass;
    private List<DATA> messages;

    public MessagesAdapter(@LayoutRes int layout, Class<HOLDER> holderClass, List<DATA> messages) {
        this.layout = layout;
        this.holderClass = holderClass;
        this.messages = messages;
    }


    @Override
    public HOLDER onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(layout, parent, false);

        try {
            Constructor<HOLDER> constructor = holderClass.getDeclaredConstructor(View.class);
            constructor.setAccessible(true);
            return constructor.newInstance(v);
        }
        catch (Exception ignore) { }
        return null;
    }

    @Override
    public void onBindViewHolder(HOLDER holder, int position) {
        holder.onBind(messages.get(position));
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    public List<DATA> getMessages() {
        return messages;
    }
}
