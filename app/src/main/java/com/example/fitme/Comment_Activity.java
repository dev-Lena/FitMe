package com.example.fitme;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

import static com.example.fitme.Feed_Main_Activity.testkey;

public class Comment_Activity extends AppCompatActivity {
    /**
     * 각 리뷰의 댓글 (리사이클러뷰)
     **/

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    ArrayList<Feed_Main_ItemData> arrayList, bookmarked_arrayList, myreview_arrayList;
    Feed_Main_Adapter feedMain_adapter;

    BottomNavigationView bottomNavigationView; // 바텀(Bottom) 네이게이션 메뉴  -> 하단바
    ImageButton imageButton_comment_save;

    // 로그인 하고 있는 유저의 정보를 담고 있는 쉐어드 프리퍼런스
    private ArrayList<List> userData = new ArrayList<List>();

    private SharedPreferences logined_user;
    private SharedPreferences.Editor user_editor;

    private SharedPreferences commentShared;
    private SharedPreferences.Editor commentShared_editor;

    TextView textView_comment_nickname, textView_unique_code;
    ImageView imageView_comment_profile;
    ImageButton imageButton_back;
    EditText editText_comment_input;
    /**
     * 리사이클러뷰
     **/
    ArrayList<Comment_ItemData> commentArrayList, comment_show_arrayList;
    Comment_Adapter Comment_Adapter;
    RecyclerView commentRecyclerview;
    LinearLayoutManager mlinearLayoutManager;

