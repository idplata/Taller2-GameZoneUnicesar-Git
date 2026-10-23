package com.gamezone.model;

import java.time.LocalDate;

/**
 * Promotion that applies a percentage discount to the total sale.
 */
public class PercentageDiscount extends Promotion {
    
    private double percentage;
    
    /**
     * Constructor with all attributes.
     */
    public PercentageDiscount(String id, String name, LocalDate startDate, 
                              LocalDate endDate, double percentage) {
        super(id, name, startDate, endDate);
        this.percentage = percentage;
    }
    
    public double getPercentage() { return percentage; }
    public void setPercentage(double percentage) { this.percentage = percentage; }
    
    @Override
    public double calculateDiscount(Sale sale) {
        if (sale == null) return 0;
        double subtotal = sale.calculatePrice();
        return subtotal * (percentage / 100.0);
    }
    
    @Override
    public String getPromotionType() {
        return "PERCENTAGE";
    }
    
    @Override
    public String toString() {
        return super.toString() + " [percentage=" + percentage + "%]";
    }
}