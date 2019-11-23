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

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class Feed_Edit_Review extends AppCompatActivity {
    /**
     * 리뷰 피드 (리사이클러뷰) - 수정
     **/
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;


    private ArrayList<Feed_Main_ItemData> arrayList;


    TextView textView_reviewcard_number_number, textView_review_writer_writer, review_date;
    EditText editText_shoppingmall_url, editText_hashtag, editText_detailed_review, editText_edit_shoppingmall_url,
            editText_edit_detailed_review;
    ImageView imageView_review_photo1;
    ImageButton imageButton_review_register, imageButton_open_web_browser, imageButton_search_download, imageButton_write_review_back, imageButton_review_edit_completed;
    RatingBar ratingBar;
    BottomNavigationView bottomNavigationView; // 바텀 네이게이션 메뉴  -> 하단바

    String ImageUri; // 수정하기 전에 가지고 온 이미지의 uri

    //constant
    final int PICTURE_REQUEST_CODE = 100;
    private int Write_OK = 1001;


    Uri uri; // 전역변수로 Uri를 선언해줘야 클래스 내 다른 메소드 내에서도 사용할 수 있음.
    String pixabay_url;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.e("Feed_Write_Review", "onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_review_card);


// 리사이클러뷰의 아이템 수정 전 데이터 받아오기

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

        Intent intent = getIntent();  // 데이터를 담아서 보낸 intent를 받아옴

        Log.e("Feed_Edit_Review 클래스에서 리사이클러뷰 수정 작업중!", "getIntent");


        String shoppingmall_url = intent.getStringExtra("URL");
        String detailed_review = intent.getStringExtra("DETAIL");
        String hashtag = intent.getStringExtra("HASHTAG");
        String review_writer = intent.getStringExtra("WRITER");
        final String reviewcard_number = intent.getStringExtra("NUMBER");
        final String date = intent.getStringExtra("DATE");
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



// 가지고 온 데이터를 넣어줌 set해줌
        editText_shoppingmall_url.setText(shoppingmall_url);
        editText_detailed_review.setText(detailed_review);
        editText_hashtag.setText(hashtag);
        textView_review_writer_writer.setText(review_writer);
        textView_reviewcard_number_number.setText(reviewcard_number);
        review_date.setText(date);
        ratingBar.setRating(float_ratingBar);
        ImageUri = review_image;

        Picasso.get()
                .load(review_image)
                .fit()
                .centerInside()
                .placeholder(R.drawable.review_plz) // 이미지가 없을 때 기본
                .error(R.drawable.review_plz)// 에러가 났을 때
                .into(imageView_review_photo1);

        Log.e("Feed_Main_Activity 클래스에서 리사이클러뷰 수정 작업중! ", "URL 세팅 완료");
        Log.e("Feed_Main_Activity 클래스에서 리사이클러뷰 수정 작업중! ", "상세리뷰 세팅 완료");


// 뒤로 가기 버튼 눌렀을 때 피드(메인 화면)로 이동

        imageButton_write_review_back = findViewById(R.id.imageButton_write_review_back);
        imageButton_write_review_back.setOnClickListener(new ImageView.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent register_intent = new Intent(Feed_Edit_Review.this, Feed_Main_Activity.class);
                startActivity(register_intent); //액티비티 이동
                finish(); // 액티비티 finish 시킴
            }
        });


        // 사용자가 수정을 완료하고 난 후에 버튼을 누르면
        // 수정한 데이터 보내기-> Feed_Main_Activity 클래스

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
                String imageView_reviewcard_img1 = imageView_review_photo1.toString();
                String textView_review_date = review_date.toString();
                //헷갈리겠지만 review_date가 뷰 객체 아이디임.

                // putExtra로 데이터 보냄
                result.putExtra("쇼핑몰URL", editText_shoppingmall_url.getText().toString());
                result.putExtra("상세리뷰", editText_detailed_review.getText().toString());
                result.putExtra("해시태그", editText_hashtag.getText().toString());
                result.putExtra("만족도", ratingBar.getRating());
                result.putExtra("작성자", textView_review_writer_writer.getText().toString());
                result.putExtra("리뷰고유번호", textView_reviewcard_number_number.getText().toString());


                    if (uri != null ) {
                        result.putExtra("리뷰이미지", uri.toString());  // String으로 바꿔서 putExtra로 데이터 보냄.
                    }else if (pixabay_url != null){
                        result.putExtra("리뷰이미지", pixabay_url);  // String으로 바꿔서 putExtra로 데이터 보냄.
                    }else {
                    result.putExtra("리뷰이미지", ImageUri); // 그렇지 않으면 기존의 이미지를 보내라

                }
                result.putExtra("작성시간", review_date.getText().toString());
                result.putExtra("POSITION", position);


                // 자신을 호출한 Activity로 데이터를 보낸다.
                setResult(RESULT_OK, result);
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

        /**  버튼눌러서 픽사베이 이미지 가지고 오는 부분은 여기서 처리하기 **/
