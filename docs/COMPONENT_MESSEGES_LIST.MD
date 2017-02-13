# MessagesList

MessagesList is a component for displaying and management of messages in conversation. Its main feature is a correct and simple interaction with a list of messages and date headers, which is implemented via adapter. Also, it supports several customization levels to let you add all of the features, that wasn’t included by default.
<p align="center">
<img src="../images/CHAT_DEFAULT_VIEW.png">
</p>

## How to make it work

To start using the component you just need to follow several steps:

#### Add this widget into your xml layout:

```xml
<com.stfalcon.chatkit.messages.MessagesList
   android:id="@+id/messagesList"
   android:layout_width="match_parent"
   android:layout_height="match_parent"
   android:layout_above="@+id/input" />
```
#### Set an adapter

Adapter’s constructor takes a sender id (a user on whose behalf messages are sending) and implementation of ImageLoader to load avatars in messages ([Click here to learn more about image loading](IMAGE_LOADER.md)).


```java
MessagesListAdapter<Message> adapter = new MessagesListAdapter<>(senderId, imageLoader);
messagesList.setAdapter(adapter);
```
Anyway, you can pass second parameter as ‘null’, and avatars will be hidden.

#### Prepare your model

To be able to add messages, you must implement the `IMessage` interface into your existing model and override its methods:

```java
public class Message implements IMessage {

   /*...*/

   @Override
   public String getId() {
       return id;
   }

   @Override
   public String getText() {
       return text;
   }

   @Override
   public IUser getUser() {
       return author;
   }

   @Override
   public Date getCreatedAt() {
       return createdAt;
   }
}
```
As you can see, you need also to add the `IUser` interface for message author displaying:

```java
public class Author implements IUser {

   /*...*/

   @Override
   public String getId() {
       return id;
   }

   @Override
   public String getName() {
       return name;
   }

   @Override
   public String getAvatar() {
       return avatar;
   }
}
```
It describes info of message author, such as user id (to differ the incoming and outgoing messages), user name and his/her avatar. If user doesn't have avatar image, you can return `null`, and image will not be shown in incoming message item:
<p align="center">
<img src="../images/CHAT_DEFAULT_WITHOUT_AVATARS.png">
</p>
That's all! This approach allows the adapter to work with your message model without type converting of any kind!

## Data management

#### Adding new messages

When your models are ready to be used by adapter, you can simply add them to the list. There are two ways to do it:

by adding one message to the start (bottom) of the list with scrolling (if needed). This method is best suitable for adding new messages by calling the `adapter.addToStart(IMessage message, boolean scroll)`;
by adding the previous messages of chatting history to the end (top) of the list with `adapter.addToEnd(List<IMessage> messages, boolean reverse)` method. Note that you can reverse the list before adding, if messages aren’t in the right order.

#### Adding messages from history

Ok, now we can add our messages. But how can we handle the history pagination? For this case, adapter has `OnLoadMoreListener` with `onLoadMore(int page, int totalItemsCount)` callback. It fires every time the user scrolls a list to the top. You can do your logic like this:

```java
@Override
public void onLoadMore(int page, int totalItemsCount) {
   if (totalItemsCount < this.total) {
       loadMessages(...);
   }
}
```
The `page` variable contains next page number to load (which is equals to the amount of executions) and `totalItemsCount`, that contains current messages counter in the list.

#### Date headers

There’s no need to worry about data headers generation, it proceeds automatically while adding and deleting messages from the list considering all the possible cases. Also it’s fully localized, because it’s created with native java methods.

#### Deleting messages

To delete messages from the list, you need to call `adapter.deleteById(String id)` and `adapter.deleteByIds(String[] ids)`. Just for convenience, these methods also have overloadings for objects (delete(IMessage message), delete(List<IMessage> messages)), which work exactly the same.

To delete all messages, just call `adapter.clear()` method.

#### Updating messages

If message has changed, you can update it by calling `adapter.update(IMessage message)`. Or call `adapter.update(String oldId, IMessage message)`, when identifier has changed.

## Interact with user’s actions

#### Click listeners

Of course, the adapter have listeners for such important actions as short and long clicks. They just returns a message object that has been pressed, with a type that is specified as the generic type of adapter:

```java
public interface OnMessageClickListener<MESSAGE extends IMessage> {
   void onMessageClick(MESSAGE message);
}
public interface OnMessageLongClickListener<MESSAGE extends IMessage> {
   void onMessageLongClick(MESSAGE message);
}
```
#### Selection mode

But these listeners are not enough, if you want to create really convenient UX. Just imagine, how long users will remove, copy or share messages by doing those actions one by one. What a waste of time!

For this case we have the selection mode, that turns on after user long press, and turns off, if amount of selected items is changed to zero. By default this behaviour is disabled, but you can enable it by calling `adapter.enableSelectionMode(SelectionListener selectionListener)`. The object in this parameter is the listener for selected messages count, that can be used as trigger for toolbar action mode (for example):
<p align="center">
<img src="../images/CHAT_DEFAULT_SELECTION_MODE.png">
</p>
...and the code looks like this:

```java
@Override
public void onSelectionChanged(int count) {
   menu.findItem(R.id.action_delete).setVisible(count > 0);
}
```
After messages are selected, you can get them with `adapter.getSelectedMessages()` as a list of objects. Then you can call `adapter.unselectAllItems()`, it will cancel messages’ selection, and call `SelectionListener.onSelectionChanged()` with zero as parameter to change action mode.

Furthermore, if you want to delete selected messages, just call  `adapter.deleteSelectedMessages()` method, and it will do all the work for you. But you need to call `adapter.getSelectedMessages()` before deleting messages from the list to make delete request from your data source.

