package com.stfalcon.chatkit.sample.dialogs;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.stfalcon.chatkit.commons.models.IDialog;
import com.stfalcon.chatkit.features.dialogs.adapters.DialogsListAdapter;
import com.stfalcon.chatkit.features.dialogs.widgets.DialogsListView;
import com.stfalcon.chatkit.sample.R;
import com.stfalcon.chatkit.sample.dialogs.fixtures.ChatListFixtures;

import java.util.List;

public class DialogsListActivity extends AppCompatActivity {
    private DialogsListView dialogsListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialogs_list);

        dialogsListView = (DialogsListView) findViewById(R.id.dialogList);

        DialogsListAdapter dialogsListAdapter = new DialogsListAdapter<>(getDialogs());

        dialogsListView.setAdapter(dialogsListAdapter);
    }

    private List<IDialog> getDialogs() {
        return ChatListFixtures.getChatList();
    }
}
