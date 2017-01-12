package com.stfalcon.chatkit.sample.dialogs.fixtures;

import android.support.annotation.NonNull;

import com.stfalcon.chatkit.commons.models.IMessage;
import com.stfalcon.chatkit.commons.models.IUser;
import com.stfalcon.chatkit.sample.models.DefaultDialog;
import com.stfalcon.chatkit.sample.models.DefaultUser;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

/**
 * Created by troy379 on 07.09.16.
 */
public final class ChatListFixtures {
    private ChatListFixtures() {
        throw new AssertionError();
    }


    private static ArrayList<String> images = new ArrayList<String>() {
        {
            add("http://i.imgur.com/pv1tBmT.png");
            add("http://i.imgur.com/R3Jm1CL.png");
            add("http://i.imgur.com/ROz4Jgh.png");
            add("http://i.imgur.com/Qn9UesZ.png");
        }
    };
    private static final ArrayList<String> groupChatImages = new ArrayList<String>() {
        {
            add("http://i.imgur.com/1vU8qF8.png");
            add("http://i.imgur.com/vy1UvUJ.png");
            add("http://i.imgur.com/vvNbXWt.png");
        }
    };
    private static final ArrayList<String> names = new ArrayList<String>() {
        {
            add("Samuel Reynolds");
            add("Kyle Hardman");
            add("Zoe Milton");
            add("Angel Ogden");
            add("Zoe Milton");
            add("Angelina Mackenzie");
            add("Kyle Oswald");
            add("Abigail Stevenson");
            add("Julia Goldman");
            add("Jordan Gill");
            add("Michelle Macey");
        }
    };
    private static final ArrayList<String> groupChatTitles = new ArrayList<String>() {
        {
            add("Samuel, Michelle");
            add("Jordan, Jordan, Zoe");
            add("Julia, Angel, Kyle, Jordan");
        }
    };
    private static final ArrayList<String> messages = new ArrayList<String>() {
        {
            add("Hello!");
            add("Hello! No problem. I can today at 2 pm. And after we can go to the office.");
            add("At first, for some time, I was not able to answer him one word");
            add("At length one of them called out in a clear, polite, smooth dialect, not unlike in sound to the Italian");
            add("By the bye, Bob, said Hopkins");
            add("He made his passenger captain of one, with four of the men; and himself, his mate, and five more, went in the other; and they contrived their business very well, for they came up to the ship about midnight.");
            add("So saying he unbuckled his baldric with the bugle");
            add("Just then her head struck against the roof of the hall: in fact she was now more than nine feet high, and she at once took up the little golden key and hurried off to the garden door.");
        }
    };
    private static SecureRandom rnd = new SecureRandom();

    public static ArrayList<DefaultDialog> getChatList() {
        ArrayList<DefaultDialog> chats = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            chats.add(getDialog());
        }
        return chats;
    }

    private static IMessage getMessage(final Date date) {
        return new IMessage() {
            @Override
            public String getId() {
                return Long.toString(UUID.randomUUID().getLeastSignificantBits());
            }

            @Override
            public String getText() {
                return messages.get(rnd.nextInt(messages.size()));
            }

            @Override
            public IUser getUser() {
                return ChatListFixtures.getUser();
            }

            @Override
            public Date getCreatedAt() {
                return date;
            }

            @Override
            public String getStatus() {
                return null;
            }
        };
    }

    private static DefaultDialog getDialog() {
        ArrayList<IUser> users = getUsers();
        return new DefaultDialog(String.valueOf(UUID.randomUUID().getLeastSignificantBits()),
                users.size() > 1 ? groupChatTitles.get(users.size() - 2) : users.get(0).getName(),
                users.size() > 1 ? groupChatImages.get(users.size() - 2) : images.get(rnd.nextInt(4)),
                users,
                getMessage(Calendar.getInstance().getTime()), rnd.nextInt(3));
    }

    private static ArrayList<IUser> getUsers() {
        ArrayList<IUser> users = new ArrayList<>();
        int usersCount = 1 + rnd.nextInt(4);
        for (int i = 0; i < usersCount; i++) {
            users.add(getUser());
        }
        return users;
    }

    @NonNull
    private static IUser getUser() {
        return new DefaultUser(String.valueOf(UUID.randomUUID().getLeastSignificantBits()),
                names.get(rnd.nextInt(names.size())), images.get(rnd.nextInt(4)));
    }
}
