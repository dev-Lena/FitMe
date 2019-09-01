package com.example.fitme;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class comment extends AppCompatActivity {

    BottomNavigationView bottomNavigationView; // 바텀 네이게이션 메뉴  -> 하단바

    /**
     * 리사이클러뷰
     **/
    ArrayList<comment_Data> commentArrayListList;
    private RecyclerView commentRecyclerview;
    private com.example.fitme.commentAdapter commentAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.e("review_written_by_me", "onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);


        loadData();
        buildRecyclerView();
//        setInsertButton();

        ImageButton imageButton_comment_save = findViewById(R.id.imageButton_comment_save);
        imageButton_comment_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveData();
            }
        });


//하단바
        bottomNavigationView = findViewById(R.id.bottomNavi);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                switch (menuItem.getItemId()) {
                    case R.id.action_home:
                        Intent home_intent = new Intent(comment.this, feed.class);
                        startActivity(home_intent);//액티비티 띄우기
//                        startActivityForResult(intent,sub);//액티비티 띄우기
                        break;
                    case R.id.action_search:
                        Intent search_intent = new Intent(comment.this, searching.class);
                        startActivity(search_intent);//액티비티 띄우기
                        break;
                    case R.id.action_write_review:
                        Intent write_intent = new Intent(comment.this, review_category.class);
                        startActivity(write_intent);//액티비티 띄우기
                        break;
                    case R.id.action_notification:
                        Intent insight_intent = new Intent(comment.this, notification.class);
                        startActivity(insight_intent);//액티비티 띄우기
                        break;
                    case R.id.action_mycloset:
                        Intent mycloset_intent = new Intent(comment.this, my_closet.class);
                        startActivity(mycloset_intent);//액티비티 띄우기
                        break;
                }


                return false;

            }
        });

    }// onCreate 닫는 중괄호

    private void saveData() {
        SharedPreferences sharedPreferences = getSharedPreferences("sharedPreferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(commentArrayListList);
        editor.putString("commentList", json);
        editor.apply();
    }

    private void loadData() {
        SharedPreferences sharedPreferences = getSharedPreferences("sharedPreferences", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("commentList", null);
        Type type = new TypeToken<ArrayList<comment>>() {
        }.getType();
        commentArrayListList = gson.fromJson(json, type);

        if (commentArrayListList == null) {
            commentArrayListList = new ArrayList<>();
        }
    }
//
    private void buildRecyclerView() {
        commentRecyclerview = findViewById(R.id.commentRecyclerview);
        commentRecyclerview.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        commentAdapter = new commentAdapter(commentArrayListList);

        commentRecyclerview.setLayoutManager(mLayoutManager);
        commentRecyclerview.setAdapter(commentAdapter);
    }

//    private void setInsertButton() {
//        Button buttonInsert = findViewById(R.id.button_insert);
//        buttonInsert.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                EditText line1 = findViewById(R.id.edittext_line_1);
//                EditText line2 = findViewById(R.id.edittext_line_2);
//                insertItem(line1.getText().toString(), line2.getText().toString());
//            }
//        });
//    }

//    private void insertItem(String line1, String line2) {
//        commentArrayListList.add(new ExampleItem(line1, line2));
//        mAdapter.notifyItemInserted(mExampleList.size());
//    }
//
//}

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.e("review_written_by_me","onRestart");
        //액티비티가 중단되었다가 다시 시작
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.e("review_written_by_me","onStart");
        //액티비티가 화면에 나타나기 시작
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e("review_written_by_me","onResume");
        //액티비티가 화면에 나타나고 상호작용이 가능해짐
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.e("review_written_by_me","onPause");
        //다른 액티비티가 시작되려함, 이 액티비티는 중단되려하고 백그라운드로 들어갑니다.
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.e("review_written_by_me","onStop");
        //액티비티가 더 이상 화면에 나타나지 않음,중단된 상태
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e("review_written_by_me","onDestroy");
        //액티비티가 종료되려고 합니다.
    }

}
