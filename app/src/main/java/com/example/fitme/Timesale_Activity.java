package com.example.fitme;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import static java.lang.String.format;


public class Timesale_Activity extends AppCompatActivity {


    // 알람 시간
    private static Calendar calendar;
    private static TimePicker timePicker;

    //타이머에 필요한 핸들러
    ImageButton imageButton_back;
    Button button_reset, button_Alarm;
//    private final long START_TIME_IN_MILLIS = 60000;  // 이게 지금 타이머에 세팅되어있는 시간이니까 START_TIME_IN_MILLIS를 사용자가 설정한 알람 시간으로 바꿔야 함 // 원래 60000이었던 것 같음
    private static Handler mHandler;
    private CountDownTimer mCountDownTimer;
    private boolean mTimerRunning;
    //    private long mTimeLeftInMillis = START_TIME_IN_MILLIS;  // 이게 지금 타이머에 세팅되어있는 시간이니까 START_TIME_IN_MILLIS를 사용자가 설정한 알람 시간으로 바꿔야 함
    private long mTimeLeftInMillis;
    // 뷰 객체
    TextView textView_leftTime;
    EditText editText_alarm_name; // 사용자가 작성하는 알람 이름

    public static String alarm_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timesale);



        // 뒤로 가기 버튼 눌렀을 때 피드(메인 화면)로 이동

        imageButton_back = findViewById(R.id.imageButton_back);
        imageButton_back.setOnClickListener(new ImageView.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent register_intent = new Intent(Timesale_Activity.this, Feed_Main_Activity.class);
                startActivity(register_intent); //액티비티 이동
                finish(); // 액티비티 finish 시킴

            }
        });



        this.calendar = Calendar.getInstance();
        // 현재 날짜 표시
        displayDate();

        this.timePicker = findViewById(R.id.timePicker);

        mHandler = new Handler(); // 알람 남은 시간 Handler

        // 날짜와 시간을 받은 알람
        findViewById(R.id.button_Calendar).setOnClickListener(mClickListener);
        findViewById(R.id.button_Alarm).setOnClickListener(mClickListener);

        // 타이머 핸들러

        textView_leftTime = (TextView) findViewById(R.id.textView_leftTime);
        button_reset = (Button) findViewById(R.id.button_reset);


        // 타이머 초기화 버튼
        button_reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (mHandler != null){
                    mHandler.removeMessages(0);
                }

                resetTimer();

                finish();
                overridePendingTransition(0,0);
                startActivity(getIntent());
                overridePendingTransition(0,0);

            }
        });

        updateCountDownText();




    }/// onCreate 닫는 중괄호

    // 타이머 시작
    private void startTimer() {
        mCountDownTimer = new CountDownTimer(mTimeLeftInMillis, 1000) {
            @Override
            public void onTick(long l) {
                mTimeLeftInMillis = l;
                updateCountDownText();
                Log.e("타이머startTimer ", "mTimeLeftInMillis & hours : " + mTimeLeftInMillis);



            }

            @Override
            public void onFinish() {
                mTimerRunning = false;
                button_reset.setVisibility(View.VISIBLE);
            }
        }.start();
        mTimerRunning = true;
        button_reset.setVisibility(View.VISIBLE);

    }

    // 타이머 텍스트 업데이트
    private void updateCountDownText() {

        int hours = (int) (mTimeLeftInMillis / 1000) / 3600;
        Log.e("타이머updateCountDownText ", "mTimeLeftInMillis & hours : " + mTimeLeftInMillis + "&" + hours);
        int minutes = (int) ((mTimeLeftInMillis / 1000) % 3600) / 60;
        Log.e("타이머updateCountDownText ", "mTimeLeftInMillis & minutes : " + mTimeLeftInMillis + "&" + minutes);
        int seconds = (int) (mTimeLeftInMillis / 1000) % 60;
        Log.e("타이머updateCountDownText ", "mTimeLeftInMillis & seconds : " + mTimeLeftInMillis + "&" + seconds);

        String timeLeftFormatted;
        if (hours > 0) {
            timeLeftFormatted = format(Locale.getDefault(),
                    "%d:%02d:%02d", hours, minutes, seconds);
            Log.e("타이머updateCountDownText ", "timeLeftFormatted  : " + timeLeftFormatted);
        } else {
            timeLeftFormatted = format(Locale.getDefault(),
                    "%02d:%02d", minutes, seconds);
            Log.e("타이머updateCountDownText ", "timeLeftFormatted  : " + timeLeftFormatted);
        }

        textView_leftTime.setText(timeLeftFormatted);
        Log.e("타이머updateCountDownText ", "textView_leftTime  : " + textView_leftTime);



    }

    // 타이머 초기화
    private void resetTimer() {
//        mTimeLeftInMillis = START_TIME_IN_MILLIS;
//        mTimeLeftInMillis =0;
        textView_leftTime.setText("");
        mHandler.removeMessages(0);
        mTimeLeftInMillis = 0;
        updateCountDownText();
        button_reset.setVisibility(View.VISIBLE);
        mHandler.sendEmptyMessageDelayed(0, 1000);  // 이부분 있어야함


    }

    // 날짜 시간 알람
    // 날짜 표시
    private void displayDate() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        TextView textView_date = findViewById(R.id.textView_date);
        textView_date.setText(format.format(this.calendar.getTime())); // 사용자가 원하는 날짜를 받아서 보여지는 곳은 textView_date임.
    }

    // DatePickerDialog 호출
    private void showDatePicker() {
        DatePickerDialog dialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {  // 사용자가 설정한 날짜를 set 함
                // 알림 날짜 설정
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, month);
                calendar.set(Calendar.DATE, dayOfMonth);


                //날짜 표시
                displayDate();  // 앞서 사용자가 설정한 날짜


            }
        }, this.calendar.get(Calendar.YEAR), this.calendar.get(Calendar.MONTH), this.calendar.get(Calendar.DAY_OF_MONTH));

        dialog.show();
    }

    //알람 등록
    private void setAlarm() {
        // 알람 시간 설정
        // TimePicker에서 시간을 받아오는 곳
        this.calendar.set(Calendar.HOUR_OF_DAY, this.timePicker.getHour());  // 사용자가 입력한 타겟 시간
        this.calendar.set(Calendar.MINUTE, this.timePicker.getMinute());  // 사용자가 입력한 타겟 분
        this.calendar.set(Calendar.SECOND, 0);                           // 사용자가 입력한 타겟 초

        Log.e("여기!!!!!!!!!!!!!!!setAlarm ", " timePicker.getHour() : " + timePicker.getHour());
        Log.e("여기!!!!!!!!!!!!!!!setAlarm ", " timePicker.getMinute() : " + timePicker.getMinute());


        // 현재일보다 이전이면 등록 실패
        if (this.calendar.before(Calendar.getInstance())) {
            Toast.makeText(this, "알람 시간이 현재시간보다 이전일 수 없습니다", Toast.LENGTH_SHORT).show();
            return;
        }

        // 사용자가 입력한 날짜와 시간, 분 초에서 현재 시간을 빼서 핸들러로 계속 해서 보여주기


        // BroadCast Receiver 설정 -> Intent에 담아서 보냄
        Intent intent = new Intent(this, Alarm_Receiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        // 알람 설정
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, this.calendar.getTimeInMillis(), pendingIntent);
        Log.e("setAlarm ", "alarmManager  : " + alarmManager);
        Log.e("setAlarm ", "calendar.getTimeInMillis() : " + calendar.getTimeInMillis());
        Log.e("setAlarm ", "pendingIntent : " + pendingIntent);

        // 토스트 메시지 보여주기 (알람 시간 표시)
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        Toast.makeText(this, " 알람 : " + format.format(calendar.getTime()), Toast.LENGTH_SHORT).show();


        // 타이머 시간 set하기
        long setTime = calendar.getTimeInMillis();
        long now = System.currentTimeMillis();

        mTimeLeftInMillis = setTime - now;
        Log.e("타이머startTimer!!!!!!!!!!!!! ", "mTimeLeftInMillis = setTime - now;!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! : " + mTimeLeftInMillis);
//        mTimeLeftInMillis = timePicker.getHour() + timePicker.getMinute() ;

        mCountDownTimer = new CountDownTimer(mTimeLeftInMillis, 1000) {
            @Override
            public void onTick(long l) {
                mTimeLeftInMillis = l;
                updateCountDownText();
                Log.e("타이머startTimer ", "mTimeLeftInMillis & hours : " + mTimeLeftInMillis);


            }

            @Override
            public void onFinish() {
                mTimerRunning = false;
                button_reset.setVisibility(View.VISIBLE);
                mHandler.removeMessages(0);
            }
        }.start();
        mTimerRunning = true;
        button_reset.setVisibility(View.VISIBLE);

    }
    View.OnClickListener mClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.button_Calendar:
                    // 달력
                    showDatePicker();

                    break;
                case R.id.button_Alarm:
                    // 알람 등록
                    setAlarm();  // 설정한 날짜와 시간에 알람이 울리도록

                    startTimer();  // 남은 시간을 보여주는 타이머를 시작


                    editText_alarm_name = findViewById(R.id.editText_alarm_name);

