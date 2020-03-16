package com.example.emotiondiary.ListVO;

public class FindData {

    private int iv_profile;
    private String tv_name;
    private String tv_content;
    private String time;
    private String user;

    //생성자
    public FindData(int iv_profile, String tv_name, String tv_content, String time, String user) {
        this.iv_profile = iv_profile;
        this.tv_name = tv_name;
        this.tv_content = tv_content;
        this.time = time;
        this.user = user;
    }

    //자료 저장 및 불러오기 메서드
    public int getIv_profile() {
        return iv_profile;
    }

    public void setIv_profile(int iv_profile) {
        this.iv_profile = iv_profile;
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

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }
}

