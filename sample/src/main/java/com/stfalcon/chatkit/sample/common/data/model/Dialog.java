package com.stfalcon.chatkit.sample.common.data.model;

import com.stfalcon.chatkit.commons.models.IDialog;

/*
 * Created by troy379 on 04.04.17.
 */
public class Dialog implements IDialog<Message> {

    private String id;
    private String dialogPhoto;
    private String dialogName;
    private Message lastMessage;
    private int usersCount;

    private int unreadCount;

    public Dialog(String id, String name, String photo, Message lastMessage, int unreadCount, int usersCount) {
        this.id = id;
        this.dialogName = name;
        this.dialogPhoto = photo;
        this.lastMessage = lastMessage;
        this.unreadCount = unreadCount;
        this.usersCount = usersCount;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getDialogPhoto() {
        return dialogPhoto;
    }

    @Override
    public String getDialogName() {
        return dialogName;
    }

    @Override
    public int getUsersCount() {
        return usersCount;
    }

    @Override
    public Message getLastMessage() {
        return lastMessage;
    }

    @Override
    public void setLastMessage(Message lastMessage) {
        this.lastMessage = lastMessage;
    }

    @Override
    public int getUnreadCount() {
        return unreadCount;
    }

    public void setUnreadCount(int unreadCount) {
        this.unreadCount = unreadCount;
    }
}
