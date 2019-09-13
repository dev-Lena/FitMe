package com.example.fitme;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class comment extends AppCompatActivity {


    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    ArrayList<feed_MainData> arrayList, bookmarked_arrayList, myreview_arrayList;
    //    ArrayList<feed_MainData> myreview_arrayList = new ArrayList<>();
//    ArrayList<feed_MainData> myreview_arrayList;
    feed_Adapter feed_adapter;

    BottomNavigationView bottomNavigationView; // 바텀 네이게이션 메뉴  -> 하단바
    ImageButton imageButton_comment_save;
    // 로그인 하고 있는 유저의 정보를 담고 있는 쉐어드 프리퍼런스
    private ArrayList<List> userData = new ArrayList<List>();

    private SharedPreferences logined_user;
    private SharedPreferences.Editor user_editor;

    private SharedPreferences commentShared;
    private SharedPreferences.Editor commentShared_editor;

    TextView textView_comment_nickname, textView_unique_code ;
    ImageView imageView_comment_profile ;
    ImageButton imageButton_back;
    EditText editText_comment_input ;
    /**
     * 리사이클러뷰에 필요한 기본 객체 선언
     **/
    ArrayList<comment_Data> commentArrayList, comment_show_arrayList;
    commentAdapter commentAdapter;
    RecyclerView commentRecyclerview;
    LinearLayoutManager mlinearLayoutManager;


    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        commentArrayList = new ArrayList<>();
        comment_loadData();  // sharedpreference에 저장한 arrayList (리사이클러뷰)를 가지고 옴. onCreate 밖에 메소드 만들어줌

        Log.e("myreview", "onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);

        /**여기서부터 리사이클러뷰 만들기**/

        commentRecyclerview = (RecyclerView) findViewById(R.id.commentRecyclerview);

        mlinearLayoutManager = new LinearLayoutManager(this);

        mlinearLayoutManager.setReverseLayout(true); // 최신순으로 리사이클러뷰 아이템 추가.

        commentRecyclerview.setLayoutManager(mlinearLayoutManager);

        comment_show_arrayList = new ArrayList<>(); // 여기다가 아이템에 해당하는 댓글만 담을꺼야

        Intent intent = getIntent();
        int position = intent.getIntExtra("POSITION", 0000);
        // 피드에서 댓글 버튼을 눌렀을 때 해당 아이템의 position
//        String uniqueKey = feed_adapter.getItem(position).getTextView_reviewcard_number();

        textView_unique_code = findViewById(R.id.textView_unique_code);

        // if문 확인하면서 commnetData 가 갖고있는 아이템에서 같은 고유번호를 가진 댓글 아이템만 가지고와라.
        // -> 그리고 리사이클러뷰에서 comment_show_arrayList 보여줄것.
        for(comment_Data item : commentArrayList){// item이라는 comment Data를 잡고 commentArrayList 안을 순회하면서 비교하면서
            if(item.getReviewUniqueCode().equals(textView_unique_code)){ // if문 확인하면서 commnetData 가 갖고있는 아이템에서
                comment_show_arrayList.add(item);
            }

        }

// 위에랑 같은 방식의 코드. 이것도 되긴 됨. 위 방식이 더 간단함.
//            if (commentArrayList != null) {  // 리스트가 null 값이 아닐 때
//                for (int i = 0; i < commentArrayList.size(); i++) {  // 사이즈만큼 순회하면서
//                    String data = commentArrayList.get(i).getReviewUniqueCode();
//                    // arrayList에서 인덱스 번호 i에 있는 아이템의 고유번호를 data 변수에 넣어주고
//                    if (data.equals(textView_unique_code)){ //
//
//                        comment_show_arrayList.add(commentArrayList.get(i));
////                    if (data.equals(uniqueKey)) {  // 가져온 인덱스 번호 i에 있는 아이템의 고유번호가 uniqueKey와 같다면
//                        //여기서 uniqueKey는 이 메소드를 사용하는 곳에서 파라미터?로 (feed_adapter.getItem(position).getTextView_reviewcard_number()를 넣어줌.
//                        // 즉, 어댑터에서 해당 아이템의 포지션을 가지고 오는데 그 아이템이 가지고 있는 고유번호를 메소드를 사용할 때 참고해 uniqueKey로 설정해줌
//
//                    }
//
//                }
//            }



        commentAdapter = new commentAdapter(comment_show_arrayList, context );
        // commentArrayList에서 해당 리뷰 아이템에 대한 댓글만 따로 담은 comment_show_arrayList를 보여줌.
        commentRecyclerview.setAdapter(commentAdapter);


       textView_comment_nickname = (TextView) findViewById(R.id.textView_comment_nickname);
        imageView_comment_profile = (ImageView) findViewById(R.id.imageView_comment_profile);
        editText_comment_input = (EditText) findViewById(R.id.editText_comment_input);
        textView_unique_code = findViewById(R.id.textView_unique_code);



// 댓글 작성 완료 버튼을 눌렀을 때
        imageButton_comment_save = findViewById(R.id.imageButton_comment_save);
        imageButton_comment_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = getIntent();
//                comment_intent.putExtra("포지션", position);
//                comment_intent.putExtra("프로필", position);
                String reviewUniqueCode = intent.getStringExtra("댓글작성한리뷰의고유번호");
                // 피드에서 댓글 버튼을 눌렀을 때 해당 아이템의 고유번호를 feed에서 코멘트 버튼을 눌렀을 때 넘겨준걸 받음

                Log.e("comment 클래스에서 댓글 추가 ", "uniq : " + reviewUniqueCode);
                // 댓글 저장할 때 해당 리뷰 아이템의 고유 번호를 가져와 저장함.
//                textView_unique_code.setText(reviewUniqueCode);



                // 로그인한 회원의 정보를 가지고 있는 쉐어드에서 정보를 빼와서 글을 등록할 때 닉네임, 평소 사이즈를 불러오도록 했음.
                logined_user = getSharedPreferences("logined_user", Context.MODE_PRIVATE);   // 현재 로그인한 회원의 정보만 담겨있는 쉐어드를 불러와서
                // 불러온 sharedPreferences라는 이름의 SharedPreferencs를 확인하는 로그
                Log.e("comment 클래스에서 댓글 추가 ", "로그인한 회원의 정보가 있는 쉐어드인 logined_user 쉐어드를 가져온다" + logined_user);

                // sharedPreferences라는 이름의 쉐어드프리퍼런스에서 String을 가져오는데
                // 뭘 가져오냐면 사용자가 입력한 editText_email랑 같은 값을 찾아서 가져와서 String json이라는 변수에 넣어줌
                String json = logined_user.getString("login_user", "");  // logined_user라는 쉐어드에 저장되어있는 logined_user라는 키에 담겨있는 값을 불러와서 json이라는 변수에 담음
//                Log.e("login 클래스에서 로그인 버튼을 눌렀을 때", "sharedPreferences에서 j저장된 array(string으로 저장됐던) 가져오기 : " + sharedPreferences.getString("email", ""));
                Log.e("comment 클래스에서 댓글 추가", "여기 확인하기 : " + json);

// 로그인할 때 로그인한 회원의 정보를 배열로 가지고 와서 추출 후 각각의 key값을 줘서 저장했던 value를 호출
                String comment_nickname = logined_user.getString("user_nickname", "");
                Log.e("comment 클래스에서 댓글 추가 로그인한 회원 정보가 있는 쉐어드에서", "닉네임 넣기 : " + comment_nickname + logined_user.getString("nickname", ""));

               // String comment_nickname =  textView_comment_nickname.getText().toString(); // logined_user라는 이름의 쉐어드에서 닉네임을 가져온다.
                String comment_content = editText_comment_input.getText().toString();
//                int comment_profile = imageView_comment_profile.getResources();

                /**이미지**/
                String imageView_comment_profile = intent.getStringExtra("프로필");// feed클래스에서 comment 버튼 눌렀을 때 intent로 보낸 프사


                // 5. ArrayList에 추가하고

                comment_Data comment_data = new comment_Data (comment_nickname , comment_content , imageView_comment_profile, reviewUniqueCode);
                Log.e("comment 클래스에서 프로필 사진", "imageView_comment_profile :" + imageView_comment_profile );
                commentArrayList.add(comment_data);
                //mArrayList.add(dict); //마지막 줄에 삽입됨


                // 6. 어댑터에서 RecyclerView에 반영하도록 합니다.

                //commentAdapter.notifyItemInserted(0);
                commentAdapter.notifyDataSetChanged();

                comment_saveData();  // sharedPreference에 리뷰가 추가된 리사이클러뷰를 저장한다 // onCreate 밖에 메소드 만들었음.
                Log.e("comment 클래스에서 (사용자가 입력한 댓글 저장)", "comment_Data :" + comment_data + "commentArrayList :" + commentArrayList);

            }
        });

        // 뒤로 가기 버튼 눌렀을 때 피드(메인 화면)로 이동

        imageButton_back = findViewById(R.id.imageButton_back);
        imageButton_back.setOnClickListener(new ImageView.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent register_intent = new Intent(comment.this, feed.class);
                startActivity(register_intent); //액티비티 이동
                finish(); // 액티비티 finish 시킴

            }
        });


