package com.stfalcon.chatkit.features.messages.adapters.holders;

import android.view.View;
import android.widget.ImageView;

import com.stfalcon.chatkit.commons.adapter.ViewHolder;
import com.stfalcon.chatkit.commons.models.IMessage;

/*
 * Created by troy379 on 09.12.16.
 */
public abstract class MessageViewHolder<MESSAGE extends IMessage> extends ViewHolder<MESSAGE> {

    private boolean isSelected;
    protected ImageLoader imageLoader;

    public MessageViewHolder(View itemView) {
        super(itemView);
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public ImageLoader getImageLoader() {
        return imageLoader;
    }

    public void setImageLoader(ImageLoader imageLoader) {
        this.imageLoader = imageLoader;
    }

    public interface ImageLoader {
        void loadImage(ImageView imageView, String url);
    }
}
