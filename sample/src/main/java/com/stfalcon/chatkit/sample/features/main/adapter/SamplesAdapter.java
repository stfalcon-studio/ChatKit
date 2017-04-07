package com.stfalcon.chatkit.sample.features.main.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.stfalcon.chatkit.sample.R;

import java.util.List;

/*
 * Created by troy379 on 04.04.17.
 */
public class SamplesAdapter extends RecyclerView.Adapter<SamplesAdapter.SampleViewHolder> {

    private List<Sample> items;
    private ClickListener clickListener;

    public SamplesAdapter(List<Sample> items) {
        this.items = items;
    }

    @Override
    public SampleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new SampleViewHolder(
                LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_sample, parent, false));
    }

    @Override
    public void onBindViewHolder(SampleViewHolder holder, int position) {
        final Sample item = items.get(position);
        holder.title.setText(item.title);
        holder.cover.setImageResource(item.coverRes);
        holder.root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickListener.onItemClick(item.type);
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void setClickListener(ClickListener clickListener) {
        this.clickListener = clickListener;
    }

    static class SampleViewHolder extends RecyclerView.ViewHolder {
        View root;
        ImageView cover;
        TextView title;

        SampleViewHolder(View itemView) {
            super(itemView);
            root = itemView.findViewById(R.id.root);
            cover = (ImageView) itemView.findViewById(R.id.cover);
            title = (TextView) itemView.findViewById(R.id.title);
        }
    }

    public interface ClickListener {
        void onItemClick(Sample.Type type);
    }
}
