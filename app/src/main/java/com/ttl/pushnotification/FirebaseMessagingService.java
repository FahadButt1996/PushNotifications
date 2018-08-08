package com.ttl.pushnotification;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

import com.google.firebase.messaging.RemoteMessage;
import com.ttl.pushnotification.DAO.DataBase;
import com.ttl.pushnotification.controller.ApplicationUtils;
import com.ttl.pushnotification.model.NotificationModel;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.Map;
import static android.support.v4.app.NotificationCompat.DEFAULT_VIBRATE;
import static android.support.v4.app.NotificationCompat.FLAG_SHOW_LIGHTS;

/**
 * Created by fahad.waqar on 27-Jul-18.
 */

public class FirebaseMessagingService extends com.google.firebase.messaging.FirebaseMessagingService {


    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        Intent intent = new Intent(this , MainActivity.class);
        Map<String, String> data = remoteMessage.getData();

        String jsonData = data.toString();
        String title = "";
        String body = "";
        JSONObject rr ;
        try {
            rr = new JSONObject(jsonData);
            body = rr.getJSONArray("values").getJSONObject(0).getJSONObject("nameValuePairs").get("body").toString();
            title = rr.getJSONArray("values").getJSONObject(0).getJSONObject("nameValuePairs").get("title").toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }


        intent.putExtra("title" , title );
        intent.putExtra("body" , body );

        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this , 0 , intent , PendingIntent.FLAG_ONE_SHOT);
        NotificationCompat.Builder notification_builder = new NotificationCompat.Builder(this);
        notification_builder.setContentTitle(title);
        notification_builder.setContentText(body);
        notification_builder.setAutoCancel(true);
        notification_builder.setSmallIcon(R.drawable.ic_about);
        notification_builder.setContentIntent(pendingIntent);
        insertNotificationToDatabase(title , body);
//        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
//        notification_builder.setSound(Uri.parse("android.resource://" + getApplicationContext().getPackageName() + "/" + R.raw.music));
        notification_builder.setDefaults( DEFAULT_VIBRATE | FLAG_SHOW_LIGHTS );
        NotificationManager notificationManager = (NotificationManager)getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0 ,notification_builder.build());
    }

    private void insertNotificationToDatabase(String title , String body) {

        DataBase.getInstance(getApplicationContext()).getDao().insertUser(new NotificationModel(""+title , ""+body, "Available" , ApplicationUtils.getDate()));
    }
}
