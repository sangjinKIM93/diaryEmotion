package com.example.emotiondiary;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.example.emotiondiary.Adapter.RecyclerViewAdapter;
import com.example.emotiondiary.ListVO.MemoData;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MemoListActivity extends AppCompatActivity {

    private ArrayList<MemoData> arrayList;      //data를 받는 ArrayList를 가져온다.
    private RecyclerViewAdapter recyclerViewAdapter;            //어댑터 받아온다.
    private RecyclerView recyclerView;          //?이거는 기본적으로 제공해주는 기능 같은데?
    private LinearLayoutManager linearLayoutManager;        //?이것도 기본적으로 제공해주는 기능 같은데?

    String userId;
    List<MemoData> datas;
    DatabaseReference db;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.memolist_recycle);

        SharedPreferences sp = getSharedPreferences("shared", MODE_PRIVATE);
        String strId = sp.getString("checkId", "");
        userId = strId;

        //ProgressDialog 생성 및 동작
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("ProgressDialog running...");
        progressDialog.setCancelable(true);
        progressDialog.setProgressStyle(android.R.style.Widget_ProgressBar_Horizontal);
        new BackgroundTask(this).execute();

        //리사이클러뷰를 다루기 위한 기본 설정 (recyclerView의 레이아웃 매니저 설정)
        recyclerView = (RecyclerView) findViewById(R.id.rv);
        linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setReverseLayout(true); //역순으로 출력(1)
        linearLayoutManager.setStackFromEnd(true);  //역순으로 출력(2)
        recyclerView.setLayoutManager(linearLayoutManager);

        //firebase에서 db 받기
        db = FirebaseDatabase.getInstance().getReference();
        datas = new ArrayList<>();
        db.child("memodata").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot data : dataSnapshot.getChildren()){
                    MemoData memoData = data.getValue(MemoData.class);
                    if(memoData.getUser().equals(userId)) {
                        datas.add(memoData);
                    }
                }
                recyclerViewAdapter = new RecyclerViewAdapter((ArrayList<MemoData>) datas);   //arraylist의 데이터를 adapter에 담고
                recyclerView.setAdapter(recyclerViewAdapter);
                recyclerViewAdapter.notifyDataSetChanged(); //새로고침
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.navigation_home:
                        break;

                    case R.id.navigation_dashboard:
                        Intent a = new Intent(MemoListActivity.this, ActivityOne.class);
                        startActivity(a);
                        finish();
                        break;

                    case R.id.navigation_notifications:
                        Intent b = new Intent(MemoListActivity.this, ActivityTwo.class);
                        startActivity(b);
                        finish();
                        break;
                }
                return false;
            }
        });

        Button btn_add = (Button) findViewById(R.id.btn_add);
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(), MemoWriteActivity.class);
                startActivity(intent);

            }
        });

    }

    @Override
    protected void onRestart() {
        super.onRestart();

        db = FirebaseDatabase.getInstance().getReference();
        datas = new ArrayList<>();
        db.child("memodata").addValueEventListener(new ValueEventListener() {           //음... 이거는 데이터가 추가되지 않으면 발동을 안 하나봐.
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //datas.clear();
                for(DataSnapshot data : dataSnapshot.getChildren()){
                    MemoData memoData = data.getValue(MemoData.class);
                    if(memoData.getUser().equals(userId)) {
                        datas.add(memoData);
                    }
                }
                recyclerViewAdapter = new RecyclerViewAdapter((ArrayList<MemoData>) datas);   //arraylist의 데이터를 adapter에 담고
                recyclerView.setAdapter(recyclerViewAdapter);
                recyclerViewAdapter.notifyDataSetChanged(); //새로고침
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
                Thread.sleep(2500);
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
}

