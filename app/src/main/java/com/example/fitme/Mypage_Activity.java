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
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.callback.LogoutResponseCallback;
import com.squareup.picasso.Picasso;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class Mypage_Activity extends AppCompatActivity {
    /**
     * 마이페이지 (내가 쓴 리뷰 미리보기 -> 리사이클러뷰) - 액티비티
     **/

// 카카오톡으로 로그인한 정보 가지고 오는 중
    String email = "";
    String nickname = "";
    String profile_imagePath = "";

    ImageView imageView_mypage_profileimage ;


    // 현재 로그인하고 있는 회원의 정보만 저장하는 쉐어드
    private SharedPreferences logined_user;
    private SharedPreferences.Editor user_editor;

    // 북마크한 리뷰를 저장하는 쉐어드
    private SharedPreferences bookmarkShared;
    private SharedPreferences.Editor bookmarkShared_editor;

    private SharedPreferences myreviewShared;
    private SharedPreferences.Editor myreviewShared_editor;

//mypage_recyclerview
    private ArrayList<List> userData= new ArrayList<List>();
    /**
     * 리사이클러뷰
     **/

    ArrayList<Feed_Main_ItemData> mypage_arrayList, arrayList, bookmarked_arrayList, myreview_arrayList;
    Mypage_Adapter mypage_adapter;
    RecyclerView mypage_recyclerview;
    GridLayoutManager GridlayoutManager;



    ViewGroup linearLayout6, linearLayout7; // 내 리뷰 :linearLayout6, 북마크 : linearLayout7, 팔로우 : linearLayout5, 팔로잉 linearLayout3
    Button  button_edit_profile ;
    BottomNavigationView bottomNavigationView; // 바텀 네이게이션 메뉴  -> 하단바
    TextView textview_myreview_nickname,textView_mypage_email, textView_nickname, textView_myreview_count,textView_bookmark_count,textView_follow,textView_following;
    ImageButton imageButton_mypage_menu;
    Uri uri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.e("Mypage_Activity","onCreate");

        loadShared();
        mypage_myreview_loadData(); // 마이페이지에서 보여줄 Grid 형식의 리사이클러뷰

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mypage);

        /**리사이클러뷰**/

        mypage_recyclerview = (RecyclerView) findViewById(R.id.mypage_recyclerview);
        GridlayoutManager = new GridLayoutManager(getApplicationContext(), 3); // 3칸으로 나눔

        // 피드 리사이클러뷰 맨 위부터 보여주기
        mypage_recyclerview.post(new Runnable() {

            @Override

            public void run() {

                mypage_recyclerview.scrollToPosition(mypage_recyclerview.getAdapter().getItemCount() - 1);

            }

        });
        GridlayoutManager.setReverseLayout(false); // 왼쪽 상위부터 정렬로 리사이클러뷰 아이템 추가.

