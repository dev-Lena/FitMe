package com.example.fitme;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class sign_up extends AppCompatActivity {

    private ArrayList<List> data = new ArrayList<List>();

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    Button button_sign_up_complete, button_sign_in;  //회원가입하기 버튼
    EditText editText_mysize, editText_nickname, editText_email, editText_password, editText_password_confirm;  // 평소 사이즈 입력하는 칸, 닉네임 적는 칸
    BottomNavigationView bottomNavigationView;   // 바텀 네이게이션 메뉴  -> 하단바

    String email ="";
    String password ="";
    Boolean womanIsTrue ;
    String currentSize = "";
    String nickname = "";
    int profile_img = R.drawable.img_dd_profile ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.e("sign_up","onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        final EditText editText_email = (EditText) findViewById(R.id.editText_email);
        final EditText editText_password = (EditText)findViewById(R.id.editText_password);
        final EditText editText_mysize= (EditText) findViewById(R.id.editText_mysize);
        final EditText editText_nickname = (EditText)findViewById(R.id.editText_nickname);
//              성별 boolean값 받아오기
        ImageView imageView_user_profile_image = (ImageView) findViewById(R.id.imageView_user_profile_image);




// 회원가입 완료 버튼 -> 로그인 화면으로 이동

        button_sign_up_complete = findViewById(R.id.button_sign_up_complete);
        button_sign_up_complete.setOnClickListener(new ImageView.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 회원가입 완료 버튼을 누르면 인텐트로 화면전환 하고
                Intent intent = new Intent(sign_up.this, login.class);
                startActivity(intent); //액티비티 이동, 여기서 1000은 식별자. 아무 숫자나 넣으주면 됨.

                // 입력한 정보를 sharedPreference에 저장
                JSONObject jsonObject = new JSONObject();  // JSONObject 객체 선언
                JSONArray jsonArray = new JSONArray();       // JSONArray 객체 선언

                try{
                    jsonObject.put("email", editText_email .getText().toString());
                    jsonObject.put("password", editText_password.getText().toString());
                    jsonObject.put("currentSize", editText_mysize.getText().toString());
                    jsonObject.put("nickname", editText_nickname.getText().toString());
                    jsonObject.put("profile_img",profile_img );

                    jsonArray.put(jsonObject);  // jsonArray에 위에서 저장한 jsonObject를 put

                } catch (JSONException e){  // 예외 처리
                    e.printStackTrace();
                }

                String jsondata = jsonArray.toString();  // jsonArray를 String값으로 바꿈. String으로 바꾼 jsonArray를 jsondata라고 이름붙임.
                saveArrayList(jsondata);                    // saveArrayList 메소드를 실행할건데 josndata를 사용할 것 -> onCreate 밖에 메소드 만듦.



            }
        });
//



// 회원가입한 정보 로그인 정보로 넘겨주기

//        editText_email = (EditText) findViewById(R.id.editText_email);
//        editText_password = (EditText) findViewById(R.id.editText_password);
        editText_password_confirm = (EditText) findViewById(R.id.editText_password_confirm);
        button_sign_up_complete = (Button) findViewById(R.id.button_sign_up_complete);


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
                    editText_password.setBackgroundColor(Color.WHITE);
                    editText_password_confirm.setBackgroundColor(Color.WHITE);
                } else {
                    editText_password.setBackgroundColor(Color.LTGRAY);
                    editText_password_confirm.setBackgroundColor(Color.LTGRAY);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }


        });


        button_sign_up_complete.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
// 이메일 입력 확인
                if( editText_email.getText().toString().length() == 0 ) {
                    Toast.makeText(sign_up.this, "Email을 입력하세요!", Toast.LENGTH_SHORT).show();
                    editText_email.requestFocus();
                    return;
                }

                // 비밀번호 입력 확인
                if( editText_password.getText().toString().length() == 0 ) {
                    Toast.makeText(sign_up.this, "비밀번호를 입력하세요!", Toast.LENGTH_SHORT).show();
                    editText_password.requestFocus();
                    return;
                }

                // 비밀번호 확인 입력 확인
                if( editText_password_confirm.getText().toString().length() == 0 ) {
                    Toast.makeText(sign_up.this, "비밀번호 확인을 입력하세요!", Toast.LENGTH_SHORT).show();
                    editText_password_confirm.requestFocus();
                    return;
                }

                // 비밀번호 일치 확인
                if( !editText_password.getText().toString().equals(editText_password_confirm.getText().toString()) ) {
                    Toast.makeText(sign_up.this, "비밀번호가 일치하지 않습니다!", Toast.LENGTH_SHORT).show();
                    editText_password.setText("");
                    editText_password_confirm.setText("");
                    editText_password.requestFocus();
                    return;
                }
// 입력한 이메일 데이터 넘겨주기
                Intent result = new Intent();
                result.putExtra("EMAIL", editText_email.getText().toString());

                // 자신을 호출한 Activity로 데이터를 보낸다.
                setResult(RESULT_OK, result);
                finish();
            }
        });

    }// onCreate 닫는 중괄호

    private void saveArrayList(String jsondata) {

        //SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        // 윗줄 아랫줄 바꿔도 되는지 확실히 모름. 확인해보길. 윗줄 쓸꺼면 onCreate 위에 sharedPreference 객체 선언 주석처리 해야함.
        sharedPreferences = getSharedPreferences("sign_up", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString("jsonData", jsondata);
        editor.apply();

    }


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