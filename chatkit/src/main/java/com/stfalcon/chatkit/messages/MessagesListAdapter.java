/*******************************************************************************
 * Copyright 2016 stfalcon.com
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *******************************************************************************/

package com.stfalcon.chatkit.messages;

import android.support.annotation.LayoutRes;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.stfalcon.chatkit.R;
import com.stfalcon.chatkit.commons.ImageLoader;
import com.stfalcon.chatkit.commons.ViewHolder;
import com.stfalcon.chatkit.commons.models.IMessage;
import com.stfalcon.chatkit.utils.DateFormatter;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 *
 */
public class MessagesListAdapter<MESSAGE extends IMessage>
        extends RecyclerView.Adapter<ViewHolder>
        implements RecyclerScrollMoreListener.OnLoadMoreListener {

    private static final int VIEW_TYPE_INCOMING_MESSAGE = 0x00;
    private static final int VIEW_TYPE_OUTCOMING_MESSAGE = 0x01;
    private static final int VIEW_TYPE_DATE_HEADER = 0x02;

    private HoldersConfig holders;
    private String senderId;
    private List<Wrapper> items;

    private int selectedItemsCount;
    private boolean isSelectMode;
    private SelectionListener selectionListener;

    private OnLoadMoreListener loadMoreListener;
    private OnClickListener<MESSAGE> onClickListener;
    private OnLongClickListener<MESSAGE> onLongClickListener;
    private ImageLoader imageLoader;
    private RecyclerView.LayoutManager layoutManager;
    private MessagesListStyle messagesListStyle;

    public MessagesListAdapter(String senderId) {
        this(senderId, null);
    }

    public MessagesListAdapter(String senderId, ImageLoader imageLoader) {
        this(senderId, new HoldersConfig(), imageLoader);
    }

    public MessagesListAdapter(String senderId, HoldersConfig holders,
                               ImageLoader imageLoader) {
        this.senderId = senderId;
        this.holders = holders;
        this.imageLoader = imageLoader;
        this.items = new ArrayList<>();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case VIEW_TYPE_INCOMING_MESSAGE:
                return getHolder(parent, holders.incomingLayout, holders.incomingHolder);
            case VIEW_TYPE_OUTCOMING_MESSAGE:
                return getHolder(parent, holders.outcomingLayout, holders.outcomingHolder);
            case VIEW_TYPE_DATE_HEADER:
                return getHolder(parent, holders.dateHeaderLayout, holders.dateHeaderHolder);
        }
        return null;
    }

    public <HOLDER extends ViewHolder>
    ViewHolder getHolder(ViewGroup parent, @LayoutRes int layout, Class<HOLDER> holderClass) {
        View v = LayoutInflater.from(parent.getContext()).inflate(layout, parent, false);

        try {
            Constructor<HOLDER> constructor = holderClass.getDeclaredConstructor(View.class);
            constructor.setAccessible(true);
            HOLDER holder = constructor.newInstance(v);
            if (holder instanceof DefaultMessageViewHolder) {
                ((DefaultMessageViewHolder) holder).applyStyle(messagesListStyle);
            }
            return holder;
        } catch (Exception e) {
            return null;
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Wrapper wrapper = items.get(position);

        if (wrapper.item instanceof IMessage) {
            ((MessageViewHolder) holder).setSelected(wrapper.isSelected);
            ((MessageViewHolder) holder).setImageLoader(this.imageLoader);
            holder.itemView.setOnLongClickListener(getMessageLongClickListener(wrapper));
            holder.itemView.setOnClickListener(getMessageClickListener(wrapper));
        }

        holder.onBind(wrapper.item);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public int getItemViewType(int position) {
        Wrapper wrapper = items.get(position);
        if (wrapper.item instanceof IMessage) {
            IMessage message = (IMessage) wrapper.item;
            if (message.getUser().getId().contentEquals(senderId)) {
                return VIEW_TYPE_OUTCOMING_MESSAGE;
            } else {
                return VIEW_TYPE_INCOMING_MESSAGE;
            }
        } else {
            return VIEW_TYPE_DATE_HEADER;
        }
    }

    @Override
    public void onLoadMore(int page, int total) {
        if (loadMoreListener != null) {
            loadMoreListener.onLoadMore(page, total);
        }
    }

    /*
    * PUBLIC METHODS
    * */
    public void add(MESSAGE message, boolean scroll) {
        boolean isNewMessageToday = !isPreviousSameDate(0, message.getCreatedAt());
        if (isNewMessageToday) {
            items.add(0, new Wrapper<>(message.getCreatedAt()));
        }
        Wrapper<MESSAGE> element = new Wrapper<>(message);
        items.add(0, element);
        notifyItemRangeInserted(0, isNewMessageToday ? 2 : 1);
        if (layoutManager != null && scroll) {
            layoutManager.scrollToPosition(0);
        }
    }

    /**
     * Method for adding history.
     *
     * @param messages messages from history.
     * @param reverse  {@code true} if need to reverse messages before adding.
     */
    public void add(ArrayList<MESSAGE> messages, boolean reverse) {
        if (reverse) Collections.reverse(messages);

        if (!items.isEmpty()) {
            int lastItemPosition = items.size() - 1;
            Date lastItem = (Date) items.get(lastItemPosition).item;
            if (DateFormatter.isSameDay(messages.get(0).getCreatedAt(), lastItem)) {
                items.remove(lastItemPosition);
                notifyItemRemoved(lastItemPosition);
            }
        }

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

    public void delete(ArrayList<MESSAGE> messages) {
        for (MESSAGE message : messages) {
            int index = getMessagePositionById(message.getId());
            items.remove(index);
            notifyItemRemoved(index);
        }
        recountDateHeaders();
    }

    public void deleteByIds(String[] ids) {
        for (String id : ids) {
            int index = getMessagePositionById(id);
            items.remove(index);
            notifyItemRemoved(index);
        }
        recountDateHeaders();
    }

    public void clear() {
        items.clear();
    }

    public void enableSelectionMode(SelectionListener selectionListener) {
        if (selectionListener == null) {
            throw new IllegalArgumentException("SelectionListener must not be null. Use `disableSelectionMode()` if you want tp disable selection mode");
        } else {
            this.selectionListener = selectionListener;
        }
    }

    public void disableSelectionMode() {
        this.selectionListener = null;
        unselectAllItems();
    }

    @SuppressWarnings("unchecked")
    public ArrayList<MESSAGE> getSelectedMessages() {
        ArrayList<MESSAGE> selectedMessages = new ArrayList<>();
        for (Wrapper wrapper : items) {
            if (wrapper.item instanceof IMessage && wrapper.isSelected) {
                selectedMessages.add((MESSAGE) wrapper.item);
            }
        }
        return selectedMessages;
    }

    public void unselectAllItems() {
        for (int i = 0; i < items.size(); i++) {
            Wrapper wrapper = items.get(i);
            if (wrapper.isSelected) {
                wrapper.isSelected = false;
                notifyItemChanged(i);
            }
        }
        isSelectMode = false;
        selectedItemsCount = 0;
        notifySelectionChanged();
    }

    public void deleteSelectedMessages() {
        ArrayList<MESSAGE> selectedMessages = getSelectedMessages();
        delete(selectedMessages);
        unselectAllItems();
    }

    public void setOnClickListener(OnClickListener<MESSAGE> onClickListener) {
        this.onClickListener = onClickListener;
    }

    public void setOnLongClickListener(OnLongClickListener<MESSAGE> onLongClickListener) {
        this.onLongClickListener = onLongClickListener;
    }

    public void setLoadMoreListener(OnLoadMoreListener loadMoreListener) {
        this.loadMoreListener = loadMoreListener;
    }

    public void setLayoutManager(RecyclerView.LayoutManager layoutManager) {
        this.layoutManager = layoutManager;
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
                if (!DateFormatter.isSameDay(message.getCreatedAt(), nextMessage.getCreatedAt())) {
                    this.items.add(new Wrapper<>(message.getCreatedAt()));
                }
            } else {
                this.items.add(new Wrapper<>(message.getCreatedAt()));
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
            return DateFormatter.isSameDay(dateToCompare, previousPositionDate);
        } else return false;
    }

    @SuppressWarnings("unchecked")
    private boolean isPreviousSameAuthor(String id, int position) {
        int prevPosition = position + 1;
        if (items.size() <= prevPosition) return false;

        if (items.get(prevPosition).item instanceof IMessage) {
            return ((MESSAGE) items.get(prevPosition).item).getUser().getId().contentEquals(id);
        } else return false;
    }

    private void incrementSelectedItemsCount() {
        selectedItemsCount++;
        notifySelectionChanged();
    }

    private void decrementSelectedItemsCount() {
        selectedItemsCount--;
        isSelectMode = selectedItemsCount > 0;

        notifySelectionChanged();
    }

    private void notifySelectionChanged() {
        if (selectionListener != null) {
            selectionListener.onSelectionChanged(selectedItemsCount);
        }
    }

    private void notifyMessageClicked(MESSAGE message) {
        if (onClickListener != null) {
            onClickListener.onMessageClick(message);
        }
    }

    private void notifyMessageLongClicked(MESSAGE message) {
        if (onLongClickListener != null) {
            onLongClickListener.onMessageLongClick(message);
        }
    }

    private View.OnClickListener getMessageClickListener(final Wrapper<MESSAGE> wrapper) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (selectionListener != null && isSelectMode) {
                    wrapper.isSelected = !wrapper.isSelected;

                    if (wrapper.isSelected) incrementSelectedItemsCount();
                    else decrementSelectedItemsCount();

                    MESSAGE message = (wrapper.item);
                    notifyItemChanged(getMessagePositionById(message.getId()));
                } else {
                    notifyMessageClicked(wrapper.item);
                }
            }
        };
    }

    private View.OnLongClickListener getMessageLongClickListener(final Wrapper<MESSAGE> wrapper) {
        return new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                if (selectionListener == null) {
                    notifyMessageLongClicked(wrapper.item);
                    return true;
                } else {
                    isSelectMode = true;
                    view.callOnClick();
                    return true;
                }
            }
        };
    }

    public void setStyle(MessagesListStyle style) {
        this.messagesListStyle = style;
    }

    /*
    * WRAPPER
    * */
    private class Wrapper<DATA> {
        private DATA item;
        boolean isSelected;

        Wrapper(DATA item) {
            this.item = item;
        }
    }

    /*
    * LISTENERS
    * */
    public interface OnLoadMoreListener {
        void onLoadMore(int page, int totalItemsCount);
    }

    public interface SelectionListener {
        void onSelectionChanged(int count);
    }

    public interface OnClickListener<MESSAGE extends IMessage> {
        void onMessageClick(MESSAGE message);
    }

    public interface OnLongClickListener<MESSAGE extends IMessage> {
        void onMessageLongClick(MESSAGE message);
    }

    /*
    * HOLDERS CONFIG
    * */
    public static class HoldersConfig {

        private Class<? extends MessageViewHolder<? extends IMessage>> incomingHolder;
        private
        @LayoutRes
        int incomingLayout;

        private Class<? extends MessageViewHolder<? extends IMessage>> outcomingHolder;
        private
        @LayoutRes
        int outcomingLayout;

        private Class<? extends ViewHolder<Date>> dateHeaderHolder;
        private
        @LayoutRes
        int dateHeaderLayout;

        public HoldersConfig() {
            this.incomingHolder = DefaultIncomingMessageViewHolder.class;
            this.incomingLayout = R.layout.item_incoming_message;

            this.outcomingHolder = DefaultOutcomingMessageViewHolder.class;
            this.outcomingLayout = R.layout.item_outcoming_message;

            this.dateHeaderHolder = DefaultDateHeaderViewHolder.class;
            this.dateHeaderLayout = R.layout.item_date_header;
        }

        public void setIncoming(Class<? extends MessageViewHolder<? extends IMessage>> holder, @LayoutRes int layout) {
            this.incomingHolder = holder;
            this.incomingLayout = layout;
        }

        public void setOutcoming(Class<? extends MessageViewHolder<? extends IMessage>> holder, @LayoutRes int layout) {
            this.outcomingHolder = holder;
            this.outcomingLayout = layout;
        }

        public void setDateHeader(Class<? extends ViewHolder<Date>> holder, @LayoutRes int layout) {
            this.dateHeaderHolder = holder;
            this.dateHeaderLayout = layout;
        }
    }

    /*
    * HOLDERS
    * */

    public static abstract class MessageViewHolder<MESSAGE extends IMessage> extends ViewHolder<MESSAGE> {

        private boolean isSelected;
        protected ImageLoader imageLoader;

        public MessageViewHolder(View itemView) {
            super(itemView);
        }

        public boolean isSelected() {
            return isSelected;
        }

        public void setSelected(boolean selected) {
            isSelected = selected;
        }

        public ImageLoader getImageLoader() {
            return imageLoader;
        }

        public void setImageLoader(ImageLoader imageLoader) {
            this.imageLoader = imageLoader;
        }
    }

    interface DefaultMessageViewHolder {
        void applyStyle(MessagesListStyle style);
    }

    public static class DefaultIncomingMessageViewHolder
            extends MessageViewHolder<IMessage> implements DefaultMessageViewHolder {

        private ViewGroup bubble;
        private TextView text;
        private TextView time;
        private ImageView userAvatar;

        public DefaultIncomingMessageViewHolder(View itemView) {
            super(itemView);
            bubble = (ViewGroup) itemView.findViewById(R.id.bubble);
            text = (TextView) itemView.findViewById(R.id.messageText);
            time = (TextView) itemView.findViewById(R.id.messageTime);
            userAvatar = (ImageView) itemView.findViewById(R.id.messageUserAvatar);
        }

        @Override
        public void onBind(IMessage message) {
            bubble.setSelected(isSelected());
            text.setText(message.getText());
            time.setText(DateFormatter.format(message.getCreatedAt(), DateFormatter.Template.TIME));

            boolean isAvatarExists = message.getUser().getAvatar() != null && !message.getUser().getAvatar().isEmpty();
            userAvatar.setVisibility(isAvatarExists ? View.VISIBLE : View.GONE);
            if (isAvatarExists && imageLoader != null) {
                imageLoader.loadImage(userAvatar, message.getUser().getAvatar());
            }
        }

        @Override
        public void applyStyle(MessagesListStyle style) {
            bubble.setPadding(style.getIncomingDefaultBubblePaddingLeft(),
                    style.getIncomingDefaultBubblePaddingTop(),
                    style.getIncomingDefaultBubblePaddingRight(),
                    style.getIncomingDefaultBubblePaddingBottom());
            bubble.setBackground(style.getIncomingBubbleDrawable());

            text.setTextColor(style.getIncomingTextColor());
            text.setTextSize(TypedValue.COMPLEX_UNIT_PX, style.getIncomingTextSize());

            userAvatar.getLayoutParams().width = style.getIncomingAvatarWidth();
            userAvatar.getLayoutParams().height = style.getIncomingAvatarHeight();

            time.setTextColor(style.getIncomingTimeTextColor());
            time.setTextSize(TypedValue.COMPLEX_UNIT_PX, style.getIncomingTimeTextSize());
        }
    }

    public static class DefaultOutcomingMessageViewHolder
            extends MessageViewHolder<IMessage> implements DefaultMessageViewHolder {

        private ViewGroup bubble;
        private TextView text;
        private TextView time;
        private ImageView userAvatar;

        public DefaultOutcomingMessageViewHolder(View itemView) {
            super(itemView);
            bubble = (ViewGroup) itemView.findViewById(R.id.bubble);
            text = (TextView) itemView.findViewById(R.id.messageText);
            time = (TextView) itemView.findViewById(R.id.messageTime);
            userAvatar = (ImageView) itemView.findViewById(R.id.messageUserAvatar);
        }

        @Override
        public void onBind(IMessage message) {
            bubble.setSelected(isSelected());
            text.setText(message.getText());
            time.setText(DateFormatter.format(message.getCreatedAt(), DateFormatter.Template.TIME));

            if (userAvatar != null) {
                boolean isAvatarExists = message.getUser().getAvatar() != null && !message.getUser().getAvatar().isEmpty();
                userAvatar.setVisibility(isAvatarExists ? View.VISIBLE : View.GONE);
                if (isAvatarExists && imageLoader != null) {
                    imageLoader.loadImage(userAvatar, message.getUser().getAvatar());
                }
            }
        }

        @Override
        public void applyStyle(MessagesListStyle style) {
            bubble.setPadding(style.getOutcomingDefaultBubblePaddingLeft(),
                    style.getOutcomingDefaultBubblePaddingTop(),
                    style.getOutcomingDefaultBubblePaddingRight(),
                    style.getOutcomingDefaultBubblePaddingBottom());
            bubble.setBackground(style.getOutcomingBubbleDrawable());

            text.setTextColor(style.getOutcomingTextColor());
            text.setTextSize(TypedValue.COMPLEX_UNIT_PX, style.getOutcomingTextSize());

            time.setTextColor(style.getOutcomingTimeTextColor());
            time.setTextSize(TypedValue.COMPLEX_UNIT_PX, style.getOutcomingTimeTextSize());
        }
    }

    public static class DefaultDateHeaderViewHolder extends ViewHolder<Date>
            implements DefaultMessageViewHolder {

        private TextView text;

        public DefaultDateHeaderViewHolder(View itemView) {
            super(itemView);
            text = (TextView) itemView.findViewById(R.id.messageText);
        }

        @Override
        public void onBind(Date date) {
            text.setText(DateFormatter.format(date, DateFormatter.Template.STRING_MONTH));
        }

        @Override
        public void applyStyle(MessagesListStyle style) {
            text.setTextSize(TypedValue.COMPLEX_UNIT_PX, style.getDateHeaderTextSize());
            text.setTextColor(style.getDateHeaderTextColor());
            text.setPadding(style.getDateHeaderPadding(), style.getDateHeaderPadding(),
                    style.getDateHeaderPadding(), style.getDateHeaderPadding());
        }
    }
}
