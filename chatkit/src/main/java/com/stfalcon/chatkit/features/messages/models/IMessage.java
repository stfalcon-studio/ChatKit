package com.stfalcon.chatkit.features.messages.models;

import java.util.Date;

/*
 * Created by troy379 on 09.12.16.
 */
public interface IMessage {

    Date getCreatedAt();

    String getId();

    String getAuthorId();

    String getText();

    // TODO: 09.12.16 most needed methods

}
