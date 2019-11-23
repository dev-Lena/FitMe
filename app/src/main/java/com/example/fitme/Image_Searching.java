package com.example.fitme;

import android.content.Context;
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




public class Image_Searching extends AppCompatActivity implements Image_Searching_Adapter.OnItemClickListener  {
    /**pixabay API를 활용한 이미지 검색 액티비티**/


    public static final String EXTRA_URL = "imageUrl";
    public static final String EXTRA_CREATOR = "creatorName";
    public static final String EXTRA_LIKES = "likeCount";

    private RecyclerView mRecyclerView;
    private Image_Searching_Adapter imageSearchingAdapter;
    private ArrayList<Image_Searching_ItemData> imageSearchItemList;
    private RequestQueue mRequestQueue;

    EditText edittext_image_searching;
    Button button_image_searching;
    TextView textView_image_searching;
    ImageButton imageButton_back;

    private Context mContext;
    private Image_Searching_Adapter.OnItemClickListener mListener;


    // 네이버 검색
    private static final String CLIENT_ID = "input your client_id";
    private static final String CLIENT_SECRET = "input your client_secret";
    private static final String URL = "https://openapi.naver.com/v1/search/shop.json";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_search);

        mRecyclerView = findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        imageSearchItemList = new ArrayList<>();

        edittext_image_searching = findViewById(R.id.edittext_image_searching);
        button_image_searching = findViewById(R.id.button_image_searching);
        textView_image_searching = findViewById(R.id.textView_image_searching);
        imageButton_back= findViewById(R.id.imageButton_back);

        // 뒤로 가기 버튼 눌렀을 때 피드(메인 화면)로 이동

        imageButton_back = findViewById(R.id.imageButton_back);
        imageButton_back.setOnClickListener(new ImageView.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent register_intent = new Intent(Image_Searching.this, Write_review_Activity.class);
                startActivity(register_intent); //액티비티 이동
                finish(); // 액티비티 finish 시킴

            }
        });

        mRequestQueue = Volley.newRequestQueue(this);

        button_image_searching.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                parseJSON();



            }
        });

    }

    private void parseJSON() { // Pixabay API 키 : 5765931-f9df1159a645d15d76213ab5d
//  참고용 예제     String url = "https://pixabay.com/api/?key=5765931-f9df1159a645d15d76213ab5d&q=kitten&image_type=photo&pretty=true";
        String search_keyword = edittext_image_searching.getText().toString(); // 사용자가 입력한 검색어를 받아서
        String url = "https://pixabay.com/api/?key=5765931-f9df1159a645d15d76213ab5d&q=" + search_keyword + "&image_type=photo" + "=true";
// 위에 String url이 되는 코드임


        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray = response.getJSONArray("hits");

                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject hit = jsonArray.getJSONObject(i);


                                String creatorName = hit.getString("user");
                                String imageUrl = hit.getString("webformatURL");
                                int likeCount = hit.getInt("likes");



                                Image_Searching_ItemData imageSearchingItemData = new Image_Searching_ItemData(imageUrl, creatorName, likeCount);
                                imageSearchItemList.add(imageSearchingItemData);

                            }

                            imageSearchingAdapter = new Image_Searching_Adapter(Image_Searching.this, imageSearchItemList);
                            mRecyclerView.setAdapter(imageSearchingAdapter);
                            imageSearchingAdapter.setOnItemClickListener(Image_Searching.this);


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
    public void onItemClick(int position) {// 여기서 사용자가 선택한 이미지를 받아올 수 있게끔
//        Intent detailIntent = new Intent(this, Naver_Search_Shop_Detail.class);
        Image_Searching_ItemData imageSearchingItemData = imageSearchItemList.get(position);
        Intent result = new Intent();  // 넘겨줄 데이터를 담는 인텐트


        result.putExtra("pixabay_url", imageSearchingItemData.getImageUrl());  // putExtra로 데이터 보냄

        setResult(RESULT_OK, result);
        Log.e("imageSearching 클래스", "onItemClick: "+ imageSearchingItemData.getImageUrl() );

        finish();

    }


    @Override
    protected void onResume() {
        super.onResume();
        Log.e("Mypage_Activity", "onResume");
        //액티비티가 화면에 나타나고 상호작용이 가능해짐
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.e("Mypage_Activity", "onPause");
        overridePendingTransition(0, 0);
        //다른 액티비티가 시작되려함, 이 액티비티는 중단되려하고 백그라운드로 들어갑니다.
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.e("Mypage_Activity", "onPause");

        //다른 액티비티가 시작되려함, 이 액티비티는 중단되려하고 백그라운드로 들어갑니다.
    }


}