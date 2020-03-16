package com.example.emotiondiary;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.example.emotiondiary.ListVO.MemoData;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MemoWriteActivity extends AppCompatActivity {

    EditText et, et2;
    int position = -1;

    String userId;

    CheckBox checkSecret;
    private boolean secretChecked;

    private ImageView imageView;
    private static final int REQUEST_CODE = 0;

    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    String imagePath;

    Button angryBtn, sadBtn, anxiousBtn, hurtBtn, embarrassedBtn, happyBtn;
    String RealEmotion = " ";
    TextView tv_emotion_Write, tv_link;

    Uri uri = null;
    String uriStr = "LINK";
    String newTitle;

    List<MemoData> datas;

    String id;

    private ValueEventListener postListener;
    String link_pre;
    String key;

    DatabaseReference db;
    String format_time, title, content, id_insert;
    private FirebaseStorage storage;

    String storageImg;

    public MemoWriteActivity() {
    }

    //동적인 권한 획득. 갤러리 접근을 위해서 추가적으로 해줘야해.
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_EXTERNAL_STORAGE:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Permission Granted
                    startStorage();
                } else {
                    // Permission Denied
                    Toast.makeText(MemoWriteActivity.this, "READ_STORAGE Denied", Toast.LENGTH_SHORT)
                            .show();
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.memowrite);

        SharedPreferences sp = getSharedPreferences("shared", MODE_PRIVATE);

        EditText scrollEdit = findViewById(R.id.content);
        scrollEdit.setMovementMethod(new ScrollingMovementMethod());

        angryBtn = findViewById(R.id.angryBtn);
        sadBtn = findViewById(R.id.sadBtn);
        anxiousBtn = findViewById(R.id.anxiousBtn);
        hurtBtn = findViewById(R.id.hurtBtn);
        embarrassedBtn = findViewById(R.id.embarrassedBtn);
        happyBtn = findViewById(R.id.happyBtn);

        //다이얼로그 띄운 후 선택 값 받기
        angryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MemoWriteActivity.this, EmotionPopupActivity.class);
                startActivityForResult(intent, 99);
            }
        });
        sadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MemoWriteActivity.this, SadPopupActivity.class);
                startActivityForResult(intent, 11);
            }
        });
        anxiousBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MemoWriteActivity.this, AnxiousPopupActivity.class);
                startActivityForResult(intent, 22);
            }
        });
        hurtBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MemoWriteActivity.this, HurtPopupActivity.class);
                startActivityForResult(intent, 33);
            }
        });
        embarrassedBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MemoWriteActivity.this, EmbarrassedPopupActivity.class);
                startActivityForResult(intent, 44);
            }
        });
        happyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MemoWriteActivity.this, HappyPopupActivity.class);
                startActivityForResult(intent, 55);
            }
        });


        //imageview 클릭하면 갤러리로 인텐트 넘겨주기.
        imageView = findViewById(R.id.iv_attachment);
        imageView.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                startStorageWrapper();
            }
        });

        storage = FirebaseStorage.getInstance();

        et = (EditText) findViewById(R.id.edit);
        et2 = (EditText) findViewById(R.id.content);
        checkSecret = findViewById(R.id.checkBoxSecret);
        tv_emotion_Write = findViewById(R.id.tv_emotion_write);
        tv_link = findViewById(R.id.link);

