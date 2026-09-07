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
}
