package com.example.fitme;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class feed extends AppCompatActivity {
    // 현재 로그인한 유저의 정보만 담는 쉐어드 프리퍼런스
    private ArrayList<List> userData = new ArrayList<List>();

    private SharedPreferences logined_user;
    private SharedPreferences.Editor user_editor;
    //
    MenuItem action_write_review; // -> 하단 바에 있는 리뷰 작성 버튼
    //    ImageButton imageButton_review_register;
    BottomNavigationView bottomNavigationView; // 바텀 네이게이션 메뉴  -> 하단바
    BottomNavigationView bottomNavigationMenu; // 바텀 네이게이션 메뉴  -> 하단바
    ImageView imageView_notification;

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    private final int Write_OK = 1001;
    /**
     * 리사이클러뷰에 필요한 기본 객체 선언
     **/
    ArrayList<feed_MainData> arrayList, bookmarked_arrayList;
    feed_Adapter feed_adapter;
    RecyclerView recyclerView;
    LinearLayoutManager linearLayoutManager;


    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        arrayList = new ArrayList<>();
        loadData();  // sharedpreference에 저장한 arrayList (리사이클러뷰)를 가지고 옴. onCreate 밖에 메소드 만들어줌

        // 피드에 들어가는 리사이클러뷰를 저장한 키값은 "feed_recyclerview"
        Log.e("feed 클래스에서(loadData)", "sharedPreference에 리사이클러뷰에 들어가는 arrayList 불러오기 :" + arrayList);


        Log.e("feed", "onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed);


        imageView_notification = findViewById(R.id.imageView_notification);  // 알림 (내 소식))

//        arrayList.add(new feed_MainData("textView_shoppingmall_url","textView_detailed_review_card"))

        /**여기서부터 리사이클러뷰 만들기**/

        recyclerView = (RecyclerView) findViewById(R.id.feed_recyclerView);

        linearLayoutManager = new LinearLayoutManager(this);

        linearLayoutManager.setReverseLayout(true); // 최신순으로 리사이클러뷰 아이템 추가.
        recyclerView.setLayoutManager(linearLayoutManager);


        feed_adapter = new feed_Adapter(arrayList);
        recyclerView.setAdapter(feed_adapter);


// Swipe를 통해서 삭제하기 위해서 ItemTouchHelper를 사용했는데 이곳에 객체 선언 -> 뒤에 ItemTouchHelper 메소드 있음.
//        new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(recyclerView);


// 리사이클러뷰 수정 // 클릭하면 수정 화면 열리고 수정한 데이터를 인텐트로 가지고 오는 것.
// 리사이클러뷰 수정에서 Adpater에서 커스텀한 클릭이벤트를 인터페이스로 가지고 와서 여기서 intent로 받아올 것.
        // 액티비티에서 커스텀 리스너 객체 생성 및 전달

//        feed_adapter.setOnItemClickListener(new feed_Adapter.OnItemClickListener() {

// 피드에 올라가는 작성글 업데이트 시간을 실제 시간으로 띄우기


        // 리사이클러뷰 아이템에 있는 우측 상단 다이얼로그 메뉴 누르는 클릭 리스너
        feed_adapter.setOnItemClickListener(new feed_Adapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, final int position) {
// TODO : 아이템 클릭 이벤트를 MainActivity에서 처리.
// 리사이클러뷰 수정
// 다이얼로그


// 리사이클러뷰 아이템 안에 버튼을 누르면 팝업 메뉴 뜨도록
                PopupMenu popup = new PopupMenu(getApplicationContext(), v);//v는 클릭된 뷰를 의미

                getMenuInflater().inflate(R.menu.reviewcard_menu, popup.getMenu());
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.action_edit:
                                // 리뷰 카드에 있는 메뉴 다이얼로그 (?) 중 수정하기를 눌렀을 때
                                Toast.makeText(getApplication(), "수정하기", Toast.LENGTH_SHORT).show();

                                Intent intent = new Intent(getApplicationContext(), edit_review.class);
                                Log.e("Feed 클래스에서 리사이클러뷰 수정 작업중! ", "edit_review로 연결되는 인텐트를 가지고왔습니다.");

//
                                intent.putExtra("URL", arrayList.get(position).textView_shoppingmall_url);
                                intent.putExtra("DETAIL", arrayList.get(position).textView_detailed_review_card);
                                intent.putExtra("POSITION", position);
                                // 위치도 받아와야 수정한 데이터를 받아왔을 때 어떤 position에 있는 아이템에 set 해줄 건지 알려줄 수 있음


                                Log.e("Feed 클래스에서 리사이클러뷰 수정 작업중! ", "URL : " + arrayList.get(position).textView_detailed_review_card);
                                Log.e("Feed 클래스에서 리사이클러뷰 수정 작업중.", "기존에 있던 데이터가 넘어가나 확인중. DETAIL : " + arrayList.get(position).textView_detailed_review_card);

                                startActivityForResult(intent, 2001);

                                Log.e("Feed 클래스에서 리사이클러뷰 수정 작업중.", "startActivityForResult를 실행. requestCode 2001");

                                //액티비티 이동, 여기서 2001은 식별자. 아무 숫자나 넣으주면 됨.


                                break;
                            case R.id.action_delete:


                                remove(position);

                                feed_adapter.notifyDataSetChanged();  // 새로고침
                                Toast.makeText(getApplication(), "삭제되었습니다", Toast.LENGTH_SHORT).show();

                                saveData();  // sharedPreference에 리뷰가 추가된 리사이클러뷰를 저장한다 // onCreate 밖에 메소드 만들었음.
                                Log.e("feed 클래스에서 (saveData)", "삭제 후   sharedpreference에 리사이클러뷰에 들어가는 arrayList 저장 :" + arrayList);


                                return true;


                            case R.id.action_share:
                                Toast.makeText(getApplication(), "공유하기", Toast.LENGTH_SHORT).show();
                                break;
                            case R.id.action_report:

                                remove(position);

                                feed_adapter.notifyDataSetChanged();  // 새로고침
                                saveData();  // sharedPreference에 리뷰가 추가된 리사이클러뷰를 저장한다 // onCreate 밖에 메소드 만들었음.
                                Log.e("feed 클래스에서 (saveData)", "신고 삭제 후 sharedpreference에 리사이클러뷰에 들어가는 arrayList 저장 :" + bookmarked_arrayList);

                                Toast.makeText(getApplication(), "신고되었습니다", Toast.LENGTH_SHORT).show();

                                break;
                            default:
                                break;
                        }
                        return false;
                    }
                });

                popup.show();//Popup Menu 보이기
