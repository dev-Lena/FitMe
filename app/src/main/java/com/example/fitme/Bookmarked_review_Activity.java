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
import com.kakao.kakaolink.v2.KakaoLinkResponse;
import com.kakao.kakaolink.v2.KakaoLinkService;
import com.kakao.message.template.LinkObject;
import com.kakao.message.template.TextTemplate;
import com.kakao.network.ErrorResult;
import com.kakao.network.callback.ResponseCallback;
import com.kakao.util.helper.log.Logger;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


/**
 * 사용자가 북마크한 리뷰들 (리사이클러뷰)
 * Adapter는 Feed_Main_Adapter 사용
 **/
public class Bookmarked_review_Activity extends AppCompatActivity {

    private ArrayList<List> userData = new ArrayList<List>();

    private SharedPreferences logined_user;
    private SharedPreferences.Editor user_editor;
    BottomNavigationView bottomNavigationView; // 바텀 네이게이션 메뉴  -> 하단바
    ImageButton imageButton_back;
    TextView textView_howmany_bookmarked_reviews;

    /**
     * 리사이클러뷰
     **/
    ArrayList<Feed_Main_ItemData> bookmarked_arrayList;
    Feed_Main_Adapter feedMain_adapter;
    RecyclerView recyclerView;
    LinearLayoutManager linearLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        bookmarked_arrayList = new ArrayList<>();

