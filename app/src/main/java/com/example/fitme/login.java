package com.example.fitme;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class login extends AppCompatActivity {

    // login java 클래스에서 필요한 객체 선언
    private ArrayList<List> userData= new ArrayList<List>();

    private SharedPreferences logined_user;
    private SharedPreferences.Editor user_editor;

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    // User Session Manager Class
//    UserSession session; // 회원 정보에 필요한 메소드 만들어놓은 UserSession 클래스 객체로 불러옴.

    String str; //  shared에 json으로 저장한 값을 가져올 때 jsonArray에서 받아온 JsonRaw 값을 담아올 String 변수

    //    JSONObject jsonObject = new JSONObject();  // JSONObject 객체 선언
    private JSONArray jsonArray = new JSONArray();       // JSONArray 객체 선언
    private ArrayList<String> userList;


    CheckBox checkBox_remember_login;
    String JOINEMAIL;
    String JOINPW;
    EditText editText_email, editText_password; // -> null값이면 토스트 값 뜨도록 // -> null값이면 토스트 값 뜨도록
    Button button_sign_in, button_signUp, button_sign_up_complete; // -> 로그인 버튼 -> 피드로 화면 연결  // -> 회원가입 버튼 -> 회원가입 화면으로 연결


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.e("login", "onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // 로그인 화면으로 넘어 오기 전에 로딩 스플래시 화면
//        Intent intent = new Intent(this, first_main.class);
//        startActivity(intent);


// SharedPreference 로 회원정보를 jsonObject에 저장해서 jsonArrayList 형태로 SharedPreference에 저장

//  SharedPreference로 로그인 정보 기억하기

        // 필요한 뷰객체 매칭
        editText_email = (EditText) findViewById(R.id.editText_email);
        editText_password = (EditText) findViewById(R.id.editText_password);
        checkBox_remember_login = (CheckBox) findViewById(R.id.checkBox_remember_login);
        button_sign_in = (Button) findViewById(R.id.button_sign_in);
        //SharedPreference와 editor 가져오기
//        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this); // 아래 코드와 같은 의미인데 this는 액티비티 정도
        sharedPreferences = getSharedPreferences("sharedPreferences", Context.MODE_PRIVATE);  // declare the database = 데이터 베이스 선언  // 액티비티 안이 아닐 떄는 context로 접근해야함 (안일 때는 this)
        editor = sharedPreferences.edit();  // 위에서 선언한 데이터 베이스에 아이템을 put 할 수 있는
        // User Session Manager
//        session = new UserSession(getApplicationContext());  // 회원 정보에 필요한 메소드 만들어놓은 UserSession 클래스 객체로 불러옴.

        checkSharedPreferences();//로그인정보기억하기체크박스가눌려있는지설정값.메소드(onCreate밖에있음)

//      로그인 버튼을 눌렀을 때
        button_sign_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sharedPreferences = getSharedPreferences("sharedPreferences", Context.MODE_PRIVATE);   // sharedpreference를 불러와서
                // 불러온 sharedPreferences라는 이름의 SharedPreferencs를 확인하는 로그
                Log.e("login 클래스에서 로그인 버튼을 눌렀을 때", "회원가입할 때 회원 정보를 저장한 sharedPreferences를 가져온다" + sharedPreferences);

                // sharedPreferences라는 이름의 쉐어드프리퍼런스에서 String을 가져오는데
                // 뭘 가져오냐면 사용자가 입력한 editText_email랑 같은 값을 찾아서 가져와서 String json이라는 변수에 넣어줌
                String json = sharedPreferences.getString(editText_email.getText().toString(), "");
//                Log.e("login 클래스에서 로그인 버튼을 눌렀을 때", "sharedPreferences에서 j저장된 array(string으로 저장됐던) 가져오기 : " + sharedPreferences.getString("email", ""));
                Log.e("login 클래스에서 로그인 버튼을 눌렀을 때", "여기 확인하기 : " + json);


                // 사용자가 입력한 값을 변수에 담기 (회원정보와 비교하기 위해)
                String inputemail = editText_email.getText().toString();   // 사용자가 email에 입력한 값을 변수에 담음 (나중에 비교할 때 쓸 것)
                String inputpassword = editText_password.getText().toString();   // 사용자가 email에 입력한 값을 변수에 담음 (나중에 비교할 때 쓸 것)

                // 사용자가 이메일이나 비밀번호 입력 창에 무엇인가 입력했을 때
                if (inputemail.trim().length() > 0 && inputpassword.trim().length() > 0) {  // 길이가 0보다 클 때

                    // editText 로 입력한 이메일이 null 값이 아니면 = 무엇인가를 입력하면 -> 일단 쉐어드에 사용자가 입력한 이메일이 있는지 확인
                    if (json != null) {

                        try {

                            JSONArray jsonArray = new JSONArray(json);
                            for (int i = 0; i < jsonArray.length(); i++) {

                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                Log.e("login 회원정보 확인", "sharedPreferences에서 저장된 array(string으로 저장됐던) 가져오기 : " + jsonObject);

                                String emailemail = jsonObject.getString("email");
                                Log.e("login 회원정보 확인", "sharedPreferences에서 저장된 array(string으로 저장됐던) 가져오기 : " + jsonObject.getString("email"));


                                String passwordpassword = jsonObject.getString("password");
                                Log.e("login 회원정보 확인", "sharedPreferences에서 저장된 array(string으로 저장됐던) 가져오기 : " + jsonObject.getString("password"));


                                String sizesize = jsonObject.getString("currentSize");
                                Log.e("login 회원정보 확인", "sharedPreferences에서 저장된 array(string으로 저장됐던) 평소 사이즈 가져오기 : " + jsonObject.getString("currentSize"));


                                String nicknamenickname = jsonObject.getString("nickname");
                                Log.e("login 회원정보 확인", "sharedPreferences에서 저장된 array(string으로 저장됐던) 닉네임 가져오기 : " + jsonObject.getString("nickname"));


                                String profileimageprofileimage = jsonObject.getString("profile_img");
                                Log.e("login 회원정보 확인", "sharedPreferences에서 저장된 array(string으로 저장됐던) 프로필 이미지 가져오기 : " + jsonObject.getString("profile_img"));



                                // 저장된 회원가입 정보와 로그인 입력한 정보를 비교하는 중
                                // 사용자가 입력한 이메일과 쉐어드에 저장한 email 값이 같은지 && 입력한 비밀번호와 쉐어드에 저장한 password 값이 같은지
                                if (inputemail.equals(emailemail) && inputpassword.equals(passwordpassword)) {

                                    // 사용자가 입력한 값이 jsonObject에 있는 데이터를 담아준 email과 password와 맞으면 로그인되어서 피드 화면으로 넘어가라.
                                    Intent intent = new Intent(getApplicationContext(), feed.class);
                                    startActivity(intent);
// 로그인한 회원의 정보를 로그인한 회원의 정보만 담는 SharedPreference인  logined_user 라는 이름의 쉐어드에 담을 것.

                                    //위에서 가져온 값을 쉐어드에 넣어주기.
                                    // JSONArray 생성
                                    logined_user = getSharedPreferences("logined_user", Context.MODE_PRIVATE);
                                    user_editor = logined_user.edit();


// 로그인할 때 로그인한 회원의 정보를 배열로 가지고 와서 추출 후 각각의 key값을 줘서 저장했던 value를 호출
                                    user_editor.putString("user_email", emailemail);  // 회원가입시 입력한 이메일이 각 arrayList의 key 값이 됨.
                                    user_editor.putString("user_password", passwordpassword);  // 회원가입시 입력한 비밀번호가 각 arrayList의 key 값이 됨.
                                    user_editor.putString("user_size", sizesize);  // 회원가입시 입력한 평소 사이즈가 각 arrayList의 key 값이 됨.
                                    user_editor.putString("user_nickname", nicknamenickname);  // 회원가입시 입력한 닉네임이 각 arrayList의 key 값이 됨.
                                    user_editor.putString("user_profileimage", profileimageprofileimage);  // 회원가입시 입력한 프로필 이미지이 각 arrayList의 key 값이 됨.

                                    user_editor.commit();   //제출


                                    String user_jsondata = jsonArray.toString();  // jsonArray를 String값으로 바꿈. String으로 바꾼 jsonArray를 user_jsondata라고 이름붙임.
                                    Log.e("login 클래스에서 로그인 버튼을 눌렀을 때", "user_jsondata 확인 중 : " + user_jsondata);
                                    user_writeArrayList(user_jsondata);                    // saveArrayList 메소드를 실행할건데 josndata를 사용할 것 -> onCreate 밖에 메소드 만듦.
                                    Log.e("login 클래스에서 로그인 버튼을 눌렀을 때", "user_writeArrayList(user_jsondata) 확인중 : " + user_editor.putString("login_user", user_jsondata));
/////////////////////////////


                                    Toast.makeText(login.this, "로그인 되었습니다", Toast.LENGTH_SHORT).show();

                                    finish();

                                }else{   // 사용자가 입력한 이메일과 비밀번호가 맞지 않는다면 토스트 메시지를 띄우세요
                                    Toast.makeText(login.this, "로그인 정보가 올바르지 않습니다", Toast.LENGTH_SHORT).show();
                                }

                            }



                        } catch (JSONException e) {
                            // editText 로 입력한 이메일이 null 값이 아니면 = 무엇인가를 입력하면 -> 일단 쉐어드에 사용자가 입력한 이메일이 있는지 확인했는데 없음
                            e.printStackTrace();
//                            Toast.makeText(login.this, "아이디 정보가 없습니다", Toast.LENGTH_SHORT).show();
                            Toast.makeText(login.this, "로그인 정보를 찾을 수 없습니다", Toast.LENGTH_SHORT).show();
                        }


                    }else { //사용자가 입력한 이메일이 회원 목록에 없을 때
                        Toast.makeText(login.this, "이메일 정보가 없습니다", Toast.LENGTH_SHORT).show();
                    }

                }else{  // 사용자가 아무것도 입력하지 않았을 때
                    Toast.makeText(login.this, "로그인 정보를 입력해주세요", Toast.LENGTH_SHORT).show();
                }




// 나중에
// 로그인 정보 기억하기 체크박스가 체크되었을때
//
                if (checkBox_remember_login.isChecked()) {
//save the checkbox preference->로그인정보기억하기체크박스설정값저장.
                    editor.putString(getString(R.string.checkbox), "True");//(key,value)
                    editor.commit();//제출
                    Log.e("onCreate로그인정보기억하기체크박스", "onCreate");

//save the email
                    String email = editText_email.getText().toString();
                    editor.putString(getString(R.string.email), email);
                    editor.commit();
                    Log.e("onCreate로그인정보기억하기체크박스", "이메일정보:" + email);

//save the password
//                    String password = editText_password.getText().toString();
//                    editor.putString(getString(R.string.password), password);
//                    editor.commit();
//                    Log.e("onCreate로그인정보기억하기체크박스", "비밀번호정보:" + password);

                } else { //  체크되지않았을때
//save the checkbox preference
                    editor.putString(getString(R.string.checkbox), "False");//(key,value)
                    editor.commit();//제출

//save the email
                    editor.putString(getString(R.string.email), "");
                    editor.commit();

//save the password
//                    editor.putString(getString(R.string.password), "");
//                    editor.commit();

                }


            }
        });


