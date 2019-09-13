package com.example.fitme;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class feed extends AppCompatActivity {
    // 현재 로그인한 유저의 정보만 담는 쉐어드 프리퍼런스

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

    /// 삭제할 때 비교할 아이템 포지션 위치

    // 뷰 객체들
    MenuItem action_write_review; // -> 하단 바에 있는 리뷰 작성 버튼
    //    ImageButton imageButton_review_register;
    BottomNavigationView bottomNavigationView; // 바텀 네이게이션 메뉴  -> 하단바
    BottomNavigationView bottomNavigationMenu; // 바텀 네이게이션 메뉴  -> 하단바
    ImageView imageView_notification, imageView_reviewcard_img1;
    TextView textView_feed_id;
    ImageButton imageButton_review_timesale;
    FloatingActionButton floatingActionButton;  // 리뷰 작성하는 글쓰기 플로팅 버튼


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

        arrayList = new ArrayList<>();
        //feed_MainData에서 받게되는 데이터를 어레이 리스트화 시킨다.
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
//        recyclerView.setHasFixedSize(true);//각 아이템이 보여지는 것을 일정하게
        recyclerView.setLayoutManager(linearLayoutManager);


        feed_adapter = new feed_Adapter(arrayList, this);//앞서 만든 리스트를 어뎁터에 적용시켜 객체를 만든다.
        recyclerView.setAdapter(feed_adapter);// 그리고 만든 객체를 리싸이클러뷰에 적용시킨다.

// Allows to remember the last item shown on screen


// 피드 메인 화면에 "닉네임 님 이런 리뷰는 어떠세요?"에 현재 로그인한 회원의 닉네임 적기

        logined_user = getSharedPreferences("logined_user", Context.MODE_PRIVATE);   // 현재 로그인한 회원의 정보만 담겨있는 쉐어드를 불러와서
        String feed_id = logined_user.getString("user_nickname", "");
        Log.e("[피드] 로그인한 회원 정보가 있는 쉐어드에서", " 현재 로그인한 유저의 닉네임 넣기 : " + feed_id);
        textView_feed_id = findViewById(R.id.textView_feed_id);
        textView_feed_id.setText(feed_id);


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

                /** 여기에 로그인한 회원과 작성자와의 일치 여부에 따라 다른 메뉴가 보이도록 if  조건문 걸어주기. **/


                // 리뷰 아이템의 작성자와 현재 로그인한 회원의 이메일이 같을 때
                // -> 수정, 삭제, 공유가 가능한 메뉴를 띄워라
                if (arrayList.get(position).textView_review_writer.equals(logined_user.getString("user_email", ""))) {

// 리사이클러뷰 아이템 안에 버튼을 누르면 팝업 메뉴 뜨도록
                    PopupMenu popup = new PopupMenu(getApplicationContext(), v);//v는 클릭된 뷰를 의미

                    getMenuInflater().inflate(R.menu.feed_review_menu, popup.getMenu());
                    popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {
                            switch (item.getItemId()) {
                                case R.id.action_edit:
                                    // 리뷰 카드에 있는 메뉴 다이얼로그 (?) 중 수정하기를 눌렀을 때
                                    Toast.makeText(getApplication(), "수정하기", Toast.LENGTH_SHORT).show();

                                    Intent intent = new Intent(getApplicationContext(), edit_review.class);
                                    Log.e("Feed 클래스에서 리사이클러뷰 수정 작업중! ", "edit_review로 연결되는 인텐트를 가지고왔습니다.");


//                                String textView_review_writer;  // 리뷰 작성 후 리뷰 카드에 들어가는 작성자
//                                String textView_reviewcard_number ;  // 리뷰 작성 후 리뷰 카드에 들어가는 고유 번호
//                                String review_date;// 리뷰 작성 후 리뷰 카드에 들어가는 최초 작성 시간
                                    // 리뷰를 수정할 때 기존의 있는 정보를 보내줌
                                    intent.putExtra("URL", arrayList.get(position).textView_shoppingmall_url);
                                    intent.putExtra("DETAIL", arrayList.get(position).textView_detailed_review_card);
                                    intent.putExtra("HASHTAG", arrayList.get(position).textView_hashtag);
                                    intent.putExtra("WRITER", arrayList.get(position).textView_review_writer);
                                    intent.putExtra("NUMBER", arrayList.get(position).textView_reviewcard_number);

                                    intent.putExtra("IMAGE", arrayList.get(position).imageView_reviewcard_img1);
                                    intent.putExtra("PROFILE", arrayList.get(position).imageView_reviewcard_profile_image);
                                    intent.putExtra("DATE", arrayList.get(position).review_date);

                                    intent.putExtra("RATING", arrayList.get(position).float_ratingBar);


                                    intent.putExtra("POSITION", position);
                                    // 위치도 받아와야 수정한 데이터를 받아왔을 때 어떤 position에 있는 아이템에 set 해줄 건지 알려줄 수 있음
                                    startActivityForResult(intent, 2001);

//// 확인 로고
//                                Log.e("Feed 클래스에서 이미지(수정) ", "이미지 : " + arrayList.get(position).imageView_reviewcard_img1);
//                                Log.e("Feed 클래스에서 리사이클러뷰 수정 작업중! ", "URL : " + arrayList.get(position).textView_detailed_review_card);
//                                Log.e("Feed 클래스에서 리사이클러뷰 수정 작업중.", "기존에 있던 데이터가 넘어가나 확인중. DETAIL : " + arrayList.get(position).textView_detailed_review_card);
//                                Log.e("Feed 클래스에서 리사이클러뷰 수정 작업중.", "startActivityForResult를 실행. requestCode 2001");
                                    Log.e("Feed 클래스에서 리사이클러뷰 수정 작업중.", "textView_review_writer : " + arrayList.get(position).textView_review_writer);
                                    Log.e("Feed 클래스에서 리사이클러뷰 수정 작업중.", "textView_reviewcard_number : " + arrayList.get(position).textView_reviewcard_number);
                                    Log.e("Feed 클래스에서 리사이클러뷰 수정 작업중.", "비교중 review_date : " + arrayList.get(position).review_date);
                                    Log.e("Feed 클래스에서 리사이클러뷰 수정 작업중.", "review_date : " + review_date); // 둘이 같음

                                    break;

                                case R.id.action_delete: // 삭제하기


                                    remove(position);  // 쉐어드 load는 remove 메소드 안에서

                                    feed_adapter.notifyDataSetChanged();  // 새로고침
                                    Toast.makeText(getApplication(), "삭제되었습니다", Toast.LENGTH_SHORT).show();

                                    saveData();  // sharedPreference에 리뷰가 추가된 리사이클러뷰를 저장한다 // onCreate 밖에 메소드 만들었음.
                                    bookmark_saveData();
                                    myreview_saveData();

                                    Log.e("feed 클래스에서 (saveData)", "삭제 후   sharedpreference에 리사이클러뷰에 들어가는 arrayList 저장 :" + arrayList);
                                    return true;


                                case R.id.action_share:  // 공유하기
                                    Toast.makeText(getApplication(), "공유하기", Toast.LENGTH_SHORT).show();
                                    break;
                                default:
                                    break;
                            }
                            return false;
                        }
                    });

                    popup.show();//Popup Menu 보이기
                    // 작성자와 로그인한 유저의 이메일이 맞을 때 if문 닫는 중괄호 -> feed_menu
                } else {  // 작성자와 로그인한 유저의 이메일이 맞지 않을 때 if문 -> bookmarked_menu
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
                                    saveData();
                                    myreview_saveData();
                                    bookmark_saveData();  // sharedPreference에 리뷰가 추가된 리사이클러뷰를 저장한다 // onCreate 밖에 메소드 만들었음.
                                    Log.e("feed 클래스에서 (saveData)", "삭제 후   sharedpreference에 리사이클러뷰에 들어가는 arrayList 저장 :" + bookmarked_arrayList);

                                    Toast.makeText(getApplication(), "신고되었습니다", Toast.LENGTH_SHORT).show();

                                    break;
                                default:
                                    break;
                            }
                            return false;
                        }
                    });

                    popup.show();//Popup Menu 보이기

                }// else 닫는 중괄호
            }// onItemClick 리스너 닫는 중괄호


            @Override  // 피드 리사이클러뷰에 들어가는 리뷰 카드 아이템에서 댓글 버튼을 눌렀을 때
            public void onCommentClick(View v, int position) {
                Intent comment_intent = new Intent(feed.this, comment.class);
                comment_intent.putExtra("POSITION", position);
                startActivity(comment_intent); //액티비티 이동


            }

            @Override  // 피드 리사이클러뷰에 들어가는 리뷰 카드 아이템에서 북마크 버튼을 눌렀을 때
            public void onBookmarkClick(View v, int position) {
                // 해당 아이템이 bookmarked_review 리사이클러뷰에 추가되어야 함.
//                bookmarked_arrayList = new ArrayList<>();
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

            // 리뷰 아이템을 클릭했을 때
            @Override
            public void onReviewClick(View v, int position) { // 리뷰 아이템 하나만 보여주는 클래스
//                Intent intent = new Intent(feed.this, review_card.class);
//                startActivity(intent); //액티비티 이동
//

            }

        });


