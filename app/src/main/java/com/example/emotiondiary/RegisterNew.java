package com.example.emotiondiary;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterNew extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private EditText et_email, et_password;
    private Button btn_register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_new);
        mAuth = FirebaseAuth.getInstance();
        et_email = findViewById(R.id.et_emailR);
        et_password = findViewById(R.id.et_passwordR);

        btn_register= findViewById(R.id.btn_registerR);
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createUser(et_email.getText().toString(), et_password.getText().toString());
            }
        });
    }

    private void createUser(String email, String password){
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (!task.isSuccessful()) {

                        } else {
                            Toast.makeText(RegisterNew.this, "회원가입 되었습니다.",Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    }
                });
    }

}
