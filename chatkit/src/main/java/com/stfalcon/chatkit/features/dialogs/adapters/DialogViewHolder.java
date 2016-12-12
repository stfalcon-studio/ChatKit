package com.stfalcon.chatkit.features.dialogs.adapters;

import android.view.View;

import com.stfalcon.chatkit.commons.adapter.ViewHolder;
import com.stfalcon.chatkit.commons.models.IDialog;

/**
 * Created by Anton Bevza on 12/9/16.
 */

abstract class DialogViewHolder<DIALOG extends IDialog> extends ViewHolder<DIALOG> {
    public DialogViewHolder(View itemView) {
        super(itemView);
    }
}
