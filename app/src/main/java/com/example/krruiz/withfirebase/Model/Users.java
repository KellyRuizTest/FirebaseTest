package com.example.krruiz.withfirebase.Model;

public class Users {

    public String name;
    public String phonenumber;
    public String password;


    public Users(){

    }

    public Users(String name, String phonenumber, String password) {
        this.name = name;
        this.phonenumber = phonenumber;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
