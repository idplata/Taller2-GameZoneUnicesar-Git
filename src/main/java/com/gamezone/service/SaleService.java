/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.gamezone.service;
import com.gamezone.model.*;
import com.gamezone.persistence.SaleRepository;
import java.util.List;

/**
 *
 * @author irind
 */
public class SaleService {
    private SaleRepository saleRepository;
    private ProductService productService;
    private PersonService personService;
    
    public SaleService(SaleRepository saleRepository, ProductService productService,PersonService personService) {
        this.saleRepository = saleRepository;
        this.productService = productService;
        this.personService = personService;
    }
    
    public void registerSale(Sale sale){
    
     if(sale.getproducts() == null || sale.getproducts().isEmpty()){
     
         throw new IllegalArgumentException("A sale must have at least one product. Try Again");
         
     }
     
     for(Product p:sale.getproducts()){
      
         if(!productService.hasEnoughStock(p.getId(), 1)){
             
             throw new IllegalArgumentException("Insufficient stock for product: " + p.getTitle());
         }
     
         productService.reduceStock(p.getId(), 1);
     
     }
     
     saleRepository.savesales(sale);
    }
    
    
    public List<Sale> getAllSales(){
    
        return saleRepository.findAllSales();
    }
    
    public List<Sale> getCustomerHistory(String customerId){
    
        return saleRepository.FindSaleByCustomer(customerId);
    }
    
    public List<Sale> getSellerHistory(String SellerId){
    
        return saleRepository.FindBySeller(SellerId);
    }
   
    public double getTotalRevenue() {
    double total = 0;
    for (Sale sale : saleRepository.findAllSales()) {
        total += sale.calculateprice();
    }
    return total;
}
   }

    