//            }
//        });

            }

            @Override  // 피드 리사이클러뷰에 들어가는 리뷰 카드 아이템에서 댓글 버튼을 눌렀을 때
            public void onCommentClick(View v, int position) {
                Intent comment_intent = new Intent(feed.this, comment.class);
                startActivity(comment_intent); //액티비티 이동


            }

            @Override  // 피드 리사이클러뷰에 들어가는 리뷰 카드 아이템에서 북마크 버튼을 눌렀을 때
            public void onBookmarkClick(View v, int position) {
                // 해당 아이템이 bookmarked_review 리사이클러뷰에 추가되어야 함.
                bookmarked_arrayList = new ArrayList<>();
                // bookmarked_recyclerview 키에 들어가는 arrayList에 해당 아이템을 추가한다.

                // bookmarked_arrayList로 데이터를 보여주는 북마크한 리뷰 리사이클러뷰를 로드해라
                bookmarked_loadData();

                // 피드 리사이클러뷰의 데이터를 담는 arrayList의 해당 position을 받아(get) bookmarked_arrayList에 추가하라
                bookmarked_arrayList.add(arrayList.get(position));

                // 업데이트 한 bookmarked_arrayList를 sharedPreference에 저장하라. "bookmarked_recyclerview"
                bookmark_saveData();

                Toast.makeText(getApplication(), "북마크한 리뷰에 추가되었습니다", Toast.LENGTH_SHORT).show();

//리사이클러뷰의 arrayList에 아이템 추가

            }

        });


//하단바
        bottomNavigationView = findViewById(R.id.bottomNavi);
        // 하단바 누를 때 색 바뀌게 하는 중
        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(0);
        menuItem.setChecked(true);
        //
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                switch (menuItem.getItemId()) {
                    case R.id.action_home:  // 피드 화면으로 이동
                        Intent home_intent = new Intent(feed.this, feed.class);
                        startActivity(home_intent);//액티비티 띄우기
//                        startActivityForResult(intent,sub);//액티비티 띄우기
                        break;
                    case R.id.action_search:
                        Intent search_intent = new Intent(feed.this, searching.class);
                        startActivity(search_intent);//검색 화면으로 액티비티 띄우기
                        break;
                    case R.id.action_write_review:  // 리뷰 쓰기 화면으로 이동
                        Intent intent = new Intent(getApplicationContext(), write_review.class);
                        startActivityForResult(intent, 1001);  //액티비티 이동, 여기서 1000은 식별자. 아무 숫자나 넣으주면 됨.
                        break;
                    case R.id.action_notification:
                        Intent notification_intent = new Intent(feed.this, notification.class);
                        startActivity(notification_intent); //알림 화면으로 액티비티 이동
                        break;
                    case R.id.action_mycloset:
                        Intent mycloset_intent = new Intent(feed.this, my_closet.class);
                        startActivity(mycloset_intent);//내 옷장 화면으로 액티비티 띄우기
                        break;
                }
                return false;
            }
        });

