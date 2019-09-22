package com.example.fitme;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;

import java.util.ArrayList;

public class image_searching extends Activity {
//    edittext_image_searching
//            button_image_searching
//    textView_image_searching
    EditText edittext_image_searching;
    Button button_image_searching;
    TextView textView_image_searching;
    ProgressBar progressBar;

    private static final String TAG = "searchApp";
    static String result = null;
    Integer responseCode = null;
    String responseMessage = "";

    public static final String EXTRA_URL = "imageUrl";
    public static final String EXTRA_CREATOR = "creatorName";
    public static final String EXTRA_LIKES = "likeCount";

    private RecyclerView mRecyclerView;
    private ExampleAdapter mExampleAdapter;
    private ArrayList<ExampleItem> mExampleList;
    private RequestQueue mRequestQueue;

    String urlString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_naver_shop_api);

        Log.d(TAG, "**** APP START ****");

        /**안씀**/

//
//
//        // GUI init
//        edittext_image_searching = (EditText) findViewById(R.id.edittext_image_searching);
//        button_image_searching = (Button) findViewById(R.id.button_image_searching);
//        textView_image_searching = (TextView) findViewById(R.id.textView_image_searching);
//        progressBar = (ProgressBar) findViewById(R.id.pb_loading_indicator);
//
//
//
//        mRecyclerView = findViewById(R.id.recycler_view);
//        mRecyclerView.setHasFixedSize(true);
//        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
//
//        mExampleList = new ArrayList<>();
//
//        edittext_image_searching = findViewById(R.id.edittext_image_searching);
//        button_image_searching = findViewById(R.id.button_image_searching);
//        textView_image_searching = findViewById(R.id.textView_image_searching);
//
//        mRequestQueue = Volley.newRequestQueue(this);
//
////        button_image_searching.setOnClickListener(new View.OnClickListener() {
////            @Override
////            public void onClick(View view) {
////
////                parseJSON();
////            }
////        });
//
//
//        // button onClick
//        button_image_searching.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//
//
//                final String searchString = edittext_image_searching.getText().toString();
//                Log.d(TAG, "Searching for : " + searchString);
////                textView_image_searching.setText("Searching for : " + searchString);
//
//                // hide keyboard
//                InputMethodManager inputManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
//                inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
//
//                // looking for
//
//                String searchStringNoSpaces = searchString.replace(" ", "+");
//
//                // Your API key
//                // TODO replace with your value
//                String key="AIzaSyCbAN7pe1WI7nw3TRqyBD163fIVXu_GGdk";
//
//                // Your Search Engine ID
//                // TODO replace with your value
//                String cx = "003665783189871589985:uf9rr2wibma";
//
//                urlString = "https://www.googleapis.com/customsearch/v1?q=" + searchStringNoSpaces + "&key=" + key + "&cx=" + cx + "&alt=json";
//                URL url = null;
//                try {
//                    url = new URL(urlString);
//                } catch (MalformedURLException e) {
//                    Log.e(TAG, "ERROR converting String to URL " + e.toString());
//                }
//                Log.d(TAG, "Url = "+  urlString);
//
//
//                // start AsyncTask
//                GoogleSearchAsyncTask searchTask = new GoogleSearchAsyncTask();
//                searchTask.execute(url);
//
//                parseJSON();
//
//            }
//        });
//
//    }
//
//    private void parseJSON() { // Pixabay API 키 : 5765931-f9df1159a645d15d76213ab5d
////  참고용 예제     String url = "https://pixabay.com/api/?key=5765931-f9df1159a645d15d76213ab5d&q=kitten&image_type=photo&pretty=true";
////        String search_keyword = edittext_image_searching.getText().toString(); // 사용자가 입력한 검색어를 받아서
////        String url = "https://pixabay.com/api/?key=5765931-f9df1159a645d15d76213ab5d&q=" + search_keyword + "&image_type=photo" + "=true";
//// 위에 String url이 되는 코드임
//
//        // 위에서 구해온 네이버 url
//        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, urlString, null,
//                new Response.Listener<JSONObject>() {
//                    @Override
//                    public void onResponse(JSONObject response) {
//                        try {
//                            JSONArray jsonArray = response.getJSONArray("hits");
//
//                            for (int i = 0; i < jsonArray.length(); i++) {
//                                JSONObject hit = jsonArray.getJSONObject(i);
//
//                                String title = hit.getString("title");
////                                JSONObject items = hit.getJSONObject ("item/items");
//                                String link = hit.getString("link");
//                                String image = hit.getString("image");
//                                int lprice = hit.getInt("lprice");
//                                String mallName = hit.getString("mallName");
////                                int likeCount = hit.getInt("likes");
//
//                                mExampleList.add(new ExampleItem(image,title,link,lprice,mallName));
//                            }
//
//                            mExampleAdapter = new ExampleAdapter(image_searching.this, mExampleList);
//                            mRecyclerView.setAdapter(mExampleAdapter);
//                            mExampleAdapter.setOnItemClickListener((ExampleAdapter.OnItemClickListener) image_searching.this);
//
////                        finish();
////                        overridePendingTransition(0,0);
////                        startActivity(getIntent());
////                        overridePendingTransition(0,0);
//
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                error.printStackTrace();
//            }
//        });
//
//        mRequestQueue.add(request);
//
//    }
//
////    @Override
////    public void onItemClick(int position) {
////        Intent detailIntent = new Intent(this, DetailActivity.class);
////        ExampleItem clickedItem = mExampleList.get(position);
////
////        detailIntent.putExtra(EXTRA_URL, clickedItem.getImageUrl());
////        detailIntent.putExtra(EXTRA_CREATOR, clickedItem.getCreator());
////        detailIntent.putExtra(EXTRA_LIKES, clickedItem.getLikeCount());
////
////        startActivity(detailIntent);
////    }
//
//
//    @Override
//    protected void onResume() {
//        super.onResume();
//        Log.e("mypage", "onResume");
//        //액티비티가 화면에 나타나고 상호작용이 가능해짐
//    }
//
//    @Override
//    protected void onPause() {
//        super.onPause();
//        Log.e("mypage", "onPause");
//        overridePendingTransition(0, 0);
//        //다른 액티비티가 시작되려함, 이 액티비티는 중단되려하고 백그라운드로 들어갑니다.
//    }
//
//    private class GoogleSearchAsyncTask extends AsyncTask<URL, Integer, String> {
//
//        protected void onPreExecute(){
//            Log.d(TAG, "AsyncTask - onPreExecute");
//            // show progressbar
////            progressBar.setVisibility(View.VISIBLE); // 찾는 중에 progress bar가 돌아감
////            progressBar.setProgress(View.INVISIBLE);
//        }
//
//
//        @Override
//        protected String doInBackground(URL... urls) {
//
//            URL url = urls[0];
//            Log.d(TAG, "AsyncTask - doInBackground, url=" + url);
//
//            // Http connection
//            HttpURLConnection conn = null;
//            try {
//                conn = (HttpURLConnection) url.openConnection();  // 돌아가는 중에 통신을 받아오고
//            } catch (IOException e) {
//                Log.e(TAG, "Http connection ERROR " + e.toString());
//            }
//
//
//            try {
//                responseCode = conn.getResponseCode();  // 리스폰 코드
//                responseMessage = conn.getResponseMessage();  // 리스폰 메시지 받음
//            } catch (IOException e) {
//                Log.e(TAG, "Http getting response code ERROR " + e.toString());
//            }
//
//            Log.d(TAG, "Http response code =" + responseCode + " message=" + responseMessage);
//
//            try {
//
//                if(responseCode == 200) { // 받은 리스폰 코드가 200일 때
//
//                    // response OK
//
//                    BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
//                    StringBuilder sb = new StringBuilder();
//                    String line;
//
//                    while ((line = rd.readLine()) != null) {
//                        sb.append(line + "\n");
//                    }
//                    rd.close();
//
//                    conn.disconnect();
//
//                    result = sb.toString();
//
//
//
//                    Log.d(TAG, "result 여기 어떤 형식인지 확인하기 =" + result);
//
//                    return result;
//
//                }else{
//
//                    // response problem
//
//                    String errorMsg = "Http ERROR response " + responseMessage + "\n" + "Make sure to replace in code your own Google API key and Search Engine ID";
//                    Log.e(TAG, errorMsg);
//                    result = errorMsg;
//                    return  result;
//
//                }
//            } catch (IOException e) {
//                Log.e(TAG, "Http Response ERROR " + e.toString());
//            }
//
//
//            return null;
//        }
//
//        protected void onProgressUpdate(Integer... progress) {
//            Log.d(TAG, "AsyncTask - onProgressUpdate, progress=" + progress);
//
//        }
//
//        protected void onPostExecute(String result) {
//
//            Log.d(TAG, "AsyncTask - onPostExecute, result=" + result);
//
//            // hide progressbar
////            progressBar.setVisibility(View.GONE);  // 프로그래스 바 없애고
//
//            // make TextView scrollable
////            textView_image_searching.setMovementMethod(new ScrollingMovementMethod());  // 스크롤링해서 textView에 결과값을 넣어줌
//            // show result
////            textView_image_searching.setText(result); // textView에 결과값을 넣어줌
//
//            // 만약 이미지만 보여주고 싶은거면
//            // 여기서 String으로 받은 url을 picasso로 넣어주면 될듯
//            // -> 리사이클러뷰
//
//
//        }


    }

}
