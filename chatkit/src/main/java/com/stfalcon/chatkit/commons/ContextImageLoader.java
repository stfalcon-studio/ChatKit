package com.stfalcon.chatkit.commons;

import android.widget.ImageView;

import com.stfalcon.chatkit.commons.models.IDialog;
import com.stfalcon.chatkit.commons.models.IUser;
import com.stfalcon.chatkit.commons.models.MessageContentType;

public interface ContextImageLoader {

    void loadImage(ImageView imageView, IDialog dialog);

    void loadImage(ImageView imageView, IUser user);

    void loadImage(ImageView imageView, MessageContentType.Image messageContent);
}
