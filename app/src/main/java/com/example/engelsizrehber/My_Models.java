package com.example.engelsizrehber;

public class My_Models {

    String email, name;

    public My_Models(String email, String name) {
        this.email = email;
        this.name = name;
    }
    public My_Models() {}

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
