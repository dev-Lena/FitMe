package com.example.fitme;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class first_main extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.e("first_main","자동로그인 확인중입니다");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_main);

        Toast.makeText(getApplication(),"자동 로그인 확인 중입니다",Toast.LENGTH_SHORT).show();


        startLoading();




    }// onCreate 닫는 중괄호

    private void startLoading() {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(first_main.this, login.class);
                startActivity(intent);
                finish();
            }
        }, 4000);
        Log.e("first_main","4초 delay");

    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.e("hashtag","onRestart");
        //액티비티가 중단되었다가 다시 시작
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.e("hashtag","onStart");
        //액티비티가 화면에 나타나기 시작
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e("hashtag","onResume");
        //액티비티가 화면에 나타나고 상호작용이 가능해짐
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.e("hashtag","onPause");
        //다른 액티비티가 시작되려함, 이 액티비티는 중단되려하고 백그라운드로 들어갑니다.
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.e("hashtag","onStop");
        //액티비티가 더 이상 화면에 나타나지 않음,중단된 상태
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e("hashtag","onDestroy");
        //액티비티가 종료되려고 합니다.
    }


}
