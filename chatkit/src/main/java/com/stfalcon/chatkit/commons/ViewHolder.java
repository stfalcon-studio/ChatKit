package com.stfalcon.chatkit.commons;

import android.support.annotation.LayoutRes;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.lang.reflect.Constructor;

/*
 * Created by troy379 on 09.12.16.
 */
public abstract class ViewHolder<DATA> extends RecyclerView.ViewHolder {

    public abstract void onBind(DATA data);

    public ViewHolder(View itemView) {
        super(itemView);
    }

    public static <HOLDER extends ViewHolder>
    ViewHolder getHolder(ViewGroup parent, @LayoutRes int layout, Class<HOLDER> holderClass) {
        View v = LayoutInflater.from(parent.getContext()).inflate(layout, parent, false);

        try {
            Constructor<HOLDER> constructor = holderClass.getDeclaredConstructor(View.class);
            constructor.setAccessible(true);
            return constructor.newInstance(v);
        } catch (Exception e) {
            return null;
        }
    }
}
