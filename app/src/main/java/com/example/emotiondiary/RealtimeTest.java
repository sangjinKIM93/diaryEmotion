package com.example.emotiondiary;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.emotiondiary.ListVO.NewsData;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class RealtimeTest extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.realtime_test);

        DatabaseReference db = FirebaseDatabase.getInstance().getReference();

//        //자바 객체를 활용한 저장을 해봅시다.
//        MemoData memoData = new MemoData();
//        memoData.setChecked(true);
//        memoData.setId(123);
//        memoData.setImgPath("pahh///dsa");
//        memoData.setLink("http/////");
//        memoData.setTime("20191104");
//        memoData.setTv_content("음... 뭘까..");
//        memoData.setTv_emotion("불안");
//        memoData.setTv_name("기원합니다.");
//        memoData.setUser("sangjin");
//        db.child("users").child("sangjin").setValue(memoData);

//        MemoData memoData2 = new MemoData("당황", "제목데스2", "본문이다2.", "20191104", 1234, "juri", true, "/굥로/경로", "뉴스 링크 http//");
//        db.child("users").child("juri").setValue(memoData2);

        db.child("users").removeValue();

        List<NewsData> listNews = new ArrayList<>();

        NewsData newsData = new NewsData();
        newsData.setTitle("될겁니다.1");
        newsData.setContent("보셨습니가1");
        newsData.setLink("기쁨");
        db.child("users").child("hosu").setValue(newsData);

        NewsData newsData2 = new NewsData();
        newsData2.setTitle("될겁니다.3");
        newsData2.setContent("보셨습니가3");
        newsData2.setLink("기쁨");
        db.child("users").child("doseo").setValue(newsData2);

        NewsData newsData3 = new NewsData();
        newsData3.setTitle("될겁니다.2");
        newsData3.setContent("보셨습니가2");
        newsData3.setLink("당황");
        db.child("users").child("guan").setValue(newsData3);

        listNews.add(newsData);
        listNews.add(newsData2);
        listNews.add(newsData3);

        db.child("users").child("total").setValue(listNews);

        //데이터 가져오기
        db.child("users").child("total").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot data : dataSnapshot.getChildren()){
                    NewsData news = data.getValue(NewsData.class);
                    if(news.getLink().equals("기쁨")) {
                        Log.d("NEWS 나와라", news.getTitle());
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        //그냥 가져오는 것은 의미가 없다.
        //해당 감정에 대해서 가져와야지. 성공!
        //자! 이제 구조를 어떻게 짤지 결정하자.
        //사실 내가 원래 짠 구조는 memodata 안에 더 쳐 넣는거지(무식한줄 알았는데 가장 깔끔한 방법이었어.)
        //여기서 고민은 ID별로 나눠서 짤것인가? ㄴㄴ 검색하기 힘들어.
        //차라리 그 로직 짤 시간에 fireStore를 써봐.

    }
}
