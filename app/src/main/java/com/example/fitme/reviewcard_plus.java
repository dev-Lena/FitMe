package com.example.fitme;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class reviewcard_plus extends AppCompatActivity {
//     현재 로그인한 유저의 정보만 담는 쉐어드 프리퍼런스

    //    Uri uri; // 전역변수로 Uri를 선언해줘야 클래스 내 다른 메소드 내에서도 사용할 수 있음.
    private ArrayList<List> userData = new ArrayList<List>();

    // 현재 로그인하고 있는 회원의 정보만 저장하는 쉐어드
    private SharedPreferences logined_user;
    private SharedPreferences.Editor user_editor;

    // 북마크한 리뷰를 저장하는 쉐어드
    private SharedPreferences bookmarkShared;
    private SharedPreferences.Editor bookmarkShared_editor;

    private SharedPreferences myreviewShared;
    private SharedPreferences.Editor myreviewShared_editor;

    public static String testkey;
    /// 삭제할 때 비교할 아이템 포지션 위치

    // 뷰 객체들

    ImageView imageView_reviewcard_img1;
    TextView textView_feed_id;
    ImageButton imageButton_back;


    // 모든 회원 정보와 모든 리뷰들이 저장되는 전체 쉐어드
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    /**
     * 리사이클러뷰에 필요한 기본 객체 선언
     **/
    ArrayList<feed_MainData> arrayList, bookmarked_arrayList, myreview_arrayList;
    //    ArrayList<feed_MainData> myreview_arrayList = new ArrayList<>();
//    ArrayList<feed_MainData> myreview_arrayList;
    feed_Adapter feed_adapter;
    RecyclerView recyclerView;
    LinearLayoutManager linearLayoutManager;

    private Context context;

    String textView_review_writer;  // 리뷰 작성 후 리뷰 카드에 들어가는 작성자
    String textView_reviewcard_number;  // 리뷰 작성 후 리뷰 카드에 들어가는 고유 번호
    String review_date;// 리뷰 작성 후 리뷰 카드에 들어가는 최초 작성 시간


    @Override
    protected void onCreate(final Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_card_plus);

// Allows to remember the last item shown on screen


        imageButton_back = findViewById(R.id.imageButton_back);
        imageButton_back.setOnClickListener(new ImageView.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent register_intent = new Intent(reviewcard_plus.this, feed.class);
                startActivity(register_intent); //액티비티 이동
                finish(); // 액티비티 finish 시킴

            }
        });




        // 리사이클러뷰 아이템에 있는 우측 상단 다이얼로그 메뉴 누르는 클릭 리스너