/**버튼 클릭시 이동 메소드 모음 **/
        // 타임 세일 알람 화면으로 이동하는 버튼
        imageButton_review_timesale = findViewById(R.id.imageButton_review_timesale);
        imageButton_review_timesale.setOnClickListener(new ImageView.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent register_intent = new Intent(feed.this, timesale.class);
                startActivity(register_intent); //액티비티 이동

            }
        });

        // 리뷰 작성 플로팅 버튼
        floatingActionButton = findViewById(R.id.floatingActionButton);
        floatingActionButton.setOnClickListener(new ImageView.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getApplicationContext(), write_review.class);
                startActivityForResult(intent, 1001);  //액티비티 이동, 여기서 1000은 식별자. 아무 숫자나 넣으주면 됨.


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
                        home_intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                        startActivity(home_intent);//액티비티 띄우기
//                        startActivityForResult(intent,sub);//액티비티 띄우기
                        break;
                    case R.id.action_search:
                        Intent search_intent = new Intent(feed.this, searching.class);
                        search_intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                        startActivity(search_intent);//검색 화면으로 액티비티 띄우기
                        break;
                    case R.id.action_insight:  // 리뷰 쓰기 화면으로 이동
                        Intent insight_intent = new Intent(feed.this, insight.class);
                        insight_intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                        startActivity(insight_intent);//검색 화면으로 액티비티 띄우기
                        break;
                    case R.id.action_notification:
                        Intent notification_intent = new Intent(feed.this, notification.class);
                        notification_intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                        startActivity(notification_intent); //알림 화면으로 액티비티 이동
                        break;
                    case R.id.action_mypage:
                        Intent mycloset_intent = new Intent(feed.this, mypage.class);
                        mycloset_intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
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

