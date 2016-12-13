package com.stfalcon.chatkit.sample;

import com.stfalcon.chatkit.commons.models.IMessage;
import com.stfalcon.chatkit.commons.models.IUser;
import com.stfalcon.chatkit.sample.dialogs.fixtures.ChatListFixtures;

import java.util.Date;

/*
 * Created by troy379 on 12.12.16.
 */
public final class Demo {
    private Demo() {
        throw new AssertionError();
    }

    public static class Message implements IMessage {

        private int id;
        private String text;

        public Message(int id) {
            this.id = id;
        }

        public Message(int id, String text) {
            this.id = id;
            this.text = text;
        }

        @Override
        public Date getCreatedAt() {
            return new Date();
        }

        @Override
        public String getId() {
            return Integer.toString(id);
        }

        @Override
        public IUser getUser() {
            return new IUser() {
                @Override
                public String getId() {
                    return id % 2 == 0 ? "0" : "1";
                }

                @Override
                public String getName() {
                    return "Peter";
                }

                @Override
                public String getAvatar() {
                    return "https://pickaface.net/assets/images/slides/slide2.png";
                }
            };
        }

        @Override
        public String getText() {
            return text == null ? id + ", example text" : text;
        }

        @Override
        public String getStatus() {
            return "Sent";
        }
    }
}
