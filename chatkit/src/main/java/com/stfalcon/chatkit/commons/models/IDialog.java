package com.stfalcon.chatkit.commons.models;

import java.util.List;

/**
 * Created by Anton Bevza on 12/9/16.
 */

public interface IDialog {

    String getId();

    List<IUser> getUsers();

    IMessage getLastMessage();

    int getUnreadCount();
}
