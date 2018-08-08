package com.ttl.pushnotification.DAO;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import com.ttl.pushnotification.model.NotificationModel;

/**
 * Created by fahad.waqar on 10-Apr-18.
 */
@Database(entities = {NotificationModel.class} , version = 2)

public abstract class DataBase extends RoomDatabase {
    private static final String DBName = "PGCNotifications.db";
    private static volatile DataBase instance;

    public static synchronized DataBase getInstance(Context context){
        if(instance == null){
            instance = create(context);
        }
        return instance;
    }

    private static DataBase create(Context context) {
        return Room.databaseBuilder(context , DataBase.class , DBName).allowMainThreadQueries().fallbackToDestructiveMigration().build();
    }

    public abstract DAO getDao();
}