//        //Spinner 객체 생성
//        Spinner spinner = (Spinner) findViewById(R.id.spinner_field);
//        //생성한 field_xml의 item을 String 배열로 가져오기
//        emotion = getResources().getStringArray(R.array.spinnerArray);
//
//        //spinner_item.xml과 emotion 인자로 어댑터(접속, 연결) 생성
//        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.spinner_item, emotion);
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        spinner.setAdapter(adapter);
//
//        //Spinner 이벤트 리스너
//        spinner.setOnItemSelectedListener(this);

        Intent intent = getIntent();
        if (intent.hasExtra("POSITION")) {
            //얘는 adapter에서 보내주는 친구
            position = intent.getIntExtra("POSITION", -1);
            id = intent.getStringExtra("ID");
            //userId = intent.getExtras().getString("CheckID");
        } else if(intent.hasExtra("URI")){
            uri = intent.getParcelableExtra("URI");
            newTitle = intent.getStringExtra("TITLE");
        }

        String strUserId = sp.getString("checkId", "");
        userId = strUserId;

        if(uri != null){
            tv_link.setText(uri.toString());
            et2.setText("["+newTitle+ "] 을 읽고....");
        }

        //수정시에는 쉐어드에서 데이터 받아와서 보여주기.
        if (position != -1) {
            //데이터를 받아와서 음... position이 아니라 해당 id값을 받아오는거야.
            //그래서 id값을 비교해서 해당 아이디면 그 자료의 값들을 가져오는


            db = FirebaseDatabase.getInstance().getReference();

            db.child("memodata").addValueEventListener(postListener = new ValueEventListener() {       //왜 여기서 멈추는거지?
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for(DataSnapshot data : dataSnapshot.getChildren()){
                        MemoData memoData = data.getValue(MemoData.class);
                        if(memoData.getId().equals(id)){
                            key = data.getKey();
                            //그외 각종 초기값 설정
                            et.setText(memoData.getTv_name());
                            et2.setText(memoData.getTv_content());
                            tv_emotion_Write.setText(memoData.getTv_emotion());

                            checkSecret.setChecked(memoData.getChecked());

                            RealEmotion = memoData.getTv_emotion();

                            //첨부파일 초기값
                            imagePath = memoData.getImgPath();
                            Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
                            imageView.setImageBitmap(bitmap);

                            link_pre = memoData.getLink();
                            tv_link.setText(link_pre);

                        }

                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

//            Log.d("WriteActivitIF", "실행");
//            String strMemodata = sp.getString("memodata", "");
//
//            //변환(JsonArray 이용)
//            ArrayList<MemoData> datas = new ArrayList<>();
//            try {
//                JSONArray jarray = new JSONArray(strMemodata);
//                for (int i = 0; i < jarray.length(); i++) {
//                    JSONObject jObject = jarray.getJSONObject(i);
//                    String JSONuser = jObject.getString("user");
//                    Log.d("write JSONuser ID", JSONuser);
//                    if (JSONuser.equals(userId)) {
//                        String JSONid = jObject.getString("id");
//                        String JSONemotion = jObject.getString("emotion");
//                        String JSONtitle = jObject.getString("tv_name");
//                        String JSONcontent = jObject.getString("tv_content");
//                        String JSONtime = jObject.getString("time");
//                        Boolean JSONcheck = jObject.getBoolean("checkSecret");
//                        String JSONimgPath = jObject.getString("imgPath");
//                        String JSONlink = jObject.getString("link");
//
//                        MemoData JSONmemdata = new MemoData();
//                        JSONmemdata.setChecked(JSONcheck);
//                        JSONmemdata.setId(JSONid);
//                        JSONmemdata.setImgPath(JSONimgPath);
//                        JSONmemdata.setLink(JSONlink);
//                        JSONmemdata.setTime(JSONtime);
//                        JSONmemdata.setTv_content(JSONcontent);
//                        JSONmemdata.setTv_emotion(JSONemotion);
//                        JSONmemdata.setTv_name(JSONtitle);
//                        JSONmemdata.setUser(JSONuser);
//
//                        datas.add(JSONmemdata);
//                    }
//                }
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }


//            //그외 각종 초기값 설정
//            et.setText(datas.get(position).getTv_name());
//            et2.setText(datas.get(position).getTv_content());
//            tv_emotion_Write.setText(datas.get(position).getTv_emotion());
//
//            checkSecret.setChecked(datas.get(position).getChecked());
//
//            RealEmotion = datas.get(position).getTv_emotion();
//
//            //첨부파일 초기값
//            Bitmap bitmap = BitmapFactory.decodeFile(datas.get(position).getImgPath());
//            imageView.setImageBitmap(bitmap);
//
//            tv_link.setText(datas.get(position).getLink());

        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void startStorageWrapper() {
        int permission = ActivityCompat.checkSelfPermission(MemoWriteActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            requestPermissions(PERMISSIONS_STORAGE, REQUEST_EXTERNAL_STORAGE);
            return;
        }
        startStorage();
    }

    public void startStorage() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");      //암시적 인텐트의 type설정해준거. image인 얘들만 받겠다.
        startActivityForResult(intent, REQUEST_CODE);
    }

    //이미지 데이터 받아오기
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE) {
            if (resultCode == RESULT_OK) {

                sendPicture(data.getData());

            } else if (resultCode == RESULT_CANCELED) {
                Toast.makeText(this, "사진 선택 취소", Toast.LENGTH_LONG).show();
            }

        }else if (requestCode == 99){
            if(resultCode == 999){
                RealEmotion = data.getStringExtra("emotion");
                Log.d("RealEmotion값을 받을 수 있는가?", RealEmotion);
            }
        } else if (requestCode == 11){
            if(resultCode == 111){
                RealEmotion = data.getStringExtra("emotion");
                Log.d("RealEmotion값을 받을 수 있는가?", RealEmotion);
            }
        } else if (requestCode == 22){
            if(resultCode == 222){
                RealEmotion = data.getStringExtra("emotion");
                Log.d("RealEmotion값을 받을 수 있는가?", RealEmotion);
            }
        } else if (requestCode == 33){
            if(resultCode == 333){
                RealEmotion = data.getStringExtra("emotion");
                Log.d("RealEmotion값을 받을 수 있는가?", RealEmotion);
            }
        } else if (requestCode == 44){
            if(resultCode == 444){
                RealEmotion = data.getStringExtra("emotion");
                Log.d("RealEmotion값을 받을 수 있는가?", RealEmotion);
            }
        } else if (requestCode == 55){
            if(resultCode == 555){
                RealEmotion = data.getStringExtra("emotion");
                Log.d("RealEmotion값을 받을 수 있는가?", RealEmotion);
            }
        }

        tv_emotion_Write.setText(RealEmotion);
    }

    private void sendPicture(Uri imgUri) {

        imagePath = getRealPathFromURI(imgUri);

        Log.d("imagePath의 절대경로", imagePath);

        Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
        imageView.setImageBitmap(bitmap);

    }

    private String getRealPathFromURI(Uri contentUri) {

        int column_index = 0;
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = getContentResolver().query(contentUri, proj, null, null, null);
        if (cursor.moveToFirst()) {
            column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);

        }
        return cursor.getString(column_index);
    }

