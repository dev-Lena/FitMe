package com.example.fitme;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import static com.example.fitme.Naver_Search_Shop_Main.EXTRA_LINK;
import static com.example.fitme.Naver_Search_Shop_Main.EXTRA_MALLNAME;
import static com.example.fitme.Naver_Search_Shop_Main.EXTRA_TITLE;

public class Naver_Search_Shop_Detail extends AppCompatActivity {

    // 네이버 검색 - 쇼핑 결과 상세보기 액티비티. (리사이클러뷰 아이템을 클릭하면 해당 아이템 상세페이지로 넘어감)

    BottomNavigationView bottomNavigationView; // 바텀 네이게이션 메뉴  -> 하단바
    private WebView shop_webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);


        Intent intent = getIntent();
        String title = intent.getStringExtra(EXTRA_TITLE);
        String mallName = intent.getStringExtra(EXTRA_MALLNAME);
        String link = intent.getStringExtra(EXTRA_LINK);
        shop_webView = findViewById(R.id.shop_webView);
        /**웹뷰**/
        // 웹뷰 셋팅
        shop_webView.getSettings().setJavaScriptEnabled(true);//자바스크립트 허용
        shop_webView.loadUrl(link ); // 접속 URL //웹뷰 실행

        // 네이버 검색에서 가져온 링크를 웹뷰에서 보여주기
        shop_webView.setWebChromeClient(new WebChromeClient());  //웹뷰에서 크롬이 실행이 가능하도록 아래 코드를 넣어준다.
        shop_webView.setWebViewClient(new WebViewClientClass());
        //웹뷰에서 홈페이지를 띄웠을때 새창이 아닌 기존창에서 실행이 되도록 아래 코드를 넣어준다.
        // 그리고 그 기능을 하는 Class를 만들어 준다.


        bottomNavigationView = findViewById(R.id.bottomNavi);

        // 하단바 누를 때 현재 위치의 아이콘 색 바꾸기
        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(4);
        menuItem.setChecked(true);

        // 바텀 네비게이션(하단바)
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.action_home :
                        Intent home_intent = new Intent(Naver_Search_Shop_Detail.this, Feed_Main_Activity.class);
                        home_intent.addFlags (Intent.FLAG_ACTIVITY_NO_ANIMATION);
                        startActivity(home_intent);//Feed_Main_Activity 클래스로 이동
                        break;
                    case R.id.action_search :
                        Intent search_intent = new Intent(Naver_Search_Shop_Detail.this, Review_searching_Activity.class);
                        search_intent.addFlags (Intent.FLAG_ACTIVITY_NO_ANIMATION);
                        startActivity(search_intent);//Review_searching_Activity 클래스로 이동
                        break;
                    case R.id.action_insight :
                        Intent write_intent = new Intent(Naver_Search_Shop_Detail.this, Size_Recommendation_Activity.class);
                        write_intent.addFlags (Intent.FLAG_ACTIVITY_NO_ANIMATION);
                        startActivity(write_intent);//Size_Recommendation_Activity 클래스로 이동
                        break;
                    case R.id.action_notification :
                        Intent insight_intent = new Intent(Naver_Search_Shop_Detail.this, Naver_Search_Shop_Main.class);
                        insight_intent.addFlags (Intent.FLAG_ACTIVITY_NO_ANIMATION);
                        startActivity(insight_intent);//Naver_Search_Shop_Main 클래스로 이동
                        break;
                    case R.id.action_mypage :
                        Intent mycloset_intent = new Intent(Naver_Search_Shop_Detail.this, Mypage_Activity.class);
                        mycloset_intent.addFlags (Intent.FLAG_ACTIVITY_NO_ANIMATION);
                        startActivity(mycloset_intent);//Review_searching_Activity 클래스로 이동
                        break;
                }

                return false;
            }
        });


    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {   // 뒤로가기 버튼을 눌렀을 때
        if ((keyCode == KeyEvent.KEYCODE_BACK) && shop_webView.canGoBack()) {
            shop_webView.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
    //웹뷰에서 홈페이지를 띄웠을때 새창이 아닌 기존창에서 실행이 되도록 아래 코드를 넣어준다.
    // 그리고 그 기능을 하는 Class를 만들어 준다.
    private class WebViewClientClass extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            Log.d("check URL", url);
            view.loadUrl(url);
            return true;
        }


    }
}
