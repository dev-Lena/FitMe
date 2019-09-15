package com.example.fitme;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class bookmarked_review extends AppCompatActivity {

    private ArrayList<List> userData = new ArrayList<List>();

    private SharedPreferences logined_user;
    private SharedPreferences.Editor user_editor;
    BottomNavigationView bottomNavigationView; // 바텀 네이게이션 메뉴  -> 하단바
    ImageButton imageButton_back;
    TextView textView_howmany_bookmarked_reviews;
    /**
     * 리사이클러뷰에 필요한 기본 객체 선언
     **/
    ArrayList<feed_MainData> bookmarked_arrayList;
    feed_Adapter feed_adapter;
    RecyclerView recyclerView;
    LinearLayoutManager linearLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // feed_ Adapter에서 만들어준 arrayList를 가지고 와서 여기서 객체 선언해줌.
        bookmarked_arrayList = new ArrayList<>();

        bookmarked_loadData();


// 여기까지 로딩


        Log.e("bookmarked_review","onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookmarked_review);


        /**여기서부터 리사이클러뷰 만들기**/

        recyclerView = (RecyclerView) findViewById(R.id.bookmarked_recyclerView);  // 화면 xml 파일에서 리사이클러뷰의 아이디와 매칭

        linearLayoutManager = new LinearLayoutManager(this);

        linearLayoutManager.setReverseLayout(true); // 최신순으로 리사이클러뷰 아이템 추가.
        recyclerView.setLayoutManager(linearLayoutManager);

       // 리사이클러뷰 맨 위부터 보여주기
        recyclerView.post(new Runnable() {

            @Override

            public void run() {

                recyclerView.scrollToPosition(recyclerView.getAdapter().getItemCount() - 1);

            }

        });
        feed_adapter = new feed_Adapter(bookmarked_arrayList,this);
        recyclerView.setAdapter(feed_adapter);
        // 리사이클러뷰 아이템에 있는 우측 상단 다이얼로그 메뉴 누르는 클릭 리스너


        // 지금까지 북마크한 리뷰는 xx 개입니다. 설정
        textView_howmany_bookmarked_reviews = findViewById(R.id.textView_howmany_bookmarked_reviews);
        int bookmark_count = bookmarked_arrayList.size();
        Log.d("bookmark_count"," : " + bookmark_count );
        textView_howmany_bookmarked_reviews.setText(String.valueOf(bookmark_count));
        Log.d("textView_howmany_bookmarked_reviews"," : " + textView_howmany_bookmarked_reviews );



        feed_adapter.setOnItemClickListener(new feed_Adapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, final int position) {
// TODO : 아이템 클릭 이벤트를 MainActivity에서 처리.
// 리사이클러뷰 수정
// 다이얼로그

// 리사이클러뷰 아이템 안에 버튼을 누르면 팝업 메뉴 뜨도록
                PopupMenu popup = new PopupMenu(getApplicationContext(), v);//v는 클릭된 뷰를 의미

                getMenuInflater().inflate(R.menu.bookmarked_menu, popup.getMenu());
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {

                            case R.id.action_share:
                                Toast.makeText(getApplication(), "공유하기", Toast.LENGTH_SHORT).show();
                                break;
                            case R.id.action_report:

                                remove(position);

                                feed_adapter.notifyDataSetChanged();  // 새로고침
                                bookmark_saveData();  // sharedPreference에 리뷰가 추가된 리사이클러뷰를 저장한다 // onCreate 밖에 메소드 만들었음.
                                Log.e("feed 클래스에서 (saveData)","삭제 후   sharedpreference에 리사이클러뷰에 들어가는 arrayList 저장 :"  + bookmarked_arrayList );

                                Toast.makeText(getApplication(), "신고되었습니다", Toast.LENGTH_SHORT).show();

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
                Intent comment_intent = new Intent(bookmarked_review.this, comment.class);
                startActivity(comment_intent); //액티비티 이동

            }

            // 북마크한 리뷰 페이지에서 북마크 버튼을 누르면 -> 북마크 해제
            @Override
            public void onBookmarkClick(View v, int position) {


                remove(position);
                feed_adapter.notifyDataSetChanged();  // 새로고침

                bookmark_saveData();  // sharedPreference에 리뷰가 추가된 리사이클러뷰를 저장한다 // onCreate 밖에 메소드 만들었음.
                Log.e("bookmarked_review 클래스에서 ","onBookmarkClickㄹ 메소드 :"  + feed_adapter  );

                Toast.makeText(getApplication(), "북마크가 해제되었습니다", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onReviewClick(View v, int position) {

            }

            @Override
            public void onLikeClick(View v, int position) {

            }
        });

        // 뒤로 가기 버튼 눌렀을 때 피드(메인 화면)로 이동

        imageButton_back = findViewById(R.id.imageButton_back);
        imageButton_back.setOnClickListener(new ImageView.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent register_intent = new Intent(bookmarked_review.this, feed.class);
                startActivity(register_intent); //액티비티 이동
                finish(); // 액티비티 finish 시킴

            }
        });



    }// onCreate 닫는 중괄호


    public void remove(int position){
        // 피드 리사이클러뷰 안에 있는 리뷰를 삭제할 때 쓰는 remove 메소드


        try{
            bookmarked_arrayList.remove(position);
            feed_adapter.notifyItemRemoved(position);


            // sharedPreference 에서 삭제하는 코드를 넣어줘야 함.... 굳이? arrayList에서 없애주면 되는거 아닌가?

        }catch (IndexOutOfBoundsException e){
            e.printStackTrace();
        }
    }

    private void bookmark_saveData() {


        SharedPreferences bookmarkShared = getSharedPreferences("bookmarkShared", MODE_PRIVATE);
        SharedPreferences.Editor bookmarkShared_editor = bookmarkShared.edit();
        Gson gson = new Gson();
        Log.e("feed 클래스", "Gson 객체 호출 : " + gson);

        String json = gson.toJson(bookmarked_arrayList);  // 여기서 arrayList는 피드에 들어가는 리사이클러뷰를 담은 arrayList 이름임.
        // 근데 여기서 이 arrayList에 넣어주면 사용자 이메일에 넣어줬을 때 .. 사용자 마다 다른 arrayList를 갖게 되는건가?
        // 이 메소드 안에서 arrayList를 객체 선언해주면 매번 초기화 되서 사용자마다 다른 arrayList를 갖을 수 있는건가?

        Log.e("feed 클래스", "Gson 객체 호출 (toJson(bookmarked_arrayList) : " + json);


        // 로그인 하고 있는 사용자의 이메일을 키값으로 갖는 value에
        logined_user = getSharedPreferences("logined_user", Context.MODE_PRIVATE);   // 현재 로그인한 회원의 정보만 담겨있는 쉐어드를 불러와서
        String feed_email = logined_user.getString("user_email", "");

        // 여기 원래 feed_email이 키값이었음 -> 근데 그렇게 하면 그 사용자의 모든 정보가 bookmarked_arrayList로 덮어씌워짐
        //sharedPreference 쉐어드-> 로그인 하고 있는 사용자의 이메일을 키값으로 갖는 value에 bookmarked_arrayList를 String으로 변환한 값을 넣어줌.
        bookmarkShared_editor.putString(feed_email, json);   // fromJson할 때도 "feed_recyclerview" 맞춰줌. // 로그인한 유저의 이메일을 키값으로 json 데이터를 넣어줌.
        Log.e("feed 클래스", "Gson 객체 호출 (키 , 들어간 값) : " + feed_email +","+ json);

        bookmarkShared_editor.apply();
        Log.e("feed 클래스", "editor. apply 성공 ");


    }
    // sharedPreference에 저장한 ArrayList 를 가져옴 (리사이클러뷰)
    private void bookmarked_loadData() {

        SharedPreferences bookmarkShared = getSharedPreferences("bookmarkShared", MODE_PRIVATE);
        Gson gson = new Gson();

        // 로그인 하고 있는 사용자의 이메일을 키값으로 갖는 value에
        logined_user = getSharedPreferences("logined_user", Context.MODE_PRIVATE);   // 현재 로그인한 회원의 정보만 담겨있는 쉐어드를 불러와서
        String feed_email = logined_user.getString("user_email", "");
        Log.e("feed 클래스 (bookmarked_loadData)", "로그인한 유저의 이메일 호출 : " + feed_email);

        String json = bookmarkShared.getString(feed_email, null);
        Type type = new TypeToken<ArrayList<feed_MainData>>() {
        }.getType();
        Log.e("feed 클래스 (bookmarked_loadData)", "typeToken객체 생성 :" + type);
        bookmarked_arrayList = gson.fromJson(json, type);
        Log.e("feed 클래스 (bookmarked_loadData)", "fromJson : arryaList(bookmarked_arrayList)는 " + bookmarked_arrayList);


        if (bookmarked_arrayList == null) {
            bookmarked_arrayList = new ArrayList<>();
        }
    }

