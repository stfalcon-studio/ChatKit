package com.stfalcon.chatkit.features.messages.adapters.holders;

import android.view.View;
import android.widget.TextView;

import com.stfalcon.chatkit.R;
import com.stfalcon.chatkit.commons.adapter.ViewHolder;
import com.stfalcon.chatkit.features.utils.DatesUtils;

import java.util.Date;

/*
 * Created by troy379 on 12.12.16.
 */
public class DefaultDateHeaderViewHolder extends ViewHolder<Date> {

    private TextView text;

    public DefaultDateHeaderViewHolder(View itemView) {
        super(itemView);
        text = (TextView) itemView.findViewById(R.id.text);
    }

    @Override
    public void onBind(Date date) {
        text.setText(DatesUtils.format(date, DatesUtils.Template.STRING_MONTH));
    }
}