//
//                // 회원가입에서 보낸 데이터 받기  // sharedpreference 로그인 정보 기억하기와 중첩하면 적용 안됨  //
        // 로그인 기능 완성하면 주석 해제하기

//                editText_email = (EditText) findViewById(R.id.editText_email);
////        editText_password = (EditText) findViewById(R.id.editText_password);
//        button_sign_in = (Button) findViewById(R.id.button_sign_in);
//        button_sign_in.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(getApplicationContext(), sign_up.class);
//
//                // SINGLE_TOP : 이미 만들어진게 있으면 그걸 쓰고, 없으면 만들어서 써라
//                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
//
//                // 동시에 사용 가
//                // intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
//
//                // intent를 보내면서 다음 액티비티로부터 데이터를 받기 위해 식별번호(1000)을 준다.
//                startActivityForResult(intent, 1000);
//            }
//        });

// 회원가입 버튼 -> 회원가입 화면으로 이동
        button_signUp =
                findViewById(R.id.button_signUp);
        button_signUp.setOnClickListener(new ImageView.OnClickListener() {
            @Override
            public void onClick(View view) {
                //액티비티 이동, 여기서 1000은 식별자. 아무 숫자나 넣으주면 됨.
                Intent intent = new Intent(getApplicationContext(), sign_up.class);
                startActivityForResult(intent, 1000);


            }
        });


    }// onCreate 닫는 중괄호

    // ArrayList 에 기록된 값을 JSONArray 배열에 담아 문자열로 저장