// 댓글 리사이클러뷰 아이템에 있는 다이얼로그 메뉴
//        PopupMenu popup = new PopupMenu(getApplicationContext(), v);//v는 클릭된 뷰를 의미
//
//        getMenuInflater().inflate(R.menu.myreview_menu, popup.getMenu());
//        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
//            @Override
//            public boolean onMenuItemClick(MenuItem item) {
//                switch (item.getItemId()) {
//                    case R.id.action_edit:
//                        // 리뷰 카드에 있는 메뉴 다이얼로그 (?) 중 수정하기를 눌렀을 때
//                        Toast.makeText(getApplication(), "수정하기", Toast.LENGTH_SHORT).show();

//                        Intent intent = new Intent(getApplicationContext(), edit_review.class);
//                        Log.e("Feed 클래스에서 리사이클러뷰 수정 작업중! ", "edit_review로 연결되는 인텐트를 가지고왔습니다.");
//
////
//                        intent.putExtra("URL", commentArrayList.get(position).textView_shoppingmall_url);
//                        intent.putExtra("DETAIL", commentArrayList.get(position).textView_detailed_review_card);
//                        intent.putExtra("HASHTAG", commentArrayList.get(position).textView_hashtag);
//                        intent.putExtra("WRITER", commentArrayList.get(position).textView_review_writer);
//                        intent.putExtra("NUMBER", commentArrayList.get(position).textView_reviewcard_number);
//
//
//                        intent.putExtra("POSITION", position);
//                        // 위치도 받아와야 수정한 데이터를 받아왔을 때 어떤 position에 있는 아이템에 set 해줄 건지 알려줄 수 있음
//
//
//                        Log.e("Feed 클래스에서 리사이클러뷰 수정 작업중! ", "URL : " + arrayList.get(position).textView_detailed_review_card);
//                        Log.e("Feed 클래스에서 리사이클러뷰 수정 작업중.", "기존에 있던 데이터가 넘어가나 확인중. DETAIL : " + arrayList.get(position).textView_detailed_review_card);
//
//                        startActivityForResult(intent, 2001);
//
//                        Log.e("Feed 클래스에서 리사이클러뷰 수정 작업중.", "startActivityForResult를 실행. requestCode 2001");
//
//                        //액티비티 이동, 여기서 2001은 식별자. 아무 숫자나 넣으주면 됨.

