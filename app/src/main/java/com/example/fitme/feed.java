package com.example.fitme;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.ClipData;
import android.content.DialogInterface;
import android.content.Intent;
import android.icu.text.Transliterator;
import android.os.Bundle;
import android.provider.SyncStateContract;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class feed extends AppCompatActivity {

    MenuItem action_write_review; // -> 하단 바에 있는 리뷰 작성 버튼
    //    ImageButton imageButton_review_register;
    BottomNavigationView bottomNavigationView; // 바텀 네이게이션 메뉴  -> 하단바
    BottomNavigationView bottomNavigationMenu; // 바텀 네이게이션 메뉴  -> 하단바
    ImageView imageView_recommendbot, imageView_notification;

    private final int Write_OK = 1001;
    /**
     * 리사이클러뷰에 필요한 기본 객체 선언
     **/
    ArrayList<feed_MainData> arrayList;
    feed_Adapter feed_adapter;
    RecyclerView recyclerView;
    LinearLayoutManager linearLayoutManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {


        Log.e("feed", "onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed);


        imageView_recommendbot = findViewById(R.id.imageView_recommendbot);  // 추천봇
        imageView_notification = findViewById(R.id.imageView_notification);  // 알림 (내 소식))

//        arrayList.add(new feed_MainData("textView_shoppingmall_url","textView_detailed_review_card"))

        /**여기서부터 리사이클러뷰 만들기**/

        recyclerView = (RecyclerView) findViewById(R.id.feed_recyclerView);
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        arrayList = new ArrayList<>();

        feed_adapter = new feed_Adapter(arrayList);
        recyclerView.setAdapter(feed_adapter);





// Swipe를 통해서 삭제하기 위해서 ItemTouchHelper를 사용했는데 이곳에 객체 선언 -> 뒤에 ItemTouchHelper 메소드 있음.
        new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(recyclerView);


// 리사이클러뷰 수정 // 클릭하면 수정 화면 열리고 수정한 데이터를 인텐트로 가지고 오는 것.
// 리사이클러뷰 수정에서 Adpater에서 커스텀한 클릭이벤트를 인터페이스로 가지고 와서 여기서 intent로 받아올 것.
        // 액티비티에서 커스텀 리스너 객체 생성 및 전달

//        feed_adapter.setOnItemClickListener(new feed_Adapter.OnItemClickListener() {

        feed_adapter.setOnItemClickListener(new feed_Adapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, final int position) {
// TODO : 아이템 클릭 이벤트를 MainActivity에서 처리.
// 리사이클러뷰 수정
// 다이얼로그

//                String textView_shoppingmall_url = editText_edit_shoppingmall_url.getText().toString();
//                String textView_detailed_review_card = editText_edit_detailed_review.getText().toString();
//                String editText_shoppingmall_url = textView_shoppingmall_url.getText().toString();
//                String editText_detailed_review  = editText_edit_detailed_review.getText().toString();

// 리사이클러뷰 아이템 안에 버튼을 누르면 팝업 메뉴 뜨도록
                PopupMenu popup= new PopupMenu(getApplicationContext(), v);//v는 클릭된 뷰를 의미

                getMenuInflater().inflate(R.menu.reviewcard_menu, popup.getMenu());
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()){
                            case R.id.action_edit:
                                // 리뷰 카드에 있는 메뉴 다이얼로그 (?) 중 수정하기를 눌렀을 때
                                Toast.makeText(getApplication(),"수정하기",Toast.LENGTH_SHORT).show();

                                Intent intent = new Intent(getApplicationContext(), edit_review.class);
                                Log.e("Feed 클래스에서 리사이클러뷰 수정 작업중! ", "edit_review로 연결되는 인텐트를 가지고왔습니다.");

//
                                intent.putExtra("URL", arrayList.get(position).textView_shoppingmall_url);
                                intent.putExtra("DETAIL", arrayList.get(position).textView_detailed_review_card);
                                intent.putExtra("POSITION",position);
                                // 위치도 받아와야 수정한 데이터를 받아왔을 때 어떤 position에 있는 아이템에 set 해줄 건지 알려줄 수 있음


                                Log.e("Feed 클래스에서 리사이클러뷰 수정 작업중! ", "URL : " + arrayList.get(position).textView_detailed_review_card);
                                Log.e("Feed 클래스에서 리사이클러뷰 수정 작업중.", "기존에 있던 데이터가 넘어가나 확인중. DETAIL : " + arrayList.get(position).textView_detailed_review_card);

                                startActivityForResult(intent, 2001);

                                Log.e("Feed 클래스에서 리사이클러뷰 수정 작업중.", "startActivityForResult를 실행. requestCode 2001");

                                //액티비티 이동, 여기서 2001은 식별자. 아무 숫자나 넣으주면 됨.


                                break;
                            case R.id.action_delete:
                                Toast.makeText(getApplication(),"삭제하기",Toast.LENGTH_SHORT).show();

//
//                                arrayList.remove(viewHolder.getAdapterPosition());   // 데이터(리사이클러뷰 아이템)를 담고 있는 arrayList에서 아이템을 없앨건데, viewHolder. Adpater에서 위치를 찾고 그 위치에 있는 아이템을 없앰.
//                                feed_adapter.notifyItemRemoved(viewHolder.getAdapterPosition());
//                                feed_adapter.notifyDataSetChanged();            // 위에서     recyclerView.setAdapter(feed_adapter); 어댑터라고 set한 리사이클러뷰인 feed_adapter를 새로고침함. 변화된 정보를 인지시키고 새로고침 시킴
//                                Toast.makeText(feed.this, "리뷰를 피드에서 삭제했습니다", Toast.LENGTH_SHORT).show();
//                                Log.e("Swipe", "스와이프해서 아이템을 지웠습니다");
//
                                break;

                            case R.id.action_share:
                                Toast.makeText(getApplication(),"공유하기",Toast.LENGTH_SHORT).show();
                                break;
                            case R.id.action_report:
                                Toast.makeText(getApplication(),"신고하기",Toast.LENGTH_SHORT).show();
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
//                    }
//                });
//                        startActivity(write_intent);//액티비티 띄우기
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






//    swipe to delete & drag to move
//    onCreate 밖에


//Swipe해서 리사이클러뷰 아이템을 삭제하기 위해서 ItemTouchHelper 메소드 사용. -> Callback 키워드 활용해야함. -> 자동완성시키면 onMove 메소드와 onSwiped 메소드가 생김.
    // 리사이클러뷰 기본으로 만들어준 LayoutManager, setAdapter 등을 하는 곳에 ItemTouchHelper 객체 선언해줘야 함.
    ItemTouchHelper.SimpleCallback itemTouchHelperCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT | ItemTouchHelper.LEFT) {  // 오른쪽으로 Swipe했을 때 또는 왼쪽으로 Swipe 했을 때
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull final RecyclerView.ViewHolder viewHolder, int direction) {

                AlertDialog.Builder alt_bld = new AlertDialog.Builder(feed.this);
                alt_bld.setMessage("리뷰를 회원님의 피드에서 삭제하시겠습니까?").setCancelable(
                        false).setPositiveButton("네",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // Action for 'Yes' Button

                                arrayList.remove(viewHolder.getAdapterPosition());   // 데이터(리사이클러뷰 아이템)를 담고 있는 arrayList에서 아이템을 없앨건데, viewHolder. Adpater에서 위치를 찾고 그 위치에 있는 아이템을 없앰.
                                feed_adapter.notifyItemRemoved(viewHolder.getAdapterPosition());
                                feed_adapter.notifyDataSetChanged();            // 위에서     recyclerView.setAdapter(feed_adapter); 어댑터라고 set한 리사이클러뷰인 feed_adapter를 새로고침함. 변화된 정보를 인지시키고 새로고침 시킴
                                Toast.makeText(feed.this, "리뷰를 피드에서 삭제했습니다", Toast.LENGTH_SHORT).show();
                                Log.e("Swipe", "스와이프해서 아이템을 지웠습니다");
                            }

                        }).setNegativeButton("아니오",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // Action for 'NO' Button
                                feed_adapter.notifyDataSetChanged();
                                dialog.cancel();
                            }
                        });
                AlertDialog alert = alt_bld.create();
                // Title for AlertDialog
                alert.setTitle("리뷰 삭제");
                // Icon for AlertDialog
                alert.setIcon(R.drawable.ic_delete_black_24dp);
                alert.show();
            }
    };


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