    int position;
    String reviewUniqueCode;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        comment_loadData();  // sharedpreference에 저장한 comment_arrayList (리사이클러뷰)를 가지고 옴(로드 load).

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);

        /**리사이클러뷰**/

        commentRecyclerview = (RecyclerView) findViewById(R.id.commentRecyclerview);
        mlinearLayoutManager = new LinearLayoutManager(this);
        mlinearLayoutManager.setReverseLayout(false); // 최신순으로 리사이클러뷰 아이템 추가.
        commentRecyclerview.setLayoutManager(mlinearLayoutManager);
        comment_show_arrayList = new ArrayList<>(); // 사용자가 한 리뷰의 댓글 버튼 클릭시 해당 리뷰에 대한 댓글들만 담아놓는 ArrayList.
        Intent intent = getIntent();
        position = intent.getIntExtra("포지션", 0000); // 리사이클러뷰 아이템의 포지션
        textView_unique_code = findViewById(R.id.textView_unique_code);

        logined_user = getSharedPreferences("logined_user", MODE_PRIVATE);
        String user_email = logined_user.getString("user_email", "");
        String email = user_email;

        filter();

        Comment_Adapter = new Comment_Adapter(comment_show_arrayList, context);
        // commentArrayList에서 해당 리뷰 아이템에 대한 댓글만 따로 담은 comment_show_arrayList를 보여줌.
        commentRecyclerview.setAdapter(Comment_Adapter);


        textView_comment_nickname = (TextView) findViewById(R.id.textView_comment_nickname);
        imageView_comment_profile = (ImageView) findViewById(R.id.imageView_comment_profile);
        editText_comment_input = (EditText) findViewById(R.id.editText_comment_input);
        textView_unique_code = findViewById(R.id.textView_unique_code);
        logined_user = getSharedPreferences("logined_user", MODE_PRIVATE);

        // 리사이클러뷰 아이템에 있는 우측 상단 다이얼로그 메뉴 누르는 클릭 리스너
        Comment_Adapter.setOnItemClickListener(new Comment_Adapter.OnItemClickListener() {
                                                  @Override
                                                  public void onItemClick(View v, final int position) {

                                                      // 리뷰 아이템의 작성자와 현재 로그인한 회원의 이메일이 같을 때
                                                      // -> 수정, 삭제, 공유가 가능한 메뉴를 띄워라
                                                      logined_user = getSharedPreferences("logined_user", MODE_PRIVATE);
                                                      if (comment_show_arrayList.get(position).getTextView_comment_nickname().equals(logined_user.getString("user_nickname", ""))) {

                                                          // 리사이클러뷰 아이템 안에 버튼을 누르면 팝업 메뉴 뜨도록
                                                          PopupMenu popup = new PopupMenu(Comment_Activity.this, v);//v는 클릭된 뷰를 의미

                                                          getMenuInflater().inflate(R.menu.comment_menu, popup.getMenu());
                                                          popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                                                              String feed_id = logined_user.getString("user_nickname", "");

                                                              @Override
                                                              public boolean onMenuItemClick(MenuItem item) {

                                                                  String feed_id = logined_user.getString("user_nickname", "");
                                                                  switch (item.getItemId()) {
                                                                      case R.id.action_edit:
                                                                          // 수정하기
                                                                          Toast.makeText(Comment_Activity.this, "댓글 수정하기", Toast.LENGTH_SHORT).show();

                                                                          AlertDialog.Builder builder = new AlertDialog.Builder(Comment_Activity.this);

                                                                          View view = LayoutInflater.from(Comment_Activity.this)
                                                                                  .inflate(R.layout.edit_box, null, false);
                                                                          builder.setView(view);
                                                                          final Button ButtonSubmit = (Button) view.findViewById(R.id.button_dialog_submit);
                                                                          final EditText editTextEnglish = (EditText) view.findViewById(R.id.edittext_dialog_endlish);

                                                                          //해당 줄에 입력되어 있던 데이터를 불러와서 다이얼로그에 보여줍니다.

                                                                          editTextEnglish.setText(comment_show_arrayList.get(position).getTextView_comment_content());

                                                                          final String comment_nickname = comment_show_arrayList.get(position).getTextView_comment_nickname();
                                                                          final AlertDialog dialog = builder.create();

                                                                          String comment_Comment = comment_show_arrayList.get(position).getTextView_comment_content();
                                                                          editTextEnglish.setText(comment_Comment);

                                                                          ButtonSubmit.setOnClickListener(new View.OnClickListener() {


                                                                              // 7. 수정 버튼을 클릭하면 현재 UI에 입력되어 있는 내용으로

                                                                              public void onClick(View v) {

                                                                                  // 댓글 데이터 로드(load)
                                                                                  comment_loadData();

                                                                                  String comment_content = editTextEnglish.getText().toString();
                                                                                  String comment_image = comment_show_arrayList.get(position).getImageView_comment_profile();
                                                                                  String comment_unique_code = commentArrayList.get(position).getReviewUniqueCode();
                                                                                  String comment_nick = comment_show_arrayList.get(position).getTextView_comment_nickname();

                                                                                  Comment_ItemData comment_Item_data = new Comment_ItemData(comment_nick, comment_content, comment_image, comment_unique_code);

                                                                                  TextView textView_comment_content = findViewById(R.id.textView_comment_content);
                                                                                  textView_comment_content.setText(comment_content);

                                                                                  // arrayList에 있는 데이터를 변경하고
                                                                                  commentArrayList.set(position, comment_Item_data);
                                                                                  comment_show_arrayList.set(position, comment_Item_data);

                                                                                  // 어댑터에서 RecyclerView에 반영하도록 합니다.
                                                                                  Comment_Adapter.notifyDataSetChanged();
                                                                                  comment_saveData();

                                                                                  dialog.dismiss();

                                                                              }
                                                                          });

                                                                          dialog.show();

                                                                          break;

                                                                      case R.id.action_delete: // 삭제하기

                                                                          // 댓글 데이터 로드(load)
                                                                          comment_loadData();
                                                                          remove(position);  // 해당 리뷰가 있는 리사이클러뷰 아이템을 삭제.

                                                                          Comment_Adapter.notifyDataSetChanged();  // 새로고침
                                                                          Toast.makeText(Comment_Activity.this, "삭제되었습니다", Toast.LENGTH_SHORT).show();

                                                                          comment_saveData(); // sharedPreference에 리뷰가 추가된 리사이클러뷰를 저장한다 // onCreate 밖에 메소드 만들었음.


                                                                          Log.e("Feed_Main_Activity 클래스에서 (saveData)", "삭제 후   sharedpreference에 리사이클러뷰에 들어가는 arrayList 저장 :" + arrayList);
                                                                          return true;


                                                                      default:
                                                                          break;
                                                                  }
                                                                  return false;
                                                              }
                                                          });

                                                          popup.show();//Popup Menu 보이기
                                                      }   // 작성자와 로그인한 유저의 이메일이 맞을 때 if문 닫는 중괄호 -> feed_menu
                                                      else {  // 작성자와 로그인한 유저의 이메일이 맞지 않을 때 if문 -> bookmarked_menu
                                                          PopupMenu popup = new PopupMenu(Comment_Activity.this, v);//v는 클릭된 뷰를 의미

                                                          getMenuInflater().inflate(R.menu.otheruser_comment_menu, popup.getMenu());
                                                          popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {

                                                              @Override
                                                              public boolean onMenuItemClick(MenuItem item) {
                                                                  logined_user = getSharedPreferences("logined_user", MODE_PRIVATE);
                                                                  String feed_id = logined_user.getString("user_nickname", "");
                                                                  switch (item.getItemId()) {

                                                                      case R.id.action_report:
                                                                          //신고하기
                                                                          remove(position);
                                                                          Comment_Adapter.notifyDataSetChanged();  // 새로고침
                                                                          comment_saveData();

                                                                          Log.e("Comment_Activity 클래스에서", "신고 완료" );

                                                                          Toast.makeText(Comment_Activity.this, "신고되었습니다", Toast.LENGTH_SHORT).show();

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


                                              }
        );


// 댓글 작성 완료 버튼을 눌렀을 때
        imageButton_comment_save = findViewById(R.id.imageButton_comment_save);
        imageButton_comment_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = getIntent();

                reviewUniqueCode = intent.getStringExtra("댓글작성한리뷰의고유번호");
                // 피드에서 댓글 버튼을 눌렀을 때 해당 아이템의 고유번호를 (메인 액티비티 - 피드)Feed_Main_Activity 클래스에서 코멘트 버튼을 눌렀을 때 넘겨준 데이터를 받음
                Log.e("Comment_Activity 클래스에서 ", "댓글 추가. 고유번호 : " + reviewUniqueCode);

                // 로그인한 회원의 정보를 가지고 있는 쉐어드에서 정보를 빼와서 글을 등록할 때 닉네임, 평소 사이즈를 불러오도록 했음.
                logined_user = getSharedPreferences("logined_user", Context.MODE_PRIVATE);
                // 현재 로그인한 회원의 정보만 담겨있는 쉐어드를 불러와서
                // 불러온 sharedPreferences라는 이름의 SharedPreferencs를 확인하는 로그
                Log.e("Comment_Activity 클래스에서  ", "댓글 추가. 로그인한 회원의 정보가 있는 쉐어드인 logined_user 쉐어드를 가져온다" + logined_user);

                // sharedPreferences라는 이름의 쉐어드프리퍼런스에서 String을 가져오는데
                // 뭘 가져오냐면 사용자가 입력한 editText_email랑 같은 값을 찾아서 가져와서 String json이라는 변수에 넣어줌
                String json = logined_user.getString("login_user", "");
                // logined_user라는 쉐어드에 저장되어있는 logined_user라는 키에 담겨있는 값을 불러와서 json이라는 변수에 담음
                Log.e("Comment_Activity 클래스에서 ", "댓글 추가 여기 확인하기 : " + json);

                // 로그인할 때 로그인한 회원의 정보를 배열로 가지고 와서 추출 후 각각의 key값을 줘서 저장했던 value를 호출
                String comment_nickname = logined_user.getString("user_nickname", "");
                Log.e("Comment_Activity 클래스에서 ", "댓글 추가 로그인한 회원 정보가 있는 쉐어드에서 닉네임 넣기 : " + comment_nickname + logined_user.getString("nickname", ""));

                 String comment_content = editText_comment_input.getText().toString();

                /**이미지**/
                String imageView_comment_profile = logined_user.getString("user_profileimage", "");

                // ArrayList에 추가하고
                Comment_ItemData comment_Item_data = new Comment_ItemData(comment_nickname, comment_content, imageView_comment_profile, reviewUniqueCode);
                Log.e("Comment_Activity 클래스에서 프로필 사진", "imageView_comment_profile :" + imageView_comment_profile);
                commentArrayList.add(comment_Item_data);

                // 어댑터에서 RecyclerView에 반영하도록 합니다.
                Comment_Adapter.notifyDataSetChanged();

                comment_saveData();  // sharedPreference에 리뷰가 추가된 리사이클러뷰를 저장한다
                Log.e("Comment_Activity 클래스에서 (사용자가 입력한 댓글 저장)", "Comment_ItemData :" + comment_Item_data + "commentArrayList :" + commentArrayList);


                finish();
                overridePendingTransition(0, 0);
                startActivity(getIntent());
                overridePendingTransition(0, 0);
            }
        });


        // 뒤로 가기 버튼 눌렀을 때 피드(메인 화면)로 이동

        imageButton_back = findViewById(R.id.imageButton_back);
        imageButton_back.setOnClickListener(new ImageView.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent register_intent = new Intent(Comment_Activity.this, Feed_Main_Activity.class);
                startActivity(register_intent); //액티비티 이동
                finish(); // 액티비티 finish 시킴


            }
        });


    }// onCreate 닫는 중괄호

    private void comment_saveData() {


        sharedPreferences = getSharedPreferences("sharedPreferences", MODE_PRIVATE);
        editor = sharedPreferences.edit();

        Gson gson = new Gson();
        String json = gson.toJson(commentArrayList);
        String jjson = gson.toJson(comment_show_arrayList);
        /** 키값 - 각 리뷰 아이템이 갖고 있는 고유번호로**/
        editor.putString("commentShared", jjson);
        editor.putString("commentShared", json);
        editor.apply();

    }


    private void comment_loadData() {
        // 댓글 데이터를 로드 load

        logined_user = getSharedPreferences("logined_user", MODE_PRIVATE);
        String user_email = logined_user.getString("user_email", "");
        Gson gson = new Gson();
        /** 키값 - 각 리뷰 아이템이 갖고 있는 고유번호로**/
        String json = sharedPreferences.getString("commentShared", null);

        Type type = new TypeToken<ArrayList<Comment_ItemData>>() {
        }.getType();
        commentArrayList = gson.fromJson(json, type);

        if (commentArrayList == null) {
            commentArrayList = new ArrayList<>();
        }
        if (comment_show_arrayList == null) {
            comment_show_arrayList = new ArrayList<>();
        }
    }

    public void remove(int position) {
        // 리뷰 피드(Feed_Main_Activity) 리사이클러뷰 안에 있는 리뷰를 삭제
        try {
            commentArrayList.remove(position);
            comment_show_arrayList.remove(position);
            Comment_Adapter.notifyItemRemoved(position);


            // sharedPreference 에서 삭제하는 코드를 넣어줘야 함.... 굳이? arrayList에서 없애주면 되는거 아닌가?

        } catch (IndexOutOfBoundsException e) {
            e.printStackTrace();
        }
    }

    public void filter() { // 메인 액티비티인 리뷰 피드(Feed_Main_Activity)에서 해당 리사이클러뷰 아이템의 댓글들만 가져와 담는 필터
        for (Comment_ItemData item : commentArrayList) {// item이라는 Comment_Activity Data를 commentArrayList 안을 순회하면서 비교하면서
            if (item.getReviewUniqueCode().equals(testkey)) {
                // 여기서 testkey는 Feed_Main_Activity 클래스에서 static으로 선언한 String 변수.
                // testkey는 feed클래스의 onCommentClick메소드에서 댓글 아이콘 버튼을 누르면 해당 리사이클러뷰 아이템의 고유 번호로 설정됨.
                // if문 확인 -> Comment_Activity Data 가 갖고있는 아이템에서 testkey와 리사이클러뷰 아이템의 고유번호가 같으면
                // 해당 리사이클러뷰 아이템의 댓글들만 보여주는 arrayLIst에 추가하라.
                comment_show_arrayList.add(item);
            }

        }
    }


    @Override
    protected void onRestart() {
        super.onRestart();
        Log.e("Myreview_Activity", "onRestart");
        //액티비티가 중단되었다가 다시 시작
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.e("Myreview_Activity", "onStart");
        //액티비티가 화면에 나타나기 시작
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e("Myreview_Activity", "onResume");
        Comment_Adapter.notifyDataSetChanged();

        //액티비티가 화면에 나타나고 상호작용이 가능해짐
    }

    @Override
    protected void onPause() {
        super.onPause();
        overridePendingTransition(0, 0);
        Log.e("Myreview_Activity", "onPause");
        //다른 액티비티가 시작되려함, 이 액티비티는 중단되려하고 백그라운드로 들어갑니다.
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.e("Myreview_Activity", "onStop");
        //액티비티가 더 이상 화면에 나타나지 않음,중단된 상태
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e("Myreview_Activity", "onDestroy");
        //액티비티가 종료되려고 합니다.
    }


}
