package com.example.fitme;

import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class sign_up extends AppCompatActivity {

    ArrayList<feed_MainData>  bookmarked_arrayList;

    private ArrayList<List> data = new ArrayList<List>();

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
//    boolean checkEmail;


    private static int PICK_IMAGE_REQUEST = 1;
//    static final String TAG = "MainActivity";
    final int REQUEST_CODE = 10000;


    Button button_sign_up_complete, button_sign_in;  //회원가입하기 버튼
    EditText editText_mysize, editText_nickname, editText_email, editText_password, editText_password_confirm;  // 평소 사이즈 입력하는 칸, 닉네임 적는 칸
    BottomNavigationView bottomNavigationView;   // 바텀 네이게이션 메뉴  -> 하단바
    ImageButton imageButton_image;

    String email ="";
    String password ="";
    String currentSize = "";
    String nickname = "";
    String profile_img = "";
    // 프로필 이미지

    ImageView  imageView_user_profile_image ;
//    final int REQUEST_CODE = 300;
    Uri uri; // 전역변수로 Uri를 선언해줘야 클래스 내 다른 메소드 내에서도 사용할 수 있음.


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Log.e("sign_up","onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        final EditText editText_email = (EditText) findViewById(R.id.editText_email);
        final EditText editText_password = (EditText)findViewById(R.id.editText_password);
        final EditText editText_mysize= (EditText) findViewById(R.id.editText_mysize);
        final EditText editText_nickname = (EditText)findViewById(R.id.editText_nickname);

        imageView_user_profile_image = (ImageView) findViewById(R.id.imageView_user_profile_image);
        imageView_user_profile_image.setImageResource(R.drawable.img_dd_profile);


// 프로필 사진 등록을 위해 갤러리 모양의 버튼을 누르면 사진을 가져와 프로필 사진에 등록.
        /** 프로필 사진 등록할 때 갤러리에서 가져오는 버튼**/
        imageButton_image = (ImageButton)findViewById(R.id.imageButton_image);
        imageButton_image.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Log.e("sign_up 클래스 ","imageButton_image.setOnClickListener : 이미지 버튼을 클릭했습니다");
//                Intent intent = new Intent();
//                intent.setType("image/*");
//                intent.setAction(Intent.ACTION_OPEN_DOCUMENT);
//                startActivityForResult(Intent.createChooser(intent,
//                        "Select Picture"), REQUEST_CODE);

                Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT );
                Log.e("이미지 버튼을 클릭했을 때 되고 있나요?","");
//                intent.setType(android.provider.MediaStore.Images.Media.CONTENT_TYPE);
                intent.setData(android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//                Intent intent = new Intent(Intent.ACTION_GET_CONTENT); //ACTION_PIC과 차이점?
                intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE,true);
                intent.setType("image/*"); //이미지만 보이게
                //Intent 시작 - 갤러리앱을 열어서 원하는 이미지를 선택할 수 있다.
                startActivityForResult(intent,REQUEST_CODE);

                Log.e("이미지 버튼을 클릭했을 때 되고 있나요?","RESULT_OK : "+ RESULT_OK);
    }
});


