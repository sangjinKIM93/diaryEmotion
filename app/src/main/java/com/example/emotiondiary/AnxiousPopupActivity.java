package com.example.emotiondiary;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class AnxiousPopupActivity extends AppCompatActivity {

    Button anxious, afraid, stressed, confused, worried, cautious, nervous, exitBtn;

    int Result_code = 222;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.anxious_popup);

        anxious = (Button) findViewById(R.id.anxious);
        anxious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent a = new Intent();
                a.putExtra("emotion", "불안");
                setResult(Result_code, a);
                finish();
            }
        });

        afraid = (Button) findViewById(R.id.afraid);
        afraid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent b = new Intent();
                b.putExtra("emotion", "두려운");
                setResult(Result_code, b);
                finish();
            }
        });

        confused = (Button) findViewById(R.id.confused);
        confused.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent c = new Intent();
                c.putExtra("emotion", "헷갈리는");
                setResult(Result_code, c);
                finish();
            }
        });
        stressed = (Button) findViewById(R.id.stressed);
        stressed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent d = new Intent();
                d.putExtra("emotion", "스트레스");
                setResult(Result_code, d);
                finish();
            }
        });
        worried = (Button) findViewById(R.id.worried);
        worried.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent e = new Intent();
                e.putExtra("emotion", "걱정스러운");
                setResult(Result_code, e);
                finish();
            }
        });
        cautious = (Button) findViewById(R.id.cautious);
        cautious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent f = new Intent();
                f.putExtra("emotion", "조심스러운");
                setResult(Result_code, f);
                finish();
            }
        });
        nervous = (Button) findViewById(R.id.nervous);
        nervous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent g = new Intent();
                g.putExtra("emotion", "신경쓰이는");
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
            return false;
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        return;
    }
}
