package com.stfalcon.chatkit.sample;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Anton Bevza on 1/17/17.
 */

public class ChatSamplesListAdapter extends RecyclerView.Adapter<ChatSamplesListAdapter.ChatPresentationViewHolder> {
    private List<ChatSample> items;
    private OnItemClickListener onItemClickListener;

    public ChatSamplesListAdapter(List<ChatSample> items) {
        this.items = items;
    }

    @Override
    public ChatPresentationViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_chat_sample, parent, false);
        return new ChatPresentationViewHolder(v);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public void onBindViewHolder(ChatPresentationViewHolder holder, int position) {
        final ChatSample item = items.get(position);
        holder.tvName.setText(item.name);
        holder.infoImage.setImageResource(item.imageRes);
        holder.root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onItemClickListener.onItemClickListener(item);
            }
        });
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    class ChatPresentationViewHolder extends RecyclerView.ViewHolder {
        View root;
        ImageView infoImage;
        TextView tvName;

        ChatPresentationViewHolder(View itemView) {
            super(itemView);
            root = itemView.findViewById(R.id.card);
            tvName = (TextView) itemView.findViewById(R.id.infoText);
            infoImage = (ImageView) itemView.findViewById(R.id.infoImage);
        }
    }

    public static class ChatSample {
        public ChatSample(String name, String description, int imageRes, Type type) {
            this.name = name;
            this.description = description;
            this.imageRes = imageRes;
            this.type = type;
        }

        public enum Type {
            DEFAULT, CUSTOM_ATTR, CUSTOM_LAYOUT, CUSTOM_VIEW_HOLDER
        }

        String name;
        String description;
        int imageRes;
        Type type;
    }

    interface OnItemClickListener {
        void onItemClickListener(ChatSample item);
    }
}
