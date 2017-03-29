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

package com.stfalcon.chatkit.dialogs;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;

import com.stfalcon.chatkit.R;
import com.stfalcon.chatkit.commons.Style;

/**
 * Style for DialogList customization by xml attributes
 */

class DialogListStyle extends Style {

    private int dialogTitleTextColor;
    private int dialogTitleTextSize;
    private int dialogTitleTextStyle;
    private int dialogUnreadTitleTextColor;
    private int dialogUnreadTitleTextStyle;

    private int dialogMessageTextColor;
    private int dialogMessageTextSize;
    private int dialogMessageTextStyle;
    private int dialogUnreadMessageTextColor;
    private int dialogUnreadMessageTextStyle;

    private int dialogDateColor;
    private int dialogDateSize;
    private int dialogDateStyle;
    private int dialogUnreadDateColor;
    private int dialogUnreadDateStyle;

    private boolean dialogUnreadBubbleEnabled;
    private int dialogUnreadBubbleTextColor;
    private int dialogUnreadBubbleTextSize;
    private int dialogUnreadBubbleTextStyle;
    private int dialogUnreadBubbleBackgroundColor;

    private int dialogAvatarWidth;
    private int dialogAvatarHeight;

    private boolean dialogMessageAvatarEnabled;
    private int dialogMessageAvatarWidth;
    private int dialogMessageAvatarHeight;

    private boolean dialogDividerEnabled;
    private int dialogDividerColor;
    private int dialogDividerLeftPadding;
    private int dialogDividerRightPadding;

    private int dialogItemBackground;
    private int dialogUnreadItemBackground;

    static DialogListStyle parse(Context context, AttributeSet attrs) {
        DialogListStyle dialogStyle = new DialogListStyle(context, attrs);

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.DialogsList);

        //Item background
        dialogStyle.setDialogItemBackground(typedArray.getColor(R.styleable.DialogsList_dialogItemBackground,
                ContextCompat.getColor(context, R.color.transparent)));
        dialogStyle.setDialogUnreadItemBackground(typedArray.getColor(R.styleable.DialogsList_dialogUnreadItemBackground,
                ContextCompat.getColor(context, R.color.transparent)));
        //Title text
        dialogStyle.setDialogTitleTextColor(typedArray.getColor(R.styleable.DialogsList_dialogTitleTextColor,
                ContextCompat.getColor(context, R.color.dialog_title_text)));
        dialogStyle.setDialogTitleTextSize(typedArray.getDimensionPixelSize(R.styleable.DialogsList_dialogTitleTextSize,
                context.getResources().getDimensionPixelSize(R.dimen.dialog_title_text_size)));
        dialogStyle.setDialogTitleTextStyle(typedArray.getInt(R.styleable.DialogsList_dialogTitleTextStyle, Typeface.NORMAL));
        //Title unread text
        dialogStyle.setDialogUnreadTitleTextColor(typedArray.getColor(R.styleable.DialogsList_dialogUnreadTitleTextColor,
                ContextCompat.getColor(context, R.color.dialog_title_text)));
        dialogStyle.setDialogUnreadTitleTextStyle(typedArray.getInt(R.styleable.DialogsList_dialogUnreadTitleTextStyle, Typeface.NORMAL));

        //Message text
        dialogStyle.setDialogMessageTextColor(typedArray.getColor(R.styleable.DialogsList_dialogMessageTextColor,
                ContextCompat.getColor(context, R.color.dialog_message_text)));
        dialogStyle.setDialogMessageTextSize(typedArray.getDimensionPixelSize(R.styleable.DialogsList_dialogMessageTextSize,
                context.getResources().getDimensionPixelSize(R.dimen.dialog_message_text_size)));
        dialogStyle.setDialogMessageTextStyle(typedArray.getInt(R.styleable.DialogsList_dialogMessageTextStyle, Typeface.NORMAL));
        //Message unread text
        dialogStyle.setDialogUnreadMessageTextColor(typedArray.getColor(R.styleable.DialogsList_dialogUnreadMessageTextColor,
                ContextCompat.getColor(context, R.color.dialog_message_text)));
        dialogStyle.setDialogUnreadMessageTextStyle(typedArray.getInt(R.styleable.DialogsList_dialogUnreadMessageTextStyle, Typeface.NORMAL));

