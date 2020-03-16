package com.example.emotiondiary;

import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.emotiondiary.Adapter.MusicRecyclerAdapter;
import com.example.emotiondiary.ListVO.ListVO;
import com.example.emotiondiary.R;

import java.util.ArrayList;

public class MusicListRecyclerActivity extends AppCompatActivity {

    //recyclerView 기본 설정을 위한 놈들
    private ArrayList<ListVO> arrayListMusic;           //arrayList는 해당 리사이클뷰의 자료형(ListVO)을 받는다.
    private MusicRecyclerAdapter musicRecyclerAdapter;
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;

    //목록으로 보여줄 자료들
    private int[] img = {R.drawable.bird_img, R.drawable.firewood_img, R.drawable.wave_img, R.drawable.bird_img, R.drawable.firewood_img, R.drawable.wave_img, R.drawable.bird_img, R.drawable.firewood_img, R.drawable.wave_img};
    private String[] title = {"0.새 지저귀는 소리", "1.장작 타는 소리", "2.부서지는 파도 소리", "3.새 지저귀는 소리", "4.장작 타는 소리", "5.부서지는 파도 소리", "6.새 지저귀는 소리", "7.장작 타는 소리", "8.부서지는 파도 소리"};
    private String[] audio = {"music", "overcome", "destination", "music", "overcome", "destination", "music", "overcome", "destination"};
    private int[] num = {0,1,2,3,4,5,6,7,8};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.musiclist_recycle);

        arrayListMusic = new ArrayList<>();

        //데이터 추가
        for(int i=0; i<img.length; i++){
            Uri uri = Uri.parse("android.resource://"+getPackageName()+"/raw/"+audio[i]);   //음악 파일 파싱하는 방법
            ListVO listVo = new ListVO(img[i], title[i], uri, num[i]);
            arrayListMusic.add(listVo);
        }

        //리사이클러뷰에 linearLayoutManager를 연결해준다.
        recyclerView = (RecyclerView)findViewById(R.id.recyclerView);
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        musicRecyclerAdapter = new MusicRecyclerAdapter(arrayListMusic);
        recyclerView.setAdapter(musicRecyclerAdapter);

        musicRecyclerAdapter.notifyDataSetChanged(); //새로고침
    }

}