//        recyclerView.setHasFixedSize(true);//각 아이템이 보여지는 것을 일정하게

        mypage_recyclerview.setLayoutManager(GridlayoutManager);
        mypage_adapter = new Mypage_Adapter(mypage_arrayList, this);//앞서 만든 리스트를 어뎁터에 적용시켜 객체를 만든다.
        mypage_recyclerview.setAdapter(mypage_adapter);// 그리고 만든 객체를 리싸이클러뷰에 적용시킨다.


        // 피드 리사이클러뷰 맨 위부터 보여주기
        mypage_recyclerview.post(new Runnable() {

            @Override

            public void run() {

                mypage_recyclerview.scrollToPosition(mypage_recyclerview.getAdapter().getItemCount() - 1);

            }

        });

        mypage_adapter.setOnItemClickListener(new Mypage_Adapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, final int position) {

                // 마이페이지에서 내가 쓴 리뷰 이미지 그리드 리사이클러뷰를 아이템을 눌렀을때
                /// 내가 쓴 리뷰 액티비티로 이동하고 해당 위치에 있는 아이템으로 스크롤 되게 해주기.
                Intent intent = new Intent(getApplicationContext(), Myreview_Activity.class);

                intent.putExtra("position",position); /*송신*/


                startActivity(intent);



            }
            });


        /**마이페이지의 로그인한 회원의 정보 넣기 : 이메일, 닉네임, 프로필 사진**/

        logined_user = getSharedPreferences("logined_user", Context.MODE_PRIVATE);   // 현재 로그인한 회원의 정보만 담겨있는 쉐어드를 불러와서

        // 이메일
        String mypage_email = logined_user.getString("user_email", "");
        Log.e("[마이페이지] 로그인 쉐어드에서", " 현재 로그인한 유저의 이메일 넣기 : " + mypage_email);
        textView_mypage_email = findViewById(R.id.textView_mypage_email);
        textView_mypage_email.setText(mypage_email);

        // 닉네임
        String mypage_nickname = logined_user.getString("user_nickname", "");
        Log.e("[마이페이지] 로그인 쉐어드에서", " 현재 로그인한 유저의 닉네임 넣기 : " + mypage_nickname);
        textView_nickname = findViewById(R.id.textView_nickname);
        textview_myreview_nickname= findViewById(R.id.textview_myreview_nickname);
        textView_nickname.setText(mypage_nickname);
        textview_myreview_nickname.setText(mypage_nickname);

        // 프로필 사진

//        String uri = logined_user.getString("user_profileimage", "");
//        imageView_mypage_profileimage = findViewById(R.id.imageView_mypage_profileimage);
//        imageView_mypage_profileimage.setImageURI(Uri.parse( uri));
        imageView_mypage_profileimage = (ImageView)findViewById(R.id.imageView_mypage_profileimage);

        Intent intent = getIntent(); /*데이터 수신*/



//        user_editor.putString("user_profileimage", profile_imagePath);  //  카카오톡 로그인한 프로필 사진 경로가 로그인한 유저 쉐어드에 저장됨
//        Picasso.get()
//                .load(profile_image_path)
//                .fit()
//                .into(imageView_mypage_profileimage);

            String ImageUri = logined_user.getString("user_profileimage","");


        Picasso.get()
                .load(ImageUri)
                .fit()
                .centerInside()
                .placeholder(R.drawable.ic_person_black_24dp) // 이미지가 없을 때 기본
                .error(R.drawable.img_dd_profile)// 에러가 났을 때
                .into(imageView_mypage_profileimage);


//        Glide.with(getApplicationContext())
//                .load(ImageUri)
//                .thumbnail(0.5f)
//                .into(imageView_mypage_profileimage);


        Log.w("Mypage_Activity 클래스","ImageUri : " + ImageUri );
        Log.w("Mypage_Activity 클래스","imageView_mypage_profileimage : " + imageView_mypage_profileimage );
        Log.e("[마이페이지] 로그인 쉐어드에서", " uri : " + ImageUri);
        Log.e("[마이페이지] 로그인 쉐어드에서 ", "imageView_mypage_profileimage : " + imageView_mypage_profileimage);


//        ArrayList<Feed_Main_ItemData> arrayList, bookmarked_arrayList, myreview_arrayList;

// 지금까지 내가 쓴 리뷰는 xx 개입니다. 설정


//
//
//        logined_user = getSharedPreferences("logined_user", Context.MODE_PRIVATE);   // 현재 로그인한 회원의 정보만 담겨있는 쉐어드를 불러와서
//        String feed_id = logined_user.getString("user_nickname", "");
//
//        myreviewShared  = getSharedPreferences("myreviewShared", Context.MODE_PRIVATE);
//        int myreview_arrayList_size = myreviewShared.getInt(feed_id,0);

        myreview_loadData();  // 쉐어드에 저장된 내가 쓴 리뷰 arrayList를 가져옴
        loadData();   // 쉐어드에 저장된 피드 arrayList를 가져옴
        bookmarked_loadData();  // 쉐어드에 저장된 북마크한 리뷰 arrayList를 가져옴


        textView_myreview_count = findViewById(R.id.textView_myreview_count);
        int myreview_count = myreview_arrayList.size();
        Log.d("bookmark_count"," : " + myreview_count );
        textView_myreview_count.setText(String.valueOf(myreview_count));
        Log.d("textView_myreview_count"," : " + textView_myreview_count );


