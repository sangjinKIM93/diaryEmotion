package com.example.emotiondiary;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class EmotionPopupActivity extends AppCompatActivity {

    Button justangry, grumpy, frustrated, annoyed, defensive, offended, irritated, exitBtn;
    int Result_code = 999;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.emtion_popup);

        justangry = (Button) findViewById(R.id.justangryBtn);
        justangry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent a = new Intent();
                a.putExtra("emotion", "분노");
                setResult(Result_code, a);
                finish();
            }
        });

        grumpy = (Button) findViewById(R.id.grumpyBtn);
        grumpy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent b = new Intent();
                b.putExtra("emotion", "툴툴대는");
                setResult(Result_code, b);
                finish();
            }
        });

        annoyed = (Button) findViewById(R.id.annoyedBtn);
        annoyed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent c = new Intent();
                c.putExtra("emotion", "짜증내는");
                setResult(Result_code, c);
                finish();
            }
        });
        frustrated = (Button) findViewById(R.id.frustratedBtn);
        frustrated.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent d = new Intent();
                d.putExtra("emotion", "좌절한");
                setResult(Result_code, d);
                finish();
            }
        });
        defensive = (Button) findViewById(R.id.defensiveBtn);
        defensive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent e = new Intent();
                e.putExtra("emotion", "방어적인");
                setResult(Result_code, e);
                finish();
            }
        });
        offended = (Button) findViewById(R.id.offendedBtn);
        offended.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent f = new Intent();
                f.putExtra("emotion", "노여워하는");
                setResult(Result_code, f);
                finish();
            }
        });
        irritated = (Button) findViewById(R.id.irritatedBtn);
        irritated.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent g = new Intent();
                g.putExtra("emotion", "성가신");
                setResult(Result_code, g);
                finish();
            }
        });

        exitBtn = findViewById(R.id.exitBtn);
        exitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_OUTSIDE) {
            finish();
        }
        return true;
    }

    //백버튼 막기
//    @Override
//    public void onBackPressed() {
//        return;
//    }
}
