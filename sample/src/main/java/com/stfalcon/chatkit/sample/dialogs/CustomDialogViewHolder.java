package com.stfalcon.chatkit.sample.dialogs;

import android.app.Dialog;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.stfalcon.chatkit.dialogs.DialogsListAdapter;
import com.stfalcon.chatkit.sample.R;
import com.stfalcon.chatkit.sample.fixtures.DialogsListFixtures;
import com.stfalcon.chatkit.sample.models.DefaultDialog;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

/**
 * Created by Anton Bevza on 1/18/17.
 */

public class CustomDialogViewHolder extends DialogsListAdapter.DialogViewHolder<DefaultDialog> {
    private ViewGroup container;
    private ViewGroup root;
    private TextView tvName;
    private TextView tvDate;
    private ImageView ivAvatar;
    private TextView tvLastMessageUser;
    private TextView tvLastMessage;

    public CustomDialogViewHolder(View itemView) {
        super(itemView);
        root = (ViewGroup) itemView.findViewById(com.stfalcon.chatkit.R.id.dialogRootLayout);
        container = (ViewGroup) itemView.findViewById(com.stfalcon.chatkit.R.id.dialogContainer);
        tvName = (TextView) itemView.findViewById(com.stfalcon.chatkit.R.id.dialogName);
        tvDate = (TextView) itemView.findViewById(com.stfalcon.chatkit.R.id.dialogDate);
        tvLastMessage = (TextView) itemView.findViewById(com.stfalcon.chatkit.R.id.dialogLastMessage);
        tvLastMessageUser = (TextView) itemView.findViewById(R.id.tvLastMessageAuthor);
        ivAvatar = (ImageView) itemView.findViewById(com.stfalcon.chatkit.R.id.dialogAvatar);
    }

    @Override
    public void onBind(final DefaultDialog dialog) {
        //Set Name
        tvName.setText(dialog.getDialogName());

        //Set Date
        tvDate.setText("Today");

        //Set Dialog avatar
        if (onLoadImagesListener != null) {
            onLoadImagesListener.loadImage(ivAvatar, dialog.getDialogPhoto());
        }

        //Set Last message user avatar
        tvLastMessageUser.setText(dialog.getLastMessage().getUser().getName() + ":");

        //Set Last message text
        tvLastMessage.setText(dialog.getLastMessage().getText());

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
