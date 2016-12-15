package com.stfalcon.chatkit.features.messages.widgets;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.widget.Space;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.stfalcon.chatkit.R;

import java.lang.reflect.Field;

/*
 * Created by troy379 on 13.12.16.
 */
public class MessageInput extends RelativeLayout
        implements View.OnClickListener, TextWatcher {

    private EditText messageInput;
    private Button messageSendButton;
    private Space buttonSpace;

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

    public void setInputListener(InputListener inputListener) {
        this.inputListener = inputListener;
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

    private boolean onSubmit() {
        return inputListener != null && inputListener.onSubmit(input);
    }

    private void init(Context context, AttributeSet attrs) {
        init(context);
        MessageInputStyle style = MessageInputStyle.parse(context, attrs);

        this.messageInput.setMaxLines(style.getInputMaxLines());
        this.messageInput.setHint(style.getInputHint());
        this.messageInput.setText(style.getInputText());
        this.messageInput.setTextSize(TypedValue.COMPLEX_UNIT_PX, style.getInputTextSize());
        this.messageInput.setTextColor(style.getInputTextColor());
        this.messageInput.setHintTextColor(style.getInputHintColor());
        this.messageInput.setBackground(style.getInputBackground());
        setCursor(style.getInputCursorDrawable());

        this.messageSendButton.setBackground(style.getInputButtonDrawable());
        this.messageSendButton.getLayoutParams().width = style.getInputButtonWidth();
        this.messageSendButton.getLayoutParams().height = style.getInputButtonHeight();
        this.buttonSpace.getLayoutParams().width = style.getInputButtonMargin();

        if (getPaddingLeft() == 0
                && getPaddingRight() == 0
                && getPaddingTop() == 0
                && getPaddingBottom() == 0) {
            setPadding(
                    style.getInputDefaultPaddingLeft(),
                    style.getInputDefaultPaddingTop(),
                    style.getInputDefaultPaddingRight(),
                    style.getInputDefaultPaddingBottom()
            );
        }
    }

    private void init(Context context) {
        inflate(context, R.layout.view_message_input, this);

        messageInput = (EditText) findViewById(R.id.messageInput);
        messageSendButton = (Button) findViewById(R.id.messageSendButton);
        buttonSpace = (Space) findViewById(R.id.buttonSpace);

        messageSendButton.setOnClickListener(this);
        messageInput.addTextChangedListener(this);
        messageInput.setText("");
    }

    private void setCursor(Drawable drawable) {
        try {
            Field f = TextView.class.getDeclaredField("mCursorDrawableRes");
            f.setAccessible(true);
            f.set(this.messageInput, drawable);
        } catch (Exception ignore) {
        }
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
}
