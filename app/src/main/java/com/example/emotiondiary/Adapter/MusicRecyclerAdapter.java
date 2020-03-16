package com.example.emotiondiary.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
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
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.emotiondiary.ListVO.ListVO;
import com.example.emotiondiary.MusicPlayerActivity;
import com.example.emotiondiary.R;


import java.util.ArrayList;

public class MusicRecyclerAdapter extends RecyclerView.Adapter<MusicRecyclerAdapter.CustomViewHolder>{

    private ArrayList<ListVO> arraylistMusic;
    private boolean multiSelect = false;
    private ArrayList<Integer> selectedItems = new ArrayList<Integer>();

    //생성자. 생성시 arrayList를 받아온다. 이 정보를 바탕으로 조작하는거야.
    public MusicRecyclerAdapter(ArrayList<ListVO> arrayListMusic) {
        this.arraylistMusic = arrayListMusic;
    }

    //액션모드를 만들겠습니다.
    private ActionMode.Callback actionModeCallbacks = new ActionMode.Callback(){

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
            for(Integer intItem : selectedItems){
                remove(intItem);
            }
            mode.finish();
            return true;
        }

        @Override
        public void onDestroyActionMode(ActionMode mode) {
            multiSelect = false;
            selectedItems.clear();
            notifyDataSetChanged();
        }
    };


    //작업할 view들을 가져옴. customview를 쓰기 위해서는 선언해줘야함.
    public class CustomViewHolder extends RecyclerView.ViewHolder {

        protected ImageView imageView;
        protected TextView textView;
        protected LinearLayout linearLayout;

        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);
            this.imageView = (ImageView) itemView.findViewById(R.id.img);
            this.textView = (TextView) itemView.findViewById(R.id.title);
            this.linearLayout = (LinearLayout) itemView.findViewById(R.id.linearLayoutRow);
        }

        void selectItem(Integer item){
            if(multiSelect){
                if(selectedItems.contains(item)){
                    selectedItems.remove(item);
                    linearLayout.setBackgroundColor(Color.WHITE);
                } else {
                    selectedItems.add(item);
                    linearLayout.setBackgroundColor(Color.LTGRAY);
                }
            }
        }

        void update(final Integer value){
            //textView.setText(value + "");
            if(selectedItems.contains(value)){
                linearLayout.setBackgroundColor(Color.LTGRAY);
            } else {
                linearLayout.setBackgroundColor(Color.WHITE);
            }
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    ((AppCompatActivity)v.getContext()).startActionMode((ActionMode.Callback) actionModeCallbacks);
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
                        int position = getAdapterPosition();
                        //음악 재생을 위해서 뷰홀더에 맞는 음악 데이터를 받아옴(arrayList 활용)
                        Uri uriValue = arraylistMusic.get(position).getRaw();

                        //화면 넘김과 동시에 데이터 넘겨주기
                        Context context = v.getContext();   //그 뷰의 context, this랑 같은거.
                        Intent intent = new Intent(context, MusicPlayerActivity.class);
                        intent.putExtra("URI_VALUE", uriValue);
                        context.startActivity(intent);
                    }
                }
            });

        }
    }

    //뷰 홀더를 할당하는 함수. + 내가 만든 레이아웃(row.xml)과 연결해줌.
    @NonNull
    @Override
    public MusicRecyclerAdapter.CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row,parent,false);
        CustomViewHolder holder = new CustomViewHolder(view);

        return holder;
    }

    //각 뷰홀더에 데이터를 연결(즉, arraylist의 값을 position에 맞게 넣어줌)
    @Override
    public void onBindViewHolder(@NonNull MusicRecyclerAdapter.CustomViewHolder holder, final int position) {
        holder.imageView.setImageResource(arraylistMusic.get(position).getImg());
        holder.textView.setText(arraylistMusic.get(position).getTitle());

        holder.update(arraylistMusic.get(position).getNum());

    }

    //item이 몇 개인지 판단. 이 갯수만큼 위에 함수들이 호출될거야.
    @Override
    public int getItemCount() {
        return (null != arraylistMusic ? arraylistMusic.size() : 0);
    }

    public void remove(int num){
        try{
            int SizeArrayList = arraylistMusic.size();
            for(int i=0; i < SizeArrayList; i++){
                if(arraylistMusic.get(i).getNum() == num){
                    arraylistMusic.remove(i);
                    notifyItemRemoved(i);
                }
            }
        }catch(IndexOutOfBoundsException ex){
            ex.printStackTrace();
        }
    }


}
