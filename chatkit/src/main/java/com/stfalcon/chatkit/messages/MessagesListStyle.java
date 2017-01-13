package com.stfalcon.chatkit.messages;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.util.AttributeSet;

import com.stfalcon.chatkit.commons.Style;

/*
 * Created by troy379 on 16.12.16.
 */
class MessagesListStyle extends Style {

    protected MessagesListStyle(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    private Drawable getMessageSelector(@ColorInt int normalColor, @ColorInt int selectedColor, @DrawableRes int shape) {

        Drawable button = DrawableCompat.wrap(getVectorDrawable(shape));
        DrawableCompat.setTintList(
                button,
                new ColorStateList(
                        new int[][]{
                                new int[]{android.R.attr.state_selected},
                                new int[]{-android.R.attr.state_selected}
                        },
                        new int[]{normalColor, selectedColor}
                ));
        return button;
    }
}