// 현재 로그인 하고 있는 사용자의 정보만 담는 sharedPreference
    public void user_writeArrayList(String user_jsondata) {

        if (userData != null) {



            // JSONArray 생성
            logined_user = getSharedPreferences("logined_user", Context.MODE_PRIVATE);
            user_editor = logined_user.edit();

            user_editor.putString("login_user", user_jsondata);  // 회원가입시 입력한 email이 각 arrayList의 key 값이 됨.

            Log.e("saveArrayList 메소드","확인중 login_user 이라는 키에 저장 중 -> 정보가 쌓어야 함 : " + user_editor.putString("login_user", user_jsondata));
            user_editor.commit();
            Log.e("saveArrayList 메소드","ArrayList인 user_jsondata를 String 형태로 logined_user에 저장했습니다 : " +   logined_user);


        }

    }

    /**
     * SharedPreferece이용한로그인정보기억하기->CheckBox
     * Checkthesharedpreferencesandsetthemaccordingly
     **/

    private void checkSharedPreferences() {//->onCreate에서이메소드호출에서사용함.
        String checkbox = sharedPreferences.getString(getString(R.string.checkbox), "False");//->여기들어가는R.string.checkbox는values.string.xml에등록->등록안하면인식을못해서빨간줄이뜸
        String email = sharedPreferences.getString(getString(R.string.email), "");//s1을비워두는이유:User가아무것도쓰지않았을때비워두기위해서
        String password = sharedPreferences.getString(getString(R.string.password), "");

        editText_email.setText(email);
        editText_password.setText(password);

//로그인정보기억하기체크박스가체크되었을때true안되어있을때는false
        if (checkbox.equals("True")) {
            checkBox_remember_login.setChecked(true);
            Log.e("checkSharedPreference메소드", "이메일정보:" + editText_email + email);
        } else {
            checkBox_remember_login.setChecked(false);
            Log.e("checkSharedPreference메소드", "비밀번호정보:" + checkBox_remember_login + password);
        }

    }


