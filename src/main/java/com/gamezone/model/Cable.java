/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.gamezone.model;


public class Cable extends Accesory{
    private String length;
    private String type;
  
    public Cable(String id, String title, String price, String amount, String length, String type) {
        super(id, title, price, amount);
        this.length=length;
        this.type=type;
    }
    
    public String getType(){return type;}
    public String getLenght(){ return length;}
    
    
}
