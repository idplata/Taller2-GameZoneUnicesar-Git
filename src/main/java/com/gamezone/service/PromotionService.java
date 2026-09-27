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
}
