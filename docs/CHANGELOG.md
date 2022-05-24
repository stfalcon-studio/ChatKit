## Version 0.3.3 (2018/10/12)
* Merged: Added upsert method to move a specific item to the start. [#209](https://github.com/stfalcon-studio/ChatKit/pull/209)

## Version 0.3.2 (2018/09/28)
* [Passing custom data to ImageLoader](https://github.com/stfalcon-studio/ChatKit/issues/183)
* Fixed [#198](https://github.com/stfalcon-studio/ChatKit/issues/198)
* Protected `items` field in `MessagesListAdapter` and `DialogsListAdapte` [#188](https://github.com/stfalcon-studio/ChatKit/issues/188)
* Fixed `delete` and `deleteByIds` methods in MessagesListAdapter
* Merged: Clear last message if null in dialog list (Fix issue #189) [#190](https://github.com/stfalcon-studio/ChatKit/pull/190)
* Merged: Add upsert method to dialogs list [#191](https://github.com/stfalcon-studio/ChatKit/pull/191)
* Merged: Update documentation to add upsertItem method [#208](https://github.com/stfalcon-studio/ChatKit/pull/208)

## Version 0.3.1 (2018/08/16)
* [Passing custom data to your ViewHolder](https://github.com/stfalcon-studio/ChatKit/blob/master/docs/COMPONENT_MESSAGES_LIST.md#passing-custom-data-to-your-viewholder) [#180](https://github.com/stfalcon-studio/ChatKit/issues/180)
* Added Proguard rules to [Readme](https://github.com/stfalcon-studio/ChatKit#proguard) and [sample project](https://github.com/stfalcon-studio/ChatKit/blob/master/sample/proguard-rules.pro). [#122](https://github.com/stfalcon-studio/ChatKit/issues/122)
* Fixed [#174](https://github.com/stfalcon-studio/ChatKit/issues/174)

## Version 0.3.0 (2018/07/12)
* [Added Typing Listener to MessageInput](https://github.com/stfalcon-studio/ChatKit/blob/master/docs/COMPONENT_MESSAGE_INPUT.MD#typing-listener). Thanks to [toanpv](https://github.com/toanpv);
* Fixed artifacts with bubble background in message list;
* Added separate method MessagesListAdapter.clear(notifyDataSetChanged). Method MessagesListAdapter.clear() does notifyDataSetChanged by default. [#89](https://github.com/stfalcon-studio/ChatKit/issues/86);
* Fixed "The totalItemsCount parameter in OnLoadMore callback contains date header." [#86](https://github.com/stfalcon-studio/ChatKit/issues/86);
* Merged pull requests:
  * Avoid Crash on empty list in addToEnd [#146](https://github.com/stfalcon-studio/ChatKit/pull/146);
  * Fix link typo in docs [#134](https://github.com/stfalcon-studio/ChatKit/pull/134);
  * Add nullable for getImageUrl() [#119](https://github.com/stfalcon-studio/ChatKit/pull/119);
  * Made correction to DialogList documentation [#112](https://github.com/stfalcon-studio/ChatKit/pull/112);
  * Allow moving Dialog item and get Dialog by id [#70](https://github.com/stfalcon-studio/ChatKit/pull/70);
  * Allow the user to get the current position of a DIALOG [#32](https://github.com/stfalcon-studio/ChatKit/pull/32);
  * Added upsert(Message) method to add or update message to adapter as appropriate [#61](https://github.com/stfalcon-studio/ChatKit/pull/61);
  * Create LICENSE [#167](https://github.com/stfalcon-studio/ChatKit/pull/167);
  * NPE check in DialofsListAdapter.java when there is no last message (is null) [#75](https://github.com/stfalcon-studio/ChatKit/pull/75);
* Sample: Fixed artifacts on some devices in ShapeImageView. Changed fixture message image.

## Version 0.2.0 (2017/04/07)

 * [Default image type](COMPONENT_MESSAGES_LIST.md#adding-image-message);
 * [Support for custom content types](COMPONENT_MESSAGES_LIST.md#custom-content-types);
 * [Links highlighting](COMPONENT_MESSAGES_LIST.md#links-highlighting);
 * Dates formatter for [dialogs](COMPONENT_DIALOGS_LIST.MD#dates-format) and [messages](COMPONENT_MESSAGES_LIST.md#dates-format);
 * [Messages copying with formatting](COMPONENT_MESSAGES_LIST.md#messages-copying);
 * IDialogs typification improvement;
 * Text styling improvement for MessagesList;
 * More styling possibilities for MessagesInput;
 * Views null safety for custom layouts;
 * Sample refactoring for better readability;
 * Fixed minify problem. Thanks to [6bangs](https://github.com/6bangs)
 * DateUtils.

 Some classes and methods from previous versions become deprecated. See [migration log](MIGRATION_GUIDE.MD) for more details.

## Version 0.1.2 (2017/02/21)

 * Access to inner views of MessageInput;
 * `textCapSentences` input type in MessageInput by default;
 * Fixed potential crash in MessagesList.update(...).

## Version 0.1.0 (2017/02/13)

 * Release.
