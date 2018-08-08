package com.ttl.pushnotification;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdReceiver;
import com.google.firebase.iid.FirebaseInstanceIdService;

/**
 * Created by fahad.waqar on 27-Jul-18.
 */

public class MyFirebaseInstanceIdServic extends FirebaseInstanceIdService {

    private static final String REG_TOKEN = "REG_TOKEN";

    @Override
    public void onTokenRefresh() {
        String recent_roken = FirebaseInstanceId.getInstance().getToken();
        Log.d(REG_TOKEN , recent_roken);
    }
}
