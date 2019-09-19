package com.example.fitme;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements ExampleAdapter.OnItemClickListener  {
    /**이미지 검색 액티비티**/


    public static final String EXTRA_URL = "imageUrl";
    public static final String EXTRA_CREATOR = "creatorName";
    public static final String EXTRA_LIKES = "likeCount";

    private RecyclerView mRecyclerView;
    private ExampleAdapter mExampleAdapter;
    private ArrayList<ExampleItem> mExampleList;
    private RequestQueue mRequestQueue;

    EditText edittext_image_searching;
    Button button_image_searching;
    TextView textView_image_searching;
    ImageButton imageButton_back;


    // 네이버 검색
    private static final String CLIENT_ID = "input your client_id";
    private static final String CLIENT_SECRET = "input your client_secret";
    private static final String URL = "https://openapi.naver.com/v1/search/shop.json";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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

        mRequestQueue = Volley.newRequestQueue(this);

        button_image_searching.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                finish();
                        overridePendingTransition(0,0);
                        startActivity(getIntent());
                        overridePendingTransition(0,0);
                parseJSON();
            }
        });

    }

    private void parseJSON() { // Pixabay API 키 : 5765931-f9df1159a645d15d76213ab5d
//  참고용 예제     String url = "https://pixabay.com/api/?key=5765931-f9df1159a645d15d76213ab5d&q=kitten&image_type=photo&pretty=true";
        String search_keyword = edittext_image_searching.getText().toString(); // 사용자가 입력한 검색어를 받아서
        String url = "https://pixabay.com/api/?key=5765931-f9df1159a645d15d76213ab5d&q=" + search_keyword + "&image_type=photo" + "=true";
// 위에 String url이 되는 코드임

//        구글 custom search API 하는 중 -> 이미지
//        API KEY :
//String google_key=        "AIzaSyCbAN7pe1WI7nw3TRqyBD163fIVXu_GGdk";
        //https://www.googleapis.com/demo/v1?key=YOUR-API-KEY&fields=items(link)
//        String url= "https://www.googleapis.com/demo/v1?key=Y"+google_key+"&fields="+search_keyword;
//
//        String cx = "003665783189871589985:uf9rr2wibma" ;
//        String url = "https://cse.google.com/cse.js?cx="+ cx;
        // 네이버 검색
        //Client ID : Jf7l2hxpdsYjCJaS9_sK
        //Client Secret : E9UvEcyrbB
//        private static final String CLIENT_ID = "input your client_id";
//        private static final String CLIENT_SECRET = "input your client_secret";
//        private static final String URL = "https://openapi.naver.com/v1/search/shop.json";
        // 예제에서 얻은 힌트 노트북 검색시
        // http://openapi.naver.com/search?key=c1b406b32dbbbbeee5f2a36ddc14067f&query=노트북&display=5&start=1&target=shop&short=sim

//String url= "https://openapi.naver.com/v1/search/shop.json" +


        // 구글 custom search에서 쓰던 방식

//        final String searchString = edittext_image_searching.getText().toString();
//        Log.d("MainActivity -> ", "Searching for : " + searchString);
////        textView_image_searching.setText("Searching for : " + searchString);
//
//        // hide keyboard
//        InputMethodManager inputManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
//        inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
//
//        // looking for
//
//        String searchStringNoSpaces = searchString.replace(" ", "+");
//
//        // Your API key
//        // TODO replace with your value
//        String key = "AIzaSyCbAN7pe1WI7nw3TRqyBD163fIVXu_GGdk";
//
//        // Your Search Engine ID
//        // TODO replace with your value
//        String cx = "003665783189871589985:uf9rr2wibma";

//        String url = "https://www.googleapis.com/customsearch/v1?q=" + searchStringNoSpaces + "&key=" + key + "&cx=" + cx + "&alt=json";


    JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
            new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        JSONArray jsonArray = response.getJSONArray("hits");

                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject hit = jsonArray.getJSONObject(i);
//
                            String creatorName = hit.getString("user");
                            String imageUrl = hit.getString("webformatURL");
                            int likeCount = hit.getInt("likes");

                            mExampleList.add(new ExampleItem(imageUrl, creatorName, likeCount));

//                            String title = hit.getString("title");
////                                JSONObject items = hit.getJSONObject ("item/items");
//                            String link = hit.getString("link");
//                            String image = hit.getString("image");
//                            int lprice = hit.getInt("lprice");
//                            String mallName = hit.getString("mallName");
//                            mExampleList.add(new ExampleItem(image,title,link,lprice,mallName));
                        }

                        mExampleAdapter = new ExampleAdapter(MainActivity.this, mExampleList);
                        mRecyclerView.setAdapter(mExampleAdapter);
                        mExampleAdapter.setOnItemClickListener(MainActivity.this);

//                        finish();
//                        overridePendingTransition(0,0);
//                        startActivity(getIntent());
//                        overridePendingTransition(0,0);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            error.printStackTrace();
        }
    });

        mRequestQueue.add(request);
}

    @Override
    public void onItemClick(int position) {
        Intent detailIntent = new Intent(this, DetailActivity.class);
        ExampleItem clickedItem = mExampleList.get(position);

        detailIntent.putExtra(EXTRA_URL, clickedItem.getImageUrl());
        detailIntent.putExtra(EXTRA_CREATOR, clickedItem.getCreator());
        detailIntent.putExtra(EXTRA_LIKES, clickedItem.getLikeCount());

        startActivity(detailIntent);
    }


    @Override
    protected void onResume() {
        super.onResume();
        Log.e("mypage", "onResume");
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


