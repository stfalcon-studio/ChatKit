package com.stfalcon.chatkit.features.dialogs.adapters;

import android.graphics.drawable.GradientDrawable;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.stfalcon.chatkit.R;
import com.stfalcon.chatkit.commons.models.IDialog;
import com.stfalcon.chatkit.features.dialogs.widgets.DialogStyle;
import com.stfalcon.chatkit.utils.DateFormatter;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

/**
 * Created by Anton Bevza on 12/9/16.
 */

public class DefaultDialogViewHolder extends DialogViewHolder<IDialog> {
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

    public DefaultDialogViewHolder(View itemView, DialogStyle dialogStyle) {
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
        tvDate.setText(DateFormatter.format(dialog.getLastMessage().getCreatedAt(), DateFormatter.Template.TIME));

        //Set Dialog avatar
        if (onLoadImagesListener != null) {
            onLoadImagesListener.onLoadImage(dialog.getDialogPhoto(), ivAvatar);
        }

        //Set Last message user avatar
        if (onLoadImagesListener != null) {
            onLoadImagesListener.onLoadImage(dialog.getLastMessage().getUser().getAvatar(), ivLastMessageUser);
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
}
