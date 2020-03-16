package com.example.emotiondiary;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.inputmethod.EditorInfo;
import android.widget.SearchView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.emotiondiary.Adapter.NewsAdapter;
import com.example.emotiondiary.ListVO.NewsData;
import com.example.emotiondiary.R;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class NewsActivity extends AppCompatActivity {

    //recyclerView 기본 설정을 위한 놈들
    private NewsAdapter NewsAdapter;
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private ArrayList<NewsData> datasNews;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.news_activity);

        createProgressDialog();  //로딩창 생성

        new GetXMLTask().execute(); //XML 파싱

        //리사이클러뷰에 linearLayoutManager를 연결해준다.
        recyclerView = (RecyclerView)findViewById(R.id.rv_news);
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);


    }

    private void createProgressDialog(){
        //ProgressDialog 생성
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("ProgressDialog running...");
        progressDialog.setCancelable(true);
        progressDialog.setProgressStyle(android.R.style.Widget_ProgressBar_Horizontal);
    }

    private class GetXMLTask extends AsyncTask<String, Void, Document> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog.show();      //파싱될 동안 progressDialog 동작
        }

        @Override
        protected Document doInBackground(String... strings) {
            //여기서는 DOM이 실질적으로 실행되는 곳
            URL url;
            Document doc = null;
            try {
                url = new URL("http://www.psychiatricnews.net/rss/allArticle.xml");
                DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
                DocumentBuilder db = dbf.newDocumentBuilder();
                doc = db.parse(new InputSource(url.openStream()));

            } catch (IOException | ParserConfigurationException | SAXException e) {
                e.printStackTrace();
            }
            return doc;
        }

        @Override
        protected void onPostExecute(Document document) {
            //DOM으로 뽑은 트리를 이용해서 리사이클러뷰에 넣어주기.
            String titleStr, linkStr, contentStr;
            NodeList nodeList = document.getElementsByTagName("item");      //document가 뭐냐? doc 로 onPreExecute의 return값

            datasNews = new ArrayList<>();

            for(int i=0; i<nodeList.getLength(); i++){
                Node node = nodeList.item(i);
                Element fstElmnt = (Element)node;

                NewsData newsData = new NewsData(); //이거를 안에 넣어줘야해. 이건 덮어씌워지지 않나봐 ㅠㅠ

                NodeList title = fstElmnt.getElementsByTagName("title");
                titleStr = title.item(0).getChildNodes().item(0).getNodeValue();
                newsData.setTitle(titleStr);

                NodeList link = fstElmnt.getElementsByTagName("link");
                linkStr = link.item(0).getChildNodes().item(0).getNodeValue();
                newsData.setLink(linkStr);

                NodeList content = fstElmnt.getElementsByTagName("description");
                contentStr = content.item(0).getChildNodes().item(0).getNodeValue();
                newsData.setContent(contentStr);

                datasNews.add(newsData);

            }

            NewsAdapter = new NewsAdapter(datasNews);
            recyclerView.setAdapter(NewsAdapter);
            NewsAdapter.notifyDataSetChanged();

            progressDialog.dismiss();

            super.onPostExecute(document);

        }

    }

    //SearchView
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        //레이아웃 정보 받아오기
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.news_menu, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) searchItem.getActionView();

        searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);       //키보드 엔터키 아이콘 바꾸기(중요한건 아니야.)

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                NewsAdapter.getFilter().filter(newText);
                return false;
            }
        });
        return true;
    }
}