        //Date text
        dialogStyle.setDialogDateColor(typedArray.getColor(R.styleable.DialogsList_dialogDateColor,
                ContextCompat.getColor(context, R.color.dialog_date_text)));
        dialogStyle.setDialogDateSize(typedArray.getDimensionPixelSize(R.styleable.DialogsList_dialogDateSize,
                context.getResources().getDimensionPixelSize(R.dimen.dialog_date_text_size)));
        dialogStyle.setDialogDateStyle(typedArray.getInt(R.styleable.DialogsList_dialogDateStyle, Typeface.NORMAL));
        //Date unread text
        dialogStyle.setDialogUnreadDateColor(typedArray.getColor(R.styleable.DialogsList_dialogUnreadDateColor,
                ContextCompat.getColor(context, R.color.dialog_date_text)));
        dialogStyle.setDialogUnreadDateStyle(typedArray.getInt(R.styleable.DialogsList_dialogUnreadDateStyle, Typeface.NORMAL));

        //Unread bubble
        dialogStyle.setDialogUnreadBubbleEnabled(typedArray.getBoolean(R.styleable.DialogsList_dialogUnreadBubbleEnabled, true));
        dialogStyle.setDialogUnreadBubbleBackgroundColor(typedArray.getColor(R.styleable.DialogsList_dialogUnreadBubbleBackgroundColor,
                ContextCompat.getColor(context, R.color.dialog_unread_bubble)));

        //Unread bubble text
        dialogStyle.setDialogUnreadBubbleTextColor(typedArray.getColor(R.styleable.DialogsList_dialogUnreadBubbleTextColor,
                ContextCompat.getColor(context, R.color.dialog_unread_text)));
        dialogStyle.setDialogUnreadBubbleTextSize(typedArray.getDimensionPixelSize(R.styleable.DialogsList_dialogUnreadBubbleTextSize,
                context.getResources().getDimensionPixelSize(R.dimen.dialog_unread_bubble_text_size)));
        dialogStyle.setDialogUnreadBubbleTextStyle(typedArray.getInt(R.styleable.DialogsList_dialogUnreadBubbleTextStyle, Typeface.NORMAL));

        //Avatar
        dialogStyle.setDialogAvatarWidth(typedArray.getDimensionPixelSize(R.styleable.DialogsList_dialogAvatarWidth,
                context.getResources().getDimensionPixelSize(R.dimen.dialog_avatar_width)));
        dialogStyle.setDialogAvatarHeight(typedArray.getDimensionPixelSize(R.styleable.DialogsList_dialogAvatarHeight,
                context.getResources().getDimensionPixelSize(R.dimen.dialog_avatar_height)));

        //Last message avatar
        dialogStyle.setDialogMessageAvatarEnabled(typedArray.getBoolean(R.styleable.DialogsList_dialogMessageAvatarEnabled, true));
        dialogStyle.setDialogMessageAvatarWidth(typedArray.getDimensionPixelSize(R.styleable.DialogsList_dialogMessageAvatarWidth,
                context.getResources().getDimensionPixelSize(R.dimen.dialog_last_message_avatar_width)));
        dialogStyle.setDialogMessageAvatarHeight(typedArray.getDimensionPixelSize(R.styleable.DialogsList_dialogMessageAvatarHeight,
                context.getResources().getDimensionPixelSize(R.dimen.dialog_last_message_avatar_height)));

        //Divider
        dialogStyle.setDialogDividerEnabled(typedArray.getBoolean(R.styleable.DialogsList_dialogDividerEnabled, true));
        dialogStyle.setDialogDividerColor(typedArray.getColor(R.styleable.DialogsList_dialogDividerColor, ContextCompat.getColor(context, R.color.dialog_divider)));
        dialogStyle.setDialogDividerLeftPadding(typedArray.getDimensionPixelSize(R.styleable.DialogsList_dialogDividerLeftPadding,
                context.getResources().getDimensionPixelSize(R.dimen.dialog_divider_margin_left)));
        dialogStyle.setDialogDividerRightPadding(typedArray.getDimensionPixelSize(R.styleable.DialogsList_dialogDividerRightPadding,
                context.getResources().getDimensionPixelSize(R.dimen.dialog_divider_margin_right)));

