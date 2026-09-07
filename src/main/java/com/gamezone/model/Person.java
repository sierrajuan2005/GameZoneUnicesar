package com.gamezone.model;

public abstract class Person {

    private String name;
    private String identification;
    private String phone;

    public Person(String name, String identification, String phone) {
        this.name = name;
        this.identification = identification;
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIdentification() {
        return identification;
    }

    public void setIdentification(String identification) {
        this.identification = identification;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public abstract String getRol();
}
