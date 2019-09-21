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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;


public class MainActivity extends AppCompatActivity implements ExampleAdapter.OnItemClickListener  {
    /**이미지 검색 액티비티**/



    public static final String EXTRA_TITLE = "title";
    public static final String EXTRA_MALLNAME = "mallName";
    public static final String EXTRA_LINK = "link";

    private RecyclerView mRecyclerView;
    private ExampleAdapter mExampleAdapter;
    private ArrayList<ExampleItem> mExampleList;
//    private RequestQueue mRequestQueue;

    EditText edittext_image_searching;
    Button button_image_searching;
    TextView textView_image_searching;
    ImageButton imageButton_back;


    String  apiURL, s_response, json,  receiveMsg;
    java.net.URL url;

    RequestQueue mRequestQueue;
    private RequestQueue mQueue;

    String search_keyword;

    //    // 네이버 검색
//    private static final String CLIENT_ID = "input your client_id";
//    private static final String CLIENT_SECRET = "input your client_secret";
//    private static final String URL = "https://openapi.naver.com/v1/search/shop.json";
final int display = 5; // 보여지는 검색결과의 수

//    APIExamSearchShop apiExamSearchShop = new APIExamSearchShop();  //클래스 위치 // return

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
//
//        search_keyword = edittext_image_searching.getText().toString(); // 사용자가 입력한 검색어를 받아서

        mQueue = VolleySingleton.getInstance(this).getRequestQueue();//https://codinginflow.com/tutorials/android/volley/part-2-singleton-pattern
//       final String search_keyword = edittext_image_searching.getText().toString(); // 사용자가 입력한 검색어를 받아서

        button_image_searching.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                search_keyword = edittext_image_searching.getText().toString(); // 사용자가 입력한 검색어를 받아서

