# ChatKit for Android

[![](https://jitpack.io/v/stfalcon-studio/Chatkit.svg)](https://jitpack.io/#stfalcon-studio/Chatkit)

ChatKit is a library designed to simplify the development of UI for such a trivial task as chat. It has flexible possibilities for styling, customizing and data management

<p align="center">
<img src="images/HEADER.jpg">
</p>

### Features

* Ready-to-use already styled solution for quick implementation;
* Default and custom media messages;
* Fully customizable layouts - setting styles out of the box (use your own colors, text appearances, drawables, selectors and sizes) or even create your own custom markup or/and holders for unique behaviour;
* List of dialogs, including tete-a-tete and group chats, markers for unread messages and last user message view;
* List of messages (incoming and outcoming) with history pagination and already calculated dates headers;
* Different avatars with no specific realization of image loading - you can use any library you want;
* Selection mode for interacting with messages;
* Links highlighting
* Easy dates formatting;
* Your own models for dialogs and messages - there is no converting needed;
* Ready to use message input view;
* Custom animations (according to RecyclerView usage).

### Who we are
Need iOS and Android apps, MVP development or prototyping? Contact us via info@stfalcon.com. We develop software since 2009, and we're known experts in this field. Check out our [portfolio](https://stfalcon.com/en/portfolio) and see more libraries from [stfalcon-studio](https://stfalcon-studio.github.io/).

## Demo Application

[![Get it on Google Play](https://play.google.com/intl/en_us/badges/images/badge_new.png)](https://play.google.com/store/apps/details?id=com.stfalcon.chatkit.sample)

### How to use

To implement all of the features above you can use the following components:

* [DialogsList](docs/COMPONENT_DIALOGS_LIST.MD);
* [MessagesList](docs/COMPONENT_MESSAGES_LIST.md);
* [MessageInput](docs/COMPONENT_MESSAGE_INPUT.MD);


### Download

1. Add jitpack to the root build.gradle file of your project at the end of repositories.
```
allprojects {
  repositories {
    ...
    maven { url 'https://jitpack.io' }
  }
}
```
2. Add the dependency
[![](https://jitpack.io/v/stfalcon-studio/Chatkit.svg)](https://jitpack.io/#stfalcon-studio/Chatkit)
```
dependencies {
  ...
  implementation 'com.github.stfalcon:chatkit:[last_version]'
}  
```

### AndroidX
To use with AndroidX you have to set targetSdkVersion for your project to 28 and add following 2 lines in ```gradle.properties``` file.
```
android.useAndroidX=true
android.enableJetifier=true
```

### Proguard
If you are using ProGuard you might need to add rules:
```
-keep class * extends com.stfalcon.chatkit.messages.MessageHolders$OutcomingTextMessageViewHolder {
     public <init>(android.view.View, java.lang.Object);
     public <init>(android.view.View);
 }
-keep class * extends com.stfalcon.chatkit.messages.MessageHolders$IncomingTextMessageViewHolder {
     public <init>(android.view.View, java.lang.Object);
     public <init>(android.view.View);
 }
-keep class * extends com.stfalcon.chatkit.messages.MessageHolders$IncomingImageMessageViewHolder {
     public <init>(android.view.View, java.lang.Object);
     public <init>(android.view.View);
 }
-keep class * extends com.stfalcon.chatkit.messages.MessageHolders$OutcomingImageMessageViewHolder {
     public <init>(android.view.View, java.lang.Object);
     public <init>(android.view.View);
 }
```

### Try it

Check out the [sample project](/sample/src/main) to try it yourself! :wink:

### Changelog
[See the changelog](docs/CHANGELOG.md) to be aware of latest improvements and fixes.

### Gratitude

We were inspired by [JSQMessagesViewController](https://github.com/jessesquires/JSQMessagesViewController) library for iOS. In our plans to improve functionality to give Android developers wide opportunities to create fast and good-looking UI for chats in their applications.

Please, contact us via github@stfalcon.com if you are using this library, just to let us know :)
Thank you!

### License

```
Copyright (C) 2017 stfalcon.com

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

https://github.com/stfalcon-studio/ChatKit/blob/master/LICENSE

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.

```
