package com.example.fitme;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class hashtag extends AppCompatActivity {

    BottomNavigationView bottomNavigationView; // 바텀 네이게이션 메뉴  -> 하단바

    ImageButton imageButton_follow_back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.e("hashtag","onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hashtag);

//하단바
        bottomNavigationView = findViewById (R.id.bottomNavi);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                switch (menuItem.getItemId()) {
                    case R.id.action_home :
                        Intent home_intent = new Intent(hashtag.this,feed.class);
                        startActivity(home_intent);//액티비티 띄우기
//                        startActivityForResult(intent,sub);//액티비티 띄우기
                        break;
                    case R.id.action_search :
                        Intent search_intent = new Intent(hashtag.this,searching.class);
                        startActivity(search_intent);//액티비티 띄우기
                        break;
                    case R.id.action_insight :
                        Intent write_intent = new Intent(hashtag.this,insight.class);
                        startActivity(write_intent);//액티비티 띄우기
                        break;
                    case R.id.action_notification :
                        Intent insight_intent = new Intent(hashtag.this,image_searching.class);
                        startActivity(insight_intent);//액티비티 띄우기
                        break;
                    case R.id.action_mypage :
                        Intent mycloset_intent = new Intent(hashtag.this, mypage.class);
                        startActivity(mycloset_intent);//액티비티 띄우기
                        break;
                }


                return false;

            }
        });

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