        bookmarked_loadData();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookmarked_review);


        /**리사이클러뷰**/

        recyclerView = (RecyclerView) findViewById(R.id.bookmarked_recyclerView);  // 화면 xml 파일에서 리사이클러뷰의 아이디와 매칭

        linearLayoutManager = new LinearLayoutManager(this);

        linearLayoutManager.setReverseLayout(true); // 최신순으로 리사이클러뷰 아이템 추가.
        recyclerView.setLayoutManager(linearLayoutManager);

        // 리사이클러뷰 최상단부터 보이기
        recyclerView.post(new Runnable() {

            @Override
            public void run() {
                recyclerView.scrollToPosition(recyclerView.getAdapter().getItemCount() - 1);
            }
        });

        feedMain_adapter = new Feed_Main_Adapter(bookmarked_arrayList, this);
        recyclerView.setAdapter(feedMain_adapter);

        // 지금까지 북마크한 리뷰는 xx 개입니다. 설정
        textView_howmany_bookmarked_reviews = findViewById(R.id.textView_howmany_bookmarked_reviews);
        int bookmark_count = bookmarked_arrayList.size();
        Log.d("bookmark_count", " : " + bookmark_count);
        textView_howmany_bookmarked_reviews.setText(String.valueOf(bookmark_count));
        Log.d("textView_howmany_bookmarked_reviews", " : " + textView_howmany_bookmarked_reviews);

        feedMain_adapter.setOnItemClickListener(new Feed_Main_Adapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, final int position) {

// 리사이클러뷰 아이템 안에 버튼을 누르면 팝업 메뉴 뜨도록
                PopupMenu popup = new PopupMenu(getApplicationContext(), v);//v는 클릭된 뷰를 의미

                getMenuInflater().inflate(R.menu.bookmarked_menu, popup.getMenu());
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {

                            case R.id.action_share: // 공유하기. 카카오톡 링크 공유 API
                                Toast.makeText(getApplication(), "공유하기", Toast.LENGTH_SHORT).show();
                                // 닉네임
                                logined_user = getSharedPreferences("logined_user", MODE_PRIVATE);
                                String feed_id = logined_user.getString("user_nickname", "");


                                // 텍스트 템플릿
                                String shop_url_link = bookmarked_arrayList.get(position).textView_shoppingmall_url;// 리뷰 아이템에서 사용자가 입력한 쇼핑몰 url
                                String shop_review_writer_link = bookmarked_arrayList.get(position).textView_nickname;
                                TextTemplate params = TextTemplate.newBuilder("[Fit Me]" + feed_id + "님께서 링크를 공유하였습니다 : \n\n" + shop_url_link, LinkObject.newBuilder().setWebUrl(shop_url_link + "\n\n").setMobileWebUrl(shop_url_link + "\n\n").build()).setButtonTitle("Fit Me 앱으로 이동하기").build();

                                HashMap<String, String> serverCallbackArgs = new HashMap<String, String>();
                                serverCallbackArgs.put("user_id", "${current_user_id}");
                                serverCallbackArgs.put("product_id", "${shared_product_id}");

                                KakaoLinkService.getInstance().sendDefault(Bookmarked_review_Activity.this, params, serverCallbackArgs, new ResponseCallback<KakaoLinkResponse>() {
                                    @Override
                                    public void onFailure(ErrorResult errorResult) {
                                        Logger.e(errorResult.toString());
                                    }

                                    @Override
                                    public void onSuccess(KakaoLinkResponse result) {
                                        // 템플릿 밸리데이션과 쿼터 체크가 성공적으로 끝남. 톡에서 정상적으로 보내졌는지 보장은 할 수 없다. 전송 성공 유무는 서버콜백 기능을 이용하여야 한다.
                                    }
                                });
                                break;
                            case R.id.action_report: // 신고하기

                                remove(position); // 삭제처리

                                feedMain_adapter.notifyDataSetChanged();  // 새로고침
                                bookmark_saveData();  // sharedPreference에 리뷰가 추가된 리사이클러뷰를 저장한다 // onCreate 밖에 메소드 만들었음.
                                Log.e("bokmarked_review 클래스 - ", "신고 완료");

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
            // 댓글
            public void onCommentClick(View v, int position) {
                Intent comment_intent = new Intent(Bookmarked_review_Activity.this, Comment_Activity.class);
                startActivity(comment_intent); //액티비티 이동

            }

            // 북마크한 리뷰 페이지에서 북마크 버튼을 누르면 -> 북마크 해제
            @Override
            public void onBookmarkClick(View v, int position) {

                remove(position);
                feedMain_adapter.notifyDataSetChanged();  // 새로고침

                bookmark_saveData();  // sharedPreference에 리뷰가 추가된 리사이클러뷰를 저장한다 // onCreate 밖에 메소드 만들었음.
                Log.e("Bookmarked_review_Activity 클래스에서 ", "onBookmarkClickㄹ 메소드 :" + feedMain_adapter);

                Toast.makeText(getApplication(), "북마크가 해제되었습니다", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onReviewClick(View v, int position) {

            }


        });

        // 뒤로 가기 버튼 눌렀을 때 피드(메인 화면)로 이동

        imageButton_back = findViewById(R.id.imageButton_back);
        imageButton_back.setOnClickListener(new ImageView.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent register_intent = new Intent(Bookmarked_review_Activity.this, Mypage_Activity.class);
                startActivity(register_intent); //액티비티 이동
                finish(); // 액티비티 finish 시킴

            }
        });


    }// onCreate 닫는 중괄호


    public void remove(int position) {
        // 피드 리사이클러뷰 안에 있는 리뷰를 삭제할 때 쓰는 remove 메소드

        try {
            bookmarked_arrayList.remove(position);
            feedMain_adapter.notifyItemRemoved(position);


            // sharedPreference 에서 삭제하는 코드를 넣어줘야 함.... 굳이? arrayList에서 없애주면 되는거 아닌가?

        } catch (IndexOutOfBoundsException e) {
            e.printStackTrace();
        }
    }

    private void bookmark_saveData() {
        // 북마크한 리뷰를 저장하는 메소드

        SharedPreferences bookmarkShared = getSharedPreferences("bookmarkShared", MODE_PRIVATE);
        SharedPreferences.Editor bookmarkShared_editor = bookmarkShared.edit();
        Gson gson = new Gson();
        Log.e("Feed_Main_Activity 클래스", "Gson 객체 호출 : " + gson);

        String json = gson.toJson(bookmarked_arrayList);  // 여기서 bookmarked_arrayList는 북마크한 리뷰 리사이클러뷰의 arrayList.
        Log.e("Bookmarked_review_Activity 클래스 - ", "Gson 객체 호출 (toJson(bookmarked_arrayList) : " + json);


        // 로그인 하고 있는 사용자의 이메일을 키값으로 갖는 value
        logined_user = getSharedPreferences("logined_user", Context.MODE_PRIVATE);   // 현재 로그인한 회원의 정보만 담겨있는 쉐어드를 불러와서
        String feed_email = logined_user.getString("user_email", "");

        // 여기 원래 feed_email이 키값이었음 -> 근데 그렇게 하면 그 사용자의 모든 정보가 bookmarked_arrayList로 덮어씌워짐
        //sharedPreference 쉐어드-> 로그인 하고 있는 사용자의 이메일을 키값으로 갖는 value에 bookmarked_arrayList를 String으로 변환한 값을 넣어줌.
        bookmarkShared_editor.putString(feed_email, json);   // fromJson할 때도 "feed_recyclerview" 맞춰줌. // 로그인한 유저의 이메일을 키값으로 json 데이터를 넣어줌.
        Log.e("Bookmarked_review_Activity 클래스 - ", "Gson 객체 호출 (키 , 들어간 값) : " + feed_email + "," + json);

        bookmarkShared_editor.apply();
        Log.e("Bookmarked_review_Activity 클래스 - ", "editor. apply 성공 ");


    }

    // sharedPreference에 저장한 Bookmarked_review_Activity 를 가져옴 (리사이클러뷰)
    private void bookmarked_loadData() {
        // 북마크한 리뷰를 저장한 데이터를 가져와 로드(load)하는 메소드

        SharedPreferences bookmarkShared = getSharedPreferences("bookmarkShared", MODE_PRIVATE);
        Gson gson = new Gson();

        // 로그인 하고 있는 사용자의 이메일을 키값으로 갖는 value에
        logined_user = getSharedPreferences("logined_user", Context.MODE_PRIVATE);   // 현재 로그인한 회원의 정보만 담겨있는 쉐어드를 불러와서
        String feed_email = logined_user.getString("user_email", "");
        Log.e("Bookmarked_review_Activity 클래스 (bookmarked_loadData) - ", "로그인한 유저의 이메일 호출 : " + feed_email);

        String json = bookmarkShared.getString(feed_email, null);
        Type type = new TypeToken<ArrayList<Feed_Main_ItemData>>() {
        }.getType();
        Log.e("Bookmarked_review_Activity 클래스 (bookmarked_loadData) - ", "typeToken객체 생성 :" + type);
        bookmarked_arrayList = gson.fromJson(json, type);
        Log.e("Bookmarked_review_Activity 클래스 (bookmarked_loadData) - ", "fromJson : arryaList(bookmarked_arrayList)는 " + bookmarked_arrayList);


        if (bookmarked_arrayList == null) {
            bookmarked_arrayList = new ArrayList<>();
        }
    }


    @Override
    protected void onRestart() {
        super.onRestart();
        Log.e("Bookmarked_review_Activity", "onRestart");
        //액티비티가 중단되었다가 다시 시작
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.e("Bookmarked_review_Activity", "onStart");
        //액티비티가 화면에 나타나기 시작
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e("Bookmarked_review_Activity", "onResume");
        //액티비티가 화면에 나타나고 상호작용이 가능해짐
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.e("Bookmarked_review_Activity", "onPause");
        overridePendingTransition(0, 0);
        //다른 액티비티가 시작되려함, 이 액티비티는 중단되려하고 백그라운드로 들어갑니다.
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.e("Bookmarked_review_Activity", "onStop");
        //액티비티가 더 이상 화면에 나타나지 않음,중단된 상태
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e("Bookmarked_review_Activity", "onDestroy");
        //액티비티가 종료되려고 합니다.
    }


}
