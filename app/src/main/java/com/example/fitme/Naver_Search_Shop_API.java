package com.example.fitme;
// 네이버 검색 API 예제 - blog 검색

import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

public class Naver_Search_Shop_API {

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
    private JSONObject jsonObj;

    private RecyclerView mRecyclerView;
    private Naver_Search_Shop_Adapter mNaverSearchShopAdapter;
    private ArrayList<Naver_Search_Shop_Item> mExampleList;

/**네이버 검색 - 쇼핑 API (검색어를 입력하면 네이버 쇼핑에서 검색어가 포함된 결과를 JSON 형태로 받을 수 있음)**/


        public String naver_search_api(String s_key) {

            final String clientId = "Jf7l2hxpdsYjCJaS9_sK";//애플리케이션 클라이언트 아이디값";
            final String clientSecret = "E9UvEcyrbB";//애플리케이션 클라이언트 시크릿값";


            try {
                String text = URLEncoder.encode(s_key, "UTF-8");
                String apiURL = "https://openapi.naver.com/v1/search/shop?query="+ text; // json 결과
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
                } else {  // 에러 발생
                    br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
                }
                String inputLine;
                StringBuffer response = new StringBuffer();
                while ((inputLine = br.readLine()) != null) {
                    response.append(inputLine);
                }
                br.close();
                System.out.println(response.toString());
                receiveMsg =response.toString();

            } catch (Exception e) {
                System.out.println(e);
            }


            return  receiveMsg;

        }


// 네이버 검색- 쇼핑 API를 통해 Json 형태의 데이터를 가지고 오는지 먼저 확인하기 위해 사용했던 코드 ("가디건"이라는 키워드를 검색해서 가지고 오는지 확인)
    public static void main(String[] args) {
//
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
