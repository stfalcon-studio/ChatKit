## Version 0.1.2 (2017/04/07)

**Note that all deprecated methods and class will be removed at version 0.3.0**

* Class `MessagesListAdapter.HoldersConfig` is marked as `deprecated`. Use `MessageHolders` instead;

* Method `MessagesListAdapter.HoldersConfig.setIncoming(Class, int)` is marked as `deprecated`. Use `MessageHolders.setIncomingTextConfig(Class, int)` instead;
* Method `MessagesListAdapter.HoldersConfig.setIncomingHolder(Class)` is marked as `deprecated`. Use `MessageHolders.setIncomingTextHolder(Class)` instead;
* Method `MessagesListAdapter.HoldersConfig.setIncomingLayout(int)` is marked as `deprecated`. Use `MessageHolders.setIncomingTextLayout(int)` instead;
* Method `MessagesListAdapter.HoldersConfig.setOutcoming(Class, int)` is marked as `deprecated`. Use `MessageHolders.setOutcomingTextConfig(Class, int)` instead;
* Method `MessagesListAdapter.HoldersConfig.setOutcomingHolder(Class)` is marked as `deprecated`. Use `MessageHolders.setOutcomingTextHolder(Class)` instead;
* Method `MessagesListAdapter.HoldersConfig.setOutcomingLayout(int)` is marked as `deprecated`. Use `MessageHolders.setOutcomingTextLayout(int)` instead;
* Method `MessagesListAdapter.HoldersConfig.setDateHeader(Class, int)` is marked as `deprecated`. Use `MessageHolders.setDateHeaderConfig(Class, int)` instead;

* Class `MessagesListAdapter.BaseMessageViewHolder` is marked as `deprecated`. Use `MessageHolders.BaseMessageViewHolder` instead.
* Class `MessagesListAdapter.DefaultDateHeaderViewHolder` is marked as `deprecated`. Use `MessageHolders.DefaultDateHeaderViewHolder` instead.
* Class `MessagesListAdapter.IncomingMessageViewHolder` is marked as `deprecated`. Use `MessageHolders.IncomingTextMessageViewHolder` instead.
* Class `MessagesListAdapter.OutcomingMessageViewHolder` is marked as `deprecated`. Use `MessageHolders.OutcomingTextMessageViewHolder` instead.