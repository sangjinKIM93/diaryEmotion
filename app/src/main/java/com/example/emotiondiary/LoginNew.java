package com.example.emotiondiary;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.emotiondiary.ListVO.MyInfo;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class LoginNew extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private EditText et_email, et_password;
    private Button btn_register, btn_login;
    private Gson gson;
    String uId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_new);

        mAuth = FirebaseAuth.getInstance();
        et_email = findViewById(R.id.et_email);
        et_password = findViewById(R.id.et_password);

        btn_register= findViewById(R.id.btn_register);
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginNew.this, RegisterNew.class);
                startActivity(intent);
            }
        });

        btn_login = findViewById(R.id.btn_login);
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginUser(et_email.getText().toString(), et_password.getText().toString());
            }
        });
    }

    private void loginUser(final String email, final String password){
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (!task.isSuccessful()) {
                            Toast.makeText(LoginNew.this, "로그인 실패", Toast.LENGTH_SHORT).show();

                        } else {
                            Toast.makeText(LoginNew.this, "로그인 성공", Toast.LENGTH_SHORT).show();

                            //shared에 저장.
                            SharedPreferences sp = getSharedPreferences("shared", MODE_PRIVATE);
                            MyInfo myInfo = new MyInfo();
                            myInfo.setName(email);
                            myInfo.setuId(email);
                            myInfo.setPhotoUrl(null);

                            gson = new GsonBuilder().create();
                            String userStr = gson.toJson(myInfo);

                            SharedPreferences.Editor editor = sp.edit();
                            editor.putString("listUserdata", userStr);
                            editor.putString("checkId", email);
                            editor.commit();

                            Intent intent = new Intent(LoginNew.this, MainActivity.class);
                            startActivity(intent);
                        }
                    }
                });
    }
}


