package com.example.emotiondiary.Adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.util.Log;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.emotiondiary.ListVO.MemoData;
import com.example.emotiondiary.MemoWriteActivity;
import com.example.emotiondiary.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.CustomViewHolder> {

    private ArrayList<MemoData> arrayList;
    private boolean multiSelect = false;
    private ArrayList<String> selectedItems = new ArrayList<>();
    private Context mContext;

    private Gson gson;
    List<MemoData> datas;
    List<MemoData> datasUser = new ArrayList<>();
    ;
    String strId;
    String id_real;

    private ValueEventListener postListener;
    private ChildEventListener childEventListener;

    //생성자
    //Alt+insert로 만듬
    public RecyclerViewAdapter(ArrayList<MemoData> arrayList) {      //String[] nameSet, String[] contentSet, int[] imgSet
        this.arrayList = arrayList;
    }

    //액션모드를 만들겠습니다.
    private ActionMode.Callback actionModeCallbacks = new ActionMode.Callback() {

        @Override
        //Action mode가 생성됐을때 콜백 함수. 여기서 menu를 추가할 수 있어.(ex. delete)
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            multiSelect = true;
            menu.add("Delete");
            return true;
        }

        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            return false;
        }

        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {

            for (String intItem : selectedItems) {
                remove(intItem);
            }

            mode.finish();
            return true;
        }

        @Override
        public void onDestroyActionMode(ActionMode mode) {

//            for (MemoData data : datas) {
//
//                if (data.getUser().equals(strId)) {
//                    datasUser.add(data);
//                }
//
//            }
//
//            multiSelect = false;
//            selectedItems.clear();
//
//            arrayList.clear();
//            arrayList.addAll(datasUser);
//            notifyDataSetChanged();



//
//            Intent intent = new Intent(mContext, MemoListActivity.class);
//            mContext.startActivity(intent);
        }
    };

    //recyclerView에 들어갈 뷰 홀더, 그리고 그 뷰 홀더에 들아갈 아이템들을 지정 / '이게 맵핑 전에 필요한 과정이 아닐까?'
    public class CustomViewHolder extends RecyclerView.ViewHolder {

        //protected ImageView iv_profile;
        protected TextView tv_name;
        protected TextView time;
        protected LinearLayout linearLayout;
        protected ImageView img_attach;
        protected TextView tv_emotion;

        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);
            //this.iv_profile = (ImageView) itemView.findViewById(R.id.iv_profile);
            this.tv_name = (TextView) itemView.findViewById(R.id.tv_name);
            this.time = (TextView) itemView.findViewById(R.id.time);
            this.linearLayout = (LinearLayout) itemView.findViewById(R.id.linearLayoutItem);
            this.img_attach = itemView.findViewById(R.id.img_attach);
            this.tv_emotion = itemView.findViewById(R.id.tv_emotion);

        }

        void selectItem(String item) {
            if (multiSelect) {
                if (selectedItems.contains(item)) {
                    selectedItems.remove(item);
                    linearLayout.setBackgroundColor(Color.WHITE);
                } else {
                    selectedItems.add(item);
                    linearLayout.setBackgroundColor(Color.LTGRAY);
                }
            }
        }

        void update(final String value) {
            //textView.setText(value + "");
            if (selectedItems.contains(value)) {
                linearLayout.setBackgroundColor(Color.LTGRAY);
            } else {
                linearLayout.setBackgroundColor(Color.WHITE);
            }
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    ((AppCompatActivity) v.getContext()).startActionMode((ActionMode.Callback) actionModeCallbacks);
                    selectItem(value);
                    return true;
                }
            });
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (multiSelect == true) {
                        selectItem(value);
                    } else {
                        //다이얼이 아닌 activity를 띄울거에요.
                        final int position = getAdapterPosition();
                        final Context vContext = v.getContext();

                        id_real = arrayList.get(position).getId();

                        Intent intent = new Intent(vContext, MemoWriteActivity.class);
                        intent.putExtra("POSITION", position);
                        intent.putExtra("ID",id_real);

                        Log.d("데이터", "넘어간다");
                        vContext.startActivity(intent);


//                        //position값 대체
//                        final int position = getAdapterPosition();
//
//                        //다이얼로그 띄우기
//                        final Context mContext = v.getContext();
//                        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
//                        View view = LayoutInflater.from(mContext).inflate(R.layout.memo_dialog,null,false);
//                        builder.setView(view);
//                        //데이터를 담을 틀들을 가져오자.
//                        final EditText editTextTitle = (EditText)view.findViewById(R.id.titleDialog);
//                        final EditText editTextContent = (EditText)view.findViewById(R.id.contentDialog);
//                        final EditText editTextEmotion = (EditText)view.findViewById(R.id.emtionSelected);
//                        final Button ButtonSubmit = (Button)view.findViewById(R.id.modification);
//
//                        //감정값 바꿔주기(for #이미지 수정 기능)
//                        int emotionValue = arrayList.get(position).getIv_profile();
//                        String emotionValueStr = Integer.toString(emotionValue);
//                        String emotionValueFirst = "";
//                        if(emotionValueStr.equals("2131230827")){ //2131230827 == ic_happy_jpg
//                            emotionValueFirst = "기쁨";
//                        } else if(emotionValueStr.equals("2131230828")){    //2131230828 == ic_hurt_jpg
//                            emotionValueFirst = "상처";
//                        } else if(emotionValueStr.equals("2131230834")){    //2131230834 == ic_sad_jpg
//                            emotionValueFirst = "슬픔";
//                        }
//
//                        //데이터 가져와서 넣기
//                        editTextTitle.setText(arrayList.get(position).getTv_name());
//                        editTextContent.setText(arrayList.get(position).getTv_content());
//                        editTextEmotion.setText(emotionValueFirst);
//
//                        final AlertDialog dialog = builder.create();
//
//                        //'수정하기' 버튼 클릭시
//                        ButtonSubmit.setOnClickListener(new View.OnClickListener() {
//                            @Override
//                            public void onClick(View v) {
//                                //수정된 데이터 받아오기 (제목, 날짜)
//                                String titleModified = editTextTitle.getText().toString();
//                                String contentModified = editTextContent.getText().toString();
//                                int id = arrayList.get(position).getId();
//                                //(for #이미지 수정 기능)
//                                String emotionModified = editTextEmotion.getText().toString();
//
//                                //이미지값 변경(for #이미지 수정 기능)
//                                int imageSelected = 0;
//                                if(emotionModified.equals("기쁨")){
//                                    imageSelected = 2131230827;
//                                } else if(emotionModified.equals("상처")){
//                                    imageSelected = 2131230828;
//                                } else if(emotionModified.equals("슬픔")){
//                                    imageSelected = 2131230834;
//                                }
//
//                                //변경된 시간
//                                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
//                                String timeModified = format.format(System.currentTimeMillis());
//
//                                //배열 수정(어댑터에서도 가능하다!)
//                                MemoData dataModified = new MemoData(imageSelected, titleModified, contentModified, timeModified, id);
//                                arrayList.set(position,dataModified);
//                                notifyItemChanged(position);
//
//                                //다이얼로그 해제
//                                dialog.dismiss();
//                            }
//                        });
//
//                        dialog.show();
                    }
                }
            });

        }
    }

    @NonNull
    //액티비티의 onCreate와 비슷
    //뷰 홀더를 생성 (이게 item.xml을 listview에 넣어주는 것.) *여기서 큰 틀 잡고
    //RecyclerView에 들어갈 뷰 홀더를 할당(create)하는 함수 & extend의 <>안에 들어가는 타입을 따른다.  RecyclerView.Adapter<RecyclerViewAdapter.CustomViewHolder>
    @Override
    public RecyclerViewAdapter.CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        mContext = parent.getContext();
        //item_list.xml 레이아웃과 맵핑한다.(??)
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list, parent, false);
        //customViewHolder의 타입으로 뷰 홀더를 저장한다.
        CustomViewHolder holder = new CustomViewHolder(view);

        return holder;
    }

    //실제로 추가될때 생명주기
    //실제 각 뷰 홀더에 데이터를 연결해주는 함수 (이게 data를 넣어주는 것) *여기서 세부 내용 넣어주는 것.
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onBindViewHolder(@NonNull final RecyclerViewAdapter.CustomViewHolder holder, final int position) {

        //이미지 bitmap형식으로 전환
        String imgPath = arrayList.get(position).getImgPath();
        Bitmap bitmap = BitmapFactory.decodeFile(imgPath);

        //홍드로이드
        //holder.iv_profile.setImageResource(arrayList.get(position).getIv_profile());
        holder.tv_emotion.setText(arrayList.get(position).getTv_emotion());
        holder.tv_name.setText(arrayList.get(position).getTv_name());
        holder.time.setText(arrayList.get(position).getTime());
//        if (imgPath.equals("null")) {
//            //holder.img_attach.setBackgroundResource(R.drawable.default_img);
//        } else {
            holder.img_attach.setImageBitmap(bitmap);
//        }

        holder.update(arrayList.get(position).getId());

        //쉐어드에서 데이터 받아오기
        SharedPreferences sp = mContext.getSharedPreferences("shared", MODE_PRIVATE);
        strId = sp.getString("checkId", "");


//        holder.itemView.setTag(position);
//
//        //아이템 클릭시 이벤트 처리
//        holder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //다이얼로그 띄우기
//                final Context mContext = v.getContext();
//                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
//                View view = LayoutInflater.from(mContext).inflate(R.layout.memo_dialog,null,false);
//                builder.setView(view);
//                //데이터를 담을 틀들을 가져오자.
//                final EditText editTextTitle = (EditText)view.findViewById(R.id.titleDialog);
//                final EditText editTextContent = (EditText)view.findViewById(R.id.contentDialog);
//                final EditText editTextEmotion = (EditText)view.findViewById(R.id.emtionSelected);
//                final Button ButtonSubmit = (Button)view.findViewById(R.id.modification);
//
//                //감정값 바꿔주기(for #이미지 수정 기능)
//                int emotionValue = arrayList.get(position).getIv_profile();
//                String emotionValueStr = Integer.toString(emotionValue);
//                String emotionValueFirst = "";
//                if(emotionValueStr.equals("2131230827")){ //2131230827 == ic_happy_jpg
//                    emotionValueFirst = "기쁨";
//                } else if(emotionValueStr.equals("2131230828")){    //2131230828 == ic_hurt_jpg
//                    emotionValueFirst = "상처";
//                } else if(emotionValueStr.equals("2131230834")){    //2131230834 == ic_sad_jpg
//                    emotionValueFirst = "슬픔";
//                }
//
//                //데이터 가져와서 넣기
//                editTextTitle.setText(arrayList.get(position).getTv_name());
//                editTextContent.setText(arrayList.get(position).getTv_content());
//                editTextEmotion.setText(emotionValueFirst);
//
//                final AlertDialog dialog = builder.create();
//
//                //'수정하기' 버튼 클릭시
//                ButtonSubmit.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        //수정된 데이터 받아오기 (제목, 날짜)
//                        String titleModified = editTextTitle.getText().toString();
//                        String contentModified = editTextContent.getText().toString();
//                        //(for #이미지 수정 기능)
//                        String emotionModified = editTextEmotion.getText().toString();
//
//                        //이미지값 변경(for #이미지 수정 기능)
//                        int imageSelected = 0;
//                        if(emotionModified.equals("기쁨")){
//                            imageSelected = 2131230827;
//                        } else if(emotionModified.equals("상처")){
//                            imageSelected = 2131230828;
//                        } else if(emotionModified.equals("슬픔")){
//                            imageSelected = 2131230834;
//                        }
//
//                        //변경된 시간
//                        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
//                        String timeModified = format.format(System.currentTimeMillis());
//
//                        //아이템에 해당하는 사진 가져오기
////                        int image = arrayList.get(position).getIv_profile();
//
//                        //배열 수정(어댑터에서도 가능하다!)
//                        MemoData dataModified = new MemoData(imageSelected, titleModified, contentModified, timeModified);
//                        arrayList.set(position,dataModified);
//                        notifyItemChanged(position);
//
//                        //다이얼로그 해제
//                        dialog.dismiss();
//                    }
//                });
//
//                dialog.show();
//
//            }
//        });

        //삭제 기능
//        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
//            @Override
//            public boolean onLongClick(View v) {
//                remove(holder.getAdapterPosition());
//
//                return true;
//            }
//        });

    }

    //RecyclerView 안에 들어갈 뷰 홀더의 갯수
    @Override
    public int getItemCount() {
        //return nameSet.length > imgSet.length ? nameSet.length : imgSet.length;
        return (null != arrayList ? arrayList.size() : 0);  //왜 해주는거지?  null체크라는 것인데 List는 null로 체크하면 안 된단다. 왜?
    }

    //remove 함수 (arraylist를 활용한)
//    public void remove(int position){
//        try{
//            arrayList.remove(position);
//            notifyItemRemoved(position);    //새로고침 기능
//        }catch(IndexOutOfBoundsException ex){
//            ex.printStackTrace();
//        }
//    }

    public void remove(String id) {

        DatabaseReference db = FirebaseDatabase.getInstance().getReference();

        db.child("memodata").orderByChild("id").equalTo(id).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot childSnapshot: dataSnapshot.getChildren()){
                    childSnapshot.getRef().removeValue();       //여기서 getRef()가 해당 자료의 폴더 이름인듯.
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });




    }


}
