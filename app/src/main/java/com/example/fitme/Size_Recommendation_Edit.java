package com.example.fitme;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Size_Recommendation_Edit extends AppCompatActivity {
    /**
     * my fit 내 청바지 상세 사이즈를 입력 하면 평균값을 구해 바지 스타일별(핏별 - ex 스키니진, 와이드진 등) 상세 사이즈(cm) 추천 (include 사용)
     * - 내 청바지 상세 사이즈 수정하기 (ex  허리 70cm 등 )
     **/
    BottomNavigationView bottomNavigationView; // 바텀 네이게이션 메뉴  -> 하단바


    private ArrayList<List> data = new ArrayList<List>();

    private SharedPreferences sharedPreferences, sizeShared;
    private SharedPreferences.Editor editor, sizeShared_editor;

    // 현재 로그인하고 있는 회원의 정보만 저장하는 쉐어드
    private SharedPreferences logined_user;
    private SharedPreferences.Editor user_editor;

    //String feed_id;
    TextView textView_nickname;

    EditText
            editText_waist1, editText_waist2, editText_waist3, editText_waist4, editText_waist5,
            editText_sero1, editText_sero2, editText_sero3, editText_sero4, editText_sero5,
            editText_hip1, editText_hip2, editText_hip3, editText_hip4, editText_hip5,
            editText_bugGi1, editText_bugGi2, editText_bugGi3, editText_bugGi4, editText_bugGi5,
            editText_long1, editText_long2, editText_long3, editText_long4, editText_long5,
            editText_ancle1, editText_ancle2, editText_ancle3, editText_ancle4, editText_ancle5;

    Button button_myfit_save;
    TableLayout tableLayout_result, tableLayout_input;

    String feed_id;

    String namename1, namename2, namename3, namename4, namename5, name1, name2, name3, name4, name5;
    //    Integer.parseInt("");
    int waist1, waist2, waist3, waist4, waist5 = 0;
    int sero1, sero2, sero3, sero4, sero5 = 0;
    int hip1, hip2, hip3, hip4, hip5 = 0;
    int bugGi1, bugGi2, bugGi3, bugGi4, bugGi5 = 0;
    int long1, long2, long3, long4, long5 = 0;
    int ancle1, ancle2, ancle3, ancle4, ancle5 = 0;
    int waistwaist1, serosero1, hiphip1, bugGibugGi1, longlong1, ancleancle1;
    int waistwaist2, serosero2, hiphip2, bugGibugGi2, longlong2, ancleancle2;
    int waistwaist3, serosero3, hiphip3, bugGibugGi3, longlong3, ancleancle3;
    int waistwaist4, serosero4, hiphip4, bugGibugGi4, longlong4, ancleancle4;
    int waistwaist5, serosero5, hiphip5, bugGibugGi5, longlong5, ancleancle5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.e("follow", "onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myfit_edit);

        editText_waist1 = findViewById(R.id.editText_waist1);
        editText_sero1 = findViewById(R.id.editText_sero1);
        editText_hip1 = findViewById(R.id.editText_hip1);
        editText_bugGi1 = findViewById(R.id.editText_bugGi1);
        editText_long1 = findViewById(R.id.editText_long1);
        editText_ancle1 = findViewById(R.id.editText_ancle1);

        editText_waist2 = findViewById(R.id.editText_waist2);
        editText_sero2 = findViewById(R.id.editText_sero2);
        editText_hip2 = findViewById(R.id.editText_hip2);
        editText_bugGi2 = findViewById(R.id.editText_bugGi2);
        editText_long2 = findViewById(R.id.editText_long2);
        editText_ancle2 = findViewById(R.id.editText_ancle2);

        editText_waist3 = findViewById(R.id.editText_waist3);
        editText_sero3 = findViewById(R.id.editText_sero3);
        editText_hip3 = findViewById(R.id.editText_hip3);
        editText_bugGi3 = findViewById(R.id.editText_bugGi3);
        editText_long3 = findViewById(R.id.editText_long3);
        editText_ancle3 = findViewById(R.id.editText_ancle3);


        /**sideShade의 사용자의 사이즈를 저장할 때 key 값을 현재 로그인한 회원의 email로 설정**/

        SharedPreferences logined_user = getSharedPreferences("logined_user", Context.MODE_PRIVATE);   // 현재 로그인한 회원의 정보만 담겨있는 쉐어드를 불러와서
        final String feed_nick = logined_user.getString("user_nickname", "");
        Log.e("[인사이트] 로그인한 회원 정보가 있는 쉐어드에서", " 현재 로그인한 유저의 닉네임 넣기 : " + feed_nick);
        textView_nickname = findViewById(R.id.textView_nickname);
        textView_nickname = findViewById(R.id.textView_nickname);

        // 닉네임 받아와서 화면에서 보여주기 "닉네임님의 핏입니다 "
        textView_nickname.setText(feed_nick);

        feed_id = logined_user.getString("user_email", "");
        load_sizeShared();
        set_text_again();



        // 지금 보고있는 화면은 수정화면. 아래 버튼을 누르면 입력한 데이터가 저장되고 결과 화면으로 이동.
        button_myfit_save = (Button) findViewById(R.id.button_myfit_save);
        button_myfit_save.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {

//1번정보
//                name1 = editText_name1.getText().toString(); // 바지 이름
                waist1 = Integer.parseInt(editText_waist1.getText().toString()); // 허리
                sero1 = Integer.parseInt(editText_sero1.getText().toString()); // 밑위
                hip1 = Integer.parseInt(editText_hip1.getText().toString()); // 엉덩이
                bugGi1 = Integer.parseInt(editText_bugGi1.getText().toString()); // 허벅지
                long1 = Integer.parseInt(editText_long1.getText().toString()); // 기장
                ancle1 = Integer.parseInt(editText_ancle1.getText().toString()); // 밑단
//2번정보
//                name2 = editText_name2.getText().toString(); // 바지 이름
                waist2 = Integer.parseInt(editText_waist2.getText().toString()); // 허리
                sero2 = Integer.parseInt(editText_sero2.getText().toString()); // 밑위
                hip2 = Integer.parseInt(editText_hip2.getText().toString()); // 엉덩이
                bugGi2 = Integer.parseInt(editText_bugGi2.getText().toString()); // 허벅지
                long2 = Integer.parseInt(editText_long2.getText().toString()); // 기장
                ancle2 = Integer.parseInt(editText_ancle2.getText().toString()); // 밑단

//3번정보
//                name3 = editText_name3.getText().toString(); // 바지 이름
                waist3 = Integer.parseInt(editText_waist3.getText().toString()); // 허리
                sero3 = Integer.parseInt(editText_sero3.getText().toString()); // 밑위
                hip3 = Integer.parseInt(editText_hip3.getText().toString()); // 엉덩이
                bugGi3 = Integer.parseInt(editText_bugGi3.getText().toString()); // 허벅지
                long3 = Integer.parseInt(editText_long3.getText().toString()); // 기장
                ancle3 = Integer.parseInt(editText_ancle3.getText().toString()); // 밑단
////4번정보
////                name4 = editText_name4.getText().toString(); // 바지 이름
//                waist4 = Integer.parseInt(editText_waist4.getText().toString()); // 허리
//                sero4 = Integer.parseInt(editText_sero4.getText().toString()); // 밑위
//                hip4 = Integer.parseInt(editText_hip4.getText().toString()); // 엉덩이
//                bugGi4 = Integer.parseInt(editText_bugGi4.getText().toString()); // 허벅지
//                long4 = Integer.parseInt(editText_long4.getText().toString()); // 기장
//                ancle4 = Integer.parseInt(editText_ancle4.getText().toString()); // 밑단
////5번정보
////                name5 = editText_name5.getText().toString(); // 바지 이름
//                waist5 = Integer.parseInt(editText_waist5.getText().toString()); // 허리
//                sero5 = Integer.parseInt(editText_sero5.getText().toString()); // 밑위
//                hip5 = Integer.parseInt(editText_hip5.getText().toString()); // 엉덩이
//                bugGi5 = Integer.parseInt(editText_bugGi5.getText().toString()); // 허벅지
//                long5 = Integer.parseInt(editText_long5.getText().toString()); // 기장
//                ancle5 = Integer.parseInt(editText_ancle5.getText().toString()); // 밑단
//

                Intent result = new Intent();  // 넘겨줄 데이터를 담는 인텐트

                result.putExtra("waist1", waist1);  // putExtra로 데이터 보냄
                result.putExtra("sero1", sero1);  // putExtra로 데이터 보냄
                result.putExtra("hip1", hip1);  // putExtra로 데이터 보냄
                result.putExtra("bugGi1", bugGi1);  // putExtra로 데이터 보냄
                result.putExtra("long1", long1);  // String으로 바꿔서 putExtra로 데이터 보냄.
                result.putExtra("ancle1", ancle1);  // putExtra로 데이터 보냄\


                Log.e("waist1","waist1 : " + waist1);

                result.putExtra("waist2", waist2);  // putExtra로 데이터 보냄
                result.putExtra("sero2", sero2);  // putExtra로 데이터 보냄
                result.putExtra("hip2", hip2);  // putExtra로 데이터 보냄
                result.putExtra("bugGi2", bugGi2);  // putExtra로 데이터 보냄
                result.putExtra("long2", long2);  // String으로 바꿔서 putExtra로 데이터 보냄.
                result.putExtra("ancle2", ancle2);  // putExtra로 데이터 보냄

                result.putExtra("waist3", waist3);  // putExtra로 데이터 보냄
                result.putExtra("sero3", sero3);  // putExtra로 데이터 보냄
                result.putExtra("hip3", hip3);  // putExtra로 데이터 보냄
                result.putExtra("bugGi3", bugGi3);  // putExtra로 데이터 보냄
                result.putExtra("long3", long3);  // String으로 바꿔서 putExtra로 데이터 보냄.
                result.putExtra("ancle3", ancle3);  // putExtra로 데이터 보냄

                result.putExtra("waist4", waist4);  // putExtra로 데이터 보냄
                result.putExtra("sero4", sero4);  // putExtra로 데이터 보냄
                result.putExtra("hip4", hip4);  // putExtra로 데이터 보냄
                result.putExtra("bugGi4", bugGi4);  // putExtra로 데이터 보냄
                result.putExtra("long4", long4);  // String으로 바꿔서 putExtra로 데이터 보냄.
                result.putExtra("ancle4", ancle4);  // putExtra로 데이터 보냄

                result.putExtra("waist5", waist5);  // putExtra로 데이터 보냄
                result.putExtra("sero5", sero5);  // putExtra로 데이터 보냄
                result.putExtra("hip5", hip5);  // putExtra로 데이터 보냄
                result.putExtra("bugGi5", bugGi5);  // putExtra로 데이터 보냄
                result.putExtra("long5", long5);  // String으로 바꿔서 putExtra로 데이터 보냄.
                result.putExtra("ancle5", ancle5);  // putExtra로 데이터 보냄


                // 자신을 호출한 Activity로 데이터를 보낸다.
                setResult(RESULT_OK, result);


finish();


            }

        });

        bottomNavigationView = findViewById(R.id.bottomNavi);
        // 하단바 누를 때 색 바뀌게 하는 중
        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(2);
        menuItem.setChecked(true);
        //
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                switch (menuItem.getItemId()) {
                    case R.id.action_home:
                        Intent home_intent = new Intent(Size_Recommendation_Edit.this, Feed_Main_Activity.class);
                        home_intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                        startActivity(home_intent);//액티비티 띄우기
//                        startActivityForResult(intent,sub);//액티비티 띄우기
                        break;
                    case R.id.action_search:
                        Intent search_intent = new Intent(Size_Recommendation_Edit.this, Review_searching_Activity.class);
                        search_intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                        startActivity(search_intent);//액티비티 띄우기
                        break;
                    case R.id.action_insight:
                        Intent write_intent = new Intent(Size_Recommendation_Edit.this, Size_Recommendation_Activity.class);
                        write_intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                        startActivity(write_intent);//액티비티 띄우기
                        break;
                    case R.id.action_shop:
                        Intent insight_intent = new Intent(Size_Recommendation_Edit.this, Naver_Search_Shop_Main.class);
                        insight_intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                        startActivity(insight_intent);//액티비티 띄우기
                        break;
                    case R.id.action_mypage:
                        Intent mycloset_intent = new Intent(Size_Recommendation_Edit.this, Mypage_Activity.class);
                        mycloset_intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                        startActivity(mycloset_intent);//액티비티 띄우기
                        break;
                }


                return false;

            }
        });

    }//onCreate 닫는 중괄호

    public void set_text_again() {
        sizeShared = getSharedPreferences("sizeShared", Context.MODE_PRIVATE);   // 현재 로그인한 회원의 정보만 담겨있는 쉐어드를 불러와서


        int waist = sizeShared.getInt("허리", 0);
        int waist_2 = sizeShared.getInt("허리2", 0);
        int waist_3 = sizeShared.getInt("허리3", 0);
        int waist_4 = sizeShared.getInt("허리4", 0);
        int waist_5 = sizeShared.getInt("허리5", 0);
        int total_waist = (waist + waist_2 + waist_3 + waist_4 + waist_5) / 5;
        int skiny = total_waist - 1;
        Log.d("[Size_Recommendation_Activity] sizeShared 쉐어드에서", " 허리 : " + waist);
        Log.d("[Size_Recommendation_Activity] sizeShared 쉐어드에서", " 허리2 : " + waist_2);
        Log.d("[Size_Recommendation_Activity] sizeShared 쉐어드에서", " 허리3 : " + waist_3);
        Log.d("[Size_Recommendation_Activity] sizeShared 쉐어드에서", " 허리 4: " + waist_4);
        Log.d("[Size_Recommendation_Activity] sizeShared 쉐어드에서", " 허리 5: " + waist_5);
        Log.d("[Size_Recommendation_Activity] sizeShared 쉐어드에서", " 토탈 허리 공식 : (waist+waist2+waist3+waist4+waist5)/5;" + total_waist);
        editText_waist1.setText(String.valueOf(waist));
        editText_waist2.setText(String.valueOf(waist_2));
        editText_waist3.setText(String.valueOf(waist_3));
//        editText_waist4.setText(String.valueOf(waist_4));
//        editText_waist5.setText(String.valueOf(waist_5));
        // 스키니,와이드, 일자, 하이웨스트


        int sero = sizeShared.getInt("밑위", 0);
        int sero_2 = sizeShared.getInt("밑위2", 0);
        int sero_3 = sizeShared.getInt("밑위3", 0);
        int sero_4 = sizeShared.getInt("밑위4", 0);
        int sero_5 = sizeShared.getInt("밑위5", 0);
        int total_sero = (sero + sero_2 + sero_3 + sero_4 + sero_5) / 5;
        int skiny_sero = total_sero - 1;
        int high_sero = total_sero + 4;
        Log.e("[Size_Recommendation_Activity] sizeShared 쉐어드에서", " 밑위 : " + sero);
        Log.e("[Size_Recommendation_Activity] sizeShared 쉐어드에서", " 밑위2 : " + sero_2);
        Log.e("[Size_Recommendation_Activity] sizeShared 쉐어드에서", " 밑위3 : " + sero_3);
        Log.e("[Size_Recommendation_Activity] sizeShared 쉐어드에서", " 밑위 4: " + sero_4);
        Log.e("[Size_Recommendation_Activity] sizeShared 쉐어드에서", " 밑위 5: " + sero_5);
        Log.e("[Size_Recommendation_Activity] sizeShared 쉐어드에서", " 토탈 밑위 공식 : (waist+waist2+waist3+waist4+waist5)/5;" + total_sero);
        editText_sero1.setText(String.valueOf(sero));
        editText_sero2.setText(String.valueOf(sero_2));
        editText_sero3.setText(String.valueOf(sero_3));
//        editText_sero4.setText(String.valueOf(sero_4));
//        editText_sero5.setText(String.valueOf(sero_5));


        int hip = sizeShared.getInt("엉덩이", 0);
        int hip_2 = sizeShared.getInt("엉덩이2", 0);
        int hip_3 = sizeShared.getInt("엉덩이3", 0);
        int hip_4 = sizeShared.getInt("엉덩이4", 0);
        int hip_5 = sizeShared.getInt("엉덩이5", 0);
        int total_hip = (hip + hip_2 + hip_3 + hip_4 + hip_5) / 5;
        int skiny_hip = total_hip - 1;
        int wide_hip = total_hip + 2;
        Log.e("[Size_Recommendation_Activity] sizeShared 쉐어드에서", " 엉덩이 : " + hip);
        Log.e("[Size_Recommendation_Activity] sizeShared 쉐어드에서", " 엉덩이 2 : " + hip_2);
        Log.e("[Size_Recommendation_Activity] sizeShared 쉐어드에서", " 엉덩이 3 : " + hip_3);
        Log.e("[Size_Recommendation_Activity] sizeShared 쉐어드에서", " 엉덩이 4: " + hip_4);
        Log.e("[Size_Recommendation_Activity] sizeShared 쉐어드에서", " 엉덩이 5: " + hip_5);
        Log.e("[Size_Recommendation_Activity] sizeShared 쉐어드에서", " 토탈 엉덩이 공식 : (waist+waist2+waist3+waist4+waist5)/5;" + total_hip);
        editText_hip1.setText(String.valueOf(hip));
        editText_hip2.setText(String.valueOf(hip_2));
        editText_hip3.setText(String.valueOf(hip_3));
//        editText_hip4.setText(String.valueOf(hip_4));
//        editText_hip5.setText(String.valueOf(hip_5));


        int bugGi = sizeShared.getInt("허벅지", 0);
        int bugGi_2 = sizeShared.getInt("허벅지2", 0);
        int bugGi_3 = sizeShared.getInt("허벅지3", 0);
        int bugGi_4 = sizeShared.getInt("허벅지4", 0);
        int bugGi_5 = sizeShared.getInt("허벅지5", 0);
        int total_bugGi = (bugGi + bugGi_2 + bugGi_3 + bugGi_4 + bugGi_5) / 5;
        int skiny_bugGi = total_bugGi - 2;
        int wide_bugGi = total_bugGi + 5;
        Log.e("[Size_Recommendation_Activity] sizeShared 쉐어드에서", " 허벅지 : " + bugGi);
        Log.e("[Size_Recommendation_Activity] sizeShared 쉐어드에서", " 허벅지 2 : " + bugGi_2);
        Log.e("[Size_Recommendation_Activity] sizeShared 쉐어드에서", " 허벅지 3 : " + bugGi_3);
        Log.e("[Size_Recommendation_Activity] sizeShared 쉐어드에서", " 허벅지 4: " + bugGi_4);
        Log.e("[Size_Recommendation_Activity] sizeShared 쉐어드에서", " 허벅지 5: " + bugGi_5);
        Log.e("[Size_Recommendation_Activity] sizeShared 쉐어드에서", " 토탈 허벅지 공식 : (waist+waist2+waist3+waist4+waist5)/5;" + total_bugGi);
        editText_bugGi1.setText(String.valueOf(bugGi));
        editText_bugGi2.setText(String.valueOf(bugGi_2));
        editText_bugGi3.setText(String.valueOf(bugGi_3));
//        editText_bugGi4.setText(String.valueOf(bugGi_4));
//        editText_bugGi5.setText(String.valueOf(bugGi_5));


        int longlong = sizeShared.getInt("기장", 0);
        int longlong_2 = sizeShared.getInt("기장2", 0);
        int longlong_3 = sizeShared.getInt("기장3", 0);
        int longlong_4 = sizeShared.getInt("기장4", 0);
        int longlong_5 = sizeShared.getInt("기장5", 0);
        int total_longlong = (longlong + longlong_2 + longlong_3 + longlong_4 + longlong_5) / 5;
        int skiny_long = total_longlong - 1;
        int long_long = total_longlong + 1;
        int high_long = total_longlong + 4;
        Log.e("[Size_Recommendation_Activity] sizeShared 쉐어드에서", " 기장 : " + longlong);
        Log.e("[Size_Recommendation_Activity] sizeShared 쉐어드에서", " 기장 2 : " + longlong_2);
        Log.e("[Size_Recommendation_Activity] sizeShared 쉐어드에서", " 기장 3 : " + longlong_3);
        Log.e("[Size_Recommendation_Activity] sizeShared 쉐어드에서", " 기장 4: " + longlong_4);
        Log.e("[Size_Recommendation_Activity] sizeShared 쉐어드에서", " 기장 5: " + longlong_5);
        Log.e("[Size_Recommendation_Activity] sizeShared 쉐어드에서", " 토탈 기장 공식 : (waist+waist2+waist3+waist4+waist5)/5;" + total_longlong);
        editText_long1.setText(String.valueOf(longlong));
        editText_long2.setText(String.valueOf(longlong_2));
        editText_long3.setText(String.valueOf(longlong_3));
//        editText_long4.setText(String.valueOf(longlong_4));
//        editText_long5.setText(String.valueOf(longlong_5));


        int ancle = sizeShared.getInt("밑단", 0);
        int ancle_2 = sizeShared.getInt("밑단2", 0);
        int ancle_3 = sizeShared.getInt("밑단3", 0);
        int ancle_4 = sizeShared.getInt("밑단4", 0);
        int ancle_5 = sizeShared.getInt("밑단5", 0);
        int total_ancle = (ancle + ancle_2 + ancle_3 + ancle_4 + ancle_5) / 5;
        int skiny_ancle = total_ancle - 3;
        int wide_ancle = total_ancle + 4;
        int straight_ancle = total_ancle + 2;
        Log.e("[Size_Recommendation_Activity] sizeShared 쉐어드에서", " 밑단 : " + ancle);
        Log.e("[Size_Recommendation_Activity] sizeShared 쉐어드에서", " 밑단 2 : " + ancle_2);
        Log.e("[Size_Recommendation_Activity] sizeShared 쉐어드에서", " 밑단 3 : " + ancle_3);
        Log.e("[Size_Recommendation_Activity] sizeShared 쉐어드에서", " 밑단 4: " + ancle_4);
        Log.e("[Size_Recommendation_Activity] sizeShared 쉐어드에서", " 밑단 5: " + ancle_5);
        Log.e("[Size_Recommendation_Activity] sizeShared 쉐어드에서", " 토탈 밑단 공식 : (waist+waist2+waist3+waist4+waist5)/5;" + total_ancle);
        editText_ancle1.setText(String.valueOf(ancle));
        editText_ancle2.setText(String.valueOf(ancle_2));
        editText_ancle3.setText(String.valueOf(ancle_3));
//        editText_ancle4.setText(String.valueOf(ancle_4));
//        editText_ancle5.setText(String.valueOf(ancle_5));


    }

    public void load_sizeShared() {
        sizeShared = getSharedPreferences("sizeShared", Context.MODE_PRIVATE);
        sizeShared_editor = sizeShared.edit();

        String json = sizeShared.getString(feed_id, "");
        Log.e("Login 클래스에서 로그인 버튼을 눌렀을 때", "여기 확인하기 : " + json);

        // editText 로 입력한 이메일이 null 값이 아니면 = 무엇인가를 입력하면 -> 일단 쉐어드에 사용자가 입력한 이메일이 있는지 확인
        if (json != null) {

            try {

                JSONArray jsonArray = new JSONArray(json);
                for (int i = 0; i < jsonArray.length(); i++) {

                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    Log.e("Login 회원정보 확인", "sharedPreferences에서 저장된 array(string으로 저장됐던) 가져오기 : " + jsonObject);

                    String namename1 = jsonObject.getString("바지이름");
                    Log.e("Login 회원정보 확인", "sharedPreferences에서 저장된 array(string으로 저장됐던) 가져오기 : " + namename1);
                    waistwaist1 = jsonObject.getInt("허리");
                    Log.e("Size_Recommendation_Activity", "sharedPreferences에서 저장된 가져오기 waistwaist1 : " + waistwaist1);
                    serosero1 = jsonObject.getInt("밑위");
                    Log.e("Size_Recommendation_Activity", "sharedPreferences에서 저장된 가져오기 serosero1 : " + serosero1);
                    hiphip1 = jsonObject.getInt("엉덩이");
                    Log.e("Size_Recommendation_Activity", "sharedPreferences에서 저장된 가져오기 hiphip1 : " + hiphip1);
                    bugGibugGi1 = jsonObject.getInt("허벅지");
                    Log.e("Size_Recommendation_Activity", "sharedPreferences에서 저장된 가져오기 bugGibugGi1 : " + bugGibugGi1);
                    longlong1 = jsonObject.getInt("기장");
                    Log.e("Size_Recommendation_Activity", "sharedPreferences에서 저장된 가져오기 longlong1 : " + longlong1);
                    ancleancle1 = jsonObject.getInt("밑단");
                    Log.e("Size_Recommendation_Activity", "sharedPreferences에서 저장된 가져오기 ancleancle1 : " + ancleancle1);

                    String namename2 = jsonObject.getString("바지이름2");
                    Log.e("Login 회원정보 확인", "sharedPreferences에서 저장된 array(string으로 저장됐던) 가져오기 : " + namename2);
                    waistwaist2 = jsonObject.getInt("허리2");
                    Log.e("Size_Recommendation_Activity", "sharedPreferences에서 저장된 가져오기 waistwaist2 : " + waistwaist2);
                    serosero2 = jsonObject.getInt("밑위2");
                    Log.e("Size_Recommendation_Activity", "sharedPreferences에서 저장된 가져오기 serosero2 : " + serosero2);
                    hiphip2 = jsonObject.getInt("엉덩이2");
                    Log.e("Size_Recommendation_Activity", "sharedPreferences에서 저장된 가져오기 hiphip2 : " + hiphip2);
                    bugGibugGi2 = jsonObject.getInt("허벅지2");
                    Log.e("Size_Recommendation_Activity", "sharedPreferences에서 저장된 가져오기 bugGibugGi2 : " + bugGibugGi2);
                    longlong2 = jsonObject.getInt("기장2");
                    Log.e("Size_Recommendation_Activity", "sharedPreferences에서 저장된 가져오기 longlong2 : " + longlong2);
                    ancleancle2 = jsonObject.getInt("밑단2");
                    Log.e("Size_Recommendation_Activity", "sharedPreferences에서 저장된 가져오기 ancleancle2 : " + ancleancle2);

                    String namename3 = jsonObject.getString("바지이름3");
                    Log.e("Login 회원정보 확인", "sharedPreferences에서 저장된 array(string으로 저장됐던) 가져오기 : " + namename3);
                    waistwaist3 = jsonObject.getInt("허리3");
                    Log.e("Size_Recommendation_Activity", "sharedPreferences에서 저장된 가져오기 waistwaist3 : " + waistwaist3);
                    serosero3 = jsonObject.getInt("밑위3");
                    Log.e("Size_Recommendation_Activity", "sharedPreferences에서 저장된 가져오기 serosero3 : " + serosero3);
                    hiphip3 = jsonObject.getInt("엉덩이3");
                    Log.e("Size_Recommendation_Activity", "sharedPreferences에서 저장된 가져오기 hiphip3 : " + hiphip3);
                    bugGibugGi3 = jsonObject.getInt("허벅지3");
                    Log.e("Size_Recommendation_Activity", "sharedPreferences에서 저장된 가져오기 bugGibugGi3 : " + bugGibugGi3);
                    longlong3 = jsonObject.getInt("기장3");
                    Log.e("Size_Recommendation_Activity", "sharedPreferences에서 저장된 가져오기 longlong3 : " + longlong3);
                    ancleancle3 = jsonObject.getInt("밑단3");
                    Log.e("Size_Recommendation_Activity", "sharedPreferences에서 저장된 가져오기 ancleancle3 : " + ancleancle3);

                    String namename4 = jsonObject.getString("바지이름4");
                    Log.e("Login 회원정보 확인", "sharedPreferences에서 저장된 array(string으로 저장됐던) 가져오기 : " + namename4);
                    waistwaist4 = jsonObject.getInt("허리4");
                    Log.e("Size_Recommendation_Activity", "sharedPreferences에서 저장된 가져오기 waistwaist4 : " + waistwaist4);
                    serosero4 = jsonObject.getInt("밑위4");
                    Log.e("Size_Recommendation_Activity", "sharedPreferences에서 저장된 가져오기 serosero4 : " + serosero4);
                    hiphip4 = jsonObject.getInt("엉덩이4");
                    Log.e("Size_Recommendation_Activity", "sharedPreferences에서 저장된 가져오기 hiphip4 : " + hiphip4);
                    bugGibugGi4 = jsonObject.getInt("허벅지4");
                    Log.e("Size_Recommendation_Activity", "sharedPreferences에서 저장된 가져오기 bugGibugGi4 : " + bugGibugGi4);
                    longlong4 = jsonObject.getInt("기장4");
                    Log.e("Size_Recommendation_Activity", "sharedPreferences에서 저장된 가져오기 longlong4 : " + longlong4);
                    ancleancle4 = jsonObject.getInt("밑단4");
                    Log.e("Size_Recommendation_Activity", "sharedPreferences에서 저장된 가져오기 ancleancle4 : " + ancleancle4);

                    String namename5 = jsonObject.getString("바지이름5");
                    Log.e("Login 회원정보 확인", "sharedPreferences에서 저장된 array(string으로 저장됐던) 가져오기 : " + namename5);
                    waistwaist5 = jsonObject.getInt("허리5");
                    Log.e("Size_Recommendation_Activity", "sharedPreferences에서 저장된 가져오기 waistwaist5 : " + waistwaist5);
                    serosero5 = jsonObject.getInt("밑위5");
                    Log.e("Size_Recommendation_Activity", "sharedPreferences에서 저장된 가져오기 serosero5 : " + serosero5);
                    hiphip5 = jsonObject.getInt("엉덩이5");
                    Log.e("Size_Recommendation_Activity", "sharedPreferences에서 저장된 가져오기 hiphip5 : " + hiphip5);
                    bugGibugGi5 = jsonObject.getInt("허벅지5");
                    Log.e("Size_Recommendation_Activity", "sharedPreferences에서 저장된 가져오기 bugGibugGi5 : " + bugGibugGi5);
                    longlong5 = jsonObject.getInt("기장5");
                    Log.e("Size_Recommendation_Activity", "sharedPreferences에서 저장된 가져오기 longlong5 : " + longlong5);
                    ancleancle5 = jsonObject.getInt("밑단5");
                    Log.e("Size_Recommendation_Activity", "sharedPreferences에서 저장된 가져오기 ancleancle5 : " + ancleancle5);

                }


// 로그인할 때 로그인한 회원의 정보를 배열로 가지고 와서 추출 후 각각의 key값을 줘서 저장했던 value를 호출
                sizeShared_editor.putString("바지이름", namename1);  // 회원가입시 입력한 이메일이 각 arrayList의 key 값이 됨.
                sizeShared_editor.putInt("허리", waistwaist1);  // 회원가입시 입력한 비밀번호가 각 arrayList의 key 값이 됨.
                sizeShared_editor.putInt("밑위", serosero1);  // 회원가입시 입력한 평소 사이즈가 각 arrayList의 key 값이 됨.
                sizeShared_editor.putInt("엉덩이", hiphip1);  // 회원가입시 입력한 닉네임이 각 arrayList의 key 값이 됨.
                sizeShared_editor.putInt("허벅지", bugGibugGi1);  // 회원가입시 입력한 프로필 이미지이 각 arrayList의 key 값이 됨.
                sizeShared_editor.putInt("기장", longlong1);  // 회원가입시 입력한 닉네임이 각 arrayList의 key 값이 됨.
                sizeShared_editor.putInt("밑단", ancleancle1);  // 회원가입시 입력한 프로필 이미지이 각 arrayList의 key 값이 됨.

                sizeShared_editor.putString("바지이름2", namename2);  // 회원가입시 입력한 이메일이 각 arrayList의 key 값이 됨.
                sizeShared_editor.putInt("허리2", waistwaist2);  // 회원가입시 입력한 비밀번호가 각 arrayList의 key 값이 됨.
                sizeShared_editor.putInt("밑위2", serosero2);  // 회원가입시 입력한 평소 사이즈가 각 arrayList의 key 값이 됨.
                sizeShared_editor.putInt("엉덩이2", hiphip2);  // 회원가입시 입력한 닉네임이 각 arrayList의 key 값이 됨.
                sizeShared_editor.putInt("허벅지2", bugGibugGi2);  // 회원가입시 입력한 프로필 이미지이 각 arrayList의 key 값이 됨.
                sizeShared_editor.putInt("기장2", longlong2);  // 회원가입시 입력한 닉네임이 각 arrayList의 key 값이 됨.
                sizeShared_editor.putInt("밑단2", ancleancle2);  // 회원가입시 입력한 프로필 이미지이 각 arrayList의 key 값이 됨.

                sizeShared_editor.putString("바지이름3", namename3);  // 회원가입시 입력한 이메일이 각 arrayList의 key 값이 됨.
                sizeShared_editor.putInt("허리3", waistwaist3);  // 회원가입시 입력한 비밀번호가 각 arrayList의 key 값이 됨.
                sizeShared_editor.putInt("밑위3", serosero3);  // 회원가입시 입력한 평소 사이즈가 각 arrayList의 key 값이 됨.
                sizeShared_editor.putInt("엉덩이3", hiphip3);  // 회원가입시 입력한 닉네임이 각 arrayList의 key 값이 됨.
                sizeShared_editor.putInt("허벅지3", bugGibugGi3);  // 회원가입시 입력한 프로필 이미지이 각 arrayList의 key 값이 됨.
                sizeShared_editor.putInt("기장3", longlong3);  // 회원가입시 입력한 닉네임이 각 arrayList의 key 값이 됨.
                sizeShared_editor.putInt("밑단3", ancleancle3);  // 회원가입시 입력한 프로필 이미지이 각 arrayList의 key 값이 됨.

                sizeShared_editor.putString("바지이름4", namename4);  // 회원가입시 입력한 이메일이 각 arrayList의 key 값이 됨.
                sizeShared_editor.putInt("허리4", waistwaist4);  // 회원가입시 입력한 비밀번호가 각 arrayList의 key 값이 됨.
                sizeShared_editor.putInt("밑위4", serosero4);  // 회원가입시 입력한 평소 사이즈가 각 arrayList의 key 값이 됨.
                sizeShared_editor.putInt("엉덩이4", hiphip4);  // 회원가입시 입력한 닉네임이 각 arrayList의 key 값이 됨.
                sizeShared_editor.putInt("허벅지4", bugGibugGi4);  // 회원가입시 입력한 프로필 이미지이 각 arrayList의 key 값이 됨.
                sizeShared_editor.putInt("기장4", longlong4);  // 회원가입시 입력한 닉네임이 각 arrayList의 key 값이 됨.
                sizeShared_editor.putInt("밑단4", ancleancle4);  // 회원가입시 입력한 프로필 이미지이 각 arrayList의 key 값이 됨.

                sizeShared_editor.putString("바지이름5", namename5);  // 회원가입시 입력한 이메일이 각 arrayList의 key 값이 됨.
                sizeShared_editor.putInt("허리5", waistwaist5);  // 회원가입시 입력한 비밀번호가 각 arrayList의 key 값이 됨.
                sizeShared_editor.putInt("밑위5", serosero5);  // 회원가입시 입력한 평소 사이즈가 각 arrayList의 key 값이 됨.
                sizeShared_editor.putInt("엉덩이5", hiphip5);  // 회원가입시 입력한 닉네임이 각 arrayList의 key 값이 됨.
                sizeShared_editor.putInt("허벅지5", bugGibugGi5);  // 회원가입시 입력한 프로필 이미지이 각 arrayList의 key 값이 됨.
                sizeShared_editor.putInt("기장5", longlong5);  // 회원가입시 입력한 닉네임이 각 arrayList의 key 값이 됨.
                sizeShared_editor.putInt("밑단5", ancleancle5);  // 회원가입시 입력한 프로필 이미지이 각 arrayList의 key 값이 됨.



            } catch (JSONException e) {
                e.printStackTrace();
            }


        }


    }




    @Override
    protected void onRestart() {
        super.onRestart();
        Log.e("follow", "onRestart");
        //액티비티가 중단되었다가 다시 시작
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.e("follow", "onStart");
        //액티비티가 화면에 나타나기 시작
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e("follow", "onResume");
        //액티비티가 화면에 나타나고 상호작용이 가능해짐
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.e("follow", "onPause");
        overridePendingTransition(0, 0);
        //다른 액티비티가 시작되려함, 이 액티비티는 중단되려하고 백그라운드로 들어갑니다.
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.e("follow", "onStop");
        //액티비티가 더 이상 화면에 나타나지 않음,중단된 상태
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e("follow", "onDestroy");
        //액티비티가 종료되려고 합니다.
    }

}
