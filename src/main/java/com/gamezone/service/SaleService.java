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
    private AccesoryService accesoryService;
    
    public SaleService(SaleRepository saleRepository, ProductService productService,PersonService personService, AccesoryService accesoryService) {
        this.saleRepository = saleRepository;
        this.productService = productService;
        this.personService = personService;
        this.accesoryService = accesoryService;
    }
    
    public void registerSale(Sale sale){
    
     if(sale.getproducts() == null || sale.getproducts().isEmpty()){
     
         throw new IllegalArgumentException("A sale must have at least one product. Try Again");
         
     }
     
     for(Product p:sale.getproducts()){
      
         if (p instanceof Accesory){
         
             Accesory accesory = (Accesory) p;
             
            if(!hasEnoughStockAccesory(accesory.getId(), 1)){
            
                throw new IllegalArgumentException(
                
                        "insufficient stock for accesory: " + accesory.getTitle()
                        
                );
                
            }
            accesoryService.updateStock(accesory.getId(),-1);
         }else{
             if(productService.hasEnoughStock(p.getId(), 1)){
             
                 throw new IllegalArgumentException(
                 
                         "Insufficient stock for product: " + p.getTitle()
                 
                 );
             }
             
           productService.reduceStock(p.getId(), 1);
         
         }
     
        
     
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
    
    
    private boolean hasEnoughStockAccesory(String id, int amount){
    
    try{
    
        Accesory accesory = accesoryService.findById(id);
        return accesory.getQuantity() >= amount;
    } catch (Exception e){
        return false;
    }
    
    }
   }

    
