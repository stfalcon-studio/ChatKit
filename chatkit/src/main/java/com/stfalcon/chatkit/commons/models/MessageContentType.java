package com.stfalcon.chatkit.commons.models;

/*
 * Created by troy379 on 28.03.17.
 */

/**
 * Interface used to mark messages as media types. For custom types see {@link } TODO link
 */
public interface MessageContentType extends IMessage {

    /**
     * Media type for image message.
     */
    interface Image extends MessageContentType {
        String getImageUrl();
    }

    /**
     * Media type for file message.
     */
    interface File extends MessageContentType {
        String getFileUrl();
    }

}
