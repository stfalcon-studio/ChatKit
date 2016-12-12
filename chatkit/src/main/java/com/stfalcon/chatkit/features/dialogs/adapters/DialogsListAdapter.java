package com.stfalcon.chatkit.features.dialogs.adapters;

import android.support.annotation.LayoutRes;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.stfalcon.chatkit.R;
import com.stfalcon.chatkit.commons.models.IDialog;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Anton Bevza on 12/9/16.
 */

public class DialogsListAdapter<DIALOG extends IDialog>
        extends RecyclerView.Adapter<DialogViewHolder> {

    private List<DIALOG> items = new ArrayList<>();
    private int itemLayoutId;
    private Class<? extends DialogViewHolder> holderClass;
    private DialogViewHolder.OnLoadImagesListener onLoadImagesListener;
    private DialogViewHolder.OnItemClickListener onItemClickListener;
    private DialogViewHolder.OnLongItemClickListener onLongItemClickListener;

    public DialogsListAdapter(@LayoutRes int itemLayoutId, Class<? extends DialogViewHolder> holderClass, List<DIALOG> dialogs) {
        this.itemLayoutId = itemLayoutId;
        this.holderClass = holderClass;
        this.items = dialogs;
    }

    public DialogsListAdapter(List<DIALOG> dialogs) {
        this(R.layout.item_dialog, DefaultDialogViewHolder.class, dialogs);
    }

    public DialogsListAdapter(@LayoutRes int itemLayoutId, List<DIALOG> dialogs) {
        this(itemLayoutId, DefaultDialogViewHolder.class, dialogs);
    }

    @SuppressWarnings("unchecked")
    @Override
    public void onBindViewHolder(DialogViewHolder holder, int position) {
        holder.setOnLoadImagesListener(onLoadImagesListener);
        holder.setOnItemClickListener(onItemClickListener);
        holder.setOnLongItemClickListener(onLongItemClickListener);
        holder.onBind(items.get(position));
    }

    @Override
    public DialogViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(itemLayoutId, parent, false);

        try {
            Constructor<? extends DialogViewHolder> constructor = holderClass.getDeclaredConstructor(View.class);
            constructor.setAccessible(true);
            return constructor.newInstance(v);
        } catch (Exception ignore) {
        }
        return null;
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void removeItemWithId(String id) {
        for (int i = 0; i < items.size(); i++) {
            if (items.get(i).getId().equals(id)) {
                items.remove(i);
                notifyItemRemoved(i);
            }
        }
    }

    public void clear() {
        if (items != null) {
            items.clear();
        }
    }

    public void setItems(List<DIALOG> items) {
        this.items = items;
        notifyDataSetChanged();
    }

    public void addItems(List<DIALOG> newItems) {
        if (newItems != null) {
            if (items == null) {
                items = new ArrayList<>();
            }
            int curSize = items.size();
            items.addAll(newItems);
            notifyItemRangeInserted(curSize, items.size());
        }
    }

    public void updateItem(int position, DIALOG item) {
        if (items == null) {
            items = new ArrayList<>();
        }
        notifyItemChanged(position);
    }

    public void setOnLoadImagesListener(DialogViewHolder.OnLoadImagesListener onLoadImagesListener) {
        this.onLoadImagesListener = onLoadImagesListener;
    }

    public DialogViewHolder.OnLoadImagesListener getOnLoadImagesListener() {
        return onLoadImagesListener;
    }

    public DialogViewHolder.OnItemClickListener getOnItemClickListener() {
        return onItemClickListener;
    }

    public void setOnItemClickListener(DialogViewHolder.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public DialogViewHolder.OnLongItemClickListener getOnLongItemClickListener() {
        return onLongItemClickListener;
    }

    public void setOnLongItemClickListener(DialogViewHolder.OnLongItemClickListener onLongItemClickListener) {
        this.onLongItemClickListener = onLongItemClickListener;
    }
}
