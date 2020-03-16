package com.example.emotiondiary;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.content.CursorLoader;

import com.example.emotiondiary.ListVO.MyInfo;
import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;

public class MyInfoActivity extends AppCompatActivity {

    private static final int GALLERY_CODE = 10;
    ImageView account_back, info_image;
    TextView logout, withdrawal, info_name;
    Gson gson;

    private FirebaseStorage storage;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_info);

        storage = FirebaseStorage.getInstance();

        info_name = findViewById(R.id.info_name);
        info_image = findViewById(R.id.info_image);

        //firebase 로그인에서 저장한 데이터 가져오기
        SharedPreferences sp = getSharedPreferences("shared", MODE_PRIVATE);
        String strUser = sp.getString("listUserdata", "");

        gson = new GsonBuilder().create();
        MyInfo myInfo = gson.fromJson(strUser, MyInfo.class);

        String name = myInfo.getName();
        Uri image = myInfo.getPhotoUrl();

        info_name.setText(name);

//        Bitmap bitmap = BitmapFactory.decodeFile(String.valueOf(image));
//        info_image.setImageBitmap(bitmap);

        /*권한*/
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(new String[] {Manifest.permission.READ_EXTERNAL_STORAGE},0);
        }

        info_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(Intent.ACTION_PICK);
                intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
                startActivityForResult(intent, GALLERY_CODE);
            }
        });

        //로그아웃, 탈퇴, 뒤로가기 버튼 클릭이벤트
        logout = findViewById(R.id.info_logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signoutAccount();
            }
        });

        withdrawal = findViewById(R.id.info_withdraw);
        withdrawal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteAccount();
            }
        });

        account_back = findViewById(R.id.account_back);
        account_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }

    private void signoutAccount(){
        AuthUI.getInstance()
                .signOut(MyInfoActivity.this)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(MyInfoActivity.this, "로그아웃 하셨습니다", Toast.LENGTH_SHORT).show();
                        moveToMain();
                    }
                });
    }

    private void deleteAccount(){
        AuthUI.getInstance()
                .delete(MyInfoActivity.this)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(MyInfoActivity.this, "탈퇴 하셨습니다", Toast.LENGTH_SHORT).show();
                        moveToMain();
                    }
                });
    }


    private void moveToMain(){
        Intent i = new Intent(MyInfoActivity.this, FirebaseLoginActivity.class);
        startActivity(i);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode == GALLERY_CODE){
            String path = getPath(data.getData());
            Bitmap bitmap = BitmapFactory.decodeFile(path);
            info_image.setImageBitmap(bitmap);
            storageUpload(path);
        }
    }
    public String getPath(Uri uri){
        String [] proj = {MediaStore.Images.Media.DATA};
        CursorLoader cursorLoader = new CursorLoader(this, uri,proj, null,null,null);
        Cursor cursor = cursorLoader.loadInBackground();
        int index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(index);
    }

    private void storageUpload(String path){

        StorageReference storageRef = storage.getReference();

        Uri file = Uri.fromFile(new File(path));
        StorageReference riversRef = storageRef.child("images/"+file.getLastPathSegment());
        UploadTask uploadTask = riversRef.putFile(file);



        // Register observers to listen for when the download is done or if it fails
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {

            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Uri downlodUri = taskSnapshot.getUploadSessionUri();
                String imgFirebasePath = downlodUri.toString();

                SharedPreferences sp = getSharedPreferences("shared", MODE_PRIVATE);
                SharedPreferences.Editor editor = sp.edit();
                editor.putString("infoImage", imgFirebasePath);
                editor.commit();

            }
        });

    }
}
