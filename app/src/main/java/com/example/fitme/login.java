package com.example.fitme;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class login extends AppCompatActivity {

    // login java 클래스에서 필요한 객체 선언

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;


    CheckBox checkBox_remember_login;
    String JOINEMAIL;
    String JOINPW;
    EditText editText_email, editText_password; // -> null값이면 토스트 값 뜨도록 // -> null값이면 토스트 값 뜨도록
    Button button_sign_in, button_signUp, button_sign_up_complete; // -> 로그인 버튼 -> 피드로 화면 연결  // -> 회원가입 버튼 -> 회원가입 화면으로 연결

//    public login(SharedPreferences sharedPreferences, SharedPreferences.Editor editor) {
//        this.sharedPreferences = sharedPreferences;
//        this.editor = editor;
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.e("login", "onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

// SharedPreference 로 회원정보를 jsonObject에 저장해서 jsonArrayList 형태로 SharedPreference에 저장\


//
//  SharedPreference로 로그인 정보 기억하기

        // 필요한 뷰객체 매칭
        editText_email = (EditText) findViewById(R.id.editText_email);
        editText_password = (EditText) findViewById(R.id.editText_password);
        checkBox_remember_login = (CheckBox) findViewById(R.id.checkBox_remember_login);
        button_sign_in = (Button) findViewById(R.id.button_sign_in);
        //SharedPreference와 editor 가져오기
//        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this); // 아래 코드와 같은 의미인데 this는 액티비티 정도
        sharedPreferences = getSharedPreferences("remember_login", Context.MODE_PRIVATE);  // declare the database = 데이터 베이스 선언  // 액티비티 안이 아닐 떄는 context로 접근해야함 (안일 때는 this)
        editor = sharedPreferences.edit();  // 위에서 선언한 데이터 베이스에 아이템을 put 할 수 있는


        checkSharedPreferences();  // 로그인정보 기억하기 체크 박스가 눌려있는지 설정값. 메소드 (onCreate 밖에 있음)

        button_sign_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { // 체크박스가 체크 되었을 때 or 체크 되지 않았을 때

                if(checkBox_remember_login.isChecked()){
                    //save the checkbox preference -> 로그인 정보 기억하기 체크박스 설정값 저장.
                    editor.putString(getString(R.string.checkbox),"True"); // (key, value)
                    editor.commit(); // 제출
                    Log.e("onCreate 로그인 정보 기억하기 체크박스", "onCreate");

                    //save the email
                    String email = editText_email.getText().toString();
                    editor.putString(getString(R.string.email),email);
                    editor.commit();
                    Log.e("onCreate 로그인 정보 기억하기 체크박스", "이메일 정보: " + email);

                    //save the password
                    String password = editText_password.getText().toString();
                    editor.putString(getString(R.string.password),password);
                    editor.commit();
                    Log.e("onCreate 로그인 정보 기억하기 체크박스", "비밀번호 정보: " + password);

                }else {
                    //save the checkbox preference
                    editor.putString(getString(R.string.checkbox),"False"); // (key, value)
                    editor.commit(); // 제출

                    //save the email
                    editor.putString(getString(R.string.email),"");
                    editor.commit();

                    //save the password
                    editor.putString(getString(R.string.password),"");
                    editor.commit();

                }

            }
        });


//        String email = sharedPreferences.getString("login_id","default"); // ("some_other_key","dafault")일 경우 첫번째 인자의 key값을 찾을 수 없을 때 "default"가 찍힘
//        Log.e("login 클래스 onCreate에서","SharedPreference에 login_id 저장 : " + email );

                //checkBox에 표시했을 때 ->onCreate 밖에
//
//                // 회원가입에서 보낸 데이터 받기  // sharedpreference 로그인 정보 기억하기와 중첩하면 적용 안됨
//
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
//                // 동시에 사용 가능
//                // intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
//
//                // intent를 보내면서 다음 액티비티로부터 데이터를 받기 위해 식별번호(1000)을 준다.
//                startActivityForResult(intent, 1000);
//            }
//        });

//
// 로그인 버튼 -> 피드 화면으로 이동

                button_sign_in = findViewById(R.id.button_sign_in);
        button_sign_in.setOnClickListener(new ImageView.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(login.this, feed.class);
                startActivity(intent); //액티비티 이동

            }
        });

// 회원가입 버튼 -> 회원가입 화면으로 이동
        button_signUp = findViewById(R.id.button_signUp);
        button_signUp.setOnClickListener(new ImageView.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intent = new Intent(login.this, sign_up.class);
////                startActivity(intent); //액티비티 이동, 여기서 1000은 식별자. 아무 숫자나 넣으주면 됨.
                Intent intent = new Intent(getApplicationContext(), sign_up.class);
                startActivityForResult(intent, 1000);

            }
        });


    }// onCreate 닫는 중괄호


    //EditText에서 입력한 값을 SharedPreferences에 저장
    private void saveArrayList(String jsondata){
//         SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        // 윗줄 아랫줄 바꿔도 되는지 확실히 모름. 확인해보길. 윗줄 쓸꺼면 onCreate 위에 sharedPreference 객체 선언 주석처리 해야함.
        sharedPreferences = getSharedPreferences("sign_up", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString("jsonData", jsondata);
        editor.apply();
    }


    /**
     * SharedPreferece이용한 로그인정보 기억하기 -> CheckBox
     * Check the shared preferences and set them accordingly
     **/

    private void checkSharedPreferences() {  // ->onCreate에서 이 메소드 호출에서 사용함.
        String checkbox = sharedPreferences.getString(getString(R.string.checkbox), "False");  // -> 여기 들어가는 R.string.checkbox는 values.string.xml에 등록 -> 등록안하면 인식을 못해서 빨간줄이 뜸
        String email = sharedPreferences.getString(getString(R.string.email), "");  //s1을 비워두는 이유 : User가 아무것도 쓰지 않았을 때 비워두기 위해서
        String password = sharedPreferences.getString(getString(R.string.password), "");

        editText_email.setText(email);
        editText_password.setText(password);

        // 로그인 정보 기억하기 체크 박스가 체크 되었을 때 true 안되어있을 때는 false
        if (checkbox.equals("True")) {
            checkBox_remember_login.setChecked(true);
            Log.e("checkSharedPreference 메소드", "이메일 정보: " + editText_email + email);
        } else {
            checkBox_remember_login.setChecked(false);
            Log.e("checkSharedPreference 메소드", "비밀번호 정보: " + checkBox_remember_login + password);
        }

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // setResult를 통해 받아온 요청번호, 상태, 데이터
        Log.d("RESULT", requestCode + "");
        Log.d("RESULT", resultCode + "");
        Log.d("RESULT", data + "");

        if (requestCode == 1000 && resultCode == RESULT_OK) {
            Toast.makeText(login.this, "회원가입을 완료했습니다!", Toast.LENGTH_SHORT).show();
            editText_email.setText(data.getStringExtra("EMAIL"));
        }
    }


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
