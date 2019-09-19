package com.example.fitme;

import android.content.ClipData;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class size_top extends AppCompatActivity {


    EditText editText_shoppingmall_url, editText_hashtag, editText_detailed_review;
    ImageView imageView_review_photo1, imageView_review_photo2, imageView_review_photo3, imageView_review_photo4, imageView_review_photo5 ;
    ImageButton imageButton_open_web_browser, imageButton_camera, imageButton_image, imageButton_review_register;
    BottomNavigationView bottomNavigationView; // 바텀 네이게이션 메뉴  -> 하단바

    //constant
    final int PICTURE_REQUEST_CODE = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.e("write_review","onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_size_top);

// 리뷰 작성하면 리뷰 카드로 데이터 넘기기


// 리뷰 등록 버튼 -> 리뷰카드 이동

        imageButton_review_register = findViewById(R.id.imageButton_review_register);
        editText_hashtag = findViewById(R.id.editText_hashtag);
        editText_shoppingmall_url = findViewById(R.id.editText_shoppingmall_url);
        editText_detailed_review = findViewById(R.id.editText_detailed_review);
        imageView_review_photo1 = (ImageView)findViewById(R.id.imageView_review_photo1);
//        imageView_review_photo2 = (ImageView)findViewById(R.id.imageView_review_photo2);
//        imageView_review_photo3 = (ImageView)findViewById(R.id.imageView_review_photo3);
//        imageView_review_photo4 = (ImageView)findViewById(R.id.imageView_review_photo4);
//        imageView_review_photo5 = (ImageView)findViewById(R.id.imageView_review_photo5);

        //데이터 보내기
// 검색 버튼 -> 검색 결과 화면 이동
        imageButton_review_register = findViewById(R.id.imageButton_review_register);
        imageButton_review_register.setOnClickListener(new ImageView.OnClickListener() {
            @Override
            public void onClick(View view) {

                String shoppinmall = editText_shoppingmall_url.getText().toString();
                String detailedReview = editText_detailed_review.getText().toString();
                Intent intent = new Intent(size_top.this, size_top.class);
                intent.putExtra("shoppinmall", shoppinmall);
                intent.putExtra("detailedReview", detailedReview);
                Log.e("write_review", "shoppinmall : " +shoppinmall);
                Log.e("write_review", "detailedReview : " +detailedReview);
                startActivity(intent);
//                Intent register_intent = new Intent(searching.this, searching_result.class);
//                startActivity(register_intent); //액티비티 이동

            }
        });


//        imageButton_review_register.setOnClickListener(new ImageButton.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                String url= editText_shoppingmall_url.getText().toString();
//                String detailed_review = editText_detailed_review.getText().toString();
//                String hashtag1 = editText_hashtag .getText().toString();
//
//
//                Intent intent = new Intent(write_review.this, review_card.class);
////                intent.putExtra("url", url);
////                intent.putExtra("detailed_review", detailed_review);
////                intent.putExtra("hashtag1", hashtag1);
////                intent.putExtra("uri1",imageView_review_photo1.toString()); // uri String으로 변환
////                intent.putExtra("uri2",imageView_review_photo2.toString()); // uri String으로 변환
////                intent.putExtra("uri3",imageView_review_photo3.toString()); // uri String으로 변환
////                intent.putExtra("uri4",imageView_review_photo4.toString()); // uri String으로 변환
////                intent.putExtra("uri5",imageView_review_photo5.toString()); // uri String으로 변환
////
//
//                Log.e("write_review", "url : " +url);
//                Log.e("write_review", "detailed_review : " +detailed_review);
////
//                startActivity(intent);
//            }
//        });


//
//// done 버튼 -> 리뷰를 등록하는 버튼
//        imageButton_review_register = findViewById(R.id.imageButton_review_register);
//        imageButton_review_register.setOnClickListener(new ImageView.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent register_intent = new Intent(write_review.this, feed.class);
//                startActivity(register_intent); //액티비티 이동
//
//            }
//        });

// 웹브라우저 오픈-> 암시적 인텐트
        imageButton_open_web_browser = findViewById(R.id.imageButton_open_web_browser);
        imageButton_open_web_browser.setOnClickListener(new ImageView.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.google.com/"));
                startActivity(intent);

            }
        });

// 카메라 앱 오픈-> 암시적 인텐트
        imageButton_camera = findViewById(R.id.imageButton_camera);
        imageButton_camera.setOnClickListener(new ImageView.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivity(intent);

            }
        });



// 리뷰에서 이미지 선택 (여러개, url, 최대 5개까지) -> 인텐트
//        imageButton_image = findViewById(R.id.imageButton_image);
//        imageButton_image.setOnClickListener(new ImageView.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.google.com/"));
//                startActivity(intent);
//
//            }
//        });

        //UI
        // 리뷰에서 이미지 선택 (여러개, url, 최대 5개까지) -> 인텐트
