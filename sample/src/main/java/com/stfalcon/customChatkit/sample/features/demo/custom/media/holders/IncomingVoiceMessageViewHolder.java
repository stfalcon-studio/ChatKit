package com.stfalcon.customChatkit.sample.features.demo.custom.media.holders;

import android.view.View;
import android.widget.TextView;

import com.stfalcon.customChatkit.messages.MessageHolders;
import com.stfalcon.customChatkit.sample.R;
import com.stfalcon.customChatkit.sample.common.data.model.Message;
import com.stfalcon.customChatkit.sample.utils.FormatUtils;
import com.stfalcon.customChatkit.utils.DateFormatter;

/*
 * Created by troy379 on 05.04.17.
 */
public class IncomingVoiceMessageViewHolder
        extends MessageHolders.IncomingTextMessageViewHolder<Message> {

    private TextView tvDuration;
    private TextView tvTime;

    public IncomingVoiceMessageViewHolder(View itemView, Object payload) {
        super(itemView, payload);
        tvDuration = (TextView) itemView.findViewById(R.id.duration);
        tvTime = (TextView) itemView.findViewById(R.id.time);
    }

    @Override
    public void onBind(Message message) {
        super.onBind(message);
        tvDuration.setText(
                FormatUtils.getDurationString(
                        message.getVoice().getDuration()));
        tvTime.setText(DateFormatter.format(message.getCreatedAt(), DateFormatter.Template.TIME));
    }
}