// 리사이클러뷰 수정
// feed -> edit_review -> feed


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
        String json = gson.toJson(arrayList);  // 여기서 arrayList는 피드에 들어가는 리사이클러뷰를 담은 arrayList 이름임.
        Log.e("feed 클래스", "Gson 객체 호출 : " + json);
        editor.putString("feed_recyclerview", json);   // fromJson할 때도 "feed_recyclerview" 맞춰줌.
        Log.e("feed 클래스", "Gson 객체 호출 : " + editor.putString("feed_recyclerview", json));
        editor.apply();
        Log.e("feed 클래스", "apply 성공 ");
    }

    public void remove(int position) {
        // 피드 리사이클러뷰 안에 있는 리뷰를 삭제할 때 쓰는 remove 메소드

        try {
            arrayList.remove(position);
            feed_adapter.notifyItemRemoved(position);


            // sharedPreference 에서 삭제하는 코드를 넣어줘야 함.... 굳이? arrayList에서 없애주면 되는거 아닌가?

        } catch (IndexOutOfBoundsException e) {
            e.printStackTrace();
        }
    }

    private void bookmarked_loadData() {
        SharedPreferences sharedPreferences = getSharedPreferences("sharedPreferences", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("bookmarked_recyclerview", null);
        Type type = new TypeToken<ArrayList<feed_MainData>>() {
        }.getType();
        Log.e("feed 클래스", "typeToken객체 생성 :" + type);
        bookmarked_arrayList = gson.fromJson(json, type);
        Log.e("feed 클래스", "fromJson : arryaList(bookmarked_arrayList)는 " + bookmarked_arrayList);

        if (bookmarked_arrayList == null) {
            bookmarked_arrayList = new ArrayList<>();
        }


    }

    private void bookmark_saveData() {


        // sharedPref
        SharedPreferences sharedPreferences = getSharedPreferences("sharedPreferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        Log.e("feed 클래스", "Gson 객체 호출 : " + gson);

        String json = gson.toJson(bookmarked_arrayList);  // 여기서 arrayList는 피드에 들어가는 리사이클러뷰를 담은 arrayList 이름임.
        Log.e("feed 클래스", "Gson 객체 호출 (toJson) : " + json);

        editor.putString("bookmarked_recyclerview", json);   // fromJson할 때도 "feed_recyclerview" 맞춰줌.
        Log.e("feed 클래스", "Gson 객체 호출 (키 : bookmarked_recyclerview) : " + editor.putString("bookmarked_recyclerview", json));

        editor.apply();
        Log.e("feed 클래스", "editor. apply 성공 ");
    }


    @Override
    // startActivityForResult에서 Result는 어떤건지
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // setResult를 통해 받아온 요청번호, 상태, 데이터
        Log.e("RESULT", requestCode + "");
        Log.e("RESULT", resultCode + "");
        Log.e("RESULT", data + "");

        if (requestCode == 1001 && resultCode == RESULT_OK) {

            Toast.makeText(feed.this, "리뷰 작성을 완료했습니다!", Toast.LENGTH_SHORT).show();

// 리뷰를 올릴 때 작성 시간
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd  HH:mm:ss", Locale.KOREA);
            String date = dateFormat.format(new Date());
            String review_date = date;
            Log.e("feed 클래스 onActivityResult ", "시간 받아오는 중 : (dateFormat + review_date + date)" + dateFormat + review_date + date);

            // 평소 사이즈 로그인한 유저의 정보만 갖고 있는 쉐어드인 logined_user

            // 로그인한 회원의 정보를 가지고 있는 쉐어드에서 정보를 빼와서 글을 등록할 때 닉네임, 평소 사이즈를 불러오도록 했음.
            logined_user = getSharedPreferences("logined_user", Context.MODE_PRIVATE);   // 현재 로그인한 회원의 정보만 담겨있는 쉐어드를 불러와서
            // 불러온 sharedPreferences라는 이름의 SharedPreferencs를 확인하는 로그
            Log.e("feed 클래스에서 리뷰를 추가해서 피드에 추가할 때 ", "로그인한 회원의 정보가 있는 쉐어드인 logined_user 쉐어드를 가져온다" + logined_user);

            // sharedPreferences라는 이름의 쉐어드프리퍼런스에서 String을 가져오는데
            // 뭘 가져오냐면 사용자가 입력한 editText_email랑 같은 값을 찾아서 가져와서 String json이라는 변수에 넣어줌
            String json = logined_user.getString("login_user", "");  // logined_user라는 쉐어드에 저장되어있는 logined_user라는 키에 담겨있는 값을 불러와서 json이라는 변수에 담음
//                Log.e("login 클래스에서 로그인 버튼을 눌렀을 때", "sharedPreferences에서 j저장된 array(string으로 저장됐던) 가져오기 : " + sharedPreferences.getString("email", ""));
            Log.e("feed 클래스에서 로그인 버튼을 눌렀을 때", "여기 확인하기 : " + json);

//


                String textView_nickname = logined_user.getString("user_nickname", "");
                Log.e("feed 에서 로그인한 회원 정보가 있는 쉐어드에서", "닉네임 넣기 : " + textView_nickname + logined_user.getString("nickname", ""));

                String textView_mysize = logined_user.getString("user_size", "");
                Log.e("feed 에서 로그인한 회원 정보가 있는 쉐어드에서", "평소 사이즈 넣기 : " + textView_mysize);
                /** 프로필 이미지도 여기에   **/

//                String textView_nickname = logined_user.getString("nickname", "");
//                Log.e("feed 에서 로그인한 회원 정보가 있는 쉐어드에서", "닉네임 넣기 : " + textView_nickname + logined_user.getString("nickname", ""));
//
//                String textView_mysize = logined_user.getString("currentSize", "");
//                Log.e("feed 에서 로그인한 회원 정보가 있는 쉐어드에서", "평소 사이즈 넣기 : " + textView_mysize);


//            try {
//
//                JSONArray jsonArray = new JSONArray(json);
//                for (int i = 0; i < jsonArray.length(); i++) {
//
//                    JSONObject jsonObject = jsonArray.getJSONObject(i);
//                    Log.e("login 회원정보 확인", "sharedPreferences에서 저장된 array(string으로 저장됐던) 가져오기 : " + jsonObject);
//
//                    String sizesize = jsonObject.getString("currentSize");
//                    Log.e("login 회원정보 확인", "sharedPreferences에서 저장된 array(string으로 저장됐던) 평소 사이즈 가져오기 : " + jsonObject.getString("currentSize"));
//
//
//                    String nickname = jsonObject.getString("nickname");
//                    Log.e("login 회원정보 확인", "sharedPreferences에서 저장된 array(string으로 저장됐던) 닉네임 가져오기 : " + jsonObject.getString("nickname"));
//
//
//                }

//
                // 닉네임


                // 프로필 이미지


//            feed_adapter = new feed_Adapter();
                // 사용자가 입력한 내용을 가져와서
                String textView_shoppingmall_url = data.getStringExtra("쇼핑몰URL");
                Log.e("쇼핑몰URL", textView_shoppingmall_url + "쇼핑몰URL 가져왔습니다!!!!!!!!!");

                String textView_detailed_review_card = data.getStringExtra("상세리뷰");
                Log.e("상세리뷰", textView_detailed_review_card + "상세리뷰 가져왔습니다!!!!!!!");

                String textView_hashtag = data.getStringExtra("해시태그");
                Log.e("해시태그", textView_hashtag + "해시태그를 가져왔습니다!!!!!!!!!");

                float float_ratingBar = data.getFloatExtra("만족도", 0);
                Log.e("만족도", float_ratingBar + "만족도를 가져왔습니다!!!!!!!");

                String textView_review_writer = data.getStringExtra("작성자");
                Log.e("작성자", textView_hashtag + "작성자를 가져왔습니다!!!!!!!!!");

                String textView_reviewcard_number = data.getStringExtra("리뷰고유번호");
                Log.e("리뷰고유번호", textView_hashtag + "리뷰고유번호를 가져왔습니다!!!!!!!!!");

//                String textView_nickname= data.getStringExtra("닉네임");
//                Log.e("닉네임", textView_hashtag + "닉네임을 가져왔습니다!!!!!!!!!");

//                String textView_mysize = data.getStringExtra("평소사이즈");
//                Log.e("평소사이즈", textView_hashtag + "평소사이즈를 가져왔습니다!!!!!!!!!");


                feed_MainData feed_MainData = new feed_MainData(textView_shoppingmall_url, textView_detailed_review_card,
                        float_ratingBar, textView_hashtag, review_date, textView_review_writer, textView_reviewcard_number,
                        textView_nickname, textView_mysize);
                Log.e("add", feed_MainData + "feed_MainData 객체 생성?????????????");

//리사이클러뷰의 arrayList에 아이템 추가

                arrayList.add(feed_MainData);

                // 추가됨.

                // sharedPreferences 에 추가
                saveData();  // sharedPreference에 리뷰가 추가된 리사이클러뷰를 저장한다 // onCreate 밖에 메소드 만들었음.
                Log.e("write_review 클래스에서 (saveData)", "sharedpreference에 리사이클러뷰에 들어가는 arrayList 저장 :" + arrayList);


                //
//            loadData();  // sharedpreference에 저장한 arrayList (리사이클러뷰)를 가지고 옴. onCreate 밖에 메소드 만들어줌
//            // 피드에 들어가는 리사이클러뷰를 저장한 키값은 "feed_recyclerview"
//            Log.e("feed 클래스에서(loadData)","sharedPreference에 리사이클러뷰에 들어가는 arrayList 불러오기 :"  + arrayList );

//            } catch (JSONException e) {
//                e.printStackTrace();
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
        }
            if (requestCode == 2001 && resultCode == RESULT_OK) {
            Toast.makeText(feed.this, "리뷰 수정을 완료했습니다!", Toast.LENGTH_SHORT).show();

            // 평소 사이즈 로그인한 유저의 정보만 갖고 있는 쉐어드인 logined_user

            // 로그인한 회원의 정보를 가지고 있는 쉐어드에서 정보를 빼와서 글을 등록할 때 닉네임, 평소 사이즈를 불러오도록 했음.
            logined_user = getSharedPreferences("logined_user", Context.MODE_PRIVATE);   // 현재 로그인한 회원의 정보만 담겨있는 쉐어드를 불러와서
            // 불러온 sharedPreferences라는 이름의 SharedPreferencs를 확인하는 로그
            Log.e("feed 클래스에서 리뷰를 추가해서 피드에 추가할 때 ", "로그인한 회원의 정보가 있는 쉐어드인 logined_user 쉐어드를 가져온다" + logined_user);

            // sharedPreferences라는 이름의 쉐어드프리퍼런스에서 String을 가져오는데
            // 뭘 가져오냐면 사용자가 입력한 editText_email랑 같은 값을 찾아서 가져와서 String json이라는 변수에 넣어줌
            String json = logined_user.getString("logined_user", "");  // logined_user라는 쉐어드에 저장되어있는 logined_user라는 키에 담겨있는 값을 불러와서 json이라는 변수에 담음
//                Log.e("login 클래스에서 로그인 버튼을 눌렀을 때", "sharedPreferences에서 j저장된 array(string으로 저장됐던) 가져오기 : " + sharedPreferences.getString("email", ""));
            Log.e("feed 클래스에서 로그인 버튼을 눌렀을 때", "여기 확인하기 : " + json);


            String textView_nickname = logined_user.getString("nickname", "");
            String textView_mysize = logined_user.getString("currentSize", "");

            // 리뷰 수정에서 보낸 수정한 데이터 가져오기 / 받아오기
            // 사용자가 수정한 내용을 가져와서
            String textView_shoppingmall_url = data.getStringExtra("쇼핑몰URL");
            Log.e("쇼핑몰URL", textView_shoppingmall_url + "수정한 쇼핑몰URL 가져왔습니다");

            String textView_detailed_review_card = data.getStringExtra("상세리뷰");
            Log.e("상세리뷰", textView_detailed_review_card + "수정한 상세리뷰 가져왔습니다");

            String textView_hashtag = data.getStringExtra("해시태그");
            Log.e("해시태그", textView_hashtag + "수정한 해시태그 가져왔습니다");

            float float_ratingBar = data.getFloatExtra("만족도", 0);
            Log.e("만족도", float_ratingBar + "만족도를 가져왔습니다!!!!!!!");

            String textView_review_writer = data.getStringExtra("작성자");
            Log.e("작성자", textView_hashtag + "작성자를 가져왔습니다!!!!!!!!!");

            String textView_reviewcard_number = data.getStringExtra("리뷰고유번호");
            Log.e("리뷰고유번호", textView_hashtag + "리뷰고유번호를 가져왔습니다!!!!!!!!!");

//                String textView_nickname= data.getStringExtra("닉네임");
//                Log.e("닉네임", textView_hashtag + "닉네임을 가져왔습니다!!!!!!!!!");
//
//                String textView_mysize = data.getStringExtra("평소사이즈");
//                Log.e("평소사이즈", textView_hashtag + "평소사이즈를 가져왔습니다!!!!!!!!!");


// 리뷰를 올릴 때 작성 시간
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd  HH:mm:ss", Locale.KOREA);
            String date = dateFormat.format(new Date());
            String review_date = date;
            Log.e("feed 클래스 onActivityResult ", "시간 받아오는 중 : (dateFormat + review_date + date)" + dateFormat + review_date + date);

            int position = data.getIntExtra("POSITION", 0000);
            Log.e("위치값", position + " 위치값을 가지고 왔습니다");

            //
            feed_MainData feed_MainData = new feed_MainData(textView_shoppingmall_url, textView_detailed_review_card, float_ratingBar, textView_hashtag, review_date,
                    textView_review_writer, textView_reviewcard_number, textView_nickname, textView_mysize);
            Log.e("edit", "ArryaList 중 이곳에 데이터를 넣을껍니다" + textView_shoppingmall_url + "," + textView_detailed_review_card);


            // 그 위치를 받아와서 그곳에 set 해주기. 리뷰 수정 버튼을 누를 때 부터 같이 위치값을 startActivityForResult로 같이 넘겼다가 돌려받음.
            arrayList.set(position, feed_MainData);
//
//            arrayList.add(feed_MainData);
//            Log.e("edit", textView_detailed_review_card + "리사이클러뷰의 arrayList에 아이템 추가");

//            feed_adapter.notifyItemRemoved(getAdapterPosition());  //아이템이 삭제한 것을 notify
            feed_adapter.notifyDataSetChanged();  // 새로고침
            Log.e("edit", "수정한거 새로고침");

            saveData();  // sharedPreference에 리뷰가 추가된 리사이클러뷰를 저장한다 // onCreate 밖에 메소드 만들었음.
            Log.e("write_review 클래스에서 (saveData)", "sharedpreference에 리사이클러뷰에 들어가는 arrayList 저장 :" + arrayList);

//
//            loadData();  // sharedpreference에 저장한 arrayList (리사이클러뷰)를 가지고 옴. onCreate 밖에 메소드 만들어줌
//            // 피드에 들어가는 리사이클러뷰를 저장한 키값은 "feed_recyclerview"
//            Log.e("feed 클래스에서(loadData)","sharedPreference에 리사이클러뷰에 들어가는 arrayList 불러오기 :"  + arrayList );
        }

    }//onActivityResult 메소드 닫는 중괄호

//    private void bookmark_saveData() {
//
//
//        // sharedPref
//        SharedPreferences sharedPreferences = getSharedPreferences("sharedPreferences", MODE_PRIVATE);
//        SharedPreferences.Editor editor = sharedPreferences.edit();
//        Gson gson = new Gson();
//        Log.e("feed 클래스","Gson 객체 호출 : "+gson);
//        String json = gson.toJson(bookmarked_arrayList);  // 여기서 arrayList는 피드에 들어가는 리사이클러뷰를 담은 arrayList 이름임.
//        Log.e("feed 클래스","Gson 객체 호출 : "+ json);
//        editor.putString("bookmarked_recyclerview", json);   // fromJson할 때도 "feed_recyclerview" 맞춰줌.
//        Log.e("feed 클래스","Gson 객체 호출 : "+ editor.putString("bookmarked_recyclerview", json));
//        editor.apply();
//        Log.e("feed 클래스","apply 성공 ");
//    }
//


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
        feed_adapter.notifyDataSetChanged();  // 새로고침


        Log.e("add", "수정한거 새로고침");
        Log.e("feed", "onResume");


        //액티비티가 화면에 나타나고 상호작용이 가능해짐
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.e("feed", "onPause");
        //다른 액티비티가 시작되려함, 이 액티비티는 중단되려하고 백그라운드로 들어갑니다.
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.e("feed", "onStop");
        //액티비티가 더 이상 화면에 나타나지 않음,중단된 상태
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e("feed", "onDestroy");
        //액티비티가 종료되려고 합니다.
    }
}
