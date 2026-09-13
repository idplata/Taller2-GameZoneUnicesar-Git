/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.gamezone.model;

public class Memory extends Accesory{
    private String Gigabytes;
    private String type;
  
    public Memory (String id, String title, String price, String amount, String Gigabytes, String type) {
        super(id, title, price, amount);
        this.Gigabytes=Gigabytes;
        this.type=type;
    }
    
    public String getType(){return type;}
    public String getGigabyte(){ return Gigabytes;}
    
    
}
