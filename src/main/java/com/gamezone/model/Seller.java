package com.gamezone.model;

public class Seller extends Person{

    private String employeeCode;
    private String workShift;

    public Seller(String name, String identification, String phone, String employeeCode, String workShift) {
        super(name, identification, phone);
        this.employeeCode = employeeCode;
        this.workShift = workShift;
    }

}