//        imageView_review_photo1 = (ImageView)findViewById(R.id.imageView_review_photo1);
//        imageView_review_photo2 = (ImageView)findViewById(R.id.imageView_review_photo2);
//        imageView_review_photo3 = (ImageView)findViewById(R.id.imageView_review_photo3);
//        imageView_review_photo4 = (ImageView)findViewById(R.id.imageView_review_photo4);
//        imageView_review_photo5 = (ImageView)findViewById(R.id.imageView_review_photo5);

        ImageButton imageButton_image = (ImageButton)findViewById(R.id.imageButton_image);
        imageButton_image.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Audio.Media.EXTERNAL_CONTENT_URI);
                //사진을 여러개 선택할수 있도록 한다
                intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                intent.setType("image/*");
                startActivityForResult(Intent.createChooser(intent, "Select Picture"),  PICTURE_REQUEST_CODE);
            }
        });



//하단바
        bottomNavigationView = findViewById (R.id.bottomNavi);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                switch (menuItem.getItemId()) {
                    case R.id.action_home :
                        Intent home_intent = new Intent(size_top.this,feed.class);
                        startActivity(home_intent);//액티비티 띄우기
//                        startActivityForResult(intent,sub);//액티비티 띄우기
                        break;
                    case R.id.action_search :
                        Intent search_intent = new Intent(size_top.this,searching.class);
                        startActivity(search_intent);//액티비티 띄우기
                        break;
                    case R.id.action_insight :
                        Intent write_intent = new Intent(size_top.this,insight.class);
                        startActivity(write_intent);//액티비티 띄우기
                        break;
                    case R.id.action_notification :
                        Intent insight_intent = new Intent(size_top.this,image_searching.class);
                        startActivity(insight_intent);//액티비티 띄우기
                        break;
                    case R.id.action_mypage :
                        Intent mycloset_intent = new Intent(size_top.this, mypage.class);
                        startActivity(mycloset_intent);//액티비티 띄우기
                        break;
                }


                return false;

            }
        });


// 웹브라우저
//
//        Uri uri = Uri.parse("http://www.google.com");
//        Intent it  = new Intent(Intent.ACTION_VIEW,uri);
//        startActivity(it);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == PICTURE_REQUEST_CODE)
        {
            if (resultCode == RESULT_OK)
            {

                //기존 이미지 지우기
                imageView_review_photo1.setImageResource(0);
                imageView_review_photo2.setImageResource(0);
                imageView_review_photo3.setImageResource(0);
                imageView_review_photo4.setImageResource(0);
                imageView_review_photo5.setImageResource(0);

                //ClipData 또는 Uri를 가져온다
                Uri uri = data.getData();
                ClipData clipData = data.getClipData();

                //이미지 URI 를 이용하여 이미지뷰에 순서대로 세팅한다.
                if(clipData!=null)
                {

                    // 최대 업로드 사진 갯수 5개
                    for(int i = 0; i < 5; i++)
                    {
                        if(i<clipData.getItemCount()){
                            Uri urione =  clipData.getItemAt(i).getUri();
                            switch (i){
                                case 0:
                                    imageView_review_photo1.setImageURI(urione);
                                    break;
                                case 1:
                                    imageView_review_photo2.setImageURI(urione);
                                    break;
                                case 2:
                                    imageView_review_photo3.setImageURI(urione);
                                    break;
                                case 3:
                                    imageView_review_photo4.setImageURI(urione);
                                    break;
                                case 4:
                                    imageView_review_photo5.setImageURI(urione);
                                    break;
                                    default:
                                        Toast myToast = Toast.makeText(this.getApplicationContext(),"최대 5개까지 업로드 가능합니다", Toast.LENGTH_SHORT);
                                        myToast.show();
                            }
                        }
                    }
                }
                else if(uri != null)
                {
                    imageView_review_photo1.setImageURI(uri);
                }
            }
        }
    }
    @Override
    protected void onRestart() {
        super.onRestart();
        Log.e("write_review","onRestart");
        //액티비티가 중단되었다가 다시 시작
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.e("write_review","onStart");
        //액티비티가 화면에 나타나기 시작
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e("write_review","onResume");
        //액티비티가 화면에 나타나고 상호작용이 가능해짐
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.e("write_review","onPause");
        //다른 액티비티가 시작되려함, 이 액티비티는 중단되려하고 백그라운드로 들어갑니다.
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.e("write_review","onStop");
        //액티비티가 더 이상 화면에 나타나지 않음,중단된 상태
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e("write_review","onDestroy");
        //액티비티가 종료되려고 합니다.
    }

}





