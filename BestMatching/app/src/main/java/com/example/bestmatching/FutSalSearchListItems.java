package com.example.bestmatching;

public class FutSalSearchListItems {

    private String stadiumStr;
    private String priceStr;
    private int groundId;

    public void setStadium(String stadium) {
        stadiumStr = stadium;
    }

    public void setPrice(String price) {
        priceStr = price;
    }

    public void setId(int id) {
        groundId = id;
    }

    ////////////////////////////////////////////


    public String getStadium() {
        return this.stadiumStr;
    }

    public String getPrice() {
        return this.priceStr;
    }

    public int getId() {
        return this.groundId;
    }

}