                        new Thread() {
            @Override
            public void run() {

                try {
                APIExamSearchShop_Shop apiExamSearchShop = new APIExamSearchShop_Shop();  //클래스 위치 // return
                    apiExamSearchShop.naver_search_api(search_keyword); //main run -> another class named APIExamSearchShop
                String result_data = apiExamSearchShop.naver_search_api(search_keyword);  //searchKeyword 로 검색한 결과를 리턴한걸 String으로 받아서 넣어줌 . data임
                String searched_url = apiExamSearchShop.apiURL;
                jsonParse(searched_url);

                Log.e("result_data","result_data : " +result_data);
                Log.e("searched_url","searched_url" + searched_url);

//                finish();
//                        overridePendingTransition(0,0);
//                        startActivity(getIntent());
//                        overridePendingTransition(0,0);

//                searchNaver(search_keyword);

//                네트워크 연결은 Thread 생성 필요


//        new Thread() {
//            @Override
//            public void run() {
//
//                try {

                /**
                    APIExamSearchShop_Shop apiExamSearchShop = new APIExamSearchShop_Shop();  //클래스 위치 // return
                    String search_keyword = edittext_image_searching.getText().toString(); // 사용자가 입력한 검색어를 받아서
                    String result_data = apiExamSearchShop.naver_search_api(search_keyword);


//                    String result_data = naver_search_api(search_keyword);

                    Log.e("MainActivity 클래스","abc : " + result_data);

//                    StringBuffer test = apiExamSearchShop.s_response; //이거는 실행 확인위해 가져왔었음 StringBuffer 타입임.
//                    String json =apiExamSearchShop.json;
                    //APIExamSearchShop 클래스에 -> json = br.readLine();



//                    here_apiURL = apiExamSearchShop.apiURL; // 네이버 SHOP API에서 검색어를 입력후 검색어 결과를 보여주는 url

//                    Log.e("test", "test" + test);
//                    Log.e("json", "json" + json); // -> 여기 안되면 volley 라이브러리 사용하기
//
//                    String shop_data = apiExamSearchShop.s_response.toString();  //가져온 검색 결과 데이터를 String으로 변환하여서 String 변수에 담아준다
//                    Log.w("MainActivity 클래스","shop_data : " + shop_data);
                    JSONObject jsonObj = new JSONObject(result_data);   // String에 담아준 데이터를 JsonObject 에 담아준다
//                    JSONObject jsonObj = new JSONObject(json);   // String에 담아준 데이터를 JsonObject 에 담아준다
                    Log.w("MainActivity 클래스","jsonObj : " + jsonObj);
                    JSONArray jsonArray = jsonObj.getJSONArray("items");  //JsonObject에 items라는 key를가지고 있는 JsonArray를 가지고 온다
                    Log.w("MainActivity 클래스","jsonArray : " + jsonArray);
                 **/

//                    parseJSON(apiExamSearchShop.apiURL);
//
//                    for (int i = 0; i < jsonArray.length(); i++) {  // items라는 키에 담겨있던 jsonArray의 길이만큼 for문을 돌려서
//                        JSONObject item = jsonArray.getJSONObject(i);                  //인덱스 i 에 있는 JsonObject를 가지고 와 jsonObject item에 넣어준다
//                        Log.w("MainActivity 클래스","item : " + item);
//
//                        String title = item.getString("title");   // jsonObject item에서 title이라는 key를 가진 String value를 가지고 와서 title이라는 String 변수에 담아준다
//                        String link = item.getString("link");
//                        String image = item.getString("image");
//                        String mallName= item.getString("mallName");
//                        int lprice = item.getInt("lprice");
//                        int hprice = item.getInt("hprice");
//
//                        Log.w("MainActivity 클래스","title : " + title);
//                        Log.w("MainActivity 클래스","link :" + link);
//                        Log.w("MainActivity 클래스","image : " + image);
//                        Log.w("MainActivity 클래스","mallName : " + mallName);
//                        Log.w("MainActivity 클래스","lprice : " + lprice);
//                        Log.w("MainActivity 클래스","hprice : " + hprice);
//
//
//                        mExampleList.add(new ExampleItem(title, link, image, mallName,lprice,hprice));  // 데이터에서 가져온 값들을 리사이클러뷰 리스트에 넣어준다8
//                        Log.w("MainActivity 클래스","mExampleList : " + mExampleList);
//                    }
////
////
////
//                    mExampleAdapter = new ExampleAdapter(MainActivity.this, mExampleList);
//                    mRecyclerView.setAdapter(mExampleAdapter);
//                    mExampleAdapter.setOnItemClickListener(MainActivity.this);


                } catch (Exception e) {
                    System.out.println(e);
                }
            }
        }.start();
////
////
            }

        });


    }//onCeate 닫는 중괄호

    private void jsonParse(String api_url) {

        String url = api_url;

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray = response.getJSONArray("items");
                            for (int i = 0; i < jsonArray.length(); i++) {  // items라는 키에 담겨있던 jsonArray의 길이만큼 for문을 돌려서
                        JSONObject item = jsonArray.getJSONObject(i);                  //인덱스 i 에 있는 JsonObject를 가지고 와 jsonObject item에 넣어준다
                        Log.w("MainActivity 클래스","item : " + item);

                        String title = item.getString("title");   // jsonObject item에서 title이라는 key를 가진 String value를 가지고 와서 title이라는 String 변수에 담아준다
                        String link = item.getString("link");
                        String image = item.getString("image");
                        String mallName= item.getString("mallName");
                        int lprice = item.getInt("lprice");
                        int hprice = item.getInt("hprice");

                        Log.w("MainActivity 클래스","title : " + title);
                        Log.w("MainActivity 클래스","link :" + link);
                        Log.w("MainActivity 클래스","image : " + image);
                        Log.w("MainActivity 클래스","mallName : " + mallName);
                        Log.w("MainActivity 클래스","lprice : " + lprice);
                        Log.w("MainActivity 클래스","hprice : " + hprice);


                        mExampleList.add(new ExampleItem(title, link, image, mallName,lprice,hprice));  // 데이터에서 가져온 값들을 리사이클러뷰 리스트에 넣어준다8
                        Log.w("MainActivity 클래스","mExampleList : " + mExampleList);
                    }
//
//
//
                    mExampleAdapter = new ExampleAdapter(MainActivity.this, mExampleList);
                    mRecyclerView.setAdapter(mExampleAdapter);
                    mExampleAdapter.setOnItemClickListener(MainActivity.this);
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

        mQueue.add(request);
    }

    private void parseJSON(String naver_api) { // Pixabay API 키 : 5765931-f9df1159a645d15d76213ab5d
//here_apiURL : 네이버 쇼핑 API에서 만든 url APIExamSearchSHop클래스에서 받아옴
    JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, naver_api, null,
            new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        JSONArray jsonArray = response.getJSONArray("items");

                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject item = jsonArray.getJSONObject(i);

                            String title = item.getString("title");
                            String link = item.getString("link");
                            String image = item.getString("image");
                            String mallName= item.getString("mallName");
                            int lprice = item.getInt("lprice");
                            int hprice = item.getInt("hprice");

                            Log.w("MainActivity 클래스","title : " + title);
                            Log.w("MainActivity 클래스","link :" + link);
                            Log.w("MainActivity 클래스","image : " + image);
                            Log.w("MainActivity 클래스","mallName : " + mallName);
                            Log.w("MainActivity 클래스","lprice : " + lprice);
                            Log.w("MainActivity 클래스","hprice : " + hprice);
                            mExampleList.add(new ExampleItem(title, link, image, mallName,lprice,hprice));
                            Log.w("MainActivity 클래스","mExampleList : " + mExampleList);

                        }

                        mExampleAdapter = new ExampleAdapter(MainActivity.this, mExampleList);
                        mRecyclerView.setAdapter(mExampleAdapter);
                        mExampleAdapter.setOnItemClickListener(MainActivity.this);


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

 class APIExamSearchShop_Shop {

    String json;
    StringBuffer s_response;
    String title;
    String link;
    String image;
    String mallName;
    int lprice;
    int hprice;
    String apiURL;

    private String receiveMsg;

    private RecyclerView mRecyclerView;
    private ExampleAdapter mExampleAdapter;
    private ArrayList<ExampleItem> mExampleList;

    public String naver_search_api(String s_key) {

        final String clientId = "Jf7l2hxpdsYjCJaS9_sK";//애플리케이션 클라이언트 아이디값";
        final String clientSecret = "E9UvEcyrbB";//애플리케이션 클라이언트 시크릿값";

//        String search = "";

        try {
            String text = URLEncoder.encode(s_key, "UTF-8");
            apiURL = "https://openapi.naver.com/v1/search/shop?query=" + text + "&start=" + 1 + "&display=" + 3; // json 결과
            //String apiURL = "https://openapi.naver.com/v1/search/blog.xml?query="+ text; // xml 결과
            URL url = new URL(apiURL);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("X-Naver-Client-Id", clientId);
            con.setRequestProperty("X-Naver-Client-Secret", clientSecret);
            int responseCode = con.getResponseCode();
            BufferedReader br;
            if (responseCode == 200) { // 정상 호출
                br = new BufferedReader(new InputStreamReader(con.getInputStream()));
                Log.w("MainActivity 클래스", "responseCode==200  정상작동 중입니까? br : " + br);

            } else {  // 에러 발생
                br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
            }
            String inputLine;
            StringBuffer response = new StringBuffer();
            while ((inputLine = br.readLine()) != null) {
                response.append(inputLine);
                json = br.readLine();
//                s_response = response;

            }
            receiveMsg = response.toString(); // 리턴값으로 넘겨줄 것
            Log.i("receiveMsg : ", receiveMsg);

            br.close();
            System.out.println(response.toString());

//                String shop_result = response.toString();
//                JsonObject jsonObject = new JsonObject(shop_result);
//                JSONObject jsonObj = new JSONObject(response.toString());
//                JSONArray jsonArray = jsonObj.getJSONArray("items");
////
//                            for (int i = 0; i < jsonArray.length(); i++) {
//                                JSONObject item = jsonArray.getJSONObject(i);
//
//                                title = item.getString("title");
//                                link = item.getString("link");
//                                image = item.getString("image");
//                                mallName= item.getString("mallName");
//                                lprice = item.getInt("lprice");
//                                hprice = item.getInt("hprice");
//
//                                Log.w("MainActivity 클래스","title : " + title);
//                                Log.w("MainActivity 클래스","link :" + link);
//                                Log.w("MainActivity 클래스","image : " + image);
//                                Log.w("MainActivity 클래스","mallName : " + mallName);
//                                Log.w("MainActivity 클래스","lprice : " + lprice);
//                                Log.w("MainActivity 클래스","hprice : " + hprice);
//
//                            }


        } catch (Exception e) {
            System.out.println(e);
        }


        return receiveMsg;  //receiveMsg = response.toString(); -> 이거를 MainActivity에 넘겨줄 것

    }
}