//                    if (editText_alarm_name.length()<21){
                        Intent intent = new Intent(Timesale_Activity.this, Alarm_Activity.class);
                        alarm_name = editText_alarm_name.getText().toString();
                    intent.putExtra("알람이름", alarm_name);
                    Log.d("Timesale_Activity", "button_Alarm: alarm_name -> " + alarm_name);
                        // 사용자가 입력한 알람이름을 인텐트로 알람 울릴 때 뜨는 액티비티로 넘기는중
//                        startActivity(comment_intent); //액티비티 이동
//                    }
//                    else {
//                        Toast.makeText(Timesale_Activity.this, "21글자 이하로 입력해주세요", Toast.LENGTH_SHORT).show();
//                    }


                    break;
            }

        }
    };


    @Override
    protected void onRestart() {
        super.onRestart();
        Log.e("Timesale_Activity", "onRestart");
        //액티비티가 중단되었다가 다시 시작
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.e("Timesale_Activity", "onStart");
        //액티비티가 화면에 나타나기 시작
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e("Timesale_Activity", "onResume");
        //액티비티가 화면에 나타나고 상호작용이 가능해짐
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.e("Timesale_Activity", "onPause");
        //다른 액티비티가 시작되려함, 이 액티비티는 중단되려하고 백그라운드로 들어갑니다.
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.e("Timesale_Activity", "onStop");
        //액티비티가 더 이상 화면에 나타나지 않음,중단된 상태
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e("Timesale_Activity", "onDestroy");
        //액티비티가 종료되려고 합니다.
    }
}


