package com.gamezone.model;


import com.gamezone.model.Accesory;

public class Cable extends Accesory{
    private int length;
    private String type;
  
    public Cable(String id, String title, String price, String amount, int length, String type) {
        super(id, title, price, amount);
        this.length=length;
        this.type=type;
    }
    
    public String getType(){return type;}
    public int getLenght(){ return length;}
    
    
}