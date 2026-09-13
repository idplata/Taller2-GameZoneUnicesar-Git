/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.gamezone.model;


public class Cable extends Accesory{
    private String length;
    private String connectortype;
  
    public Cable(String id, String title, String price, String stock, String length, String connectortype) {
        super(id, title, price, stock);
        this.length=length;
        this.connectortype=connectortype;
    }
    
    public String getConnectortype(){return connectortype;}
    public String getLenght(){ return length;}
    
    
}
