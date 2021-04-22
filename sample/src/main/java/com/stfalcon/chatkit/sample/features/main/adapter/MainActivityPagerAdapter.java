package com.stfalcon.chatkit.sample.features.main.adapter;

import android.content.Context;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.stfalcon.chatkit.sample.R;

/*
 * Created by troy379 on 11.04.17.
 */
public class MainActivityPagerAdapter extends FragmentStatePagerAdapter {

    public static final int ID_DEFAULT = 0;
    public static final int ID_STYLED = 1;
    public static final int ID_CUSTOM_LAYOUT = 2;
    public static final int ID_CUSTOM_VIEW_HOLDER = 3;
    public static final int ID_CUSTOM_CONTENT = 4;

    private final Context context;

    public MainActivityPagerAdapter(Context context, FragmentManager fm) {
        super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        this.context = context;
    }

    @Override
    public Fragment getItem(int position) {
        String title = null;
        String description = null;
        switch (position) {
            case ID_DEFAULT:
                title = context.getString(R.string.sample_title_default);
                description = context.getString(R.string.sample_subtitle_default);
                break;
            case ID_STYLED:
                title = context.getString(R.string.sample_title_attrs);
                description = context.getString(R.string.sample_subtitle_attrs);
                break;
            case ID_CUSTOM_LAYOUT:
                title = context.getString(R.string.sample_title_layout);
                description = context.getString(R.string.sample_subtitle_layout);
                break;
            case ID_CUSTOM_VIEW_HOLDER:
                title = context.getString(R.string.sample_title_holder);
                description = context.getString(R.string.sample_subtitle_holder);
                break;
            case ID_CUSTOM_CONTENT:
                title = context.getString(R.string.sample_title_custom_content);
                description = context.getString(R.string.sample_subtitle_custom_content);
                break;
        }
        return DemoCardFragment.newInstance(position, title, description);
    }

    @Override
    public int getCount() {
        return 5;
    }
}
