package com.gamezone.model;


public abstract class Accesory {
    protected String id;
    protected String title;
    protected String price;
    protected String amount; 
    
    public Accesory (String id, String title, String price, String amount){
        this.id = id;
        this.title = title;
        this.price = price;
        this.amount = amount;
    }
    public String getId() { return id; }
    public String getTitle() { return title; }
    public String getPrice() { return price; }
    public String getAmount() { return amount; }
    

    public String getDescription() {
        return "Console: " + getTitle() + 
               " | Id: " + getId() + 
               " | Title: " + getTitle() +  
               " | Price: $" + getPrice() + 
               " | Amount: " + getAmount();
    }
}
