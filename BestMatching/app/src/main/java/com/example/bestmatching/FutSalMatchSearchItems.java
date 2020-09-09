package com.example.bestmatching;

public class FutSalMatchSearchItems {

    private String titleStr;
    private String groundStr;
    private String dateStr;
    private String startTimeStr;
    private String endTimeStr;

    public void setTitle(String title) {
        titleStr = title;
    }

    public void setGround(String ground) {
        groundStr = ground;
    }

    public void setDate(String date) {
        dateStr = date;
    }

    public void setStartTime(String start_time) {
        startTimeStr = start_time;
    }

    public void setEndtime(String end_time) {
        endTimeStr = end_time;
    }

    ////////////////////////////////////////////


    public String getTitle() {
        return this.titleStr;
    }

    public String getGround() {
        return this.groundStr;
    }

    public String getDate() {
        return this.dateStr;
    }

    public String getStartTime() {
        return this.startTimeStr;
    }

    public String getEndTime() {
        return this.endTimeStr;
    }


}
