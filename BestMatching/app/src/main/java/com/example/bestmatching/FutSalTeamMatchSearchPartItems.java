package com.example.bestmatching;

public class FutSalTeamMatchSearchPartItems {
    private String team_nameStr;
    private String nameStr;
    private String ageStr;
    private String locationStr;
    private String posStr;
    private String emailStr;
    private String phoneStr;

    public void setTeam_name(String team_name) {
        team_nameStr = team_name;
    }

    public void setName(String name) {
        nameStr = name;
    }

    public void setAge(String age) {
        ageStr = age;
    }

    public void setLoc(String location) {
        locationStr = location;
    }

    public void setPos(String pos) {
        posStr = pos;
    }

    public void setEmail(String email) {
        emailStr = email;
    }

    public void setPhone(String phone) {
        phoneStr = phone;
    }


    ////////////////////////////////////////////

    public String getTeam_name() {
        return this.team_nameStr;
    }
    public String getName() {
        return this.nameStr;
    }

    public String getAge() {
        return this.ageStr;
    }

    public String getLoc() {
        return this.locationStr;
    }

    public String getPos() {
        return this.posStr;
    }

    public String getEmail() {
        return this.emailStr;
    }

    public String getPhone() {
        return this.phoneStr;
    }



}
