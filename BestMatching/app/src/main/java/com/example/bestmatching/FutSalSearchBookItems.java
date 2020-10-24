package com.example.bestmatching;

public class FutSalSearchBookItems {

    private String groundNameStr;
    private String prcieStr;
    private String startTimeStr;
    private String endTimeStr;
    private int groundId;

    public void setGroundName(String groundName) {
        groundNameStr = groundName;
    }

    public void setPrcie(String price) {
        prcieStr = price;
    }

    public void setStartTime(String start_time) {
        startTimeStr = start_time;
    }

    public void setEndtime(String end_time) {
        endTimeStr = end_time;
    }

    public void setId(int id) {
        groundId = id;
    }

    ////////////////////////////////////////////


    public String getGroundName() {
        return this.groundNameStr;
    }

    public String getPrcie() {
        return this.prcieStr;
    }

    public String getStartTime() {
        return this.startTimeStr;
    }

    public String getEndTime() {
        return this.endTimeStr;
    }

    public int getId() {
        return this.groundId;
    }

}
