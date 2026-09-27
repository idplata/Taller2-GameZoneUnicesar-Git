package com.gamezone.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PromotionService {
    private final PromotionRepository repository;
    private final List<Promotion> promotions;
    
    public PromotionService(PromotionRepository repository) {
        this.repository = repository;
        this.promotions = repository.loadAll();
    }
    
     public Promotion registerPercentageDiscount(String id, String name, LocalDate startDate,LocalDate endDate, double percentage) {
        Promotion promotion = new PercentageDiscount(id, name, startDate, endDate, percentage);
        addAndSave(promotion);
        return promotion;
    }
      public Promotion registerCategoryDiscount(String id, String name, LocalDate startDate,LocalDate endDate, double percentage, String targetCategory) {
        Promotion promotion = new CategoryDiscount(id, name, startDate, endDate, percentage, targetCategory);
        addAndSave(promotion);
        return promotion;
    }
       public Promotion registerBulkPurchaseDiscount(String id, String name, LocalDate startDate,LocalDate endDate, int minimumQuantity, double percentage) {
        Promotion promotion = new BulkPurchaseDiscount(id, name, startDate, endDate, minimumQuantity, percentage);
        addAndSave(promotion);
        return promotion;
    }
}
