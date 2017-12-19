package com.techdmz.lamooss.waterorder;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

public class WaterFirebaseInstanceIDService extends FirebaseInstanceIdService {
    public WaterFirebaseInstanceIDService() {
    }

    @Override
    public void onTokenRefresh() {
        Log.d("FirebaseIDSvr" , "onTokenRefresh() 호출됨.");

        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.d("FirebaseIDSvr", "refreshed Token : " + refreshedToken);
    }


    /*
    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
    */
}
