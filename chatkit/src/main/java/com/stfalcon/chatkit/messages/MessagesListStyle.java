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

package com.stfalcon.chatkit.messages;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.util.AttributeSet;

import com.stfalcon.chatkit.R;
import com.stfalcon.chatkit.commons.Style;

/**
 * Style for MessagesListStyle customization by xml attributes
 */
@SuppressWarnings("WeakerAccess")
public class MessagesListStyle extends Style {

    private int textAutoLinkMask;
    private int incomingTextLinkColor;
    private int outcomingTextLinkColor;

    private int incomingAvatarWidth;
    private int incomingAvatarHeight;

    private int incomingBubbleDrawable;
    private int incomingDefaultBubbleColor;
    private int incomingDefaultBubblePressedColor;
    private int incomingDefaultBubbleSelectedColor;

    private int incomingImageOverlayDrawable;
    private int incomingDefaultImageOverlayPressedColor;
    private int incomingDefaultImageOverlaySelectedColor;

    private int incomingDefaultBubblePaddingLeft;
    private int incomingDefaultBubblePaddingRight;
    private int incomingDefaultBubblePaddingTop;
    private int incomingDefaultBubblePaddingBottom;

    private int incomingTextColor;
    private int incomingTextSize;
    private int incomingTextStyle;

    private int incomingTimeTextColor;
    private int incomingTimeTextSize;
    private int incomingTimeTextStyle;

    private int incomingImageTimeTextColor;
    private int incomingImageTimeTextSize;
    private int incomingImageTimeTextStyle;

    private int outcomingBubbleDrawable;
    private int outcomingDefaultBubbleColor;
    private int outcomingDefaultBubblePressedColor;
    private int outcomingDefaultBubbleSelectedColor;

    private int outcomingImageOverlayDrawable;
    private int outcomingDefaultImageOverlayPressedColor;
    private int outcomingDefaultImageOverlaySelectedColor;

    private int outcomingDefaultBubblePaddingLeft;
    private int outcomingDefaultBubblePaddingRight;
    private int outcomingDefaultBubblePaddingTop;
    private int outcomingDefaultBubblePaddingBottom;

    private int outcomingTextColor;
    private int outcomingTextSize;
    private int outcomingTextStyle;

    private int outcomingTimeTextColor;
    private int outcomingTimeTextSize;
    private int outcomingTimeTextStyle;

    private int outcomingImageTimeTextColor;
    private int outcomingImageTimeTextSize;
    private int outcomingImageTimeTextStyle;

    private int dateHeaderPadding;
    private String dateHeaderFormat;
    private int dateHeaderTextColor;
    private int dateHeaderTextSize;
    private int dateHeaderTextStyle;

    static MessagesListStyle parse(Context context, AttributeSet attrs) {
        MessagesListStyle style = new MessagesListStyle(context, attrs);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.MessagesList);

        style.textAutoLinkMask = typedArray.getInt(R.styleable.MessagesList_textAutoLink, 0);
        style.incomingTextLinkColor = typedArray.getColor(R.styleable.MessagesList_incomingTextLinkColor,
                style.getSystemAccentColor());
        style.outcomingTextLinkColor = typedArray.getColor(R.styleable.MessagesList_outcomingTextLinkColor,
                style.getSystemAccentColor());

        style.incomingAvatarWidth = typedArray.getDimensionPixelSize(R.styleable.MessagesList_incomingAvatarWidth,
                style.getDimension(R.dimen.message_avatar_width));
        style.incomingAvatarHeight = typedArray.getDimensionPixelSize(R.styleable.MessagesList_incomingAvatarHeight,
                style.getDimension(R.dimen.message_avatar_height));