If you need to disable selection mode on-the-fly, use `adapter.disableSelectionMode()`. It will cancel items’ selection and remove the listener.

**N.B.! When selection mode is enabled, your custom `OnMessageLongClickListener` will be ignored due to the conflict of logic.**

## Make it look the way you want

Certainly, initially defined appearance will not be suitable for everybody. And again, we have several options to solve this problem.

#### Styling via attributes

The simplest way to customize appearance is to use `MessageList` widget attributes. With them we can set up a default background color for incoming and outcoming messages, and even the colors for their pushed conditions and single selection. You can also change color and size for message font, send time and data in the headers. Now, by defining attributes this way:

```xml
<com.stfalcon.chatkit.messages.MessagesList
   android:id="@+id/messagesList"
   android:layout_width="match_parent"
   android:layout_height="match_parent"
   android:layout_above="@+id/input"
   app:incomingDefaultBubbleColor="@color/ivory"
   app:incomingDefaultBubblePressedColor="@color/ivory_dark"
   app:incomingDefaultBubbleSelectedColor="@color/gray"
   app:incomingTextColor="@color/black"
   app:incomingTextSize="18sp"
   app:outcomingDefaultBubbleColor="@color/green"
   app:outcomingDefaultBubblePressedColor="@color/green_dark"
   app:outcomingDefaultBubbleSelectedColor="@color/gray_dark_transparent"
   app:outcomingTextColor="@color/white"
   app:outcomingTextSize="18sp"/>
```
...we can get something like this:
<p align="center">
<img src="../images/CHAT_CUSTOM_ATTRS_STYLE.png">
</p>
If the shape of bubble doesn’t suits you, set your own drawable through the `incomingBubbleDrawable`/`outcomingBubbleDrawable` attribute. However, color customization attributes would not work for setting their state color, you should describe this behavior using selectors. Also you can set paddings for each of them by using `incomingBubblePaddingXXX` and `outcomingBubblePaddingXXX` attributes.

In case if you don’t like default data format, you can set it with only one line by using java date format — `dateHeaderFormat="dd.MM.yy"`

You can view [all available attributes here](STYLES_ATTR.md)

#### Create your own layout

But what if you need not only to change the appearance of the elements, but also to change their position? It does not matter, because you can create your own layout! The only condition — IDs must match with default IDs, and widget types should not cause a ClassCastException (ie, either be the same or the subclass type). Here are IDs with types, which were defined in MessageList:

* `@id/bubble` (ViewGroup)
* `@id/messageText` (TextView)
* `@id/messageTime`  (TextView)
* `@id/messageUserAvatar` (ImageView)

For better understanding see how [default layout looks like](https://github.com/stfalcon-studio/ChatKit/blob/master/sample/src/main/res/layout/item_custom_incoming_message.xml)

After a layout was created, you need to put it into `HoldersConfig` object, which has appropriate methods for each layout files: `setIncomingLayout(int layoutRes)`, `setOutcomingLayout(int layoutRes)` `setDateHeaderLayout(int layoutRes)`. To hook up a config object, you need to transfer it to adapter through a constructor:

```java
MessagesListAdapter.HoldersConfig holdersConfig = new MessagesListAdapter.HoldersConfig();
holdersConfig.setIncomingLayout(R.layout.item_custom_incoming_message);
holdersConfig.setOutcomingLayout(R.layout.item_custom_outcoming_message);
adapter = new MessagesListAdapter<>(senderId, holdersConfig, imageLoader);
```
<p align="center">
<img src="../images/CHAT_CUSTOM_LAYOUT.png">
<h6 align="center">Here we’ve changed a message time position, an avatar shape and a bubble shape.</h6>
</p>

#### Not enough features? Create your own holder!

Sometimes a message text displaying is not enough. For example, you need to add the message processing status and reactions to the message (as in Slack). Of course, for this purpose you have to create your own layout, but you can’t do it without changing the logic of ViewHolder. `HolderConfig` can do the trick. You can transfer your own holder class with `setIncomingHolder(Class holderClass)`, `setOutcomingHolder(Class holderClass)` and `setDateHeaderHolder(Class holderClass)` methods in it. For convenience' sake, it also contains methods for simultaneous adding of layout file and holder:

```java
holdersConfig.setIncoming(CustomIncomingMessageViewHolder.class,
        R.layout.item_custom_holder_incoming_message);
holdersConfig.setOutcoming(CustomOutcomingMessageViewHolder.class,
        R.layout.item_custom_holder_outcoming_message);
```
To create your own holder, you need to inherit your class from `MessagesListAdapter.BaseMessageViewHolder<>`, and transfer your message class to generic type, because on the assumption of it the `onBind(IMessage message)` method will be typified. This method is similar to `onBindViewHolder()` method from `RecyclerView.Adapter` class: you can manipulate your data from it and upload images through `protected` of the `ImageLoader` field, and get `isSelected` state.

However, if you’re going to add new features without rewriting behavior from a scratch, you can inherit standard realization class from a holder you need:

* `IncomingMessageViewHolder` - incoming message holder;
* `OutcomingMessageViewHolder` - outgoing message holder;
* `DefaultDateHeaderViewHolder` - date separator holder.

For example, you can add status for outgoing messages with only few lines:

```java
public class CustomOutcomingMessageViewHolder
       extends MessagesListAdapter.OutcomingMessageViewHolder<Message> {

   public CustomOutcomingMessageViewHolder(View itemView) {
       super(itemView);
   }

   @Override
   public void onBind(Message message) {
       super.onBind(message);

       time.setText(message.getStatus() + " " + time.getText());
   }
}
```
<p align="center">
<img src="../images/CHAT_CUSTOM_HOLDER.png">
<h6 align="center">Pay attention to outgoing message’ status and online indicator.</h6>
</p>