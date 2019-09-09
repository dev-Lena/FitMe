package com.example.fitme;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;


public class myreview extends AppCompatActivity {

    private ArrayList<List> userData = new ArrayList<List>();

    private SharedPreferences logined_user;
    private SharedPreferences.Editor user_editor;

//    private SharedPreferences myreviewShared;
//    private SharedPreferences.Editor myreviewShared_editor;

    BottomNavigationView bottomNavigationView; // 바텀 네이게이션 메뉴  -> 하단바
    /**
     * 리사이클러뷰에 필요한 기본 객체 선언
     **/
    ArrayList<feed_MainData> myreview_arrayList = new ArrayList<>();
    feed_Adapter feed_adapter;
    RecyclerView recyclerView;
    LinearLayoutManager linearLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // feed_ Adapter에서 만들어준 arrayList를 가지고 와서 여기서 객체 선언해줌.
        myreview_arrayList = new ArrayList<>();

        myreview_loadData();


// 여기까지 로딩


        Log.e("myreview", "onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myreview);


        /**여기서부터 리사이클러뷰 만들기**/

        recyclerView = (RecyclerView) findViewById(R.id.myreview_recyclerview);  // 화면 xml 파일에서 리사이클러뷰의 아이디와 매칭

        linearLayoutManager = new LinearLayoutManager(this);

        linearLayoutManager.setReverseLayout(true); // 최신순으로 리사이클러뷰 아이템 추가.
        recyclerView.setLayoutManager(linearLayoutManager);


        feed_adapter = new feed_Adapter(myreview_arrayList,this);
        recyclerView.setAdapter(feed_adapter);

        // 리사이클러뷰 아이템에 있는 우측 상단 다이얼로그 메뉴 누르는 클릭 리스너

        feed_adapter.setOnItemClickListener(new feed_Adapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, final int position) {
// TODO : 아이템 클릭 이벤트를 MainActivity에서 처리.
// 리사이클러뷰 수정
// 다이얼로그


// 리사이클러뷰 아이템 안에 버튼을 누르면 팝업 메뉴 뜨도록
                PopupMenu popup = new PopupMenu(getApplicationContext(), v);//v는 클릭된 뷰를 의미

                getMenuInflater().inflate(R.menu.myreview_menu, popup.getMenu());
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.action_edit:
                                // 리뷰 카드에 있는 메뉴 다이얼로그 (?) 중 수정하기를 눌렀을 때
                                Toast.makeText(getApplication(), "수정하기", Toast.LENGTH_SHORT).show();

                                Intent intent = new Intent(getApplicationContext(), write_review.class);
                                Log.e("Feed 클래스에서 리사이클러뷰 수정 작업중! ", "edit_review로 연결되는 인텐트를 가지고왔습니다.");

//
                                intent.putExtra("URL", myreview_arrayList.get(position).textView_shoppingmall_url);
                                intent.putExtra("DETAIL", myreview_arrayList.get(position).textView_detailed_review_card);
                                intent.putExtra("HASHTAG", myreview_arrayList.get(position).textView_hashtag);
                                intent.putExtra("WRITER", myreview_arrayList.get(position).textView_review_writer);
                                intent.putExtra("NUMBER", myreview_arrayList.get(position).textView_reviewcard_number);


                                intent.putExtra("POSITION", position);
                                // 위치도 받아와야 수정한 데이터를 받아왔을 때 어떤 position에 있는 아이템에 set 해줄 건지 알려줄 수 있음


                                Log.e("Feed 클래스에서 리사이클러뷰 수정 작업중! ", "URL : " + myreview_arrayList.get(position).textView_detailed_review_card);
                                Log.e("Feed 클래스에서 리사이클러뷰 수정 작업중.", "기존에 있던 데이터가 넘어가나 확인중. DETAIL : " + myreview_arrayList.get(position).textView_detailed_review_card);

                                startActivityForResult(intent, 2001);

                                Log.e("Feed 클래스에서 리사이클러뷰 수정 작업중.", "startActivityForResult를 실행. requestCode 2001");

                                //액티비티 이동, 여기서 2001은 식별자. 아무 숫자나 넣으주면 됨.


                                break;

                            case R.id.action_delete:


                                remove(position);

                                feed_adapter.notifyDataSetChanged();  // 새로고침
                                Toast.makeText(getApplication(), "삭제되었습니다", Toast.LENGTH_SHORT).show();

                                myreview_saveData();  // sharedPreference에 리뷰가 추가된 리사이클러뷰를 저장한다 // onCreate 밖에 메소드 만들었음.
                                Log.e("feed 클래스에서 (saveData)", "삭제 후   sharedpreference에 리사이클러뷰에 들어가는 arrayList 저장 :" + myreview_arrayList);


                                return true;

                            case R.id.action_share:
                                Toast.makeText(getApplication(), "공유하기", Toast.LENGTH_SHORT).show();
                                break;

                            default:
                                break;
                        }
                        return false;
                    }
                });

                popup.show();//Popup Menu 보이기

            }

            @Override
            public void onCommentClick(View v, int position) {
                Intent comment_intent = new Intent(myreview.this, comment.class);
                startActivity(comment_intent); //액티비티 이동

            }

            // 북마크한 리뷰 페이지에서 북마크 버튼을 누르면 -> 북마크 해제
            @Override
            public void onBookmarkClick(View v, int position) {

// 자기가 쓴 리뷰니까 북마크한 리뷰에 추가되지 않도록 비활성화 시키기 9월 5일에 하기

            }
        });