// 카메라 앱 오픈-> 암시적 인텐트
        imageButton_search_download = (ImageButton) findViewById(R.id.imageButton_download);
        imageButton_search_download.setOnClickListener(new ImageButton.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getApplicationContext(), Image_Searching.class);
                startActivityForResult(intent, 7001);

            }
        });


        // 갤러리에서 이미지 선택하는 버튼을 누르면
        ImageButton imageButton_image = (ImageButton) findViewById(R.id.imageButton_image);
        imageButton_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);  // 지금은 한장인데 나중에 사진 리사이클러뷰로 할 수도 있어서  Multiple로 냅둠.
                intent.setType("image/*");
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICTURE_REQUEST_CODE);
            }
        });

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if(requestCode == PICTURE_REQUEST_CODE)
        {
            if (resultCode == RESULT_OK)
            {

                //기존 이미지 지우기
                imageView_review_photo1.setImageResource(0);


                //ClipData 또는 Uri를 가져온다
                uri = data.getData();  // 해당 이미지의 파일 경로 즉, uri 정보를 받는다

                ClipData clipData = data.getClipData();
                Log.e("Feed_Write_Review 클래스에서", "이메일 가져오는 중 , uri : " + uri.toString()+","+uri);

                // 받아온 이미지를 인텐트에 담아서 putExtra로 보내주기.
                // -> 어디서 받냐면 onCreate에서 Feed_Main_Activity 클래스에 보낼 데이터 담을 때

                //ClipData 또는 Uri를 가져온다

                //이미지 URI 를 이용하여 이미지뷰에 순서대로 세팅한다.
                // 로그 찍었을 때 ClipData로는 실행이 되지 않고 uri방식으로 실행된다.
                if(clipData!=null) {


                    imageView_review_photo1.setImageURI(Uri.parse( uri.toString()));

                    Log.e("Feed_Write_Review 클래스에서", "uri--> 확인중 " + uri.toString() );

                }
                else if(uri != null)
                {
                    imageView_review_photo1.setImageURI(uri);
                }
            }
        }
        if (requestCode == 7001 && resultCode == RESULT_OK) {

            // pixabay API에서 intent로 받아온 이미지 url


            pixabay_url = data.getStringExtra("pixabay_url"); /*String형*/

            // 이미지 url은 인텐트로 받기, 변수 pixabay_url에 넣어주기
            ClipData clipData = data.getClipData();
            Log.e("Feed_Write_Review 클래스에서", "pixabay_url 가져오는 중 , uri : " + pixabay_url);

            if(pixabay_url != null)
            {
                Uri pixabay_url_url = Uri.parse(pixabay_url);

                Picasso.get().load(pixabay_url).fit().centerInside().into(imageView_review_photo1);
                Log.e("Feed_Write_Review 클래스에서", "pixabay_url_url,uri--> 확인중 " +pixabay_url_url+","+ imageView_review_photo1 );
            }

        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.e("Feed_Write_Review", "onRestart");
        //액티비티가 중단되었다가 다시 시작
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.e("Feed_Write_Review", "onStart");
        //액티비티가 화면에 나타나기 시작
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e("Feed_Write_Review", "onResume");
        //액티비티가 화면에 나타나고 상호작용이 가능해짐
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.e("Feed_Write_Review", "onPause");
        //다른 액티비티가 시작되려함, 이 액티비티는 중단되려하고 백그라운드로 들어갑니다.
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.e("Feed_Write_Review", "onStop");
        //액티비티가 더 이상 화면에 나타나지 않음,중단된 상태
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e("Feed_Write_Review", "onDestroy");
        //액티비티가 종료되려고 합니다.
    }

}





