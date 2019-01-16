package com.stfalcon.customChatkit.sample.features.demo.custom.layout;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.stfalcon.customChatkit.dialogs.DialogsList;
import com.stfalcon.customChatkit.dialogs.DialogsListAdapter;
import com.stfalcon.customChatkit.sample.R;
import com.stfalcon.customChatkit.sample.common.data.fixtures.DialogsFixtures;
import com.stfalcon.customChatkit.sample.common.data.model.Dialog;
import com.stfalcon.customChatkit.sample.features.demo.DemoDialogsActivity;

public class CustomLayoutDialogsActivity extends DemoDialogsActivity {

    public static void open(Context context) {
        context.startActivity(new Intent(context, CustomLayoutDialogsActivity.class));
    }

    private DialogsList dialogsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_layout_dialogs);

        dialogsList = (DialogsList) findViewById(R.id.dialogsList);
        initAdapter();
    }

    @Override
    public void onDialogClick(Dialog dialog) {
        CustomLayoutMessagesActivity.open(this);
    }

    private void initAdapter() {
        super.dialogsAdapter = new DialogsListAdapter<>(R.layout.item_custom_dialog, super.imageLoader);
        super.dialogsAdapter.setItems(DialogsFixtures.getDialogs());

        super.dialogsAdapter.setOnDialogClickListener(this);
        super.dialogsAdapter.setOnDialogLongClickListener(this);

        dialogsList.setAdapter(super.dialogsAdapter);
    }
}
