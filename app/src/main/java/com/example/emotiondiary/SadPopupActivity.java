package com.example.emotiondiary;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class SadPopupActivity extends AppCompatActivity {

    Button disappointed, regretful, depressed, pessimistic, tearful, dismayed, sad, exitBtn;
    int Result_code = 111;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.sad_popup);

        sad = (Button) findViewById(R.id.sadBtn);
        sad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent g = new Intent();
                g.putExtra("emotion", "슬픈");
                setResult(Result_code, g);
                finish();
            }
        });
        disappointed = (Button) findViewById(R.id.disappointBtn);
        disappointed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent a = new Intent();
                a.putExtra("emotion", "실망한");
                setResult(Result_code, a);
                finish();
            }
        });

        regretful = (Button) findViewById(R.id.regretBtn);
        regretful.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent b = new Intent();
                b.putExtra("emotion", "후회되는");
                setResult(Result_code, b);
                finish();
            }
        });

        pessimistic = (Button) findViewById(R.id.pessimiBtn);
        pessimistic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent c = new Intent();
                c.putExtra("emotion", "염세적인");
                setResult(Result_code, c);
                finish();
            }
        });
        depressed = (Button) findViewById(R.id.depressBtn);
        depressed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent d = new Intent();
                d.putExtra("emotion", "우울한");
                setResult(Result_code, d);
                finish();
            }
        });
        tearful = (Button) findViewById(R.id.tearBtn);
        tearful.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent e = new Intent();
                e.putExtra("emotion", "눈물이 나는");
                setResult(Result_code, e);
                finish();
            }
        });
        dismayed = (Button) findViewById(R.id.dismayBtn);
        dismayed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent f = new Intent();
                f.putExtra("emotion", "낭패한");
                setResult(Result_code, f);
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