        style.incomingBubbleDrawable = typedArray.getResourceId(R.styleable.MessagesList_incomingBubbleDrawable, -1);
        style.incomingDefaultBubbleColor = typedArray.getColor(R.styleable.MessagesList_incomingDefaultBubbleColor,
                style.getColor(R.color.white_two));
        style.incomingDefaultBubblePressedColor = typedArray.getColor(R.styleable.MessagesList_incomingDefaultBubblePressedColor,
                style.getColor(R.color.white_two));
        style.incomingDefaultBubbleSelectedColor = typedArray.getColor(R.styleable.MessagesList_incomingDefaultBubbleSelectedColor,
                style.getColor(R.color.cornflower_blue_two_24));

        style.incomingImageOverlayDrawable = typedArray.getResourceId(R.styleable.MessagesList_incomingImageOverlayDrawable, -1);
        style.incomingDefaultImageOverlayPressedColor = typedArray.getColor(R.styleable.MessagesList_incomingDefaultImageOverlayPressedColor,
                style.getColor(R.color.transparent));
        style.incomingDefaultImageOverlaySelectedColor = typedArray.getColor(R.styleable.MessagesList_incomingDefaultImageOverlaySelectedColor,
                style.getColor(R.color.cornflower_blue_light_40));

        style.incomingDefaultBubblePaddingLeft = typedArray.getDimensionPixelSize(R.styleable.MessagesList_incomingBubblePaddingLeft,
                style.getDimension(R.dimen.message_padding_left));
        style.incomingDefaultBubblePaddingRight = typedArray.getDimensionPixelSize(R.styleable.MessagesList_incomingBubblePaddingRight,
                style.getDimension(R.dimen.message_padding_right));
        style.incomingDefaultBubblePaddingTop = typedArray.getDimensionPixelSize(R.styleable.MessagesList_incomingBubblePaddingTop,
                style.getDimension(R.dimen.message_padding_top));
        style.incomingDefaultBubblePaddingBottom = typedArray.getDimensionPixelSize(R.styleable.MessagesList_incomingBubblePaddingBottom,
                style.getDimension(R.dimen.message_padding_bottom));
        style.incomingTextColor = typedArray.getColor(R.styleable.MessagesList_incomingTextColor,
                style.getColor(R.color.dark_grey_two));
        style.incomingTextSize = typedArray.getDimensionPixelSize(R.styleable.MessagesList_incomingTextSize,
                style.getDimension(R.dimen.message_text_size));
        style.incomingTextStyle = typedArray.getInt(R.styleable.MessagesList_incomingTextStyle, Typeface.NORMAL);

        style.incomingTimeTextColor = typedArray.getColor(R.styleable.MessagesList_incomingTimeTextColor,
                style.getColor(R.color.warm_grey_four));
        style.incomingTimeTextSize = typedArray.getDimensionPixelSize(R.styleable.MessagesList_incomingTimeTextSize,
                style.getDimension(R.dimen.message_time_text_size));
        style.incomingTimeTextStyle = typedArray.getInt(R.styleable.MessagesList_incomingTimeTextStyle, Typeface.NORMAL);

        style.incomingImageTimeTextColor = typedArray.getColor(R.styleable.MessagesList_incomingImageTimeTextColor,
                style.getColor(R.color.warm_grey_four));
        style.incomingImageTimeTextSize = typedArray.getDimensionPixelSize(R.styleable.MessagesList_incomingImageTimeTextSize,
                style.getDimension(R.dimen.message_time_text_size));
        style.incomingImageTimeTextStyle = typedArray.getInt(R.styleable.MessagesList_incomingImageTimeTextStyle, Typeface.NORMAL);

        style.outcomingBubbleDrawable = typedArray.getResourceId(R.styleable.MessagesList_outcomingBubbleDrawable, -1);
        style.outcomingDefaultBubbleColor = typedArray.getColor(R.styleable.MessagesList_outcomingDefaultBubbleColor,
                style.getColor(R.color.cornflower_blue_two));
        style.outcomingDefaultBubblePressedColor = typedArray.getColor(R.styleable.MessagesList_outcomingDefaultBubblePressedColor,
                style.getColor(R.color.cornflower_blue_two));
        style.outcomingDefaultBubbleSelectedColor = typedArray.getColor(R.styleable.MessagesList_outcomingDefaultBubbleSelectedColor,
                style.getColor(R.color.cornflower_blue_two_24));

