package com.gamezone.model;

/**
 * Abstract base class for persons in GameZone.
 * Defines common attributes and requires subclasses to specify role and info.
 */
public abstract class Person {

    private String name;
    private String identification;
    private String phone;

    /**
     * Creates a person with name, identification and phone.
     *
     * @param name          person's full name
     * @param identification unique identifier
     * @param phone         contact phone
     */
    public Person(String name, String identification, String phone) {
        this.name = name;
        this.identification = identification;
        this.phone = phone;
    }

    /**
     * Creates a person with name and identification.
     *
     * @param name          person's full name
     * @param identification unique identifier
     */
    public Person(String name, String identification) {
        this.name = name;
        this.identification = identification;
    }

    /** @return person's name */
    public String getName() {
        return name;
    }

    /** Sets person's name */
    public void setName(String name) {
        this.name = name;
    }

    /** @return person's identification */
    public String getIdentification() {
        return identification;
    }

    /** Sets person's identification */
    public void setIdentification(String identification) {
        this.identification = identification;
    }

    /** @return person's phone */
    public String getPhone() {
        return phone;
    }

    /** Sets person's phone */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * Abstract method to define role (Customer, Seller).
     *
     * @return role string
     */
    public abstract String getRol();

    /**
     * Abstract method to return string info of the person.
     *
     * @return formatted string with person details
     */
    public abstract String toString();
}
