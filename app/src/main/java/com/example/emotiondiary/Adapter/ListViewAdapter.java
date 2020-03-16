package com.example.emotiondiary.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.emotiondiary.ListVO.ListVO;
import com.example.emotiondiary.R;

import java.util.ArrayList;

public class ListViewAdapter extends BaseAdapter {

    private ArrayList<ListVO> listVO = new ArrayList<ListVO>();

    //생성자 -> 데이터 셋팅. *굳이 안 해줘도 되긴 하는 듯. 왜냐면 위에서 설정한 listVO로 처리가능
    public ListViewAdapter(){

    }

    //리스트 몇개만들지 세는 것. 안드로이드 시스템 입장에서는 공간부터 만들고 시작해야해.
    @Override
    public int getCount(){
        return listVO.size();
    }

    //
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final int pos = position;   //현재 몇번째 아이템인지 표시
        final Context context = parent.getContext();

        //처음 생성할때, 재활용 할 view가 없을때
        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.row, parent, false);
        }

        //view 생성이 아닌 데이터만 바꿀때
        ImageView image = (ImageView)convertView.findViewById(R.id.img);
        TextView title = (TextView)convertView.findViewById(R.id.title);

        ListVO listViewItem = listVO.get(position);

        //아이템 내 각 위젯에 데이터 반영
       // image.setImageDrawable(listViewItem.getImg());
        title.setText(listViewItem.getTitle());

        //리스트뷰 클릭 이벤트
//        convertView.setOnClickListener(new View.OnClickListener(){
//            @Override
//            public void onClick(View view){
//                Toast.makeText(context, (pos+1)+"번째 리스트가 클릭되었습니다.", Toast.LENGTH_SHORT).show();
//
//
//            }
//        });

        return convertView;

    }

    @Override
    public Object getItem(int position) {
        return listVO.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

//    public  void addVO(Drawable icon, String title, Uri audio){
//        //ListVO 클래스의 item 인스턴스 생성. ListVO는 img와 text를 담을 수 있는 하나의 클래스를 임의로 만든거야.
//        ListVO item = new ListVO();
//
//        item.setImg(icon);
//        item.setTitle(title);
//        item.setRaw(audio);
//
//
//        listVO.add(item);
//
//    }


}
