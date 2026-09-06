package com.gamezone.model;

/**
 * Represents a gaming console product.
 */
public class Console extends Product {
    
    private static final long serialVersionUID = 1L;
    
    private String brand;
    private String model;
    private int generation;
    
    public Console(String id, String title, double price, int quantity,
                   String brand, String model, int generation) {
        super(id, title, price, quantity);
        this.brand = brand;
        this.model = model;
        this.generation = generation;
    }
    
    public String getBrand() { return brand; }
    public String getModel() { return model; }
    public int getGeneration() { return generation; }
    
    @Override
    public String getDescription() {
        return "Console: " + getTitle() + 
               " | Brand: " + brand + 
               " | Model: " + model + 
               " | Generation: " + generation + 
               " | Price: $" + getPrice() + 
               " | Stock: " + getQuantity();
    }
}
