package com.example.emotiondiary;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.DialogInterface;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;

import static com.example.emotiondiary.R.drawable.pause_button;

public class MusicPlayerActivity extends Activity {

    MediaPlayer mp;
    SeekBar seekBar;
    ImageView img;
    private ObjectAnimator anim;

    Handler handler = new Handler();
    TextView textAdvice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.musicplayer_constraint);

        textAdvice = (TextView) findViewById(R.id.tv_advice);
        textAdvice.setText("Deep Breath");
        int value = 0;
        String[] advices = {"깊게 숨을 들이쉽니다.", "깊게 숨을 내쉽니다.", "다시 한번 반복합니다.", "깊게 숨을 들이쉽니다.", "깊게 숨을 내쉽니다.", "이제 눈을 감고 반복합니다."};

        //버튼 회전시키기
        img = (ImageView) findViewById(R.id.rotateBtn);

        anim = ObjectAnimator.ofFloat(img, "rotation", 0, 360);
        anim.setDuration(20000);
        anim.setRepeatCount(100);
        anim.setRepeatMode(ObjectAnimator.RESTART);


        //Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate);
        //btn.setAnimation(animation);


        //button을 불러온다.
        final Button btn_play = (Button) findViewById(R.id.btn_play);

        //미디어플레이어 생성
        Uri urivalue = getIntent().getParcelableExtra("URI_VALUE");
        mp = MediaPlayer.create(getApplicationContext(), urivalue);

        //seekbar를 만든다
//        seekBar = (SeekBar)findViewById(R.id.seekBar);


        btn_play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //seekBar에 대한 처리
//                seekBar.setMax(mp.getDuration()); // 음악의 총 길이를 시크바의 최대값으로 적용
//                seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
//                    @Override
//                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
//                        if(fromUser)    //사용자가 시크바를 움직이면
//                            mp.seekTo(progress);    //재생위치를 바꿔준다(움직인 곳에서의 음악재생).
//                    }
//
//                    @Override
//                    public void onStartTrackingTouch(SeekBar seekBar) {
//
//                    }
//
//                    @Override
//                    public void onStopTrackingTouch(SeekBar seekBar) {
//
//                    }
//                });

                if (mp.isPlaying()) {

                    anim.pause();
                    mp.pause();
                    btn_play.setBackgroundResource(R.drawable.play_button);

                } else {
                    mp.start();
                    anim.start();
                    btn_play.setBackgroundResource(pause_button);

                    //runnable로 스레드 생성해주는 것
                    new Thread(new Runnable() {
                        boolean isRun = false;
                        int value = 0;
                        String[] advices = {"깊게 숨을 들이쉽니다.", "깊게 숨을 내쉽니다.", "다시 한번 반복합니다.", "깊게 숨을 들이쉽니다.", "깊게 숨을 내쉽니다.", "이제 눈을 감고 반복합니다."};
                        String advice;

                        @Override
                        public void run() {
                            isRun = true;
                            while ((isRun)) {

                                advice = advices[value];
                                value += 1;

                                handler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        textAdvice.setText(advice);
                                    }
                                });
                                try {
                                    Thread.sleep(3000);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }

                                //문구 끝날시 핸들러 종료
                                if (value == 6) {
                                    isRun = false;
                                    Log.d("handler가", "종료됩니다.");
                                }
                            }
                        }
                    }).start();
                }
            }
        });
    }

    //애니메이션 클릭시 정지 기능.
    public void animationClicked(View view) {
        DialogSimple();
    }

    private void DialogSimple() {
        AlertDialog.Builder alt_bld = new AlertDialog.Builder(this);
        alt_bld.setMessage("Do you want to stop animation effect ?").setCancelable(
                false).setPositiveButton("Yes",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // Action for 'Yes' Button
                        anim.pause();
                    }
                }).setNegativeButton("No",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // Action for 'NO' Button
                        dialog.cancel();
                    }
                });
        AlertDialog alert = alt_bld.create();
        // Title for AlertDialog
        alert.setTitle("Title");
        // Icon for AlertDialog
        alert.setIcon(R.drawable.ic_launcher_foreground);
        alert.show();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        mp.stop();
    }


}
