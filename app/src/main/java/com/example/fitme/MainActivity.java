package com.example.fitme;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**ExampleAdapter와 ExampleItem, MainActivity, DetailActivity 자바 클래스는 네이버 Shop API 한세트임**/


public class MainActivity extends AppCompatActivity implements ExampleAdapter.OnItemClickListener  {
    /**이미지 검색 액티비티**/



    public static final String EXTRA_TITLE = "title";
    public static final String EXTRA_MALLNAME = "mallName";
    public static final String EXTRA_LINK = "link";

    RecyclerView mRecyclerView;
    ExampleAdapter mExampleAdapter;
    ArrayList<ExampleItem> mExampleList;
//    private RequestQueue mRequestQueue;

    EditText edittext_image_searching;
    Button button_image_searching;
    TextView textView_image_searching;
    ImageButton imageButton_back;


    String  apiURL, s_response, json;
    String receiveMsg;
    java.net.URL url;

    String search_keyword;
TextView yogi;
    //    // 네이버 검색
//    private static final String CLIENT_ID = "input your client_id";
//    private static final String CLIENT_SECRET = "input your client_secret";
//    private static final String URL = "https://openapi.naver.com/v1/search/shop.json";
final int display = 5; // 보여지는 검색결과의 수

//    APIExamSearchShop apiExamSearchShop = new APIExamSearchShop();  //클래스 위치 // return
BottomNavigationView bottomNavigationView; // 바텀 네이게이션 메뉴  -> 하단바
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_naver_shop_api);

        mRecyclerView = findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mExampleList = new ArrayList<>();

        edittext_image_searching = findViewById(R.id.edittext_image_searching);
        button_image_searching = findViewById(R.id.button_image_searching);
        textView_image_searching = findViewById(R.id.textView_image_searching);
        imageButton_back= findViewById(R.id.imageButton_back);

        // 뒤로 가기 버튼 눌렀을 때 피드(메인 화면)로 이동

        imageButton_back = findViewById(R.id.imageButton_back);
        imageButton_back.setOnClickListener(new ImageView.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent register_intent = new Intent(MainActivity.this, feed.class);
                startActivity(register_intent); //액티비티 이동
                finish(); // 액티비티 finish 시킴

            }
        });
//
//        search_keyword = edittext_image_searching.getText().toString(); // 사용자가 입력한 검색어를 받아서

//        mQueue = VolleySingleton.getInstance(this).getRequestQueue();//https://codinginflow.com/tutorials/android/volley/part-2-singleton-pattern
//       final String search_keyword = edittext_image_searching.getText().toString(); // 사용자가 입력한 검색어를 받아서

        button_image_searching.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                search_keyword = edittext_image_searching.getText().toString(); // 사용자가 입력한 검색어를 받아서

                        new Thread() { // 네트워크 통신을 위해 쓰레드를 돌려야함
            @Override
            public void run() {

//                getJSON(search_keyword);

                try {

                    search_keyword = edittext_image_searching.getText().toString(); // 사용자가 입력한 검색어를 받아서
                    APIExamSearchShop apiExamSearchShop = new APIExamSearchShop();  //클래스 위치 // return
                    apiExamSearchShop.naver_search_api(search_keyword); //main run -> another class named APIExamSearchShop
//
//
                String result_data = apiExamSearchShop.naver_search_api(search_keyword);  //searchKeyword 로 검색한 결과를 리턴한걸 String으로 받아서 넣어줌 . data임
                String searched_url = apiURL;
                    Log.e("MainActivity result_data------","result_data : " +result_data);
                    Log.e("MainActivity searched_url","searched_url" + searched_url);

//
//                /**여기서 부터 로그가 찍히지 않아요**/
//
                    JSONObject jsonObj = new JSONObject(result_data);
                    Log.e("MainActivity 클래스","jsonObj : " + jsonObj);
                    JSONArray jsonArray = jsonObj.getJSONArray("items");  //JsonObject에 items라는 key를가지고 있는 JsonArray를 가지고 온다
                    Log.e("MainActivity 클래스","jsonArray : " + jsonArray);
                    for (int i = 0; i < jsonArray.length(); i++) {  // items라는 키에 담겨있던 jsonArray의 길이만큼 for문을 돌려서
                        JSONObject item = jsonArray.getJSONObject(i);                  //인덱스 i 에 있는 JsonObject를 가지고 와 jsonObject item에 넣어준다
                        Log.e("MainActivity 클래스","item : " + item);

                        String title = (item.getString("title")).replace("<b>","").replace(search_keyword,"").replace("<\b>","");   // jsonObject item에서 title이라는 key를 가진 String value를 가지고 와서 title이라는 String 변수에 담아준다
                        String link = item.getString("link");
                        String image = item.getString("image");
                        String mallName= item.getString("mallName");
                        String lprice = item.getString("lprice");
                        String hprice = item.getString("hprice");

                        Log.e("MainActivity 클래스","title : " + title);
                        Log.e("MainActivity 클래스","link :" + link);
                        Log.e("MainActivity 클래스","image : " + image);
                        Log.e("MainActivity 클래스","mallName : " + mallName);
                        Log.e("MainActivity 클래스","lprice : " + lprice);
                        Log.e("MainActivity 클래스","hprice : " + hprice);

                        ExampleItem exampleItem = new ExampleItem(title, link, image, mallName,lprice, hprice);
                        mExampleList.add(exampleItem);  //리사이클러뷰의 arrayList에 아이템 추가
                        Log.e("MainActivity 클래스","mExampleList : " + mExampleList);




                    }

//                    mExampleAdapter = new ExampleAdapter(MainActivity.this, mExampleList);
//
//                    mExampleAdapter.notifyDataSetChanged();  // 새로고침
//
//                    mRecyclerView.setAdapter(mExampleAdapter);
//                    mExampleAdapter.setOnItemClickListener(MainActivity.this);

                } catch (Exception e) {
                    System.out.println(e);
                    Log.e("e------------------ㅠㅠ------------------------","e"+e);
                }
            }
        }.start();
