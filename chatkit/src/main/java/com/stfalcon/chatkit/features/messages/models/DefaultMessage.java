package com.stfalcon.chatkit.features.messages.models;

import com.stfalcon.chatkit.commons.models.IMessage;
import com.stfalcon.chatkit.commons.models.IUser;

import java.util.Date;

/*
 * Created by troy379 on 09.12.16.
 */
public class DefaultMessage implements IMessage {

    public DefaultMessage(long messageId, long authorId, Date createdAt, String text) {
        this.messageId = messageId;
        this.authorId = authorId;
        this.createdAt = createdAt;
        this.text = text;
    }

    private long messageId;
    private long authorId;
    private Date createdAt;
    private String text;

    public long getMessageId() {
        return messageId;
    }

    public void setMessageId(long messageId) {
        this.messageId = messageId;
    }

    public long getAuthorId() {
        return authorId;
    }

    public void setAuthorId(long authorId) {
        this.authorId = authorId;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String getId() {
        return String.valueOf(messageId);
    }

    public String getText() {
        return text;
    }

    @Override
    public IUser getUser() {
        return null;
    }

    public void setText(String text) {
        this.text = text;
    }
}
