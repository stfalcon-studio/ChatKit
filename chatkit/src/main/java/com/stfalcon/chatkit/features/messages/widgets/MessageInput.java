package com.stfalcon.chatkit.features.messages.widgets;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.stfalcon.chatkit.R;

/*
 * Created by troy379 on 13.12.16.
 */
public class MessageInput extends RelativeLayout
        implements View.OnClickListener, TextWatcher {

    private static final int DEFAULT_MAX_LINES = 5;

    private EditText messageInput;
    private Button messageSendButton;

    private CharSequence input;
    private InputListener inputListener;

    public MessageInput(Context context) {
        super(context);
        init(context);
    }

    public MessageInput(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public MessageInput(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.messageSendButton) {
            boolean isSubmitted = onSubmit();
            if (isSubmitted) {
                messageInput.setText("");
            }
        }
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        input = charSequence;
        messageSendButton.setEnabled(input.length() > 0);
    }

    @Override
    public void afterTextChanged(Editable editable) {

    }

    public void setInputListener(InputListener inputListener) {
        this.inputListener = inputListener;
    }

    private boolean onSubmit() {
        if (inputListener != null) {
            return inputListener.onSubmit(input);
        }
        return false;
    }

    private void init(Context context, AttributeSet attrs) {
        init(context);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.MessageInput);
        int maxLines = typedArray.getInt(R.styleable.MessageInput_android_maxLines, DEFAULT_MAX_LINES);

        int textSize = typedArray.getDimensionPixelSize(R.styleable.MessageInput_android_textSize, 0);
        ColorStateList textColor = typedArray.getColorStateList(R.styleable.MessageInput_android_textColor);
        ColorStateList hintColor = typedArray.getColorStateList(R.styleable.MessageInput_android_textColorHint);
        CharSequence hint = typedArray.getText(R.styleable.MessageInput_android_hint);
        CharSequence text = typedArray.getText(R.styleable.MessageInput_android_text);
        Drawable button = typedArray.getDrawable(R.styleable.MessageInput_android_button);

        typedArray.recycle();

        messageInput.setMaxLines(maxLines);
        if (hint != null) messageInput.setHint(hint);
        if (text != null) messageInput.setText(text);
        if (textSize > 0) messageInput.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
        if (textColor != null) messageInput.setTextColor(textColor);
        if (hintColor != null) messageInput.setHintTextColor(hintColor);
        if (button != null) messageSendButton.setBackground(button);
    }

    private void init(Context context) {
        inflate(context, R.layout.view_message_input, this);

        messageInput = (EditText) findViewById(R.id.messageInput);
        messageSendButton = (Button) findViewById(R.id.messageSendButton);

        messageSendButton.setOnClickListener(this);
        messageInput.addTextChangedListener(this);
        messageInput.setText("");
    }

    /**
     * Interface definition for a callback to be invoked when user entered his input
     */
    public interface InputListener {

        /**
         * Fires when user press send button.
         *
         * @param input input entered by user
         * @return if input text is valid, you must return {@code true} and input will be cleared, otherwise return false.
         */
        boolean onSubmit(CharSequence input);
    }

    /*
    * TODO: 13.12.16 customization:
    * default drawable color
    * button margin
    * */
}
