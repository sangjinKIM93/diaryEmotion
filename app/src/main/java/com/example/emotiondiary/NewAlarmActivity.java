package com.example.emotiondiary;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

public class NewAlarmActivity extends AppCompatActivity {

    TextView alarmTime;
    String timeStr;

    Handler handler = new Handler();
    AlarmManager alarmManager;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alarm_memo);
        alarmTime = findViewById(R.id.alarmTime);
        final TimePicker picker = (TimePicker)findViewById(R.id.timePicker);
        picker.setIs24HourView(true); //24시간 모드 쓸건지 12am/pm쓸건지

        //앞서 설정한 시간값 가져오기(없으면 현재시간)
        SharedPreferences sharedPreferences = getSharedPreferences("daily alarm", MODE_PRIVATE);
        long millis = sharedPreferences.getLong("nextNotifyTime", Calendar.getInstance().getTimeInMillis());
        boolean alarmSetted = true;
        if(millis == Calendar.getInstance().getTimeInMillis()){
            alarmSetted = false;
        }
        //캘린더를 받아와서 받아온 시간으로 캘린더 시간 설정. -> 이 calendar 시간을 바탕으로 timepicker 시간을 설정해줌. 근데 왜 이렇게 복잡한겨?
        Calendar nextNotifyTime = new GregorianCalendar();
        nextNotifyTime.setTimeInMillis(millis);
        //이전 설정값으로 TimePicker 초기화
        Date currentTime = nextNotifyTime.getTime();
        SimpleDateFormat HourFormat = new SimpleDateFormat("kk", Locale.getDefault());
        SimpleDateFormat MinuteFormat = new SimpleDateFormat("mm", Locale.getDefault());
        int pre_hour = Integer.parseInt(HourFormat.format(currentTime));
        int pre_minute = Integer.parseInt(MinuteFormat.format(currentTime));
        //설정시간 UI에 보여주기 . 이전에 설정되어있지 않다면 timepicker는 00:00로 되어야하겠네?
        if(alarmSetted == true) {
            timeStr = "알람 시간 : " + String.valueOf(pre_hour) + " 시" + String.valueOf(pre_minute) + " 분";
            alarmTime.setText(timeStr);
        }

        Button button = (Button)findViewById(R.id.setAlarmBtn);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int hour, hour_24, minute;
                String am_pm;

                if(Build.VERSION.SDK_INT>=23){
                   hour_24 = picker.getHour();
                   minute = picker.getMinute();
                } else {
                    hour_24 = picker.getCurrentHour();
                    minute = picker.getCurrentMinute();
                }

                if(hour_24>12){
                    am_pm = "PM";
                    hour = hour_24 - 12;
                } else {
                    hour = hour_24;
                    am_pm = "AM";
                }

                //현재 지정된 시간으로 알람 시간 설정
                Calendar calendar = Calendar.getInstance();
                calendar.setTimeInMillis(System.currentTimeMillis());
                calendar.set(Calendar.HOUR_OF_DAY, hour_24);
                calendar.set(Calendar.MINUTE, minute);
                calendar.set(Calendar.SECOND, 0);

                //이미 지난 시간을 지정했다면 다음날 같은 시간으로 설정
                if(calendar.before(Calendar.getInstance())){
                    calendar.add(Calendar.DATE,1);
                }

                Date currentDateTime = calendar.getTime();
                String date_text = new SimpleDateFormat("yyyy년 MM월 dd일 EE요일 a hh시 mm분", Locale.getDefault()).format(currentDateTime);
                Toast.makeText(NewAlarmActivity.this, date_text + "으로 알람이 설정되었습니다.", Toast.LENGTH_SHORT).show();

                //shared에 설정한 값 저장(왜? 그래야 초기 셋팅하지)
                SharedPreferences.Editor editor = getSharedPreferences("daily alarm", MODE_PRIVATE).edit();
                editor.putLong("nextNotifyTime", (long)calendar.getTimeInMillis());
                editor.apply();

                diaryNotification(calendar);

                timeStr = "알람 시간 : " + String.valueOf(hour_24) + " 시 " + String.valueOf(minute) + " 분";
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                alarmTime.setText(timeStr);
                            }
                        });
                    }
                }).start();

            }
        });

        Button cancelAlarm = findViewById(R.id.cancelAlarm);
        cancelAlarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new Thread(new Runnable() {         //새로운 흐름을 만든다.
                    @Override
                    public void run() {
                        handler.post(new Runnable() {   //스레드간 통신을 한다.(안드로이드에서 스레드 실행을 위해서 필요한 부분.)
                            @Override
                            public void run() {
                                alarmTime.setText("알람을 설정해주세요.");
                            }
                        });
                    }
                }).start();

                alarmCancel();
            }
        });
    }

    void diaryNotification(Calendar calendar) {
        Boolean dailyNotify = true;

        PackageManager pm = this.getPackageManager();  //애는 뭘까? 현재 패키지와 관련된 다양한 종류의 정보를 검색하는 클래스
        ComponentName receiver = new ComponentName(this, DeviceBootReceiver.class); //얘는 물까?
        Intent alarmIntent = new Intent(this, AlarmReceiver.class);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0 , alarmIntent, 0);
        alarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);

        if(dailyNotify){

            if(alarmManager!= null){
                //이걸로 끝난거 아니야? 만약 핸드폰을 껏다가 켰다고 가정해봐. 그러면 알람 매니저가 초기화 됨.
                alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);

                if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){
                    alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
                }
            }

            //부팅 후 실행되는 리시버 사용가능하게 설정(여기서 PackageManager가 사용됨) _  DeviceBootReceiver를 해주지? 재부팅시 동작할 수 있도록 설정해주는 듯
            pm.setComponentEnabledSetting(receiver,
                    PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
                    PackageManager.DONT_KILL_APP);

        }

    }

    void alarmCancel(){
        alarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, AlarmReceiver.class);
        PendingIntent sender = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        if(sender!=null){
            alarmManager.cancel(sender);
            sender.cancel();
        }
    }


}
