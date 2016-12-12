package com.stfalcon.chatkit.features.dialogs.adapters;

import android.graphics.Typeface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.stfalcon.chatkit.R;
import com.stfalcon.chatkit.commons.models.IDialog;
import com.stfalcon.chatkit.utils.DateFormatter;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

/**
 * Created by Anton Bevza on 12/9/16.
 */

public class DefaultDialogViewHolder extends DialogViewHolder<IDialog> {
    private ViewGroup container;
    private TextView tvName;
    private TextView tvDate;
    private ImageView ivAvatar;
    private ImageView ivLastMessageUser;//TODO
    private TextView tvLastMessage;
    private TextView tvBubble;
    private View divider;

    public DefaultDialogViewHolder(View itemView) {
        super(itemView);
        container = (ViewGroup) itemView.findViewById(R.id.dialogContainer);
        tvName = (TextView) itemView.findViewById(R.id.dialogName);
        tvDate = (TextView) itemView.findViewById(R.id.dialogDate);
        tvLastMessage = (TextView) itemView.findViewById(R.id.dialogLastMessage);
        tvBubble = (TextView) itemView.findViewById(R.id.dialogUnreadBubble);
        ivLastMessageUser = (ImageView) itemView.findViewById(R.id.dialogLastMessageUserAvatar);
        ivAvatar = (ImageView) itemView.findViewById(R.id.dialogAvatar);
        divider = itemView.findViewById(R.id.dialogDivider);
    }

    @Override
    public void onBind(final IDialog dialog) {
        //Set Name
        tvName.setText(dialog.getDialogName());
        tvName.setTypeface(dialog.getUnreadCount() > 1 ? Typeface.DEFAULT_BOLD : Typeface.DEFAULT);

        //Set Date
        tvDate.setText(DateFormatter.format(dialog.getLastMessage().getCreatedAt(), DateFormatter.Template.TIME));
        tvDate.setTypeface(dialog.getUnreadCount() > 0 ? Typeface.DEFAULT_BOLD : Typeface.DEFAULT);

        //Set Dialog avatar
        if (onLoadImagesListener != null) {
            onLoadImagesListener.onLoadImage(dialog.getDialogPhoto(), ivAvatar);
        }

        //Set Last message user avatar
        if (onLoadImagesListener != null) {
            onLoadImagesListener.onLoadImage(dialog.getLastMessage().getUser().getAvatar(), ivLastMessageUser);
        }
        ivLastMessageUser.setVisibility(dialog.getUsers().size() > 1 ? VISIBLE : GONE);

        //Set Last message text
        tvLastMessage.setText(dialog.getLastMessage().getText());

        //Set Unread message count bubble
        tvBubble.setText(String.valueOf(dialog.getUnreadCount()));
        tvBubble.setVisibility(dialog.getUnreadCount() > 0 ? VISIBLE : GONE);

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
