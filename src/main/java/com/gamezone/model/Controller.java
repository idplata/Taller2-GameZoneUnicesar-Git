/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.gamezone.model;

public class Controller extends Accesory{
    private String wired;
    private String wireless;
    private String type;
  
    public Controller (String id, String title, String price, String amount,String wired,String wireless, String type) {
        super(id, title, price, amount);
        this.wired=wired;
        this.wireless=wireless;
        this.type=type;
    }

    public String getType(){return type;}
    public String getWired(){ return wired;}
    public String getWireless(){ return wireless;}
    
    
}
