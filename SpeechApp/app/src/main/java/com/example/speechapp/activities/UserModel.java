package com.example.speechapp.activities;

class UserModel {


    public  String name, email,password, userid;

    public UserModel(String name, String password, String email, String userid) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.userid = userid;
    }

    public UserModel(){

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }



    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

}
