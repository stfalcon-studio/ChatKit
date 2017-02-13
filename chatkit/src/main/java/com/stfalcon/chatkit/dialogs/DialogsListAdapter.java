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

package com.stfalcon.chatkit.dialogs;

import android.graphics.drawable.GradientDrawable;
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
import com.stfalcon.chatkit.commons.models.IDialog;
import com.stfalcon.chatkit.commons.models.IMessage;
import com.stfalcon.chatkit.utils.DateFormatter;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

/**
 * Adapter for {@link DialogsList}
 */
public class DialogsListAdapter<DIALOG extends IDialog>
        extends RecyclerView.Adapter<DialogsListAdapter.BaseDialogViewHolder> {

    private List<DIALOG> items = new ArrayList<>();
    private int itemLayoutId;
    private Class<? extends BaseDialogViewHolder> holderClass;
    private ImageLoader imageLoader;
    private BaseDialogViewHolder.OnItemClickListener<DIALOG> onItemClickListener;
    private BaseDialogViewHolder.OnLongItemClickListener<DIALOG> onLongItemClickListener;
    private DialogListStyle dialogStyle;

    /**
     * For default list item layout and view holder
     *
     * @param imageLoader image loading method
     */
    public DialogsListAdapter(ImageLoader imageLoader) {
        this(R.layout.item_dialog, DialogViewHolder.class, imageLoader);
    }

    /**
     * For custom list item layout and default view holder
     *
     * @param itemLayoutId custom list item resource id
     * @param imageLoader  image loading method
     */
    public DialogsListAdapter(@LayoutRes int itemLayoutId, ImageLoader imageLoader) {
        this(itemLayoutId, DialogViewHolder.class, imageLoader);
    }

    /**
     * For custom list item layout and custom view holder
     *
     * @param itemLayoutId custom list item resource id
     * @param holderClass  custom view holder class
     * @param imageLoader  image loading method
     */
    public DialogsListAdapter(@LayoutRes int itemLayoutId, Class<? extends BaseDialogViewHolder> holderClass,
                              ImageLoader imageLoader) {
        this.itemLayoutId = itemLayoutId;
        this.holderClass = holderClass;
        this.imageLoader = imageLoader;
    }

    @SuppressWarnings("unchecked")
    @Override
    public void onBindViewHolder(BaseDialogViewHolder holder, int position) {
        holder.setImageLoader(imageLoader);
        holder.setOnItemClickListener(onItemClickListener);
        holder.setOnLongItemClickListener(onLongItemClickListener);
        holder.onBind(items.get(position));
    }

    @Override
    public BaseDialogViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(itemLayoutId, parent, false);
        //create view holder by reflation
        try {
            Constructor<? extends BaseDialogViewHolder> constructor = holderClass.getDeclaredConstructor(View.class);
            constructor.setAccessible(true);
            BaseDialogViewHolder baseDialogViewHolder = constructor.newInstance(v);
            if (baseDialogViewHolder instanceof DialogViewHolder) {
                ((DialogViewHolder) baseDialogViewHolder).setDialogStyle(dialogStyle);
            }
            return baseDialogViewHolder;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @return size of dialogs list
     */
    @Override
    public int getItemCount() {
        return items.size();
    }

    /**
     * remove item with id
     * @param id dialog i
     */
    public void deleteById(String id) {
        for (int i = 0; i < items.size(); i++) {
            if (items.get(i).getId().equals(id)) {
                items.remove(i);
                notifyItemRemoved(i);
            }
        }
    }

    /**
     * clear dialogs list
     */
    public void clear() {
        if (items != null) {
            items.clear();
        }
        notifyDataSetChanged();
    }

    /**
     * Set dialogs list
     *
     * @param items dialogs list
     */
    public void setItems(List<DIALOG> items) {
        this.items = items;
        notifyDataSetChanged();
    }

    /**
     * Add dialogs items
     *
     * @param newItems new dialogs list
     */
    public void addItems(List<DIALOG> newItems) {
        if (newItems != null) {
            if (items == null) {
                items = new ArrayList<>();
            }
            int curSize = items.size();
            items.addAll(newItems);
            notifyItemRangeInserted(curSize, items.size());
        }
    }

    /**
     * Add dialog to the end of dialogs list
     *
     * @param dialog dialog item
     */
    public void addItem(DIALOG dialog) {
        items.add(dialog);
        notifyItemInserted(0);
    }

    /**
     * Add dialog to dialogs list
     *
     * @param dialog dialog item
     * @param position position in dialogs lost
     */
    public void addItem(int position, DIALOG dialog) {
        items.add(position, dialog);
        notifyItemInserted(position);
    }

    /**
     * Update dialog by position in dialogs list
     *
     * @param position position in dialogs list
     * @param item     new dialog item
     */
    public void updateItem(int position, DIALOG item) {
        if (items == null) {
            items = new ArrayList<>();
        }
        items.set(position, item);
        notifyItemChanged(position);
    }

    /**
     * Update dialog by dialog id
     *
     * @param item new dialog item
     */
    public void updateItemById(DIALOG item) {
        if (items == null) {
            items = new ArrayList<>();
        }
        for (int i = 0; i < items.size(); i++) {
            if (items.get(i).getId().equals(item.getId())) {
                items.set(i, item);
                notifyItemChanged(i);
                break;
            }
        }
    }

    /**
     * Update last message in dialog and swap item to top of list.
     *
     * @param dialogId Dialog ID
     * @param message  New message
     * @return false if dialog doesn't exist.
     */
    public boolean updateDialogWithMessage(String dialogId, IMessage message) {
        boolean dialogExist = false;
        for (int i = 0; i < items.size(); i++) {
            if (items.get(i).getId().equals(dialogId)) {
                items.get(i).setLastMessage(message);
                notifyItemChanged(i);
                if (i != 0) {
                    Collections.swap(items, i, 0);
                    notifyItemMoved(i, 0);
                }
                dialogExist = true;
                break;
            }
        }
        return dialogExist;
    }

    /**
     * Register a callback to be invoked when image need to load.
     *
     * @param imageLoader image loading method
     */
    public void setImageLoader(ImageLoader imageLoader) {
        this.imageLoader = imageLoader;
    }

    /**
     * @return registered image loader
     */
    public ImageLoader getImageLoader() {
        return imageLoader;
    }

    /**
     * @return the on click callback registered for this view.
     */
    public BaseDialogViewHolder.OnItemClickListener getOnItemClickListener() {
        return onItemClickListener;
    }

    /**
     * Register a callback to be invoked when item is clicked.
     *
     * @param onItemClickListener on click item callback
     */
    public void setOnItemClickListener(BaseDialogViewHolder.OnItemClickListener<DIALOG> onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    /**
     * @return on long click item callback
     */
    public BaseDialogViewHolder.OnLongItemClickListener getOnLongItemClickListener() {
        return onLongItemClickListener;
    }

    /**
     * Register a callback to be invoked when item is long clicked.
     */
    public void setOnLongItemClickListener(BaseDialogViewHolder.OnLongItemClickListener<DIALOG> onLongItemClickListener) {
        this.onLongItemClickListener = onLongItemClickListener;
    }

    //TODO ability to set style programmatically
    void setStyle(DialogListStyle dialogStyle) {
        this.dialogStyle = dialogStyle;
    }

    /*
    * HOLDERS
    * */
    public abstract static class BaseDialogViewHolder<DIALOG extends IDialog> extends ViewHolder<DIALOG> {
        protected ImageLoader imageLoader;
        protected OnItemClickListener onItemClickListener;
        protected OnLongItemClickListener onLongItemClickListener;

        public BaseDialogViewHolder(View itemView) {
            super(itemView);
        }

        void setImageLoader(ImageLoader imageLoader) {
            this.imageLoader = imageLoader;
        }

        void setOnItemClickListener(OnItemClickListener onItemClickListener) {
            this.onItemClickListener = onItemClickListener;
        }

        void setOnLongItemClickListener(OnLongItemClickListener onLongItemClickListener) {
            this.onLongItemClickListener = onLongItemClickListener;
        }

        public interface OnItemClickListener<DIALOG extends IDialog> {
            void onItemClick(View view, DIALOG dialog);
        }

        public interface OnLongItemClickListener<DIALOG extends IDialog> {
            void onLongItemClick(View view, DIALOG dialog);
        }
    }

    public static class DialogViewHolder<DIALOG extends IDialog> extends BaseDialogViewHolder<DIALOG> {
        protected DialogListStyle dialogStyle;
        protected ViewGroup container;
        protected ViewGroup root;
        protected TextView tvName;
        protected TextView tvDate;
        protected ImageView ivAvatar;
        protected ImageView ivLastMessageUser;
        protected TextView tvLastMessage;
        protected TextView tvBubble;
        protected ViewGroup dividerContainer;
        protected View divider;

        public DialogViewHolder(View itemView) {
            super(itemView);
            root = (ViewGroup) itemView.findViewById(R.id.dialogRootLayout);
            container = (ViewGroup) itemView.findViewById(R.id.dialogContainer);
            tvName = (TextView) itemView.findViewById(R.id.dialogName);
            tvDate = (TextView) itemView.findViewById(R.id.dialogDate);
            tvLastMessage = (TextView) itemView.findViewById(R.id.dialogLastMessage);
            tvBubble = (TextView) itemView.findViewById(R.id.dialogUnreadBubble);
            ivLastMessageUser = (ImageView) itemView.findViewById(R.id.dialogLastMessageUserAvatar);
            ivAvatar = (ImageView) itemView.findViewById(R.id.dialogAvatar);
            dividerContainer = (ViewGroup) itemView.findViewById(R.id.dialogDividerContainer);
            divider = itemView.findViewById(R.id.dialogDivider);

        }

        private void applyStyle() {
            if (dialogStyle != null) {
                //Texts
                tvName.setTextSize(TypedValue.COMPLEX_UNIT_PX, dialogStyle.getDialogTitleTextSize());
                tvLastMessage.setTextSize(TypedValue.COMPLEX_UNIT_PX, dialogStyle.getDialogMessageTextSize());
                tvDate.setTextSize(TypedValue.COMPLEX_UNIT_PX, dialogStyle.getDialogDateSize());

                //Divider
                divider.setBackgroundColor(dialogStyle.getDialogDividerColor());
                dividerContainer.setPadding(dialogStyle.getDialogDividerLeftPadding(), 0,
                        dialogStyle.getDialogDividerRightPadding(), 0);
                //Avatar
                ivAvatar.getLayoutParams().width = dialogStyle.getDialogAvatarWidth();
                ivAvatar.getLayoutParams().height = dialogStyle.getDialogAvatarHeight();

                //Last message user avatar
                ivLastMessageUser.getLayoutParams().width = dialogStyle.getDialogMessageAvatarWidth();
                ivLastMessageUser.getLayoutParams().height = dialogStyle.getDialogMessageAvatarHeight();

                //Unread bubble
                GradientDrawable bgShape = (GradientDrawable) tvBubble.getBackground();
                bgShape.setColor(dialogStyle.getDialogUnreadBubbleBackgroundColor());
                tvBubble.setVisibility(dialogStyle.isDialogDividerEnabled() ? VISIBLE : GONE);
                tvBubble.setTextSize(TypedValue.COMPLEX_UNIT_PX, dialogStyle.getDialogUnreadBubbleTextSize());
                tvBubble.setTextColor(dialogStyle.getDialogUnreadBubbleTextColor());
                tvBubble.setTypeface(tvBubble.getTypeface(), dialogStyle.getDialogUnreadBubbleTextStyle());
            }
        }


        private void applyDefaultStyle() {
            if (dialogStyle != null) {
                root.setBackgroundColor(dialogStyle.getDialogItemBackground());
                tvName.setTextColor(dialogStyle.getDialogTitleTextColor());
                tvName.setTypeface(tvName.getTypeface(), dialogStyle.getDialogTitleTextStyle());

                tvDate.setTextColor(dialogStyle.getDialogDateColor());
                tvDate.setTypeface(tvDate.getTypeface(), dialogStyle.getDialogDateStyle());

                tvLastMessage.setTextColor(dialogStyle.getDialogMessageTextColor());
                tvLastMessage.setTypeface(tvLastMessage.getTypeface(), dialogStyle.getDialogMessageTextStyle());
            }
        }

        private void applyUnreadStyle() {
            if (dialogStyle != null) {
                root.setBackgroundColor(dialogStyle.getDialogUnreadItemBackground());
                tvName.setTextColor(dialogStyle.getDialogUnreadTitleTextColor());
                tvName.setTypeface(tvName.getTypeface(), dialogStyle.getDialogUnreadTitleTextStyle());

                tvDate.setTextColor(dialogStyle.getDialogUnreadDateColor());
                tvDate.setTypeface(tvDate.getTypeface(), dialogStyle.getDialogUnreadDateStyle());

                tvLastMessage.setTextColor(dialogStyle.getDialogUnreadMessageTextColor());
                tvLastMessage.setTypeface(tvLastMessage.getTypeface(), dialogStyle.getDialogUnreadMessageTextStyle());
            }
        }


        @Override
        public void onBind(final DIALOG dialog) {
            if (dialog.getUnreadCount() > 0) {
                applyUnreadStyle();
            } else {
                applyDefaultStyle();
            }

            //Set Name
            tvName.setText(dialog.getDialogName());

            //Set Date
            tvDate.setText(getDateString(dialog.getLastMessage().getCreatedAt()));

            //Set Dialog avatar
            if (imageLoader != null) {
                imageLoader.loadImage(ivAvatar, dialog.getDialogPhoto());
            }

            //Set Last message user avatar
            if (imageLoader != null) {
                imageLoader.loadImage(ivLastMessageUser, dialog.getLastMessage().getUser().getAvatar());
            }
            ivLastMessageUser.setVisibility(dialogStyle.isDialogMessageAvatarEnabled()
                    && dialog.getUsers().size() > 1 ? VISIBLE : GONE);

            //Set Last message text
            tvLastMessage.setText(dialog.getLastMessage().getText());

            //Set Unread message count bubble
            tvBubble.setText(String.valueOf(dialog.getUnreadCount()));
            tvBubble.setVisibility(dialogStyle.isDialogUnreadBubbleEnabled() &&
                    dialog.getUnreadCount() > 0 ? VISIBLE : GONE);

            if (onItemClickListener != null) {
                container.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        onItemClickListener.onItemClick(view, dialog);
                    }
                });
            }

            if (onLongItemClickListener != null) {
                container.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View view) {
                        onLongItemClickListener.onLongItemClick(view, dialog);
                        return true;
                    }
                });
            }
        }

        protected String getDateString(Date date) {
            return DateFormatter.format(date, DateFormatter.Template.TIME);
        }

        protected DialogListStyle getDialogStyle() {
            return dialogStyle;
        }

        protected void setDialogStyle(DialogListStyle dialogStyle) {
            this.dialogStyle = dialogStyle;
            applyStyle();
        }
    }
}
