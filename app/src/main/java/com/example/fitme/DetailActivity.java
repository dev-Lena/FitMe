package com.example.fitme;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import static com.example.fitme.MainActivity.EXTRA_LINK;
import static com.example.fitme.MainActivity.EXTRA_MALLNAME;
import static com.example.fitme.MainActivity.EXTRA_TITLE;

public class DetailActivity extends AppCompatActivity {

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

        TextView text_view_title_detail = findViewById(R.id.text_view_title_detail);
        TextView text_view_mallName_detail = findViewById(R.id.text_view_mallName_detail);
        TextView text_view_link_detail = findViewById(R.id.text_view_link_detail);
        shop_webView = findViewById(R.id.shop_webView);

//        Picasso.get().load(imageUrl).fit().centerInside().into(imageView);
        text_view_title_detail.setText(title);
        text_view_mallName_detail.setText("스토어 : " + mallName);
        text_view_link_detail.setText("링크 : " + link);

        // 웹뷰

        // 웹뷰 셋팅
        shop_webView.getSettings().setJavaScriptEnabled(true);//자바스크립트 허용
                //mWebView.loadUrl("http://www.pois.co.kr/mobile/login.do");

        shop_webView.loadUrl(link + "/html/test.html"); // 접속 URL //웹뷰 실행

        // 네이버 검색에서 가져온 링크를 웹뷰에서 보여주기

        shop_webView.setWebChromeClient(new WebChromeClient());  //웹뷰에서 크롬이 실행이 가능하도록 아래 코드를 넣어준다.
        shop_webView.setWebViewClient(new WebViewClientClass());
        //웹뷰에서 홈페이지를 띄웠을때 새창이 아닌 기존창에서 실행이 되도록 아래 코드를 넣어준다.
        // 그리고 그 기능을 하는 Class를 만들어 준다.


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
