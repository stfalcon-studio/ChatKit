package com.stfalcon.chatkit.sample.features.main.adapter;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.stfalcon.chatkit.sample.R;

/*
 * Created by troy379 on 11.04.17.
 */
public class DemoCardFragment extends Fragment
        implements View.OnClickListener {

    private static final String ARG_ID = "id";
    private static final String ARG_TITLE = "title";
    private static final String ARG_DESCRIPTION = "description";

    private int id;
    private String title, description;
    private OnActionListener actionListener;

    public DemoCardFragment() {

    }

    public static DemoCardFragment newInstance(int id, String title, String description) {
        DemoCardFragment fragment = new DemoCardFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_ID, id);
        args.putString(ARG_TITLE, title);
        args.putString(ARG_DESCRIPTION, description);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            this.id = getArguments().getInt(ARG_ID);
            this.title = getArguments().getString(ARG_TITLE);
            this.description = getArguments().getString(ARG_DESCRIPTION);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_demo_card, container, false);

        TextView tvTitle = (TextView) v.findViewById(R.id.tvTitle);
        TextView tvDescription = (TextView) v.findViewById(R.id.tvDescription);
        Button button = (Button) v.findViewById(R.id.button);

        tvTitle.setText(title);
        tvDescription.setText(description);
        button.setOnClickListener(this);

        return v;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button:
                onAction();
                break;
        }
    }

    public void onAction() {
        if (actionListener != null) {
            actionListener.onAction(this.id);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnActionListener) {
            actionListener = (OnActionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnActionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        actionListener = null;
    }

    public interface OnActionListener {
        void onAction(int id);
    }
}