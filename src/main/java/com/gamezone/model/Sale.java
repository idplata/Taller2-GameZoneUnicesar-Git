/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.gamezone.model;
import java.time.LocalDateTime;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author irind
 */
public class Sale implements Serializable {
    
    private LocalDateTime date;
    private Customer customer;
    private Seller seller;
    private String saleid;
    private List<Product> products;
    
    public Sale(String saleid, Customer customer, Seller seller, List<Product> products){
        this.date = LocalDateTime.now(); //I used AI for getting this specific function
        this.customer = customer;
        this.saleid = saleid;
        this.seller = seller;
        this.products = new ArrayList<>(products); //AI used to detect logic problems
    }
    
    public String getid(){ return saleid;}
    public Customer getcostumer(){return customer;}
    public Seller getseller(){return seller;}
    public List<Product> getproducts(){return new ArrayList<>(products);}
    public LocalDateTime getdate(){return date;}
    
    //SETTERS
    public void setid(String saleid){ this.saleid = saleid;}
    public void setcustomer(Customer customer){this.customer = customer;}
    public void setseller(Seller seller){this.seller = seller;}
    public void setproducts(List<Product> products){this.products = new ArrayList<>(products);}
    public void setdate(LocalDateTime date){this.date = date;}
    
    
    public double calculateprice(){
    
        double sum = 0;
        for (Product product: products){
        
            sum = sum + product.getPrice();
        
        }
        return sum;
    }
    
    
    @Override
    public String toString(){
        
        return "Sale"
                + "id :" + saleid + ", date: " + date + ", total" + calculateprice();
        
    }
}

