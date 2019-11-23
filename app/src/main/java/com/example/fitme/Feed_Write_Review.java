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
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.squareup.picasso.Picasso;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static com.example.fitme.Feed_Main_Activity.randomkeygenerator;

public class Feed_Write_Review extends AppCompatActivity {
    /**
     * 리뷰 피드(feed)에 올라갈 리뷰 글을 작성하는 액티비티
     **/

    // 현재 로그인한 유저의 정보만 담는 쉐어드 프리퍼런스
    private ArrayList<List> userData = new ArrayList<List>();

    private SharedPreferences logined_user;
    private SharedPreferences.Editor user_editor;
    //

    private ArrayList<Feed_Main_ItemData> arrayList, myreview_arrayList;

    TextView review_date, textView_review_writer, textView_reviewcard_number;
    EditText editText_shoppingmall_url, editText_hashtag, editText_detailed_review;
    ImageView imageView_review_photo1;
    ImageButton imageButton_open_web_browser, imageButton_search_download, imageButton_image, imageButton_review_register, imageButton_write_review_back;
    BottomNavigationView bottomNavigationView; // 바텀 네이게이션 메뉴  -> 하단바
    RatingBar ratingBar;


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


// 리뷰 등록 버튼 -> 리뷰카드 이동

        editText_hashtag = findViewById(R.id.editText_hashtag);  // 해시태그
        editText_shoppingmall_url = findViewById(R.id.editText_shoppingmall_url);  // 쇼핑몰 url
        editText_detailed_review = findViewById(R.id.editText_detailed_review);  // 상세리뷰
        imageView_review_photo1 = (ImageView) findViewById(R.id.imageView_review_photo1);  // 리뷰 이미지
        ratingBar = findViewById(R.id.ratingBar);  // 만족도
        review_date = findViewById(R.id.review_date);  // 리뷰 작성 시간
        textView_review_writer = findViewById(R.id.textView_review_writer);  // 작성
        textView_reviewcard_number = findViewById(R.id.textView_reviewcard_number);  // 리뷰 고유 번호


        // 뒤로 가기 버튼 눌렀을 때 피드(메인 화면)로 이동

        imageButton_write_review_back = findViewById(R.id.imageButton_write_review_back);
        imageButton_write_review_back.setOnClickListener(new ImageView.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent register_intent = new Intent(Feed_Write_Review.this, Feed_Main_Activity.class);
                startActivity(register_intent); //액티비티 이동
                finish(); // 액티비티 finish 시킴

            }
        });


        //데이터 보내기
