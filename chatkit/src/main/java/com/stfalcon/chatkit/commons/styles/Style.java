package com.stfalcon.chatkit.commons.styles;

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

    public Style(Context context, AttributeSet attrs) {
        this.context = context;
        this.resources = context.getResources();
        this.attrs = attrs;
    }

    protected int getSystemAccentColor() {
        return getSystemColor(R.attr.colorAccent);
    }

    protected int getSystemPrimaryColor() {
        return getSystemColor(R.attr.colorPrimary);
    }

    protected int getSystemPrimaryDarkColor() {
        return getSystemColor(R.attr.colorPrimaryDark);
    }

    protected int getSystemPrimaryTextColor() {
        return getSystemColor(android.R.attr.textColorPrimary);
    }

    protected int getSystemHintColor() {
        return getSystemColor(android.R.attr.textColorHint);
    }

    protected int getSystemColor(@AttrRes int attr) {
        TypedValue typedValue = new TypedValue();

        TypedArray a = context.obtainStyledAttributes(typedValue.data, new int[]{attr});
        int color = a.getColor(0, 0);
        a.recycle();

        return color;
    }

    protected int getDimension(@DimenRes int dimen) {
        return resources.getDimensionPixelSize(dimen);
    }

    protected int getColor(@ColorRes int color) {
        return ContextCompat.getColor(context, color);
    }

    protected Drawable getDrawable(@DrawableRes int drawable) {
        return ContextCompat.getDrawable(context, drawable);
    }

    protected Drawable getVectorDrawable(@DrawableRes int drawable) {
        return VectorDrawableCompat.create(resources, drawable, null);
    }

}
