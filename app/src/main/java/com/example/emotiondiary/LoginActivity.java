package com.example.emotiondiary;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.emotiondiary.ListVO.UserData;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class LoginActivity extends AppCompatActivity {

    EditText idInput, pwInput;
    CheckBox autoLogin;
    Button loginBtn, registerBtn;
    Boolean checkInfo;
    SharedPreferences pref;

    private Gson gson;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        idInput = (EditText)findViewById(R.id.idInput);
        pwInput = (EditText)findViewById(R.id.pwInput);
        autoLogin = (CheckBox)findViewById(R.id.checkBox);
        loginBtn = (Button)findViewById(R.id.loginBtn);
        registerBtn = (Button)findViewById(R.id.registerBtn);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String id = idInput.getText().toString();
                String pw = pwInput.getText().toString();

                if(loginValidation(id, pw) == true){
                    //화면 전환
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    intent.putExtra("ID", id);
                    startActivity(intent);

                    Toast.makeText(LoginActivity.this, "로그인 성공", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(LoginActivity.this, "로그인 실패", Toast.LENGTH_LONG).show();
                }
            }
        });

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });



    }

    public boolean loginValidation(String id, String password){

        SharedPreferences sp = getSharedPreferences("shared", MODE_PRIVATE);
        String strUser = sp.getString("userdata", "");

        gson = new GsonBuilder().create();

        Type listType = new TypeToken<ArrayList<UserData>>(){}.getType();
        List<UserData> datas = gson.fromJson(strUser, listType);

        //쉐어드에 저장된 값과 일치하는지 확인
        checkInfo = false;
        for(UserData data : datas){
            if(data.getuId().equals(id)  && data.getuPassword().equals(password)){
                checkInfo = true;
                break;
            }
        }


        if(checkInfo == true) {
            // login success
            SharedPreferences.Editor editor = sp.edit();
            editor.putString("checkId", id);
            editor.commit();

            return true;
        }  else {
            // login failed
            return false;
        }

    }

}