//하단바
        bottomNavigationView = findViewById(R.id.bottomNavi);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                switch (menuItem.getItemId()) {
                    case R.id.action_home:
                        Intent home_intent = new Intent(myreview.this, feed.class);
                        startActivity(home_intent);//메인 피드 화면으로 이동. 액티비티 띄우기

//                        startActivityForResult(intent,sub);//액티비티 띄우기
                        break;
                    case R.id.action_search:
                        Intent search_intent = new Intent(myreview.this, searching.class);
                        startActivity(search_intent);//검색 화면으로 이동. 액티비티 띄우기
                        break;
                    case R.id.action_write_review:
                        Intent write_intent = new Intent(myreview.this, write_review.class);
                        startActivity(write_intent);//리뷰 작성 화면으로 이동. 액티비티 띄우기
                        break;
                    case R.id.action_notification:
                        Intent insight_intent = new Intent(myreview.this, notification.class);
                        startActivity(insight_intent);//알림 화면으로 이동. 액티비티 띄우기
                        break;
                    case R.id.action_mypage:
                        Intent mycloset_intent = new Intent(myreview.this, mypage.class);
                        startActivity(mycloset_intent);//마이페이지 화면으로 이동. 액티비티 띄우기
                        break;
                }


                return false;

            }
        });


    }// onCreate 닫는 중괄호

    public void remove(int position) {
        // 피드 리사이클러뷰 안에 있는 리뷰를 삭제할 때 쓰는 remove 메소드

        try {
            myreview_arrayList.remove(position);
            feed_adapter.notifyItemRemoved(position);


            // sharedPreference 에서 삭제하는 코드를 넣어줘야 함.... 굳이? arrayList에서 없애주면 되는거 아닌가?

        } catch (IndexOutOfBoundsException e) {
            e.printStackTrace();
        }
    }

    private void myreview_saveData() {

//
        SharedPreferences myreviewShared = getSharedPreferences("myreviewShared", MODE_PRIVATE);
        SharedPreferences.Editor myreviewShared_editor = myreviewShared.edit();
        Gson gson = new Gson();
        Log.e("myreview 클래스", "Gson 객체 호출 : " + gson);

        String json = gson.toJson(myreview_arrayList);  // 여기서 arrayList는 피드에 들어가는 리사이클러뷰를 담은 arrayList 이름임.

        Log.e("myreview 클래스", "Gson 객체 호출 (toJson(myreview_arrayList) : " + json);


        // 로그인 하고 있는 사용자의 이메일을 키값으로 갖는 value에
        logined_user = getSharedPreferences("logined_user", Context.MODE_PRIVATE);   // 현재 로그인한 회원의 정보만 담겨있는 쉐어드를 불러와서
        String feed_email = logined_user.getString("user_email", "");

        myreviewShared_editor.putString(feed_email, json);   // fromJson할 때도 "feed_recyclerview" 맞춰줌. // 로그인한 유저의 이메일을 키값으로 json 데이터를 넣어줌.
        Log.e("myreview 클래스", "Gson 객체 호출 (키 , 들어간 값) : " + feed_email + "," + json);

        myreviewShared_editor.apply();
        Log.e("myreview 클래스", "editor. apply 성공 ");


    }

    // sharedPreference에 저장한 ArrayList 를 가져옴 (리사이클러뷰)
    private void myreview_loadData() {

        SharedPreferences myreviewShared = getSharedPreferences("myreviewShared", MODE_PRIVATE);
        Gson gson = new Gson();

        // 로그인 하고 있는 사용자의 이메일을 키값으로 갖는 value에
        logined_user = getSharedPreferences("logined_user", Context.MODE_PRIVATE);   // 현재 로그인한 회원의 정보만 담겨있는 쉐어드를 불러와서
        String feed_email = logined_user.getString("user_email", "");
        Log.e("feed 클래스 ", "로그인한 유저의 이메일 호출 : " + feed_email);

        String json = myreviewShared.getString(feed_email, null);
        Type type = new TypeToken<ArrayList<feed_MainData>>() {
        }.getType();

        Log.e("feed 클래스 (myreview_loadData)", "typeToken객체 생성 :" + type);

        myreview_arrayList = gson.fromJson(json, type);
        Log.e("feed 클래스 (myreview_loadData)", "fromJson : arryaList(myreview_arrayList)는 " + myreview_arrayList);


        if (myreview_arrayList == null) {
            myreview_arrayList = new ArrayList<>();
        }
    } // myreview_loadData 메소드 닫는 중괄호





    @Override
    protected void onRestart() {
        super.onRestart();
        Log.e("myreview","onRestart");
        //액티비티가 중단되었다가 다시 시작
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.e("myreview","onStart");
        //액티비티가 화면에 나타나기 시작
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e("myreview","onResume");
        //액티비티가 화면에 나타나고 상호작용이 가능해짐
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.e("myreview","onPause");
        //다른 액티비티가 시작되려함, 이 액티비티는 중단되려하고 백그라운드로 들어갑니다.
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.e("myreview","onStop");
        //액티비티가 더 이상 화면에 나타나지 않음,중단된 상태
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e("myreview","onDestroy");
        //액티비티가 종료되려고 합니다.
    }

}