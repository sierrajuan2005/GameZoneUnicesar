package com.gamezone.model;

import java.time.LocalDate;

public class WarrantyData {
    private String type;
    private String identifier;
    private String productIdentifier;
    private String saleIdentifier;
    private LocalDate startDate;
    private LocalDate endDate;

    public WarrantyData(String type, String identifier, String productIdentifier, String saleIdentifier, LocalDate startDate, LocalDate endDate) {
        this.type = type;
        this.identifier = identifier;
        this.productIdentifier = productIdentifier;
        this.saleIdentifier = saleIdentifier;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public String getType() {
        return type;
    }

    public String getIdentifier() {
        return identifier;
    }
}