        style.outcomingImageOverlayDrawable = typedArray.getResourceId(R.styleable.MessagesList_outcomingImageOverlayDrawable, -1);
        style.outcomingDefaultImageOverlayPressedColor = typedArray.getColor(R.styleable.MessagesList_outcomingDefaultImageOverlayPressedColor,
                style.getColor(R.color.transparent));
        style.outcomingDefaultImageOverlaySelectedColor = typedArray.getColor(R.styleable.MessagesList_outcomingDefaultImageOverlaySelectedColor,
                style.getColor(R.color.cornflower_blue_light_40));

        style.outcomingDefaultBubblePaddingLeft = typedArray.getDimensionPixelSize(R.styleable.MessagesList_outcomingBubblePaddingLeft,
                style.getDimension(R.dimen.message_padding_left));
        style.outcomingDefaultBubblePaddingRight = typedArray.getDimensionPixelSize(R.styleable.MessagesList_outcomingBubblePaddingRight,
                style.getDimension(R.dimen.message_padding_right));
        style.outcomingDefaultBubblePaddingTop = typedArray.getDimensionPixelSize(R.styleable.MessagesList_outcomingBubblePaddingTop,
                style.getDimension(R.dimen.message_padding_top));
        style.outcomingDefaultBubblePaddingBottom = typedArray.getDimensionPixelSize(R.styleable.MessagesList_outcomingBubblePaddingBottom,
                style.getDimension(R.dimen.message_padding_bottom));
        style.outcomingTextColor = typedArray.getColor(R.styleable.MessagesList_outcomingTextColor,
                style.getColor(R.color.white));
        style.outcomingTextSize = typedArray.getDimensionPixelSize(R.styleable.MessagesList_outcomingTextSize,
                style.getDimension(R.dimen.message_text_size));
        style.outcomingTextStyle = typedArray.getInt(R.styleable.MessagesList_outcomingTextStyle, Typeface.NORMAL);

        style.outcomingTimeTextColor = typedArray.getColor(R.styleable.MessagesList_outcomingTimeTextColor,
                style.getColor(R.color.white60));
        style.outcomingTimeTextSize = typedArray.getDimensionPixelSize(R.styleable.MessagesList_outcomingTimeTextSize,
                style.getDimension(R.dimen.message_time_text_size));
        style.outcomingTimeTextStyle = typedArray.getInt(R.styleable.MessagesList_outcomingTimeTextStyle, Typeface.NORMAL);

        style.outcomingImageTimeTextColor = typedArray.getColor(R.styleable.MessagesList_outcomingImageTimeTextColor,
                style.getColor(R.color.warm_grey_four));
        style.outcomingImageTimeTextSize = typedArray.getDimensionPixelSize(R.styleable.MessagesList_outcomingImageTimeTextSize,
                style.getDimension(R.dimen.message_time_text_size));
        style.outcomingImageTimeTextStyle = typedArray.getInt(R.styleable.MessagesList_outcomingImageTimeTextStyle, Typeface.NORMAL);

        style.dateHeaderPadding = typedArray.getDimensionPixelSize(R.styleable.MessagesList_dateHeaderPadding,
                style.getDimension(R.dimen.message_date_header_padding));
        style.dateHeaderFormat = typedArray.getString(R.styleable.MessagesList_dateHeaderFormat);
        style.dateHeaderTextColor = typedArray.getColor(R.styleable.MessagesList_dateHeaderTextColor,
                style.getColor(R.color.warm_grey_two));
        style.dateHeaderTextSize = typedArray.getDimensionPixelSize(R.styleable.MessagesList_dateHeaderTextSize,
                style.getDimension(R.dimen.message_date_header_text_size));
        style.dateHeaderTextStyle = typedArray.getInt(R.styleable.MessagesList_dateHeaderTextStyle, Typeface.NORMAL);

        typedArray.recycle();

