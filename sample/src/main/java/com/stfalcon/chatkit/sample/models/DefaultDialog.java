package com.stfalcon.chatkit.sample.models;

import com.stfalcon.chatkit.commons.models.IDialog;
import com.stfalcon.chatkit.commons.models.IMessage;
import com.stfalcon.chatkit.commons.models.IUser;

import java.util.ArrayList;

/**
 * Created by Anton Bevza on 12/9/16.
 */

public class DefaultDialog implements IDialog {
    private String id;

    private ArrayList<IUser> users;

    private IMessage lastMessage;

    private int unreadCount;

    public DefaultDialog(String id, ArrayList<IUser> users, IMessage lastMessage, int unreadCount) {
        this.id = id;
        this.users = users;
        this.lastMessage = lastMessage;
        this.unreadCount = unreadCount;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public ArrayList<IUser> getUsers() {
        return users;
    }

    @Override
    public IMessage getLastMessage() {
        return lastMessage;
    }

    @Override
    public int getUnreadCount() {
        return unreadCount;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setUsers(ArrayList<IUser> users) {
        this.users = users;
    }

    public void setLastMessage(IMessage lastMessage) {
        this.lastMessage = lastMessage;
    }

    public void setUnreadCount(int unreadCount) {
        this.unreadCount = unreadCount;
    }
}
