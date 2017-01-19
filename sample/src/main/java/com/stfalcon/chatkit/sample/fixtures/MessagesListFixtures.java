package com.stfalcon.chatkit.sample.fixtures;

import com.stfalcon.chatkit.commons.models.IMessage;
import com.stfalcon.chatkit.commons.models.IUser;
import com.stfalcon.chatkit.sample.models.DefaultUser;

import java.util.Date;
import java.util.UUID;

/*
 * Created by troy379 on 12.12.16.
 */
public final class MessagesListFixtures extends Fixtures {

    private MessagesListFixtures() {
        throw new AssertionError();
    }

    public static class Message implements IMessage {

        private long id;
        private String text;

        public Message() {
            this(messages.get(rnd.nextInt(messages.size())));
        }

        public Message(String text) {
            this.text = text;
            this.id = UUID.randomUUID().getLeastSignificantBits();
        }

        @Override
        public Date getCreatedAt() {
            return new Date();
        }

        @Override
        public String getId() {
            return String.valueOf(id);
        }

        @Override
        public DefaultUser getUser() {
            return new DefaultUser(id % 2 == 0 ? "0" : "1", id % 2 == 0 ? names.get(0) : names.get(1),
                    id % 2 == 0 ? avatars.get(0) : avatars.get(1), "online");
        }

        @Override
        public String getText() {
            return text;
        }

        @Override
        public String getStatus() {
            return "Sent";
        }
    }
}
