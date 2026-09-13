/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.gamezone.model;

public class Controller extends Accesory{
    private String  connectionType;
  
    public Controller (String id, String title, String price, String stock,String  connectionType) {
        super(id, title, price, stock);
        this.connectionType=connectionType;
    }

    public String getConnectionType(){return connectionType;}
    
    
}
