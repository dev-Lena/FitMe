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
import android.widget.ImageButton;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class insight_item extends AppCompatActivity {
/** 복사해서 만들어만 놓았음 수정해야함 **/
    BottomNavigationView bottomNavigationView; // 바텀 네이게이션 메뉴  -> 하단바


    private ArrayList<List> data = new ArrayList<List>();

    private SharedPreferences sharedPreferences, sizeShared;
    private SharedPreferences.Editor editor, sizeShared_editor;

    // 현재 로그인하고 있는 회원의 정보만 저장하는 쉐어드
    private SharedPreferences logined_user;
    private SharedPreferences.Editor user_editor;

//String feed_id;
    TextView textView_nickname,
    //사용자가 입력한 값의 평균치를 구한걸 보여주는 TextView
    textView_name,textView_waist,textView_sero, textView_hip, textView_bugGi, textView_long, textView_ancle,
    textView_name1,textView_waist1,textView_sero1, textView_hip1, textView_bugGi1, textView_long1, textView_ancle1,
    textView_name2,textView_waist2,textView_sero2, textView_hip2, textView_bugGi2, textView_long2, textView_ancle2,
    textView_name3,textView_waist3,textView_sero3, textView_hip3, textView_bugGi3, textView_long3, textView_ancle3,
    textView_name4,textView_waist4,textView_sero4, textView_hip4, textView_bugGi4, textView_long4, textView_ancle4;



    ImageButton imageButton_back;
    ImageButton imageButton_follow_back;
    Button button_myfit_save, button_myfit_edit;
    TableLayout tableLayout_result, tableLayout_input;

    String feed_id;

    String namename1,namename2,namename3,namename4,namename5,name1,name2,name3,name4,name5 ;
//    Integer.parseInt("");
    int waist1,waist2,waist3,waist4,waist5 = 0;
    int sero1,sero2,sero3,sero4,sero5 = 0;
    int hip1,hip2,hip3,hip4,hip5 = 0;
    int bugGi1,bugGi2,bugGi3,bugGi4,bugGi5 = 0;
    int long1,long2,long3,long4,long5 = 0;
    int ancle1,ancle2,ancle3,ancle4,ancle5 = 0;
    int waistwaist1,serosero1,hiphip1,bugGibugGi1,longlong1,ancleancle1 ;
    int waistwaist2,serosero2,hiphip2,bugGibugGi2,longlong2,ancleancle2 ;
    int waistwaist3,serosero3,hiphip3,bugGibugGi3,longlong3,ancleancle3 ;
    int waistwaist4,serosero4,hiphip4,bugGibugGi4,longlong4,ancleancle4 ;
    int waistwaist5,serosero5,hiphip5,bugGibugGi5,longlong5,ancleancle5 ;

    private Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.e("follow","onCreate");
//        something_to_save();
//        set_text_again();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insight_item);




        textView_waist = findViewById(R.id.textView_waist);
        textView_sero = findViewById(R.id.textView_sero);
        textView_hip = findViewById(R.id.textView_hip);
        textView_bugGi = findViewById(R.id.textView_bugGi);
        textView_long = findViewById(R.id.textView_long);
        textView_ancle = findViewById(R.id.textView_ancle);

        textView_waist1 = findViewById(R.id.textView_waist1);
        textView_sero1 = findViewById(R.id.textView_sero1);
        textView_hip1 = findViewById(R.id.textView_hip1);
        textView_bugGi1 = findViewById(R.id.textView_bugGi1);
        textView_long1 = findViewById(R.id.textView_long1);
        textView_ancle1 = findViewById(R.id.textView_ancle1);

        textView_waist2 = findViewById(R.id.textView_waist2);
        textView_sero2 = findViewById(R.id.textView_sero2);
        textView_hip2 = findViewById(R.id.textView_hip2);
        textView_bugGi2 = findViewById(R.id.textView_bugGi2);
        textView_long2 = findViewById(R.id.textView_long2);
        textView_ancle2 = findViewById(R.id.textView_ancle2);

        textView_waist3 = findViewById(R.id.textView_waist3);
        textView_sero3 = findViewById(R.id.textView_sero3);
        textView_hip3 = findViewById(R.id.textView_hip3);
        textView_bugGi3 = findViewById(R.id.textView_bugGi3);
        textView_long3 = findViewById(R.id.textView_long3);
        textView_ancle3 = findViewById(R.id.textView_ancle3);

        /**sideShade의 사용자의 사이즈를 저장할 때 key 값을 현재 로그인한 회원의 email로 설정**/

        SharedPreferences logined_user = getSharedPreferences("logined_user", Context.MODE_PRIVATE);   // 현재 로그인한 회원의 정보만 담겨있는 쉐어드를 불러와서
        final String feed_nick = logined_user.getString("user_nickname", "");
        Log.e("[인사이트] 로그인한 회원 정보가 있는 쉐어드에서", " 현재 로그인한 유저의 닉네임 넣기 : " + feed_nick);
        textView_nickname = findViewById(R.id.textView_nickname);
        textView_nickname = findViewById(R.id.textView_nickname);

        // 닉네임 받아와서 화면에서 보여주기 "닉네임님의 핏입니다 "
        textView_nickname.setText(feed_nick);

    feed_id = logined_user.getString("user_email", "");

        set_text_again();
        // sizeShared에 있는 데이터를 가지고 와서 setText해줌


//        sizeShared_editor.commit();   //제출




        //frameLayout 버튼 변경하는 것
        // 지금 보고 있는 화면은 결과 화면. 아래 버튼을 누르면 수정하는 화면으로 이동.
        button_myfit_edit = (Button) findViewById(R.id.button_myfit_edit) ;
        button_myfit_edit.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), insight_edit.class);
                startActivityForResult(intent, 5001);
//                finish();

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
                    case R.id.action_home :
                        Intent home_intent = new Intent(insight_item.this,feed.class);
                        home_intent.addFlags (Intent.FLAG_ACTIVITY_NO_ANIMATION);
                        startActivity(home_intent);//액티비티 띄우기
