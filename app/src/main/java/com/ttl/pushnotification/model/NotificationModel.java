package com.ttl.pushnotification.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by fahad.waqar on 02-Aug-18.
 */
@Entity
public class NotificationModel {
    @PrimaryKey(autoGenerate = true)
    public int id;
    public String title;
    public String textBody;
    public String status;
    public String date;

    public NotificationModel( String title, String textBody, String status, String date) {
//        this.id = id;
        this.title = title;
        this.textBody = textBody;
        this.status = status;
        this.date = date;
    }
}
