package com.ttl.pushnotification.DAO;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.ttl.pushnotification.model.NotificationModel;
import java.util.List;

/**
 * Created by fahad.waqar on 10-Apr-18.
 */
@Dao
public interface DAO {
    @Query("Select * from NotificationModel ORDER BY id DESC")
    List<NotificationModel> getAllNotifications();

    @Query("Select COUNT(*) from NotificationModel")
    int getCountOfNotificationsInDatabase();

    @Insert
    void insertUser(NotificationModel user);

    @Update
    void updateUser(NotificationModel user);

    @Query("DELETE FROM NotificationModel where id = :id")
    void DeleteSpecificNotification(int id);

    @Delete
    void deleteUser(NotificationModel user);

    @Query("Select id, title , textBody  from NotificationModel where title =:title ")
    List<NotificationModel> getSelectedNotification(String title);

}
