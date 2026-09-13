
package com.gamezone.model;


public abstract class Accesory {
    protected String id;
    protected String title;
    protected String price;
    protected String stock; 
    
    public Accesory (String id, String title, String price, String stock){
        this.id = id;
        this.title = title;
        this.price = price;
        this.stock = stock;
    }
    public String getId() { return id; }
    public String getTitle() { return title; }
    public String getPrice() { return price; }
    public String getStock() { return stock; }
    

    public String getDescription() {
        return "Console: " + getTitle() + 
               " | Id: " + getId() + 
               " | Title: " + getTitle() +  
               " | Price: $" + getPrice() + 
               " | Stock: " + getStock();
    }
}