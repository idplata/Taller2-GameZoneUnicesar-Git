/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.gamezone.model;

public class Memory extends Accesory{
    private String Gigabytes;
    private String memorytype;
  
    public Memory (String id, String title, String price, String stock, String Gigabytes, String memorytype) {
        super(id, title, price, stock);
        this.Gigabytes=Gigabytes;
        this.memorytype=memorytype;
    }
    
    public String getMemoryType(){return memorytype;}
    public String getGigabyte(){ return Gigabytes;}
    
    
}
