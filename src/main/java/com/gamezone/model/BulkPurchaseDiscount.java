/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.gamezone.model;

import java.time.LocalDate;

public class BulkPurchaseDiscount extends Promotion {
    private int minimumQuantity;
    private double percentage;
    
    public BulkPurchaseDiscount(String id, String name, LocalDate startDate, LocalDate endDate, int minimumQuantity, double percentage) {
        super(id, name, startDate, endDate);
        this.minimumQuantity = minimumQuantity;
        this.percentage = percentage;
    }
    
    public int getMinimumQuantity() { return minimumQuantity; }
    public void setMinimumQuantity(int minimumQuantity) { this.minimumQuantity = minimumQuantity; }
    public double getPercentage() { return percentage; }
    public void setPercentage(double percentage) { this.percentage = percentage; }
    
    @Override
    public double calculateDiscount(Sale sale) {
        if (sale == null || sale.getProducts().size() < minimumQuantity) {
            return 0;
        }
        return sale.calculatePrice() * (percentage / 100.0);
    }
}