package com.stfalcon.chatkit.sample;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.stfalcon.chatkit.sample.dialogs.DialogsListActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Dialogs list button
        findViewById(R.id.btnChatList).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, DialogsListActivity.class));
            }
        });

        //Messages list button
        findViewById(R.id.btnMessagesList).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, MessagesListActivity.class));
            }
        });

    }
}
