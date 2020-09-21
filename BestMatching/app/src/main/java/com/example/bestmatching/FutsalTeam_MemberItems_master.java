package com.example.bestmatching;

public class FutsalTeam_MemberItems_master {

    private String idStr;
    private String nameStr;
    private String ageStr;
    private String locationStr;
    private String phonenumberStr;
    private String positionStr;


    public void setId(String id) { idStr=id; }
    public void setName(String name) { nameStr=name; }
    public void setAge(String age) { ageStr=age; }
    public void setLocation(String location) { locationStr=location; }
    public void setPhonenumber(String phonenumber) { phonenumberStr=phonenumber; }
    public void setPosition(String position) { positionStr=position; }

    ////////////////////////////////////////////

    public String getId(){ return this.idStr;}
    public String getName(){ return this.nameStr;}
    public String getAge(){ return this.ageStr;}
    public String getLocation(){ return this.locationStr;}
    public String getPhonenumberStr(){ return this.phonenumberStr;}
    public String getPositionStr(){ return this.positionStr;}

}

