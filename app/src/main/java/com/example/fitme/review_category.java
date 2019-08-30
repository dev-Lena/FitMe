package com.example.fitme;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class review_category extends AppCompatActivity {


    Button button_top, button_bottom, button_dress;
    BottomNavigationView bottomNavigationView; // 바텀 네이게이션 메뉴  -> 하단바

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.e("review_category","onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_size_category);

// 상의

        button_top = findViewById(R.id.button_top);
        button_top.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent recommend_intent = new Intent(review_category.this, write_review.class);
                startActivity(recommend_intent); //액티비티 이동

            }
        });
// 하의
        button_bottom = findViewById(R.id.button_bottom);
        button_bottom.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent recommend_intent = new Intent(review_category.this, size_bottom.class);
                startActivity(recommend_intent); //액티비티 이동

            }
        });
// 원피스
        button_dress = findViewById(R.id.button_dress);
        button_dress.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent recommend_intent = new Intent(review_category.this, size_dress.class);
                startActivity(recommend_intent); //액티비티 이동

            }
        });


//하단바
        bottomNavigationView = findViewById (R.id.bottomNavi);
        // 하단바 누를 때 색 바뀌게 하는 중
        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem  = menu.getItem(2);
        menuItem.setChecked(true);
        //
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                switch (menuItem.getItemId()) {
                    case R.id.action_home :
                        Intent home_intent = new Intent(review_category.this,feed.class);
                        startActivity(home_intent);//액티비티 띄우기
//                        startActivityForResult(intent,sub);//액티비티 띄우기
                        break;
                    case R.id.action_search :
                        Intent search_intent = new Intent(review_category.this,searching.class);
                        startActivity(search_intent);//액티비티 띄우기
                        break;
                    case R.id.action_write_review :
                        Intent write_intent = new Intent(review_category.this,review_category.class);
                        startActivity(write_intent);//액티비티 띄우기
                        break;
                    case R.id.action_notification :
                        Intent insight_intent = new Intent(review_category.this,notification.class);
                        startActivity(insight_intent);//액티비티 띄우기
                        break;
                    case R.id.action_mycloset :
                        Intent mycloset_intent = new Intent(review_category.this,my_closet.class);
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
        Log.e("review_category","onRestart");
        //액티비티가 중단되었다가 다시 시작
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.e("review_category","onStart");
        //액티비티가 화면에 나타나기 시작
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e("review_category","onResume");
        //액티비티가 화면에 나타나고 상호작용이 가능해짐
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.e("review_category","onPause");
        //다른 액티비티가 시작되려함, 이 액티비티는 중단되려하고 백그라운드로 들어갑니다.
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.e("review_category","onStop");
        //액티비티가 더 이상 화면에 나타나지 않음,중단된 상태
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e("review_category","onDestroy");
        //액티비티가 종료되려고 합니다.
    }

}
