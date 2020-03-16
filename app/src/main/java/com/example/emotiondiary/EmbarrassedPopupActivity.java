package com.example.emotiondiary;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class EmbarrassedPopupActivity extends AppCompatActivity {

    Button embarrassed, isolated, conscious, inferior, guilty, ashamed, pathetic, exitBtn;
    int Result_code = 444;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.embarrassed_popup);

        embarrassed = (Button) findViewById(R.id.embarrassed);
        embarrassed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent a = new Intent();
                a.putExtra("emotion", "당황");
                setResult(Result_code, a);
                finish();
            }
        });

        isolated = (Button) findViewById(R.id.isolated);
        isolated.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent b = new Intent();
                b.putExtra("emotion", "격리된");
                setResult(Result_code, b);
                finish();
            }
        });

        inferior = (Button) findViewById(R.id.inferior);
        inferior.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent c = new Intent();
                c.putExtra("emotion", "열등한");
                setResult(Result_code, c);
                finish();
            }
        });
        conscious = (Button) findViewById(R.id.conscious);
        conscious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent d = new Intent();
                d.putExtra("emotion", "시선 의식하는");
                setResult(Result_code, d);
                finish();
            }
        });
        guilty = (Button) findViewById(R.id.guilty);
        guilty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent e = new Intent();
                e.putExtra("emotion", "죄책감의");
                setResult(Result_code, e);
                finish();
            }
        });
        ashamed = (Button) findViewById(R.id.ashamed);
        ashamed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent f = new Intent();
                f.putExtra("emotion", "부끄러운");
                setResult(Result_code, f);
                finish();
            }
        });
        pathetic = (Button) findViewById(R.id.pathetic);
        pathetic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent g = new Intent();
                g.putExtra("emotion", "한심한");
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
