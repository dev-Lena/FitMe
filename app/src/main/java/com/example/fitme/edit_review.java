package com.example.fitme;

import android.content.ClipData;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class edit_review extends AppCompatActivity {

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;


    private ArrayList<feed_MainData> arrayList;


    TextView textView_reviewcard_number_number, textView_review_writer_writer, review_date;
    EditText editText_shoppingmall_url, editText_hashtag, editText_detailed_review, editText_edit_shoppingmall_url,
            editText_edit_detailed_review;
    ImageView imageView_review_photo1;
    ImageButton imageButton_review_register, imageButton_open_web_browser, imageButton_camera, imageButton_write_review_back, imageButton_review_edit_completed;
    RatingBar ratingBar;
    BottomNavigationView bottomNavigationView; // 바텀 네이게이션 메뉴  -> 하단바

    //constant
    final int PICTURE_REQUEST_CODE = 100;
    private int Write_OK = 1001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.e("write_review", "onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_review_card);


// 리사이클러뷰의 아이템 수정 전 데이터 받아오기

//        intent.putExtra("WRITER", arrayList.get(position).textView_review_writer);
//        intent.putExtra("NUMBER", arrayList.get(position).textView_reviewcard_number);

        // 넘겨 받은 데이터 받기

        // 글 등록 버튼
        imageButton_review_register = findViewById(R.id.imageButton_review_register);
        editText_shoppingmall_url = findViewById(R.id.editText_shoppingmall_url);
        editText_detailed_review = findViewById(R.id.editText_detailed_review);
        editText_hashtag = findViewById(R.id.editText_hashtag);
        ratingBar = findViewById(R.id.ratingBar);
        imageView_review_photo1 = (ImageView) findViewById(R.id.imageView_review_photo1);
        review_date = (TextView) findViewById(R.id.review_date);
        textView_review_writer_writer= findViewById(R.id.textView_review_writer_writer);
        textView_reviewcard_number_number= findViewById(R.id.textView_reviewcard_number_number);




        Intent intent = getIntent();

        Log.e("edit_review 클래스에서 리사이클러뷰 수정 작업중!", "getIntent");

        // 피드에 올라간 리뷰카드에서 받아온 데이터
//        intent.putExtra("URL", arrayList.get(position).textView_shoppingmall_url);
//        intent.putExtra("DETAIL", arrayList.get(position).textView_detailed_review_card);
//        intent.putExtra("HASHTAG", arrayList.get(position).textView_hashtag);
//        intent.putExtra("WRITER", arrayList.get(position).textView_review_writer);
//        intent.putExtra("NUMBER", arrayList.get(position).textView_reviewcard_number);
//
//        intent.putExtra("IMAGE", arrayList.get(position).imageView_reviewcard_img1);
//        intent.putExtra("PROFILE", arrayList.get(position).imageView_reviewcard_profile_image);
//        intent.putExtra("DATE", arrayList.get(position).review_date);
//
//        intent.putExtra("RATING", arrayList.get(position).float_ratingBar);
//        intent.putExtra("POSITION", position);

        String shoppingmall_url = intent.getStringExtra("URL");
        String detailed_review = intent.getStringExtra("DETAIL");
        String hashtag = intent.getStringExtra("HASHTAG");
        String review_writer = intent.getStringExtra("WRITER");
        String reviewcard_number = intent.getStringExtra("NUMBER");
        String date = intent.getStringExtra("DATE");
        String review_image = intent.getStringExtra("IMAGE");
        Float float_ratingBar = intent.getFloatExtra("RATING", 0);


        final int position = intent.getIntExtra("POSITION", 0000);

        Log.e("edit_ing", "shoppingmall_url  : " + shoppingmall_url );
        Log.e("edit_ing", "detailed_review  : " + detailed_review );
        Log.e("edit_ing", "hashtag  : " + hashtag );
        Log.e("edit_ing", "review_writer  : " + review_writer );
        Log.e("edit_ing", "reviewcard_number  : " + reviewcard_number );
        Log.e("edit_ing 이미지 확인 중입니다", "review_image  : " + review_image );
        Log.e("edit_ing", "date +++++++++++++++++++++++++++++++++++++++++ : " + date);
        Log.e("edit_ing", "float_ratingBar  : " + float_ratingBar );
        Log.e("edit_ing", "position  : " + position );

        Log.e("***********************************************   date : ", date);
// 가지고 온 데이터를 넣어줌 set해줌
        editText_shoppingmall_url.setText(shoppingmall_url);
        editText_detailed_review.setText(detailed_review);
        editText_hashtag.setText(hashtag);
        textView_review_writer_writer.setText(review_writer);
        textView_reviewcard_number_number.setText(reviewcard_number);
        review_date.setText(date);
        ratingBar.setRating(float_ratingBar);
        String ImageUri = review_image;

        Picasso.get()
                .load(review_image)
                .fit()
                .centerInside()
                .placeholder(R.drawable.review_plz) // 이미지가 없을 때 기본
                .error(R.drawable.review_plz)// 에러가 났을 때
                .into(imageView_review_photo1);

        Log.e("***********************************************  review_date : ", String.valueOf(review_date));
        Log.e("Feed 클래스에서 리사이클러뷰 수정 작업중! ", "URL 세팅 완료");
        Log.e("Feed 클래스에서 리사이클러뷰 수정 작업중! ", "상세리뷰 세팅 완료");


// 뒤로 가기 버튼 눌렀을 때 피드(메인 화면)로 이동

        imageButton_write_review_back = findViewById(R.id.imageButton_write_review_back);
        imageButton_write_review_back.setOnClickListener(new ImageView.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent register_intent = new Intent(edit_review.this, feed.class);
                startActivity(register_intent); //액티비티 이동
                finish(); // 액티비티 finish 시킴
            }
        });


        // 사용자가 수정을 완료하고 난 후에 버튼을 누르면
        // 수정한 데이터 보내기-> feed 클래스

        imageButton_review_register = findViewById(R.id.imageButton_review_register);
        imageButton_review_register.setOnClickListener(new ImageView.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent result = new Intent();  // 넘겨줄 데이터를 담는 인텐트

                String textView_shoppingmall_url = editText_shoppingmall_url.getText().toString();
                String textView_detailed_review_card = editText_detailed_review.getText().toString();
                String text_View_edit_hashtag = editText_hashtag.getText().toString();
                float int_ratingBar = ratingBar.getRating();
                String textView_review_writer = textView_review_writer_writer.getText().toString();
                String textView_reviewcard_number = textView_reviewcard_number_number.getText().toString();
                String imageView_reviewcard_img = imageView_review_photo1.toString();
                String textView_review_date = review_date.toString();
                //헷갈리겠지만 review_date가 뷰 객체 아이디임.

                // putExtra로 데이터 보냄
                result.putExtra("쇼핑몰URL", editText_shoppingmall_url.getText().toString());
                result.putExtra("상세리뷰", editText_detailed_review.getText().toString());
                result.putExtra("해시태그", editText_hashtag.getText().toString());
                result.putExtra("만족도", ratingBar.getRating());
                result.putExtra("작성자", textView_review_writer_writer.getText().toString());
                result.putExtra("리뷰고유번호", textView_reviewcard_number_number.getText().toString());
                result.putExtra("리뷰이미지", imageView_review_photo1.toString());
                result.putExtra("작성시간", review_date.toString());
//                result.putExtra("작성시간", date);
                result.putExtra("POSITION", position);

                Log.e("edit_review 클래스에서 리사이클러뷰 수정 작업중! ++++++++++++++++++++ ",  review_date.toString());



                // 자신을 호출한 Activity로 데이터를 보낸다.
                setResult(RESULT_OK, result);
                finish();

            }
        });


        // 리사이클러뷰 수정..  수정 전 데이터 받고 수정한 데이터 넘겨주기


        // 리사이클러뷰 추가


