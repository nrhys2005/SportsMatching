package com.example.bestmatching;

public class FutSalHelp_NoticeItems {

    private String categoryStr;
    private String titleStr;
    private String contentStr;
    private int noticeid;

    public void setCategory(String category) {
        categoryStr = category;
    }

    public void settitle(String title) {
        titleStr=title;
    }

    public void setcontent(String content) {
        contentStr=content;
    }

    public void setId(int id) {
        noticeid = id;
    }

    ////////////////////////////////////////////


    public String getCategoryStr() {
        return this.categoryStr;
    }

    public String gettitleStr() {
        return this.titleStr;
    }
    public String getContentStrt() {
        return this.contentStr;
    }

    public int getId() {
        return this.noticeid;
    }

}