//                        startActivityForResult(intent,sub);//액티비티 띄우기
                        break;
                    case R.id.action_search :
                        Intent search_intent = new Intent(insight_item.this,searching.class);
                        search_intent.addFlags (Intent.FLAG_ACTIVITY_NO_ANIMATION);
                        startActivity(search_intent);//액티비티 띄우기
                        break;
                    case R.id.action_insight :
                        Intent write_intent = new Intent(insight_item.this, insight_item.class);
                        write_intent.addFlags (Intent.FLAG_ACTIVITY_NO_ANIMATION);
                        startActivity(write_intent);//액티비티 띄우기
                        break;
                    case R.id.action_notification :
                        Intent insight_intent = new Intent(insight_item.this,MainActivity.class);
                        insight_intent.addFlags (Intent.FLAG_ACTIVITY_NO_ANIMATION);
                        startActivity(insight_intent);//액티비티 띄우기
                        break;
                    case R.id.action_mypage :
                        Intent mycloset_intent = new Intent(insight_item.this, mypage.class);
                        mycloset_intent.addFlags (Intent.FLAG_ACTIVITY_NO_ANIMATION);
                        startActivity(mycloset_intent);//액티비티 띄우기
                        break;
                }


                return false;

            }
        });

        }//onCreate 닫는 중괄호

    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // setResult를 통해 받아온 요청번호, 상태, 데이터
        Log.e("RESULT", requestCode + "");
        Log.e("RESULT", resultCode + "");
        Log.e("RESULT", data + "");

        // write_review 클래스에서 리뷰 작성 후 사용자가 입력한 데이터를 가지고 옴.
        /** 리뷰 작성 **/
        if (requestCode == 5001 && resultCode == RESULT_OK) {


            Toast.makeText(insight_item.this, "사이즈 작성을 완료했습니다!", Toast.LENGTH_SHORT).show();

            // 인텐트로 edit에서 사용자가 입력한 내용을 가져와서
// 1번정보
            int  waist1 = data.getIntExtra("waist1",0);
            int  sero1 = data.getIntExtra("sero1",0);
            int  hip1 = data.getIntExtra("hip1",0);
            int  bugGi1 = data.getIntExtra("bugGi1",0);
            int  long1 = data.getIntExtra("long1",0);
            int  ancle1 = data.getIntExtra("ancle1",0);
//2번정보
            int  waist2 = data.getIntExtra("waist2",0);
            int  sero2 = data.getIntExtra("sero2",0);
            int  hip2 = data.getIntExtra("hip2",0);
            int  bugGi2 = data.getIntExtra("bugGi2",0);
            int  long2 = data.getIntExtra("long2",0);
            int  ancle2 = data.getIntExtra("ancle2",0);
            //3번정보
            int  waist3 = data.getIntExtra("waist3",0);
            int  sero3 = data.getIntExtra("sero3",0);
            int  hip3 = data.getIntExtra("hip3",0);
            int  bugGi3 = data.getIntExtra("bugGi3",0);
            int  long3 = data.getIntExtra("long3",0);
            int  ancle3 = data.getIntExtra("ancle3",0);
            //4번정보
            int  waist4 = data.getIntExtra("waist4",0);
            int  sero4 = data.getIntExtra("sero4",0);
            int  hip4 = data.getIntExtra("hip4",0);
            int  bugGi4 = data.getIntExtra("bugGi4",0);
            int  long4 = data.getIntExtra("long4",0);
            int  ancle4 = data.getIntExtra("ancle4",0);
            //5번정보
            int  waist5 = data.getIntExtra("waist5",0);
            int  sero5 = data.getIntExtra("sero5",0);
            int  hip5 = data.getIntExtra("hip5",0);
            int  bugGi5 = data.getIntExtra("bugGi5",0);
            int  long5 = data.getIntExtra("long5",0);
            int  ancle5 = data.getIntExtra("ancle5",0);


            int total_waist = (waist1+waist2+waist3+waist4+waist5)/5;
            int skiny = total_waist  -1;

            Log.e("[insight] sizeShared 쉐어드에서", " 허리 : " + waist1);
            Log.e("[insight] sizeShared 쉐어드에서", " 허리2 : " + waist2);
            Log.e("[insight] sizeShared 쉐어드에서", " 허리3 : " + waist3);
            Log.e("[insight] sizeShared 쉐어드에서", " 허리 4: " + waist4);
            Log.e("[insight] sizeShared 쉐어드에서", " 허리 5: " + waist5);
            Log.e("[insight] sizeShared 쉐어드에서", " 토탈 허리 공식 : (waist+waist2+waist3+waist4+waist5)/5;" + total_waist);
            textView_waist.setText(String.valueOf(total_waist));
            // 스키니,와이드, 일자, 하이웨스트
            textView_waist1.setText(String.valueOf(skiny));
            textView_waist2.setText(String.valueOf(total_waist));
            textView_waist3.setText(String.valueOf(total_waist));
            textView_waist4.setText(String.valueOf(total_waist));

            int total_sero = (sero1+sero2+sero3+sero4+sero5)/5;
            int skiny_sero = total_sero -1;
            int high_sero = total_sero + 4 ;
            Log.e("[insight] sizeShared 쉐어드에서", " 밑위 : " + sero1);
            Log.e("[insight] sizeShared 쉐어드에서", " 밑위2 : " + sero2);
            Log.e("[insight] sizeShared 쉐어드에서", " 밑위3 : " + sero3);
            Log.e("[insight] sizeShared 쉐어드에서", " 밑위 4: " + sero4);
            Log.e("[insight] sizeShared 쉐어드에서", " 밑위 5: " + sero5);
            Log.e("[insight] sizeShared 쉐어드에서", " 토탈 밑위 공식 : (waist+waist2+waist3+waist4+waist5)/5;" + total_sero);
            textView_sero.setText(String.valueOf(total_sero));
            // 스키니,와이드, 일자, 하이웨스트
            textView_sero1.setText(String.valueOf(skiny_sero));
            textView_sero2.setText(String.valueOf(total_sero));
            textView_sero3.setText(String.valueOf(total_sero));
            textView_sero4.setText(String.valueOf(high_sero));


            int total_hip = (hip1+hip2+hip3+hip4+hip5)/5;
            int skiny_hip = total_hip-1;
            int wide_hip =total_hip +2;
            Log.e("[insight] sizeShared 쉐어드에서", " 엉덩이 : " + hip1);
            Log.e("[insight] sizeShared 쉐어드에서", " 엉덩이 2 : " + hip2);
            Log.e("[insight] sizeShared 쉐어드에서", " 엉덩이 3 : " + hip3);
            Log.e("[insight] sizeShared 쉐어드에서", " 엉덩이 4: " + hip4);
            Log.e("[insight] sizeShared 쉐어드에서", " 엉덩이 5: " + hip5);
            Log.e("[insight] sizeShared 쉐어드에서", " 토탈 엉덩이 공식 : (waist+waist2+waist3+waist4+waist5)/5;" + total_hip);
            textView_hip.setText(String.valueOf(total_hip));
            // 스키니,와이드, 일자, 하이웨스트
            textView_hip1.setText(String.valueOf(skiny_hip));
            textView_hip2.setText(String.valueOf(wide_hip));
            textView_hip3.setText(String.valueOf(total_hip));
            textView_hip4.setText(String.valueOf(total_hip));

            int total_bugGi = (bugGi1+bugGi2+bugGi3+bugGi4+bugGi5)/5;
            int skiny_bugGi = total_bugGi- 2;
            int wide_bugGi = total_bugGi +5;
            Log.e("[insight] sizeShared 쉐어드에서", " 허벅지 : " + bugGi1);
            Log.e("[insight] sizeShared 쉐어드에서", " 허벅지 2 : " + bugGi2);
            Log.e("[insight] sizeShared 쉐어드에서", " 허벅지 3 : " + bugGi3);
            Log.e("[insight] sizeShared 쉐어드에서", " 허벅지 4: " + bugGi4);
            Log.e("[insight] sizeShared 쉐어드에서", " 허벅지 5: " + bugGi5);
            Log.e("[insight] sizeShared 쉐어드에서", " 토탈 허벅지 공식 : (waist+waist2+waist3+waist4+waist5)/5;" + total_bugGi);
            textView_bugGi.setText(String.valueOf(total_bugGi));
            // 스키니,와이드, 일자, 하이웨스트
            textView_bugGi1.setText(String.valueOf(skiny_bugGi));
            textView_bugGi2.setText(String.valueOf(wide_bugGi));
            textView_bugGi3.setText(String.valueOf(total_bugGi));
            textView_bugGi4.setText(String.valueOf(total_bugGi));


            int total_longlong = (longlong1+longlong2+longlong3+longlong4+longlong5)/5;
            int skiny_long = total_longlong - 1;
            int long_long = total_longlong +1;
            int high_long =total_longlong+4;
            Log.e("[insight] sizeShared 쉐어드에서", " 기장 : " + longlong1);
            Log.e("[insight] sizeShared 쉐어드에서", " 기장 2 : " + longlong2);
            Log.e("[insight] sizeShared 쉐어드에서", " 기장 3 : " + longlong3);
            Log.e("[insight] sizeShared 쉐어드에서", " 기장 4: " + longlong4);
            Log.e("[insight] sizeShared 쉐어드에서", " 기장 5: " + longlong5);
            Log.e("[insight] sizeShared 쉐어드에서", " 토탈 기장 공식 : (waist+waist2+waist3+waist4+waist5)/5;" + total_longlong);
            textView_long.setText(String.valueOf(total_longlong));
            // 스키니,와이드, 일자, 하이웨스트
            textView_long1.setText(String.valueOf(skiny_long));
            textView_long2.setText(String.valueOf(long_long));
            textView_long3.setText(String.valueOf(total_longlong));
            textView_long4.setText(String.valueOf(high_long));


            int total_ancle = (ancle1+ancle2+ancle3+ancle4+ancle5)/5;
            int skiny_ancle = total_ancle -3;
            int wide_ancle = total_ancle +4;
            int straight_ancle = total_ancle +2;
            Log.e("[insight] sizeShared 쉐어드에서", " 밑단 : " + ancle1);
            Log.e("[insight] sizeShared 쉐어드에서", " 밑단 2 : " + ancle2);
            Log.e("[insight] sizeShared 쉐어드에서", " 밑단 3 : " + ancle3);
            Log.e("[insight] sizeShared 쉐어드에서", " 밑단 4: " + ancle4);
            Log.e("[insight] sizeShared 쉐어드에서", " 밑단 5: " + ancle5);
            Log.e("[insight] sizeShared 쉐어드에서", " 토탈 밑단 공식 : (waist+waist2+waist3+waist4+waist5)/5;" + total_ancle);
            textView_ancle.setText(String.valueOf(total_ancle));
            // 스키니,와이드, 일자, 하이웨스트
            textView_ancle1.setText(String.valueOf(skiny_ancle));
            textView_ancle2.setText(String.valueOf(wide_ancle));
            textView_ancle3.setText(String.valueOf(straight_ancle));
            textView_ancle4.setText(String.valueOf(total_ancle));
            TextView text_view_fitname = findViewById(R.id. text_view_fitname);
//            String image_view = "";
//
//            text_view_fitname.setText("스키니핏");
//
//            Picasso.get().load().fit().centerInside().into(image_view);
//
//            InsightItemData insightItemData = new InsightItemData( text_view_fitname,  textView_waist,  textView_sero,  textView_bugGi,  textView_long,  image_view,  textView_ancle);

//            feed_MainData AA = new feed_MainData(textView_shoppingmall_url,textView_detailed_review_card,float_ratingBar,textView_hashtag,review_date,textView_review_writer,textView_reviewcard_number,textView_nickname,textView_mysize,imageView_reviewcard_img1,imageView_reviewcard_profile_image);
//            insight_arrayList.add(insightItemData);  //리사이클러뷰의 arrayList에 아이템 추가
//
//
//            insightAdapter.notifyDataSetChanged();  // 새로고침


            // sharedPreference에 리뷰가 추가된 리사이클러뷰를 저장한다


            sizeShared = getSharedPreferences("sizeShared", Context.MODE_PRIVATE);
            sizeShared_editor = sizeShared.edit();

//                SharedPreferences logined_user = getSharedPreferences("logined_user", Context.MODE_PRIVATE);   // 현재 로그인한 회원의 정보만 담겨있는 쉐어드를 불러와서
//                final String feed_id = logined_user.getString("user_nickname", "");
            String json = sizeShared.getString(feed_id, "");
            Log.e("login 클래스에서 로그인 버튼을 눌렀을 때", "여기 확인하기 : " + json);


// 로그인할 때 로그인한 회원의 정보를 배열로 가지고 와서 추출 후 각각의 key값을 줘서 저장했던 value를 호출
//            sizeShared_editor.putString( "바지이름", namename1);  // 회원가입시 입력한 이메일이 각 arrayList의 key 값이 됨.

            sizeShared_editor.putInt("허리", waist1);  // 회원가입시 입력한 비밀번호가 각 arrayList의 key 값이 됨.
            sizeShared_editor.putInt("밑위", sero1);  // 회원가입시 입력한 평소 사이즈가 각 arrayList의 key 값이 됨.
            sizeShared_editor.putInt("엉덩이", hip1);  // 회원가입시 입력한 닉네임이 각 arrayList의 key 값이 됨.
            sizeShared_editor.putInt("허벅지", bugGi1);  // 회원가입시 입력한 프로필 이미지이 각 arrayList의 key 값이 됨.
            sizeShared_editor.putInt("기장", long1);  // 회원가입시 입력한 닉네임이 각 arrayList의 key 값이 됨.
            sizeShared_editor.putInt("밑단", ancle1);  // 회원가입시 입력한 프로필 이미지이 각 arrayList의 key 값이 됨.

//            sizeShared_editor.putString( "바지이름2", namename2);  // 회원가입시 입력한 이메일이 각 arrayList의 key 값이 됨.
            sizeShared_editor.putInt("허리2", waist2);  // 회원가입시 입력한 비밀번호가 각 arrayList의 key 값이 됨.
            sizeShared_editor.putInt("밑위2", sero2);  // 회원가입시 입력한 평소 사이즈가 각 arrayList의 key 값이 됨.
            sizeShared_editor.putInt("엉덩이2", hip2);  // 회원가입시 입력한 닉네임이 각 arrayList의 key 값이 됨.
            sizeShared_editor.putInt("허벅지2", bugGi2);  // 회원가입시 입력한 프로필 이미지이 각 arrayList의 key 값이 됨.
            sizeShared_editor.putInt("기장2", long2);  // 회원가입시 입력한 닉네임이 각 arrayList의 key 값이 됨.
            sizeShared_editor.putInt("밑단2", ancle2);  // 회원가입시 입력한 프로필 이미지이 각 arrayList의 key 값이 됨.

//            sizeShared_editor.putString( "바지이름3", namename3);  // 회원가입시 입력한 이메일이 각 arrayList의 key 값이 됨.
            sizeShared_editor.putInt("허리3", waist3);  // 회원가입시 입력한 비밀번호가 각 arrayList의 key 값이 됨.
            sizeShared_editor.putInt("밑위3", sero3);  // 회원가입시 입력한 평소 사이즈가 각 arrayList의 key 값이 됨.
            sizeShared_editor.putInt("엉덩이3", hip3);  // 회원가입시 입력한 닉네임이 각 arrayList의 key 값이 됨.
            sizeShared_editor.putInt("허벅지3", bugGi3);  // 회원가입시 입력한 프로필 이미지이 각 arrayList의 key 값이 됨.
            sizeShared_editor.putInt("기장3", long3);  // 회원가입시 입력한 닉네임이 각 arrayList의 key 값이 됨.
            sizeShared_editor.putInt("밑단3", ancle3);  // 회원가입시 입력한 프로필 이미지이 각 arrayList의 key 값이 됨.

//            sizeShared_editor.putString( "바지이름4", namename4);  // 회원가입시 입력한 이메일이 각 arrayList의 key 값이 됨.
            sizeShared_editor.putInt("허리4", waist4);  // 회원가입시 입력한 비밀번호가 각 arrayList의 key 값이 됨.
            sizeShared_editor.putInt("밑위4", sero4);  // 회원가입시 입력한 평소 사이즈가 각 arrayList의 key 값이 됨.
            sizeShared_editor.putInt("엉덩이4", hip4);  // 회원가입시 입력한 닉네임이 각 arrayList의 key 값이 됨.
            sizeShared_editor.putInt("허벅지4", bugGi4);  // 회원가입시 입력한 프로필 이미지이 각 arrayList의 key 값이 됨.
            sizeShared_editor.putInt("기장4", long4);  // 회원가입시 입력한 닉네임이 각 arrayList의 key 값이 됨.
            sizeShared_editor.putInt("밑단4", ancle4);  // 회원가입시 입력한 프로필 이미지이 각 arrayList의 key 값이 됨.

//            sizeShared_editor.putString( "바지이름5", namename5);  // 회원가입시 입력한 이메일이 각 arrayList의 key 값이 됨.
            sizeShared_editor.putInt("허리5", waist5);  // 회원가입시 입력한 비밀번호가 각 arrayList의 key 값이 됨.
            sizeShared_editor.putInt("밑위5", sero5);  // 회원가입시 입력한 평소 사이즈가 각 arrayList의 key 값이 됨.
            sizeShared_editor.putInt("엉덩이5", hip5);  // 회원가입시 입력한 닉네임이 각 arrayList의 key 값이 됨.
            sizeShared_editor.putInt("허벅지5", bugGi5);  // 회원가입시 입력한 프로필 이미지이 각 arrayList의 key 값이 됨.
            sizeShared_editor.putInt("기장5", long5);  // 회원가입시 입력한 닉네임이 각 arrayList의 key 값이 됨.
            sizeShared_editor.putInt("밑단5", ancle5);  // 회원가입시 입력한 프로필 이미지이 각 arrayList의 key 값이 됨.



            sizeShared_editor.commit();   //제출




//            String size_jsondata = jsonArray.toString();  // jsonArray를 String값으로 바꿈. String으로 바꾼 jsonArray를 user_jsondata라고 이름붙임.
//            Log.e("login 클래스에서 로그인 버튼을 눌렀을 때", "user_jsondata 확인 중 : " + size_jsondata);
//            save_in_Shared(size_jsondata);                    // saveArrayList 메소드를 실행할건데 josndata를 사용할 것 -> onCreate 밖에 메소드 만듦.
//            Log.e("login 클래스에서 로그인 버튼을 눌렀을 때", "user_writeArrayList(user_jsondata) 확인중 : " + size_jsondata);

//            something_to_load ();  // 쉐어드에 저장되어있던 정보들을 원키 원밸류로 넣어줌
            //아예 작성자가 값을 입력하고 가지고 왔을 때 원키 원벨류로 넣어주고 사용자가 결과 화면을 켰을 떄는
            //원키 원벨류에 들어가 있는 값을 로드하게





        }
    }


            public void set_text_again(){
            sizeShared = getSharedPreferences("sizeShared", Context.MODE_PRIVATE);   // 현재 로그인한 회원의 정보만 담겨있는 쉐어드를 불러와서

//            스키니핏 -> 허리 -1, 밑위 -1, 힙 -1, 허벅지 -2, 기장 -1, 밑단 -3
//            와이드핏 -> 허리, 밑위,힙+2,벅지+5, 기장+1, 밑단 +4
//            일자핏 -> 허리, 밑위, 힙,허벅지, 기장, 밑단 + 2
//            하이웨스트 -> 허리, 힙, 밑위 +4, 허벅지, 기장 +4, 밑단

            //로드 해줘야 하나?

            int waist = sizeShared.getInt("허리", 0);
            int waist_2 =  sizeShared.getInt("허리2", 0);
            int waist_3 =  sizeShared.getInt("허리3", 0);
            int waist_4 =  sizeShared.getInt("허리4", 0);
            int waist_5 =  sizeShared.getInt("허리5", 0);
            int total_waist = (waist+waist_2+waist_3+waist_4+waist_5)/5;
            int skiny = total_waist  -1;
            Log.d("[insight] sizeShared 쉐어드에서", " 허리 : " + waist);
            Log.d("[insight] sizeShared 쉐어드에서", " 허리2 : " + waist_2);
            Log.d("[insight] sizeShared 쉐어드에서", " 허리3 : " + waist_3);
            Log.d("[insight] sizeShared 쉐어드에서", " 허리 4: " + waist_4);
            Log.d("[insight] sizeShared 쉐어드에서", " 허리 5: " + waist_5);
            Log.d("[insight] sizeShared 쉐어드에서", " 토탈 허리 공식 : (waist+waist2+waist3+waist4+waist5)/5;" + total_waist);
            textView_waist.setText(String.valueOf(total_waist));
            // 스키니,와이드, 일자, 하이웨스트
            textView_waist1.setText(String.valueOf(skiny));
            textView_waist2.setText(String.valueOf(total_waist));
            textView_waist3.setText(String.valueOf(total_waist));
            textView_waist4.setText(String.valueOf(total_waist));

            int sero = sizeShared.getInt("밑위", 0);
            int sero_2 = sizeShared.getInt("밑위2", 0);
            int sero_3 = sizeShared.getInt("밑위3", 0);
            int sero_4 = sizeShared.getInt("밑위4", 0);
            int sero_5 = sizeShared.getInt("밑위5", 0);
            int total_sero = (sero+sero_2+sero_3+sero_4+sero_5)/5;
            int skiny_sero = total_sero -1;
            int high_sero = total_sero + 4 ;
            Log.e("[insight] sizeShared 쉐어드에서", " 밑위 : " + sero);
            Log.e("[insight] sizeShared 쉐어드에서", " 밑위2 : " + sero_2);
            Log.e("[insight] sizeShared 쉐어드에서", " 밑위3 : " + sero_3);
            Log.e("[insight] sizeShared 쉐어드에서", " 밑위 4: " + sero_4);
            Log.e("[insight] sizeShared 쉐어드에서", " 밑위 5: " + sero_5);
            Log.e("[insight] sizeShared 쉐어드에서", " 토탈 밑위 공식 : (waist+waist2+waist3+waist4+waist5)/5;" + total_sero);
            textView_sero.setText(String.valueOf(total_sero));
            // 스키니,와이드, 일자, 하이웨스트
            textView_sero1.setText(String.valueOf(skiny_sero));
            textView_sero2.setText(String.valueOf(total_sero));
            textView_sero3.setText(String.valueOf(total_sero));
            textView_sero4.setText(String.valueOf(high_sero));


            int hip = sizeShared.getInt("엉덩이", 0);
            int hip_2 = sizeShared.getInt("엉덩이2", 0);
            int hip_3 = sizeShared.getInt("엉덩이3", 0);
            int hip_4 = sizeShared.getInt("엉덩이4", 0);
            int hip_5 = sizeShared.getInt("엉덩이5", 0);
            int total_hip = (hip+hip_2+hip_3+hip_4+hip_5)/5;
            int skiny_hip = total_hip-1;
            int wide_hip =total_hip +2;
            Log.e("[insight] sizeShared 쉐어드에서", " 엉덩이 : " + hip);
            Log.e("[insight] sizeShared 쉐어드에서", " 엉덩이 2 : " + hip_2);
            Log.e("[insight] sizeShared 쉐어드에서", " 엉덩이 3 : " + hip_3);
            Log.e("[insight] sizeShared 쉐어드에서", " 엉덩이 4: " + hip_4);
            Log.e("[insight] sizeShared 쉐어드에서", " 엉덩이 5: " + hip_5);
            Log.e("[insight] sizeShared 쉐어드에서", " 토탈 엉덩이 공식 : (waist+waist2+waist3+waist4+waist5)/5;" + total_hip);
            textView_hip.setText(String.valueOf(total_hip));
            // 스키니,와이드, 일자, 하이웨스트
            textView_hip1.setText(String.valueOf(skiny_hip));
            textView_hip2.setText(String.valueOf(wide_hip));
            textView_hip3.setText(String.valueOf(total_hip));
            textView_hip4.setText(String.valueOf(total_hip));


            int bugGi = sizeShared.getInt("허벅지", 0);
            int bugGi_2 = sizeShared.getInt("허벅지2", 0);
            int bugGi_3 = sizeShared.getInt("허벅지3", 0);
            int bugGi_4 = sizeShared.getInt("허벅지4", 0);
            int bugGi_5 = sizeShared.getInt("허벅지5", 0);
            int total_bugGi = (bugGi+bugGi_2+bugGi_3+bugGi_4+bugGi_5)/5;
            int skiny_bugGi = total_bugGi- 2;
            int wide_bugGi = total_bugGi +5;
            Log.e("[insight] sizeShared 쉐어드에서", " 허벅지 : " + bugGi);
            Log.e("[insight] sizeShared 쉐어드에서", " 허벅지 2 : " + bugGi_2);
            Log.e("[insight] sizeShared 쉐어드에서", " 허벅지 3 : " + bugGi_3);
            Log.e("[insight] sizeShared 쉐어드에서", " 허벅지 4: " + bugGi_4);
            Log.e("[insight] sizeShared 쉐어드에서", " 허벅지 5: " + bugGi_5);
            Log.e("[insight] sizeShared 쉐어드에서", " 토탈 허벅지 공식 : (waist+waist2+waist3+waist4+waist5)/5;" + total_bugGi);
            textView_bugGi.setText(String.valueOf(total_bugGi));
            // 스키니,와이드, 일자, 하이웨스트
            textView_bugGi1.setText(String.valueOf(skiny_bugGi));
            textView_bugGi2.setText(String.valueOf(wide_bugGi));
            textView_bugGi3.setText(String.valueOf(total_bugGi));
            textView_bugGi4.setText(String.valueOf(total_bugGi));


            int longlong = sizeShared.getInt("기장", 0);
            int longlong_2 = sizeShared.getInt("기장2", 0);
            int longlong_3 = sizeShared.getInt("기장3", 0);
            int longlong_4 = sizeShared.getInt("기장4", 0);
            int longlong_5 = sizeShared.getInt("기장5", 0);
            int total_longlong = (longlong+longlong_2+longlong_3+longlong_4+longlong_5)/5;
            int skiny_long = total_longlong - 1;
            int long_long = total_longlong +1;
            int high_long =total_longlong+4;
            Log.e("[insight] sizeShared 쉐어드에서", " 기장 : " + longlong);
            Log.e("[insight] sizeShared 쉐어드에서", " 기장 2 : " + longlong_2);
            Log.e("[insight] sizeShared 쉐어드에서", " 기장 3 : " + longlong_3);
            Log.e("[insight] sizeShared 쉐어드에서", " 기장 4: " + longlong_4);
            Log.e("[insight] sizeShared 쉐어드에서", " 기장 5: " + longlong_5);
            Log.e("[insight] sizeShared 쉐어드에서", " 토탈 기장 공식 : (waist+waist2+waist3+waist4+waist5)/5;" + total_longlong);
            textView_long.setText(String.valueOf(total_longlong));
            // 스키니,와이드, 일자, 하이웨스트
            textView_long1.setText(String.valueOf(skiny_long));
            textView_long2.setText(String.valueOf(long_long));
            textView_long3.setText(String.valueOf(total_longlong));
            textView_long4.setText(String.valueOf(high_long));

            int ancle = sizeShared.getInt("밑단", 0);
            int ancle_2 = sizeShared.getInt("밑단2", 0);
            int ancle_3 = sizeShared.getInt("밑단3", 0);
            int ancle_4 = sizeShared.getInt("밑단4", 0);
            int ancle_5 = sizeShared.getInt("밑단5", 0);
            int total_ancle = (ancle+ancle_2+ancle_3+ancle_4+ancle_5)/5;
            int skiny_ancle = total_ancle -3;
            int wide_ancle = total_ancle +4;
            int straight_ancle = total_ancle +2;
            Log.e("[insight] sizeShared 쉐어드에서", " 밑단 : " + ancle);
            Log.e("[insight] sizeShared 쉐어드에서", " 밑단 2 : " + ancle_2);
            Log.e("[insight] sizeShared 쉐어드에서", " 밑단 3 : " + ancle_3);
            Log.e("[insight] sizeShared 쉐어드에서", " 밑단 4: " + ancle_4);
            Log.e("[insight] sizeShared 쉐어드에서", " 밑단 5: " + ancle_5);
            Log.e("[insight] sizeShared 쉐어드에서", " 토탈 밑단 공식 : (waist+waist2+waist3+waist4+waist5)/5;" + total_ancle);
            textView_ancle.setText(String.valueOf(total_ancle));
            // 스키니,와이드, 일자, 하이웨스트
            textView_ancle1.setText(String.valueOf(skiny_ancle));
            textView_ancle2.setText(String.valueOf(wide_ancle));
            textView_ancle3.setText(String.valueOf(straight_ancle));
            textView_ancle4.setText(String.valueOf(total_ancle));


        }



