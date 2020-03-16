package com.example.emotiondiary;

import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class OtherMemoActivity extends AppCompatActivity {

    TextView titleOther, contentOther, timeOther, userOther, linkOther;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.othermemo);

        //담을 그릇 설정
        titleOther = (TextView)findViewById(R.id.titleOtherMemo);
        contentOther = (TextView)findViewById(R.id.contentOtherMemo);
        timeOther = (TextView)findViewById(R.id.timeOtherMemo);
        userOther = (TextView)findViewById(R.id.userOtherMemo);
        linkOther = findViewById(R.id.link_othermemo);

        //내용 담기
        String title = getIntent().getStringExtra("TITLE");
        String content = getIntent().getStringExtra("CONTENT");
        String time = getIntent().getStringExtra("TIME");
        String user = getIntent().getStringExtra("USER");
        String link = getIntent().getStringExtra("LINK");

        titleOther.setText(title);
        contentOther.setText(content);
        timeOther.setText(time);
        userOther.setText(user);
        linkOther.setText(link);





    }
}