// 회원가입 완료 버튼 -> 로그인 화면으로 이동


        button_sign_up_complete = findViewById(R.id.button_sign_up_complete);
        button_sign_up_complete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // 이메일 형식 검사 & 닉네임과 평소 사이즈가 빈칸인지 검사


                // 사용자가 입력한 값이 있을 때(0보다 클 때)
                if (editText_email.length() > 0 && editText_password.length() > 4 && editText_password_confirm.length() > 4 && editText_nickname.length()>0&& editText_mysize.length()>0) {
                    // 길이가 0보다 클 때, 비밀번호가 5자 이상일 때

                    if (!checkEmail(editText_email.getText().toString())) {
                        //알맞은 이메일 패턴을 입력해 주세요.
                        Toast.makeText(sign_up.this, "이메일 형식에 맞게 입력해주세요", Toast.LENGTH_SHORT).show();
                    } else {
                        //올바른 이메일 패턴입니다.

                        // 회원가입 완료 버튼을 누르면 인텐트로 화면전환 하고
                        Intent intent = new Intent(sign_up.this, login.class);
                        Toast.makeText(sign_up.this, "회원가입을 완료했습니다!", Toast.LENGTH_SHORT).show();
                        startActivity(intent); //액티비티 이동, 여기서 1000은 식별자. 아무 숫자나 넣으주면 됨.
                    }



/** 회원가입에서 입력한 정보 로그인으로 데이터 넘겨주기(이메일) **/
                    Intent result = new Intent();
                    result.putExtra("EMAIL", editText_email.getText().toString());
                    result.putExtra("PASSWORD", editText_password.getText().toString());

                    // 자신을 호출한 Activity로 데이터를 보낸다.
                    setResult(RESULT_OK, result);
                    finish();


                    //JSONObject에 입력한 값을 저장하기 (SharedPreference)
                    JSONObject jsonObject = new JSONObject();  // JSONObject 객체 선언
                    JSONArray jsonArray = new JSONArray();       // JSONArray 객체 선언


                    email = editText_email.getText().toString();
                    password = editText_password.getText().toString();
                    currentSize = editText_mysize.getText().toString();
                    nickname = editText_nickname.getText().toString();
                    profile_img = imageView_user_profile_image.toString();
//                profile_img = imageView_user_profile_image.get().toString();

                    try {

                        // 로그인 정보만 담고 있는 쉐어드에 회원가입 완료 버튼을 눌렀을 때 각자의 키값에 넣어주는 중?

                        jsonObject.put("email", email);
                        jsonObject.put("password", password);
                        jsonObject.put("currentSize", currentSize);
                        jsonObject.put("nickname", nickname);
                        jsonObject.put("profile_img", profile_img);
//                        jsonObject.put("bookmarkList", bookmarked_arrayList);
//                        jsonObject.put("bookmarkList", myreview_arrayList); 내가쓴 리뷰 리스트

                        Log.e("onCreate 회원가입 완료 버튼을 누르면", "JSONObject에 email을 넣었습니다 : " + email);
                        Log.e("onCreate 회원가입 완료 버튼을 누르면", "JSONObject에 password을 넣었습니다 : " + password);
                        Log.e("onCreate 회원가입 완료 버튼을 누르면", "JSONObject에 currentSize을 넣었습니다 : " + currentSize);
                        Log.e("onCreate 회원가입 완료 버튼을 누르면", "JSONObject에 nickname을 넣었습니다 : " + nickname);
                        Log.e("onCreate 회원가입 완료 버튼을 누르면", "JSONObject에 profile_img을 넣었습니다 : " + profile_img);


                        jsonArray.put(jsonObject);  // jsonArray에 위에서 저장한 jsonObject를 put

                        Log.e("onCreate 회원가입 완료 버튼을 누르면", "jsonArray에 위에서 저장한 jsonObject를 put");


                    } catch (JSONException e) {  // 예외 처리
                        e.printStackTrace();
                    }

                    String jsondata = jsonArray.toString();  // jsonArray를 String값으로 바꿈. String으로 바꾼 jsonArray를 jsondata라고 이름붙임.
                    writeArrayList(jsondata);                    // saveArrayList 메소드를 실행할건데 josndata를 사용할 것 -> onCreate 밖에 메소드 만듦.


                    Log.e("onCreate 회원가입 완료 버튼을 누르면", "JSONObject :  " + jsonObject);
                    Log.e("onCreate 회원가입 완료 버튼을 누르면", "JSONArray : " + jsonArray);
                }else {
                    Toast.makeText(sign_up.this, "필수 정보(*)를 입력해주세요", Toast.LENGTH_SHORT).show();
                }
            }
        });


