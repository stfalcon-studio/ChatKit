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

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
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
import com.stfalcon.chatkit.commons.models.MessageContentType;
import com.stfalcon.chatkit.utils.DateFormatter;
import com.stfalcon.chatkit.utils.RoundedImageView;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * Adapter for {@link MessagesList}.
 */
public class MessagesListAdapter<MESSAGE extends IMessage>
        extends RecyclerView.Adapter<ViewHolder>
        implements RecyclerScrollMoreListener.OnLoadMoreListener {

    private static final int VIEW_TYPE_DATE_HEADER = 0x00;
    private static final int VIEW_TYPE_TEXT_MESSAGE = 0x01;
    private static final int VIEW_TYPE_IMAGE_MESSAGE = 0x02;
    private static final int VIEW_TYPE_FILE_MESSAGE = 0x03;

    private HoldersConfig holders;
    private String senderId;
    private List<Wrapper> items;

    private int selectedItemsCount;
    private boolean isSelectMode;
    private SelectionListener selectionListener;

    private OnLoadMoreListener loadMoreListener;
    private OnMessageClickListener<MESSAGE> onMessageClickListener;
    private OnMessageLongClickListener<MESSAGE> onMessageLongClickListener;
    private ImageLoader imageLoader;
    private RecyclerView.LayoutManager layoutManager;
    private MessagesListStyle messagesListStyle;
    private DateFormatter.Formatter dateHeadersFormatter;

    /**
     * For default list item layout and view holder.
     *
     * @param senderId    identifier of sender.
     * @param imageLoader image loading method.
     */
    public MessagesListAdapter(String senderId, ImageLoader imageLoader) {
        this(senderId, new HoldersConfig(), imageLoader);
    }

    /**
     * For default list item layout and view holder.
     *
     * @param senderId    identifier of sender.
     * @param holders     custom layouts and view holders. See {@link HoldersConfig} documentation for details
     * @param imageLoader image loading method.
     */
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
            case VIEW_TYPE_DATE_HEADER:
                return getHolder(parent, holders.dateHeaderLayout, holders.dateHeaderHolder);
            case VIEW_TYPE_TEXT_MESSAGE:
                return getHolder(parent, holders.incomingTextLayout, holders.incomingTextHolder);
            case -VIEW_TYPE_TEXT_MESSAGE:
                return getHolder(parent, holders.outcomingTextLayout, holders.outcomingTextHolder);
            case VIEW_TYPE_IMAGE_MESSAGE:
                return getHolder(parent, holders.incomingImageLayout, holders.incomingImageHolder);
            case -VIEW_TYPE_IMAGE_MESSAGE:
                return getHolder(parent, holders.outcomingImageLayout, holders.outcomingImageHolder);
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Wrapper wrapper = items.get(position);

        if (wrapper.item instanceof IMessage) {
            ((BaseMessageViewHolder) holder).isSelected = wrapper.isSelected;
            ((BaseMessageViewHolder) holder).imageLoader = this.imageLoader;
            holder.itemView.setOnLongClickListener(getMessageLongClickListener(wrapper));
            holder.itemView.setOnClickListener(getMessageClickListener(wrapper));
        } else if (wrapper.item instanceof Date) {
            ((DefaultDateHeaderViewHolder) holder).dateHeadersFormatter = dateHeadersFormatter;
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
        boolean isOutcoming = false;
        int viewType;

        if (wrapper.item instanceof IMessage) {
            IMessage message = (IMessage) wrapper.item;
            isOutcoming = message.getUser().getId().contentEquals(senderId);

            if (message instanceof MessageContentType) viewType = getMediaViewType(message);
            else viewType = VIEW_TYPE_TEXT_MESSAGE;
        } else {
            viewType = VIEW_TYPE_DATE_HEADER;
        }

        return isOutcoming ? viewType * -1 : viewType;
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

    /**
     * Adds message to bottom of list and scroll if needed.
     *
     * @param message message to add.
     * @param scroll  {@code true} if need to scroll list to bottom when message added.
     */
    public void addToStart(MESSAGE message, boolean scroll) {
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
     * Adds messages list in chronological order. Use this method to add history.
     *
     * @param messages messages from history.
     * @param reverse  {@code true} if need to reverse messages before adding.
     */
    public void addToEnd(List<MESSAGE> messages, boolean reverse) {
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

    /**
     * Updates message by its id.
     *
     * @param message updated message object.
     */
    public void update(MESSAGE message) {
        update(message.getId(), message);
    }

    /**
     * Updates message by old identifier (use this method if id has changed). Otherwise use {@link #update(IMessage)}
     *
     * @param oldId      an identifier of message to update.
     * @param newMessage new message object.
     */
    public void update(String oldId, MESSAGE newMessage) {
        int position = getMessagePositionById(oldId);
        if (position >= 0) {
            Wrapper<MESSAGE> element = new Wrapper<>(newMessage);
            items.set(position, element);
            notifyItemChanged(position);
        }
    }

    /**
     * Deletes message.
     *
     * @param message message to delete.
     */
    public void delete(MESSAGE message) {
        deleteById(message.getId());
    }

    /**
     * Deletes messages list.
     *
     * @param messages messages list to delete.
     */
    public void delete(List<MESSAGE> messages) {
        for (MESSAGE message : messages) {
            int index = getMessagePositionById(message.getId());
            items.remove(index);
            notifyItemRemoved(index);
        }
        recountDateHeaders();
    }

    /**
     * Deletes message by its identifier.
     *
     * @param id identifier of message to delete.
     */
    public void deleteById(String id) {
        int index = getMessagePositionById(id);
        if (index >= 0) {
            items.remove(index);
            notifyItemRemoved(index);
            recountDateHeaders();
        }
    }

    /**
     * Deletes messages by its identifiers.
     *
     * @param ids array of identifiers of messages to delete.
     */
    public void deleteByIds(String[] ids) {
        for (String id : ids) {
            int index = getMessagePositionById(id);
            items.remove(index);
            notifyItemRemoved(index);
        }
        recountDateHeaders();
    }

    /**
     * Returns {@code true} if, and only if, messages count in adapter is non-zero.
     *
     * @return {@code true} if size is 0, otherwise {@code false}
     */
    public boolean isEmpty() {
        return items.isEmpty();
    }

    /**
     * Clears the messages list.
     */
    public void clear() {
        items.clear();
    }

    /**
     * Enables selection mode.
     *
     * @param selectionListener listener for selected items count. To get selected messages use {@link #getSelectedMessages()}.
     */
    public void enableSelectionMode(SelectionListener selectionListener) {
        if (selectionListener == null) {
            throw new IllegalArgumentException("SelectionListener must not be null. Use `disableSelectionMode()` if you want tp disable selection mode");
        } else {
            this.selectionListener = selectionListener;
        }
    }

    /**
     * Disables selection mode and removes {@link SelectionListener}.
     */
    public void disableSelectionMode() {
        this.selectionListener = null;
        unselectAllItems();
    }

    /**
     * Returns the list of selected messages.
     *
     * @return list of selected messages. Empty list if nothing was selected or selection mode is disabled.
     */
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

    /**
     * Returns selected messages text and do {@link #unselectAllItems()} for you.
     *
     * @param formatter The formatter that allows you to format your message model when copying.
     * @param reverse   Change ordering when copying messages.
     * @return formatted text by {@link Formatter}. If it's {@code null} - {@code MESSAGE#toString()} will be used.
     */
    public String getSelectedMessagesText(Formatter<MESSAGE> formatter, boolean reverse) {
        String copiedText = getSelectedText(formatter, reverse);
        unselectAllItems();
        return copiedText;
    }

    /**
     * Copies text to device clipboard and returns selected messages text. Also it does {@link #unselectAllItems()} for you.
     *
     * @param context   The context.
     * @param formatter The formatter that allows you to format your message model when copying.
     * @param reverse   Change ordering when copying messages.
     * @return formatted text by {@link Formatter}. If it's {@code null} - {@code MESSAGE#toString()} will be used.
     */
    public String copySelectedMessagesText(Context context, Formatter<MESSAGE> formatter, boolean reverse) {
        String copiedText = getSelectedText(formatter, reverse);
        copyToClipboard(context, copiedText);
        unselectAllItems();
        return copiedText;
    }

    /**
     * Unselect all of the selected messages. Notifies {@link SelectionListener} with zero count.
     */
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

    /**
     * Deletes all of the selected messages and disables selection mode.
     * Call {@link #getSelectedMessages()} before calling this method to delete messages from your data source.
     */
    public void deleteSelectedMessages() {
        List<MESSAGE> selectedMessages = getSelectedMessages();
        delete(selectedMessages);
        unselectAllItems();
    }

    /**
     * Sets click listener for item. Fires ONLY if list is not in selection mode.
     *
     * @param onMessageClickListener click listener.
     */
    public void setOnMessageClickListener(OnMessageClickListener<MESSAGE> onMessageClickListener) {
        this.onMessageClickListener = onMessageClickListener;
    }

    /**
     * Sets long click listener for item. Fires only if selection mode is disabled.
     *
     * @param onMessageLongClickListener long click listener.
     */
    public void setOnMessageLongClickListener(OnMessageLongClickListener<MESSAGE> onMessageLongClickListener) {
        this.onMessageLongClickListener = onMessageLongClickListener;
    }

    /**
     * Set callback to be invoked when list scrolled to top.
     *
     * @param loadMoreListener listener.
     */
    public void setLoadMoreListener(OnLoadMoreListener loadMoreListener) {
        this.loadMoreListener = loadMoreListener;
    }

    /**
     * Sets custom {@link DateFormatter.Formatter} for text representation of date headers.
     */
    public void setDateHeadersFormatter(DateFormatter.Formatter dateHeadersFormatter) {
        this.dateHeadersFormatter = dateHeadersFormatter;
    }

    /*
    * PRIVATE METHODS
    * */
    private <HOLDER extends ViewHolder>
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

    private int getMediaViewType(IMessage message) {
        if (message instanceof MessageContentType.Image
                && ((MessageContentType.Image) message).getImageUrl() != null) {
            return VIEW_TYPE_IMAGE_MESSAGE;
        }
        if (message instanceof MessageContentType.File
                && ((MessageContentType.File) message).getFileUrl() != null) {
            return VIEW_TYPE_FILE_MESSAGE;
        }
        // TODO: 28.03.17 custom types map
        return VIEW_TYPE_TEXT_MESSAGE;
    }

    private void recountDateHeaders() {
        List<Integer> indicesToDelete = new ArrayList<>();

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

    private void generateDateHeaders(List<MESSAGE> messages) {
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
        if (onMessageClickListener != null) {
            onMessageClickListener.onMessageClick(message);
        }
    }

    private void notifyMessageLongClicked(MESSAGE message) {
        if (onMessageLongClickListener != null) {
            onMessageLongClickListener.onMessageLongClick(message);
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

    private String getSelectedText(Formatter<MESSAGE> formatter, boolean reverse) {
        StringBuilder builder = new StringBuilder();

        ArrayList<MESSAGE> selectedMessages = getSelectedMessages();
        if (reverse) Collections.reverse(selectedMessages);

        for (MESSAGE message : selectedMessages) {
            builder.append(formatter == null
                    ? message.toString()
                    : formatter.format(message));
            builder.append("\n\n");
        }
        builder.replace(builder.length() - 2, builder.length(), "");

        return builder.toString();
    }

    private void copyToClipboard(Context context, String copiedText) {
        ClipboardManager clipboard = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText(copiedText, copiedText);
        clipboard.setPrimaryClip(clip);
    }

    void setLayoutManager(RecyclerView.LayoutManager layoutManager) {
        this.layoutManager = layoutManager;
    }

    void setStyle(MessagesListStyle style) {
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

    /**
     * Interface definition for a callback to be invoked when next part of messages need to be loaded.
     */
    public interface OnLoadMoreListener {

        /**
         * Fires when user scrolled to the end of list.
         *
         * @param page            next page to download.
         * @param totalItemsCount current items count.
         */
        void onLoadMore(int page, int totalItemsCount);
    }

    /**
     * Interface definition for a callback to be invoked when selected messages count is changed.
     */
    public interface SelectionListener {

        /**
         * Fires when selected items count is changed.
         *
         * @param count count of selected items.
         */
        void onSelectionChanged(int count);
    }

    /**
     * Interface definition for a callback to be invoked when message item is clicked.
     */
    public interface OnMessageClickListener<MESSAGE extends IMessage> {

        /**
         * Fires when message was clicked.
         *
         * @param message clicked message.
         */
        void onMessageClick(MESSAGE message);
    }

    /**
     * Interface definition for a callback to be invoked when message item is long clicked.
     */
    public interface OnMessageLongClickListener<MESSAGE extends IMessage> {

        /**
         * Fires when message was long clicked.
         *
         * @param message clicked message.
         */
        void onMessageLongClick(MESSAGE message);
    }

    /**
     * Interface used to format your message model when copying.
     */
    public interface Formatter<MESSAGE> {

        /**
         * Formats an string representation of the message object.
         *
         * @param message The object that should be formatted.
         * @return Formatted text.
         */
        String format(MESSAGE message);
    }

    /*
    * HOLDERS CONFIG
    * */

    /**
     * Configuration object for passing custom layouts and view holders into adapter.
     * You need to pass it into {@link MessagesListAdapter#MessagesListAdapter(String, HoldersConfig, ImageLoader)} to apply your changes.
     */
    public static class HoldersConfig {

        private Class<? extends BaseMessageViewHolder<? extends IMessage>> incomingTextHolder;
        private int incomingTextLayout;

        private Class<? extends BaseMessageViewHolder<? extends IMessage>> outcomingTextHolder;
        private int outcomingTextLayout;

        private Class<? extends BaseMessageViewHolder<? extends IMessage>> incomingImageHolder;
        private int incomingImageLayout;

        private Class<? extends BaseMessageViewHolder<? extends IMessage>> outcomingImageHolder;
        private int outcomingImageLayout;

        private Class<? extends ViewHolder<Date>> dateHeaderHolder;
        private int dateHeaderLayout;

        public HoldersConfig() {
            this.incomingTextHolder = DefaultIncomingTextMessageViewHolder.class;
            this.incomingTextLayout = R.layout.item_incoming_text_message;

            this.outcomingTextHolder = DefaultOutcomingTextMessageViewHolder.class;
            this.outcomingTextLayout = R.layout.item_outcoming_text_message;

            this.incomingImageHolder = DefaultIncomingImageMessageViewHolder.class;
            this.incomingImageLayout = R.layout.item_incoming_image_message;

            this.outcomingImageHolder = DefaultOutcomingImageMessageViewHolder.class;
            this.outcomingImageLayout = R.layout.item_outcoming_image_message;

            this.dateHeaderHolder = DefaultDateHeaderViewHolder.class;
            this.dateHeaderLayout = R.layout.item_date_header;
        }

        /**
         * Sets both of custom view holder class and layout resource for incoming text message.
         *
         * @param holder holder class.
         * @param layout layout resource.
         */
        public void setIncomingTextConfig(Class<? extends BaseMessageViewHolder<? extends IMessage>> holder, @LayoutRes int layout) {
            this.incomingTextHolder = holder;
            this.incomingTextLayout = layout;
        }

        /**
         * Sets custom view holder class for incoming text message.
         *
         * @param holder holder class.
         */
        public void setIncomingTextHolder(Class<? extends BaseMessageViewHolder<? extends IMessage>> holder) {
            this.incomingTextHolder = holder;
        }

        /**
         * Sets custom layout resource for incoming text message.
         *
         * @param layout layout resource.
         */
        public void setIncomingTextLayout(@LayoutRes int layout) {
            this.incomingTextLayout = layout;
        }

        /**
         * Sets both of custom view holder class and layout resource for outcoming text message.
         *
         * @param holder holder class.
         * @param layout layout resource.
         */
        public void setOutcomingTextConfig(Class<? extends BaseMessageViewHolder<? extends IMessage>> holder, @LayoutRes int layout) {
            this.outcomingTextHolder = holder;
            this.outcomingTextLayout = layout;
        }

        /**
         * Sets custom view holder class for outcoming text message.
         *
         * @param holder holder class.
         */
        public void setOutcomingTextHolder(Class<? extends BaseMessageViewHolder<? extends IMessage>> holder) {
            this.outcomingTextHolder = holder;
        }

        /**
         * Sets custom layout resource for outcoming text message.
         *
         * @param layout layout resource.
         */
        public void setOutcomingTextLayout(@LayoutRes int layout) {
            this.outcomingTextLayout = layout;
        }

        /**
         * Sets both of custom view holder class and layout resource for incoming image message.
         *
         * @param holder holder class.
         * @param layout layout resource.
         */
        public void setIncomingImageConfig(Class<? extends BaseMessageViewHolder<? extends MessageContentType.Image>> holder, @LayoutRes int layout) {
            this.incomingImageHolder = holder;
            this.incomingImageLayout = layout;
        }

        /**
         * Sets custom view holder class for incoming image message.
         *
         * @param holder holder class.
         */
        public void setIncomingImageHolder(Class<? extends BaseMessageViewHolder<? extends MessageContentType.Image>> holder) {
            this.incomingImageHolder = holder;
        }

        /**
         * Sets custom layout resource for incoming image message.
         *
         * @param layout layout resource.
         */
        public void setIncomingImageLayout(@LayoutRes int layout) {
            this.incomingImageLayout = layout;
        }

        /**
         * Sets both of custom view holder class and layout resource for outcoming image message.
         *
         * @param holder holder class.
         * @param layout layout resource.
         */
        public void setOutcomingImageConfig(Class<? extends BaseMessageViewHolder<? extends MessageContentType.Image>> holder, @LayoutRes int layout) {
            this.outcomingImageHolder = holder;
            this.outcomingImageLayout = layout;
        }

        /**
         * Sets custom view holder class for outcoming image message.
         *
         * @param holder holder class.
         */
        public void setOutcomingImageHolder(Class<? extends BaseMessageViewHolder<? extends MessageContentType.Image>> holder) {
            this.outcomingImageHolder = holder;
        }

        /**
         * Sets custom layout resource for outcoming image message.
         *
         * @param layout layout resource.
         */
        public void setOutcomingImageLayout(@LayoutRes int layout) {
            this.outcomingImageLayout = layout;
        }

        /**
         * Sets both of custom view holder class and layout resource for date header.
         *
         * @param holder holder class.
         * @param layout layout resource.
         */
        public void setDateHeaderConfig(Class<? extends ViewHolder<Date>> holder, @LayoutRes int layout) {
            this.dateHeaderHolder = holder;
            this.dateHeaderLayout = layout;
        }

        /**
         * Sets custom view holder class for date header.
         *
         * @param holder holder class.
         */
        public void setDateHeaderHolder(Class<? extends ViewHolder<Date>> holder) {
            this.dateHeaderHolder = holder;
        }

        /**
         * Sets custom layout reource for date header.
         *
         * @param layout layout resource.
         */
        public void setDateHeaderLayout(@LayoutRes int layout) {
            this.dateHeaderLayout = layout;
        }




        /*
        * FIXME: DEPRECATED
        * */

        /**
         * This method is deprecated. Use {@link #setIncomingTextConfig(Class, int)} instead.
         *
         * @param holder holder class.
         * @param layout layout resource.
         */
        @Deprecated
        public void setIncoming(Class<? extends BaseMessageViewHolder<? extends IMessage>> holder, @LayoutRes int layout) {
            this.incomingTextHolder = holder;
            this.incomingTextLayout = layout;
        }

        /**
         * This method is deprecated. Use {@link #setIncomingTextHolder(Class)} instead.
         *
         * @param holder holder class.
         */
        @Deprecated
        public void setIncomingHolder(Class<? extends BaseMessageViewHolder<? extends IMessage>> holder) {
            this.incomingTextHolder = holder;
        }

        /**
         * This method is deprecated. Use {@link #setIncomingTextLayout(int)} instead.
         *
         * @param layout layout resource.
         */
        @Deprecated
        public void setIncomingLayout(@LayoutRes int layout) {
            this.incomingTextLayout = layout;
        }

        /**
         * This method is deprecated. Use {@link #setOutcomingTextConfig(Class, int)} instead.
         *
         * @param holder holder class.
         * @param layout layout resource.
         */
        @Deprecated
        public void setOutcoming(Class<? extends BaseMessageViewHolder<? extends IMessage>> holder, @LayoutRes int layout) {
            this.outcomingTextHolder = holder;
            this.outcomingTextLayout = layout;
        }

        /**
         * This method is deprecated. Use {@link #setOutcomingTextHolder(Class)} instead.
         *
         * @param holder holder class.
         */
        @Deprecated
        public void setOutcomingHolder(Class<? extends BaseMessageViewHolder<? extends IMessage>> holder) {
            this.outcomingTextHolder = holder;
        }

        /**
         * This method is deprecated. Use {@link #setOutcomingTextLayout(int)} instead.
         *
         * @param layout layout resource.
         */
        @Deprecated
        public void setOutcomingLayout(@LayoutRes int layout) {
            this.outcomingTextLayout = layout;
        }

        /**
         * This method is deprecated. Use {@link #setDateHeaderConfig(Class, int)} instead.
         *
         * @param holder holder class.
         * @param layout layout resource.
         */
        @Deprecated
        public void setDateHeader(Class<? extends ViewHolder<Date>> holder, @LayoutRes int layout) {
            this.dateHeaderHolder = holder;
            this.dateHeaderLayout = layout;
        }
    }

    /*
    * HOLDERS
    * */

    /**
     * The base class for view holders for incoming and outcoming message.
     * You can extend it to create your own holder in conjuction with custom layout or even using default layout.
     */
    public static abstract class BaseMessageViewHolder<MESSAGE extends IMessage> extends ViewHolder<MESSAGE> {

        private boolean isSelected;

        /**
         * Callback for implementing images loading in message list
         */
        protected ImageLoader imageLoader;

        public BaseMessageViewHolder(View itemView) {
            super(itemView);
        }

        /**
         * Make message unselected
         *
         * @return weather is item selected.
         */
        public boolean isSelected() {
            return isSelected;
        }

        /**
         * Getter for {@link #imageLoader}
         *
         * @return image loader interface.
         */
        public ImageLoader getImageLoader() {
            return imageLoader;
        }

    }

    /**
     * Default view holder implementation for incoming text message
     */
    public static class IncomingTextMessageViewHolder<MESSAGE extends IMessage>
            extends BaseIncomingMessageViewHolder<MESSAGE> implements DefaultMessageViewHolder {

        protected ViewGroup bubble;
        protected TextView text;

        public IncomingTextMessageViewHolder(View itemView) {
            super(itemView);
            bubble = (ViewGroup) itemView.findViewById(R.id.bubble);
            text = (TextView) itemView.findViewById(R.id.messageText);
        }

        @Override
        public void onBind(MESSAGE message) {
            super.onBind(message);
            bubble.setSelected(isSelected());
            text.setText(message.getText());
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
            text.setTypeface(text.getTypeface(), style.getIncomingTextStyle());
            text.setAutoLinkMask(style.getTextAutoLinkMask());
            text.setLinkTextColor(style.getIncomingTextLinkColor());

            userAvatar.getLayoutParams().width = style.getIncomingAvatarWidth();
            userAvatar.getLayoutParams().height = style.getIncomingAvatarHeight();

            time.setTextColor(style.getIncomingTimeTextColor());
            time.setTextSize(TypedValue.COMPLEX_UNIT_PX, style.getIncomingTimeTextSize());
            time.setTypeface(time.getTypeface(), style.getIncomingTimeTextStyle());
        }
    }

    /**
     * Default view holder implementation for outcoming text message
     */
    public static class OutcomingTextMessageViewHolder<MESSAGE extends IMessage>
            extends BaseMessageViewHolder<MESSAGE> implements DefaultMessageViewHolder {

        protected ViewGroup bubble;
        protected TextView text;
        protected TextView time;
        protected ImageView userAvatar;

        public OutcomingTextMessageViewHolder(View itemView) {
            super(itemView);
            bubble = (ViewGroup) itemView.findViewById(R.id.bubble);
            text = (TextView) itemView.findViewById(R.id.messageText);
            time = (TextView) itemView.findViewById(R.id.messageTime);
            userAvatar = (ImageView) itemView.findViewById(R.id.messageUserAvatar);
        }

        @Override
        public void onBind(MESSAGE message) {
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
            text.setTypeface(text.getTypeface(), style.getOutcomingTextStyle());
            text.setAutoLinkMask(style.getTextAutoLinkMask());
            text.setLinkTextColor(style.getOutcomingTextLinkColor());
            text.setLinkTextColor(style.getOutcomingTextLinkColor());

            time.setTextColor(style.getOutcomingTimeTextColor());
            time.setTextSize(TypedValue.COMPLEX_UNIT_PX, style.getOutcomingTimeTextSize());
            time.setTypeface(time.getTypeface(), style.getOutcomingTimeTextStyle());
        }
    }

    /**
     * Default view holder implementation for incoming image message
     */
    public static class IncomingImageMessageViewHolder<MESSAGE extends MessageContentType.Image>
            extends BaseIncomingMessageViewHolder<MESSAGE> {

        protected ImageView image;

        public IncomingImageMessageViewHolder(View itemView) {
            super(itemView);
            image = (ImageView) itemView.findViewById(R.id.image);

            if (image instanceof RoundedImageView) {
                ((RoundedImageView) image).setCorners(
                        R.dimen.message_bubble_corners_radius,
                        R.dimen.message_bubble_corners_radius,
                        R.dimen.message_bubble_corners_radius,
                        0
                );
            }
        }

        @Override
        public void onBind(MESSAGE message) {
            super.onBind(message);
            imageLoader.loadImage(image, message.getImageUrl());
        }
    }

    /**
     * Default view holder implementation for outcoming image message
     */
    public static class OutcomingImageMessageViewHolder<MESSAGE extends MessageContentType.Image>
            extends BaseMessageViewHolder<MESSAGE> {

        protected ImageView image;
        protected TextView time;

        public OutcomingImageMessageViewHolder(View itemView) {
            super(itemView);
            image = (ImageView) itemView.findViewById(R.id.image);
            time = (TextView) itemView.findViewById(R.id.messageTime);

            if (image instanceof RoundedImageView) {
                ((RoundedImageView) image).setCorners(
                        R.dimen.message_bubble_corners_radius,
                        R.dimen.message_bubble_corners_radius,
                        0,
                        R.dimen.message_bubble_corners_radius
                );
            }
        }

        @Override
        public void onBind(MESSAGE message) {
            imageLoader.loadImage(image, message.getImageUrl());
            time.setText(DateFormatter.format(message.getCreatedAt(), DateFormatter.Template.TIME));
        }
    }

    /**
     * Default view holder implementation for date header
     */
    public static class DefaultDateHeaderViewHolder extends ViewHolder<Date>
            implements DefaultMessageViewHolder {

        protected TextView text;
        protected String dateFormat;
        protected DateFormatter.Formatter dateHeadersFormatter;

        public DefaultDateHeaderViewHolder(View itemView) {
            super(itemView);
            text = (TextView) itemView.findViewById(R.id.messageText);
        }

        @Override
        public void onBind(Date date) {
            String formattedDate = null;
            if (dateHeadersFormatter != null) formattedDate = dateHeadersFormatter.format(date);
            text.setText(formattedDate == null ? DateFormatter.format(date, dateFormat) : formattedDate);
        }

        @Override
        public void applyStyle(MessagesListStyle style) {
            text.setTextColor(style.getDateHeaderTextColor());
            text.setTextSize(TypedValue.COMPLEX_UNIT_PX, style.getDateHeaderTextSize());
            text.setTypeface(text.getTypeface(), style.getDateHeaderTextStyle());
            text.setPadding(style.getDateHeaderPadding(), style.getDateHeaderPadding(),
                    style.getDateHeaderPadding(), style.getDateHeaderPadding());
            dateFormat = style.getDateHeaderFormat();
            dateFormat = dateFormat == null ? DateFormatter.Template.STRING_DAY_MONTH_YEAR.get() : dateFormat;
        }
    }

    interface DefaultMessageViewHolder {
        void applyStyle(MessagesListStyle style);
    }

    private abstract static class BaseIncomingMessageViewHolder<MESSAGE extends IMessage>
            extends BaseMessageViewHolder<MESSAGE> {

        protected TextView time;
        protected ImageView userAvatar;

        public BaseIncomingMessageViewHolder(View itemView) {
            super(itemView);
            time = (TextView) itemView.findViewById(R.id.messageTime);
            userAvatar = (ImageView) itemView.findViewById(R.id.messageUserAvatar);
        }

        @Override
        public void onBind(MESSAGE message) {
            time.setText(DateFormatter.format(message.getCreatedAt(), DateFormatter.Template.TIME));

            boolean isAvatarExists = imageLoader != null && message.getUser().getAvatar() != null && !message.getUser().getAvatar().isEmpty();
            userAvatar.setVisibility(isAvatarExists ? View.VISIBLE : View.GONE);
            if (isAvatarExists) {
                imageLoader.loadImage(userAvatar, message.getUser().getAvatar());
            }
        }
    }

    private static class DefaultIncomingTextMessageViewHolder
            extends IncomingTextMessageViewHolder<IMessage> {

        public DefaultIncomingTextMessageViewHolder(View itemView) {
            super(itemView);
        }
    }

    private static class DefaultOutcomingTextMessageViewHolder
            extends OutcomingTextMessageViewHolder<IMessage> {

        public DefaultOutcomingTextMessageViewHolder(View itemView) {
            super(itemView);
        }
    }

    private static class DefaultIncomingImageMessageViewHolder
            extends IncomingImageMessageViewHolder<MessageContentType.Image> {

        public DefaultIncomingImageMessageViewHolder(View itemView) {
            super(itemView);
        }
    }

    private static class DefaultOutcomingImageMessageViewHolder
            extends OutcomingImageMessageViewHolder<MessageContentType.Image> {

        public DefaultOutcomingImageMessageViewHolder(View itemView) {
            super(itemView);
        }
    }



    /*
    * FIXME: DEPRECATED
    * */

    /**
     * This class is deprecated. Use {@link IncomingTextMessageViewHolder(Class)} instead.
     */
    @Deprecated
    public static class IncomingMessageViewHolder<MESSAGE extends IMessage>
            extends IncomingTextMessageViewHolder<MESSAGE> implements DefaultMessageViewHolder {

        public IncomingMessageViewHolder(View itemView) {
            super(itemView);
        }
    }

    /**
     * This class is deprecated. Use {@link OutcomingTextMessageViewHolder(Class)} instead.
     */
    @Deprecated
    public static class OutcomingMessageViewHolder<MESSAGE extends IMessage>
            extends OutcomingTextMessageViewHolder<MESSAGE> {

        public OutcomingMessageViewHolder(View itemView) {
            super(itemView);
        }
    }
}