//                mExampleAdapter.notifyDataSetChanged();  // 새로고침


                mExampleAdapter = new ExampleAdapter(MainActivity.this, mExampleList);

//                mExampleAdapter.notifyDataSetChanged();  // 새로고침

                mRecyclerView.setAdapter(mExampleAdapter);
                mExampleAdapter.setOnItemClickListener(MainActivity.this);

            }


        });



        bottomNavigationView = findViewById(R.id.bottomNavi);
        // 하단바 누를 때 색 바뀌게 하는 중
        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(3);
        menuItem.setChecked(true);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.action_home :
                        Intent home_intent = new Intent(MainActivity.this,feed.class);
                        home_intent.addFlags (Intent.FLAG_ACTIVITY_NO_ANIMATION);
                        startActivity(home_intent);//액티비티 띄우기
//                        startActivityForResult(intent,sub);//액티비티 띄우기
                        break;
                    case R.id.action_search :
                        Intent search_intent = new Intent(MainActivity.this,searching.class);
                        search_intent.addFlags (Intent.FLAG_ACTIVITY_NO_ANIMATION);
                        startActivity(search_intent);//액티비티 띄우기
                        break;
                    case R.id.action_insight :
                        Intent write_intent = new Intent(MainActivity.this,insight.class);
                        write_intent.addFlags (Intent.FLAG_ACTIVITY_NO_ANIMATION);
                        startActivity(write_intent);//액티비티 띄우기
                        break;
                    case R.id.action_notification :
                        Intent insight_intent = new Intent(MainActivity.this,MainActivity.class);
                        insight_intent.addFlags (Intent.FLAG_ACTIVITY_NO_ANIMATION);
                        startActivity(insight_intent);//액티비티 띄우기
                        break;
                    case R.id.action_mypage :
                        Intent mycloset_intent = new Intent(MainActivity.this, mypage.class);
                        mycloset_intent.addFlags (Intent.FLAG_ACTIVITY_NO_ANIMATION);
                        startActivity(mycloset_intent);//액티비티 띄우기
                        break;
                }

                return false;
            }
        });



    }//onCeate 닫는 중괄호

    @Override
    public void onItemClick(int position) {  // 리사이클러뷰 아이템을 클릭했을 때
        Intent detailIntent = new Intent(this, DetailActivity.class);
        ExampleItem clickedItem = mExampleList.get(position);

        detailIntent.putExtra(EXTRA_TITLE , clickedItem.getTitle());
        detailIntent.putExtra(EXTRA_MALLNAME, clickedItem.getMallName());
        detailIntent.putExtra(EXTRA_LINK, clickedItem.getLink());


        startActivity(detailIntent);
    }


    @Override
    protected void onResume() {
        super.onResume();
        Log.e("mypage", "onResume");
//        mExampleAdapter.notifyDataSetChanged();  // 새로고침
        //액티비티가 화면에 나타나고 상호작용이 가능해짐
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.e("mypage", "onPause");
        overridePendingTransition(0, 0);
        //다른 액티비티가 시작되려함, 이 액티비티는 중단되려하고 백그라운드로 들어갑니다.
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.e("mypage", "onPause");
//        finish();
//        overridePendingTransition(0,0);
        //다른 액티비티가 시작되려함, 이 액티비티는 중단되려하고 백그라운드로 들어갑니다.
    }



}


