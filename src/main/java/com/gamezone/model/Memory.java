package com.gamezone.model;

import com.gamezone.model.Accesory;

public class Memory extends Accesory{
    private int Gigabytes;
    private String type;
  
    private Memory (String id, String title, String price, String amount, int Gigabytes, String type) {
        super(id, title, price, amount);
        this.Gigabytes=Gigabytes;
        this.type=type;
    }

    public Memory(String id, String title, String price, String amount) {
        super(id, title, price, amount);
    }
    
    public String getType(){return type;}
    public int getGigabyte(){ return Gigabytes;}
    
    
}