package com.example.emotiondiary;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class PopupActivity extends AppCompatActivity {

    Button justangry, grumpy, frustrated, annoyed, defensive, offended, irritated;
    Button disappointed, regretful, depressed, pessimistic, tearful, dismayed, sad;
    Button anxious, afraid, stressed, confused, worried, cautious, nervous;
    Button hurt, jealous, betrayed, abandoned, shocked, victimized, aggrieved;
    Button embarrassed, isolated, conscious, inferior, guilty, ashamed, pathetic;
    Button happy, thankful, comfortable, content, excited, relieved, confident;
    int Result_code = 10;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.popup_select);

        happy = (Button) findViewById(R.id.happyF);
        happy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent a = new Intent();
                a.putExtra("emotion", "기쁜");
                setResult(Result_code, a);
                finish();
            }
        });

        thankful = (Button) findViewById(R.id.thankF);
        thankful.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent b = new Intent();
                b.putExtra("emotion", "감사하는");
                setResult(Result_code, b);
                finish();
            }
        });

        content = (Button) findViewById(R.id.contentF);
        content.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent c = new Intent();
                c.putExtra("emotion", "만족한");
                setResult(Result_code, c);
                finish();
            }
        });
        comfortable = (Button) findViewById(R.id.comfortF);
        comfortable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent d = new Intent();
                d.putExtra("emotion", "편안한");
                setResult(Result_code, d);
                finish();
            }
        });
        excited = (Button) findViewById(R.id.excitedF);
        excited.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent e = new Intent();
                e.putExtra("emotion", "신이 난");
                setResult(Result_code, e);
                finish();
            }
        });
        relieved = (Button) findViewById(R.id.relievedF);
        relieved.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent f = new Intent();
                f.putExtra("emotion", "안도하는");
                setResult(Result_code, f);
                finish();
            }
        });
        confident = (Button) findViewById(R.id.confidentF);
        confident.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent g = new Intent();
                g.putExtra("emotion", "자신하는");
                setResult(Result_code, g);
                finish();
            }
        });

        embarrassed = (Button) findViewById(R.id.embarrassedF);
        embarrassed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent a = new Intent();
                a.putExtra("emotion", "당황");
                setResult(Result_code, a);
                finish();
            }
        });

        isolated = (Button) findViewById(R.id.isolatedF);
        isolated.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent b = new Intent();
                b.putExtra("emotion", "격리된");
                setResult(Result_code, b);
                finish();
            }
        });

        inferior = (Button) findViewById(R.id.inferiorF);
        inferior.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent c = new Intent();
                c.putExtra("emotion", "열등한");
                setResult(Result_code, c);
                finish();
            }
        });
        conscious = (Button) findViewById(R.id.conciousF);
        conscious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent d = new Intent();
                d.putExtra("emotion", "시선 의식하는");
                setResult(Result_code, d);
                finish();
            }
        });
        guilty = (Button) findViewById(R.id.guiltyF);
        guilty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent e = new Intent();
                e.putExtra("emotion", "죄책감의");
                setResult(Result_code, e);
                finish();
            }
        });
        ashamed = (Button) findViewById(R.id.ashamedF);
        ashamed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent f = new Intent();
                f.putExtra("emotion", "부끄러운");
                setResult(Result_code, f);
                finish();
            }
        });
        pathetic = (Button) findViewById(R.id.patheticF);
        pathetic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent g = new Intent();
                g.putExtra("emotion", "한심한");
                setResult(Result_code, g);
                finish();
            }
        });

        hurt = (Button) findViewById(R.id.hurtF);
        hurt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent a = new Intent();
                a.putExtra("emotion", "상처");
                setResult(Result_code, a);
                finish();
            }
        });

        jealous = (Button) findViewById(R.id.jealousF);
        jealous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent b = new Intent();
                b.putExtra("emotion", "질투하는");
                setResult(Result_code, b);
                finish();
            }
        });

        abandoned = (Button) findViewById(R.id.abandonedF);
        abandoned.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent c = new Intent();
                c.putExtra("emotion", "버려진");
                setResult(Result_code, c);
                finish();
            }
        });
        betrayed = (Button) findViewById(R.id.betrayedF);
        betrayed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent d = new Intent();
                d.putExtra("emotion", "배신당한");
                setResult(Result_code, d);
                finish();
            }
        });
        shocked = (Button) findViewById(R.id.shockF);
        shocked.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent e = new Intent();
                e.putExtra("emotion", "충격받은");
                setResult(Result_code, e);
                finish();
            }
        });
        victimized = (Button) findViewById(R.id.victimizedF);
        victimized.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent f = new Intent();
                f.putExtra("emotion", "희생된");
                setResult(Result_code, f);
                finish();
            }
        });
        aggrieved = (Button) findViewById(R.id.aggrievedF);
        aggrieved.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent g = new Intent();
                g.putExtra("emotion", "억울한");
                setResult(Result_code, g);
                finish();
            }
        });

        anxious = (Button) findViewById(R.id.anxiousF);
        anxious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent a = new Intent();
                a.putExtra("emotion", "불안");
                setResult(Result_code, a);
                finish();
            }
        });

        afraid = (Button) findViewById(R.id.afraidF);
        afraid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent b = new Intent();
                b.putExtra("emotion", "두려운");
                setResult(Result_code, b);
                finish();
            }
        });

        confused = (Button) findViewById(R.id.confusedF);
        confused.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent c = new Intent();
                c.putExtra("emotion", "헷갈리는");
                setResult(Result_code, c);
                finish();
            }
        });
        stressed = (Button) findViewById(R.id.stressF);
        stressed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent d = new Intent();
                d.putExtra("emotion", "스트레스");
                setResult(Result_code, d);
                finish();
            }
        });
        worried = (Button) findViewById(R.id.worriyF);
        worried.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent e = new Intent();
                e.putExtra("emotion", "걱정스러운");
                setResult(Result_code, e);
                finish();
            }
        });
        cautious = (Button) findViewById(R.id.cautiousF);
        cautious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent f = new Intent();
                f.putExtra("emotion", "조심스러운");
                setResult(Result_code, f);
                finish();
            }
        });
        nervous = (Button) findViewById(R.id.nervousf);
        nervous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent g = new Intent();
                g.putExtra("emotion", "신경쓰이는");
                setResult(Result_code, g);
                finish();
            }
        });

        justangry = (Button) findViewById(R.id.angryF);
        justangry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent a = new Intent();
                a.putExtra("emotion", "분노");
                setResult(Result_code, a);
                finish();
            }
        });

        grumpy = (Button) findViewById(R.id.grumpyF);
        grumpy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent b = new Intent();
                b.putExtra("emotion", "툴툴대는");
                setResult(Result_code, b);
                finish();
            }
        });

        annoyed = (Button) findViewById(R.id.annoyF);
        annoyed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent c = new Intent();
                c.putExtra("emotion", "짜증내는");
                setResult(Result_code, c);
                finish();
            }
        });
        frustrated = (Button) findViewById(R.id.frustatedF);
        frustrated.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent d = new Intent();
                d.putExtra("emotion", "좌절한");
                setResult(Result_code, d);
                finish();
            }
        });
        defensive = (Button) findViewById(R.id.defensivF);
        defensive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent e = new Intent();
                e.putExtra("emotion", "방어적인");
                setResult(Result_code, e);
                finish();
            }
        });
        offended = (Button) findViewById(R.id.offendedF);
        offended.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent f = new Intent();
                f.putExtra("emotion", "노여워하는");
                setResult(Result_code, f);
                finish();
            }
        });
        irritated = (Button) findViewById(R.id.irritatedF);
        irritated.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent g = new Intent();
                g.putExtra("emotion", "성가신");
                setResult(Result_code, g);
                finish();
            }
        });

        sad = (Button) findViewById(R.id.sadP);
        sad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent g = new Intent();
                g.putExtra("emotion", "슬픈");
                setResult(Result_code, g);
                finish();
            }
        });
        disappointed = (Button) findViewById(R.id.disappointF);
        disappointed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent a = new Intent();
                a.putExtra("emotion", "실망한");
                setResult(Result_code, a);
                finish();
            }
        });

        regretful = (Button) findViewById(R.id.regretF);
        regretful.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent b = new Intent();
                b.putExtra("emotion", "후회되는");
                setResult(Result_code, b);
                finish();
            }
        });

        pessimistic = (Button) findViewById(R.id.pessimiF);
        pessimistic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent c = new Intent();
                c.putExtra("emotion", "염세적인");
                setResult(Result_code, c);
                finish();
            }
        });
        depressed = (Button) findViewById(R.id.depressF);
        depressed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent d = new Intent();
                d.putExtra("emotion", "우울한");
                setResult(Result_code, d);
                finish();
            }
        });
        tearful = (Button) findViewById(R.id.tearF);
        tearful.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent e = new Intent();
                e.putExtra("emotion", "눈물이 나는");
                setResult(Result_code, e);
                finish();
            }
        });
        dismayed = (Button) findViewById(R.id.dismayedf);
        dismayed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent f = new Intent();
                f.putExtra("emotion", "낭패한");
                setResult(Result_code, f);
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
