package com.gamezone.model;

import java.time.LocalDate;

/**
 * Promotion that applies a percentage discount only to products of a specific category.
 */
public class CategoryDiscount extends Promotion {
    
    private double percentage;
    private String targetCategory; 
    
    /**
     * Constructor with all attributes.
     */
    public CategoryDiscount(String id, String name, LocalDate startDate, 
                            LocalDate endDate, double percentage, String targetCategory) {
        super(id, name, startDate, endDate);
        this.percentage = percentage;
        this.targetCategory = targetCategory;
    }
    
    public double getPercentage() { return percentage; }
    public void setPercentage(double percentage) { this.percentage = percentage; }
    public String getTargetCategory() { return targetCategory; }
    public void setTargetCategory(String targetCategory) { this.targetCategory = targetCategory; }
    
    @Override
    public double calculateDiscount(Sale sale) {
        if (sale == null) return 0;
        
        double categoryTotal = 0;
        for (Product p : sale.getProducts()) {
            if (matchesCategory(p)) {
                categoryTotal += p.getPrice();
            }
        }
        return categoryTotal * (percentage / 100.0);
    }
    
    /**
     * Checks if a product belongs to the target category.
     */
    private boolean matchesCategory(Product product) {
        if ("VIDEOGAME".equalsIgnoreCase(targetCategory)) {
            return product instanceof VideoGame;
        } else if ("CONSOLE".equalsIgnoreCase(targetCategory)) {
            return product instanceof Console;
        }
        return false;
    }
    
    @Override
    public String getPromotionType() {
        return "CATEGORY";
    }
    
    @Override
    public String toString() {
        return super.toString() + " [percentage=" + percentage + 
               "%, category=" + targetCategory + "]";
    }
}