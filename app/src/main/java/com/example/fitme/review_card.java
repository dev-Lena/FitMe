package com.example.fitme;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class review_card extends AppCompatActivity {

    ImageView imageView_reviewcard_img1, imageView_reviewcard_img2, imageView_reviewcard_img3, imageView_reviewcard_img4, imageView_reviewcard_img5;

    TextView textView_mysize, textView_hashtag1, textView_detailed_review_card, textView_shoppingmall_url;
    BottomNavigationView bottomNavigationView; // 바텀 네이게이션 메뉴  -> 하단바

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.e("review_card","onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_card);

        // 상의 리뷰 작성 데이터 받기
//        textView_detailed_review_card = (TextView)findViewById(R.id.textView_detailed_review_card);
//        textView_shoppingmall_url= (TextView)findViewById(R.id.textView_shoppingmall_url);
        textView_hashtag1 = (TextView)findViewById(R.id.textView_hashtag1);
        imageView_reviewcard_img1 = (ImageView)findViewById (R.id.imageView_reviewcard_img1);
        textView_hashtag1 = (TextView)findViewById(R.id.textView_hashtag1);
        textView_hashtag1 = (TextView)findViewById(R.id.textView_hashtag1);
        textView_hashtag1 = (TextView)findViewById(R.id.textView_hashtag1);
        textView_hashtag1 = (TextView)findViewById(R.id.textView_hashtag1);

        // 검색 화면에서 검색어 데이터 받기
        textView_detailed_review_card = (TextView)findViewById(R.id.textView_detailed_review_card);
        textView_shoppingmall_url = (TextView)findViewById(R.id.textView_shoppingmall_url);

        Intent intent = getIntent();
//        Log.d("HashTag_Check",intent.getExtras().getString("HashTag_Check_Check"));

        String shoppinmall = intent.getStringExtra("shoppinmall");
        String detailedReview = intent.getStringExtra("detailedReview");

        Log.e("review_card", "shoppinmall : " +shoppinmall);
        textView_shoppingmall_url.setText(shoppinmall);
        textView_detailed_review_card.setText(detailedReview);
        Log.e("review_card", "detailedReview : " +detailedReview);

//
//
//        Intent intent = getIntent();
//
//        String detailed_review = intent.getStringExtra("detailed_review");
//        String url = intent.getStringExtra("url");
//        imageView_reviewcard_img1 = getIntent().getParcelableExtra("url1");
//        imageView_reviewcard_img2 = getIntent().getParcelableExtra("url2");
////        String uri1 = intent.getStringExtra("url1");
////        String uri2 = intent.getStringExtra("url2");
////        String uri3 = intent.getStringExtra("url3");
////        String uri4 = intent.getStringExtra("url4");
////        String uri5 = intent.getStringExtra("url5");
//
//        Bundle extras = getIntent().getExtras();
////        imageView_reviewcard_img1 = Uri.parse(extras.getString("url1"));
////        imageView_reviewcard_img2 = Uri.parse(extras.getString("url2"));
////        imageView_reviewcard_img3 = Uri.parse(extras.getString("url3"));
////        imageView_reviewcard_img4 = Uri.parse(extras.getString("url4"));
////        imageView_reviewcard_img5 = Uri.parse(extras.getString("url5"));
//        String hashtag1 = intent.getStringExtra("hashtag1");
//
//        Log.e("searching_result", "url : " +url);
//        // 쇼핑 url
//        textView_shoppingmall_url.setText(url);
//        //상세 후기
//        textView_detailed_review_card.setText(detailed_review);
//        // 해시태그 1
//        textView_hashtag1.setText(hashtag1);

        // 사진 5장                                          -> String으로 바꿔준 Uri를 객체에 넣어줘야하는데 방법을 못찾음
//        imageView_reviewcard_img1.(myUri);
//
//       imageView_reviewcard_img1 = Uri.parse(uri1);
//        imageView_reviewcard_img2 = Uri.parse(uri2);
//        imageView_reviewcard_img3 = Uri.parse(uri3);
//        imageView_reviewcard_img4 = Uri.parse(uri4);
//        imageView_reviewcard_img5 = Uri.parse(uri5);

    }



    @Override
    protected void onRestart() {
        super.onRestart();
        Log.e("review_card","onRestart");
        //액티비티가 중단되었다가 다시 시작
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.e("review_card","onStart");
        //액티비티가 화면에 나타나기 시작
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e("review_card","onResume");
        //액티비티가 화면에 나타나고 상호작용이 가능해짐
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.e("review_card","onPause");
        //다른 액티비티가 시작되려함, 이 액티비티는 중단되려하고 백그라운드로 들어갑니다.
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.e("review_card","onStop");
        //액티비티가 더 이상 화면에 나타나지 않음,중단된 상태
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e("review_card","onDestroy");
        //액티비티가 종료되려고 합니다.
    }

}
