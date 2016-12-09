package com.stfalcon.chatkit.features.messages.models;

import java.util.Date;

/*
 * Created by troy379 on 09.12.16.
 */
public class DefaultMessage {

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

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
