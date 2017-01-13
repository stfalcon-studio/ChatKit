package com.stfalcon.chatkit.messages;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorInt;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.util.AttributeSet;

import com.stfalcon.chatkit.R;
import com.stfalcon.chatkit.commons.Style;

/*
 * Created by troy379 on 15.12.16.
 */
class MessageInputStyle extends Style {

    private static final int DEFAULT_MAX_LINES = 5;

    private Drawable inputButtonDrawable;
    private Drawable inputDefaultButtonDrawable;
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

        style.inputButtonDrawable = typedArray.getDrawable(R.styleable.MessageInput_inputButtonDrawable);
        style.inputDefaultButtonDrawable = style.getButtonSelector(typedArray.getColor(R.styleable.MessageInput_inputDefaultButtonColor, style.getSystemAccentColor()));
        style.inputButtonWidth = typedArray.getDimensionPixelSize(R.styleable.MessageInput_inputButtonWidth, style.getDimension(R.dimen.input_button_width));
        style.inputButtonHeight = typedArray.getDimensionPixelSize(R.styleable.MessageInput_inputButtonHeight, style.getDimension(R.dimen.input_button_height));
        style.inputButtonMargin = typedArray.getDimensionPixelSize(R.styleable.MessageInput_inputButtonMargin, style.getDimension(R.dimen.input_button_margin));
        style.inputMaxLines = typedArray.getInt(R.styleable.MessageInput_inputMaxLines, DEFAULT_MAX_LINES);
        style.inputHint = typedArray.getString(R.styleable.MessageInput_inputHint);
        style.inputText = typedArray.getString(R.styleable.MessageInput_inputText);
        style.inputTextSize = typedArray.getDimensionPixelSize(R.styleable.MessageInput_inputTextSize, style.getDimension(R.dimen.input_text_size));
        style.inputTextColor = typedArray.getColor(R.styleable.MessageInput_inputTextColor, style.getSystemPrimaryTextColor());
        style.inputHintColor = typedArray.getColor(R.styleable.MessageInput_inputHintColor, style.getSystemHintColor());
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

    Drawable getInputButtonDrawable() {
        return inputButtonDrawable != null ? inputButtonDrawable : inputDefaultButtonDrawable;
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

    private Drawable getButtonSelector(@ColorInt int color) {
        ColorDrawable normalColor = new ColorDrawable(color);
        ColorDrawable pressedColor = new ColorDrawable(color);
        ColorDrawable disabledColor = new ColorDrawable(Color.GRAY);
        pressedColor.setAlpha(200);

        Drawable button = DrawableCompat.wrap(getVectorDrawable(R.drawable.ic_send));
        DrawableCompat.setTintList(
                button,
                new ColorStateList(
                        new int[][]{
                                new int[]{android.R.attr.state_enabled, -android.R.attr.state_pressed},
                                new int[]{android.R.attr.state_enabled, android.R.attr.state_pressed},
                                new int[]{-android.R.attr.state_enabled}
                        },
                        new int[]{normalColor.getColor(), pressedColor.getColor(), disabledColor.getColor()}
                ));
        return button;
    }
}