// 지금까지 북마크한 쓴 리뷰는 xx 개입니다. 설정
        textView_bookmark_count = findViewById(R.id.textView_bookmark_count);
        int bookmark_count = bookmarked_arrayList.size();
        Log.d("bookmark_count"," : " + bookmark_count );
        textView_bookmark_count.setText(String.valueOf(bookmark_count));
        Log.d("textView_howmany_bookmarked_reviews"," : " + textView_bookmark_count );

        /**여기에 팔로우, 팔로잉**/


// 내가 쓴 리뷰 레이아웃을 클릭하면 -> 내가 쓴 리뷰 화면으로 전환
        linearLayout6 = (ViewGroup) findViewById(R.id.linearLayout6);
        linearLayout6 .setOnClickListener(new ImageView.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(Mypage_Activity.this, Myreview_Activity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent); //액티비티 이동
            }
        });

// 북마크한 리뷰 레이아웃을 클릭하면 -> 북마크한 리뷰 화면으로 전환
        linearLayout7 = (ViewGroup) findViewById(R.id.linearLayout7);
        linearLayout7 .setOnClickListener(new ImageView.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(Mypage_Activity.this, Bookmarked_review_Activity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent); //액티비티 이동
            }
        });

//// 팔로우 리뷰 레이아웃을 클릭하면 -> 팔로우 화면으로 전환
//
//        linearLayout5 = (ViewGroup) findViewById(R.id.linearLayout5);
//        linearLayout5 .setOnClickListener(new ImageView.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                Intent intent = new Intent(Mypage_Activity.this, follow.class);
//                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
//                startActivity(intent); //액티비티 이동
//            }
//        });
//
//// 팔로우 리뷰 레이아웃을 클릭하면 -> 팔로우 화면으로 전환
//
//        linearLayout3 = (ViewGroup) findViewById(R.id.linearLayout3);
//        linearLayout3 .setOnClickListener(new ImageView.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                Intent intent = new Intent(Mypage_Activity.this, following.class);
//                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
//                startActivity(intent); //액티비티 이동
//            }
//        });



        // 프로필 수정
        button_edit_profile  = findViewById(R.id.button_edit_profile );
        button_edit_profile .setOnClickListener(new ImageView.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent register_intent = new Intent(Mypage_Activity.this, Edit_Profile.class);
                register_intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
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

                                onClickLogout();

                                // 그리고 로그인 화면으로 이동
                                Intent intent = new Intent(Mypage_Activity.this, Login.class);
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
                        Intent home_intent = new Intent(Mypage_Activity.this, Feed_Main_Activity.class);
                        home_intent.addFlags (Intent.FLAG_ACTIVITY_NO_ANIMATION);
                        startActivity(home_intent);//액티비티 띄우기
//                        startActivityForResult(intent,sub);//액티비티 띄우기
                        break;
                    case R.id.action_search :
                        Intent search_intent = new Intent(Mypage_Activity.this, Review_searching_Activity.class);
                        search_intent.addFlags (Intent.FLAG_ACTIVITY_NO_ANIMATION);
                        startActivity(search_intent);//액티비티 띄우기
                        break;
                    case R.id.action_insight :
                        Intent write_intent = new Intent(Mypage_Activity.this, Size_Recommendation_Activity.class);
                        write_intent.addFlags (Intent.FLAG_ACTIVITY_NO_ANIMATION);
                        startActivity(write_intent);//액티비티 띄우기
                        break;
                    case R.id.action_shop:
                        Intent insight_intent = new Intent(Mypage_Activity.this, Naver_Search_Shop_Main.class);
                        insight_intent.addFlags (Intent.FLAG_ACTIVITY_NO_ANIMATION);
                        startActivity(insight_intent);//액티비티 띄우기
                        break;
                    case R.id.action_mypage :
                        Intent mycloset_intent = new Intent(Mypage_Activity.this, Mypage_Activity.class);
                        mycloset_intent.addFlags (Intent.FLAG_ACTIVITY_NO_ANIMATION);
                        startActivity(mycloset_intent);//액티비티 띄우기
                        break;
                }

                return false;
            }
        });

    }//onCreate 닫는 중괄호




    //카카오톡 로그인 하는 중
    private void onClickLogout() {
        UserManagement.getInstance().requestLogout(new LogoutResponseCallback() {
            @Override
            public void onCompleteLogout() {
                redirectLoginActivity();
            }
        });
    }
    protected void redirectLoginActivity() {
        final Intent intent = new Intent(this, Login.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(intent);
        finish();
    }
//
    private void bookmarked_loadData() {
//        SharedPreferences sharedPreferences = getSharedPreferences("sharedPreferences", MODE_PRIVATE);
        SharedPreferences bookmarkShared = getSharedPreferences("bookmarkShared", MODE_PRIVATE);
        Gson gson = new Gson();

        // 로그인 하고 있는 사용자의 이메일을 키값으로 갖는 value에
        logined_user = getSharedPreferences("logined_user", Context.MODE_PRIVATE);   // 현재 로그인한 회원의 정보만 담겨있는 쉐어드를 불러와서
        String feed_email = logined_user.getString("user_email", "");
        Log.e("Feed_Main_Activity 클래스 (bookmarked_loadData)", "로그인한 유저의 이메일 호출 : " + feed_email);

        String json = bookmarkShared.getString(feed_email, null);
        Type type = new TypeToken<ArrayList<Feed_Main_ItemData>>() {
        }.getType();
        Log.e("Feed_Main_Activity 클래스 (bookmarked_loadData)", "typeToken객체 생성 :" + type);
        bookmarked_arrayList = gson.fromJson(json, type);
        Log.e("Feed_Main_Activity 클래스 (bookmarked_loadData)", "fromJson : arryaList(bookmarked_arrayList)는 " + bookmarked_arrayList);


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
        Log.e("Feed_Main_Activity 클래스", "Gson 객체 호출 : " + gson);

        String json = gson.toJson(bookmarked_arrayList);  // 여기서 arrayList는 피드에 들어가는 리사이클러뷰를 담은 arrayList 이름임.
        // 근데 여기서 이 arrayList에 넣어주면 사용자 이메일에 넣어줬을 때 .. 사용자 마다 다른 arrayList를 갖게 되는건가?
        // 이 메소드 안에서 arrayList를 객체 선언해주면 매번 초기화 되서 사용자마다 다른 arrayList를 갖을 수 있는건가?

        Log.e("Feed_Main_Activity 클래스", "Gson 객체 호출 (toJson(bookmarked_arrayList) : " + json);


        // 로그인 하고 있는 사용자의 이메일을 키값으로 갖는 value에
        logined_user = getSharedPreferences("logined_user", Context.MODE_PRIVATE);   // 현재 로그인한 회원의 정보만 담겨있는 쉐어드를 불러와서
        String feed_email = logined_user.getString("user_email", "");

        // 여기 원래 feed_email이 키값이었음 -> 근데 그렇게 하면 그 사용자의 모든 정보가 bookmarked_arrayList로 덮어씌워짐
        //sharedPreference 쉐어드-> 로그인 하고 있는 사용자의 이메일을 키값으로 갖는 value에 bookmarked_arrayList를 String으로 변환한 값을 넣어줌.
        bookmarkShared_editor.putString(feed_email, json);   // fromJson할 때도 "feed_recyclerview" 맞춰줌. // 로그인한 유저의 이메일을 키값으로 json 데이터를 넣어줌.
        Log.e("Feed_Main_Activity 클래스", "Gson 객체 호출 (키 , 들어간 값) : " + feed_email + "," + json);

        bookmarkShared_editor.apply();
        Log.e("Feed_Main_Activity 클래스", "editor. apply 성공 ");


        // 그럼 현재 로그인하고 있는 유저의 데이터를 담고 있는 쉐어드에도 넣어줘야하나?
//        logined_user = getSharedPreferences("logined_user", Context.MODE_PRIVATE);
//        user_editor = logined_user.edit();
//
//
//// 로그인할 때 로그인한 회원의 정보를 배열로 가지고 와서 추출 후 각각의 key값을 줘서 저장했던 value를 호출
//        user_editor.putString("user_bookmarkList", json);  // 회원가입시 입력한 이메일이 각 arrayList의 key 값이 됨.


    }//bookmarked_saveData 메소드 닫는 중괄호

    private void mypage_myreview_loadData() {

        SharedPreferences myreviewShared = getSharedPreferences("myreviewShared", MODE_PRIVATE);
        Gson gson = new Gson();


        // 로그인 하고 있는 사용자의 이메일을 키값으로 갖는 value에
        logined_user = getSharedPreferences("logined_user", Context.MODE_PRIVATE);   // 현재 로그인한 회원의 정보만 담겨있는 쉐어드를 불러와서
        String feed_email = logined_user.getString("user_email", "");
        Log.e("Feed_Main_Activity 클래스 ", "로그인한 유저의 이메일 호출 : " + feed_email);

        String json = myreviewShared.getString(feed_email, null);
        Type type = new TypeToken<ArrayList<Feed_Main_ItemData>>() {
        }.getType();

        Log.e("Feed_Main_Activity 클래스 (myreview_loadData)", "typeToken객체 생성 :" + type);

        mypage_arrayList = gson.fromJson(json, type);
        Log.e("Feed_Main_Activity 클래스 (myreview_loadData)", "fromJson : arryaList(myreview_arrayList)는 " + mypage_arrayList);

//
        if (mypage_arrayList == null) {
            mypage_arrayList = new ArrayList<>();
        }


    } // myreview_loadData 메소드 닫는 중괄호

    private void myreview_saveData() {

//
        SharedPreferences myreviewShared = getSharedPreferences("myreviewShared", MODE_PRIVATE);
        SharedPreferences.Editor myreviewShared_editor = myreviewShared.edit();
        Gson gson = new Gson();
        Log.e("Myreview_Activity 클래스", "Gson 객체 호출 : " + gson);

        System.out.println("myReview arrayList.size : " + myreview_arrayList.size());

        String json = gson.toJson(myreview_arrayList);  // 여기서 arrayList는 피드에 들어가는 리사이클러뷰를 담은 arrayList 이름임.

        Log.e("Myreview_Activity 클래스", "Gson 객체 호출 (toJson(myreview_arrayList) : " + json);


        // 로그인 하고 있는 사용자의 이메일을 키값으로 갖는 value에
        logined_user = getSharedPreferences("logined_user", Context.MODE_PRIVATE);   // 현재 로그인한 회원의 정보만 담겨있는 쉐어드를 불러와서
        String feed_email = logined_user.getString("user_email", "");

        myreviewShared_editor.putString(feed_email, json);   // fromJson할 때도 "feed_recyclerview" 맞춰줌. // 로그인한 유저의 이메일을 키값으로 json 데이터를 넣어줌.
        Log.e("Myreview_Activity 클래스", "Gson 객체 호출 (키 , 들어간 값) : " + feed_email + "," + json);

        myreviewShared_editor.apply();
        Log.e("Myreview_Activity 클래스", "editor. apply 성공 ");


    }

    // sharedPreference에 저장한 ArrayList 를 가져옴 (리사이클러뷰)
    private void myreview_loadData() {

        SharedPreferences myreviewShared = getSharedPreferences("myreviewShared", MODE_PRIVATE);
        Gson gson = new Gson();


        // 로그인 하고 있는 사용자의 이메일을 키값으로 갖는 value에
        logined_user = getSharedPreferences("logined_user", Context.MODE_PRIVATE);   // 현재 로그인한 회원의 정보만 담겨있는 쉐어드를 불러와서
        String feed_email = logined_user.getString("user_email", "");
        Log.e("Feed_Main_Activity 클래스 ", "로그인한 유저의 이메일 호출 : " + feed_email);

        String json = myreviewShared.getString(feed_email, null);
        Type type = new TypeToken<ArrayList<Feed_Main_ItemData>>() {
        }.getType();

        Log.e("Feed_Main_Activity 클래스 (myreview_loadData)", "typeToken객체 생성 :" + type);

        myreview_arrayList = gson.fromJson(json, type);
        Log.e("Feed_Main_Activity 클래스 (myreview_loadData)", "fromJson : arryaList(myreview_arrayList)는 " + myreview_arrayList);

//
        if (myreview_arrayList == null) {
            myreview_arrayList = new ArrayList<>();
        }


    } // myreview_loadData 메소드 닫는 중괄호
    // sharedPreference에 저장한 ArrayList 를 가져옴 (리사이클러뷰)
    private void loadData() {
        SharedPreferences sharedPreferences = getSharedPreferences("sharedPreferences", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("feed_recyclerview", null);
        Type type = new TypeToken<ArrayList<Feed_Main_ItemData>>() {
        }.getType();
        Log.e("Feed_Main_Activity 클래스", "typeToken객체 생성 :" + type);
        arrayList = gson.fromJson(json, type);
        Log.e("Feed_Main_Activity 클래스", "fromJson : arryaList는 " + arrayList);

        if (arrayList == null) {
            arrayList = new ArrayList<>();
        }

    }


    // sharedPreference에 리사이클러뷰 안에 들어가는 arrayList를 저장하는 메소드를 만들어줌.
    private void saveData() {
        SharedPreferences sharedPreferences = getSharedPreferences("sharedPreferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        Log.e("Feed_Main_Activity 클래스", "Gson 객체 호출 : " + gson);
        String json = gson.toJson(arrayList);  // arrayList를 Json으로 바꿈 // 여기서 arrayList는 피드에 들어가는 리사이클러뷰를 담은 arrayList 이름임.
        Log.e("Feed_Main_Activity 클래스", "Gson 객체 호출 : " + json);
        editor.putString("feed_recyclerview", json);   // fromJson할 때도 "feed_recyclerview" 맞춰줌.
        Log.e("Feed_Main_Activity 클래스", "Gson 객체 호출 : " + editor.putString("feed_recyclerview", json));
        editor.apply();
        Log.e("Feed_Main_Activity 클래스", "apply 성공 ");
    }


    // 카카오톡으로 로그인한
    /*쉐어드값 불러오기*/
    private void loadShared() {
        logined_user = getSharedPreferences("logined_user", MODE_PRIVATE);
        email = logined_user.getString("user_email", "");
        nickname = logined_user.getString("user_nickname", "");
        profile_imagePath =  logined_user.getString("user_profileimage", "");
    }


    @Override
    protected void onRestart() {
        super.onRestart();
        Log.e("Mypage_Activity","onRestart");
        //액티비티가 중단되었다가 다시 시작
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.e("Mypage_Activity","onStart");
        //액티비티가 화면에 나타나기 시작
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e("Mypage_Activity","onResume");
        //액티비티가 화면에 나타나고 상호작용이 가능해짐
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.e("Mypage_Activity","onPause");
        overridePendingTransition(0, 0);
        //다른 액티비티가 시작되려함, 이 액티비티는 중단되려하고 백그라운드로 들어갑니다.
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.e("Mypage_Activity","onStop");
        //액티비티가 더 이상 화면에 나타나지 않음,중단된 상태
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e("Mypage_Activity","onDestroy");
        //액티비티가 종료되려고 합니다.
    }
}