//    //Spinner 이벤트 리스너 구체화
//    @Override
//    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//        emotionSelected = emotion[position];
//    }
//
//    //spinner에서 항목 선택 안 했을때 처리
//    @Override
//    public void onNothingSelected(AdapterView<?> parent) {
//    }


    public void saveBtnClicked(View view) {

//        int imgSelected = 0;
//
//        if (emotionSelected.equals("기쁨")) {
//            imgSelected = R.drawable.ic_happy;
//        } else if (emotionSelected.equals("상처")) {
//            imgSelected = R.drawable.ic_hurt;
//        } else if (emotionSelected.equals("슬픔")) {
//            imgSelected = R.drawable.ic_sad;
//        }

        ///수정을 위한 클릭일때
        if (position != -1) {

            //각종 데이터 초기화
            initializationMemo();

            Map<String, Object> memoUpdate = new HashMap<>();
            memoUpdate.put("tv_name", title);
            memoUpdate.put("tv_content", content);
            memoUpdate.put("tv_emotion", RealEmotion);
            memoUpdate.put("time", format_time);
            memoUpdate.put("imgPath", imagePath);
            memoUpdate.put("checked", secretChecked);

            db.child("memodata").child(key).updateChildren(memoUpdate);  //자. 이제 여기를 push가 아닌 update로 바꿔주면 됨.
            finish();

        } else {

            //각종 데이터 초기화
            initializationMemo();
            datas = new ArrayList<>();

            MemoData memodata;
            if (uri == null) {
                memodata = new MemoData();
                memodata.setChecked(secretChecked);
                memodata.setId(id_insert);
                memodata.setImgPath(imagePath);
                memodata.setLink(uriStr);
                memodata.setTime(format_time);
                memodata.setTv_content(content);
                memodata.setTv_emotion(RealEmotion);
                memodata.setTv_name(title);
                memodata.setUser(userId);
            } else {
                memodata = new MemoData();
                memodata.setChecked(secretChecked);
                memodata.setId(id_insert);
                memodata.setImgPath(imagePath);
                memodata.setLink(uri.toString());
                memodata.setTime(format_time);
                memodata.setTv_content(content);
                memodata.setTv_emotion(RealEmotion);
                memodata.setTv_name(title);
                memodata.setUser(userId);
            }

            datas.add(memodata);

            if(db.child("memodata") == null){
                db.child("memodata").setValue(memodata);
            } else {
                db.child("memodata").push().setValue(memodata);
            }
            finish();

        }
    }

    private void initializationMemo() {

        //현재 시간 받아오기
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        format_time = format.format(System.currentTimeMillis());
        title = et.getText().toString();
        content = et2.getText().toString();

        db = FirebaseDatabase.getInstance().getReference();

        //각종 설정
        if (checkSecret.isChecked()) {
            secretChecked = true;
        } else {
            secretChecked = false;
        }

        id_insert = format_time+title+userId;

        if (imagePath == null) {
            imagePath = "null";
        }

        storageUpload(imagePath);   //사진 업로드
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
                storageImg = "null";
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Uri downlodUri = taskSnapshot.getUploadSessionUri();
                storageImg = downlodUri.toString();
            }
        });


    }

}

