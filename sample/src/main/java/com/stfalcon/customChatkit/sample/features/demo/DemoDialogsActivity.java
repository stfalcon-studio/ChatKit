package com.stfalcon.customChatkit.sample.features.demo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.stfalcon.customChatkit.commons.ImageLoader;
import com.stfalcon.customChatkit.dialogs.DialogsListAdapter;
import com.stfalcon.customChatkit.sample.common.data.model.Dialog;
import com.stfalcon.customChatkit.sample.utils.AppUtils;

/*
 * Created by troy379 on 05.04.17.
 */
public abstract class DemoDialogsActivity extends AppCompatActivity
        implements DialogsListAdapter.OnDialogClickListener<Dialog>,
        DialogsListAdapter.OnDialogLongClickListener<Dialog> {

    protected ImageLoader imageLoader;
    protected DialogsListAdapter<Dialog> dialogsAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        imageLoader = new ImageLoader() {
            @Override
            public void loadImage(ImageView imageView, String url, Object payload) {
                Picasso.with(DemoDialogsActivity.this).load(url).into(imageView);
            }
        };
    }

    @Override
    public void onDialogLongClick(Dialog dialog) {
        AppUtils.showToast(
                this,
                dialog.getDialogName(),
                false);
    }
}
