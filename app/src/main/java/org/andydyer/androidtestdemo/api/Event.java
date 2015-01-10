package org.andydyer.androidtestdemo.api;

import android.text.format.DateUtils;

import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;
import java.util.TimeZone;

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

    public String getCreatedAtRelativeTime() {
        try {
            TimeZone utc = TimeZone.getTimeZone("UTC");
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
            format.setTimeZone(utc);
            GregorianCalendar calendar = new GregorianCalendar(utc);
            calendar.setTime(format.parse(createdAt));
            return DateUtils.getRelativeTimeSpanString(calendar.getTimeInMillis(),
                    System.currentTimeMillis(), DateUtils.SECOND_IN_MILLIS).toString();
        } catch (Exception e) {
            return createdAt;
        }
    }
}
