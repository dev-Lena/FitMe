package com.example.fitme;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class login extends AppCompatActivity {

    String JOINEMAIL;
    String JOINPW;
    EditText editText_email, editText_password; // -> null값이면 토스트 값 뜨도록 // -> null값이면 토스트 값 뜨도록
    Button button_sign_in, button_signUp,button_sign_up_complete   ; // -> 로그인 버튼 -> 피드로 화면 연결  // -> 회원가입 버튼 -> 회원가입 화면으로 연결

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.e("login", "onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        // 회원가입에서 보낸 데이터 받기

        editText_email = (EditText) findViewById(R.id.editText_email);
//        editText_password = (EditText) findViewById(R.id.editText_password);
        button_sign_in = (Button) findViewById(R.id.button_sign_in);
        button_sign_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), sign_up.class);

                // SINGLE_TOP : 이미 만들어진게 있으면 그걸 쓰고, 없으면 만들어서 써라
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);

                // 동시에 사용 가능
                // intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);

                // intent를 보내면서 다음 액티비티로부터 데이터를 받기 위해 식별번호(1000)을 준다.
                startActivityForResult(intent, 1000);
            }
        });


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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // setResult를 통해 받아온 요청번호, 상태, 데이터
        Log.d("RESULT", requestCode + "");
        Log.d("RESULT", resultCode + "");
        Log.d("RESULT", data + "");

        if(requestCode == 1000 && resultCode == RESULT_OK) {
            Toast.makeText(login.this, "회원가입을 완료했습니다!", Toast.LENGTH_SHORT).show();
            editText_email.setText(data.getStringExtra("EMAIL"));
        }
    }


    @Override
    protected void onRestart() {
        super.onRestart();
        Log.e("login","onRestart");
        //액티비티가 중단되었다가 다시 시작
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.e("login","onStart");
        //액티비티가 화면에 나타나기 시작
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e("login","onResume");
        //액티비티가 화면에 나타나고 상호작용이 가능해짐
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.e("login","onPause");
        //다른 액티비티가 시작되려함, 이 액티비티는 중단되려하고 백그라운드로 들어갑니다.
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.e("login","onStop");
        //액티비티가 더 이상 화면에 나타나지 않음,중단된 상태
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e("login","onDestroy");
        //액티비티가 종료되려고 합니다.
    }
}
