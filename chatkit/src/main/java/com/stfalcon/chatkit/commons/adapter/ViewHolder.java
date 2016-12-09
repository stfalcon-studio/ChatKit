package com.stfalcon.chatkit.commons.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;

/*
 * Created by troy379 on 09.12.16.
 */
public abstract class ViewHolder<DATA> extends RecyclerView.ViewHolder {

    public abstract void onBind(DATA data);

    public ViewHolder(View itemView) {
        super(itemView);
    }
}
