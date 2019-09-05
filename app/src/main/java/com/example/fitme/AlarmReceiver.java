package com.example.fitme;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class AlarmReceiver extends BroadcastReceiver {


    @Override
    public void onReceive(Context context, Intent intent) {
        Intent sIntent = new Intent(context, AlarmService.class);
//        Oreo26 버전 이후 부터는 Background에서 하는게 안되고 foreground에서 해야함
//        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.0){
//            context.startForegroundService(sIntent);
//        }else{

        /**알람 서비스 방송 받기**/
            context.startService(sIntent);
//        }
    }
}
