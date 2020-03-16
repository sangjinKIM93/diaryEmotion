package com.example.emotiondiary.Adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.emotiondiary.ListVO.NewsData;
import com.example.emotiondiary.R;
import com.example.emotiondiary.ReadNewsActivity;

import java.util.ArrayList;
import java.util.List;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.CustomViewHolder> implements Filterable {

    private ArrayList<NewsData> newsData;
    private List<NewsData> newsDataFull;    //검색을 위한 복제본

    //생성자. 생성시 arrayList를 받아온다. 이 정보를 바탕으로 조작하는거야.
    public NewsAdapter(ArrayList<NewsData> newsData) {
        this.newsData = newsData;
        newsDataFull = new ArrayList<>(newsData);
    }

    @Override
    public Filter getFilter() {
        return exampleFilter;
    }

    private Filter exampleFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<NewsData> filteredList = new ArrayList<>();    //필터된 리스트

            if(constraint ==null || constraint.length() ==0){
                filteredList.addAll(newsDataFull);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();
                for (NewsData item : newsDataFull){
                    //content 조건 && title 조건
                    if(item.getContent().toLowerCase().contains(filterPattern) || item.getTitle().toLowerCase().contains(filterPattern)){
                        filteredList.add(item);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = filteredList;
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            newsData.clear();
            newsData.addAll((List)results.values);
            notifyDataSetChanged();
        }
    };


    //작업할 view들을 가져옴. customview를 쓰기 위해서는 선언해줘야함.
    public class CustomViewHolder extends RecyclerView.ViewHolder {

        protected TextView titleXML, contentXML;


        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);
            this.titleXML = (TextView) itemView.findViewById(R.id.newsTitle);
            this.contentXML = (TextView) itemView.findViewById(R.id.newsContent);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    Context context = v.getContext();

                    Intent i = new Intent(context, ReadNewsActivity.class);
                    Uri uri = Uri.parse(newsData.get(position).getLink());
                    String title = newsData.get(position).getTitle();
                    i.putExtra("webLink", uri);
                    i.putExtra("TITLE", title);
                    context.startActivity(i);

//                    Intent intent = new Intent(Intent.ACTION_VIEW);
//                    Uri uri = Uri.parse(newsData.get(position).getLink());
//                    intent.setData(uri);
//                    context.startActivity(intent);

                }
            });


        }
    }

    //뷰 홀더를 할당하는 함수. + 내가 만든 레이아웃(row.xml)과 연결해줌.
    @NonNull
    @Override
    public NewsAdapter.CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_news, parent, false);
        CustomViewHolder holder = new CustomViewHolder(view);

        return holder;
    }

    //각 뷰홀더에 데이터를 연결(즉, arraylist의 값을 position에 맞게 넣어줌)
    @Override
    public void onBindViewHolder(@NonNull NewsAdapter.CustomViewHolder holder, final int position) {

        holder.titleXML.setText(newsData.get(position).getTitle());
        holder.contentXML.setText(newsData.get(position).getContent());

    }

    //item이 몇 개인지 판단. 이 갯수만큼 위에 함수들이 호출될거야.
    @Override
    public int getItemCount() {
        return (null != newsData ? newsData.size() : 0);
    }


}
