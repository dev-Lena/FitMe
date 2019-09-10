package com.example.fitme;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class insight extends AppCompatActivity {
/** 복사해서 만들어만 놓았음 수정해야함 **/
    BottomNavigationView bottomNavigationView; // 바텀 네이게이션 메뉴  -> 하단바

    ImageButton imageButton_follow_back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.e("follow","onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insight);

//하단바

        bottomNavigationView = findViewById(R.id.bottomNavi);
        // 하단바 누를 때 색 바뀌게 하는 중
        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(2);
        menuItem.setChecked(true);
        //
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                switch (menuItem.getItemId()) {
                    case R.id.action_home :
                        Intent home_intent = new Intent(insight.this,feed.class);
                        home_intent.addFlags (Intent.FLAG_ACTIVITY_NO_ANIMATION);
                        startActivity(home_intent);//액티비티 띄우기
//                        startActivityForResult(intent,sub);//액티비티 띄우기
                        break;
                    case R.id.action_search :
                        Intent search_intent = new Intent(insight.this,searching.class);
                        search_intent.addFlags (Intent.FLAG_ACTIVITY_NO_ANIMATION);
                        startActivity(search_intent);//액티비티 띄우기
                        break;
                    case R.id.action_insight :
                        Intent write_intent = new Intent(insight.this,insight.class);
                        write_intent.addFlags (Intent.FLAG_ACTIVITY_NO_ANIMATION);
                        startActivity(write_intent);//액티비티 띄우기
                        break;
                    case R.id.action_notification :
                        Intent insight_intent = new Intent(insight.this,notification.class);
                        insight_intent.addFlags (Intent.FLAG_ACTIVITY_NO_ANIMATION);
                        startActivity(insight_intent);//액티비티 띄우기
                        break;
                    case R.id.action_mypage :
                        Intent mycloset_intent = new Intent(insight.this, mypage.class);
                        mycloset_intent.addFlags (Intent.FLAG_ACTIVITY_NO_ANIMATION);
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
        Log.e("follow","onRestart");
        //액티비티가 중단되었다가 다시 시작
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.e("follow","onStart");
        //액티비티가 화면에 나타나기 시작
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e("follow","onResume");
        //액티비티가 화면에 나타나고 상호작용이 가능해짐
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.e("follow","onPause");
        overridePendingTransition(0, 0);
        //다른 액티비티가 시작되려함, 이 액티비티는 중단되려하고 백그라운드로 들어갑니다.
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.e("follow","onStop");
        //액티비티가 더 이상 화면에 나타나지 않음,중단된 상태
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e("follow","onDestroy");
        //액티비티가 종료되려고 합니다.
    }

}
