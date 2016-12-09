package com.stfalcon.chatkit.features.messages.models;

import java.util.Date;

/*
 * Created by troy379 on 09.12.16.
 */
public class DefaultMessage implements IMessage {

    public DefaultMessage(String messageId, String authorId, Date createdAt, String text) {
        this.messageId = messageId;
        this.authorId = authorId;
        this.createdAt = createdAt;
        this.text = text;
    }

    private String messageId;
    private String authorId;
    private Date createdAt;
    private String text;

    @Override
    public String getId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    @Override
    public String getAuthorId() {
        return authorId;
    }

    public void setAuthorId(String authorId) {
        this.authorId = authorId;
    }

    @Override
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
