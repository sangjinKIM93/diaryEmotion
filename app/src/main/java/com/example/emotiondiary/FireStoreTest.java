package com.example.emotiondiary;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class FireStoreTest extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.firestore_test);

        FirebaseFirestore db = FirebaseFirestore.getInstance();

//        ArrayList<NewsData> newsDataArray = new ArrayList<>();
//        NewsData newsdata = new NewsData();
//        newsdata.setTitle("해리포터");
//        newsdata.setContent("론과 해리가 만났다.");
//        newsdata.setLink("제이케이롤링");
//
//        NewsData newsdata2 = new NewsData();
//        newsdata2.setTitle("해리포터2");
//        newsdata2.setContent("론2과 해리2가 만났다.");
//        newsdata2.setLink("제이케이롤링2");
//
//        newsDataArray.add(newsdata);
//        newsDataArray.add(newsdata2);
//
//        //여러개의 자료 저장
//        for (NewsData data : newsDataArray){
//            db.collection("users").document().set(data);
//        }
        //이런식으로 하니까 덮어씌워지는구나...(이거는 document별로 저장할때 유용할듯) 다른 방법이 있을거야.

        Map<String, Object> newsData2 = new HashMap<>();
        newsData2.put("title", "해리포터2_주리");
        newsData2.put("content", Arrays.asList("론과 해리가 만났다2", "문을 부수고 나갔다.2", "차는 하늘을 날아다닌다.2"));
        newsData2.put("author", "제이케이롤링2");

        Map<String, Object> newsData = new HashMap<>();
        newsData.put("title", "마법사기단_주리");
        newsData.put("content", Arrays.asList("론과 해리가 만났다", "문을 부수고 나갔다.", "차는 하늘을 날아다닌다."));
        newsData.put("author", "제이케이롤링");
        newsData.put("objectExample", newsData2);   //이런식으로 객체를 저장할 수 있어. 나같은 경우는 각 memodata를 저렇게 객체로 저장할 수 있지.그런데 포인트는 쿼리가 가능하냐야.

        //커스텀 데이터 형식도 제공하네.
        //되돌리는건 City city = documentSnapshot.toObject(City.class);
        //만약 객체 연속으로 들어갔다면

        db.collection("users")
                .document("juri")
                .collection("memodata")
                .document()
                .set(newsData);

        db.collection("users")
                .document()
                .collection("memodata")
                .whereEqualTo("author", "제이케이롤링")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d("TAG", document.getId() + " => " + document.getData());
                                String content = document.get("title").toString();
                                Log.d("TAG_CONTENT", content);
                            }
                        } else {
                            Log.d("TAG", "Error getting documents.", task.getException());
                        }
                    }
                });

//        db.collection("users").document().delete().addOnCompleteListener(new OnCompleteListener<Void>() {
//            @Override
//            public void onComplete(@NonNull Task<Void> task) {
//                if (task.isSuccessful()) {
//                    Log.d("삭제 완료", "삭제 완료!!");
//                }
//            }
//        });


    }
}
