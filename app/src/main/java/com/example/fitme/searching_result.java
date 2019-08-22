package com.example.fitme;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class searching_result extends AppCompatActivity {

    ImageButton imageButton_searching_result_back;
    String HashTag;
    TextView textView_searching_result;
    BottomNavigationView bottomNavigationView; // 바텀 네이게이션 메뉴  -> 하단바

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.e("searching_result","onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searching_result);

// 검색 화면에서 검색어 데이터 받기
        textView_searching_result = (TextView)findViewById(R.id.textView_searching_result);
        Intent intent = getIntent();
//        Log.d("HashTag_Check",intent.getExtras().getString("HashTag_Check_Check"));

        String HashTag = intent.getStringExtra("HashTag");

        Log.e("searching_result", "HashTag : " +HashTag);
        textView_searching_result.setText(HashTag);

// 뒤로가기 버튼을 누르면 검색 화면으로 이동
        imageButton_searching_result_back = findViewById(R.id.imageButton_searching_result_back);
        imageButton_searching_result_back.setOnClickListener(new ImageView.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(searching_result.this, searching.class);
                startActivity(intent); //액티비티 이동

            }
        });

//하단바
        bottomNavigationView = findViewById (R.id.bottomNavi);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                switch (menuItem.getItemId()) {
                    case R.id.action_home :
                        Intent home_intent = new Intent(searching_result.this,feed.class);
                        startActivity(home_intent);//액티비티 띄우기
//                        startActivityForResult(intent,sub);//액티비티 띄우기
                        break;
                    case R.id.action_search :
                        Intent search_intent = new Intent(searching_result.this,searching.class);
                        startActivity(search_intent);//액티비티 띄우기
                        break;
                    case R.id.action_write_review :
                        Intent write_intent = new Intent(searching_result.this,review_category.class);
                        startActivity(write_intent);//액티비티 띄우기
                        break;
                    case R.id.action_insight :
                        Intent insight_intent = new Intent(searching_result.this,insight.class);
                        startActivity(insight_intent);//액티비티 띄우기
                        break;
                    case R.id.action_mycloset :
                        Intent mycloset_intent = new Intent(searching_result.this,my_closet.class);
                        startActivity(mycloset_intent);//액티비티 띄우기
                        break;
                }


                return false;

            }
        });

    } // onCreate 닫는 중괄호

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.e("searching_result","onRestart");
        //액티비티가 중단되었다가 다시 시작
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.e("searching_result","onStart");
        //액티비티가 화면에 나타나기 시작
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e("searching_result","onResume");
        //액티비티가 화면에 나타나고 상호작용이 가능해짐
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.e("searching_result","onPause");
        //다른 액티비티가 시작되려함, 이 액티비티는 중단되려하고 백그라운드로 들어갑니다.
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.e("searching_result","onStop");
        //액티비티가 더 이상 화면에 나타나지 않음,중단된 상태
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e("searching_result","onDestroy");
        //액티비티가 종료되려고 합니다.
    }
}
