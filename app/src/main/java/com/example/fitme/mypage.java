package com.example.fitme;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

public class mypage extends AppCompatActivity {

    private ArrayList<List> userData= new ArrayList<List>();

    private SharedPreferences logined_user;
    private SharedPreferences.Editor user_editor;
    Button button_review_timesale, button_logout, button_myreview, button_bookmarked_review  , button_follow , button_following , button_edit_hashtag , button_edit_profile ;
    BottomNavigationView bottomNavigationView; // 바텀 네이게이션 메뉴  -> 하단바
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.e("mypage","onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mypage);

        // 타임 세일 알람 화면으로 이동하는 버튼
        button_review_timesale = findViewById(R.id.button_review_timesale);
        button_review_timesale.setOnClickListener(new ImageView.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent register_intent = new Intent(mypage.this, timesale.class);
                startActivity(register_intent); //액티비티 이동

            }
        });

// 내가 쓴 리뷰로 이동하는 버튼
        button_myreview = findViewById(R.id.button_myreview);
        button_myreview.setOnClickListener(new ImageView.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent register_intent = new Intent(mypage.this, myreview.class);
                startActivity(register_intent); //액티비티 이동

            }
        });
//button_myreview


// 북마크한 리뷰
        button_bookmarked_review   = findViewById(R.id.button_bookmarked_review  );
        button_bookmarked_review  .setOnClickListener(new ImageView.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mypage.this, bookmarked_review.class);
                startActivity(intent); //액티비티 이동

            }
        });

//        // 팔로우
//        button_follow  = findViewById(R.id.button_follow );
//        button_follow .setOnClickListener(new ImageView.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent register_intent = new Intent(mypage.this, follow.class);
//                startActivity(register_intent); //액티비티 이동
//
//            }
//        });
//
//        // 팔로잉
//        button_following  = findViewById(R.id.button_following );
//        button_following .setOnClickListener(new ImageView.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent register_intent = new Intent(mypage.this, following.class);
//                startActivity(register_intent); //액티비티 이동
//
//            }
//        });

        // 관심 해시태그
        button_edit_hashtag  = findViewById(R.id.button_edit_hashtag );
        button_edit_hashtag .setOnClickListener(new ImageView.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent register_intent = new Intent(mypage.this, hashtag.class);
                startActivity(register_intent); //액티비티 이동

            }
        });

        // 프로필 수정
        button_edit_profile  = findViewById(R.id.button_edit_profile );
        button_edit_profile .setOnClickListener(new ImageView.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent register_intent = new Intent(mypage.this, edit_profile.class);
                startActivity(register_intent); //액티비티 이동

            }
        });

        // 로그아웃
        button_logout  = findViewById(R.id.button_logout );
        button_logout .setOnClickListener(new ImageView.OnClickListener() {
            @Override
            public void onClick(View view) {

                Toast.makeText(getApplication(), "로그아웃 되었습니다", Toast.LENGTH_SHORT).show();
                // 현재 로그인한 회원의 정보를 담는 쉐어드에서 데이터를 삭제해주기
                logined_user = getSharedPreferences("logined_user", Context.MODE_PRIVATE);
                user_editor = logined_user.edit();
                user_editor.clear();
                user_editor.commit();
                Log.e("mypage에서 로그아웃 하는 중","logined_user 확인 중 : " + logined_user);

                // 그리고 로그인 화면으로 이동
                Intent intent = new Intent(mypage.this, login.class);
                startActivity(intent); //액티비티 이동
                finish();
            }
        });


        bottomNavigationView = findViewById(R.id.bottomNavi);
        // 하단바 누를 때 색 바뀌게 하는 중
        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(4);
        menuItem.setChecked(true);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.action_home :
                        Intent home_intent = new Intent(mypage.this,feed.class);
                        startActivity(home_intent);//액티비티 띄우기
//                        startActivityForResult(intent,sub);//액티비티 띄우기
                        break;
                    case R.id.action_search :
                        Intent search_intent = new Intent(mypage.this,searching.class);
                        startActivity(search_intent);//액티비티 띄우기
                        break;
                    case R.id.action_write_review :
                        Intent write_intent = new Intent(mypage.this,write_review.class);
                        startActivity(write_intent);//액티비티 띄우기
                        break;
                    case R.id.action_notification :
                        Intent insight_intent = new Intent(mypage.this,notification.class);
                        startActivity(insight_intent);//액티비티 띄우기
                        break;
                    case R.id.action_mypage :
                        Intent mycloset_intent = new Intent(mypage.this, mypage.class);
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
        Log.e("mypage","onRestart");
        //액티비티가 중단되었다가 다시 시작
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.e("mypage","onStart");
        //액티비티가 화면에 나타나기 시작
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e("mypage","onResume");
        //액티비티가 화면에 나타나고 상호작용이 가능해짐
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.e("mypage","onPause");
        //다른 액티비티가 시작되려함, 이 액티비티는 중단되려하고 백그라운드로 들어갑니다.
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.e("mypage","onStop");
        //액티비티가 더 이상 화면에 나타나지 않음,중단된 상태
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e("mypage","onDestroy");
        //액티비티가 종료되려고 합니다.
    }
}
