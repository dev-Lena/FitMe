package com.example.fitme;

import android.app.ProgressDialog;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

public class write_review extends AppCompatActivity {

    // 현재 로그인한 유저의 정보만 담는 쉐어드 프리퍼런스
    private ArrayList<List> userData = new ArrayList<List>();

    private SharedPreferences logined_user;
    private SharedPreferences.Editor user_editor;
    //

    private ArrayList<feed_MainData> arrayList;


    TextView word_review_date, word_textView_review_writer, word_textView_reviewcard_number,textView_review_writer_writer,textView_reviewcard_number, textView_reviewcard_number_number;
    EditText editText_shoppingmall_url, editText_hashtag, editText_detailed_review;
    ImageView imageView_review_photo1, imageView_review_photo2, imageView_review_photo3, imageView_review_photo4, imageView_review_photo5 ;
    ImageButton imageButton_open_web_browser, imageButton_camera, imageButton_image, imageButton_review_register;
    BottomNavigationView bottomNavigationView; // 바텀 네이게이션 메뉴  -> 하단바
    RatingBar ratingBar;

    //constant
    final int PICTURE_REQUEST_CODE = 100;
    private int Write_OK = 1001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.e("write_review","onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_review_card);

// 리뷰 작성하면 리뷰 카드로 데이터 넘기기


// 리뷰 등록 버튼 -> 리뷰카드 이동

        editText_hashtag = findViewById(R.id.editText_hashtag);  // 해시태그
        editText_shoppingmall_url = findViewById(R.id.editText_shoppingmall_url);  // 쇼핑몰 url
        editText_detailed_review = findViewById(R.id.editText_detailed_review);  // 상세리뷰
        imageView_review_photo1 = (ImageView)findViewById(R.id.imageView_review_photo1);  // 리뷰 이미지
        ratingBar = findViewById(R.id.ratingBar);  // 만족도
        word_review_date = findViewById(R.id.review_date);  // 리뷰 작성 시간
        word_textView_review_writer = findViewById(R.id.textView_review_writer);  // 작성
        word_textView_reviewcard_number = findViewById(R.id.textView_reviewcard_number);  // 리뷰 고유 번호





        //데이터 보내기
// 리뷰 등록 버튼 -> 피드 화면 이동
        imageButton_review_register = findViewById(R.id.imageButton_review_register);
        imageButton_review_register.setOnClickListener(new ImageView.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent result = new Intent();  // 넘겨줄 데이터를 담는 인텐트

                String textView_shoppingmall_url = editText_shoppingmall_url.getText().toString();
                String textView_detailed_review_card = editText_detailed_review.getText().toString();
                String textView_hashtag = editText_hashtag.getText().toString();
                float int_ratingBar = ratingBar.getRating();
//                String review_date = word_review_date.getText().toString();
                // 평소 사이즈 로그인한 유저의 정보만 갖고 있는 쉐어드인 logined_user

                // 로그인한 회원의 정보를 가지고 있는 쉐어드에서 정보를 빼와서 글을 등록할 때 닉네임, 평소 사이즈를 불러오도록 했음.
                logined_user = getSharedPreferences("logined_user", Context.MODE_PRIVATE);   // 현재 로그인한 회원의 정보만 담겨있는 쉐어드를 불러와서
                // 불러온 sharedPreferences라는 이름의 SharedPreferencs를 확인하는 로그
                Log.e("feed 클래스에서 리뷰를 추가해서 피드에 추가할 때 ", "로그인한 회원의 정보가 있는 쉐어드인 logined_user 쉐어드를 가져온다" + logined_user);

                // sharedPreferences라는 이름의 쉐어드프리퍼런스에서 String을 가져오는데
                // 뭘 가져오냐면 사용자가 입력한 editText_email랑 같은 값을 찾아서 가져와서 String json이라는 변수에 넣어줌
                String json = logined_user.getString("login_user", "");  // logined_user라는 쉐어드에 저장되어있는 logined_user라는 키에 담겨있는 값을 불러와서 json이라는 변수에 담음
//                Log.e("login 클래스에서 로그인 버튼을 눌렀을 때", "sharedPreferences에서 j저장된 array(string으로 저장됐던) 가져오기 : " + sharedPreferences.getString("email", ""));
                Log.e("feed 클래스에서 로그인 버튼을 눌렀을 때", "여기 확인하기 : " + json);




// 로그인할 때 로그인한 회원의 정보를 배열로 가지고 와서 추출 후 각각의 key값을 줘서 저장했던 value를 호출


                String textView_email = logined_user.getString("user_email", "");
                Log.e("feed 에서 로그인한 회원 정보가 있는 쉐어드에서", "이메일 넣기 : " + textView_email + logined_user.getString("user_email", ""));

                String  textView_review_writer = textView_email;


//                String textView_reviewcard_number = textView_reviewcard_number_number.getText().toString();


                result.putExtra("쇼핑몰URL", editText_shoppingmall_url.getText().toString());  // putExtra로 데이터 보냄
                result.putExtra("상세리뷰", editText_detailed_review.getText().toString());  // putExtra로 데이터 보냄
                result.putExtra("해시태그", editText_hashtag.getText().toString());  // putExtra로 데이터 보냄
                result.putExtra("만족도", ratingBar.getRating());  // putExtra로 데이터 보냄
//                result.putExtra("리뷰시간", word_review_date.getText().toString());  // putExtra로 데이터 보냄
//                result.putExtra("작성자", textView_review_writer_writer.getText().toString());  // putExtra로 데이터 보냄
//                result.putExtra("리뷰고유번호", textView_reviewcard_number_number.getText().toString());  // putExtra로 데이터 보냄

                // 자신을 호출한 Activity로 데이터를 보낸다.
                setResult(RESULT_OK, result);
                new ContentDownload().execute("");
                Log.e("write_review 클래스에서", "AsyncTask를 이용한 로딩화면 넣는 중 : " );

                finish();
//ContentList & File Download

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
                //사진을 여러개 선택할수 있도록 한다 -> 하나만 올리는 걸로 수정했음.
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
                        Intent home_intent = new Intent(write_review.this,feed.class);
                        startActivity(home_intent);//액티비티 띄우기
//                        startActivityForResult(intent,sub);//액티비티 띄우기
                        break;
                    case R.id.action_search :
                        Intent search_intent = new Intent(write_review.this,searching.class);
                        startActivity(search_intent);//액티비티 띄우기
                        break;
                    case R.id.action_write_review :
                        Intent write_intent = new Intent(write_review.this,write_review.class);
                        startActivity(write_intent);//액티비티 띄우기
                        break;
                    case R.id.action_notification :
                        Intent insight_intent = new Intent(write_review.this,notification.class);
                        startActivity(insight_intent);//액티비티 띄우기
                        break;
                    case R.id.action_mycloset :
                        Intent mycloset_intent = new Intent(write_review.this, mypage.class);
                        startActivity(mycloset_intent);//액티비티 띄우기
                        break;
                }


                return false;

            }
        });

        // AsyncTask를 이용한 로딩 화면
