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
 * Created by Anton Bevza on 12/9/16.
 */

public class DialogsListAdapter<DIALOG extends IDialog>
        extends RecyclerView.Adapter<DialogsListAdapter.DialogViewHolder> {

    private List<DIALOG> items = new ArrayList<>();
    private int itemLayoutId;
    private Class<? extends DialogViewHolder> holderClass;
    private DialogViewHolder.OnLoadImagesListener onLoadImagesListener;
    private DialogViewHolder.OnItemClickListener onItemClickListener;
    private DialogViewHolder.OnLongItemClickListener onLongItemClickListener;
    private DialogListStyle dialogStyle;

    public DialogsListAdapter(@LayoutRes int itemLayoutId, Class<? extends DialogViewHolder> holderClass, List<DIALOG> dialogs) {
        this.itemLayoutId = itemLayoutId;
        this.holderClass = holderClass;
        this.items = dialogs;
    }

    public DialogsListAdapter(List<DIALOG> dialogs) {
        this(R.layout.item_dialog, DefaultDialogViewHolder.class, dialogs);
    }

    public DialogsListAdapter(@LayoutRes int itemLayoutId, List<DIALOG> dialogs) {
        this(itemLayoutId, DefaultDialogViewHolder.class, dialogs);
    }

    @SuppressWarnings("unchecked")
    @Override
    public void onBindViewHolder(DialogViewHolder holder, int position) {
        holder.setOnLoadImagesListener(onLoadImagesListener);
        holder.setOnItemClickListener(onItemClickListener);
        holder.setOnLongItemClickListener(onLongItemClickListener);
        holder.onBind(items.get(position));
    }

    @Override
    public DialogViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(itemLayoutId, parent, false);

        try {
            Constructor<? extends DialogViewHolder> constructor = holderClass.getDeclaredConstructor(View.class, DialogListStyle.class);
            constructor.setAccessible(true);
            return constructor.newInstance(v, dialogStyle);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void removeItemWithId(String id) {
        for (int i = 0; i < items.size(); i++) {
            if (items.get(i).getId().equals(id)) {
                items.remove(i);
                notifyItemRemoved(i);
            }
        }
    }

    public void clear() {
        if (items != null) {
            items.clear();
        }
    }

    public void setItems(List<DIALOG> items) {
        this.items = items;
        notifyDataSetChanged();
    }

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

    public void addItem(DIALOG dialog) {
        items.add(0, dialog);
        notifyItemInserted(0);
    }

    public void updateItem(int position, DIALOG item) {
        if (items == null) {
            items = new ArrayList<>();
        }
        notifyItemChanged(position);
    }

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
     * @param dialogId Dialog ID
     * @param message New message
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

    public void setOnLoadImagesListener(DialogViewHolder.OnLoadImagesListener onLoadImagesListener) {
        this.onLoadImagesListener = onLoadImagesListener;
    }

    public DialogViewHolder.OnLoadImagesListener getOnLoadImagesListener() {
        return onLoadImagesListener;
    }

    public DialogViewHolder.OnItemClickListener getOnItemClickListener() {
        return onItemClickListener;
    }

    public void setOnItemClickListener(DialogViewHolder.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public DialogViewHolder.OnLongItemClickListener getOnLongItemClickListener() {
        return onLongItemClickListener;
    }

    public void setOnLongItemClickListener(DialogViewHolder.OnLongItemClickListener onLongItemClickListener) {
        this.onLongItemClickListener = onLongItemClickListener;
    }

    public void setStyle(DialogListStyle dialogStyle) {
        this.dialogStyle = dialogStyle;
    }



    /*
    * HOLDERS
    * */
    public abstract static class DialogViewHolder<DIALOG extends IDialog> extends ViewHolder<DIALOG> {
        OnLoadImagesListener onLoadImagesListener;
        OnItemClickListener onItemClickListener;
        OnLongItemClickListener onLongItemClickListener;
        protected DialogListStyle dialogStyle;

        public DialogViewHolder(View itemView, DialogListStyle dialogStyle) {
            super(itemView);
            this.dialogStyle = dialogStyle;
        }

        public void setOnLoadImagesListener(OnLoadImagesListener onLoadImagesListener) {
            this.onLoadImagesListener = onLoadImagesListener;
        }

        public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
            this.onItemClickListener = onItemClickListener;
        }

        public void setOnLongItemClickListener(OnLongItemClickListener onLongItemClickListener) {
            this.onLongItemClickListener = onLongItemClickListener;
        }

        public interface OnLoadImagesListener {
            void onLoadImage(ImageView imageView, String url);
        }

        public interface OnItemClickListener {
            void onItemClick(View view, IDialog dialog);
        }

        public interface OnLongItemClickListener {
            void onLongItemClick(View view, IDialog dialog);
        }
    }

    public static class DefaultDialogViewHolder extends DialogViewHolder<IDialog> {
        private ViewGroup container;
        private ViewGroup root;
        private TextView tvName;
        private TextView tvDate;
        private ImageView ivAvatar;
        private ImageView ivLastMessageUser;
        private TextView tvLastMessage;
        private TextView tvBubble;
        private ViewGroup dividerContainer;
        private View divider;

        public DefaultDialogViewHolder(View itemView, DialogListStyle dialogStyle) {
            super(itemView, dialogStyle);
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

            applyStyle();
        }

        private void applyStyle() {
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


        private void applyDefaultStyle() {
            root.setBackgroundColor(dialogStyle.getDialogItemBackground());
            tvName.setTextColor(dialogStyle.getDialogTitleTextColor());
            tvName.setTypeface(tvName.getTypeface(), dialogStyle.getDialogTitleTextStyle());

            tvDate.setTextColor(dialogStyle.getDialogDateColor());
            tvDate.setTypeface(tvDate.getTypeface(), dialogStyle.getDialogDateStyle());

            tvLastMessage.setTextColor(dialogStyle.getDialogMessageTextColor());
            tvLastMessage.setTypeface(tvLastMessage.getTypeface(), dialogStyle.getDialogMessageTextStyle());
        }

        private void applyUnreadStyle() {
            root.setBackgroundColor(dialogStyle.getDialogUnreadItemBackground());
            tvName.setTextColor(dialogStyle.getDialogUnreadTitleTextColor());
            tvName.setTypeface(tvName.getTypeface(), dialogStyle.getDialogUnreadTitleTextStyle());

            tvDate.setTextColor(dialogStyle.getDialogUnreadDateColor());
            tvDate.setTypeface(tvDate.getTypeface(), dialogStyle.getDialogUnreadDateStyle());

            tvLastMessage.setTextColor(dialogStyle.getDialogUnreadMessageTextColor());
            tvLastMessage.setTypeface(tvLastMessage.getTypeface(), dialogStyle.getDialogUnreadMessageTextStyle());
        }


        @Override
        public void onBind(final IDialog dialog) {
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
            if (onLoadImagesListener != null) {
                onLoadImagesListener.onLoadImage(ivAvatar, dialog.getDialogPhoto());
            }

            //Set Last message user avatar
            if (onLoadImagesListener != null) {
                onLoadImagesListener.onLoadImage(ivLastMessageUser, dialog.getLastMessage().getUser().getAvatar());
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
    }
}
