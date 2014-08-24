package org.andydyer.androidtestdemo.api;

import lombok.Getter;

/**
 * Created by andy on 8/23/14.
 */
public class Actor {

    @Getter long id;
    @Getter String login;
    @Getter String gravatarId;
    @Getter String url;
    @Getter String avatarUrl;
}
