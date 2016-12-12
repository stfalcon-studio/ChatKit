package com.stfalcon.chatkit.sample.models;

import com.stfalcon.chatkit.commons.models.IUser;

/**
 * Created by Anton Bevza on 12/12/16.
 */
public class DefaultUser implements IUser {
    private String id;
    private String name;
    private String avatar;

    public DefaultUser(String id, String name, String avatar) {
        this.id = id;
        this.name = name;
        this.avatar = avatar;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getAvatar() {
        return avatar;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}