//
//                        break;
//                    case R.id.action_delete:
//
////                        remove(position);
//
//                        commentAdapter.notifyDataSetChanged();  // 새로고침
//                        Toast.makeText(getApplication(), "삭제되었습니다", Toast.LENGTH_SHORT).show();
//
//                        comment_saveData();  // sharedPreference에 리뷰가 추가된 리사이클러뷰를 저장한다 // onCreate 밖에 메소드 만들었음.
//                        Log.e("feed 클래스에서 (saveData)", "삭제 후   sharedpreference에 리사이클러뷰에 들어가는 arrayList 저장 :" + arrayList);
//
//
//                        return true;
//
//                    default:
//                        break;
//                }
//                return false;
//            }
//        });
//
//        popup.show();//Popup Menu 보이기
////            }
////        });




    }// onCreate 닫는 중괄호

    private void comment_saveData() {


//        Intent intent = getIntent();
//        int position = intent.getIntExtra("POSITION", 0000);
//        // 피드에서 댓글 버튼을 눌렀을 때 해당 아이템의 position
//        String uniqueKey = feed_adapter.getItem(position).getTextView_reviewcard_number();

        sharedPreferences = getSharedPreferences("sharedPreferences", MODE_PRIVATE);
        editor = sharedPreferences.edit();

//        commentShared = getSharedPreferences("commentShared",MODE_PRIVATE);
//        commentShared_editor = commentShared.edit();
        Gson gson = new Gson();
        String json = gson.toJson(commentArrayList);
        /** 여기서 줘야하는 키값이 각 리뷰 아이템이 갖고 있는 고유번호로 해주면 됨**/
//        commentShared_editor.putString(uniqueKey, json);
//        commentShared_editor.apply();
        editor.putString("commentShared", json);
        editor.apply();

    }



    private void comment_loadData() {
//
//        Intent intent = getIntent();
//        int position = intent.getIntExtra("POSITION", 0000);
//        // 피드에서 댓글 버튼을 눌렀을 때 해당 아이템의 position
//        String uniqueKey = feed_adapter.getItem(position).getTextView_reviewcard_number();

//        commentShared = getSharedPreferences("commentShared", MODE_PRIVATE);
//        commentShared_editor = commentShared.edit();

        sharedPreferences = getSharedPreferences("sharedPreferences", MODE_PRIVATE);

        Gson gson = new Gson();
        /** 여기서 줘야하는 키값이 각 리뷰 아이템이 갖고 있는 고유번호로 해주면 됨**/
//        String json = commentShared.getString(uniqueKey, null);
        String json = sharedPreferences.getString("commentShared", null);
        Type type = new TypeToken<ArrayList<comment_Data>>() {
        }.getType();
        commentArrayList = gson.fromJson(json, type);

        if (commentArrayList == null) {
            commentArrayList = new ArrayList<>();
        }
    }

    public void remove(int position) {
        // 피드 리사이클러뷰 안에 있는 리뷰를 삭제할 때 쓰는 remove 메소드

        try {
            commentArrayList.remove(position);
            commentAdapter.notifyItemRemoved(position);


            // sharedPreference 에서 삭제하는 코드를 넣어줘야 함.... 굳이? arrayList에서 없애주면 되는거 아닌가?

        } catch (IndexOutOfBoundsException e) {
            e.printStackTrace();
        }
    }

//



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
