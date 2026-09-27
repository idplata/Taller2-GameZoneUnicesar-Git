package com.gamezone.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/*
 * Contiene las reglas de negocio del módulo de promociones: registrar
 * nuevas promociones de cada tipo, listar todas o solo las vigentes, y
 * seleccionar la mejor promoción aplicable para una venta dada.
 *
 * Esta es la única clase del módulo autorizada para invocar a
 * PromotionRepository; la capa de interfaz de usuario y otros servicios
 * (como SaleService) siempre deben pasar por este servicio en lugar de
 * acceder directamente a la persistencia.
 */
public class PromotionService {
    private final PromotionRepository repository;
    private final List<Promotion> promotions;
    
        /* Crea el servicio y carga las promociones previamente persistidas. */
    public PromotionService(PromotionRepository repository) {
        this.repository = repository;
        this.promotions = repository.loadAll();
    }
    
        /* Registra una nueva promoción de tipo porcentaje, aplicada sobre el total de la venta. */
     public Promotion registerPercentageDiscount(String id, String name, LocalDate startDate,LocalDate endDate, double percentage) {
        Promotion promotion = new PercentageDiscount(id, name, startDate, endDate, percentage);
        addAndSave(promotion);
        return promotion;
    }
     
    /*
     * Registra una nueva promoción de tipo categoría, aplicada solo a
     * los productos que pertenecen a la categoría objetivo indicada
     * ("VIDEOGAME" o "CONSOLE").
     */
      public Promotion registerCategoryDiscount(String id, String name, LocalDate startDate,LocalDate endDate, double percentage, String targetCategory) {
        Promotion promotion = new CategoryDiscount(id, name, startDate, endDate, percentage, targetCategory);
        addAndSave(promotion);
        return promotion;
    }
      
    /*
     * Registra una nueva promoción por volumen de compra, aplicada sobre
     * el total de la venta solo cuando la cantidad de productos alcanza
     * el mínimo indicado.
     */
       public Promotion registerBulkPurchaseDiscount(String id, String name, LocalDate startDate,LocalDate endDate, int minimumQuantity, double percentage) {
        Promotion promotion = new BulkPurchaseDiscount(id, name, startDate, endDate, minimumQuantity, percentage);
        addAndSave(promotion);
        return promotion;
    }
       
     /* Devuelve todas las promociones alguna vez registradas, incluyendo vencidas o futuras. */
        public List<Promotion> listAllPromotions() {
        return Collections.unmodifiableList(promotions);
    }
        
    /* Devuelve las promociones vigentes hoy (la fecha actual cae dentro de su rango de vigencia). */        
        public List<Promotion> listActivePromotions() {
        LocalDate today = LocalDate.now();
        List<Promotion> active = new ArrayList<>();
        for (Promotion promotion : promotions) {
            if (promotion.isActive(today)) {
                active.add(promotion);
            }
        }
        return active;
    }
        
    /*
     * Determina qué promoción vigente otorga el mayor descuento monetario
     * para la venta indicada. Las promociones no son acumulables, así que
     * como máximo se aplica una sola.
     *
     * Esta lógica de selección pertenece a la capa de servicios (no a
     * Sale ni al menú de consola) porque es una regla de negocio que
     * depende de consultar todas las promociones registradas; el modelo
     * no debe conocer la colección completa de promociones, y la
     * interfaz de usuario no debe saber cómo se calcula "el mejor
     * descuento".
     *
     * Retorna null si ninguna promoción vigente aplica, o si el mayor
     * descuento encontrado es cero.
     */
        public Promotion findBestPromotionFor(Sale sale) {
        Promotion bestPromotion = null;
        double bestDiscount = 0.0;
 
        for (Promotion promotion : listActivePromotions()) {
            double discount = promotion.calculateDiscount(sale);
            if (discount > bestDiscount) {
                bestDiscount = discount;
                bestPromotion = promotion;
            }
        }
 
        return bestPromotion;
    }
    /* Busca una promoción por su identificador único. Retorna null si no existe ninguna con ese id. */
        public Promotion findById(String id) {
        for (Promotion promotion : promotions) {
            if (promotion.getId().equals(id)) {
                return promotion;
            }
        }
        return null;
    }
    /* Agrega una promoción a la lista en memoria y persiste inmediatamente la lista actualizada. */
        private void addAndSave(Promotion promotion) {
        promotions.add(promotion);
        repository.saveAll(promotions);
    }
}
