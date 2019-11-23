package com.example.fitme;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class Alarm_Receiver extends BroadcastReceiver {


    @Override
    public void onReceive(Context context, Intent intent) {
        Intent sIntent = new Intent(context, Alarm_Service.class);
        // intent로 Service에서 계속 체크하고 있던 알람이 타겟 시간(알람이 울리는 시간)이 되었을 때 신호를 받음
//        Oreo26 버전 이후 부터는 Background에서 하면 안되고, foreground에서 해야함
//        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.0){
//            context.startForegroundService(sIntent);
//        }else{

        /**알람 서비스 방송 받기**/
            context.startService(sIntent);
//        }
    }
}
