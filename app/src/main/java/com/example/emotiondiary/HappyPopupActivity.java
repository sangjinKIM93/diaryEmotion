package com.example.emotiondiary;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class HappyPopupActivity extends AppCompatActivity {

    Button happy, thankful, comfortable, content, excited, relieved, confident, exitBtn;
    int Result_code = 555;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.happy_popup);

        happy = (Button) findViewById(R.id.happy);
        happy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent a = new Intent();
                a.putExtra("emotion", "기쁜");
                setResult(Result_code, a);
                finish();
            }
        });

        thankful = (Button) findViewById(R.id.thank);
        thankful.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent b = new Intent();
                b.putExtra("emotion", "감사하는");
                setResult(Result_code, b);
                finish();
            }
        });

        content = (Button) findViewById(R.id.contented);
        content.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent c = new Intent();
                c.putExtra("emotion", "만족한");
                setResult(Result_code, c);
                finish();
            }
        });
        comfortable = (Button) findViewById(R.id.comfort);
        comfortable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent d = new Intent();
                d.putExtra("emotion", "편안한");
                setResult(Result_code, d);
                finish();
            }
        });
        excited = (Button) findViewById(R.id.excited);
        excited.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent e = new Intent();
                e.putExtra("emotion", "신이 난");
                setResult(Result_code, e);
                finish();
            }
        });
        relieved = (Button) findViewById(R.id.relieved);
        relieved.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent f = new Intent();
                f.putExtra("emotion", "안도하는");
                setResult(Result_code, f);
                finish();
            }
        });
        confident = (Button) findViewById(R.id.confident);
        confident.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent g = new Intent();
                g.putExtra("emotion", "자신하는");
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
