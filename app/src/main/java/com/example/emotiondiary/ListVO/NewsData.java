package com.example.emotiondiary.ListVO;

import java.io.Serializable;

public class NewsData implements Serializable {

    //왜 private 캡슐화 때문에, 즉, 다른 클래스에서 접근하지 못하게
    private String title;
    private String content;
    private String link;

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
