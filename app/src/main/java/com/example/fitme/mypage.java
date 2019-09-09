package com.example.fitme;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
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
    ViewGroup linearLayout6, linearLayout7,linearLayout5 , linearLayout3 ; // 내 리뷰 :linearLayout6, 북마크 : linearLayout7, 팔로우 : linearLayout5, 팔로잉 linearLayout3
    Button  button_logout, button_myreview, button_edit_hashtag , button_edit_profile ;
    BottomNavigationView bottomNavigationView; // 바텀 네이게이션 메뉴  -> 하단바
    TextView textView_mypage_email, textView_nickname;
    ImageButton imageButton_mypage_menu;
    ImageView imageView_mypage_profileimage;
    Uri uri;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.e("mypage","onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mypage2);


        //마이페이지의 로그인한 회원의 정보 넣기 : 이메일, 닉네임, 프로필 사진


        // 이메일
        logined_user = getSharedPreferences("logined_user", Context.MODE_PRIVATE);   // 현재 로그인한 회원의 정보만 담겨있는 쉐어드를 불러와서
        String mypage_email = logined_user.getString("user_email", "");
        Log.e("[마이페이지] 로그인 쉐어드에서", " 현재 로그인한 유저의 이메일 넣기 : " + mypage_email);
        textView_mypage_email = findViewById(R.id.textView_mypage_email);
        textView_mypage_email.setText(mypage_email);

        // 닉네임
        String mypage_nickname = logined_user.getString("user_nickname", "");
        Log.e("[마이페이지] 로그인 쉐어드에서", " 현재 로그인한 유저의 닉네임 넣기 : " + mypage_nickname);
        textView_nickname = findViewById(R.id.textView_nickname);
        textView_nickname.setText(mypage_nickname);

        // 프로필 사진

//        String uri = logined_user.getString("user_profileimage", "");
//        imageView_mypage_profileimage = findViewById(R.id.imageView_mypage_profileimage);
//        imageView_mypage_profileimage.setImageURI(Uri.parse( uri));
        imageView_mypage_profileimage = (ImageView)findViewById(R.id.imageView_mypage_profileimage);
        String ImageUri = logined_user.getString("user_profileimage", null);
        imageView_mypage_profileimage.setImageURI(Uri.parse(ImageUri));

        Log.e("[마이페이지] 로그인 쉐어드에서", " uri : " + ImageUri);
        Log.e("[마이페이지] 로그인 쉐어드에서", "imageView_mypage_profileimage : " + imageView_mypage_profileimage);


//        imageView_mypage_profileimage.setImageURI(uri);


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

// 내가 쓴 리뷰 레이아웃을 클릭하면 -> 내가 쓴 리뷰 화면으로 전환
        linearLayout6 = (ViewGroup) findViewById(R.id.linearLayout6);
        linearLayout6 .setOnClickListener(new ImageView.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(mypage.this, myreview.class);
                startActivity(intent); //액티비티 이동
            }
        });

// 북마크한 리뷰 레이아웃을 클릭하면 -> 북마크한 리뷰 화면으로 전환
        linearLayout7 = (ViewGroup) findViewById(R.id.linearLayout7);
        linearLayout7 .setOnClickListener(new ImageView.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(mypage.this, bookmarked_review.class);
                startActivity(intent); //액티비티 이동
            }
        });

// 팔로우 리뷰 레이아웃을 클릭하면 -> 팔로우 화면으로 전환

        linearLayout5 = (ViewGroup) findViewById(R.id.linearLayout5);
        linearLayout5 .setOnClickListener(new ImageView.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(mypage.this, follow.class);
                startActivity(intent); //액티비티 이동
            }
        });

// 팔로우 리뷰 레이아웃을 클릭하면 -> 팔로우 화면으로 전환

        linearLayout3 = (ViewGroup) findViewById(R.id.linearLayout3);
        linearLayout3 .setOnClickListener(new ImageView.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(mypage.this, following.class);
                startActivity(intent); //액티비티 이동
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


        imageButton_mypage_menu= findViewById(R.id.imageButton_mypage_menu);
        imageButton_mypage_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popup = new PopupMenu(getApplicationContext(), view);//v는 클릭된 뷰를 의미

                getMenuInflater().inflate(R.menu.mypage_menu, popup.getMenu());
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.action_logout:
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
                                break;
                            default:
                                break;
                        }
                        return false;
            }
        });
                popup.show();//Popup Menu 보이기

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
