package com.gamezone.model;

public class Seller extends Person {

    private String employeeCode;
    private String workShift;

    public Seller(String name, String identification, String phone, String employeeCode, String workShift) {
        super(name, identification, phone);
        this.employeeCode = employeeCode;
        this.workShift = workShift;
    }

    public Seller(String name, String identification) {
        super(name, identification);
    }

    public String getEmployeeCode() {
        return employeeCode;
    }

    public void setEmployeeCode(String employeeCode) {
        this.employeeCode = employeeCode;
    }

    public String getWorkShift() {
        return workShift;
    }

    public void setWorkShift(String workShift) {
        this.workShift = workShift;
    }

    @Override
    public String getRol() {
        return "Seller";
    }

    @Override
    public String toString() {
        return "Seller{name='" + getName() + "',identification='" + getIdentification() +
                "', phone='" + getPhone() + "', employeeCode='" + getEmployeeCode() +
                "', workShift='" + getWorkShift() + "'}";
    }
}
