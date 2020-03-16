package com.example.emotiondiary;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.emotiondiary.Adapter.ListViewAdapter;
import com.example.emotiondiary.ListVO.ListVO;

public class MusicListActivity extends AppCompatActivity {

    private ListView listview;
    private ListViewAdapter adapter;
    private int[] img = {R.drawable.bird_img, R.drawable.firewood_img, R.drawable.wave_img};
    private String[] Title = {"새 지저귀는 소리", "장작 타는 소리", "부서지는 파도 소리"};
    private String[] audio = {"music", "overcome", "destination"};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.musiclist_relative);

        Intent intent = getIntent();
        int data = intent.getIntExtra("INTENT_KEY", 0);
        Log.d("INTENT_KEY"," " + data);

        //변수 초기화
        adapter = new ListViewAdapter();
        listview = (ListView) findViewById(R.id.musicList);

        //어뎁터 할당
        listview.setAdapter(adapter);

        //클릭시 화면 전환. 오호. 내가 이런 코드를? 어답터 정보 받아와서 클릭시처리. -> ListView라 그런거...
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Uri uriValue = ((ListVO)adapter.getItem(position)).getRaw();    //ListVO에서 해당 position의 노래 가져오기

                Intent intent = new Intent(MusicListActivity.this, MusicPlayerActivity.class);
                intent.putExtra("URI_VALUE", uriValue);
                startActivity(intent);
            }
        });
    }

    public void backBtnClicked(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
