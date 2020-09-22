package com.example.bestmatching;

public class FutSalMatchSearchPartItems {

    private String nameStr;
    private String ageStr;
    private String emailStr;
    private String phoneStr;

    public void setName(String name) {
        nameStr = name;
    }

    public void setAge(String age) {
        ageStr = age;
    }

    public void setEmail(String email) {
        emailStr = email;
    }

    public void setPhone(String phone) {
        phoneStr = phone;
    }


    ////////////////////////////////////////////


    public String getName() {
        return this.nameStr;
    }

    public String getAge() {
        return this.ageStr;
    }

    public String getEmail() {
        return this.emailStr;
    }

    public String getPhone() {
        return this.phoneStr;
    }



}
