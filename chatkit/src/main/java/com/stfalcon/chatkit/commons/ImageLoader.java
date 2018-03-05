/*******************************************************************************
 * Copyright 2016 stfalcon.com
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *******************************************************************************/

package com.stfalcon.chatkit.commons;

import android.widget.ImageView;

import com.stfalcon.chatkit.commons.models.IDialog;
import com.stfalcon.chatkit.commons.models.IUser;
import com.stfalcon.chatkit.commons.models.MessageContentType;

/**
 * Callback for implementing images loading in message list
 */
public abstract class ImageLoader implements ContextImageLoader {

    public abstract void loadImage(ImageView imageView, String url);

    @Override
    public void loadImage(ImageView imageView, IDialog dialog) {
        loadImage(imageView, dialog.getDialogPhoto());
    }

    @Override
    public void loadImage(ImageView imageView, IUser user) {
        loadImage(imageView, user.getAvatar());
    }

    @Override
    public void loadImage(ImageView imageView, MessageContentType.Image messageContent) {
        loadImage(imageView, messageContent.getImageUrl());
    }
}

