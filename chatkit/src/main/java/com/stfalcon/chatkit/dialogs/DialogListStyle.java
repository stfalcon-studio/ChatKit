package com.stfalcon.chatkit.dialogs;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;

import com.stfalcon.chatkit.R;
import com.stfalcon.chatkit.commons.Style;

/**
 * Created by Anton Bevza on 12/13/16.
 */

public class DialogListStyle extends Style {
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

    public static DialogListStyle parse(Context context, AttributeSet attrs) {
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

    public int getDialogTitleTextColor() {
        return dialogTitleTextColor;
    }

    public void setDialogTitleTextColor(int dialogTitleTextColor) {
        this.dialogTitleTextColor = dialogTitleTextColor;
    }

    public int getDialogTitleTextSize() {
        return dialogTitleTextSize;
    }

    public void setDialogTitleTextSize(int dialogTitleTextSize) {
        this.dialogTitleTextSize = dialogTitleTextSize;
    }

    public int getDialogTitleTextStyle() {
        return dialogTitleTextStyle;
    }

    public void setDialogTitleTextStyle(int dialogTitleTextStyle) {
        this.dialogTitleTextStyle = dialogTitleTextStyle;
    }

    public int getDialogUnreadTitleTextColor() {
        return dialogUnreadTitleTextColor;
    }

    public void setDialogUnreadTitleTextColor(int dialogUnreadTitleTextColor) {
        this.dialogUnreadTitleTextColor = dialogUnreadTitleTextColor;
    }

    public int getDialogUnreadTitleTextStyle() {
        return dialogUnreadTitleTextStyle;
    }

    public void setDialogUnreadTitleTextStyle(int dialogUnreadTitleTextStyle) {
        this.dialogUnreadTitleTextStyle = dialogUnreadTitleTextStyle;
    }

    public int getDialogMessageTextColor() {
        return dialogMessageTextColor;
    }

    public void setDialogMessageTextColor(int dialogMessageTextColor) {
        this.dialogMessageTextColor = dialogMessageTextColor;
    }

    public int getDialogMessageTextSize() {
        return dialogMessageTextSize;
    }

    public void setDialogMessageTextSize(int dialogMessageTextSize) {
        this.dialogMessageTextSize = dialogMessageTextSize;
    }

    public int getDialogMessageTextStyle() {
        return dialogMessageTextStyle;
    }

    public void setDialogMessageTextStyle(int dialogMessageTextStyle) {
        this.dialogMessageTextStyle = dialogMessageTextStyle;
    }

    public int getDialogUnreadMessageTextColor() {
        return dialogUnreadMessageTextColor;
    }

    public void setDialogUnreadMessageTextColor(int dialogUnreadMessageTextColor) {
        this.dialogUnreadMessageTextColor = dialogUnreadMessageTextColor;
    }

    public int getDialogUnreadMessageTextStyle() {
        return dialogUnreadMessageTextStyle;
    }

    public void setDialogUnreadMessageTextStyle(int dialogUnreadMessageTextStyle) {
        this.dialogUnreadMessageTextStyle = dialogUnreadMessageTextStyle;
    }

    public int getDialogDateColor() {
        return dialogDateColor;
    }

    public void setDialogDateColor(int dialogDateColor) {
        this.dialogDateColor = dialogDateColor;
    }

    public int getDialogDateSize() {
        return dialogDateSize;
    }

    public void setDialogDateSize(int dialogDateSize) {
        this.dialogDateSize = dialogDateSize;
    }

    public int getDialogDateStyle() {
        return dialogDateStyle;
    }

    public void setDialogDateStyle(int dialogDateStyle) {
        this.dialogDateStyle = dialogDateStyle;
    }

    public int getDialogUnreadDateColor() {
        return dialogUnreadDateColor;
    }

    public void setDialogUnreadDateColor(int dialogUnreadDateColor) {
        this.dialogUnreadDateColor = dialogUnreadDateColor;
    }

    public int getDialogUnreadDateStyle() {
        return dialogUnreadDateStyle;
    }

    public void setDialogUnreadDateStyle(int dialogUnreadDateStyle) {
        this.dialogUnreadDateStyle = dialogUnreadDateStyle;
    }

    public boolean isDialogUnreadBubbleEnabled() {
        return dialogUnreadBubbleEnabled;
    }

    public void setDialogUnreadBubbleEnabled(boolean dialogUnreadBubbleEnabled) {
        this.dialogUnreadBubbleEnabled = dialogUnreadBubbleEnabled;
    }

    public int getDialogUnreadBubbleTextColor() {
        return dialogUnreadBubbleTextColor;
    }

    public void setDialogUnreadBubbleTextColor(int dialogUnreadBubbleTextColor) {
        this.dialogUnreadBubbleTextColor = dialogUnreadBubbleTextColor;
    }

    public int getDialogUnreadBubbleTextSize() {
        return dialogUnreadBubbleTextSize;
    }

    public void setDialogUnreadBubbleTextSize(int dialogUnreadBubbleTextSize) {
        this.dialogUnreadBubbleTextSize = dialogUnreadBubbleTextSize;
    }

    public int getDialogUnreadBubbleTextStyle() {
        return dialogUnreadBubbleTextStyle;
    }

    public void setDialogUnreadBubbleTextStyle(int dialogUnreadBubbleTextStyle) {
        this.dialogUnreadBubbleTextStyle = dialogUnreadBubbleTextStyle;
    }

    public int getDialogUnreadBubbleBackgroundColor() {
        return dialogUnreadBubbleBackgroundColor;
    }

    public void setDialogUnreadBubbleBackgroundColor(int dialogUnreadBubbleBackgroundColor) {
        this.dialogUnreadBubbleBackgroundColor = dialogUnreadBubbleBackgroundColor;
    }

    public int getDialogAvatarWidth() {
        return dialogAvatarWidth;
    }

    public void setDialogAvatarWidth(int dialogAvatarWidth) {
        this.dialogAvatarWidth = dialogAvatarWidth;
    }

    public int getDialogAvatarHeight() {
        return dialogAvatarHeight;
    }

    public void setDialogAvatarHeight(int dialogAvatarHeight) {
        this.dialogAvatarHeight = dialogAvatarHeight;
    }

    public boolean isDialogDividerEnabled() {
        return dialogDividerEnabled;
    }

    public void setDialogDividerEnabled(boolean dialogDividerEnabled) {
        this.dialogDividerEnabled = dialogDividerEnabled;
    }

    public int getDialogDividerColor() {
        return dialogDividerColor;
    }

    public void setDialogDividerColor(int dialogDividerColor) {
        this.dialogDividerColor = dialogDividerColor;
    }

    public int getDialogDividerLeftPadding() {
        return dialogDividerLeftPadding;
    }

    public void setDialogDividerLeftPadding(int dialogDividerLeftMargin) {
        this.dialogDividerLeftPadding = dialogDividerLeftMargin;
    }

    public int getDialogDividerRightPadding() {
        return dialogDividerRightPadding;
    }

    public void setDialogDividerRightPadding(int dialogDividerRightMargin) {
        this.dialogDividerRightPadding = dialogDividerRightMargin;
    }

    public int getDialogItemBackground() {
        return dialogItemBackground;
    }

    public void setDialogItemBackground(int dialogItemBackground) {
        this.dialogItemBackground = dialogItemBackground;
    }

    public int getDialogUnreadItemBackground() {
        return dialogUnreadItemBackground;
    }

    public void setDialogUnreadItemBackground(int dialogUnreadItemBackground) {
        this.dialogUnreadItemBackground = dialogUnreadItemBackground;
    }

    public void setDialogMessageAvatarEnabled(boolean dialogMessageAvatarEnabled) {
        this.dialogMessageAvatarEnabled = dialogMessageAvatarEnabled;
    }

    public boolean isDialogMessageAvatarEnabled() {
        return dialogMessageAvatarEnabled;
    }

    public int getDialogMessageAvatarWidth() {
        return dialogMessageAvatarWidth;
    }

    public void setDialogMessageAvatarWidth(int dialogMessageAvatarWidth) {
        this.dialogMessageAvatarWidth = dialogMessageAvatarWidth;
    }

    public int getDialogMessageAvatarHeight() {
        return dialogMessageAvatarHeight;
    }

    public void setDialogMessageAvatarHeight(int dialogMessageAvatarHeight) {
        this.dialogMessageAvatarHeight = dialogMessageAvatarHeight;
    }
}
