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
    private String saleId;
    private List<Product> products;
    
    public Sale(String saleId, Customer customer, Seller seller, List<Product> products){
        this.date = LocalDateTime.now(); //I used AI for getting this specific function
        this.customer = customer;
        this.saleId = saleId;
        this.seller = seller;
        this.products = new ArrayList<>(products); //AI used to detect logic problems
    }
    
    public String getId(){ return saleId;}
    public Customer getCustomer(){return customer;}
    public Seller getSeller(){return seller;}
    public List<Product> getProducts(){return new ArrayList<>(products);}
    public LocalDateTime getDate(){return date;}
    
    //SETTERS
    public void setId(String saleId){ this.saleId = saleId;}
    public void setCustomer(Customer customer){this.customer = customer;}
    public void setSeller(Seller seller){this.seller = seller;}
    public void setProducts(List<Product> products){this.products = new ArrayList<>(products);}
    public void setDate(LocalDateTime date){this.date = date;}
    
    
    public double calculatePrice(){
    
        double sum = 0;
        for (Product product: products){
        
            sum = sum + product.getPrice();
        
        }
        return sum;
    }
    
    
    @Override
    public String toString(){
        
        return "Sale"
                + "id :" + saleId + ", date: " + date + ", total" + calculatePrice();
        
    }
}

