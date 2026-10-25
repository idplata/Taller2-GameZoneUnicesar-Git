/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.gamezone.model;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author irind
 */
public class Sale implements Serializable {
    private static final long serialVersionUID = 1L;
    private LocalDateTime date;
    private Customer customer;
    private Seller seller;
    private String saleId;
    private List<Product> products;
    private String appliedPromotionName;
    private double discountAmount;
    
    public Sale(String saleId, Customer customer, Seller seller, List<Product> products){
        this.date = LocalDateTime.now(); //I used AI for getting this specific function
        this.customer = customer;
        this.saleId = saleId;
        this.seller = seller;
        this.products = new ArrayList<>(products);
        //AI used to detect logic problems
    }
    
    public String getId(){ return saleId;}
    public Customer getCustomer(){return customer;}
    public Seller getSeller(){return seller;}
    public List<Product> getProducts(){return new ArrayList<>(products);}
    public LocalDateTime getDate(){return date;}

    public String getAppliedPromotionName() { return appliedPromotionName; }
    public double getDiscountAmount() { return discountAmount; }
    
    //SETTERS
    public void setId(String saleId){ this.saleId = saleId;}
    public void setCustomer(Customer customer){this.customer = customer;}
    public void setSeller(Seller seller){this.seller = seller;}
    public void setProducts(List<Product> products){this.products = new ArrayList<>(products);}
    public void setDate(LocalDateTime date){this.date = date;}
    
    
    
    public void setAppliedPromotionName(String appliedPromotionName) { this.appliedPromotionName = appliedPromotionName; }
    public void setDiscountAmount(double discountAmount) { this.discountAmount = discountAmount; }
    
    
  
    public double calculateSubtotal() {
        double sum = 0;
        for (Product product : products) {
            sum += product.getPrice();
        }
        return sum;
    }

    public double calculatePrice() {
        return calculateSubtotal(); 
    }


    public double getTotal() {
            return calculateSubtotal() - discountAmount;
       }
    
    public String generateReceipt() {
        StringBuilder receipt = new StringBuilder();
        receipt.append("========== RECIBO DE VENTA ==========\n");
        receipt.append("Venta    : ").append(saleId).append("\n");
        receipt.append("Cliente  : ").append(customer.getName()).append("\n");
        receipt.append("Vendedor : ").append(seller.getName()).append("\n");
        for (Product product : products) {
            receipt.append("  - ").append(product.getTitle())
               .append(" $").append(String.format("%.2f", product.getPrice())).append("\n");
        }
        receipt.append("Subtotal : $").append(String.format("%.2f", calculateSubtotal())).append("\n");
        if (appliedPromotionName != null) {
            receipt.append("Descuento (").append(appliedPromotionName).append(") : -$")
               .append(String.format("%.2f", discountAmount)).append("\n");
        }
            receipt.append("TOTAL    : $").append(String.format("%.2f", getTotal())).append("\n");
            return receipt.toString();
        }

    public boolean canBeReturned() {
        LocalDate saleDate = date.toLocalDate();
        LocalDate limit = saleDate.plusDays(30);
        return !LocalDate.now().isAfter(limit);
    }
    public double getTotal() {
    return calculatePrice();
}
    @Override
    public String toString(){
        
        return "Sale"
                + "id :" + saleId + ", date: " + date + ", total" + calculatePrice();
        
    }
}