// 회원가입한 정보 로그인 정보로 넘겨주기

        editText_password_confirm = (EditText) findViewById(R.id.editText_password_confirm);


        // 비밀번호 일치 검사
        editText_password_confirm.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                String password = editText_password.getText().toString();
                String confirm = editText_password_confirm.getText().toString();

                if (password.equals(confirm)) {
                    editText_password.setTextColor(Color.BLACK);
                    editText_password_confirm.setTextColor(Color.BLACK);

                } else {

                    editText_password.setTextColor(Color.RED);
                    editText_password_confirm.setTextColor(Color.RED);

                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }


        });



    }// onCreate 닫는 중괄호

    /**
     * 이메일 포맷 체크
     * @param email
     * @return
     */
    public static boolean checkEmail(String email){

        String regex = "^[_a-zA-Z0-9-\\.]+@[\\.a-zA-Z0-9-]+\\.[a-zA-Z]+$";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(email);
        boolean isNormal = m.matches();
        return isNormal;

    }

    // ArrayList 에 기록된 값을 JSONArray 배열에 담아 문자열로 저장

    // 회원 정보를 모두 모아 놓는 곳 -> sharedPreferences 라는 이름의 쉐어드
    public void writeArrayList(String jsondata) {

        if (data != null) {

            // JSONArray 생성
            sharedPreferences = getSharedPreferences("sharedPreferences", Context.MODE_PRIVATE);
            editor = sharedPreferences.edit();

//            JSONArray array = new JSONArray();

            for (int i = 0; i < data.size(); i++) {

                // 리스트 아이템 하나씩 JSONArray 배열에 추가

                JSONArray array = new JSONArray();
                array.put(data.get(i));

                Log.e("회원정보 확인하는 메소드","확인중" + array.put(data.get(i)));



            }

            editor.putString(email, jsondata);  // 회원가입시 입력한 email이 각 arrayList의 key 값이 됨.

            Log.e("saveArrayList 메소드","확인중" + editor.putString(email,jsondata));
            editor.commit();
            Log.e("saveArrayList 메소드","ArrayList인 jsonData를 String 형태로 sharedPreference에 저장했습니다 ");


        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {







//        if(requestCode == REQUEST_CODE) {
            if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {
//
//
//                Bitmap image_bitmap = null;
//                try {
//                    image_bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), data.getData());
//
//                    //배치해놓은 ImageView에 set
//                    imageView_user_profile_image.setImageBitmap(image_bitmap);
//
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//


////                //기존 이미지 지우기
//                imageView_user_profile_image.setImageResource(0);

//                Log.e("sign_up 클래스 ","onActivityResult : 기존 이미지 지워졌나요?");



                //ClipData 또는 Uri를 가져온다
                uri = data.getData();  // 해당 이미지의 파일 경로 즉, uri 정보를 받는다
                Log.e("sign_up 클래스 ","onActivityResult : uri 이미지 가져왔나요?" + uri);

                ClipData clipData = data.getClipData();

                if(clipData!=null) {


                    imageView_user_profile_image .setImageURI(Uri.parse(uri.toString()));
                    Log.e("sign_up 클래스 ","onActivityResult : imageView_user_profile_image 이미지 가져왔나요?" + imageView_user_profile_image);

                }
                else if(uri != null)
                {
                    imageView_user_profile_image.setImageURI(uri);
                    Log.e("sign_up 클래스 ","onActivityResult : imageView_user_profile_image 이미지 가져왔나요?" + imageView_user_profile_image);
                }
            }
//        }
    }//onActivityResult 메소드 닫는 중괄호



//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        if (resultCode == RESULT_OK) {
//            if (requestCode == SELECT_PICTURE) {
//                Uri selectedImageUri = data.getData();
//                selectedImagePath = getPath(selectedImageUri);
//
//                int position = getIntent().getIntExtra(Keys.POSTION, 0);
//
//
//            }
//        }
//    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//
//
//
//        if(requestCode == PICTURE_REQUEST_CODE)
//        {
//            if (resultCode == RESULT_OK)
//            {
//
////                //기존 이미지 지우기
//                imageView_user_profile_image.setImageResource(0);
//
//
//                //ClipData 또는 Uri를 가져온다
//                Uri uri = data.getData();
//                ClipData clipData = data.getClipData();
//
//                //이미지 URI 를 이용하여 이미지뷰에 순서대로 세팅한다.
//                if(clipData!=null)
//                {
//
//                    // 최대 업로드 사진 갯수 5개
//                    for(int i = 0; i < 5; i++)
//                    {
//                        if(i<clipData.getItemCount()){
//                            Uri urione =  clipData.getItemAt(i).getUri();
//                            switch (i){
//                                case 0:
//                                    imageView_user_profile_image.setImageURI(urione);
//                                    break;
////                                case 1:
////                                    imageView_review_photo2.setImageURI(urione);
////                                    break;
////                                case 2:
////                                    imageView_review_photo3.setImageURI(urione);
////                                    break;
////                                case 3:
////                                    imageView_review_photo4.setImageURI(urione);
////                                    break;
////                                case 4:
////                                    imageView_review_photo5.setImageURI(urione);
////                                    break;
//                                default:
//                                    Toast myToast = Toast.makeText(this.getApplicationContext(),"1개의 이미지만 업로드 가능합니다", Toast.LENGTH_SHORT);
//                                    myToast.show();
//                            }
//                        }
//                    }
//                }
//                else if(uri != null)
//                {
//                    imageView_user_profile_image.setImageURI(uri);
//                }
//            }
//        }
//    }


    @Override
    protected void onRestart() {
        super.onRestart();
        Log.e("sign_up","onRestart");
        //액티비티가 중단되었다가 다시 시작
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.e("sign_up","onStart");
        //액티비티가 화면에 나타나기 시작
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e("sign_up","onResume");
        //액티비티가 화면에 나타나고 상호작용이 가능해짐
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.e("sign_up","onPause");
        //다른 액티비티가 시작되려함, 이 액티비티는 중단되려하고 백그라운드로 들어갑니다.
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.e("sign_up","onStop");
        //액티비티가 더 이상 화면에 나타나지 않음,중단된 상태
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e("sign_up","onDestroy");
        //액티비티가 종료되려고 합니다.
    }
}