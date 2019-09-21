package com.example.fitme;
// 네이버 검색 API 예제 - blog 검색

import android.util.Log;

import androidx.recyclerview.widget.RecyclerView;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

public class APIExamSearchShop {

    String json;
      StringBuffer s_response;
    String title ;
    String link ;
    String image ;
    String mallName;
    int lprice ;
    int hprice ;
    String apiURL;

    private String  receiveMsg;

    private RecyclerView mRecyclerView;
    private ExampleAdapter mExampleAdapter;
    private ArrayList<ExampleItem> mExampleList;

/**네이버 쇼핑 API 하는 중**/
// 변수로 해서 넘겨주면 순서 없이 동시에 일어나기 때문에 return값으로 결과를 넘겨줘야 함.

        public String naver_search_api(String s_key) {

            final String clientId = "Jf7l2hxpdsYjCJaS9_sK";//애플리케이션 클라이언트 아이디값";
            final String clientSecret = "E9UvEcyrbB";//애플리케이션 클라이언트 시크릿값";



//        String search = "";

            try {
                String text = URLEncoder.encode(s_key, "UTF-8");
                apiURL = "https://openapi.naver.com/v1/search/shop?query="+ text + "&start=" + 1 + "&display="+3; // json 결과
                //String apiURL = "https://openapi.naver.com/v1/search/blog.xml?query="+ text; // xml 결과
                URL url = new URL(apiURL);
                HttpURLConnection con = (HttpURLConnection)url.openConnection();
                con.setRequestMethod("GET");
                con.setRequestProperty("X-Naver-Client-Id", clientId);
                con.setRequestProperty("X-Naver-Client-Secret", clientSecret);
                int responseCode = con.getResponseCode();
                BufferedReader br;
                if(responseCode==200) { // 정상 호출
                    br = new BufferedReader(new InputStreamReader(con.getInputStream()));
                    Log.w("MainActivity 클래스","responseCode==200  정상작동 중입니까? br : " + br);

                } else {  // 에러 발생
                    br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
                }
                String inputLine;
                StringBuffer response = new StringBuffer();
                while ((inputLine = br.readLine()) != null) {
                    response.append(inputLine);
                    json = br.readLine();
                    s_response = response;

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



    public static void main(String[] args) {

//        final String clientId = "Jf7l2hxpdsYjCJaS9_sK";//애플리케이션 클라이언트 아이디값";
//        final String clientSecret = "E9UvEcyrbB";//애플리케이션 클라이언트 시크릿값";
//
//        String search = "";
//        try {
//            String text = URLEncoder.encode("가디건", "UTF-8");
//            String apiURL = "https://openapi.naver.com/v1/search/shop?query="+ text; // json 결과
//            //String apiURL = "https://openapi.naver.com/v1/search/blog.xml?query="+ text; // xml 결과
//            URL url = new URL(apiURL);
//            HttpURLConnection con = (HttpURLConnection)url.openConnection();
//            con.setRequestMethod("GET");
//            con.setRequestProperty("X-Naver-Client-Id", clientId);
//            con.setRequestProperty("X-Naver-Client-Secret", clientSecret);
//            int responseCode = con.getResponseCode();
//            BufferedReader br;
//            if(responseCode==200) { // 정상 호출
//                br = new BufferedReader(new InputStreamReader(con.getInputStream()));
//            } else {  // 에러 발생
//                br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
//            }
//            String inputLine;
//            StringBuffer response = new StringBuffer();
//            while ((inputLine = br.readLine()) != null) {
//                response.append(inputLine);
//            }
//            br.close();
//            System.out.println(response.toString());
//        } catch (Exception e) {
//            System.out.println(e);
//        }
    }
}
