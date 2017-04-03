package com.stfalcon.chatkit.commons.models;

import com.stfalcon.chatkit.messages.MessageHoldersConfigurator;

/*
 * Created by troy379 on 28.03.17.
 */

/**
 * Interface used to mark messages as custom content types. For its representation see {@link MessageHoldersConfigurator}
 */

public interface MessageContentType extends IMessage {

    /**
     * Default media type for image message.
     */
    interface Image extends IMessage {
        String getImageUrl();
    }

    // other default types will be here

}
