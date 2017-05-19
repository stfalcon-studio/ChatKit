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

#### Add attachments

If you want to add multimedia messages, you may need an "attachments" button. You can enable it with the attribute `showAttachmentButton="true"`, and to react to its pressing it is necessary to install the listener:
```java
messageInput.setAttachmentsListener(new MessageInput.AttachmentsListener() {
   @Override
   public void onAddAttachments() {
       //select attachments
   }
});
```

#### Typing Listener

If you want to track typing event:
```java
messageInput.setTypingListener(new MessageInput.TypingListener() {
     @Override
     public void onStartTyping() {
         
     }

     @Override
     public void onStopTyping() {

     }
 });
```
Default delay typing is 1500, you can change it with only one line `delayTypingStatus="delayInMilis"`

#### Make it look the way you want

By using available widget attribute you can change color and size of text and input hint,  maximum number of permitted lines, size and indents “submit” button, and its icon and background.

For example, we need to change color and size of the text, replace submit and attachment buttons background with a new one or change color of default (and the same for their icons) and change some sizes and indents.

```xml
<com.stfalcon.chatkit.messages.MessageInput
   android:layout_width="match_parent"
   android:layout_height="wrap_content"
   app:attachmentButtonDefaultIconColor="@color/green"
   app:attachmentButtonDefaultIconPressedColor="@color/green_dark"
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

Also, you can change default

You can view [all available attributes here](STYLES_ATTR.md)