package com.gamezone.model;

public class Controller extends Accesory{
    private int wired;
    private int wireless;
    private String type;
  
    private Controller (String id, String title, String price, String amount, int Gigabytes, String type) {
        super(id, title, price, amount);
        this.wired=wired;
        this.wireless=wireless;
        this.type=type;
    }

    public Controller(String id, String title, String price, String amount) {
        super(id, title, price, amount);
    }
    
    public String getType(){return type;}
    public int getWired(){ return wired;}
    public int getWireless(){ return wireless;}
    
    
}