// 리뷰 등록 버튼 -> 피드 화면 onActivityResult 메소드 확인
        imageButton_review_register = findViewById(R.id.imageButton_review_register);
        imageButton_review_register.setOnClickListener(new ImageView.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (editText_detailed_review.length() > 0) {
                    if (uri != null | pixabay_url != null) {// 상세 리뷰를 작성하고 이미지가 null값이 아닐 때

                        Intent result = new Intent();  // 넘겨줄 데이터를 담는 인텐트

/** 지금 여기가 MainData 클래스를 통해서 리사이클러뷰 ArrayList에 넣어줄 값들을 불러와서 set해주는 곳임.**/
/** 사용자가 입력한 데이터를 변수에 담아줌 **/
                        String textView_shoppingmall_url = editText_shoppingmall_url.getText().toString();
                        String textView_detailed_review_card = editText_detailed_review.getText().toString();
                        String textView_hashtag = editText_hashtag.getText().toString();
                        float float_ratingBar = ratingBar.getRating();

/** 로그인한 회원 정보 쉐어드에서 데이터 받아옴**/
                        // 로그인한 회원의 정보를 가지고 있는 쉐어드에서 정보를 빼와서 글을 등록할 때 닉네임, 평소 사이즈를 불러오도록 했음.
                        logined_user = getSharedPreferences("logined_user", Context.MODE_PRIVATE);   // 현재 로그인한 회원의 정보만 담겨있는 쉐어드를 불러와서
                        // 쉐어드프리퍼런스에서 String을 가져오는데 사용자가 입력한 editText_email랑 같은 값을 찾아서 가져와서 String json이라는 변수에 넣어줌
                        String json = logined_user.getString("login_user", "");  // logined_user라는 쉐어드에 저장되어있는 logined_user라는 키에 담겨있는 값을 불러와서 json이라는 변수에 담음
                        String textView_nickname = logined_user.getString("user_nickname", "");
                        String textView_mysize = logined_user.getString("user_size", "");
                        String imageView_reviewcard_profile_image = logined_user.getString("user_profileimage", "");
                        String textView_review_writer = logined_user.getString("user_email", "");

                        // 리뷰 작성 시간
                        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd  HH:mm:ss", Locale.KOREA);
                        String date = dateFormat.format(new Date());
                        String review_date = date;


                        String textView_reviewcard_number = randomkeygenerator();  // 리뷰 고유 번호

                        float_ratingBar = result.getFloatExtra("만족도", 0);
                        String imageView_reviewcard_img1 = result.getStringExtra("리뷰이미지");


                        // 데이터를 myreview_arrayList에 넣어줌 -> 내가 쓴 리뷰 리사이클러뷰에 올라감
                        myreview_arrayList = new ArrayList<>();
                        // myreview_arrayList로 데이터를 보여주는 내가 쓴 리뷰 리사이클러뷰를 로드해라
                        myreview_loadData();


                        // 피드 리사이클러뷰의 데이터를 담는 arrayList의 해당 position을 받아(get) myreview_arrayList에 추가하라
                        Feed_Main_ItemData feed_Main_ItemData = new Feed_Main_ItemData(textView_shoppingmall_url, textView_detailed_review_card,
                                float_ratingBar, textView_hashtag, review_date, textView_review_writer, textView_reviewcard_number,
                                textView_nickname, textView_mysize, imageView_reviewcard_img1, imageView_reviewcard_profile_image);
                        myreview_arrayList.add(feed_Main_ItemData);
                        // 위에서 가져온 값들 확인하는 로그들
                        Log.e("Feed_Write_Review 클래스에서 리뷰를 추가해서 피드에 추가할 때 ", "로그인한 회원의 정보가 있는 쉐어드인 logined_user 쉐어드를 가져온다" + logined_user);
                        Log.e("Login 클래스에서 로그인 버튼을 눌렀을 때", "sharedPreferences에서 j저장된 array(string으로 저장됐던) 가져오기 : " + logined_user.getString("email", ""));
                        Log.e("Feed_Write_Review 클래스에서 로그인 버튼을 눌렀을 때", "여기 확인하기 : " + json);
                        Log.e("[리뷰 추가] Feed_Write_Review 에서 로그인한 회원 정보가 있는 쉐어드에서", "닉네임 넣기 : " + textView_nickname + logined_user.getString("nickname", ""));
                        Log.e("[리뷰 추가] Feed_Write_Review 에서 로그인한 회원 정보가 있는 쉐어드에서", "평소 사이즈 넣기 : " + textView_mysize);
                        Log.e("Feed_Main_Activity 클래스 onActivityResult ", "시간 받아오는 중 : (dateFormat + review_date + date)" + dateFormat + review_date + date);
                        Log.e("Feed_Write_Review 만족도", float_ratingBar + "만족도를 가져왔습니다!!!!!!!");
                        Log.e("Feed_Write_Review 작성자", textView_hashtag + "작성자를 가져왔습니다!!!!!!!!!");
                        Log.e("Feed_Write_Review 클래스 onActivityResult---------->", "리뷰이미지" + imageView_reviewcard_img1);
                        Log.e("Feed_Write_Review 클래스에서 리뷰 등록 버튼을 눌렀을 때", "myreview_arrayList : " + myreview_arrayList);


                        // 로그인할 때 로그인한 회원의 정보를 배열로 가지고 와서 추출 후 각각의 key값을 줘서 저장했던 value를 호출


                        /**인텐트 result에 담음**/

                        String textView_email = logined_user.getString("user_email", "");

                        result.putExtra("쇼핑몰URL", editText_shoppingmall_url.getText().toString());  // putExtra로 데이터 보냄
                        result.putExtra("상세리뷰", editText_detailed_review.getText().toString());  // putExtra로 데이터 보냄
                        result.putExtra("해시태그", editText_hashtag.getText().toString());  // putExtra로 데이터 보냄
                        result.putExtra("만족도", ratingBar.getRating());  // putExtra로 데이터 보냄
                        if (uri != null) {
                            result.putExtra("리뷰이미지", uri.toString());  // String으로 바꿔서 putExtra로 데이터 보냄.
                        } else if (pixabay_url != null) {
                            result.putExtra("리뷰이미지", pixabay_url);  // String으로 바꿔서 putExtra로 데이터 보냄.
                        }
                        result.putExtra("작성자", textView_review_writer);  // putExtra로 데이터 보냄
                        result.putExtra("리뷰고유번호", textView_reviewcard_number);  // putExtra로 데이터 보냄
                        result.putExtra("작성시간", review_date);  // putExtra로 데이터 보냄

// 확인 로그
                        Log.e("Feed_Write_Review 로그인한 회원 정보가 있는 쉐어드에서", "이메일 넣기 : " + textView_email + logined_user.getString("user_email", ""));
                        Log.e("작성자 ,리뷰고유번호, 작성시간", textView_review_writer + textView_reviewcard_number + review_date);

                        // 자신을 호출한 Activity로 데이터를 보낸다.
                        setResult(RESULT_OK, result);

                        new ContentDownload().execute(""); // 리뷰 작성호 보이는 progress bar 로딩 화면 Asynctask
                        Log.e("Feed_Write_Review 클래스에서", "AsyncTask를 이용한 로딩화면 넣는 중 : ");

                        finish();

//ContentList & File Download
                    } else { // 이미지를 업로드 하지 않았을 때
                        Toast.makeText(Feed_Write_Review.this, "이미지를 업로드해주세요", Toast.LENGTH_SHORT).show();
                    }

                } else { // 상세 리뷰를 작성하지 않았을 때
                    Toast.makeText(Feed_Write_Review.this, "상세 리뷰를 작성해주세요", Toast.LENGTH_SHORT).show();
                }
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


// 이미지 버튼을 누르면 이미지에서 사진 가져오기
        ImageButton imageButton_image = (ImageButton) findViewById(R.id.imageButton_image);
        imageButton_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //사진을 여러개 선택할수 있도록 한다 -> 하나만 올리는 걸로 수정했음.
                Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);  // 지금은 한장인데 나중에 사진 리사이클러뷰로 할 수도 있어서  Multiple로 냅둠.
                intent.setType("image/*");

                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICTURE_REQUEST_CODE);
            }
        });


    }// onCreate 닫는 중괄호

    // sharedPreference에 저장한 ArrayList 를 가져옴 (리사이클러뷰)
    private void myreview_loadData() {

        SharedPreferences myreviewShared = getSharedPreferences("myreviewShared", MODE_PRIVATE);
        Gson gson = new Gson();

        // 로그인 하고 있는 사용자의 이메일을 키값으로 갖는 value에
        logined_user = getSharedPreferences("logined_user", Context.MODE_PRIVATE);   // 현재 로그인한 회원의 정보만 담겨있는 쉐어드를 불러와서
        String feed_email = logined_user.getString("user_email", "");
        Log.e("Feed_Main_Activity 클래스 ", "로그인한 유저의 이메일 호출 : " + feed_email);

        String json = myreviewShared.getString(feed_email, null);
        Type type = new TypeToken<ArrayList<Feed_Main_ItemData>>() {
        }.getType();

        Log.e("Feed_Main_Activity 클래스 (myreview_loadData)", "typeToken객체 생성 :" + type);

        myreview_arrayList = gson.fromJson(json, type);
        Log.e("Feed_Main_Activity 클래스 (myreview_loadData)", "fromJson : arryaList(myreview_arrayList)는 " + myreview_arrayList);

        if (myreview_arrayList == null) {
            myreview_arrayList = new ArrayList<>();
        }
    } // myreview_loadData 메소드 닫는 중괄호

    // AsyncTask로 로딩 화면 만드는 메소드
    class ContentDownload extends AsyncTask<String, Void, String> {

        ProgressDialog asyncDialog = new ProgressDialog(Feed_Write_Review.this);

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


        }

    }
    // AsyncTask로 로딩 화면 만드는 메소드 여기까지


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == PICTURE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {

                //기존 이미지 지우기
                imageView_review_photo1.setImageResource(0);


                //ClipData 또는 Uri를 가져온다
                uri = data.getData();  // 해당 이미지의 파일 경로 즉, uri 정보를 받는다

                ClipData clipData = data.getClipData();
                Log.e("Feed_Write_Review 클래스에서", "이메일 가져오는 중 , uri : " + uri.toString() + "," + uri);

                // 받아온 이미지를 인텐트에 담아서 putExtra로 보내주기.
                // -> 어디서 받냐면 onCreate에서 Feed_Main_Activity 클래스에 보낼 데이터 담을 때

                //ClipData 또는 Uri를 가져온다

                //이미지 URI 를 이용하여 이미지뷰에 순서대로 세팅한다.
                // 로그 찍었을 때 ClipData로는 실행이 되지 않고 uri방식으로 실행된다.
                if (clipData != null) {


                    imageView_review_photo1.setImageURI(Uri.parse(uri.toString()));

                    Log.e("Feed_Write_Review 클래스에서", "uri--> 확인중 " + uri.toString());

                } else if (uri != null) {
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

            if (pixabay_url != null) {
                Uri pixabay_url_url = Uri.parse(pixabay_url);
                Picasso.get().load(pixabay_url).fit().centerInside().into(imageView_review_photo1);
                Log.e("Feed_Write_Review 클래스에서", "pixabay_url_url,uri--> 확인중 " + pixabay_url_url + "," + imageView_review_photo1);
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
        ProgressDialog asyncDialog = new ProgressDialog(Feed_Write_Review.this);
        if (asyncDialog != null) {
            asyncDialog.dismiss();
            asyncDialog = null;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e("Feed_Write_Review", "onDestroy");

        //액티비티가 종료되려고 합니다.

    }

}





