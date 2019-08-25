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

import java.util.ArrayList;

public class edit_review extends AppCompatActivity {



    private ArrayList<feed_MainData> arrayList;


    EditText editText_shoppingmall_url, editText_hashtag, editText_detailed_review, editText_edit_shoppingmall_url, editText_edit_detailed_review;
    ImageView imageView_review_photo1, imageView_review_photo2, imageView_review_photo3, imageView_review_photo4, imageView_review_photo5 ;
    ImageButton imageButton_open_web_browser, imageButton_camera, imageButton_image, imageButton_review_register, imageButton_review_edit_completed;
    BottomNavigationView bottomNavigationView; // 바텀 네이게이션 메뉴  -> 하단바

    //constant
    final int PICTURE_REQUEST_CODE = 100;
    private int Write_OK = 1001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.e("write_review","onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_review_card);

// 리사이클러뷰의 아이템 수정 전 데이터 받아오기

        // 넘겨 받은 데이터 받기

        editText_edit_shoppingmall_url= (EditText)findViewById(R.id.editText_edit_shoppingmall_url);
        editText_edit_detailed_review = (EditText)findViewById(R.id.editText_edit_detailed_review);
        Intent intent = getIntent();

        Log.e("edit_review 클래스에서 리사이클러뷰 수정 작업중!","getIntent");


        String shoppingmall_url = intent.getStringExtra("URL");
        String detailed_review = intent.getStringExtra("DETAIL");
        final int position = intent.getIntExtra("POSITION",0000);


        Log.e("edit_ing", "shoppingmall_url  : " + shoppingmall_url );
        Log.e("edit_ing", "detailed_review  : " + detailed_review );

        editText_edit_shoppingmall_url.setText(shoppingmall_url);
        editText_edit_detailed_review.setText(detailed_review);

        Log.e("Feed 클래스에서 리사이클러뷰 수정 작업중! ", "URL 세팅 완료");
        Log.e("Feed 클래스에서 리사이클러뷰 수정 작업중! ", "상세리뷰 세팅 완료");


// 리뷰 등록 버튼 -> 리뷰카드 이동

        // 글 등록 버튼
        imageButton_review_register = findViewById(R.id.imageButton_review_register);


        editText_hashtag = findViewById(R.id.editText_hashtag);
        editText_shoppingmall_url = findViewById(R.id.editText_shoppingmall_url);
        editText_detailed_review = findViewById(R.id.editText_detailed_review);
        imageView_review_photo1 = (ImageView)findViewById(R.id.imageView_review_photo1);
        imageView_review_photo2 = (ImageView)findViewById(R.id.imageView_review_photo2);
        imageView_review_photo3 = (ImageView)findViewById(R.id.imageView_review_photo3);
        imageView_review_photo4 = (ImageView)findViewById(R.id.imageView_review_photo4);
        imageView_review_photo5 = (ImageView)findViewById(R.id.imageView_review_photo5);


        //데이터 보내기
// 검색 버튼 -> 검색 결과 화면 이동
        imageButton_review_edit_completed = findViewById(R.id.imageButton_review_edit_completed);
        imageButton_review_edit_completed.setOnClickListener(new ImageView.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent result = new Intent();  // 넘겨줄 데이터를 담는 인텐트

                String textView_shoppingmall_url = editText_shoppingmall_url.getText().toString();

                String textView_detailed_review_card = editText_detailed_review.getText().toString();

                result.putExtra("쇼핑몰URL", editText_shoppingmall_url.getText().toString());  // putExtra로 데이터 보냄
                result.putExtra("상세리뷰", editText_detailed_review.getText().toString());  // putExtra로 데이터 보냄\


                // 자신을 호출한 Activity로 데이터를 보낸다.
                setResult(RESULT_OK, result);
                finish();

            }
        });


        // 리사이클러뷰 수정..  수정 전 데이터 받고 수정한 데이터 넘겨주기



  // 리사이클러뷰 추가




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

        imageButton_review_edit_completed = findViewById(R.id.imageButton_review_edit_completed);
        imageButton_review_edit_completed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


// 수정한 데이터 보내기
// 리사이클러뷰 수정
                Intent result = new Intent();  // 넘겨줄 데이터를 담는 인텐트
                Log.e("edit_review 클래스에서 리사이클러뷰 수정 작업중! ", "Intent 생성");

                //             String textView_shoppingmall_url = editText_edit_shoppingmall_url.getText().toString();

                //            String textView_detailed_review_card = editText_edit_detailed_review.getText().toString();

                result.putExtra("쇼핑몰URL", editText_edit_shoppingmall_url.getText().toString());  // putExtra로 데이터 보냄
                result.putExtra("상세리뷰", editText_edit_detailed_review.getText().toString());  // putExtra로 데이터 보냄
                result.putExtra("POSITION",position);

                Log.e("edit_review 클래스에서 리사이클러뷰 수정 작업중! ", "쇼핑몰 URL을 put했습니다" + editText_edit_shoppingmall_url);
                Log.e("edit_review 클래스에서 리사이클러뷰 수정 작업중! ", "쇼핑몰 DETAIL을 put했습니다" + editText_edit_detailed_review);


                // 자신을 호출한 Activity로 데이터를 보낸다.
                setResult(RESULT_OK, result);
                Log.e("edit_review 클래스에서 리사이클러뷰 수정 작업중! ", "Result로 set 끝!");
                finish();

            }
        });




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
                        Intent home_intent = new Intent(edit_review.this,feed.class);
                        startActivity(home_intent);//액티비티 띄우기
//                        startActivityForResult(intent,sub);//액티비티 띄우기
                        break;
                    case R.id.action_search :
                        Intent search_intent = new Intent(edit_review.this,searching.class);
                        startActivity(search_intent);//액티비티 띄우기
                        break;
                    case R.id.action_write_review :
                        Intent write_intent = new Intent(edit_review.this,review_category.class);
                        startActivity(write_intent);//액티비티 띄우기
                        break;
                    case R.id.action_insight :
                        Intent insight_intent = new Intent(edit_review.this,insight.class);
                        startActivity(insight_intent);//액티비티 띄우기
                        break;
                    case R.id.action_mycloset :
                        Intent mycloset_intent = new Intent(edit_review.this,my_closet.class);
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

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
////        // setResult를 통해 받아온 요청번호, 상태, 데이터
////        Log.d("RESULT", requestCode + "");
////        Log.d("RESULT", resultCode + "");
////        Log.d("RESULT", data + "");
//
//        if(requestCode == 10010 && resultCode == RESULT_OK) {
//            Toast.makeText(write_review.this, "리뷰작성을 완료했습니다!", Toast.LENGTH_SHORT).show();
//            textView_shoppingmall_url.setText(data.getStringExtra("리뷰"));
//            editText_email.setText(data.getStringExtra("리뷰"));
//        }
//    }



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





