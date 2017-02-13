# MessageInput

MessageInput is a subsidiary component for entering text messages, it supports a simple validation and processes all “submit” button’s states. Also, it supports enough attributes for appearance stylization.

#### Add to your markup

To add `MessageInput` to layout is as simple, as a common `EditText`:

```xml
<com.stfalcon.chatkit.messages.MessageInput
   android:id="@+id/input"
   android:layout_width="match_parent"
   android:layout_height="wrap_content"
   android:layout_alignParentBottom="true"
   app:inputHint="@string/hint_enter_a_message"/>
```

For input hint you need to use `inputHint` attribute.

#### Validate and submit

Message validation have two simple rules:
* if input is empty, the button disables.
* when pressing submit after typing text — widget returns text to check. A listener returns `true`, if the text is valid, and the text is cleared. If else — nothing happens.

```java
inputView.setInputListener(new MessageInput.InputListener() {
   @Override
   public boolean onSubmit(CharSequence input) {
       //validate and send message
       adapter.addToStart(message, true);
       return true;
   }
});
```
<img src="../images/MESSAGE_INPUT_DEFAULT_EMPTY.png" width="50%"><img src="../images/MESSAGE_INPUT_DEFAULT_FILLED.png" width="50%">

#### Make it look the way you want

By using available widget attribute you can change color and size of text and input hint,  maximum number of permitted lines, size and indents “submit” button, and its icon and background.

For example, we need to change color and size of the text, replace button’s background with a new one and change some sizes and indents.

```xml
<com.stfalcon.chatkit.messages.MessageInput
   android:layout_width="match_parent"
   android:layout_height="wrap_content"
   app:inputButtonBackground="@drawable/input_button_background"
   app:inputButtonHeight="30dp"
   app:inputButtonIcon="@drawable/ic_send_selector"
   app:inputButtonMargin="16dp"
   app:inputButtonWidth="30dp"
   app:inputMaxLines="3"
   app:inputTextColor="@color/black"
   app:inputTextSize="18sp"/>
```
<img src="../images/MESSAGE_INPUT_STYLED_EMPTY.png" width="50%"><img src="../images/MESSAGE_INPUT_STYLED_FILLED.png" width="50%">

You can view [all available attributes here](STYLES_ATTR.md)