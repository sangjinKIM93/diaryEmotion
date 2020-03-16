package com.example.emotiondiary;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class AlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        //notification 기본 셋팅
        NotificationManager notificationManager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);

        Intent notificationIntent = new Intent(context, MemoWriteActivity.class);
        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent pendingI = PendingIntent.getActivity(context, 0, notificationIntent, 0);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "default");

        //채널 설정
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){

            builder.setSmallIcon(R.drawable.meditation_icon);

            String channelName = "매일 알람 채널";
            String description = "매일 정해진 시간에 알람";
            int importance = NotificationManager.IMPORTANCE_HIGH;

            NotificationChannel channel = new NotificationChannel("default", channelName, importance);
            channel.setDescription(description);

            if(notificationManager != null){
                notificationManager.createNotificationChannel(channel);
            }

        } else {
            builder.setSmallIcon(R.mipmap.ic_launcher);
        }

        builder.setAutoCancel(true)
                .setDefaults(NotificationCompat.DEFAULT_ALL)
                .setWhen(System.currentTimeMillis())
                .setTicker("{Time to watch some cool stuff}")
                .setContentTitle("지금 기분이 어떠신가요?")
                .setContentText("하루에 한번 당신의 감정을 기록해보세요.")
                .setContentInfo("INFO")
                .setContentIntent(pendingI);

        if(notificationManager != null){

            //notification 실행
            notificationManager.notify(1234, builder.build());

            //내일 같은 시간으로 알림시간 결정
            Calendar nextNotifyTime = Calendar.getInstance();
            nextNotifyTime.add(Calendar.DATE, 1);

            SharedPreferences.Editor editor = context.getSharedPreferences("daily alarm", Context.MODE_PRIVATE).edit();
            editor.putLong("nextNotifyTime", nextNotifyTime.getTimeInMillis());
            editor.apply();

            Date currentDateTime = nextNotifyTime.getTime();
            String date_text = new SimpleDateFormat("yyyy년 MM월 dd일 EE요일 a hh시 mm분", Locale.getDefault()).format(currentDateTime);
            Toast.makeText(context, date_text + "으로 알람이 설정되었습니다.", Toast.LENGTH_SHORT);

        }



    }
}
