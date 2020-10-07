package com.example.bestmatching;

public class FutSalHelp_QuestionItems {

    private String categoryStr;
    private String titleStr;
    private String contentStr;
    private String questionid;

    public void setCategory(String category) {
        categoryStr ="["+ category+ "]";
    }

    public void settitle(String title) {
        titleStr=title;
    }

    public void setcontent(String content) {
        contentStr=content;
    }

    public void setId(String id) {
        questionid = id;
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

    public String getId() {
        return this.questionid;
    }

}

