package com.stfalcon.chatkit.features.dialogs.widgets;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SimpleItemAnimator;
import android.util.AttributeSet;

import com.stfalcon.chatkit.R;
import com.stfalcon.chatkit.features.dialogs.adapters.DialogsListAdapter;

/**
 * Created by Anton Bevza on 12/9/16.
 */

public class DialogsListView extends RecyclerView {
    private DialogStyle dialogStyle;

    public DialogsListView(Context context) {
        super(context);
    }

    public DialogsListView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DialogsListView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        parseStyle(context, attrs, defStyle);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();

        LinearLayoutManager layout = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        SimpleItemAnimator animator = new DefaultItemAnimator();

        setLayoutManager(layout);
        setItemAnimator(animator);
    }

    @Override
    public void setAdapter(Adapter adapter) {
        if (!(adapter instanceof DialogsListAdapter)) {
            throw new ClassCastException("Only for DialogsListAdapter");
        }
        ((DialogsListAdapter) adapter).setStyle(dialogStyle);
        super.setAdapter(adapter);
    }

    @SuppressWarnings("ResourceType")
    private void parseStyle(Context context, AttributeSet attrs, int defStyle) {
        dialogStyle = new DialogStyle();
        TypedArray ta = context.getTheme().obtainStyledAttributes(attrs, R.styleable.DialogsListView, R.attr.DialogsListView, defStyle);
        //Item background
        dialogStyle.setDialogItemBackground(ta.getColor(R.styleable.DialogsListView_dialogItemBackground,
                ContextCompat.getColor(context, R.color.transparent)));
        dialogStyle.setDialogUnreadItemBackground(ta.getColor(R.styleable.DialogsListView_dialogUnreadItemBackground,
                ContextCompat.getColor(context, R.color.transparent)));
        //Title text
        dialogStyle.setDialogTitleTextColor(ta.getColor(R.styleable.DialogsListView_dialogTitleTextColor,
                ContextCompat.getColor(context, R.color.black)));
        dialogStyle.setDialogTitleTextSize(ta.getDimensionPixelSize(R.styleable.DialogsListView_dialogTitleTextSize,
                context.getResources().getDimensionPixelSize(R.dimen.dialog_title_text_size)));
        dialogStyle.setDialogTitleTextStyle(ta.getInt(R.styleable.DialogsListView_dialogTitleTextStyle, Typeface.NORMAL));
        //Title unread text
        dialogStyle.setDialogUnreadTitleTextColor(ta.getColor(R.styleable.DialogsListView_dialogUnreadTitleTextColor,
                ContextCompat.getColor(context, R.color.black)));
        dialogStyle.setDialogUnreadTitleTextStyle(ta.getInt(R.styleable.DialogsListView_dialogUnreadTitleTextStyle, Typeface.BOLD));

        //Message text
        dialogStyle.setDialogMessageTextColor(ta.getColor(R.styleable.DialogsListView_dialogMessageTextColor,
                ContextCompat.getColor(context, R.color.black)));
        dialogStyle.setDialogMessageTextSize(ta.getDimensionPixelSize(R.styleable.DialogsListView_dialogMessageTextSize,
                context.getResources().getDimensionPixelSize(R.dimen.dialog_message_text_size)));
        dialogStyle.setDialogMessageTextStyle(ta.getInt(R.styleable.DialogsListView_dialogMessageTextStyle, Typeface.NORMAL));
        //Message unread text
        dialogStyle.setDialogUnreadMessageTextColor(ta.getColor(R.styleable.DialogsListView_dialogUnreadMessageTextColor,
                ContextCompat.getColor(context, R.color.black)));
        dialogStyle.setDialogUnreadMessageTextStyle(ta.getInt(R.styleable.DialogsListView_dialogUnreadMessageTextStyle, Typeface.NORMAL));

        //Date text
        dialogStyle.setDialogDateColor(ta.getColor(R.styleable.DialogsListView_dialogDateColor,
                ContextCompat.getColor(context, R.color.black)));
        dialogStyle.setDialogDateSize(ta.getDimensionPixelSize(R.styleable.DialogsListView_dialogDateSize,
                context.getResources().getDimensionPixelSize(R.dimen.dialog_date_text_size)));
        dialogStyle.setDialogDateStyle(ta.getInt(R.styleable.DialogsListView_dialogDateStyle, Typeface.NORMAL));
        //Date unread text
        dialogStyle.setDialogUnreadDateColor(ta.getColor(R.styleable.DialogsListView_dialogUnreadDateColor,
                ContextCompat.getColor(context, R.color.black)));
        dialogStyle.setDialogUnreadDateStyle(ta.getInt(R.styleable.DialogsListView_dialogUnreadDateStyle, Typeface.BOLD));

        //Unread bubble
        dialogStyle.setDialogUnreadBubbleEnabled(ta.getBoolean(R.styleable.DialogsListView_dialogUnreadBubbleEnabled, true));
        dialogStyle.setDialogUnreadBubbleBackgroundColor(ta.getColor(R.styleable.DialogsListView_dialogUnreadBubbleBackgroundColor,
                ContextCompat.getColor(context, R.color.blue)));

        //Unread bubble text
        dialogStyle.setDialogUnreadBubbleTextColor(ta.getColor(R.styleable.DialogsListView_dialogUnreadBubbleTextColor,
                ContextCompat.getColor(context, R.color.white)));
        dialogStyle.setDialogUnreadBubbleTextSize(ta.getDimensionPixelSize(R.styleable.DialogsListView_dialogUnreadBubbleTextSize,
                context.getResources().getDimensionPixelSize(R.dimen.dialog_unread_bubble_text_size)));
        dialogStyle.setDialogUnreadBubbleTextStyle(ta.getInt(R.styleable.DialogsListView_dialogUnreadBubbleTextStyle, Typeface.NORMAL));

        //Avatar
        dialogStyle.setDialogAvatarWidth(ta.getDimensionPixelSize(R.styleable.DialogsListView_dialogAvatarWidth,
                context.getResources().getDimensionPixelSize(R.dimen.dialog_avatar_width)));
        dialogStyle.setDialogAvatarHeight(ta.getDimensionPixelSize(R.styleable.DialogsListView_dialogAvatarHeight,
                context.getResources().getDimensionPixelSize(R.dimen.dialog_avatar_height)));

        //Last message avatar
        dialogStyle.setDialogMessageAvatarEnabled(ta.getBoolean(R.styleable.DialogsListView_dialogMessageAvatarEnabled, true));
        dialogStyle.setDialogMessageAvatarWidth(ta.getDimensionPixelSize(R.styleable.DialogsListView_dialogMessageAvatarWidth,
                context.getResources().getDimensionPixelSize(R.dimen.dialog_last_message_avatar_width)));
        dialogStyle.setDialogMessageAvatarHeight(ta.getDimensionPixelSize(R.styleable.DialogsListView_dialogMessageAvatarHeight,
                context.getResources().getDimensionPixelSize(R.dimen.dialog_last_message_avatar_height)));

        //Divider
        dialogStyle.setDialogDividerEnabled(ta.getBoolean(R.styleable.DialogsListView_dialogDividerEnabled, true));
        dialogStyle.setDialogDividerColor(ta.getColor(R.styleable.DialogsListView_dialogDividerColor, ContextCompat.getColor(context, R.color.black50)));
        dialogStyle.setDialogDividerLeftPadding(ta.getDimensionPixelSize(R.styleable.DialogsListView_dialogDividerLeftPadding,
                context.getResources().getDimensionPixelSize(R.dimen.dialog_divider_margin_left)));
        dialogStyle.setDialogDividerRightPadding(ta.getDimensionPixelSize(R.styleable.DialogsListView_dialogDividerRightPadding,
                context.getResources().getDimensionPixelSize(R.dimen.dialog_divider_margin_right)));
    }
}
