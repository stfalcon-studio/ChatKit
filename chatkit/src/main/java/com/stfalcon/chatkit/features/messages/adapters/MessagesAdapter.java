package com.stfalcon.chatkit.features.messages.adapters;

import android.support.annotation.LayoutRes;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.stfalcon.chatkit.features.messages.adapters.holders.MessageViewHolder;
import com.stfalcon.chatkit.features.messages.models.IMessage;
import com.stfalcon.chatkit.features.utils.DatesUtils;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/*
 * Created by troy379 on 09.12.16.
 */
public class MessagesAdapter<HOLDER extends MessageViewHolder<MESSAGE>, MESSAGE extends IMessage>
        extends RecyclerView.Adapter<HOLDER> {

    private @LayoutRes int layout;
    private Class<HOLDER> holderClass;
    private List<Wrapper> items;

    public MessagesAdapter(@LayoutRes int layout, Class<HOLDER> holderClass) {
        this.layout = layout;
        this.holderClass = holderClass;
        this.items = new ArrayList<>();
    }

    @Override
    public HOLDER onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(layout, parent, false);

        try {
            Constructor<HOLDER> constructor = holderClass.getDeclaredConstructor(View.class);
            constructor.setAccessible(true);
            return constructor.newInstance(v);
        } catch (Exception e) {
            return null;
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public void onBindViewHolder(HOLDER holder, int position) {
        Object item = items.get(position).item;

        if (item instanceof IMessage) {
            holder.onBind((MESSAGE) item);
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }


    /*
    * PRIVATE METHODS
    * */
    public void add(MESSAGE message) {
        boolean isNewMessageToday = !isPreviousSameDate(0, message.getCreatedAt());
        if (isNewMessageToday) {
            items.add(0, new Wrapper<>(message.getCreatedAt()));
        }
        Wrapper<MESSAGE> element = new Wrapper<>(message);
        items.add(0, element);
        notifyItemRangeInserted(0, isNewMessageToday ? 2 : 1);
    }

    public void add(ArrayList<MESSAGE> messages) {
        Collections.reverse(messages);
        int oldSize = items.size();
        generateDateHeaders(messages);
        notifyItemRangeInserted(oldSize, items.size() - oldSize);
    }

    public void update(String oldId, MESSAGE newMessage) {
        int position = getMessagePositionById(oldId);
        Wrapper<MESSAGE> element = new Wrapper<>(newMessage);
        items.set(position, element);
        notifyItemChanged(position);
    }

    public void deleteByIds(String[] ids) {
        for (String id : ids) {
            int index = getMessagePositionById(id);
            items.remove(index);
            notifyItemRemoved(index);
        }
        recountDateHeaders();
    }

    @SuppressWarnings("unchecked")
    public void onLastItemLoaded() {
        MESSAGE lastMessage = (MESSAGE) items.get(items.size() - 1).item;
        this.items.add(new Wrapper<>(lastMessage.getCreatedAt()));
        notifyItemInserted(items.size());
    }

    /*
    * PRIVATE METHODS
    * */
    private void recountDateHeaders() {
        ArrayList<Integer> indicesToDelete = new ArrayList<>();

        for (int i = 0; i < items.size(); i++) {
            Wrapper wrapper = items.get(i);
            if (wrapper.item instanceof Date) {
                if (i == 0) {
                    indicesToDelete.add(i);
                } else {
                    if (items.get(i - 1).item instanceof Date) {
                        indicesToDelete.add(i);
                    }
                }
            }
        }

        Collections.reverse(indicesToDelete);
        for (int i : indicesToDelete) {
            items.remove(i);
            notifyItemRemoved(i);
        }
    }

    private void generateDateHeaders(ArrayList<MESSAGE> messages) {
        for (int i = 0; i < messages.size(); i++) {
            MESSAGE message = messages.get(i);
            this.items.add(new Wrapper<>(message));
            if (messages.size() > i + 1) {
                MESSAGE nextMessage = messages.get(i + 1);
                if (!DatesUtils.isSameDay(message.getCreatedAt(), nextMessage.getCreatedAt())) {
                    this.items.add(new Wrapper<>(message.getCreatedAt()));
                }
            }
        }
    }

    @SuppressWarnings("unchecked")
    private int getMessagePositionById(String id) {
        for (int i = 0; i < items.size(); i++) {
            Wrapper wrapper = items.get(i);
            if (wrapper.item instanceof IMessage) {
                MESSAGE message = (MESSAGE) wrapper.item;
                if (message.getId().contentEquals(id)) {
                    return i;
                }
            }
        }
        return -1;
    }

    @SuppressWarnings("unchecked")
    private boolean isPreviousSameDate(int position, Date dateToCompare) {
        if (items.size() <= position) return false;
        if (items.get(position).item instanceof IMessage) {
            Date previousPositionDate = ((MESSAGE) items.get(position).item).getCreatedAt();
            return DatesUtils.isSameDay(dateToCompare, previousPositionDate);
        } else return false;
    }

    @SuppressWarnings("unchecked")
    private boolean isPreviousSameAuthor(String id, int position) {
        int prevPosition = position + 1;
        if (items.size() <= prevPosition) return false;

        if (items.get(prevPosition).item instanceof IMessage) {
            return ((MESSAGE) items.get(prevPosition).item).getAuthorId().contentEquals(id);
        } else return false;
    }

    /*
    * WRAPPERS
    * */
    private class Wrapper<DATA> {
        private DATA item;

        public Wrapper(DATA item) {
            this.item = item;
        }
    }

    /*
    * LISTENERS
    * */
    public interface Listener<MESSAGE extends IMessage> {
        void onMessageLongClick(MESSAGE message);
    }
}
