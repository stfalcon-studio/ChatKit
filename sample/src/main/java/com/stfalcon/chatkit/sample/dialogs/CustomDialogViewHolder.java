package com.stfalcon.chatkit.sample.dialogs;

import android.view.View;

import com.stfalcon.chatkit.dialogs.DialogsListAdapter;
import com.stfalcon.chatkit.sample.R;
import com.stfalcon.chatkit.sample.fixtures.Fixtures;
import com.stfalcon.chatkit.sample.models.DefaultDialog;
import com.stfalcon.chatkit.sample.models.DefaultUser;

/**
 * Created by Anton Bevza on 1/18/17.
 */

public class CustomDialogViewHolder extends DialogsListAdapter.DialogViewHolder<DefaultDialog> {
    private View onlineView;

    public CustomDialogViewHolder(View itemView) {
        super(itemView);
        onlineView = itemView.findViewById(R.id.online);
    }

    @Override
    public void onBind(DefaultDialog dialog) {
        super.onBind(dialog);

        if (dialog.getUsers().size() > 1) {
            onlineView.setVisibility(View.GONE);
        } else {
            boolean isOnline = ((DefaultUser)dialog.getUsers().get(0)).isOnline();
            onlineView.setVisibility(View.VISIBLE);
            if (isOnline) {
                onlineView.setBackgroundResource(R.drawable.bubble_online);
            } else {
                onlineView.setBackgroundResource(R.drawable.bubble_offline);
            }
        }
    }
}