        typedArray.recycle();

        return dialogStyle;
    }

    private DialogListStyle(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    int getDialogTitleTextColor() {
        return dialogTitleTextColor;
    }

    void setDialogTitleTextColor(int dialogTitleTextColor) {
        this.dialogTitleTextColor = dialogTitleTextColor;
    }

    int getDialogTitleTextSize() {
        return dialogTitleTextSize;
    }

    void setDialogTitleTextSize(int dialogTitleTextSize) {
        this.dialogTitleTextSize = dialogTitleTextSize;
    }

    int getDialogTitleTextStyle() {
        return dialogTitleTextStyle;
    }

    void setDialogTitleTextStyle(int dialogTitleTextStyle) {
        this.dialogTitleTextStyle = dialogTitleTextStyle;
    }

    int getDialogUnreadTitleTextColor() {
        return dialogUnreadTitleTextColor;
    }

    void setDialogUnreadTitleTextColor(int dialogUnreadTitleTextColor) {
        this.dialogUnreadTitleTextColor = dialogUnreadTitleTextColor;
    }

    int getDialogUnreadTitleTextStyle() {
        return dialogUnreadTitleTextStyle;
    }

    void setDialogUnreadTitleTextStyle(int dialogUnreadTitleTextStyle) {
        this.dialogUnreadTitleTextStyle = dialogUnreadTitleTextStyle;
    }

    int getDialogMessageTextColor() {
        return dialogMessageTextColor;
    }

    void setDialogMessageTextColor(int dialogMessageTextColor) {
        this.dialogMessageTextColor = dialogMessageTextColor;
    }

    int getDialogMessageTextSize() {
        return dialogMessageTextSize;
    }

    void setDialogMessageTextSize(int dialogMessageTextSize) {
        this.dialogMessageTextSize = dialogMessageTextSize;
    }

    int getDialogMessageTextStyle() {
        return dialogMessageTextStyle;
    }

    void setDialogMessageTextStyle(int dialogMessageTextStyle) {
        this.dialogMessageTextStyle = dialogMessageTextStyle;
    }

    int getDialogUnreadMessageTextColor() {
        return dialogUnreadMessageTextColor;
    }

    void setDialogUnreadMessageTextColor(int dialogUnreadMessageTextColor) {
        this.dialogUnreadMessageTextColor = dialogUnreadMessageTextColor;
    }

    int getDialogUnreadMessageTextStyle() {
        return dialogUnreadMessageTextStyle;
    }

    void setDialogUnreadMessageTextStyle(int dialogUnreadMessageTextStyle) {
        this.dialogUnreadMessageTextStyle = dialogUnreadMessageTextStyle;
    }

    int getDialogDateColor() {
        return dialogDateColor;
    }

    void setDialogDateColor(int dialogDateColor) {
        this.dialogDateColor = dialogDateColor;
    }

    int getDialogDateSize() {
        return dialogDateSize;
    }

    void setDialogDateSize(int dialogDateSize) {
        this.dialogDateSize = dialogDateSize;
    }

    int getDialogDateStyle() {
        return dialogDateStyle;
    }

    void setDialogDateStyle(int dialogDateStyle) {
        this.dialogDateStyle = dialogDateStyle;
    }

    int getDialogUnreadDateColor() {
        return dialogUnreadDateColor;
    }

    void setDialogUnreadDateColor(int dialogUnreadDateColor) {
        this.dialogUnreadDateColor = dialogUnreadDateColor;
    }

    int getDialogUnreadDateStyle() {
        return dialogUnreadDateStyle;
    }

    void setDialogUnreadDateStyle(int dialogUnreadDateStyle) {
        this.dialogUnreadDateStyle = dialogUnreadDateStyle;
    }

    boolean isDialogUnreadBubbleEnabled() {
        return dialogUnreadBubbleEnabled;
    }

    void setDialogUnreadBubbleEnabled(boolean dialogUnreadBubbleEnabled) {
        this.dialogUnreadBubbleEnabled = dialogUnreadBubbleEnabled;
    }

    int getDialogUnreadBubbleTextColor() {
        return dialogUnreadBubbleTextColor;
    }

    void setDialogUnreadBubbleTextColor(int dialogUnreadBubbleTextColor) {
        this.dialogUnreadBubbleTextColor = dialogUnreadBubbleTextColor;
    }

    int getDialogUnreadBubbleTextSize() {
        return dialogUnreadBubbleTextSize;
    }

    void setDialogUnreadBubbleTextSize(int dialogUnreadBubbleTextSize) {
        this.dialogUnreadBubbleTextSize = dialogUnreadBubbleTextSize;
    }

    int getDialogUnreadBubbleTextStyle() {
        return dialogUnreadBubbleTextStyle;
    }

    void setDialogUnreadBubbleTextStyle(int dialogUnreadBubbleTextStyle) {
        this.dialogUnreadBubbleTextStyle = dialogUnreadBubbleTextStyle;
    }

    int getDialogUnreadBubbleBackgroundColor() {
        return dialogUnreadBubbleBackgroundColor;
    }

    void setDialogUnreadBubbleBackgroundColor(int dialogUnreadBubbleBackgroundColor) {
        this.dialogUnreadBubbleBackgroundColor = dialogUnreadBubbleBackgroundColor;
    }

    int getDialogAvatarWidth() {
        return dialogAvatarWidth;
    }

    void setDialogAvatarWidth(int dialogAvatarWidth) {
        this.dialogAvatarWidth = dialogAvatarWidth;
    }

    int getDialogAvatarHeight() {
        return dialogAvatarHeight;
    }

    void setDialogAvatarHeight(int dialogAvatarHeight) {
        this.dialogAvatarHeight = dialogAvatarHeight;
    }

    boolean isDialogDividerEnabled() {
        return dialogDividerEnabled;
    }

    void setDialogDividerEnabled(boolean dialogDividerEnabled) {
        this.dialogDividerEnabled = dialogDividerEnabled;
    }

    int getDialogDividerColor() {
        return dialogDividerColor;
    }

    void setDialogDividerColor(int dialogDividerColor) {
        this.dialogDividerColor = dialogDividerColor;
    }

    int getDialogDividerLeftPadding() {
        return dialogDividerLeftPadding;
    }

    void setDialogDividerLeftPadding(int dialogDividerLeftMargin) {
        this.dialogDividerLeftPadding = dialogDividerLeftMargin;
    }

    int getDialogDividerRightPadding() {
        return dialogDividerRightPadding;
    }

    void setDialogDividerRightPadding(int dialogDividerRightMargin) {
        this.dialogDividerRightPadding = dialogDividerRightMargin;
    }

    int getDialogItemBackground() {
        return dialogItemBackground;
    }

    void setDialogItemBackground(int dialogItemBackground) {
        this.dialogItemBackground = dialogItemBackground;
    }

    int getDialogUnreadItemBackground() {
        return dialogUnreadItemBackground;
    }

    void setDialogUnreadItemBackground(int dialogUnreadItemBackground) {
        this.dialogUnreadItemBackground = dialogUnreadItemBackground;
    }

    void setDialogMessageAvatarEnabled(boolean dialogMessageAvatarEnabled) {
        this.dialogMessageAvatarEnabled = dialogMessageAvatarEnabled;
    }

    boolean isDialogMessageAvatarEnabled() {
        return dialogMessageAvatarEnabled;
    }

    int getDialogMessageAvatarWidth() {
        return dialogMessageAvatarWidth;
    }

    void setDialogMessageAvatarWidth(int dialogMessageAvatarWidth) {
        this.dialogMessageAvatarWidth = dialogMessageAvatarWidth;
    }

    int getDialogMessageAvatarHeight() {
        return dialogMessageAvatarHeight;
    }

    void setDialogMessageAvatarHeight(int dialogMessageAvatarHeight) {
        this.dialogMessageAvatarHeight = dialogMessageAvatarHeight;
    }
}
