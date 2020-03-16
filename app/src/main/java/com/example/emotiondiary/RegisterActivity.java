package com.example.emotiondiary;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegisterActivity extends AppCompatActivity {

    private Gson gson;
    private EditText registerIdInput, registerPwInput;
    private String registerId, registerPw;
    private Button registerButton;

    boolean checkValidation = true;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);

        registerButton = (Button)findViewById(R.id.registerBtnReal);

        registerIdInput = (EditText)findViewById(R.id.registerIdInput);
        registerPwInput= (EditText)findViewById(R.id.registerPwInput);


        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerId = registerIdInput.getText().toString();
                registerPw = registerPwInput.getText().toString();

                Pattern p = Pattern.compile("[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
                                "\\@" +
                                "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
                                "(" +
                                "\\." +
                                "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
                                ")+"
                );
                Matcher m = p.matcher((registerIdInput).getText().toString());

                if ( !m.matches()){
                    Toast.makeText(RegisterActivity.this, "Email형식으로 입력하세요", Toast.LENGTH_SHORT).show();
                    checkValidation = false;
                } else {
                    checkValidation = true;
                }

                String pwPattern = "^(?=.*\\d)(?=.*[~`!@#$%\\^&*()-])(?=.*[a-z])(?=.*[A-Z]).{9,12}$";
                Matcher matcher = Pattern.compile(pwPattern).matcher(registerPw);

                if(!matcher.matches()){
                    Toast.makeText(RegisterActivity.this,  "영어 대소, 숫자, 특수문자 조합 9~12자리 형식으로 입력해주세요.", Toast.LENGTH_SHORT).show();
                    checkValidation = false;
                } else {
                    checkValidation = true;
                }

                if(checkValidation == true) {
                    //쉐어드에 저장된 값을 가져온다.
                    SharedPreferences sp = getSharedPreferences("shared", MODE_PRIVATE);
                    String strUser = sp.getString("userdata", "");

                    //변환
                    gson = new GsonBuilder().create();
                    Type listType = new TypeToken<ArrayList<UserData>>() {
                    }.getType();

                    List<UserData> datas;
                    if (strUser.equals("")) {
                        datas = new ArrayList<UserData>();
                    } else {
                        datas = gson.fromJson(strUser, listType);
                    }

                    //하나의 객체를 만들어야해.
                    UserData userData = new UserData(registerId, registerPw);

                    //Contact를 자료형으로 받는 list를 만든다.
                    datas.add(userData);

                    for (UserData data : datas) {
                        Log.d("PRINT", "" + data.getuId());
                        Log.d("PRINT", "" + data.getuPassword());
                    }

                    //Json으로 변환
                    strUser = gson.toJson(datas, listType);

                    //쉐어드에 자료 저장하기.
                    SharedPreferences.Editor editor = sp.edit();
                    editor.putString("userdata", strUser);
                    //editor.clear();
                    editor.commit();

                    finish();
                }
            }
        });
    }


}
