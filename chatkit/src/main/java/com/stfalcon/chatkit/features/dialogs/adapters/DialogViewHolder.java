package com.stfalcon.chatkit.features.dialogs.adapters;

import android.view.View;
import android.widget.ImageView;

import com.stfalcon.chatkit.commons.adapter.ViewHolder;
import com.stfalcon.chatkit.commons.models.IDialog;

/**
 * Created by Anton Bevza on 12/9/16.
 */

public abstract class DialogViewHolder<DIALOG extends IDialog> extends ViewHolder<DIALOG> {
    OnLoadImagesListener onLoadImagesListener;
    OnItemClickListener onItemClickListener;
    OnLongItemClickListener onLongItemClickListener;

    public DialogViewHolder(View itemView) {
        super(itemView);
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
        void onLoadImage(String url, ImageView imageView); // TODO: 13.12.16 change params ordering;
    }

    public interface OnItemClickListener {
        void onItemClick(View view, IDialog dialog);
    }

    public interface OnLongItemClickListener {
        void onLongItemClick(View view, IDialog dialog);
    }
}
