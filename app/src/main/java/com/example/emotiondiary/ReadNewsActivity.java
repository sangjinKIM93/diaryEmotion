package com.example.emotiondiary;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class ReadNewsActivity extends AppCompatActivity implements View.OnClickListener {

    private WebView mWebView; // 웹뷰 선언
    private WebSettings mWebSettings; //웹뷰세팅

    private Animation fab_open;
    private Animation fab_close;
    private Boolean isFabOpen = false;
    private FloatingActionButton fab, fab1;

    Uri uri;
    String title;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.read_news);

        //uri주소 받아오기
        uri= getIntent().getParcelableExtra("webLink");
        title = getIntent().getStringExtra("TITLE");
        mWebView = (WebView) findViewById(R.id.webview_news);

        //웹뷰 기본설정
        mWebView.setWebViewClient(new WebViewClient()); // 클릭시 새창 안뜨게
        mWebSettings = mWebView.getSettings(); //세부 세팅 등록
        mWebSettings.setJavaScriptEnabled(true); // 웹페이지 자바스클비트 허용 여부
        mWebSettings.setSupportMultipleWindows(false); // 새창 띄우기 허용 여부
        mWebSettings.setJavaScriptCanOpenWindowsAutomatically(false); // 자바스크립트 새창 띄우기(멀티뷰) 허용 여부
        mWebSettings.setLoadWithOverviewMode(true); // 메타태그 허용 여부
        mWebSettings.setUseWideViewPort(true); // 화면 사이즈 맞추기 허용 여부
        mWebSettings.setSupportZoom(false); // 화면 줌 허용 여부
        mWebSettings.setBuiltInZoomControls(false); // 화면 확대 축소 허용 여부
        mWebSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN); // 컨텐츠 사이즈 맞추기
        mWebSettings.setCacheMode(WebSettings.LOAD_NO_CACHE); // 브라우저 캐시 허용 여부
        mWebSettings.setDomStorageEnabled(true); // 로컬저장소 허용 여부

        mWebView.loadUrl(String.valueOf(uri)); // 웹뷰에 표시할 웹사이트 주소, 웹뷰 시작

        //floatingButton 설정
        fab_open = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_open);
        fab_close = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_close);

        fab = (FloatingActionButton)findViewById(R.id.fab);
        fab1 = (FloatingActionButton)findViewById(R.id.fab1);

        fab.setOnClickListener(this);
        fab1.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id){
            case R.id.fab:
                anim();
                break;
            case R.id.fab1:
                anim();
                Intent i = new Intent(ReadNewsActivity.this, MemoWriteActivity.class);
                i.putExtra("URI", uri);
                i.putExtra("TITLE", title);
                startActivity(i);
                finish();
                break;
        }
    }

    private void anim() {
        if(isFabOpen){
            fab1.startAnimation(fab_close);
            fab1.setClickable(false);
            isFabOpen = false;
        } else {
            fab1.startAnimation(fab_open);
            fab1.setClickable(true);
            isFabOpen = true;
        }
    }
}
