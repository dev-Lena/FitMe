package com.example.fitme;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class timesale extends AppCompatActivity {

    TextView clockTextView ;
    private static Handler mHandler ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.e("timesale 클래스에서","onCreate -> 타이머 하는 중");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timesale);

        mHandler = new Handler() ;

        // 핸들러로 전달할 runnable 객체. 수신 스레드 실행.
        final Runnable runnable = new Runnable() {
            @Override
            public void run() {
                Calendar cal = Calendar.getInstance();

                SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
                String strTime = sdf.format(cal.getTime());

                clockTextView = findViewById(R.id.clockTextView);
                clockTextView.setText(strTime);  // 위에서 SimpleDateFormat으로 얻어온 현재 시간을 담은 strTime 변수로 clockTextView를 set함.
            }
        } ;

        // 새로운 스레드 실행 코드. 1초 단위로 현재 시각 표시 요청.
        class NewRunnable implements Runnable {
            @Override
            public void run() {
                while (true) {

                    try {
                        Thread.sleep(1000);  // 1초마다 시간이 바뀌도록
                    } catch (Exception e) {
                        e.printStackTrace() ;
                    }

                    mHandler.post(runnable) ;
                }
            }
        }

        NewRunnable nr = new NewRunnable() ;  // 1초마다 시간 바뀌도록 한 runnable 객체 생성
        Thread t = new Thread(nr) ;  //  runnable을 참고하여 돌아가는 쓰레드 객체 생성
        t.start() ;  // 시작
    }// onCreate 닫는 중괄호


    @Override
    protected void onRestart() {
        super.onRestart();
        Log.e("timesale","onRestart");
        //액티비티가 중단되었다가 다시 시작
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.e("timesale","onStart");
        //액티비티가 화면에 나타나기 시작
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e("timesale","onResume");
        //액티비티가 화면에 나타나고 상호작용이 가능해짐
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.e("timesale","onPause");
        //다른 액티비티가 시작되려함, 이 액티비티는 중단되려하고 백그라운드로 들어갑니다.
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.e("timesale","onStop");
        //액티비티가 더 이상 화면에 나타나지 않음,중단된 상태
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e("timesale","onDestroy");
        //액티비티가 종료되려고 합니다.
    }
}
