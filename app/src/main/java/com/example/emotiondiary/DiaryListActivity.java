package com.example.emotiondiary;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;


import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class DiaryListActivity extends AppCompatActivity implements View.OnClickListener {

    Button writeBtn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.diarylist_relative);

        loadList();

        writeBtn = (Button)findViewById(R.id.writeBtn);
        writeBtn.setOnClickListener(this);

    }

    public void loadList(){
        LinearLayout layout = (LinearLayout)findViewById(R.id.list);
        layout.setBackgroundColor(Color.argb(100,0,0,66));
        String[] lists = fileList();
        if(lists.equals(null) || lists.length == 0){
            layout.addView(makeTextView("작성된 일기가 없습니다.", "nt"));
        } else {
            layout.removeAllViews();
            for(int i=0; i<lists.length; i++){
                if(lists[i].endsWith(".txt"))
                    layout.addView(makeTextView(lists[i], "t"+i));
            }
            if(layout.getChildCount() ==0 )
                layout.addView(makeTextView("작성된 일기가 없습니다", "nt"));
        }
    }

    public TextView makeTextView(String label, String tag) {
        TextView tv = new TextView(this);
        tv.setText(label);
        tv.setTag(tag);
        tv.setOnClickListener(this);
        return tv;
    }

    @Override
    public void onClick(View v) {
        Intent it = new Intent(DiaryListActivity.this, DiaryWriteActivity.class);
        if(v==writeBtn){
            it.putExtra("fName", "new");
        } else {
            TextView tv = (TextView)v;
            String tLabel = (String)tv.getText();
            it.putExtra("fName", tLabel);   //위에서 maketextview한 것을 사용하는거지.
        }
        startActivity(it);
        finish();
    }
}
