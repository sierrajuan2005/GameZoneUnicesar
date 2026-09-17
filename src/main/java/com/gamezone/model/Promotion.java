package com.gamezone.model;

import java.time.LocalDate;

/**
 * Represents an abstract promotion applied to a sale.
 */
public abstract class Promotion {
    private String identifier;
    private String name;
    private LocalDate startDate;
    private LocalDate endDate;
    
    /*
    Creates a new promotion.
    @param identifier unique promotion identifier
    @param name promotion name
    @param startDate promotion start date
    @param endDate promotion end date
     */
    public Promotion(String identifier, String name, LocalDate startDate, LocalDate endDate) {
        this.identifier = identifier;
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
    }
    
    /*
    Returns the promotion identifier.
    @return promotion identifier
     */
    public String getIdentifier() {
        return identifier;
    }

    /*
    Updates the promotion identifier.
    @param identifier new promotion identifier
     */
    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    /*
    Returns the promotion name.
    @return promotion name
     */
    public String getName() {
        return name;
    }

    /*
    Updates the promotion name.
    @param name new promotion name
     */
    public void setName(String name) {
        this.name = name;
    }

    /*
    Returns the promotion start date.
    @return start date
     */
    public LocalDate getStartDate() {
        return startDate;
    }

    /*
    Updates the promotion start date.
    @param startDate new start date
     */
    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    /*
    Returns the promotion end date.
    @return end date
     */
    public LocalDate getEndDate() {
        return endDate;
    }

    /*
    Updates the promotion end date.
    @param endDate new end date
     */
    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }


    /*
    Checks whether the promotion is active on the given date
    @param date date to check
    @return true if the date is within the promotion validity range
     */
    public boolean isActive(LocalDate date){
        return !date.isBefore(startDate) && !date.isAfter(endDate);
    }
}
