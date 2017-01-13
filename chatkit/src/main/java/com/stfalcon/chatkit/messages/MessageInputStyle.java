package com.stfalcon.chatkit.messages;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;

import com.stfalcon.chatkit.R;
import com.stfalcon.chatkit.commons.Style;

/*
 * Created by troy379 on 15.12.16.
 */
class MessageInputStyle extends Style {

    private static final int DEFAULT_MAX_LINES = 5;

    private Drawable inputButtonBackground;
    private Drawable inputButtonIcon;
    private int inputButtonWidth;
    private int inputButtonHeight;
    private int inputButtonMargin;
    private int inputMaxLines;
    private String inputHint;
    private String inputText;
    private int inputTextSize;
    private int inputTextColor;
    private int inputHintColor;
    private Drawable inputBackground;
    private Drawable inputCursorDrawable;

    private int inputDefaultPaddingLeft;
    private int inputDefaultPaddingRight;
    private int inputDefaultPaddingTop;
    private int inputDefaultPaddingBottom;

    static MessageInputStyle parse(Context context, AttributeSet attrs) {
        MessageInputStyle style = new MessageInputStyle(context, attrs);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.MessageInput);

        style.inputButtonBackground = typedArray.getDrawable(R.styleable.MessageInput_inputButtonBackground);
        if (style.inputButtonBackground == null) {
            style.inputButtonBackground = ContextCompat.getDrawable(context, R.drawable.selector_bg_send);
        }
        style.inputButtonIcon = typedArray.getDrawable(R.styleable.MessageInput_inputButtonIcon);
        if (style.inputButtonIcon == null) {
            style.inputButtonIcon = ContextCompat.getDrawable(context, R.drawable.selector_icon_send);
        }
        style.inputButtonWidth = typedArray.getDimensionPixelSize(R.styleable.MessageInput_inputButtonWidth, style.getDimension(R.dimen.input_button_width));
        style.inputButtonHeight = typedArray.getDimensionPixelSize(R.styleable.MessageInput_inputButtonHeight, style.getDimension(R.dimen.input_button_height));
        style.inputButtonMargin = typedArray.getDimensionPixelSize(R.styleable.MessageInput_inputButtonMargin, style.getDimension(R.dimen.input_button_margin));
        style.inputMaxLines = typedArray.getInt(R.styleable.MessageInput_inputMaxLines, DEFAULT_MAX_LINES);
        style.inputHint = typedArray.getString(R.styleable.MessageInput_inputHint);
        style.inputText = typedArray.getString(R.styleable.MessageInput_inputText);
        style.inputTextSize = typedArray.getDimensionPixelSize(R.styleable.MessageInput_inputTextSize, style.getDimension(R.dimen.input_text_size));
        style.inputTextColor = typedArray.getColor(R.styleable.MessageInput_inputTextColor, ContextCompat.getColor(context, R.color.dark_grey_two));
        style.inputHintColor = typedArray.getColor(R.styleable.MessageInput_inputHintColor, ContextCompat.getColor(context, R.color.warm_grey_three));
        style.inputBackground = typedArray.getDrawable(R.styleable.MessageInput_inputBackground);
        style.inputCursorDrawable = typedArray.getDrawable(R.styleable.MessageInput_inputCursorDrawable);

        typedArray.recycle();

        style.inputDefaultPaddingLeft = style.getDimension(R.dimen.input_padding_left);
        style.inputDefaultPaddingRight = style.getDimension(R.dimen.input_padding_right);
        style.inputDefaultPaddingTop = style.getDimension(R.dimen.input_padding_top);
        style.inputDefaultPaddingBottom = style.getDimension(R.dimen.input_padding_bottom);

        return style;
    }

    private MessageInputStyle(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    Drawable getInputButtonBackground() {
        return inputButtonBackground;
    }

    int getInputButtonMargin() {
        return inputButtonMargin;
    }

    int getInputButtonWidth() {
        return inputButtonWidth;
    }

    int getInputButtonHeight() {
        return inputButtonHeight;
    }

    int getInputMaxLines() {
        return inputMaxLines;
    }

    String getInputHint() {
        return inputHint;
    }

    String getInputText() {
        return inputText;
    }

    int getInputTextSize() {
        return inputTextSize;
    }

    int getInputTextColor() {
        return inputTextColor;
    }

    int getInputHintColor() {
        return inputHintColor;
    }

    Drawable getInputBackground() {
        return inputBackground;
    }

    Drawable getInputCursorDrawable() {
        return inputCursorDrawable;
    }

    int getInputDefaultPaddingLeft() {
        return inputDefaultPaddingLeft;
    }

    int getInputDefaultPaddingRight() {
        return inputDefaultPaddingRight;
    }

    int getInputDefaultPaddingTop() {
        return inputDefaultPaddingTop;
    }

    int getInputDefaultPaddingBottom() {
        return inputDefaultPaddingBottom;
    }

    Drawable getInputButtonIcon() {
        return inputButtonIcon;
    }

}
