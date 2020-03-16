package com.example.emotiondiary;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class HurtPopupActivity extends AppCompatActivity {

    Button hurt, jealous, betrayed, abandoned, shocked, victimized, aggrieved, exitBtn;
    int Result_code = 333;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.hurt_popup);

        hurt = (Button) findViewById(R.id.hurt);
        hurt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent a = new Intent();
                a.putExtra("emotion", "상처");
                setResult(Result_code, a);
                finish();
            }
        });

        jealous = (Button) findViewById(R.id.jealous);
        jealous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent b = new Intent();
                b.putExtra("emotion", "질투하는");
                setResult(Result_code, b);
                finish();
            }
        });

        abandoned = (Button) findViewById(R.id.abandoned);
        abandoned.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent c = new Intent();
                c.putExtra("emotion", "버려진");
                setResult(Result_code, c);
                finish();
            }
        });
        betrayed = (Button) findViewById(R.id.betrayed);
        betrayed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent d = new Intent();
                d.putExtra("emotion", "배신당한");
                setResult(Result_code, d);
                finish();
            }
        });
        shocked = (Button) findViewById(R.id.shocked);
        shocked.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent e = new Intent();
                e.putExtra("emotion", "충격받은");
                setResult(Result_code, e);
                finish();
            }
        });
        victimized = (Button) findViewById(R.id.victimized);
        victimized.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent f = new Intent();
                f.putExtra("emotion", "희생된");
                setResult(Result_code, f);
                finish();
            }
        });
        aggrieved = (Button) findViewById(R.id.aggrieved);
        aggrieved.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent g = new Intent();
                g.putExtra("emotion", "억울한");
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
