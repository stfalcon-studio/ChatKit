package com.stfalcon.chatkit.commons.models;

import java.util.Date;

/**
 * Created by Anton Bevza on 12/9/16.
 */

public interface IMessage {

    String getId();

    String getText();

    IUser getUser();

    Date getCreatedAt();
}
