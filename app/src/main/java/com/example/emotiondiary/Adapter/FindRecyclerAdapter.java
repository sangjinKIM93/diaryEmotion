package com.example.emotiondiary.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.emotiondiary.ListVO.MemoData;
import com.example.emotiondiary.OtherMemoActivity;
import com.example.emotiondiary.R;

import java.util.ArrayList;

public class FindRecyclerAdapter extends RecyclerView.Adapter<FindRecyclerAdapter.CustomViewHolder> {

    private ArrayList<MemoData> arrayListFind;


    //생성자. 생성시 arrayList를 받아온다. 이 정보를 바탕으로 조작하는거야.
    public FindRecyclerAdapter(ArrayList<MemoData> arrayListFind) {
        this.arrayListFind = arrayListFind;
    }


    //작업할 view들을 가져옴. customview를 쓰기 위해서는 선언해줘야함.
    public class CustomViewHolder extends RecyclerView.ViewHolder {

        protected TextView titleFind, contentFind, timeFind, userIdFind;
        protected ImageView iv_find;


        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);
            this.titleFind = (TextView) itemView.findViewById(R.id.findTitle);
            this.contentFind = (TextView) itemView.findViewById(R.id.findContent);
            this.timeFind = (TextView) itemView.findViewById(R.id.findTime);
            this.userIdFind = (TextView) itemView.findViewById(R.id.findUserId);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    int position = getAdapterPosition();
                    String title = arrayListFind.get(position).getTv_name();
                    String content = arrayListFind.get(position).getTv_content();
                    String time = arrayListFind.get(position).getTime();
                    String user = arrayListFind.get(position).getUser();
                    String link = arrayListFind.get(position).getLink();
                    //화면 넘김과 동시에 데이터 넘겨주기
                    Context context = v.getContext();   //그 뷰의 context, this랑 같은거.
                    Intent intent = new Intent(context, OtherMemoActivity.class);
                    intent.putExtra("TITLE", title);
                    intent.putExtra("CONTENT", content);
                    intent.putExtra("TIME", time);
                    intent.putExtra("USER", user);
                    intent.putExtra("LINK", link);
                    context.startActivity(intent);
                }
            });


        }
    }

    //뷰 홀더를 할당하는 함수. + 내가 만든 레이아웃(row.xml)과 연결해줌.
    @NonNull
    @Override
    public FindRecyclerAdapter.CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_find, parent, false);
        CustomViewHolder holder = new CustomViewHolder(view);

        return holder;
    }

    //각 뷰홀더에 데이터를 연결(즉, arraylist의 값을 position에 맞게 넣어줌)
    @Override
    public void onBindViewHolder(@NonNull FindRecyclerAdapter.CustomViewHolder holder, final int position) {



        holder.titleFind.setText(arrayListFind.get(position).getTv_name());
        holder.contentFind.setText(arrayListFind.get(position).getTv_content());
        holder.timeFind.setText(arrayListFind.get(position).getTime());
        holder.userIdFind.setText(arrayListFind.get(position).getUser());

//        //이미지 bitmap형식으로 전환
//        String imgPath = arrayListFind.get(position).getImgPath();
//        Bitmap bitmap = BitmapFactory.decodeFile(imgPath);
//        if (imgPath.equals("null")) {
//            holder.iv_find.setBackgroundResource(R.drawable.default_img);
//        } else {
//            holder.iv_find.setImageBitmap(bitmap);
//        }


    }

    //item이 몇 개인지 판단. 이 갯수만큼 위에 함수들이 호출될거야.
    @Override
    public int getItemCount() {
        return (null != arrayListFind ? arrayListFind.size() : 0);
    }


}
