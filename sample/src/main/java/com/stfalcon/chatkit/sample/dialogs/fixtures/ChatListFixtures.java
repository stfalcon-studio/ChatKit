package com.stfalcon.chatkit.sample.dialogs.fixtures;

import android.support.annotation.NonNull;

import com.stfalcon.chatkit.commons.models.IDialog;
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
            add("https://pickaface.net/assets/images/slides/slide2.png");
            add("https://pickaface.net/assets/images/slides/slide4.png");
            add("https://acadeu.com/static/img/demo/avatars/jorgediaz.jpg");
            add("http://avatar-creator.net/assets/temp-avatars/svgA9704302547033876.png");
        }
    };
    private static String groupChatImage = "https://lh6.ggpht.com/e67TRisvQxr9wUxjujVcVjPLFDfI3aZmLHuBeFO5esWO7FyjtO1PckubLZsJlzkC8Byv=w300";
    private static final String AB = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    private static SecureRandom rnd = new SecureRandom();

    public static ArrayList<IDialog> getChatList() {
        ArrayList<IDialog> chats = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            chats.add(getChat());
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
                return randomString(getRandomInt());
            }

            @Override
            public IUser getUser() {
                return getUser();
            }

            @Override
            public Date getCreatedAt() {
                return date;
            }
        };
    }

    private static String randomString(int len) {
        StringBuilder sb = new StringBuilder(len);
        for (int i = 0; i < len; i++)
            sb.append(AB.charAt(rnd.nextInt(AB.length())));
        return sb.toString();
    }

    private static int getRandomInt() {
        return rnd.nextInt(40) + 5;
    }

    public static IDialog getChat() {
        ArrayList<IUser> users = getUsers();

        return new DefaultDialog(String.valueOf(UUID.randomUUID().getLeastSignificantBits()),
                users.size() > 1 ? "Group" : users.get(0).getName(),
                users.size() > 1 ? groupChatImage : images.get(rnd.nextInt(4)),
                users,
                getMessage(Calendar.getInstance().getTime()), rnd.nextInt(2));
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
                randomString(5) + " " + randomString(5), images.get(rnd.nextInt(4)));
    }
}