//        imageButton_review_register = findViewById(R.id.imageButton_review_register);
//        imageButton_review_register.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//
//// 수정한 데이터 보내기
//// 리사이클러뷰 수정
//                Intent result = new Intent();  // 넘겨줄 데이터를 담는 인텐트
//                Log.e("edit_review 클래스에서 리사이클러뷰 수정 작업중! ", "Intent 생성");
//
//                result.putExtra("쇼핑몰URL", editText_shoppingmall_url.getText().toString());  // putExtra로 데이터 보냄
//                result.putExtra("상세리뷰", editText_detailed_review.getText().toString());  // putExtra로 데이터 보냄
//                result.putExtra("해시태그", editText_hashtag.getText().toString());  // putExtra로 데이터 보냄
//                result.putExtra("만족도", ratingBar.getRating());  // putExtra로 데이터 보냄
//                result.putExtra("리뷰이미지", imageView_review_photo1.toString());  // putExtra로 데이터 보냄
//
//
//                result.putExtra("POSITION", position);
//
//                Log.e("edit_review 클래스에서 리사이클러뷰 수정 작업중! ", "쇼핑몰 URL을 put했습니다" + editText_shoppingmall_url);
//                Log.e("edit_review 클래스에서 리사이클러뷰 수정 작업중! ", "쇼핑몰 DETAIL을 put했습니다" + editText_detailed_review);
//
//
//                // 자신을 호출한 Activity로 데이터를 보낸다.
//                setResult(RESULT_OK, result);
//                Log.e("edit_review 클래스에서 리사이클러뷰 수정 작업중! ", "Result로 set 끝!");
//                finish();
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


        ImageButton imageButton_image = (ImageButton) findViewById(R.id.imageButton_image);
        imageButton_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Audio.Media.EXTERNAL_CONTENT_URI);
                //사진을 여러개 선택할수 있도록 한다
                intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                intent.setType("image/*");
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICTURE_REQUEST_CODE);
            }
        });

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {


        if (requestCode == PICTURE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {

                //기존 이미지 지우기
                imageView_review_photo1.setImageResource(0);
//                imageView_review_photo2.setImageResource(0);
//                imageView_review_photo3.setImageResource(0);
//                imageView_review_photo4.setImageResource(0);
//                imageView_review_photo5.setImageResource(0);

                //ClipData 또는 Uri를 가져온다
                Uri uri = data.getData();
                ClipData clipData = data.getClipData();

                //이미지 URI 를 이용하여 이미지뷰에 순서대로 세팅한다.
                if (clipData != null) {

                    // 최대 업로드 사진 갯수 5개
                    for (int i = 0; i < 5; i++) {
                        if (i < clipData.getItemCount()) {
                            Uri urione = clipData.getItemAt(i).getUri();
                            switch (i) {
                                case 0:
                                    imageView_review_photo1.setImageURI(urione);
                                    break;
//                                case 1:
//                                    imageView_review_photo2.setImageURI(urione);
//                                    break;
//                                case 2:
//                                    imageView_review_photo3.setImageURI(urione);
//                                    break;
//                                case 3:
//                                    imageView_review_photo4.setImageURI(urione);
//                                    break;
//                                case 4:
//                                    imageView_review_photo5.setImageURI(urione);
//                                    break;
                                default:
                                    Toast myToast = Toast.makeText(this.getApplicationContext(), "한 개의 이미지만 업로드할 수 있습니다", Toast.LENGTH_SHORT);
                                    myToast.show();
                            }
                        }
                    }
                } else if (uri != null) {
                    imageView_review_photo1.setImageURI(uri);
                }
            }
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.e("write_review", "onRestart");
        //액티비티가 중단되었다가 다시 시작
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.e("write_review", "onStart");
        //액티비티가 화면에 나타나기 시작
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e("write_review", "onResume");
        //액티비티가 화면에 나타나고 상호작용이 가능해짐
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.e("write_review", "onPause");
        //다른 액티비티가 시작되려함, 이 액티비티는 중단되려하고 백그라운드로 들어갑니다.
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.e("write_review", "onStop");
        //액티비티가 더 이상 화면에 나타나지 않음,중단된 상태
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e("write_review", "onDestroy");
        //액티비티가 종료되려고 합니다.
    }

}





