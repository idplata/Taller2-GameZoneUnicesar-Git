package com.gamezone.model;

import java.time.LocalDate;
 
/*
 * Clase abstracta base que representa una garantía asociada a un
 * producto vendido dentro de una venta específica. Centraliza los
 * atributos y el comportamiento comunes a todos los tipos de garantía
 * (identificador, producto, venta, fechas de vigencia), mientras que
 * los aspectos que dependen del tipo (duración, nombre del tipo y costo
 * adicional) se declaran como métodos abstractos que cada subclase debe
 * implementar.
 *
 * Esta clase no puede instanciarse directamente: una garantía siempre
 * debe ser de un tipo concreto (BasicWarranty o ExtendedWarranty).
 */
public abstract class Warranty {
 
    private String id;
    private Product product;
    private Sale sale;
    private LocalDate startDate;
    private LocalDate endDate;
 
    /*
     * Crea una nueva garantía. La fecha de fin no se recibe como
     * parámetro: se calcula automáticamente sumando a la fecha de inicio
     * la duración en meses que cada subclase define en
     * getDurationInMonths().
     */
    public Warranty(String id, Product product, Sale sale, LocalDate startDate) {
        this.id = id;
        this.product = product;
        this.sale = sale;
        this.startDate = startDate;
        this.endDate = startDate.plusMonths(getDurationInMonths());
    }
    
     public String getId() {
        return id;
    }
 
    /* Retorna el producto cubierto por esta garantía. */
    public Product getProduct() {
        return product;
    }
 
    /* Retorna la venta en la que se otorgó esta garantía. */
    public Sale getSale() {
        return sale;
    }
 
    /* Retorna la fecha de inicio de la garantía (la fecha de la venta). */
    public LocalDate getStartDate() {
        return startDate;
    }
 
    /* Retorna la fecha de fin de la garantía, calculada según su duración. */
    public LocalDate getEndDate() {
        return endDate;
    }
 
    /*
     * Retorna la duración de la garantía en meses. Cada tipo de garantía
     * define su propia duración, por eso este método es abstracto.
     */
    public abstract int getDurationInMonths();
 
    /*
     * Retorna el nombre legible del tipo de garantía (por ejemplo,
     * "Garantía Básica"). Es abstracto porque cada subclase se
     * identifica de forma distinta.
     */
    public abstract String getWarrantyType();
 
 
}
