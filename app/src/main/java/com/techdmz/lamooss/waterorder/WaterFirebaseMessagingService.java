package com.techdmz.lamooss.waterorder;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.nfc.Tag;
import android.os.IBinder;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Map;

public class WaterFirebaseMessagingService extends FirebaseMessagingService {

    public WaterFirebaseMessagingService() {
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        Log.d("FirebaseMsgSvr" , "onMessageReceived() 호출됨.");

        String from = remoteMessage.getFrom();
        Map<String, String> data = remoteMessage.getData();
        String contents = data.get("contents");

        Log.d("FirebaseMsgSvr", "from : " + from + ", contents : + contects");

        sendToActivity(getApplicationContext(), from, contents);
    }

    public void sendToActivity(Context context, String from, String contents)
    {
        Intent intent = new Intent(context, MainActivity.class);
        intent.putExtra("from", from);
        intent.putExtra("contents", contents);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);

        context.startActivity(intent);
    }


    /*
    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
    */
}
