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

import static com.example.fitme.MainActivity.EXTRA_LINK;
import static com.example.fitme.MainActivity.EXTRA_MALLNAME;
import static com.example.fitme.MainActivity.EXTRA_TITLE;

public class DetailActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigationView; // 바텀 네이게이션 메뉴  -> 하단바
    private WebView shop_webView;
//    private String myUrl = "file:///android_asset"; // 접속 URL (내장HTML의 경우 왼쪽과 같이 쓰고 아니면 걍 URL)


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);


        Intent intent = getIntent();
        String title = intent.getStringExtra(EXTRA_TITLE);
        String mallName = intent.getStringExtra(EXTRA_MALLNAME);
        String link = intent.getStringExtra(EXTRA_LINK);
//
//        TextView text_view_title_detail = findViewById(R.id.text_view_title_detail);
//        TextView text_view_mallName_detail = findViewById(R.id.text_view_mallName_detail);
//        TextView text_view_link_detail = findViewById(R.id.text_view_link_detail);
        shop_webView = findViewById(R.id.shop_webView);

//        Picasso.get().load(imageUrl).fit().centerInside().into(imageView);
//        text_view_title_detail.setText(title);
//        text_view_mallName_detail.setText("스토어 : " + mallName);
//        text_view_link_detail.setText("링크 : " + link);

        // 웹뷰

        // 웹뷰 셋팅
        shop_webView.getSettings().setJavaScriptEnabled(true);//자바스크립트 허용
                //mWebView.loadUrl("http://www.pois.co.kr/mobile/login.do");

        shop_webView.loadUrl(link ); // 접속 URL //웹뷰 실행

        // 네이버 검색에서 가져온 링크를 웹뷰에서 보여주기

        shop_webView.setWebChromeClient(new WebChromeClient());  //웹뷰에서 크롬이 실행이 가능하도록 아래 코드를 넣어준다.
        shop_webView.setWebViewClient(new WebViewClientClass());
        //웹뷰에서 홈페이지를 띄웠을때 새창이 아닌 기존창에서 실행이 되도록 아래 코드를 넣어준다.
        // 그리고 그 기능을 하는 Class를 만들어 준다.


        bottomNavigationView = findViewById(R.id.bottomNavi);
        // 하단바 누를 때 색 바뀌게 하는 중
        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(4);
        menuItem.setChecked(true);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.action_home :
                        Intent home_intent = new Intent(DetailActivity.this,feed.class);
                        home_intent.addFlags (Intent.FLAG_ACTIVITY_NO_ANIMATION);
                        startActivity(home_intent);//액티비티 띄우기
//                        startActivityForResult(intent,sub);//액티비티 띄우기
                        break;
                    case R.id.action_search :
                        Intent search_intent = new Intent(DetailActivity.this,searching.class);
                        search_intent.addFlags (Intent.FLAG_ACTIVITY_NO_ANIMATION);
                        startActivity(search_intent);//액티비티 띄우기
                        break;
                    case R.id.action_insight :
                        Intent write_intent = new Intent(DetailActivity.this,insight.class);
                        write_intent.addFlags (Intent.FLAG_ACTIVITY_NO_ANIMATION);
                        startActivity(write_intent);//액티비티 띄우기
                        break;
                    case R.id.action_notification :
                        Intent insight_intent = new Intent(DetailActivity.this,MainActivity.class);
                        insight_intent.addFlags (Intent.FLAG_ACTIVITY_NO_ANIMATION);
                        startActivity(insight_intent);//액티비티 띄우기
                        break;
                    case R.id.action_mypage :
                        Intent mycloset_intent = new Intent(DetailActivity.this, mypage.class);
                        mycloset_intent.addFlags (Intent.FLAG_ACTIVITY_NO_ANIMATION);
                        startActivity(mycloset_intent);//액티비티 띄우기
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
