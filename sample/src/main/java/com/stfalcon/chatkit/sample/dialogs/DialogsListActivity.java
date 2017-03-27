package com.stfalcon.chatkit.sample.dialogs;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.stfalcon.chatkit.commons.ImageLoader;
import com.stfalcon.chatkit.commons.models.IMessage;
import com.stfalcon.chatkit.dialogs.DialogsList;
import com.stfalcon.chatkit.dialogs.DialogsListAdapter;
import com.stfalcon.chatkit.sample.ChatSamplesListAdapter;
import com.stfalcon.chatkit.sample.R;
import com.stfalcon.chatkit.sample.fixtures.DialogsListFixtures;
import com.stfalcon.chatkit.sample.messages.MessagesListActivity;
import com.stfalcon.chatkit.sample.models.DefaultDialog;
import com.stfalcon.chatkit.utils.DateFormatter;

import java.util.Date;
import java.util.List;

public class DialogsListActivity extends AppCompatActivity {

    private static final String ARG_TYPE = "type";

    public static void open(Activity activity, ChatSamplesListAdapter.ChatSample.Type type) {
        Intent intent = new Intent(activity, DialogsListActivity.class);
        intent.putExtra(ARG_TYPE, type);
        activity.startActivity(intent);
    }

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


        DialogsList dialogsListView = (DialogsList) findViewById(R.id.dialogsList);

        ImageLoader imageLoader = new ImageLoader() {
            @Override
            public void loadImage(ImageView imageView, String url) {
                //If you using another library - write here your way to load image
                Picasso.with(DialogsListActivity.this).load(url).into(imageView);
            }
        };

        if (type == ChatSamplesListAdapter.ChatSample.Type.CUSTOM_LAYOUT) {
            dialogsListAdapter = new DialogsListAdapter<>(R.layout.item_dialog_custom, imageLoader);
        } else if (type == ChatSamplesListAdapter.ChatSample.Type.CUSTOM_VIEW_HOLDER) {
            dialogsListAdapter = new DialogsListAdapter<>(R.layout.item_dialog_custom_view_holder,
                    CustomDialogViewHolder.class, imageLoader);
        } else {
            dialogsListAdapter = new DialogsListAdapter<>(imageLoader);
        }
        dialogsListAdapter.setItems(getDialogs());

        dialogsListAdapter.setOnDialogClickListener(new DialogsListAdapter.OnDialogClickListener<DefaultDialog>() {
            @Override
            public void onDialogClick(DefaultDialog dialog) {
                MessagesListActivity.open(DialogsListActivity.this, type);
            }
        });

        dialogsListAdapter.setOnDialogLongClickListener(new DialogsListAdapter.OnDialogLongClickListener<DefaultDialog>() {
            @Override
            public void onDialogLongClick(DefaultDialog dialog) {
                Toast.makeText(DialogsListActivity.this, dialog.getDialogName(),
                        Toast.LENGTH_SHORT).show();
            }
        });
        dialogsListAdapter.setDatesFormatter(new DateFormatter.Formatter() {
            @Override
            public String format(Date date) {
                if (DateFormatter.isToday(date)) {
                    return DateFormatter.format(date, DateFormatter.Template.TIME);
                } else if (DateFormatter.isYesterday(date)) {
                    return getString(R.string.date_header_yesterday);
                } else if (DateFormatter.isCurrentYear(date)) {
                    return DateFormatter.format(date, DateFormatter.Template.STRING_DAY_MONTH);
                } else {
                    return DateFormatter.format(date, DateFormatter.Template.STRING_DAY_MONTH_YEAR);
                }
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
}
