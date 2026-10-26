/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.gamezone.service;
import com.gamezone.model.*;
import com.gamezone.persistence.SaleRepository;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author irind
 */
public class SaleService {
    private SaleRepository saleRepository;
    private ProductService productService;
    private PersonService personService;
    private AccesoryService accesoryService;
    private PromotionService promotionService;
    private WarrantyService warrantyService;
   
    
    public SaleService(SaleRepository saleRepository, ProductService productService,PersonService personService, AccesoryService accesoryService, PromotionService promotionService, WarrantyService warrantyService) {
        this.saleRepository = saleRepository;
        this.productService = productService;
        this.personService = personService;
        this.accesoryService = accesoryService;
        this.warrantyService=warrantyService;
        this.promotionService = promotionService;
        
    }
    
    public void registerSale(Sale sale){
    
     if(sale.getProducts() == null || sale.getProducts().isEmpty()){
     
         throw new IllegalArgumentException("A sale must have at least one product. Try Again");
         
     }
     
     for(Product p:sale.getProducts()){
      
         if (p instanceof Accesory){
         
             Accesory accesory = (Accesory) p;
             
            if(!hasEnoughStockAccesory(accesory.getId(), 1)){
            
                throw new IllegalArgumentException(
                
                        "insufficient stock for accesory: " + accesory.getTitle()
                        
                );
                
            }
            accesoryService.updateStock(accesory.getId(),-1);
         }else{
             if(!productService.hasEnoughStock(p.getId(), 1)){
             
                 throw new IllegalArgumentException(
                 
                         "Insufficient stock for product: " + p.getTitle()
                 
                 );
             }
             
           productService.reduceStock(p.getId(), 1);
         
         }
     
        
     
     }
     Promotion best = promotionService.findBestPromotionFor(sale);
        if (best != null) {
            sale.setAppliedPromotionName(best.getName());
            sale.setDiscountAmount(best.calculateDiscount(sale));
        }
     
     saleRepository.saveSales(sale);
    }
    
    
    public List<Sale> getAllSales(){
    
        return saleRepository.findAllSales();
    }
    
    public List<Sale> listAllSales() {
        return getAllSales();
    }
    public Optional<Sale> findSaleById(String saleId) {
        return saleRepository.findAllSales().stream()
                .filter(sale -> sale.getId().equals(saleId))
                .findFirst();
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

        total += sale.getTotal();
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
    
     public double getMonthlySalesTotal(int month, int year) {
        double total = 0;
        for (Sale sale : getAllSales()) {
            if (sale.getDate().getMonthValue() == month && sale.getDate().getYear() == year) {
                total += sale.getTotal();
            }
        }
        return total;
    }
   }

    
