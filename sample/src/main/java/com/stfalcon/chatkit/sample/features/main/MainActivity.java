package com.stfalcon.chatkit.sample.features.main;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.stfalcon.chatkit.sample.R;
import com.stfalcon.chatkit.sample.features.demo.custom.holder.CustomHolderDialogsActivity;
import com.stfalcon.chatkit.sample.features.demo.custom.layout.CustomLayoutDialogsActivity;
import com.stfalcon.chatkit.sample.features.demo.custom.media.CustomMediaMessagesActivity;
import com.stfalcon.chatkit.sample.features.demo.def.DefaultDialogsActivity;
import com.stfalcon.chatkit.sample.features.demo.styled.StyledDialogsActivity;
import com.stfalcon.chatkit.sample.features.main.adapter.Sample;
import com.stfalcon.chatkit.sample.features.main.adapter.SamplesAdapter;

import java.util.ArrayList;
import java.util.List;

/*
 * Created by troy379 on 04.04.17.
 */
public class MainActivity extends AppCompatActivity
        implements SamplesAdapter.ClickListener {

    private final List<Sample> samples = new ArrayList<Sample>() {
        {
            add(new Sample(R.string.sample_title_default,
                    R.drawable.bg_preview_default_design,
                    Sample.Type.DEFAULT));

            add(new Sample(R.string.sample_title_attrs,
                    R.drawable.bg_preview_attrs_styling,
                    Sample.Type.STYLED));

            add(new Sample(R.string.sample_title_layout,
                    R.drawable.bg_preview_custom_layout,
                    Sample.Type.CUSTOM_LAYOUT));

            add(new Sample(R.string.sample_title_holder,
                    R.drawable.bg_preview_custom_holder,
                    Sample.Type.CUSTOM_VIEW_HOLDER));

            add(new Sample(R.string.sample_title_media,
                    R.drawable.bg_preview_custom_holder, // TODO: 04.04.17 change bg
                    Sample.Type.CUSTOM_MEDIA));
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SamplesAdapter adapter = new SamplesAdapter(samples);
        adapter.setClickListener(this);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onItemClick(Sample.Type type) {
        switch (type) {
            case DEFAULT:
                DefaultDialogsActivity.open(this);
                break;
            case STYLED:
                StyledDialogsActivity.open(this);
                break;
            case CUSTOM_LAYOUT:
                CustomLayoutDialogsActivity.open(this);
                break;
            case CUSTOM_VIEW_HOLDER:
                CustomHolderDialogsActivity.open(this);
                break;
            case CUSTOM_MEDIA:
                CustomMediaMessagesActivity.open(this);
                break;
        }
    }
}
