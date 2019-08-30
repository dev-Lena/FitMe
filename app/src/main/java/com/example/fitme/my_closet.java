package com.example.fitme;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class my_closet extends AppCompatActivity {

    Button button_review_written_by_me, button_bookmarked_review  , button_follow , button_following , button_edit_hashtag , button_edit_profile ;
    BottomNavigationView bottomNavigationView; // 바텀 네이게이션 메뉴  -> 하단바
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.e("my_closet","onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_closet);

// 내가 쓴 리뷰 -> 리뷰를 등록하는 버튼
        button_review_written_by_me = findViewById(R.id.button_review_written_by_me);
        button_review_written_by_me.setOnClickListener(new ImageView.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent register_intent = new Intent(my_closet.this, review_written_by_me.class);
                startActivity(register_intent); //액티비티 이동

            }
        });



// 북마크한 리뷰
        button_bookmarked_review   = findViewById(R.id.button_bookmarked_review  );
        button_bookmarked_review  .setOnClickListener(new ImageView.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(my_closet.this, bookmarked_review.class);
                startActivity(intent); //액티비티 이동

            }
        });

        // 팔로우
        button_follow  = findViewById(R.id.button_follow );
        button_follow .setOnClickListener(new ImageView.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent register_intent = new Intent(my_closet.this, follow.class);
                startActivity(register_intent); //액티비티 이동

            }
        });

        // 팔로잉
        button_following  = findViewById(R.id.button_following );
        button_following .setOnClickListener(new ImageView.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent register_intent = new Intent(my_closet.this, following.class);
                startActivity(register_intent); //액티비티 이동

            }
        });

        // 관심 해시태그
        button_edit_hashtag  = findViewById(R.id.button_edit_hashtag );
        button_edit_hashtag .setOnClickListener(new ImageView.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent register_intent = new Intent(my_closet.this, hashtag.class);
                startActivity(register_intent); //액티비티 이동

            }
        });

        // 프로필 수정
        button_edit_profile  = findViewById(R.id.button_edit_profile );
        button_edit_profile .setOnClickListener(new ImageView.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent register_intent = new Intent(my_closet.this, edit_profile.class);
                startActivity(register_intent); //액티비티 이동

            }
        });
        bottomNavigationView = findViewById (R.id.bottomNavi);
        // 하단바 누를 때 색 바뀌게 하는 중
        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem  = menu.getItem(4);
        menuItem.setChecked(true);
        //
        BottomNavigationView.OnNavigationItemSelectedListener onNavigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.action_home :
                        Intent home_intent = new Intent(my_closet.this,feed.class);
                        startActivity(home_intent);//액티비티 띄우기
//                        startActivityForResult(intent,sub);//액티비티 띄우기
                        break;
                    case R.id.action_search :
                        Intent search_intent = new Intent(my_closet.this,searching.class);
                        startActivity(search_intent);//액티비티 띄우기
                        break;
                    case R.id.action_write_review :
                        Intent write_intent = new Intent(my_closet.this,review_category.class);
                        startActivity(write_intent);//액티비티 띄우기
                        break;
                    case R.id.action_notification :
                        Intent insight_intent = new Intent(my_closet.this,notification.class);
                        startActivity(insight_intent);//액티비티 띄우기
                        break;
                    case R.id.action_mycloset :
                        Intent mycloset_intent = new Intent(my_closet.this,my_closet.class);
                        startActivity(mycloset_intent);//액티비티 띄우기
                        break;
                }

                return false;
            }
        };

    }


    @Override
    protected void onRestart() {
        super.onRestart();
        Log.e("my_closet","onRestart");
        //액티비티가 중단되었다가 다시 시작
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.e("my_closet","onStart");
        //액티비티가 화면에 나타나기 시작
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e("my_closet","onResume");
        //액티비티가 화면에 나타나고 상호작용이 가능해짐
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.e("my_closet","onPause");
        //다른 액티비티가 시작되려함, 이 액티비티는 중단되려하고 백그라운드로 들어갑니다.
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.e("my_closet","onStop");
        //액티비티가 더 이상 화면에 나타나지 않음,중단된 상태
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e("my_closet","onDestroy");
        //액티비티가 종료되려고 합니다.
    }
}