//        new ContentDownload().execute("ContentList & File Download");


    }// onCreate 닫는 중괄호




    // AsyncTask로 로딩 화면 만드는 메소드
    class ContentDownload extends AsyncTask<String, Void, String> {

        ProgressDialog asyncDialog = new ProgressDialog(write_review.this);

        @Override

        protected void onPreExecute() {  // AsyncTask의 작업이 진행되는 동안 ProgressDialog를 '보겠다'는 선언


            asyncDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            asyncDialog.setMessage("리뷰 업로드 중입니다");
            asyncDialog.show();

            super.onPreExecute();
        }

        @Override

        protected String doInBackground(String... strings) {  // doInBackground동안에는 계속해서 progressdialog가 올라와 있는 상태
            // for문이 0.5초마다 돌아가면서 progress의 값이 계속 설정되고 bar가 늘어나는것을 볼 수 있습니다.


            for (int i = 0; i < 5; i++) {

                asyncDialog.setProgress(i * 30);
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }

            String abc = "Parsing & Download OK!!!";

            return abc;
        }

        @Override

        protected void onPostExecute(String s) {  // AsyncTask의 작업이 종료된 후 onPostExcute에 진입 후 바로 Dialog를 닫아줌

            super.onPostExecute(s);

//            asyncDialog.dismiss();
//
//            Toast.makeText(write_review.this, "리뷰가 업로드 되었습니다", Toast.LENGTH_SHORT).show();

        }

    }
    // AsyncTask로 로딩 화면 만드는 메소드 여기까지


// 리뷰 작성시 각 리뷰마다 부여되는 고유 번호 생성하는 메소드
//    private static final String ALPHA_NUMERIC_STRING = "0123456789";
//
//    public static String randomkeygenerator() {
//        int count = 8;
//        StringBuilder builder = new StringBuilder();
//        while (count-- != 0) {
//            int character = (int) (Math.random() * ALPHA_NUMERIC_STRING.length());
//            builder.append(ALPHA_NUMERIC_STRING.charAt(character));
//        }
//        return builder.toString();
//    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {



        if(requestCode == PICTURE_REQUEST_CODE)
        {
            if (resultCode == RESULT_OK)
            {

                //기존 이미지 지우기
                imageView_review_photo1.setImageResource(0);


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
                                        Toast myToast = Toast.makeText(this.getApplicationContext(),"1개의 이미지만 업로드 가능합니다", Toast.LENGTH_SHORT);
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
        ProgressDialog asyncDialog = new ProgressDialog(write_review.this);
        if (asyncDialog != null) {
            asyncDialog.dismiss();
            asyncDialog = null;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e("write_review","onDestroy");

        //액티비티가 종료되려고 합니다.

    }

}





