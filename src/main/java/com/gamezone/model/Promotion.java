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
    
}
