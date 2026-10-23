package com.gamezone.model;
 
import java.time.LocalDate;
 
/*
 * Representa la garantía extendida: cubre defectos de fábrica y daños
 * accidentales, dura 12 meses desde la fecha de venta, y tiene un costo
 * adicional equivalente al 10% del precio del producto asociado. Se
 * asigna solo de forma opcional, cuando el vendedor decide ofrecerla.
 */
public class ExtendedWarranty extends Warranty {
 
    private static final int DURATION_IN_MONTHS = 12;
    private static final double COST_RATE = 0.10;
 
    /* Crea una garantía extendida a partir de la fecha de inicio indicada. */
    public ExtendedWarranty(String id, Product product, Sale sale, LocalDate startDate) {
        super(id, product, sale, startDate);
    }
    
    /* La garantía extendida dura 12 meses. */
    @Override
    public int getDurationInMonths() {
        return DURATION_IN_MONTHS;
    }
 
    /* Nombre del tipo de garantía mostrado al usuario. */
    @Override
    public String getWarrantyType() {
        return "Garantía Extendida";
    }
 
    /*
     * El costo adicional es el 10% del precio del producto asociado.
     * Se calcula cada vez a partir del precio actual del producto, en
     * lugar de guardarlo como atributo, para no duplicar un dato que
     * siempre se puede derivar.
     */
    @Override
    public double getAdditionalCost() {
        return getProduct().getPrice() * COST_RATE;
    }
}