//        int index = find_myreview_arrayList(myreview_arrayList.get(position));
//        find_bookmark_arrayList(position); //

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

//    public int find_feed_arrayList(feed_MainData feed_MainData) {
//        feed_MainData data;  // 아이템 객체와 arrayList 안에 아이템을 비교해주기 위해 선언해주는 변수
//
//        if (arrayList != null) {
//            for (int i = 0; i < arrayList.size(); i++) {
//                data = arrayList.get(i);// arrayList에서 i를 가지고 와서 data 변수에 넣어주고
//
//                if (data == feed_MainData) {  // 해당 아이템 feed_MainData 과 data 가 같으면
//
//                    return i; // 인덱스 i를 리턴해주고
//                }
//
//            }
//        }
//        return -1;  // 0보다 커야 작업을 수행할 수 있기 때문에 실패하면 -1을 리턴해주라 -> List에서 index는 0부터 시작이니까
//    }
//-> 원래 객체를 가지고 와서 비교해주는 걸 했었는데 안되서 객체를 가지고 오고 가지고 온 객체 안에 고유한 데이터를 비교해주는 메소드로 바꿈. 그게 아래 메소드.

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


        // 그럼 현재 로그인하고 있는 유저의 데이터를 담고 있는 쉐어드에도 넣어줘야하나?
