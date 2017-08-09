package com.stfalcon.chatkit.sample.features.demo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.squareup.picasso.Picasso;
import com.stfalcon.chatkit.commons.ImageLoader;
import com.stfalcon.chatkit.commons.models.IUser;
import com.stfalcon.chatkit.dialogs.DialogsListAdapter;
import com.stfalcon.chatkit.sample.common.data.model.Dialog;
import com.stfalcon.chatkit.sample.utils.AppUtils;

import java.util.Random;

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
            public void loadImage(ImageView imageView, String url) {
                Picasso.with(DemoDialogsActivity.this)
                        .load(url)
                        .into(imageView);
            }

            @Override
            public void loadImage(ImageView imageView, IUser user) {
                if(new Random().nextInt(2) == 1) {
                    Picasso.with(DemoDialogsActivity.this)
                            .load(user.getAvatar())
                            .into(imageView);
                } else {
                    TextDrawable drawable = TextDrawable.builder().buildRound(
                            user.getName().substring(0, 2),
                            DemoDialogsActivity.this.getResources().getColor(android.R.color.holo_red_dark));
                    imageView.setImageDrawable(drawable);
                }
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
