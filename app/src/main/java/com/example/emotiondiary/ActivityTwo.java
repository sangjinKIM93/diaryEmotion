package com.example.emotiondiary;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.emotiondiary.Adapter.FindRecyclerAdapter;
import com.example.emotiondiary.ListVO.MemoData;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ActivityTwo extends AppCompatActivity {

    String userId;
    String emotionSelected;
    int Request_Code = 1;

    ProgressDialog progressDialog;

    //recyclerView 기본 설정을 위한 놈들
    private FindRecyclerAdapter findRecyclerAdapter;
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;

    List<MemoData> datas;
    DatabaseReference db;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_two);

        //ProgressDialog 생성
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("ProgressDialog running...");
        progressDialog.setCancelable(true);
        progressDialog.setProgressStyle(android.R.style.Widget_ProgressBar_Horizontal);

        SharedPreferences sp = getSharedPreferences("shared", MODE_PRIVATE);
        userId = sp.getString("checkId", " ");

        //다이얼로그 띄운 후 선택 값 받기
        Button selectEmotion = (Button) findViewById(R.id.selectEmotion);
        selectEmotion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ActivityTwo.this, PopupActivity.class);
                startActivityForResult(intent, Request_Code);

            }
        });

        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setSelectedItemId(R.id.navigation_notifications);    //선택된 메뉴 색깔 바꾸기
        navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.navigation_home:
                        Intent a = new Intent(ActivityTwo.this, MemoListActivity.class);
                        startActivity(a);
                        finish();
                        break;

                    case R.id.navigation_dashboard:
                        Intent b = new Intent(ActivityTwo.this, ActivityOne.class);
                        startActivity(b);
                        finish();
                        break;

                    case R.id.navigation_notifications:
                        break;
                }

                return false;
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        new BackgroundTask(this).execute();

        if (requestCode == Request_Code){
            if(resultCode == 10){
                emotionSelected = data.getStringExtra("emotion");
                Log.d("RealEmotion값을 받을 수 있는가?", emotionSelected);
            }
        }

        //리사이클러뷰에 linearLayoutManager를 연결해준다.
        recyclerView = (RecyclerView)findViewById(R.id.recyclerViewFind);
        linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setReverseLayout(true); //역순으로 출력(1)
        linearLayoutManager.setStackFromEnd(true);  //역순으로 출력(2)
        recyclerView.setLayoutManager(linearLayoutManager);


        db = FirebaseDatabase.getInstance().getReference();
        datas = new ArrayList<>();

        db.child("memodata").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot data : dataSnapshot.getChildren()){
                    MemoData memoData = data.getValue(MemoData.class);
                    if (!(memoData.getUser().equals(userId))  && memoData.getChecked() != true && memoData.getTv_emotion().equals(emotionSelected)) {
                        datas.add(memoData);
                    }
                }
                //리사이클러뷰에 어댑터를 연결해준다.
                findRecyclerAdapter = new FindRecyclerAdapter((ArrayList<MemoData>) datas);
                recyclerView.setAdapter(findRecyclerAdapter);
                findRecyclerAdapter.notifyDataSetChanged(); // 자료 추가 후 새로고침
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    class BackgroundTask extends AsyncTask<Integer, Integer, Integer> {

        Context context;

        BackgroundTask(Context context) {
            this.context = context;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog.show();
        }
        @Override
        protected Integer doInBackground(Integer... integers) {
            try {
                Thread.sleep(1500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Integer integer) {
            progressDialog.dismiss();
            super.onPostExecute(integer);
        }
    }


    @Override
    protected void onResume() {
        super.onResume();


    }
}
