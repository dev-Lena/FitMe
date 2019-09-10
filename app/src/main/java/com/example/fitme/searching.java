package com.example.fitme;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class searching extends AppCompatActivity implements View.OnClickListener {
    EditText editText_searching;
    ImageButton imageButton_searching;
    FloatingActionButton floatingActionButton_searching_map, floatingActionButton_edit_hashtag ; // 지도 찾을 수 있는 플로팅 버튼
    BottomNavigationView bottomNavigationView; // 바텀 네이게이션 메뉴  -> 하단바


    //
//    txt_main에 입력한 값을 불러와 intent를 할때 putExtra를 이용해 값을 넘겨줍니다.
//
//    txt_main의 값은 txt_main.getText().toString()을 이용하면 됩니다.


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.e("searching","onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searching);


        editText_searching = (EditText) findViewById(R.id.editText_searching);   // 데이터 입력하는 곳
        imageButton_searching = (ImageButton) findViewById(R.id.imageButton_searching);  // 검색하고 누르는 버튼






//데이터 보내기
// 검색 버튼 -> 검색 결과 화면 이동
        imageButton_searching = findViewById(R.id.imageButton_searching);
        imageButton_searching.setOnClickListener(new ImageView.OnClickListener() {
            @Override
            public void onClick(View view) {

                String HashTag = editText_searching.getText().toString();
                Intent intent = new Intent(searching.this, searching_result.class);
                intent.putExtra("HashTag", HashTag);
                Log.e("searching", "HashTag : " +HashTag);
                startActivity(intent);
//                Intent register_intent = new Intent(searching.this, searching_result.class);
//                startActivity(register_intent); //액티비티 이동

            }
        });



// 맵 오픈-> 암시적 인텐트
        floatingActionButton_searching_map = findViewById(R.id.floatingActionButton_searching_map);
        floatingActionButton_searching_map.setOnClickListener(new ImageView.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri uri = Uri.parse("geo:38.899533,-77.036476");
                Intent intent = new Intent(Intent.ACTION_VIEW,uri);
                startActivity(intent);

                // 길찾기
//                Uri uri2 = Uri.parse("http://maps.google.com/maps?f=d&saddr=출발지주소&daddr=도착지주소&hl=ko");
////                Intent it = new Intent(Intent.ACTION_VIEW,URI);
////                startActivity(it);

            }
        });

        // 관심 해시태그
        floatingActionButton_edit_hashtag  = findViewById(R.id.floatingActionButton_edit_hashtag );
        floatingActionButton_edit_hashtag .setOnClickListener(new ImageView.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent register_intent = new Intent(searching.this, hashtag.class);
                startActivity(register_intent); //액티비티 이동

            }
        });



//하단바
        bottomNavigationView = findViewById (R.id.bottomNavi);
        // 하단바 누를 때 색 바뀌게 하는 중
        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem  = menu.getItem(1);
        menuItem.setChecked(true);
        //
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                switch (menuItem.getItemId()) {
                    case R.id.action_home :
                        Intent home_intent = new Intent(searching.this,feed.class);
                        home_intent.addFlags (Intent.FLAG_ACTIVITY_NO_ANIMATION);
                        startActivity(home_intent);//액티비티 띄우기
//                        startActivityForResult(intent,sub);//액티비티 띄우기
                        break;
                    case R.id.action_search :
                        Intent search_intent = new Intent(searching.this,searching.class);
                        search_intent.addFlags (Intent.FLAG_ACTIVITY_NO_ANIMATION);
                        startActivity(search_intent);//액티비티 띄우기
                        break;
                    case R.id.action_insight :
                        Intent write_intent = new Intent(searching.this,insight.class);
                        write_intent.addFlags (Intent.FLAG_ACTIVITY_NO_ANIMATION);
                        startActivity(write_intent);//액티비티 띄우기
                        break;
                    case R.id.action_notification :
                        Intent insight_intent = new Intent(searching.this,notification.class);
                        insight_intent.addFlags (Intent.FLAG_ACTIVITY_NO_ANIMATION);
                        startActivity(insight_intent);//액티비티 띄우기
                        break;
                    case R.id.action_mypage :
                        Intent mycloset_intent = new Intent(searching.this, mypage.class);
                        mycloset_intent.addFlags (Intent.FLAG_ACTIVITY_NO_ANIMATION);
                        startActivity(mycloset_intent);//액티비티 띄우기
                        break;
                }


                return false;

            }
        });

    }


    @Override
    public void onClick(View view) {
        // TODO Auto-generated method stub
        Intent intent = new Intent(this, searching_result.class);
        intent.putExtra("HashTag",editText_searching.getText().toString());
        startActivity(intent);
        finish();
    }
    @Override
    protected void onRestart() {
        super.onRestart();
        Log.e("searching","onRestart");
        //액티비티가 중단되었다가 다시 시작
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.e("searching","onStart");
        //액티비티가 화면에 나타나기 시작
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e("searching","onResume");
        //액티비티가 화면에 나타나고 상호작용이 가능해짐
    }




    @Override
    protected void onPause() {
        super.onPause();
        Log.e("searching","onPause");
        overridePendingTransition(0, 0);
        //다른 액티비티가 시작되려함, 이 액티비티는 중단되려하고 백그라운드로 들어갑니다.
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.e("searching","onStop");
        //액티비티가 더 이상 화면에 나타나지 않음,중단된 상태
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e("searching","onDestroy");
        //액티비티가 종료되려고 합니다.
    }




}


