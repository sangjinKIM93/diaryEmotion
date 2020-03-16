package com.example.emotiondiary.ListVO;

import android.net.Uri;

public class ListVO {
    private int img;
    private String Title;
    private Uri audio;
    private int num;

    public ListVO(int image, String title, Uri uri, int num) {
        this.img = image;
        this.Title = title;
        this.audio = uri;
        this.num = num;
    }

    public int getImg(){
        return img;
    }

    public void setImg(int img){
        this.img = img;
    }

    public Uri getRaw(){
        return audio;
    }

    public void setRaw(Uri audio){
        this.audio = audio;
    }
    
    public String getTitle(){
        return Title;
    }

    public void setTitle(String title){
        Title = title;
    }

    public int getNum(){
        return num;
    }

    public void setNum(int num){
        this.num = num;
    }

}