//        feed_adapter.setOnItemClickListener(new feed_Adapter.OnItemClickListener() {
//            @Override
//            public void onItemClick(View v, final int position) {
// TODO : 아이템 클릭 이벤트를 MainActivity에서 처리.
// 리사이클러뷰 수정
// 다이얼로그

                /** 여기에 로그인한 회원과 작성자와의 일치 여부에 따라 다른 메뉴가 보이도록 if  조건문 걸어주기. **/

    }// onCreate 닫는 중괄호




    // sharedPreference에 저장한 ArrayList 를 가져옴 (리사이클러뷰)
    private void loadData() {
        SharedPreferences sharedPreferences = getSharedPreferences("sharedPreferences", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("feed_recyclerview", null);
        Type type = new TypeToken<ArrayList<feed_MainData>>() {
        }.getType();
        Log.e("feed 클래스", "typeToken객체 생성 :" + type);
        arrayList = gson.fromJson(json, type);
        Log.e("feed 클래스", "fromJson : arryaList는 " + arrayList);

        if (arrayList == null) {
            arrayList = new ArrayList<>();
        }

    }


    // sharedPreference에 리사이클러뷰 안에 들어가는 arrayList를 저장하는 메소드를 만들어줌.
    private void saveData() {
        SharedPreferences sharedPreferences = getSharedPreferences("sharedPreferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        Log.e("feed 클래스", "Gson 객체 호출 : " + gson);
        String json = gson.toJson(arrayList);  // arrayList를 Json으로 바꿈 // 여기서 arrayList는 피드에 들어가는 리사이클러뷰를 담은 arrayList 이름임.
        Log.e("feed 클래스", "Gson 객체 호출 : " + json);
        editor.putString("feed_recyclerview", json);   // fromJson할 때도 "feed_recyclerview" 맞춰줌.
        Log.e("feed 클래스", "Gson 객체 호출 : " + editor.putString("feed_recyclerview", json));
        editor.apply();
        Log.e("feed 클래스", "apply 성공 ");
    }


    public void remove(int position) {
        // 피드 리사이클러뷰 안에 있는 리뷰를 삭제할 때 쓰는 remove 메소드

        myreview_loadData();  // 내가 쓴 리뷰 쉐어드를 가지고 온다
        bookmarked_loadData();  // 북마크한 리뷰 쉐어드를 가지고 온다

        int myreview_index = find_myreview_arrayList(feed_adapter.getItem(position).getTextView_reviewcard_number());
        Log.d("myreview_index", myreview_index + "");
        int bookmark_index = find_bookmark_arrayList(feed_adapter.getItem(position).getTextView_reviewcard_number());
        Log.d("bookmark_index", bookmark_index + "");

        try {
            // 피드 리사이클러뷰에 들어간느 arrayList에서 삭제 -> 지금 클래스에서는 이미 arrayList에서 작업하고 있기 때문에 아래 같은 작업이 필요없음.
            arrayList.remove(position);

            // 내가 쓴 리뷰 리사이클러뷰에서 삭제
            if (myreview_index > -1) { // 찾아온 값이 있을 때 -> 없으면 -1로 리턴하라고 했음
                myreview_arrayList.remove(myreview_index);
                Log.d("myreview_arrayList", "myreview_arrayList.size() : " + myreview_arrayList.size());
            }

            // 북마크한 리뷰 리사이클러뷰에 해당 리뷰가 있을 때
            if (bookmark_index > -1) { // 찾아온 값이 있을 때 -> 없으면 -1로 리턴하라고 했음
                bookmarked_arrayList.remove(bookmark_index);
                Log.d("bookmarked_arrayList", "bookmarked_arrayList.size() : " + bookmarked_arrayList.size());
            }else {
                //do nothing
                Log.d("bookmarked_arrayList", "bookmarked_arrayList.do nothing" + bookmarked_arrayList.size());
            }


            feed_adapter.notifyItemRemoved(position);
            // sharedPreference 에서 삭제하는 코드를 넣어줘야 함.... 굳이? arrayList에서 없애주면 되는거 아닌가?


        } catch (IndexOutOfBoundsException e) {
            e.printStackTrace();
        }
    }

    /** 메인 피드에서 글을 삭제하면 해당 리뷰와 같은 아이템을 피드 리사이클러뷰 arrayList에서도 찾아서 삭제해야하는데, 이 때 같은 리뷰 아이템을 찾는 메소드**/
    public int find_feed_arrayList (String uniqueKey) {  // 리사이클러뷰 리스트에 들어가는 아이템 객체를 가지고 와서
        // 아이템 객체와 arrayList 안에 아이템을 비교해주기 위해 선언해주는 변수
        // 리뷰 아이템마다 고유 번호를 랜덤으로 부여했는데, 아이템 객체를 가지고 와서 그 안에 있는 고유 번호를 비교하도록 해놓음.

        if (arrayList != null) {  // 리스트가 null 값이 아닐 때
            for (int i = 0; i < arrayList.size(); i++) {  // 사이즈만큼 순회하면서
                String data = arrayList.get(i).getTextView_reviewcard_number();
                // arrayList에서 인덱스 번호 i에 있는 아이템의 고유번호를 data 변수에 넣어주고

                if (data.equals(uniqueKey)) {  // 가져온 인덱스 번호 i에 있는 아이템의 고유번호가 uniqueKey와 같다면
                    //여기서 uniqueKey는 이 메소드를 사용하는 곳에서 파라미터?로 (feed_adapter.getItem(position).getTextView_reviewcard_number()를 넣어줌.
                    // 즉, 어댑터에서 해당 아이템의 포지션을 가지고 오는데 그 아이템이 가지고 있는 고유번호를 메소드를 사용할 때 참고해 uniqueKey로 설정해줌

                    return i; // 인덱스 i를 리턴해주고
                }

            }
        }
        return -1;  // 0보다 커야 작업을 수행할 수 있기 때문에 실패하면 -1을 리턴해주라 -> List에서 index는 0부터 시작이니까
    }


    /** 메인 피드에서 글을 삭제하면 해당 리뷰와 같은 아이템을 myreview 리사이클러뷰 arrayList에서도 찾아서 삭제해야하는데, 이 때 같은 리뷰 아이템을 찾는 메소드**/
    public int find_myreview_arrayList(String uniqueKey) {  // 리사이클러뷰 리스트에 들어가는 아이템 객체를 가지고 와서
         // 아이템 객체와 arrayList 안에 아이템을 비교해주기 위해 선언해주는 변수
        // 리뷰 아이템마다 고유 번호를 랜덤으로 부여했는데, 아이템 객체를 가지고 와서 그 안에 있는 고유 번호를 비교하도록 해놓음.

        if (myreview_arrayList != null) {  // 리스트가 null 값이 아닐 때
            for (int i = 0; i < myreview_arrayList.size(); i++) {  // 사이즈만큼 순회하면서
                String data = myreview_arrayList.get(i).getTextView_reviewcard_number();
                // arrayList에서 인덱스 번호 i에 있는 아이템의 고유번호를 data 변수에 넣어주고

                if (data.equals(uniqueKey)) {  // 가져온 인덱스 번호 i에 있는 아이템의 고유번호가 uniqueKey와 같다면
                    //여기서 uniqueKey는 이 메소드를 사용하는 곳에서 파라미터?로 (feed_adapter.getItem(position).getTextView_reviewcard_number()를 넣어줌.
                    // 즉, 어댑터에서 해당 아이템의 포지션을 가지고 오는데 그 아이템이 가지고 있는 고유번호를 메소드를 사용할 때 참고해 uniqueKey로 설정해줌

                    return i; // 인덱스 i를 리턴해주고
                }

            }
        }
        return -1;  // 0보다 커야 작업을 수행할 수 있기 때문에 실패하면 -1을 리턴해주라 -> List에서 index는 0부터 시작이니까
    }


    /** 메인 피드에서 글을 삭제하면 해당 리뷰와 같은 아이템을 bookmarked_arrayList 리사이클러뷰 arrayList에서도 찾아서 삭제해야하는데, 이 때 같은 리뷰 아이템을 찾는 메소드**/
    public int find_bookmark_arrayList(String uniqueKey) {  // 리사이클러뷰 리스트에 들어가는 아이템 객체를 가지고 와서
        // 아이템 객체와 arrayList 안에 아이템을 비교해주기 위해 선언해주는 변수
        // 리뷰 아이템마다 고유 번호를 랜덤으로 부여했는데, 아이템 객체를 가지고 와서 그 안에 있는 고유 번호를 비교하도록 해놓음.

        if (bookmarked_arrayList != null) {  // 리스트가 null 값이 아닐 때
            for (int i = 0; i < bookmarked_arrayList.size(); i++) {  // 사이즈만큼 순회하면서
                String data = bookmarked_arrayList.get(i).getTextView_reviewcard_number();
                // arrayList에서 인덱스 번호 i에 있는 아이템의 고유번호를 data 변수에 넣어주고

                if (data.equals(uniqueKey)) {  // 가져온 인덱스 번호 i에 있는 아이템의 고유번호가 uniqueKey와 같다면
                    //여기서 uniqueKey는 이 메소드를 사용하는 곳에서 파라미터?로 (feed_adapter.getItem(position).getTextView_reviewcard_number()를 넣어줌.
                    // 즉, 어댑터에서 해당 아이템의 포지션을 가지고 오는데 그 아이템이 가지고 있는 고유번호를 메소드를 사용할 때 참고해 uniqueKey로 설정해줌

                    return i; // 인덱스 i를 리턴해주고
                }

            }
        }
        if (bookmarked_arrayList == null) {  // 다른 arrayList와 달리 계속 비어있을 수 있기 때문에 null값에 대한 예외 처리가 필요함.
            bookmarked_arrayList = new ArrayList<>();
        }

        return -1;  // 0보다 커야 작업을 수행할 수 있기 때문에 실패하면 -1을 리턴해주라 -> List에서 index는 0부터 시작이니까
    }

    // 각 쉐어드에 저장하는 메소드
    private void bookmarked_loadData() {
//        SharedPreferences sharedPreferences = getSharedPreferences("sharedPreferences", MODE_PRIVATE);
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

    private void bookmark_saveData() {

//        bookmarked_arrayList = new ArrayList<>();
        // sharedPref
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
        Log.e("feed 클래스", "Gson 객체 호출 (키 , 들어간 값) : " + feed_email + "," + json);

        bookmarkShared_editor.apply();
        Log.e("feed 클래스", "editor. apply 성공 ");


    }//bookmarked_saveData 메소드 닫는 중괄호

    private void myreview_saveData() {

//
        SharedPreferences myreviewShared = getSharedPreferences("myreviewShared", MODE_PRIVATE);
        SharedPreferences.Editor myreviewShared_editor = myreviewShared.edit();
        Gson gson = new Gson();
        Log.e("myreview 클래스", "Gson 객체 호출 : " + gson);

        System.out.println("myReview arrayList.size : " + myreview_arrayList.size());

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



    private static final String ALPHA_NUMERIC_STRING = "0123456789";

    public static String randomkeygenerator() {
        int count = 8;
        StringBuilder builder = new StringBuilder();
        while (count-- != 0) {
            int character = (int) (Math.random() * ALPHA_NUMERIC_STRING.length());
            builder.append(ALPHA_NUMERIC_STRING.charAt(character));
        }
        return builder.toString();
    }


    // 생명주기 로그 찍으면서 확인
    @Override
    protected void onRestart() {
        super.onRestart();
        Log.e("feed", "onRestart");
        //액티비티가 중단되었다가 다시 시작
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.e("feed", "onStart");
        //액티비티가 화면에 나타나기 시작
    }

    @Override
    protected void onResume() {
        super.onResume();
//        feed_adapter.notifyDataSetChanged();  // 새로고침

        //액티비티가 화면에 나타나고 상호작용이 가능해짐
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.e("feed", "onPause");
        overridePendingTransition(0, 0);
        //다른 액티비티가 시작되려함, 이 액티비티는 중단되려하고 백그라운드로 들어갑니다.
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.e("feed", "onStop");
//        saveData();  // sharedPreference에 리뷰가 추가된 리사이클러뷰를 저장한다 // onCreate 밖에 메소드 만들었음.
//        Log.e("feed 클래스에서 (onStop)", "saveData");

        //액티비티가 더 이상 화면에 나타나지 않음,중단된 상태
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e("feed", "onDestroy");
        //액티비티가 종료되려고 합니다.
    }
}
