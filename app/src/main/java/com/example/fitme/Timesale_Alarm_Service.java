package com.example.fitme;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

public class Timesale_Alarm_Service extends Service {
    /**
     * 온라인 쇼핑몰 타임 세일 알람 시간 백그라운드에서 카운팅
     **/

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        // Foreground에서 실행되면 Notification을 보여줘야 함

//        if(Build.VERSION_CODES >= Build.VERSION_CODES.0){\

        String channelId = createNotificationChannel();

        Notification.Builder builder = new Notification.Builder(this, channelId);
        Notification notification = builder.setOngoing(true)
                .setSmallIcon(R.mipmap.ic_launcher)
//                    .setCategory(Notification.CATEGORY_SERVICE)
                .build();

        startForeground(1, notification);
//        }

        // 알림장 호출
        Intent intent1 = new Intent(this, Timesale_Alarm_Activity.class);
        //새로운 TASK를 생성해서 Activity를 최상위로 올림
        intent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent1);
        Log.e("Timesale_Alarm_Service","Alarm");

//        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.0){
//            stopForeground(true);
//        }
        stopSelf();

        return START_NOT_STICKY;
    }

    private String createNotificationChannel() {
        String channelId = "Alarm";
        String channelName = getString(R.string.app_name);
        NotificationChannel channel = new NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_NONE);
        channel.setSound(null, null);
        channel.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);
        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        manager.createNotificationChannel(channel);

        return channelId;
    }
}// 클래스 닫는 중괄호



