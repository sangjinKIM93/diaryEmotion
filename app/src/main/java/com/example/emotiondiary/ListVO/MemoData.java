package com.example.emotiondiary.ListVO;

public class MemoData {

    private String tv_emotion;
    private String tv_name;
    private String tv_content;
    private String time;
    private String id;
    private String user;
    private boolean checked;
    private String imgPath;
    private String link;

    //생성자
//    public MemoData(String tv_emotion, String tv_name, String tv_content, String time, int id, String user, boolean checked, String imgPath, String link) {
//        this.tv_emotion = tv_emotion;
//        this.tv_name = tv_name;
//        this.tv_content = tv_content;
//        this.time = time;
//        this.id = id;
//        this.user = user;
//        this.checked = checked;
//        this.imgPath = imgPath;
//        this.link = link;
//    }


    //자료 저장 및 불러오기 메서드


    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

    public boolean getChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public String getTv_emotion() {
        return tv_emotion;
    }

    public void setTv_emotion(String tv_emotion) {
        this.tv_emotion = tv_emotion;
    }

    public String getTv_name() {
        return tv_name;
    }

    public void setTv_name(String tv_name) {
        this.tv_name = tv_name;
    }

    public String getTv_content() {
        return tv_content;
    }

    public void setTv_content(String tv_content) {
        this.tv_content = tv_content;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }
}

