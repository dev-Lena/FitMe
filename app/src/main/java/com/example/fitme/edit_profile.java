package com.example.fitme;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class edit_profile extends AppCompatActivity {


    Button button_edit_complete;  //회원가입하기 버튼
    EditText editText_mysize, editText_nickname, editText_email, editText_password, editText_password_confirm;  // 평소 사이즈 입력하는 칸, 닉네임 적는 칸
    BottomNavigationView bottomNavigationView;   // 바텀 네이게이션 메뉴  -> 하단바

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.e("edit_profile","onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);


// 프로필 수정 완료 버튼 -> 내 옷장 화면으로 이동
        button_edit_complete = findViewById(R.id.button_edit_complete);
        button_edit_complete.setOnClickListener(new ImageView.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(edit_profile.this, mypage.class);
                startActivity(intent); //액티비티 이동, 여기서 1000은 식별자. 아무 숫자나 넣으주면 됨.

            }
        });

// 회원가입한 정보 로그인 정보로 넘겨주기

        editText_email = (EditText) findViewById(R.id.editText_email);
        editText_password = (EditText) findViewById(R.id.editText_password);
        editText_password_confirm = (EditText) findViewById(R.id.editText_password_confirm);
        button_edit_complete = (Button) findViewById(R.id.button_edit_complete);


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
                    editText_password.setBackgroundColor(Color.GREEN);
                    editText_password_confirm.setBackgroundColor(Color.GREEN);
                } else {
                    editText_password.setBackgroundColor(Color.RED);
                    editText_password_confirm.setBackgroundColor(Color.RED);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }


        });


        button_edit_complete.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                // 비밀번호 입력 확인
                if( editText_password.getText().toString().length() == 0 ) {
                    Toast.makeText(edit_profile.this, "비밀번호를 입력하세요!", Toast.LENGTH_SHORT).show();
                    editText_password.requestFocus();
                    return;
                }

                // 비밀번호 확인 입력 확인
                if( editText_password_confirm.getText().toString().length() == 0 ) {
                    Toast.makeText(edit_profile.this, "비밀번호 확인을 입력하세요!", Toast.LENGTH_SHORT).show();
                    editText_password_confirm.requestFocus();
                    return;
                }

                // 비밀번호 일치 확인
                if( !editText_password.getText().toString().equals(editText_password_confirm.getText().toString()) ) {
                    Toast.makeText(edit_profile.this, "비밀번호가 일치하지 않습니다!", Toast.LENGTH_SHORT).show();
                    editText_password.setText("");
                    editText_password_confirm.setText("");
                    editText_password.requestFocus();
                    return;
                }

                Intent result = new Intent();
                result.putExtra("EMAIL", editText_email.getText().toString());

                // 자신을 호출한 Activity로 데이터를 보낸다.
                setResult(RESULT_OK, result);
                finish();
            }
        });

    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.e("edit_profile","onRestart");
        //액티비티가 중단되었다가 다시 시작
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.e("edit_profile","onStart");
        //액티비티가 화면에 나타나기 시작
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e("edit_profile","onResume");
        //액티비티가 화면에 나타나고 상호작용이 가능해짐
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.e("edit_profile","onPause");
        //다른 액티비티가 시작되려함, 이 액티비티는 중단되려하고 백그라운드로 들어갑니다.
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.e("edit_profile","onStop");
        //액티비티가 더 이상 화면에 나타나지 않음,중단된 상태
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e("edit_profile","onDestroy");
        //액티비티가 종료되려고 합니다.
    }

}