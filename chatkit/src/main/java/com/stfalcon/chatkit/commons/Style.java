package com.stfalcon.chatkit.commons;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.annotation.AttrRes;
import android.support.annotation.ColorRes;
import android.support.annotation.DimenRes;
import android.support.annotation.DrawableRes;
import android.support.graphics.drawable.VectorDrawableCompat;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.util.TypedValue;

import com.stfalcon.chatkit.R;

/*
 * Created by troy379 on 15.12.16.
 */
public abstract class Style {

    protected Context context;
    protected Resources resources;
    protected AttributeSet attrs;

    protected Style(Context context, AttributeSet attrs) {
        this.context = context;
        this.resources = context.getResources();
        this.attrs = attrs;
    }

    protected final int getSystemAccentColor() {
        return getSystemColor(R.attr.colorAccent);
    }

    protected final int getSystemPrimaryColor() {
        return getSystemColor(R.attr.colorPrimary);
    }

    protected final int getSystemPrimaryDarkColor() {
        return getSystemColor(R.attr.colorPrimaryDark);
    }

    protected final int getSystemPrimaryTextColor() {
        return getSystemColor(android.R.attr.textColorPrimary);
    }

    protected final int getSystemHintColor() {
        return getSystemColor(android.R.attr.textColorHint);
    }

    protected final int getSystemColor(@AttrRes int attr) {
        TypedValue typedValue = new TypedValue();

        TypedArray a = context.obtainStyledAttributes(typedValue.data, new int[]{attr});
        int color = a.getColor(0, 0);
        a.recycle();

        return color;
    }

    protected final int getDimension(@DimenRes int dimen) {
        return resources.getDimensionPixelSize(dimen);
    }

    protected final int getColor(@ColorRes int color) {
        return ContextCompat.getColor(context, color);
    }

    protected final Drawable getDrawable(@DrawableRes int drawable) {
        return ContextCompat.getDrawable(context, drawable);
    }

    protected final Drawable getVectorDrawable(@DrawableRes int drawable) {
        return VectorDrawableCompat.create(resources, drawable, null);
    }

}
