package com.stfalcon.chatkit.sample.dialogs;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.stfalcon.chatkit.commons.ImageLoader;
import com.stfalcon.chatkit.commons.models.IDialog;
import com.stfalcon.chatkit.commons.models.IMessage;
import com.stfalcon.chatkit.dialogs.DialogsListAdapter;
import com.stfalcon.chatkit.dialogs.DialogsList;
import com.stfalcon.chatkit.sample.ChatSamplesListAdapter;
import com.stfalcon.chatkit.sample.R;
import com.stfalcon.chatkit.sample.fixtures.DialogsListFixtures;
import com.stfalcon.chatkit.sample.messages.MessagesListActivity;
import com.stfalcon.chatkit.sample.models.DefaultDialog;

import java.util.List;

public class DialogsListActivity extends AppCompatActivity {
    private static final String ARG_TYPE = "type";

    private DialogsListAdapter<DefaultDialog> dialogsListAdapter;
    private ChatSamplesListAdapter.ChatSample.Type type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        type = (ChatSamplesListAdapter.ChatSample.Type) getIntent().getExtras().getSerializable(ARG_TYPE);

        switch (type) {
            case CUSTOM_ATTR:
                setContentView(R.layout.activity_dialogs_list_attr);
                break;
            case CUSTOM_LAYOUT:
            case CUSTOM_VIEW_HOLDER:
                setContentView(R.layout.activity_dialogs_list_layout);
                break;
            default:
                setContentView(R.layout.activity_dialogs_list_default);
        }


        DialogsList dialogsListView = (DialogsList) findViewById(R.id.dialogList);

        ImageLoader imageLoader = new ImageLoader() {
            @Override
            public void loadImage(ImageView imageView, String url) {
                Picasso.with(DialogsListActivity.this).load(url).into(imageView);
            }
        };

        if (type == ChatSamplesListAdapter.ChatSample.Type.CUSTOM_LAYOUT) {
            dialogsListAdapter = new DialogsListAdapter<>(R.layout.item_dialog_custom, getDialogs(), imageLoader);
        } else if (type == ChatSamplesListAdapter.ChatSample.Type.CUSTOM_VIEW_HOLDER) {
            dialogsListAdapter = new DialogsListAdapter<>(R.layout.item_dialog_custom_view_holder,
                    CustomDialogViewHolder.class, getDialogs(), imageLoader);
        } else {
            dialogsListAdapter = new DialogsListAdapter<>(getDialogs(), imageLoader);
        }

        dialogsListAdapter.setOnItemClickListener(new DialogsListAdapter.BaseDialogViewHolder.OnItemClickListener() {
            @Override
            public void onItemClick(View view, IDialog dialog) {
                MessagesListActivity.open(DialogsListActivity.this, type);
            }
        });

        dialogsListAdapter.setOnLongItemClickListener(new DialogsListAdapter.BaseDialogViewHolder.OnLongItemClickListener() {
            @Override
            public void onLongItemClick(View view, IDialog dialog) {
                Toast.makeText(DialogsListActivity.this, "Show menu",
                        Toast.LENGTH_SHORT).show();
            }
        });

        dialogsListView.setAdapter(dialogsListAdapter);
    }

    private void onNewMessage(String dialogId, IMessage message) {
        if (!dialogsListAdapter.updateDialogWithMessage(dialogId, message)) {
            //Dialog with this ID doesn't exist, so you can create new Dialog or update all dialogs list
        }
    }

    private void onNewDialog(DefaultDialog dialog) {
        dialogsListAdapter.addItem(dialog);
    }

    private List<DefaultDialog> getDialogs() {
        return DialogsListFixtures.getChatList();
    }

    public static void open(Activity activity, ChatSamplesListAdapter.ChatSample.Type type) {
        Intent intent = new Intent(activity, DialogsListActivity.class);
        intent.putExtra(ARG_TYPE, type);
        activity.startActivity(intent);
    }
}
