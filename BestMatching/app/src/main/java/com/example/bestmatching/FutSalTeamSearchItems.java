package com.example.bestmatching;

public class FutSalTeamSearchItems {

    private String titleStr;
    private String masterStr;
    private String phoneStr;
    private String locationStr;
    private String weekStr;

    public void setTitle(String title) {
        titleStr = title;
    }

    public void setMaster(String master) {
        masterStr = master;
    }

    public void setPhone(String phone) {
        phoneStr = phone;
    }

    public void setLocation (String location) {
        locationStr = location;
    }

    public void setWeek(String week) {
        weekStr = week;
    }

    ////////////////////////////////////////////


    public String getTitle() {
        return this.titleStr;
    }
    public String getMaster() {
        return this.masterStr;
    }

    public String getPhone() {
        return this.phoneStr;
    }

    public String getLocation() {
        return this.locationStr;
    }
    public String getWeek() {
        return this.weekStr;
    }
}