//
//    @Override
//    // startActivityForResult에서 Result는 어떤건지
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        // setResult를 통해 받아온 요청번호, 상태, 데이터
//        Log.e("RESULT", requestCode + "");
//        Log.e("RESULT", resultCode + "");
//        Log.e("RESULT", data + "");
//
//        if (requestCode == 1001 && resultCode == RESULT_OK) {
//
//            Toast.makeText(bookmarked_review.this, "리뷰 작성을 완료했습니다!", Toast.LENGTH_SHORT).show();
//
//
////            feed_adapter = new feed_Adapter();
//            // 사용자가 입력한 내용을 가져와서
//            String textView_shoppingmall_url = data.getStringExtra("쇼핑몰URL");
//            Log.e("쇼핑몰URL", textView_shoppingmall_url + "쇼핑몰URL 가져왔습니다!!!!!!!!!");
//            String textView_detailed_review_card = data.getStringExtra("상세리뷰");
//            Log.e("상세리뷰", textView_detailed_review_card + "상세리뷰 가져왔습니다!!!!!!!");
//            String textView_hashtag = data.getStringExtra("해시태그");
//            Log.e("해시태그", textView_hashtag + "해시태그를 가져왔습니다!!!!!!!!!");
//            float float_ratingBar = data.getFloatExtra("만족도", 0);
//            Log.e("만족도", float_ratingBar + "만족도를 가져왔습니다!!!!!!!");
//
//            String textView_review_writer = data.getStringExtra("작성자");
//            Log.e("작성자", textView_hashtag + "작성자를 가져왔습니다!!!!!!!!!");
//
//            String textView_reviewcard_number = data.getStringExtra("리뷰고유번호");
//            Log.e("리뷰고유번호", textView_hashtag + "리뷰고유번호를 가져왔습니다!!!!!!!!!");
//
//            String textView_nickname= data.getStringExtra("닉네임");
//            Log.e("닉네임", textView_hashtag + "닉네임을 가져왔습니다!!!!!!!!!");
//
//            String textView_mysize = data.getStringExtra("평소사이즈");
//            Log.e("평소사이즈", textView_hashtag + "평소사이즈를 가져왔습니다!!!!!!!!!");
//
//            String review_date = data.getStringExtra("리뷰시간");
//            Log.e("리뷰시간", textView_hashtag + "리뷰시간을 가져왔습니다!!!!!!!!!");
//
//
//
//            feed_MainData feed_MainData = new feed_MainData(textView_shoppingmall_url, textView_detailed_review_card, float_ratingBar, textView_hashtag, review_date,
//                    textView_review_writer, textView_reviewcard_number, textView_nickname, textView_mysize );
//            Log.e("add", textView_detailed_review_card + "feed_MainData 객체 생성");
//
////리사이클러뷰의 arrayList에 아이템 추가
//            bookmarked_arrayList.add(feed_MainData);
//            Log.e("bookmarked_review", textView_detailed_review_card + "리사이클러뷰의 arrayList에 아이템 추가 (리뷰 쓰고 다시 피드 화면으로 돌아옴)");
//
//            feed_adapter.notifyDataSetChanged();  // 새로고침
//            Log.e("bookmarked_review", textView_detailed_review_card + "새로고침");
//
//            // sharedPreferences 에 추가
//            bookmark_saveData();  // sharedPreference에 리뷰가 추가된 리사이클러뷰를 저장한다 // onCreate 밖에 메소드 만들었음.
//            Log.e("bookmarked_review 클래스에서 (saveData)","sharedpreference에 리사이클러뷰에 들어가는 arrayList 저장 :"  + bookmarked_arrayList );
//
//
//            //
////            loadData();  // sharedpreference에 저장한 arrayList (리사이클러뷰)를 가지고 옴. onCreate 밖에 메소드 만들어줌
////            // 피드에 들어가는 리사이클러뷰를 저장한 키값은 "feed_recyclerview"
////            Log.e("feed 클래스에서(loadData)","sharedPreference에 리사이클러뷰에 들어가는 arrayList 불러오기 :"  + arrayList );
//
//        }
//
//        if (requestCode == 2001 && resultCode == RESULT_OK) {
//            Toast.makeText(bookmarked_review.this, "리뷰 수정을 완료했습니다!", Toast.LENGTH_SHORT).show();
//
//
//            // 리뷰 수정에서 보낸 수정한 데이터 가져오기 / 받아오기
//            // 사용자가 수정한 내용을 가져와서
//            String textView_shoppingmall_url = data.getStringExtra("쇼핑몰URL");
//            Log.e("쇼핑몰URL", textView_shoppingmall_url + "수정한 쇼핑몰URL 가져왔습니다");
//
//            String textView_detailed_review_card = data.getStringExtra("상세리뷰");
//            Log.e("상세리뷰", textView_detailed_review_card + "수정한 상세리뷰 가져왔습니다");
//
//            String textView_hashtag = data.getStringExtra("해시태그");
//            Log.e("쇼핑몰URL", textView_hashtag + "수정한 해시태그 가져왔습니다");
//
//            float float_ratingBar = data.getFloatExtra("만족도", 0);
//            Log.e("만족도", float_ratingBar + "만족도를 가져왔습니다!!!!!!!");
//
//            String textView_review_writer = data.getStringExtra("작성자");
//            Log.e("작성자", textView_hashtag + "작성자를 가져왔습니다!!!!!!!!!");
//
//            String textView_reviewcard_number = data.getStringExtra("리뷰고유번호");
//            Log.e("리뷰고유번호", textView_hashtag + "리뷰고유번호를 가져왔습니다!!!!!!!!!");
//
//            String textView_nickname= data.getStringExtra("닉네임");
//            Log.e("닉네임", textView_hashtag + "닉네임을 가져왔습니다!!!!!!!!!");
//
//            String textView_mysize = data.getStringExtra("평소사이즈");
//            Log.e("평소사이즈", textView_hashtag + "평소사이즈를 가져왔습니다!!!!!!!!!");
//
//            String review_date = data.getStringExtra("리뷰시간");
//            Log.e("리뷰시간", textView_hashtag + "리뷰시간을 가져왔습니다!!!!!!!!!");
//
//
//            int position = data.getIntExtra("POSITION", 0000);
//            Log.e("위치값", position + " 위치값을 가지고 왔습니다");
//
//            //
//            // ArrayList에 추가하고
//            feed_MainData feed_MainData = new feed_MainData(textView_shoppingmall_url, textView_detailed_review_card, float_ratingBar, textView_hashtag, review_date,
//                    textView_review_writer, textView_reviewcard_number, textView_nickname, textView_mysize );
//            Log.e("bookmarked_review", "ArryaList 중 이곳에 데이터를 넣을껍니다" + textView_shoppingmall_url + "," + textView_detailed_review_card);
//
//
//            // 그 위치를 받아와서 그곳에 set 해주기. 리뷰 수정 버튼을 누를 때 부터 같이 위치값을 startActivityForResult로 같이 넘겼다가 돌려받음.
//            bookmarked_arrayList.set(position, feed_MainData);
////
////            arrayList.add(feed_MainData);
////            Log.e("edit", textView_detailed_review_card + "리사이클러뷰의 arrayList에 아이템 추가");
//
////            feed_adapter.notifyItemRemoved(getAdapterPosition());  //아이템이 삭제한 것을 notify
//            feed_adapter.notifyDataSetChanged();  // 새로고침
//            Log.e("bookmarked_review", "수정한거 새로고침");
//
//            bookmark_saveData();  // sharedPreference에 리뷰가 추가된 리사이클러뷰를 저장한다 // onCreate 밖에 메소드 만들었음.
//            Log.e("bookmarked_review 클래스에서 (saveData)","sharedpreference에 리사이클러뷰에 들어가는 arrayList 저장 :"  + bookmarked_arrayList );
//
////
////            loadData();  // sharedpreference에 저장한 arrayList (리사이클러뷰)를 가지고 옴. onCreate 밖에 메소드 만들어줌
////            // 피드에 들어가는 리사이클러뷰를 저장한 키값은 "feed_recyclerview"
////            Log.e("feed 클래스에서(loadData)","sharedPreference에 리사이클러뷰에 들어가는 arrayList 불러오기 :"  + arrayList );
//        }
//
//    }//onActivityResult 메소드 닫는 중괄호




    @Override
    protected void onRestart() {
        super.onRestart();
        Log.e("bookmarked_review","onRestart");
        //액티비티가 중단되었다가 다시 시작
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.e("bookmarked_review","onStart");
        //액티비티가 화면에 나타나기 시작
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e("bookmarked_review","onResume");
        //액티비티가 화면에 나타나고 상호작용이 가능해짐
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.e("bookmarked_review","onPause");
        overridePendingTransition(0, 0);
        //다른 액티비티가 시작되려함, 이 액티비티는 중단되려하고 백그라운드로 들어갑니다.
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.e("bookmarked_review","onStop");
        //액티비티가 더 이상 화면에 나타나지 않음,중단된 상태
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e("bookmarked_review","onDestroy");
        //액티비티가 종료되려고 합니다.
    }



}
