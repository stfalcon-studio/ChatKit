# ImageLoader

In order to not be limited with using any of the specific libraries to upload images, we have brought this process to a separate `ImageLoader` interface. By implementing it on your side, you get an absolute freedom in the process of downloading and processing of the image before it is displayed. `ImageLoader` is a required parameter in [DialogsList](COMPONENT_DIALOGS_LIST.md) and [MessagesList](COMPONENT_MESSEGES_LIST.md) components, where images often appear in the UI.

For example, to load images with Picasso, you just need to add only one line :wink:

```java
ImageLoader imageLoader = new ImageLoader() {
   @Override
   public void loadImage(ImageView imageView, String url) {
       Picasso.with(MessagesListActivity.this).load(url).into(imageView);
   }
};
```