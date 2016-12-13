package com.stfalcon.chatkit.sample.dialogs;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.stfalcon.chatkit.commons.models.IDialog;
import com.stfalcon.chatkit.commons.models.IMessage;
import com.stfalcon.chatkit.features.dialogs.adapters.DialogViewHolder;
import com.stfalcon.chatkit.features.dialogs.adapters.DialogsListAdapter;
import com.stfalcon.chatkit.features.dialogs.widgets.DialogsListView;
import com.stfalcon.chatkit.sample.R;
import com.stfalcon.chatkit.sample.dialogs.fixtures.ChatListFixtures;
import com.stfalcon.chatkit.sample.models.DefaultDialog;

import java.util.List;

public class DialogsListActivity extends AppCompatActivity {
    private DialogsListAdapter<DefaultDialog> dialogsListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialogs_list);

        DialogsListView dialogsListView = (DialogsListView) findViewById(R.id.dialogList);

        dialogsListAdapter = new DialogsListAdapter<>(getDialogs());

        dialogsListAdapter.setOnLoadImagesListener(new DialogViewHolder.OnLoadImagesListener() {
            @Override
            public void onLoadImage(String url, ImageView imageView) {
                Picasso.with(DialogsListActivity.this).load(url).into(imageView);
            }
        });

        dialogsListAdapter.setOnItemClickListener(new DialogViewHolder.OnItemClickListener() {
            @Override
            public void onItemClick(View view, IDialog dialog) {
                Toast.makeText(DialogsListActivity.this, "Open chat with " + dialog.getDialogName(),
                        Toast.LENGTH_SHORT).show();
            }
        });

        dialogsListAdapter.setOnLongItemClickListener(new DialogViewHolder.OnLongItemClickListener() {
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
        return ChatListFixtures.getChatList();
    }
}
