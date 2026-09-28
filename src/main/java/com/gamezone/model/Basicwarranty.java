package com.gamezone.model;
 
import java.time.LocalDate;
 
/*
 * Representa la garantía básica: cubre únicamente defectos de fábrica,
 * dura 6 meses desde la fecha de venta y no tiene costo adicional para
 * el cliente. Se genera automáticamente cuando una venta incluye una
 * consola.
 */
public class BasicWarranty extends Warranty {
 
    private static final int DURATION_IN_MONTHS = 6;
 
    /* Crea una garantía básica a partir de la fecha de inicio indicada. */
    public BasicWarranty(String id, Product product, Sale sale, LocalDate startDate) {
        super(id, product, sale, startDate);
    }
    
       /* La garantía básica dura 6 meses. */
    @Override
    public int getDurationInMonths() {
        return DURATION_IN_MONTHS;
    }
 
    /* Nombre del tipo de garantía mostrado al usuario. */
    @Override
    public String getWarrantyType() {
        return "Basic Warranty";
    }
 
    /* La garantía básica no suma ningún costo adicional a la venta. */
    @Override
    public double getAdditionalCost() {
        return 0.0;
    }
}