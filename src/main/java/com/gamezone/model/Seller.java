package com.gamezone.model;

/**
 * Seller entity extending Person.
 * Stores employee code and work shift.
 */
public class Seller extends Person{

    private String employeeCode;
    private String workShift;

    /**
     * Creates a seller with name, identification, phone, employee code and work shift.
     *
     * @param name           seller's full name
     * @param identification unique identifier
     * @param phone          contact phone
     * @param employeeCode   seller's employee code
     * @param workShift      seller's work shift
     */
    public Seller(String name, String identification, String phone, String employeeCode, String workShift) {
        super(name, identification, phone);
        this.employeeCode = employeeCode;
        this.workShift = workShift;
    }

    /**
     * Creates a seller with name and identification.
     *
     * @param name           seller's full name
     * @param identification unique identifier
     */
    public Seller(String name, String identification) {
        super(name, identification);
    }

    /** @return employee code */
    public String getEmployeeCode() {
        return employeeCode;
    }

    /** Sets employee code */
    public void setEmployeeCode(String employeeCode) {
        this.employeeCode = employeeCode;
    }

    /** @return work shift */
    public String getWorkShift() {
        return workShift;
    }

    /** Sets work shift */
    public void setWorkShift(String workShift) {
        this.workShift = workShift;
    }

    /** @return role string "Seller" */
    @Override
    public String getRol() {
        return "Seller";
    }

    /** @return string with seller details */
    @Override
    public String toString() {
        return "Seller{name='" + getName() + "',identification='" + getIdentification() +
                "', phone='" + getPhone() + "', employeeCode='" + getEmployeeCode() +
                "', workShift='" + getWorkShift() + "'}";
    }
}
