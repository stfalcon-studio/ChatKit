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
        container = (ViewGroup) itemView.findViewById(R.id.container);
        tvName = (TextView) itemView.findViewById(R.id.tvName);
        tvDate = (TextView) itemView.findViewById(R.id.tvDate);
        tvLastMessage = (TextView) itemView.findViewById(R.id.tvLastMessage);
        tvBubble = (TextView) itemView.findViewById(R.id.tvBubble);
        ivLastMessageUser = (ImageView) itemView.findViewById(R.id.ivLastMessageUser);
        ivAvatar = (ImageView) itemView.findViewById(R.id.ivAvatar);
        divider = itemView.findViewById(R.id.divider);
    }

    @Override
    public void onBind(IDialog defaultDialog) {
        //Set Name
        tvName.setText(defaultDialog.getUsers().size() > 0 ? tvName.getContext().getString(R.string.chat_group)
                : defaultDialog.getUsers().get(0).getName());//TODO Group name
        tvName.setTypeface(defaultDialog.getUnreadCount() > 0 ? Typeface.DEFAULT_BOLD : Typeface.DEFAULT);

        //Set Date
        tvDate.setText(DateFormatter.format(defaultDialog.getLastMessage().getCreatedAt(), DateFormatter.Template.TIME));
        tvDate.setTypeface(defaultDialog.getUnreadCount() > 0 ? Typeface.DEFAULT_BOLD : Typeface.DEFAULT);

        //Set Dialog avatar
        //TODO load image
        ivAvatar = (ImageView) itemView.findViewById(R.id.ivAvatar);

        //Set Last message user avatar
        //TODO load image
        ivLastMessageUser.setVisibility(defaultDialog.getUsers().size() > 0 ? VISIBLE : GONE);

        //Set Last message text
        tvLastMessage.setText(defaultDialog.getLastMessage().getText());

        //Set Unread message count bubble
        tvBubble.setText(String.valueOf(defaultDialog.getUnreadCount()));
        tvBubble.setVisibility(defaultDialog.getUnreadCount() > 0 ? VISIBLE : GONE);
    }
}
