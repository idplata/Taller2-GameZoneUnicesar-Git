package com.gamezone.model;

import java.io.Serializable;

/**
 * Abstract base class representing a product in the store.
 */
public abstract class Product implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    private String id;
    private String title;
    private double price;
    private int quantity;
    
    public Product(String id, String title, double price, int quantity) {
        this.id = id;
        this.title = title;
        this.price = price;
        this.quantity = quantity;
    }
    
    public String getId() { return id; }
    public String getTitle() { return title; }
    public double getPrice() { return price; }
    public int getQuantity() { return quantity; }
    
    public void setTitle(String title) { this.title = title; }
    public void setPrice(double price) { this.price = price; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
    
    /**
     * Returns a full description. Must be implemented by subclasses.
     * 
     * @return formatted description string
     */
    public abstract String getDescription();
}