//    // 회원가입 후 회원가입한 정보에서 Result로 email과 password를 가지고 오는 메소드
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//        // setResult를 통해 받아온 요청번호, 상태, 데이터
//        Log.d("RESULT", requestCode + "");
//        Log.d("RESULT", resultCode + "");
//        Log.d("RESULT", data + "");
//
//        if (requestCode == 1000 && resultCode == RESULT_OK) {
//            Toast.makeText(login.this, "회원가입을 완료했습니다!", Toast.LENGTH_SHORT).show();
//            editText_email.setText(data.getStringExtra("EMAIL"));
//            editText_password.setText(data.getStringExtra("PASSWORD"));
//
//        }
//    }


    @Override
    protected void onRestart() {
        super.onRestart();
        Log.e("login", "onRestart");
        //액티비티가 중단되었다가 다시 시작
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.e("login", "onStart");
        //액티비티가 화면에 나타나기 시작
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e("login", "onResume");
        //액티비티가 화면에 나타나고 상호작용이 가능해짐
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.e("login", "onPause");
        //다른 액티비티가 시작되려함, 이 액티비티는 중단되려하고 백그라운드로 들어갑니다.
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.e("login", "onStop");
        //액티비티가 더 이상 화면에 나타나지 않음,중단된 상태

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e("login", "onDestroy");
        //액티비티가 종료되려고 합니다.

    }
}