//        logined_user = getSharedPreferences("logined_user", Context.MODE_PRIVATE);
//        user_editor = logined_user.edit();
//
//
//// 로그인할 때 로그인한 회원의 정보를 배열로 가지고 와서 추출 후 각각의 key값을 줘서 저장했던 value를 호출
//        user_editor.putString("user_bookmarkList", json);  // 회원가입시 입력한 이메일이 각 arrayList의 key 값이 됨.


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


    @Override
    // startActivityForResult에서 Result는 어떤건지
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // setResult를 통해 받아온 요청번호, 상태, 데이터
        Log.e("RESULT", requestCode + "");
        Log.e("RESULT", resultCode + "");
        Log.e("RESULT", data + "");

        // write_review 클래스에서 리뷰 작성 후 사용자가 입력한 데이터를 가지고 옴.
        /** 리뷰 작성 **/
        if (requestCode == 1001 && resultCode == RESULT_OK) {

            myreview_loadData();

            Toast.makeText(feed.this, "리뷰 작성을 완료했습니다!", Toast.LENGTH_SHORT).show();

            // 로그인한 회원의 정보를 가지고 있는 쉐어드에서 정보를 빼와서 글을 등록할 때 닉네임, 평소 사이즈를 불러오도록 했음.
            logined_user = getSharedPreferences("logined_user", Context.MODE_PRIVATE);   // 현재 로그인한 회원의 정보만 담겨있는 쉐어드를 불러와서
            // sharedPreferences라는 이름의 쉐어드프리퍼런스에서 String을 가져오는데 사용자가 입력한 editText_email랑 같은 값을 찾아서 가져와서 String json이라는 변수에 넣어줌
            String json = logined_user.getString("login_user", "");
            /**이미지  **/
            String imageView_reviewcard_profile_image = logined_user.getString("user_profileimage", null);
            // 로그인할 때 로그인한 회원의 정보를 배열로 가지고 와서 추출 후 각각의 key값을 줘서 저장했던 value를 호출
            String textView_nickname = logined_user.getString("user_nickname", "");
            String textView_mysize = logined_user.getString("user_size", "");

// 확인 로그
            Log.e("feed 클래스 onActivityResult ", "시간 받아오는 중 : (dateFormat + review_date + date)" + review_date);
            Log.e("feed 클래스에서 로그인 버튼을 눌렀을 때", "여기 확인하기 : " + json);
            Log.e("[리뷰 추가] feed 에서 로그인한 회원 정보가 있는 쉐어드에서", "프로필 사진 넣기 : " + imageView_reviewcard_profile_image);
            Log.e("[리뷰 추가] feed 에서 로그인한 회원 정보가 있는 쉐어드에서", "닉네임 넣기 : " + textView_nickname + logined_user.getString("nickname", ""));
            Log.e("[리뷰 추가] feed 에서 로그인한 회원 정보가 있는 쉐어드에서", "평소 사이즈 넣기 : " + textView_mysize);


            // 사용자가 입력한 내용을 가져와서
            review_date = data.getStringExtra("작성시간");  // 여기 안되면 작성시간 메소드 위에 수식 주석 해제하기
            String textView_reviewcard_number = data.getStringExtra("리뷰고유번호");
            String textView_shoppingmall_url = data.getStringExtra("쇼핑몰URL");
            String textView_detailed_review_card = data.getStringExtra("상세리뷰");
            String textView_hashtag = data.getStringExtra("해시태그");
            float float_ratingBar = data.getFloatExtra("만족도", 0);
            String textView_review_writer = data.getStringExtra("작성자");
            /**이미지**/
            String imageView_reviewcard_img1 = data.getStringExtra("리뷰이미지");

//MainData
            feed_MainData feed_MainData = new feed_MainData(textView_shoppingmall_url, textView_detailed_review_card,
                    float_ratingBar, textView_hashtag, review_date, textView_review_writer, textView_reviewcard_number,
                    textView_nickname, textView_mysize, imageView_reviewcard_img1, imageView_reviewcard_profile_image);

            arrayList.add(feed_MainData);  //리사이클러뷰의 arrayList에 아이템 추가
            myreview_arrayList.add(feed_MainData); // 내가 쓴 리뷰에 추가

            System.out.println("arrayList log"+arrayList.get(arrayList.size() - 1));
            System.out.println("myreview log"+myreview_arrayList.get(myreview_arrayList.size() - 1));

            feed_adapter.notifyDataSetChanged();  // 새로고침


            // sharedPreference에 리뷰가 추가된 리사이클러뷰를 저장한다
            saveData();   // onCreate 밖에 메소드 만들었음.
            myreview_saveData();

// 확인 로그
            Log.e("작성시간", review_date + "작성시간을 가져왔습니다!!!!!!!!!");
            Log.e("리뷰고유번호", textView_reviewcard_number + "리뷰고유번호를 가져왔습니다!!!!!!!!!");
            Log.e("쇼핑몰URL", textView_shoppingmall_url + "쇼핑몰URL 가져왔습니다!!!!!!!!!");
            Log.e("상세리뷰", textView_detailed_review_card + "상세리뷰 가져왔습니다!!!!!!!");
            Log.e("해시태그", textView_hashtag + "해시태그를 가져왔습니다!!!!!!!!!");
            Log.e("만족도", float_ratingBar + "만족도를 가져왔습니다!!!!!!!");
            Log.e("작성자", textView_hashtag + "작성자를 가져왔습니다!!!!!!!!!");
            Log.e("feed 클래스에서 onActivityResult", " '리뷰이미지' :" + imageView_reviewcard_img1);
            Log.e("feed 클래스에서 (saveData)", "sharedpreference에 리사이클러뷰에 들어가는 arrayList 저장 :" + arrayList);

        }

        /** 리뷰를 수정했을 때 **/

        if (requestCode == 2001 && resultCode == RESULT_OK) {
            Toast.makeText(feed.this, "리뷰 수정을 완료했습니다!", Toast.LENGTH_SHORT).show();

            // 평소 사이즈 로그인한 유저의 정보만 갖고 있는 쉐어드인 logined_user
            // 로그인한 회원의 정보를 가지고 있는 쉐어드에서 정보를 빼와서 글을 등록할 때 닉네임, 평소 사이즈를 불러오도록 했음.
            logined_user = getSharedPreferences("logined_user", Context.MODE_PRIVATE);   // 현재 로그인한 회원의 정보만 담겨있는 쉐어드를 불러와서

            // sharedPreferences라는 이름의 쉐어드프리퍼런스에서 사용자가 입력한 editText_email랑 같은 값을 찾아서 가져와서 변수에 넣어줌
            String json = logined_user.getString("logined_user", "");// logined_user라는 쉐어드에 저장되어있는 logined_user라는 키에 담겨있는 값을 불러와서 json이라는 변수에 담음

            //user_nickname & user_size & user_profileimage
            String textView_nickname = logined_user.getString("user_nickname", "");
            String textView_mysize = logined_user.getString("user_size", "");
            String imageView_reviewcard_profile_image = logined_user.getString("user_profileimage", null);

            //확인로그
            Log.e("feed 클래스에서 리뷰 수정 후 ", " logined_user 쉐어드를 가져온다" + logined_user);
            Log.e("feed 클래스 리뷰 수정 후", "여기 확인하기 : " + json);
            Log.e("feed 클래스에서 onActivityResult - 리뷰 수정 후", " 'user_profileimage' :" + imageView_reviewcard_profile_image);

            // 리뷰 수정에서 보낸 수정한 데이터 가져오기 / 받아오기
            // 사용자가 수정한 내용을 가져와서
            review_date = data.getStringExtra("작성시간");
            String textView_shoppingmall_url = data.getStringExtra("쇼핑몰URL");
            String textView_detailed_review_card = data.getStringExtra("상세리뷰");
            String textView_hashtag = data.getStringExtra("해시태그");
            float float_ratingBar = data.getFloatExtra("만족도", 0);
            textView_review_writer = data.getStringExtra("작성자");
            textView_reviewcard_number = data.getStringExtra("리뷰고유번호");
            /**이미지**/
            String imageView_reviewcard_img1 = data.getStringExtra("리뷰이미지");
//            ArrayList<feed_MainData> = data.getStringArrayListExtra(comment);


//확인 로그
            Log.e("작성시간", review_date + "작성시간을 가져왔습니다!!!!!!!!!");
            Log.e("쇼핑몰URL", textView_shoppingmall_url + "수정한 쇼핑몰URL 가져왔습니다");
            Log.e("상세리뷰", textView_detailed_review_card + "수정한 상세리뷰 가져왔습니다");
            Log.e("해시태그", textView_hashtag + "수정한 해시태그 가져왔습니다");
            Log.e("만족도", float_ratingBar + "만족도를 가져왔습니다!!!!!!!");
            Log.e("작성자", textView_hashtag + "작성자를 가져왔습니다!!!!!!!!!");
            Log.e("리뷰고유번호", textView_hashtag + "리뷰고유번호를 가져왔습니다!!!!!!!!!");
            Log.e("feed 클래스에서 onActivityResult", " '리뷰이미지' ++++++++++++++++++++++++ :" + imageView_reviewcard_img1);

            int position = data.getIntExtra("POSITION", 0000);
            Log.e("위치값", position + " 위치값을 가지고 왔습니다");

            //
            feed_MainData feed_MainData = new feed_MainData(textView_shoppingmall_url, textView_detailed_review_card,
                    float_ratingBar, textView_hashtag, review_date, textView_review_writer, textView_reviewcard_number,
                    textView_nickname, textView_mysize, imageView_reviewcard_img1, imageView_reviewcard_profile_image);
            Log.e("edit", "ArryaList 중 이곳에 데이터를 넣을껍니다 imageView_reviewcard_img1 : +++++++++++++++++++++++++++++++++" + imageView_reviewcard_img1);


            // 그 위치를 받아와서 그곳에 set 해주기. 리뷰 수정 버튼을 누를 때 부터 같이 위치값을 startActivityForResult로 같이 넘겼다가 돌려받음.
            arrayList.set(position, feed_MainData);

            feed_adapter.notifyDataSetChanged();  // 새로고침
            Log.e("edit", "수정한거 새로고침");

            saveData();  // sharedPreference에 리뷰가 추가된 리사이클러뷰를 저장한다 // onCreate 밖에 메소드 만들었음.
            myreview_saveData();
            Log.e("feed 클래스에서 (saveData)", "sharedpreference에 리사이클러뷰에 들어가는 arrayList 저장 :" + arrayList);
            // 업데이트 한 bookmarked_arrayList를 sharedPreference에 저장하라. "bookmarked_recyclerview"
            bookmark_saveData();

        }

    }//onActivityResult 메소드 닫는 중괄호


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
        feed_adapter.notifyDataSetChanged();  // 새로고침
//        loadData();
//        Log.e("feed 클래스에서 (onResume)--------------->", "loadData :" + arrayList);


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
