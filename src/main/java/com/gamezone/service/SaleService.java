/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.gamezone.service;
import com.gamezone.model.*;
import com.gamezone.persistence.SaleRepository;
import java.util.List;
import java.util.Optional;
import java.util.Map;
import java.util.LinkedHashMap;
import java.time.LocalDate;
import java.util.ArrayList;

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
        public SaleService(SaleRepository saleRepository, ProductService productService, PersonService personService,
                       AccesoryService accesoryService, PromotionService promotionService, WarrantyService warrantyService) {
            this.saleRepository = saleRepository;
            this.productService = productService;
            this.personService = personService;
            this.accesoryService = accesoryService;
            this.warrantyService = warrantyService;
            this.promotionService = promotionService;
        }
    
        public void registerSale(Sale sale, List<String> productIdsWithExtendedWarranty) {
     
            if (sale == null || sale.getProducts().isEmpty()) {
                throw new IllegalArgumentException("A sale must have at least one product.");
            }
            if (sale.getCustomer() == null || sale.getSeller() == null) {
                throw new IllegalArgumentException("The sale must have a customer and a seller.");
            }
            List<String> extendedIds = productIdsWithExtendedWarranty == null
                    ? new ArrayList<>() : new ArrayList<>(productIdsWithExtendedWarranty);

        
            Map<String, Integer> productUnits = new LinkedHashMap<>();
            Map<String, Integer> accessoryUnits = new LinkedHashMap<>();
            countUnits(sale, productUnits, accessoryUnits);
            validateStock(sale, productUnits, accessoryUnits);
            validateExtendedWarrantyRequests(sale, extendedIds);

        
            discountStock(productUnits, accessoryUnits);
            try {
        
                double subtotal = sale.calculateSubtotal();
                applyWarranties(sale, extendedIds);

                Promotion best = promotionService.findBestPromotionFor(sale);
                if (best != null) {
                    sale.setAppliedPromotionName(best.getName());
                    sale.setDiscountAmount(Math.min(best.calculateDiscount(sale), subtotal));
                }

            
                saleRepository.saveSales(sale);
            } catch (RuntimeException e) {
                    restoreStock(productUnits, accessoryUnits);
                    throw e;
            }
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
    
    
    
    private void countUnits(Sale sale, Map<String, Integer> productUnits, Map<String, Integer> accessoryUnits) {
        for (Product p : sale.getProducts()) {
            Map<String, Integer> target = (p instanceof Accesory) ? accessoryUnits : productUnits;
            target.merge(p.getId(), 1, Integer::sum);
        }
    }

    private void validateStock(Sale sale, Map<String, Integer> productUnits, Map<String, Integer> accessoryUnits) {
        for (Map.Entry<String, Integer> entry : productUnits.entrySet()) {
            Product product = productService.findById(entry.getKey());
            if (product == null) {
                throw new IllegalArgumentException("Product not found: " + entry.getKey());
            }
            if (product.getQuantity() < entry.getValue()) {
                throw new IllegalArgumentException(String.format(
                        "Insufficient stock for %s: requested %d, available %d..",
                        product.getTitle(), entry.getValue(), product.getQuantity()));
            }
        }
        for (Map.Entry<String, Integer> entry : accessoryUnits.entrySet()) {
            Accesory accesory;
            try {
                accesory = accesoryService.findById(entry.getKey());
            } catch (RuntimeException e) {
                throw new IllegalArgumentException("Accessory not found: " + entry.getKey());
            }
            if (accesory.getQuantity() < entry.getValue()) {
                throw new IllegalArgumentException(String.format(
                        "Insufficient stock for accessory %s: requested %d, available %d.",
                        accesory.getTitle(), entry.getValue(), accesory.getQuantity()));
            }
        }
    }
        private void validateExtendedWarrantyRequests(Sale sale, List<String> extendedIds) {
        Map<String, Integer> consoleUnits = new LinkedHashMap<>();
        for (Product p : sale.getProducts()) {
            if (p instanceof Console) {
                consoleUnits.merge(p.getId(), 1, Integer::sum);
            }
        }
        Map<String, Integer> requested = new LinkedHashMap<>();
        for (String id : extendedIds) {
            requested.merge(id, 1, Integer::sum);
        }
        for (Map.Entry<String, Integer> entry : requested.entrySet()) {
            int available = consoleUnits.getOrDefault(entry.getKey(), 0);
            if (available == 0) {
                throw new IllegalArgumentException(
                        "Extended warranty only applies to consoles included in the sale: " + entry.getKey());
            }
            if (entry.getValue() > available) {
                throw new IllegalArgumentException(
                        "More extended warranties were requested than units of console  " + entry.getKey() + " in the sale.");
            }
        }
    }
    
    private void discountStock(Map<String, Integer> productUnits, Map<String, Integer> accessoryUnits) {
        for (Map.Entry<String, Integer> entry : productUnits.entrySet()) {
            productService.reduceStock(entry.getKey(), entry.getValue());
        }
        for (Map.Entry<String, Integer> entry : accessoryUnits.entrySet()) {
            accesoryService.updateStock(entry.getKey(), -entry.getValue());
        }
    }
    
    private void restoreStock(Map<String, Integer> productUnits, Map<String, Integer> accessoryUnits) {
        for (Map.Entry<String, Integer> entry : productUnits.entrySet()) {
            productService.restoreStock(entry.getKey(), entry.getValue());
        }
        for (Map.Entry<String, Integer> entry : accessoryUnits.entrySet()) {
            accesoryService.updateStock(entry.getKey(), entry.getValue());
        }
    }
        
        private void applyWarranties(Sale sale, List<String> extendedIds) {
            List<String> remaining = new ArrayList<>(extendedIds);
            LocalDate startDate = sale.getDate().toLocalDate();
            double extraCost = 0;
            for (Product product : sale.getProducts()) {
                if (product instanceof Console) {
                    warrantyService.assignBasicWarranty(product, sale, startDate);
                    if (remaining.remove(product.getId())) {
                        ExtendedWarranty extended = warrantyService.assignExtendedWarranty(product, sale, startDate);
                        extraCost += extended.getAdditionalCost();
                        sale.addExtendedWarrantyTitle(product.getTitle());
                    }
                }
            }
            sale.setExtendedWarrantyCost(extraCost);
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

    