        return style;
    }

    public MessagesListStyle(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    private Drawable getMessageSelector(@ColorInt int normalColor, @ColorInt int selectedColor,
                                        @ColorInt int pressedColor, @DrawableRes int shape) {

        Drawable drawable = DrawableCompat.wrap(getVectorDrawable(shape)).mutate();
        DrawableCompat.setTintList(
                drawable,
                new ColorStateList(
                        new int[][]{
                                new int[]{android.R.attr.state_selected},
                                new int[]{android.R.attr.state_pressed},
                                new int[]{-android.R.attr.state_pressed, -android.R.attr.state_selected}
                        },
                        new int[]{selectedColor, pressedColor, normalColor}
                ));
        return drawable;
    }

    public int getTextAutoLinkMask() {
        return textAutoLinkMask;
    }

    public int getIncomingTextLinkColor() {
        return incomingTextLinkColor;
    }

    public int getOutcomingTextLinkColor() {
        return outcomingTextLinkColor;
    }

    public int getIncomingAvatarWidth() {
        return incomingAvatarWidth;
    }

    public int getIncomingAvatarHeight() {
        return incomingAvatarHeight;
    }

    public int getIncomingDefaultBubblePaddingLeft() {
        return incomingDefaultBubblePaddingLeft;
    }

    public int getIncomingDefaultBubblePaddingRight() {
        return incomingDefaultBubblePaddingRight;
    }

    public int getIncomingDefaultBubblePaddingTop() {
        return incomingDefaultBubblePaddingTop;
    }

    public int getIncomingDefaultBubblePaddingBottom() {
        return incomingDefaultBubblePaddingBottom;
    }

    public int getIncomingTextColor() {
        return incomingTextColor;
    }

    public int getIncomingTextSize() {
        return incomingTextSize;
    }

    public int getIncomingTextStyle() {
        return incomingTextStyle;
    }

    public Drawable getOutcomingBubbleDrawable() {
        if (outcomingBubbleDrawable == -1) {
            return getMessageSelector(outcomingDefaultBubbleColor, outcomingDefaultBubbleSelectedColor,
                    outcomingDefaultBubblePressedColor, R.drawable.shape_outcoming_message);
        } else {
            return getDrawable(outcomingBubbleDrawable);
        }
    }

    public Drawable getOutcomingImageOverlayDrawable() {
        if (outcomingImageOverlayDrawable == -1) {
            return getMessageSelector(Color.TRANSPARENT, outcomingDefaultImageOverlaySelectedColor,
                    outcomingDefaultImageOverlayPressedColor, R.drawable.shape_outcoming_message);
        } else {
            return getDrawable(outcomingImageOverlayDrawable);
        }
    }

    public int getOutcomingDefaultBubblePaddingLeft() {
        return outcomingDefaultBubblePaddingLeft;
    }

    public int getOutcomingDefaultBubblePaddingRight() {
        return outcomingDefaultBubblePaddingRight;
    }

    public int getOutcomingDefaultBubblePaddingTop() {
        return outcomingDefaultBubblePaddingTop;
    }

    public int getOutcomingDefaultBubblePaddingBottom() {
        return outcomingDefaultBubblePaddingBottom;
    }

    public int getIncomingDefaultBubbleColor() {
        return incomingDefaultBubbleColor;
    }

    public int getIncomingDefaultBubblePressedColor() {
        return incomingDefaultBubblePressedColor;
    }

    public int getIncomingDefaultBubbleSelectedColor() {
        return incomingDefaultBubbleSelectedColor;
    }

    public int getIncomingDefaultImageOverlayPressedColor() {
        return incomingDefaultImageOverlayPressedColor;
    }

    public int getIncomingDefaultImageOverlaySelectedColor() {
        return incomingDefaultImageOverlaySelectedColor;
    }

    public int getOutcomingDefaultBubbleColor() {
        return outcomingDefaultBubbleColor;
    }

    public int getOutcomingDefaultBubblePressedColor() {
        return outcomingDefaultBubblePressedColor;
    }

    public int getOutcomingDefaultBubbleSelectedColor() {
        return outcomingDefaultBubbleSelectedColor;
    }

    public int getOutcomingDefaultImageOverlayPressedColor() {
        return outcomingDefaultImageOverlayPressedColor;
    }

    public int getOutcomingDefaultImageOverlaySelectedColor() {
        return outcomingDefaultImageOverlaySelectedColor;
    }

    public int getOutcomingTextColor() {
        return outcomingTextColor;
    }

    public int getOutcomingTextSize() {
        return outcomingTextSize;
    }

    public int getOutcomingTextStyle() {
        return outcomingTextStyle;
    }

    public int getOutcomingTimeTextColor() {
        return outcomingTimeTextColor;
    }

    public int getOutcomingTimeTextSize() {
        return outcomingTimeTextSize;
    }

    public int getOutcomingTimeTextStyle() {
        return outcomingTimeTextStyle;
    }

    public int getOutcomingImageTimeTextColor() {
        return outcomingImageTimeTextColor;
    }

    public int getOutcomingImageTimeTextSize() {
        return outcomingImageTimeTextSize;
    }

    public int getOutcomingImageTimeTextStyle() {
        return outcomingImageTimeTextStyle;
    }

    public int getDateHeaderTextColor() {
        return dateHeaderTextColor;
    }

    public int getDateHeaderTextSize() {
        return dateHeaderTextSize;
    }

    public int getDateHeaderTextStyle() {
        return dateHeaderTextStyle;
    }

    public int getDateHeaderPadding() {
        return dateHeaderPadding;
    }

    public String getDateHeaderFormat() {
        return dateHeaderFormat;
    }

    public int getIncomingTimeTextSize() {
        return incomingTimeTextSize;
    }

    public int getIncomingTimeTextStyle() {
        return incomingTimeTextStyle;
    }

    public int getIncomingTimeTextColor() {
        return incomingTimeTextColor;
    }

    public int getIncomingImageTimeTextColor() {
        return incomingImageTimeTextColor;
    }

    public int getIncomingImageTimeTextSize() {
        return incomingImageTimeTextSize;
    }

    public int getIncomingImageTimeTextStyle() {
        return incomingImageTimeTextStyle;
    }

    public Drawable getIncomingBubbleDrawable() {
        if (incomingBubbleDrawable == -1) {
            return getMessageSelector(incomingDefaultBubbleColor, incomingDefaultBubbleSelectedColor,
                    incomingDefaultBubblePressedColor, R.drawable.shape_incoming_message);
        } else {
            return getDrawable(incomingBubbleDrawable);
        }
    }

    public Drawable getIncomingImageOverlayDrawable() {
        if (incomingImageOverlayDrawable == -1) {
            return getMessageSelector(Color.TRANSPARENT, incomingDefaultImageOverlaySelectedColor,
                    incomingDefaultImageOverlayPressedColor, R.drawable.shape_incoming_message);
        } else {
            return getDrawable(incomingImageOverlayDrawable);
        }
    }

    public void setTextAutoLinkMask(int textAutoLinkMask) {
        this.textAutoLinkMask = textAutoLinkMask;
    }

    public void setIncomingTextLinkColor(int incomingTextLinkColor) {
        this.incomingTextLinkColor = incomingTextLinkColor;
    }

    public void setOutcomingTextLinkColor(int outcomingTextLinkColor) {
        this.outcomingTextLinkColor = outcomingTextLinkColor;
    }

    public void setIncomingAvatarWidth(int incomingAvatarWidth) {
        this.incomingAvatarWidth = incomingAvatarWidth;
    }

    public void setIncomingAvatarHeight(int incomingAvatarHeight) {
        this.incomingAvatarHeight = incomingAvatarHeight;
    }

    public void setIncomingBubbleDrawable(int incomingBubbleDrawable) {
        this.incomingBubbleDrawable = incomingBubbleDrawable;
    }

    public void setIncomingDefaultBubbleColor(int incomingDefaultBubbleColor) {
        this.incomingDefaultBubbleColor = incomingDefaultBubbleColor;
    }

    public void setIncomingDefaultBubblePressedColor(int incomingDefaultBubblePressedColor) {
        this.incomingDefaultBubblePressedColor = incomingDefaultBubblePressedColor;
    }

    public void setIncomingDefaultBubbleSelectedColor(int incomingDefaultBubbleSelectedColor) {
        this.incomingDefaultBubbleSelectedColor = incomingDefaultBubbleSelectedColor;
    }

    public void setIncomingImageOverlayDrawable(int incomingImageOverlayDrawable) {
        this.incomingImageOverlayDrawable = incomingImageOverlayDrawable;
    }

    public void setIncomingDefaultImageOverlayPressedColor(int incomingDefaultImageOverlayPressedColor) {
        this.incomingDefaultImageOverlayPressedColor = incomingDefaultImageOverlayPressedColor;
    }

    public void setIncomingDefaultImageOverlaySelectedColor(int incomingDefaultImageOverlaySelectedColor) {
        this.incomingDefaultImageOverlaySelectedColor = incomingDefaultImageOverlaySelectedColor;
    }

    public void setIncomingDefaultBubblePaddingLeft(int incomingDefaultBubblePaddingLeft) {
        this.incomingDefaultBubblePaddingLeft = incomingDefaultBubblePaddingLeft;
    }

    public void setIncomingDefaultBubblePaddingRight(int incomingDefaultBubblePaddingRight) {
        this.incomingDefaultBubblePaddingRight = incomingDefaultBubblePaddingRight;
    }

    public void setIncomingDefaultBubblePaddingTop(int incomingDefaultBubblePaddingTop) {
        this.incomingDefaultBubblePaddingTop = incomingDefaultBubblePaddingTop;
    }

    public void setIncomingDefaultBubblePaddingBottom(int incomingDefaultBubblePaddingBottom) {
        this.incomingDefaultBubblePaddingBottom = incomingDefaultBubblePaddingBottom;
    }

    public void setIncomingTextColor(int incomingTextColor) {
        this.incomingTextColor = incomingTextColor;
    }

    public void setIncomingTextSize(int incomingTextSize) {
        this.incomingTextSize = incomingTextSize;
    }

    public void setIncomingTextStyle(int incomingTextStyle) {
        this.incomingTextStyle = incomingTextStyle;
    }

    public void setIncomingTimeTextColor(int incomingTimeTextColor) {
        this.incomingTimeTextColor = incomingTimeTextColor;
    }

    public void setIncomingTimeTextSize(int incomingTimeTextSize) {
        this.incomingTimeTextSize = incomingTimeTextSize;
    }

    public void setIncomingTimeTextStyle(int incomingTimeTextStyle) {
        this.incomingTimeTextStyle = incomingTimeTextStyle;
    }

    public void setIncomingImageTimeTextColor(int incomingImageTimeTextColor) {
        this.incomingImageTimeTextColor = incomingImageTimeTextColor;
    }

    public void setIncomingImageTimeTextSize(int incomingImageTimeTextSize) {
        this.incomingImageTimeTextSize = incomingImageTimeTextSize;
    }

    public void setIncomingImageTimeTextStyle(int incomingImageTimeTextStyle) {
        this.incomingImageTimeTextStyle = incomingImageTimeTextStyle;
    }

    public void setOutcomingBubbleDrawable(int outcomingBubbleDrawable) {
        this.outcomingBubbleDrawable = outcomingBubbleDrawable;
    }

    public void setOutcomingDefaultBubbleColor(int outcomingDefaultBubbleColor) {
        this.outcomingDefaultBubbleColor = outcomingDefaultBubbleColor;
    }

    public void setOutcomingDefaultBubblePressedColor(int outcomingDefaultBubblePressedColor) {
        this.outcomingDefaultBubblePressedColor = outcomingDefaultBubblePressedColor;
    }

    public void setOutcomingDefaultBubbleSelectedColor(int outcomingDefaultBubbleSelectedColor) {
        this.outcomingDefaultBubbleSelectedColor = outcomingDefaultBubbleSelectedColor;
    }

    public void setOutcomingImageOverlayDrawable(int outcomingImageOverlayDrawable) {
        this.outcomingImageOverlayDrawable = outcomingImageOverlayDrawable;
    }

    public void setOutcomingDefaultImageOverlayPressedColor(int outcomingDefaultImageOverlayPressedColor) {
        this.outcomingDefaultImageOverlayPressedColor = outcomingDefaultImageOverlayPressedColor;
    }

    public void setOutcomingDefaultImageOverlaySelectedColor(int outcomingDefaultImageOverlaySelectedColor) {
        this.outcomingDefaultImageOverlaySelectedColor = outcomingDefaultImageOverlaySelectedColor;
    }

    public void setOutcomingDefaultBubblePaddingLeft(int outcomingDefaultBubblePaddingLeft) {
        this.outcomingDefaultBubblePaddingLeft = outcomingDefaultBubblePaddingLeft;
    }

    public void setOutcomingDefaultBubblePaddingRight(int outcomingDefaultBubblePaddingRight) {
        this.outcomingDefaultBubblePaddingRight = outcomingDefaultBubblePaddingRight;
    }

    public void setOutcomingDefaultBubblePaddingTop(int outcomingDefaultBubblePaddingTop) {
        this.outcomingDefaultBubblePaddingTop = outcomingDefaultBubblePaddingTop;
    }

    public void setOutcomingDefaultBubblePaddingBottom(int outcomingDefaultBubblePaddingBottom) {
        this.outcomingDefaultBubblePaddingBottom = outcomingDefaultBubblePaddingBottom;
    }

    public void setOutcomingTextColor(int outcomingTextColor) {
        this.outcomingTextColor = outcomingTextColor;
    }

    public void setOutcomingTextSize(int outcomingTextSize) {
        this.outcomingTextSize = outcomingTextSize;
    }

    public void setOutcomingTextStyle(int outcomingTextStyle) {
        this.outcomingTextStyle = outcomingTextStyle;
    }

    public void setOutcomingTimeTextColor(int outcomingTimeTextColor) {
        this.outcomingTimeTextColor = outcomingTimeTextColor;
    }

    public void setOutcomingTimeTextSize(int outcomingTimeTextSize) {
        this.outcomingTimeTextSize = outcomingTimeTextSize;
    }

    public void setOutcomingTimeTextStyle(int outcomingTimeTextStyle) {
        this.outcomingTimeTextStyle = outcomingTimeTextStyle;
    }

    public void setOutcomingImageTimeTextColor(int outcomingImageTimeTextColor) {
        this.outcomingImageTimeTextColor = outcomingImageTimeTextColor;
    }

    public void setOutcomingImageTimeTextSize(int outcomingImageTimeTextSize) {
        this.outcomingImageTimeTextSize = outcomingImageTimeTextSize;
    }

    public void setOutcomingImageTimeTextStyle(int outcomingImageTimeTextStyle) {
        this.outcomingImageTimeTextStyle = outcomingImageTimeTextStyle;
    }

    public void setDateHeaderPadding(int dateHeaderPadding) {
        this.dateHeaderPadding = dateHeaderPadding;
    }

    public void setDateHeaderFormat(String dateHeaderFormat) {
        this.dateHeaderFormat = dateHeaderFormat;
    }

    public void setDateHeaderTextColor(int dateHeaderTextColor) {
        this.dateHeaderTextColor = dateHeaderTextColor;
    }

    public void setDateHeaderTextSize(int dateHeaderTextSize) {
        this.dateHeaderTextSize = dateHeaderTextSize;
    }

    public void setDateHeaderTextStyle(int dateHeaderTextStyle) {
        this.dateHeaderTextStyle = dateHeaderTextStyle;
    }
}
