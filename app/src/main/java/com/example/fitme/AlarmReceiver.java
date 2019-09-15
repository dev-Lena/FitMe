package com.example.fitme;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class AlarmReceiver extends BroadcastReceiver {


    @Override
    public void onReceive(Context context, Intent intent) {
        Intent sIntent = new Intent(context, AlarmService.class);
        // intent로 서비스에서 계속 체크하고 있던 알람이 타겟 시간이 되었을 때 신호를 받음
//        Oreo26 버전 이후 부터는 Background에서 하는게 안되고 foreground에서 해야함
//        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.0){
//            context.startForegroundService(sIntent);
//        }else{

        /**알람 서비스 방송 받기**/
            context.startService(sIntent);
//        }
    }
}
