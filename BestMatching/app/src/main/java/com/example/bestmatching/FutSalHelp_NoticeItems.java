package com.example.bestmatching;

public class FutSalHelp_NoticeItems {


    private String titleStr;
    private String create_timeStr;
    private String contentStr;



    public void settitle(String title) {
        titleStr="[공지사항]  "+title;
    }
    public void setCreate_time(String create_time) {
        create_timeStr=create_time;
    }

    public void setcontent(String content) {
        contentStr=content;
    }



    ////////////////////////////////////////////




    public String gettitleStr() {
        return this.titleStr;
    }
    public String getCreate_timeStr(){return this.create_timeStr; }
    public String getContentStrt() {
        return this.contentStr;
    }



}

