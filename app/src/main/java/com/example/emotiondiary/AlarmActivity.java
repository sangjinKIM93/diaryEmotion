package com.example.emotiondiary;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.provider.AlarmClock;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class AlarmActivity extends AppCompatActivity {

    int h=0, mi=0;
    TextView textView;
    static final String KEY_DATA = "KEY_DATA";
    String alarmBackup;
    EditText inputH, inputMi;
    int inputMinInt = 0, inputHourInt = 0;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alarm_set);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR);   //화면 가로,세로 전환 활성화. 역은 portrait. 아마 savedinstance 때문에 그랬을듯.

        inputH = (EditText)findViewById(R.id.inputHour);
        inputMi = (EditText)findViewById(R.id.inputMiniute);
        textView = (TextView) findViewById(R.id.test);
        //알람 설정 상태 표시
        if(savedInstanceState == null) {
            textView.setText("알람을 생성해주세요.");
        }else{
            textView.setText(savedInstanceState.getString(KEY_DATA));
        }

        Button button = findViewById(R.id.setTime);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTime();
            }
        });

    }


    private void showTime() {
        TimePickerDialog timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                h = hourOfDay;
                mi = minute;
            }
        }, 13, 15, true);
        timePickerDialog.setMessage("메세지");
        timePickerDialog.show();
    }



    public void alarmMakerClicked(View view) {

        //editText에 적힌 값 받아와서 int값으로 전환해주기.
        String inputHourStr = inputH.getText().toString();
        String inputMinuteStr = inputMi.getText().toString();

        inputHourInt = Integer.parseInt(inputHourStr);
        inputMinInt = Integer.parseInt(inputMinuteStr);


        textView.setText("알람 시간 : " + inputHourInt + "시 " + inputMinInt+ "분");
        //알람 설정 해주는 암시적 인텐트
        Intent intent = new Intent(AlarmClock.ACTION_SET_ALARM)
                .putExtra(AlarmClock.EXTRA_HOUR, inputHourInt)
                .putExtra(AlarmClock.EXTRA_MINUTES, inputMinInt)
                .putExtra(AlarmClock.EXTRA_RINGTONE, R.raw.overcome); //내 생각에는 핸드폰 기기 안에 있는 음악 파일을 이용해야해.
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }

    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override
    protected void onPause() {
        super.onPause();
        Toast.makeText(this, "Alarm_onPause 호출 됨",Toast.LENGTH_LONG).show();
        //어떤 데이터를 저장하고 싶은데? textview의 text 데이터


    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {

        textView.setText("알람 시간 : " + inputHourInt + "시 " + inputMinInt+ "분");
        alarmBackup = textView.getText().toString();

        outState.putString(KEY_DATA, alarmBackup);

        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onStop() {
        super.onStop();

        Toast.makeText(this, "Alarm_onStop 호출 됨",Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Toast.makeText(this, "Alarm_onDestroy 호출 됨", Toast.LENGTH_LONG).show();
    }
}
