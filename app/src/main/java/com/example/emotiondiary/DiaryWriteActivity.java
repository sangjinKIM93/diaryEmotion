package com.example.emotiondiary;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;

public class DiaryWriteActivity extends Activity implements View.OnClickListener {

    EditText content;
    Button listBtn, saveBtn, delBtn;
    String fName;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.diarywrite_constraint);

        content = (EditText)findViewById(R.id.edit);    //content = 글 작성 내용
        content.setBackgroundColor(Color.argb(100,225,255,99));

        Bundle params = getIntent().getExtras();    //넘어온 정보를 읽어냄
        fName = params.getString("fName");      //list에서 넘어온 파일명을 읽어냄

        if(fName.equals("new")){            //넘어온 파일명이 "new"이면
            fName = System.currentTimeMillis()+".txt";          //현재시간으로 파일명 설정(txt 파일)
            Toast.makeText(this, "새로운 열기를 입력하세요.", Toast.LENGTH_SHORT).show();
        } else {
            content.setText(readFile(this));    //넘어온 파일명이 기존에 저장된 것이면 기존 파일 내용을 불러온다. (readfile이 )
            Toast.makeText(this, "저장된 열기를 표시합니다.", Toast.LENGTH_SHORT).show();
        }

        listBtn = (Button)findViewById(R.id.listBtn);
        listBtn.setOnClickListener(this);

        saveBtn = (Button)findViewById(R.id.saveBtn);
        saveBtn.setOnClickListener(this);

        delBtn = (Button)findViewById(R.id.delBtn);
        delBtn.setOnClickListener(this);
    }

    public String readFile(Context c) {
        FileInputStream in = null;
        ByteArrayOutputStream out = null;
        int len = 0;

        byte[] bStr = new byte[1024]; //1Byte씩 읽지 않고 충분한 크기의 버퍼만큼 한번에 읽어서 처리하기 위함

        try{
            in = c.openFileInput(fName);    //파일을 불러와
            out = new ByteArrayOutputStream();  //버퍼 단위로 읽어

            while((len = in.read(bStr)) != -1){
                out.write(bStr, 0, len);    //읽은 내용을 버퍼에 저장
            }
            out.close();
            in.close();

        } catch (Exception e) {
            try{
                if(out != null) out.close();
                if(in != null) in.close();

            }catch(Exception e2){
                e2.printStackTrace();
            }
        }
        return out.toString();
    }

    public void saveFile(Context c, String cStr){
        FileOutputStream out = null;
        byte[] bStr = cStr.getBytes();
        try{
            //파일 생성 & 버퍼로 저장
            out = c.openFileOutput(fName, Context.MODE_PRIVATE);
            out.write(bStr, 0, bStr.length);
            out.close();
        }catch(Exception e){
            try{
                if(out != null) out.close();
            }catch (Exception e2){
                e2.printStackTrace();
            }
        }
    }

    public void returnMain(){
        Intent it = new Intent(this, DiaryListActivity.class);
        startActivity(it);
        finish();
    }

    @Override
    public void onClick(View v) {
        if(v==listBtn){
            returnMain();
        }else if( v == saveBtn){
            String cStr = content.getText().toString();
            saveFile(this,cStr);
            Toast.makeText(this, "파일이 저장되었습니다.", Toast.LENGTH_SHORT).show();

        }else if( v==delBtn){
            deleteFile(fName);
            content.setText("");
            Toast.makeText(this, "파일이 삭제되었습니다.", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        String cStr = content.getText().toString();
        saveFile(this,cStr);
        Toast.makeText(this, "파일이 임시 저장되었습니다.", Toast.LENGTH_SHORT).show();

    }

}