//  혹시 모르니까 메소드로 만들어놓음 -> 저장된 값을 불러와서 원키 원벨류하는 중
    // startActivityForResult 하고 난 다음에 Shared에 저장해줄 것
    public void something_to_load (){
        sizeShared = getSharedPreferences("sizeShared", Context.MODE_PRIVATE);
        sizeShared_editor = sizeShared.edit();

//                SharedPreferences logined_user = getSharedPreferences("logined_user", Context.MODE_PRIVATE);   // 현재 로그인한 회원의 정보만 담겨있는 쉐어드를 불러와서
//                final String feed_id = logined_user.getString("user_nickname", "");
        String json = sizeShared.getString(feed_id, "");
        Log.e("login 클래스에서 로그인 버튼을 눌렀을 때", "여기 확인하기 : " + json);

        // editText 로 입력한 이메일이 null 값이 아니면 = 무엇인가를 입력하면 -> 일단 쉐어드에 사용자가 입력한 이메일이 있는지 확인
        if (json != null) {

            try {

                JSONArray jsonArray = new JSONArray(json);
                for (int i = 0; i < jsonArray.length(); i++) {

                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    Log.e("login 회원정보 확인", "sharedPreferences에서 저장된 array(string으로 저장됐던) 가져오기 : " + jsonObject);

//                    String namename1 = jsonObject.getString("바지이름");
//                    Log.e("login 회원정보 확인", "sharedPreferences에서 저장된 array(string으로 저장됐던) 가져오기 : " + namename1);
                    waistwaist1 = jsonObject.getInt("허리");
                    Log.e("insight", "sharedPreferences에서 저장된 가져오기 waistwaist1 : " + waistwaist1);
                    serosero1 = jsonObject.getInt("밑위");
                    Log.e("insight", "sharedPreferences에서 저장된 가져오기 serosero1 : " + serosero1);
                    hiphip1 = jsonObject.getInt("엉덩이");
                    Log.e("insight", "sharedPreferences에서 저장된 가져오기 hiphip1 : " + hiphip1);
                    bugGibugGi1 = jsonObject.getInt("허벅지");
                    Log.e("insight", "sharedPreferences에서 저장된 가져오기 bugGibugGi1 : " + bugGibugGi1);
                    longlong1 = jsonObject.getInt("기장");
                    Log.e("insight", "sharedPreferences에서 저장된 가져오기 longlong1 : " + longlong1);
                    ancleancle1 = jsonObject.getInt("밑단");
                    Log.e("insight", "sharedPreferences에서 저장된 가져오기 ancleancle1 : " + ancleancle1);

//                    String namename2 = jsonObject.getString("바지이름2");
//                    Log.e("login 회원정보 확인", "sharedPreferences에서 저장된 array(string으로 저장됐던) 가져오기 : " + namename2);
                    waistwaist2 = jsonObject.getInt("허리2");
                    Log.e("insight", "sharedPreferences에서 저장된 가져오기 waistwaist2 : " + waistwaist2);
                    serosero2 = jsonObject.getInt("밑위2");
                    Log.e("insight", "sharedPreferences에서 저장된 가져오기 serosero2 : " + serosero2);
                    hiphip2 = jsonObject.getInt("엉덩이2");
                    Log.e("insight", "sharedPreferences에서 저장된 가져오기 hiphip2 : " + hiphip2);
                    bugGibugGi2 = jsonObject.getInt("허벅지2");
                    Log.e("insight", "sharedPreferences에서 저장된 가져오기 bugGibugGi2 : " + bugGibugGi2);
                    longlong2 = jsonObject.getInt("기장2");
                    Log.e("insight", "sharedPreferences에서 저장된 가져오기 longlong2 : " + longlong2);
                    ancleancle2 = jsonObject.getInt("밑단2");
                    Log.e("insight", "sharedPreferences에서 저장된 가져오기 ancleancle2 : " + ancleancle2);

//                    String namename3 = jsonObject.getString("바지이름3");
//                    Log.e("login 회원정보 확인", "sharedPreferences에서 저장된 array(string으로 저장됐던) 가져오기 : " + namename3);
                    waistwaist3 = jsonObject.getInt("허리3");
                    Log.e("insight", "sharedPreferences에서 저장된 가져오기 waistwaist3 : " + waistwaist3);
                    serosero3 = jsonObject.getInt("밑위3");
                    Log.e("insight", "sharedPreferences에서 저장된 가져오기 serosero3 : " + serosero3);
                    hiphip3 = jsonObject.getInt("엉덩이3");
                    Log.e("insight", "sharedPreferences에서 저장된 가져오기 hiphip3 : " + hiphip3);
                    bugGibugGi3 = jsonObject.getInt("허벅지3");
                    Log.e("insight", "sharedPreferences에서 저장된 가져오기 bugGibugGi3 : " + bugGibugGi3);
                    longlong3 = jsonObject.getInt("기장3");
                    Log.e("insight", "sharedPreferences에서 저장된 가져오기 longlong3 : " + longlong3);
                    ancleancle3 = jsonObject.getInt("밑단3");
                    Log.e("insight", "sharedPreferences에서 저장된 가져오기 ancleancle3 : " + ancleancle3);

//                    String namename4 = jsonObject.getString("바지이름4");
//                    Log.e("login 회원정보 확인", "sharedPreferences에서 저장된 array(string으로 저장됐던) 가져오기 : " + namename4);
                    waistwaist4 = jsonObject.getInt("허리4");
                    Log.e("insight", "sharedPreferences에서 저장된 가져오기 waistwaist4 : " + waistwaist4);
                    serosero4 = jsonObject.getInt("밑위4");
                    Log.e("insight", "sharedPreferences에서 저장된 가져오기 serosero4 : " + serosero4);
                    hiphip4 = jsonObject.getInt("엉덩이4");
                    Log.e("insight", "sharedPreferences에서 저장된 가져오기 hiphip4 : " + hiphip4);
                    bugGibugGi4 = jsonObject.getInt("허벅지4");
                    Log.e("insight", "sharedPreferences에서 저장된 가져오기 bugGibugGi4 : " + bugGibugGi4);
                    longlong4 = jsonObject.getInt("기장4");
                    Log.e("insight", "sharedPreferences에서 저장된 가져오기 longlong4 : " + longlong4);
                    ancleancle4 = jsonObject.getInt("밑단4");
                    Log.e("insight", "sharedPreferences에서 저장된 가져오기 ancleancle4 : " + ancleancle4);

//                    String namename5 = jsonObject.getString("바지이름5");
//                    Log.e("login 회원정보 확인", "sharedPreferences에서 저장된 array(string으로 저장됐던) 가져오기 : " + namename5);
                    waistwaist5 = jsonObject.getInt("허리5");
                    Log.e("insight", "sharedPreferences에서 저장된 가져오기 waistwaist5 : " + waistwaist5);
                    serosero5 = jsonObject.getInt("밑위5");
                    Log.e("insight", "sharedPreferences에서 저장된 가져오기 serosero5 : " + serosero5);
                    hiphip5 = jsonObject.getInt("엉덩이5");
                    Log.e("insight", "sharedPreferences에서 저장된 가져오기 hiphip5 : " + hiphip5);
                    bugGibugGi5 = jsonObject.getInt("허벅지5");
                    Log.e("insight", "sharedPreferences에서 저장된 가져오기 bugGibugGi5 : " + bugGibugGi5);
                    longlong5 = jsonObject.getInt("기장5");
                    Log.e("insight", "sharedPreferences에서 저장된 가져오기 longlong5 : " + longlong5);
                    ancleancle5 = jsonObject.getInt("밑단5");
                    Log.e("insight", "sharedPreferences에서 저장된 가져오기 ancleancle5 : " + ancleancle5);

                }



//jsonObject.put("바지이름", name1);
//                    jsonObject.put("허리", waist1);
//                    jsonObject.put("밑위", sero1);
//                    jsonObject.put("엉덩이", hip1);
//                    jsonObject.put("허벅지", bugGi1);
//                    jsonObject.put("기장", long1);
//                    jsonObject.put("밑단", ancle1);


// 로그인할 때 로그인한 회원의 정보를 배열로 가지고 와서 추출 후 각각의 key값을 줘서 저장했던 value를 호출
                sizeShared_editor.putString( "바지이름", namename1);  // 회원가입시 입력한 이메일이 각 arrayList의 key 값이 됨.
                sizeShared_editor.putInt("허리", waistwaist1);  // 회원가입시 입력한 비밀번호가 각 arrayList의 key 값이 됨.
                sizeShared_editor.putInt("밑위", serosero1);  // 회원가입시 입력한 평소 사이즈가 각 arrayList의 key 값이 됨.
                sizeShared_editor.putInt("엉덩이", hiphip1);  // 회원가입시 입력한 닉네임이 각 arrayList의 key 값이 됨.
                sizeShared_editor.putInt("허벅지", bugGibugGi1);  // 회원가입시 입력한 프로필 이미지이 각 arrayList의 key 값이 됨.
                sizeShared_editor.putInt("기장", longlong1);  // 회원가입시 입력한 닉네임이 각 arrayList의 key 값이 됨.
                sizeShared_editor.putInt("밑단", ancleancle1);  // 회원가입시 입력한 프로필 이미지이 각 arrayList의 key 값이 됨.

                sizeShared_editor.putString( "바지이름2", namename2);  // 회원가입시 입력한 이메일이 각 arrayList의 key 값이 됨.
                sizeShared_editor.putInt("허리2", waistwaist2);  // 회원가입시 입력한 비밀번호가 각 arrayList의 key 값이 됨.
                sizeShared_editor.putInt("밑위2", serosero2);  // 회원가입시 입력한 평소 사이즈가 각 arrayList의 key 값이 됨.
                sizeShared_editor.putInt("엉덩이2", hiphip2);  // 회원가입시 입력한 닉네임이 각 arrayList의 key 값이 됨.
                sizeShared_editor.putInt("허벅지2", bugGibugGi2);  // 회원가입시 입력한 프로필 이미지이 각 arrayList의 key 값이 됨.
                sizeShared_editor.putInt("기장2", longlong2);  // 회원가입시 입력한 닉네임이 각 arrayList의 key 값이 됨.
                sizeShared_editor.putInt("밑단2", ancleancle2);  // 회원가입시 입력한 프로필 이미지이 각 arrayList의 key 값이 됨.

                sizeShared_editor.putString( "바지이름3", namename3);  // 회원가입시 입력한 이메일이 각 arrayList의 key 값이 됨.
                sizeShared_editor.putInt("허리3", waistwaist3);  // 회원가입시 입력한 비밀번호가 각 arrayList의 key 값이 됨.
                sizeShared_editor.putInt("밑위3", serosero3);  // 회원가입시 입력한 평소 사이즈가 각 arrayList의 key 값이 됨.
                sizeShared_editor.putInt("엉덩이3", hiphip3);  // 회원가입시 입력한 닉네임이 각 arrayList의 key 값이 됨.
                sizeShared_editor.putInt("허벅지3", bugGibugGi3);  // 회원가입시 입력한 프로필 이미지이 각 arrayList의 key 값이 됨.
                sizeShared_editor.putInt("기장3", longlong3);  // 회원가입시 입력한 닉네임이 각 arrayList의 key 값이 됨.
                sizeShared_editor.putInt("밑단3", ancleancle3);  // 회원가입시 입력한 프로필 이미지이 각 arrayList의 key 값이 됨.

                sizeShared_editor.putString( "바지이름4", namename4);  // 회원가입시 입력한 이메일이 각 arrayList의 key 값이 됨.
                sizeShared_editor.putInt("허리4", waistwaist4);  // 회원가입시 입력한 비밀번호가 각 arrayList의 key 값이 됨.
                sizeShared_editor.putInt("밑위4", serosero4);  // 회원가입시 입력한 평소 사이즈가 각 arrayList의 key 값이 됨.
                sizeShared_editor.putInt("엉덩이4", hiphip4);  // 회원가입시 입력한 닉네임이 각 arrayList의 key 값이 됨.
                sizeShared_editor.putInt("허벅지4", bugGibugGi4);  // 회원가입시 입력한 프로필 이미지이 각 arrayList의 key 값이 됨.
                sizeShared_editor.putInt("기장4", longlong4);  // 회원가입시 입력한 닉네임이 각 arrayList의 key 값이 됨.
                sizeShared_editor.putInt("밑단4", ancleancle4);  // 회원가입시 입력한 프로필 이미지이 각 arrayList의 key 값이 됨.

                sizeShared_editor.putString( "바지이름5", namename5);  // 회원가입시 입력한 이메일이 각 arrayList의 key 값이 됨.
                sizeShared_editor.putInt("허리5", waistwaist5);  // 회원가입시 입력한 비밀번호가 각 arrayList의 key 값이 됨.
                sizeShared_editor.putInt("밑위5", serosero5);  // 회원가입시 입력한 평소 사이즈가 각 arrayList의 key 값이 됨.
                sizeShared_editor.putInt("엉덩이5", hiphip5);  // 회원가입시 입력한 닉네임이 각 arrayList의 key 값이 됨.
                sizeShared_editor.putInt("허벅지5", bugGibugGi5);  // 회원가입시 입력한 프로필 이미지이 각 arrayList의 key 값이 됨.
                sizeShared_editor.putInt("기장5", longlong5);  // 회원가입시 입력한 닉네임이 각 arrayList의 key 값이 됨.
                sizeShared_editor.putInt("밑단5", ancleancle5);  // 회원가입시 입력한 프로필 이미지이 각 arrayList의 key 값이 됨.





                sizeShared_editor.commit();   //제출


                String size_jsondata = jsonArray.toString();  // jsonArray를 String값으로 바꿈. String으로 바꾼 jsonArray를 user_jsondata라고 이름붙임.
                Log.e("login 클래스에서 로그인 버튼을 눌렀을 때", "user_jsondata 확인 중 : " + size_jsondata);
                save_in_Shared(size_jsondata);                    // saveArrayList 메소드를 실행할건데 josndata를 사용할 것 -> onCreate 밖에 메소드 만듦.
                Log.e("login 클래스에서 로그인 버튼을 눌렀을 때", "user_writeArrayList(user_jsondata) 확인중 : " + size_jsondata);
//                finish();
//                overridePendingTransition(0,0);
//                startActivity(getIntent());
//                overridePendingTransition(0,0);
/////////////////////////////

            } catch (JSONException e) {
                // editText 로 입력한 이메일이 null 값이 아니면 = 무엇인가를 입력하면 -> 일단 쉐어드에 사용자가 입력한 이메일이 있는지 확인했는데 없음
                e.printStackTrace();
//                            Toast.makeText(login.this, "아이디 정보가 없습니다", Toast.LENGTH_SHORT).show();
//                Toast.makeText(insight.this, "사이즈 데이터를 찾을 수 없습니다", Toast.LENGTH_SHORT).show();
            }


        }
//하단바



    }




    public void save_in_Shared(String jsondata) {


        if (data != null) {

            // JSONArray 생성
            sizeShared = getSharedPreferences("sizeShared", Context.MODE_PRIVATE);
            sizeShared_editor = sizeShared.edit();

//            logined_user = getSharedPreferences("logined_user", Context.MODE_PRIVATE);   // 현재 로그인한 회원의 정보만 담겨있는 쉐어드를 불러와서
//            feed_id = logined_user.getString("user_email", "");
//            Log.e("[피드] 로그인한 회원 정보가 있는 쉐어드에서", " 현재 로그인한 유저의 이메일 : " + feed_id);


            for (int i = 0; i < data.size(); i++) {

                // 리스트 아이템 하나씩 JSONArray 배열에 추가

                JSONArray array = new JSONArray();
                array.put(data.get(i));

                Log.e("회원정보 확인하는 메소드","확인중" + array.put(data.get(i)));
            }
//            for(comment_Data item : commentArrayList){// item이라는 comment Data를 잡고 commentArrayList 안을 순회하면서 비교하면서
//                if(item.getReviewUniqueCode().equals(testkey)){ // if문 확인하면서 commnetData 가 갖고있는 아이템에서
////            if(item.getReviewUniqueCode().equals(position)){
//                    comment_show_arrayList.add(item);
//                }


            sizeShared_editor.putString(feed_id, jsondata);  // 회원가입시 입력한 email이 각 arrayList의 key 값이 됨.
//            Log.e("saveArrayList 메소드","확인중" + editor.putString(feed_id,jsondata));
            sizeShared_editor.commit();
            Log.e("saveArrayList 메소드","ArrayList인 jsonData를 String 형태로 sharedPreference에 저장했습니다 ");
            Log.d("saveArrayList 메소드[확인 중]","새로운 키에 저장되는지 원래 있던 키에 저장되는지 확인 필요");
//
//            finish();
//            overridePendingTransition(0,0);
//            startActivity(getIntent());
//            overridePendingTransition(0,0);
        }
    }





    @Override
    protected void onRestart() {
        super.onRestart();
        Log.e("follow","onRestart");
        //액티비티가 중단되었다가 다시 시작
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.e("follow","onStart");
        //액티비티가 화면에 나타나기 시작
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e("follow","onResume");
        //액티비티가 화면에 나타나고 상호작용이 가능해짐
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.e("follow","onPause");
        overridePendingTransition(0, 0);
        //다른 액티비티가 시작되려함, 이 액티비티는 중단되려하고 백그라운드로 들어갑니다.
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.e("follow","onStop");
        //액티비티가 더 이상 화면에 나타나지 않음,중단된 상태
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e("follow","onDestroy");
        //액티비티가 종료되려고 합니다.
    }

}
