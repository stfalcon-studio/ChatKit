package com.stfalcon.chatkit.sample.features.main.adapter;

import android.support.annotation.DrawableRes;
import android.support.annotation.StringRes;

/*
 * Created by troy379 on 04.04.17.
 */
public class Sample {

    @StringRes int title;
    @DrawableRes int coverRes;
    Type type;

    public Sample(@StringRes int title, @DrawableRes int coverRes, Type type) {
        this.title = title;
        this.coverRes = coverRes;
        this.type = type;
    }

    public enum Type {
        DEFAULT, STYLED, CUSTOM_LAYOUT, CUSTOM_VIEW_HOLDER, CUSTOM_MEDIA
    }
}
