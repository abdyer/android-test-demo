package org.andydyer.androidtestdemo.api;

import lombok.Getter;

/**
 * Created by andy on 8/23/14.
 */
public class Event {

    @Getter long id;
    @Getter String type;
    @Getter Actor actor;
    @Getter Repo repo;
    @Getter String createdAt;
}
