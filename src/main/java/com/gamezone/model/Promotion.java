/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.gamezone.model;

import java.io.Serializable;
import java.time.LocalDate;

public abstract class Promotion implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    private String id;
    private LocalDate startDate;
    private LocalDate endDate;
    private String name;
    
    public Promotion(String id, String name, LocalDate startDate, LocalDate endDate) {
        this.id = id;
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
    }
    
    public String getId(){
        return id;
    }
    public String getName(){
        return name;
    }
    public LocalDate startDate(){
        return startDate;
    }
    
    public LocalDate endDate(){
        
        return endDate;
    }
    
    public void setId(String id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setStartDate(LocalDate startDate) { this.startDate = startDate; }
    public void setEndDate(LocalDate endDate) { this.endDate = endDate; }
    
    public boolean isActive(LocalDate date) {
        if (date == null) return false;
        return !date.isBefore(startDate) && !date.isAfter(endDate);
    }
    
    public abstract double calculateDiscount(Sale sale);
    
    public abstract String getPromotionType();
    
    @Override
    public String toString() {
        return "Promotion{id='" + id + "', name='" + name + 
               "', from=" + startDate + ", to=" + endDate + "}";
    }
}
