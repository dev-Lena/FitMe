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


public class myreview extends AppCompatActivity {

    private ArrayList<List> userData = new ArrayList<List>();

    private SharedPreferences logined_user;
    private SharedPreferences.Editor user_editor;

    private SharedPreferences bookmarkShared;
    private SharedPreferences.Editor bookmarkShared_editor;

    private SharedPreferences myreviewShared;
    private SharedPreferences.Editor myreviewShared_editor;

    BottomNavigationView bottomNavigationView; // 바텀 네이게이션 메뉴  -> 하단바
    ImageButton imageButton_back;
    TextView textView_howmany_reviews;
    /**
     * 리사이클러뷰에 필요한 기본 객체 선언
     **/

    ArrayList<feed_MainData> arrayList, bookmarked_arrayList, myreview_arrayList;

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

        //리사이클러뷰 맨 위부터 보여주기
        recyclerView.post(new Runnable() {

            @Override

            public void run() {

                recyclerView.scrollToPosition(recyclerView.getAdapter().getItemCount() - 1);

            }

        });
        feed_adapter = new feed_Adapter(myreview_arrayList,this);
        recyclerView.setAdapter(feed_adapter);

        // 리사이클러뷰 아이템에 있는 우측 상단 다이얼로그 메뉴 누르는 클릭 리스너

        myreview_loadData();


        // 지금까지 내가 쓴 리뷰는 xx 개입니다. 설정
        textView_howmany_reviews = findViewById(R.id.textView_howmany_reviews);
        int bookmark_count = myreview_arrayList.size();
        Log.d("bookmark_count"," : " + bookmark_count );
        textView_howmany_reviews.setText(String.valueOf(bookmark_count));
        Log.d("textView_howmany_bookmarked_reviews"," : " + textView_howmany_reviews );



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

                                Intent intent = new Intent(getApplicationContext(), edit_review.class);
                                Log.e("Feed 클래스에서 리사이클러뷰 수정 작업중! ", "edit_review로 연결되는 인텐트를 가지고왔습니다.");


//                                String textView_review_writer;  // 리뷰 작성 후 리뷰 카드에 들어가는 작성자
//                                String textView_reviewcard_number ;  // 리뷰 작성 후 리뷰 카드에 들어가는 고유 번호
//                                String review_date;// 리뷰 작성 후 리뷰 카드에 들어가는 최초 작성 시간
                                // 리뷰를 수정할 때 기존의 있는 정보를 보내줌
                                intent.putExtra("URL", myreview_arrayList.get(position).textView_shoppingmall_url);
                                intent.putExtra("DETAIL", myreview_arrayList.get(position).textView_detailed_review_card);
                                intent.putExtra("HASHTAG", myreview_arrayList.get(position).textView_hashtag);
                                intent.putExtra("WRITER", myreview_arrayList.get(position).textView_review_writer);
                                intent.putExtra("NUMBER", myreview_arrayList.get(position).textView_reviewcard_number);

                                intent.putExtra("IMAGE", myreview_arrayList.get(position).imageView_reviewcard_img1);
                                intent.putExtra("PROFILE", myreview_arrayList.get(position).imageView_reviewcard_profile_image);
                                intent.putExtra("DATE", myreview_arrayList.get(position).review_date);

                                intent.putExtra("RATING", myreview_arrayList.get(position).float_ratingBar);


                                intent.putExtra("POSITION", position);
                                // 위치도 받아와야 수정한 데이터를 받아왔을 때 어떤 position에 있는 아이템에 set 해줄 건지 알려줄 수 있음
                                startActivityForResult(intent, 2001);


                                break;

                            case R.id.action_delete:

                                // 여기서 삭제하면 내가 쓴 리뷰, 피드에 있는 리뷰, 북마크한 리뷰에서 모두 사라지게

                                remove(position);  // 북마크한 리뷰와 내가 쓴 리뷰에서도 지우고
                                // 내가 쓴 리뷰 리사이클러뷰에서 지우기

                                feed_adapter.notifyDataSetChanged();  // 새로고침
                                Toast.makeText(getApplication(), "삭제되었습니다", Toast.LENGTH_SHORT).show();

                                saveData();  // sharedPreference에 리뷰가 추가된 리사이클러뷰를 저장한다 // onCreate 밖에 메소드 만들었음.
                                bookmark_saveData();
                                myreview_saveData();
                                Log.e("feed 클래스에서 (saveData)", "삭제 후   sharedpreference에 리사이클러뷰에 들어가는 arrayList 저장 :" + myreview_arrayList);

                                break;
//                                return true;

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
                // 해당 아이템이 bookmarked_review 리사이클러뷰에 추가되어야 함.
//                bookmarked_arrayList = new ArrayList<>();

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
                Intent register_intent = new Intent(myreview.this, feed.class);
                startActivity(register_intent); //액티비티 이동
                finish(); // 액티비티 finish 시킴

            }
        });



    }// onCreate 닫는 중괄호

    public void remove(int position) {
        // 피드 리사이클러뷰 안에 있는 리뷰를 삭제할 때 쓰는 remove 메소드

        loadData(); // 피드 쉐어드를 가지고 온다
        bookmarked_loadData();  // 북마크한 리뷰 쉐어드를 가지고 온다

        // 내가 쓴 리뷰에서 삭제하기로 선택한 아이템과 전체 피드에서 같은 아이템을 찾기
        int feed_index = find_feed_arrayList(feed_adapter.getItem(position).getTextView_reviewcard_number());
        Log.d("feed_index", feed_index + "");

        // 내가 쓴 리뷰에서 삭제하기로 선택한 아이템과 북마크한 리뷰에서 같은 아이템을 찾기
        int bookmark_index = find_bookmark_arrayList(feed_adapter.getItem(position).getTextView_reviewcard_number());
        Log.d("bookmark_index", bookmark_index + "");


        try {


            // 피드 리사이클러뷰에서 삭제
            if (feed_index > -1) { // 찾아온 값이 있을 때 -> 없으면 -1로 리턴하라고 했음
                arrayList.remove(feed_index);
                Log.d("arrayList", "arrayList.size() : " + arrayList.size());
            }

            // 내가 쓴 리뷰 리사이클러뷰에 들어간느 arrayList에서 삭제 -> 지금 클래스에서는 이미 arrayList에서 작업하고 있기 때문에 아래 같은 작업이 필요없음.
            myreview_arrayList.remove(position);


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

//    public void remove(int position){
//        // 피드 리사이클러뷰 안에 있는 리뷰를 삭제할 때 쓰는 remove 메소드
//
//        try{
//            bookmarked_arrayList.remove(position);
//            feed_adapter.notifyItemRemoved(position);
//
//
//            // sharedPreference 에서 삭제하는 코드를 넣어줘야 함.... 굳이? arrayList에서 없애주면 되는거 아닌가?
//
//        }catch (IndexOutOfBoundsException e){
//            e.printStackTrace();
//        }
//    }

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
        overridePendingTransition(0, 0);
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