//            feed_adapter = new feed_Adapter();
            // 사용자가 입력한 내용을 가져와서
            String textView_shoppingmall_url = data.getStringExtra("쇼핑몰URL");
            Log.e("쇼핑몰URL", textView_shoppingmall_url + "쇼핑몰URL 가져왔습니다!!!!!!!!!");
            String textView_detailed_review_card = data.getStringExtra("상세리뷰");
            Log.e("상세리뷰", textView_detailed_review_card + "상세리뷰 가져왔습니다!!!!!!!");
            // ArrayList에 추가하고


            Log.e("add", "arrayList에 넣었습니다");
            feed_MainData feed_MainData = new feed_MainData(textView_shoppingmall_url, textView_detailed_review_card);
            Log.e("add", textView_detailed_review_card + "feed_MainData 객체 생성");


            arrayList.add(feed_MainData);
            Log.e("add", textView_detailed_review_card + "리사이클러뷰의 arrayList에 아이템 추가");

            feed_adapter.notifyDataSetChanged();  // 새로고침
            Log.e("add", textView_detailed_review_card + "새로고침");


        }

        if (requestCode == 2001 && resultCode == RESULT_OK) {
            Toast.makeText(feed.this, "리뷰 수정을 완료했습니다!", Toast.LENGTH_SHORT).show();


            // 리뷰 수정에서 보낸 수정한 데이터 가져오기 / 받아오기
            // 사용자가 수정한 내용을 가져와서
            String textView_shoppingmall_url = data.getStringExtra("쇼핑몰URL");
            Log.e("쇼핑몰URL", textView_shoppingmall_url + "수정한 쇼핑몰URL 가져왔습니다");

            String textView_detailed_review_card = data.getStringExtra("상세리뷰");
            Log.e("상세리뷰", textView_detailed_review_card + "수정한 상세리뷰 가져왔습니다");

            int position = data.getIntExtra("POSITION",0000);
            Log.e("위치값", position + " 위치값을 가지고 왔습니다");

            //
            // ArrayList에 추가하고
            feed_MainData feed_MainData = new feed_MainData(textView_shoppingmall_url, textView_detailed_review_card);
            Log.e("edit", "ArryaList 중 이곳에 데이터를 넣을껍니다" +textView_shoppingmall_url+ ","+textView_detailed_review_card);


            // 그 위치를 받아와서 그곳에 set 해주기. 리뷰 수정 버튼을 누를 때 부터 같이 위치값을 startActivityForResult로 같이 넘겼다가 돌려받음.
            arrayList.set(position,feed_MainData);
//
//            arrayList.add(feed_MainData);
//            Log.e("edit", textView_detailed_review_card + "리사이클러뷰의 arrayList에 아이템 추가");

//            feed_adapter.notifyItemRemoved(getAdapterPosition());  //아이템이 삭제한 것을 notify
            feed_adapter.notifyDataSetChanged();  // 새로고침
            Log.e("edit",  "수정한거 새로고침");
        }
    }//onActivityResult 메소드 닫는 중괄호

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
        Log.e("add",  "수정한거 새로고침");
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
