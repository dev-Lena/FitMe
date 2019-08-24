package com.example.fitme;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class feed extends AppCompatActivity {

    BottomNavigationView bottomNavigationView; // 바텀 네이게이션 메뉴  -> 하단바
    BottomNavigationView bottomNavigationMenu; // 바텀 네이게이션 메뉴  -> 하단바
    ImageView imageView_recommendbot, imageView_notification;


// 리사이클러뷰에 필요
    ArrayList<feed_MainData> arrayList;
    feed_Adapter feed_adapter;
    RecyclerView recyclerView;
    LinearLayoutManager linearLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.e("feed", "onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed);


        imageView_recommendbot = findViewById(R.id.imageView_recommendbot);  // 추천봇
        imageView_notification = findViewById(R.id.imageView_notification);  // 알림 (내 소식))

        /**여기서부터 리사이클러뷰 만들기**/

        recyclerView = (RecyclerView)findViewById(R.id.feed_recyclerView);
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        arrayList = new ArrayList<>();

        feed_adapter = new feed_Adapter(arrayList);
        recyclerView.setAdapter(feed_adapter);




//알림
        imageView_notification.setOnClickListener(new ImageView.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent notification_intent = new Intent(feed.this, notification.class);
                startActivity(notification_intent); //액티비티 이동

            }
        });

//하단바
        bottomNavigationView = findViewById(R.id.bottomNavi);
        // 하단바 누를 때 색 바뀌게 하는 중
        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(0);
        menuItem.setChecked(true);
        //
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                switch (menuItem.getItemId()) {
                    case R.id.action_home:
                        Intent home_intent = new Intent(feed.this, feed.class);
                        startActivity(home_intent);//액티비티 띄우기
//                        startActivityForResult(intent,sub);//액티비티 띄우기
                        break;
                    case R.id.action_search:
                        Intent search_intent = new Intent(feed.this, searching.class);
                        startActivity(search_intent);//액티비티 띄우기
                        break;
                    case R.id.action_write_review:
                        Intent write_intent = new Intent(feed.this, review_category.class);
                        startActivity(write_intent);//액티비티 띄우기
                        break;
                    case R.id.action_insight:
                        Intent insight_intent = new Intent(feed.this, insight.class);
                        startActivity(insight_intent);//액티비티 띄우기
                        break;
                    case R.id.action_mycloset:
                        Intent mycloset_intent = new Intent(feed.this, my_closet.class);
                        startActivity(mycloset_intent);//액티비티 띄우기
                        break;
                }


                return false;

            }
        });
        //여기 내용까지 onCreate 안
    }


    // 생명주기 로그 찍으면서 확인
    @Override
    protected void onRestart() {
        super.onRestart();
        Log.e("feed", "onRestart");
        //액티비티가 중단되었다가 다시 시작
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.e("feed", "onStart");
        //액티비티가 화면에 나타나기 시작
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e("feed", "onResume");
        //액티비티가 화면에 나타나고 상호작용이 가능해짐
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.e("feed", "onPause");
        //다른 액티비티가 시작되려함, 이 액티비티는 중단되려하고 백그라운드로 들어갑니다.
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.e("feed", "onStop");
        //액티비티가 더 이상 화면에 나타나지 않음,중단된 상태
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e("feed", "onDestroy");
        //액티비티가 종료되려고 합니다.
    }

}
