package com.example.emotiondiary.ListVO;

public class UserData {

    private String uId;
    private String uPassword;

    public UserData(String uId, String uPassword) {
        this.uId = uId;
        this.uPassword = uPassword;
    }

    public String getuId() {
        return uId;
    }

    public void setuId(String uId) {
        this.uId = uId;
    }

    public String getuPassword() {
        return uPassword;
    }

    public void setuPassword(String uPassword) {
        this.uPassword = uPassword;
    }


}
