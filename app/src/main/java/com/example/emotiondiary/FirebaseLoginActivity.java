package com.example.emotiondiary;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.emotiondiary.ListVO.MyInfo;
import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.IdpResponse;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.Arrays;
import java.util.List;

public class FirebaseLoginActivity extends AppCompatActivity {

    private static int RC_SIGN_IN = 1000;
    private Gson gson;
    String uId;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.firsbase_login);

        //로그인을 위한 기본 셋팅
        // Choose authentication providers : 어떤 로그인을 지원할지. ex)구글, 페이스북
        List<AuthUI.IdpConfig> providers = Arrays.asList(
                new AuthUI.IdpConfig.EmailBuilder().build(),
                new AuthUI.IdpConfig.GoogleBuilder().build());

        // Create and launch sign-in intent : 기본 화면 만들기
        startActivityForResult(
                AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setIsSmartLockEnabled(false)       //ex)지문,홍채 인식
                        .setAvailableProviders(providers)
                        .setTheme(R.style.GreenTheme)       //테마 변경
                        .setLogo(R.drawable.main_icon)      //로고 변경
                        .build(),
                RC_SIGN_IN);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //로그인 정보 받아와서 처리
        if (requestCode == RC_SIGN_IN) {
            IdpResponse response = IdpResponse.fromResultIntent(data);

            if (resultCode == RESULT_OK) {
                // Successfully signed in
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                //파이어베이스가 제공해주는 user정보를 받아오기
                uId = user.getUid();
                String name = user.getDisplayName();
                Uri photoUrl = user.getPhotoUrl();

                //shared에 저장.(이후, MyInfo에서 활용)
                SharedPreferences sp = getSharedPreferences("shared", MODE_PRIVATE);

                MyInfo myInfo = new MyInfo();
                myInfo.setName(name);
                myInfo.setuId(uId);
                myInfo.setPhotoUrl(photoUrl);

                gson = new GsonBuilder().create();
                String userStr = gson.toJson(myInfo);

                SharedPreferences.Editor editor = sp.edit();
                editor.putString("listUserdata", userStr);
                editor.putString("checkId", name);
                editor.commit();

                Intent i = new Intent(this, MainActivity.class);
                startActivity(i);
                finish();
                // ...
            } else {
                Toast.makeText(this, "로그인 실패, 다시 시도해주세요.", Toast.LENGTH_LONG).show();
            }
        }
    }
}
