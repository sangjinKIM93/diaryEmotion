package com.example.emotiondiary;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity {

    String userId;

    //ActionBar 설정(새로 그려주기)
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.actionbar_actions, menu);
        return true;
    }

    //ActionBar 설정(아이콘 클릭시 액티비티 이동)
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_info:
                Intent intent = new Intent(this, MyInfoActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_constraint);

        SharedPreferences sp = getSharedPreferences("shared", MODE_PRIVATE);
        userId = sp.getString("checkId", "");

    }


    public void diaryClicked(View view) {
        Intent intent2 = new Intent(this, MemoListActivity.class);
        startActivity(intent2);
    }

    public void alramClicked(View view) {
        Intent intent = new Intent(this, NewAlarmActivity.class);
        startActivity(intent);
    }



    //뒤로 가면 종료하시겠습니까 dial 생성
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK:
                String alertTitle = "Auto Scheduler";
                String buttonMessage = "어플을 종료하시겠습니까?";
                String buttonYes = "Yes";
                String buttonNo = "No";

                new AlertDialog.Builder(MainActivity.this)
                        .setTitle(alertTitle)
                        .setMessage(buttonMessage)
                        .setPositiveButton(buttonYes, new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // TODO Auto-generated method stub
                                moveTaskToBack(true);
                                finish();
                            }
                        })
                        .setNegativeButton(buttonNo, null)
                        .show();
        }
        return true;
    }
}
