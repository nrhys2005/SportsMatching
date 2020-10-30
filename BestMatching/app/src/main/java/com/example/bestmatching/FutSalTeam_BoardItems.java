package com.example.bestmatching;

public class FutSalTeam_BoardItems {


    private String titleStr;

    private String part_countStr;
    private String no_countStr;
    private String max_part_countStr;



    public void setTitle(String title) {
        titleStr = title;
    }

    public void setPart_count(String part_count) {
        part_countStr = part_count;
    }

    public void setNo_count(String no_count) {
        no_countStr = no_count;
    }

    public void setMax_part_count(String max_part_count) {
        max_part_countStr = max_part_count;
    }



    ////////////////////////////////////////////


    public String getTitleStr() {
        return this.titleStr;
    }

    public String getPart_count(){return this.part_countStr; }

    public String getNo_count() {
        return this.no_countStr;
    }

    public String getMax_part_count() {
        return this.max_part_countStr;
    }

}

