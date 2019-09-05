package com.example.fitme;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class first_main extends AppCompatActivity {

    private ArrayList<List> userData= new ArrayList<List>();

    private SharedPreferences logined_user;
    private SharedPreferences.Editor user_editor;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.e("first_main","자동로그인 확인중입니다");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_main);




        startLoading();

        }// onCreate 닫는 중괄호

    private void startLoading() {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
//                Intent intent = new Intent(first_main.this, login.class);
//                startActivity(intent);
                autoLogin();  // 자동 로그인 검사
                finish();
            }
        }, 3000);
        Log.e("first_main","4초 delay");

    }

    // 자동 로그인 검사하기
    private void autoLogin(){

// 마지막으로 로그인했던 유저의 데이터가 담겨있는 쉐어드를 호출
        logined_user = getSharedPreferences("logined_user", Context.MODE_PRIVATE);   // 현재 로그인한 회원의 정보만 담겨있는 쉐어드를 불러와서
        Log.e("first main 클래스", "마지막으로 로그인한 회원의 정보가 있는 쉐어드인 logined_user 쉐어드를 가져온다" + logined_user);

        // sharedPreferences라는 이름의 쉐어드프리퍼런스에서 String을 가져오는데
        // 뭘 가져오냐면 사용자가 입력한 editText_email랑 같은 값을 찾아서 가져와서 String json이라는 변수에 넣어줌
//        String last_user_json = logined_user.getString("login_user", "");
//        // logined_user라는 쉐어드에 저장되어있는 logined_user라는 키에 담겨있는 값을 불러와서 json이라는 변수에 담음
//        Log.e("feed 클래스에서 로그인 버튼을 눌렀을 때", "여기 확인하기 : " + last_user_json);
        String last_user_json = logined_user.getString("user_email", "");
        Log.e("first_main 클래스", "마지막으로 로그인한 유저의 이메일 정보 가져오기 : " + last_user_json );

        if(last_user_json.length() == 0) {  // 마지막으로 로그인한 유저의 이메일 정보가 없으면
            // call Login Activity
            intent = new Intent(first_main.this, login.class);  // 로그인 화면으로 넘어가라
            startActivity(intent);
            this.finish();
        } else {   // 마지막으로 로그인한 유저의 이메일 정보가 있다면
            // Call Next Activity
            intent = new Intent(first_main.this, feed.class);  // 피드 화면 (= 메인 화면)으로 넘어가고
//                intent.putExtra("STD_NUM", SaveSharedPreference.getUserName(this).toString());
            startActivity(intent);
            Toast.makeText(getApplication(),"자동 로그인 되었습니다",Toast.LENGTH_SHORT).show();
            this.finish();
        }
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
