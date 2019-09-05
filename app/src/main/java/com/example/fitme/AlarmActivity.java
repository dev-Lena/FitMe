package com.example.fitme;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class AlarmActivity extends AppCompatActivity {


    private MediaPlayer mediaPlayer;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);

        // 알림음 재생
        this.mediaPlayer = MediaPlayer.create(this,R.raw.xylophone);
        this.mediaPlayer.start();

        findViewById(R.id.buttonClose).setOnClickListener(mClickListener);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        // 알림음이 종료됨
        //MediaPlayer release
        if(this.mediaPlayer != null ) {
            this.mediaPlayer.release();
            this.mediaPlayer = null;

        }
    }

    // 알람 종료
    // 알림음이 종료됨
    private void close(){
        if (this.mediaPlayer.isPlaying()){
            this.mediaPlayer.stop();
            this.mediaPlayer.release();
            this.mediaPlayer = null;
        }
    }

    View.OnClickListener mClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.buttonClose :
                    //알림 종료 버튼을 누르면 알림이 꺼지고 알람을 맞췄던 화면으로 이동
                    Intent intent = new Intent(AlarmActivity.this, timesale.class);
                    startActivity(intent);
                    close();
                    break;

            }
        }
    };
}
