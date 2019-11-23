package com.example.fitme;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import static com.example.fitme.Timesale_Activity.alarm_name;

public class Alarm_Activity extends AppCompatActivity {

    // 알람이 울릴 때 보여지는 xml을 다루는 액티비티

    private MediaPlayer mediaPlayer;
    TextView textView_alarm_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);

        // 알림음 재생
        this.mediaPlayer = MediaPlayer.create(this,R.raw.xylophone);
        this.mediaPlayer.start();

        findViewById(R.id.buttonClose).setOnClickListener(mClickListener);
        textView_alarm_name = findViewById(R.id.textView_alarm_name);


        Intent intent = getIntent();  // 데이터를 담아서 보낸 intent를 받아옴
//        String
//        alarm_name = intent.getStringExtra("알람이름");
//        String alarm_name = intent.getExtras().getString("알람이름"); /*String형*/
        textView_alarm_name.setText(alarm_name);
        Log.d("Alarm_Activity", "onCreate: alarm_name -> " + alarm_name);
        Log.d("Alarm_Activity", "onCreate: textView_alarm_name -> " + textView_alarm_name);
        // Timesale_Activity 클래스에서 인텐트에 담아서 보낸 사용자가 입력한 알람이름을 보여줌


        Intent link_intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://"+ alarm_name));
        startActivity(link_intent); //액티비티 이동


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

                    ActivityCompat.finishAffinity(Alarm_Activity.this);

                    close();
                    break;

            }
        }
    };
}
