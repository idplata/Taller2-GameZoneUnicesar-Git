package com.gamezone.model;
/**
 * Representa a un cliente de la tienda GameZone Unicesar: una persona
 * que compra productos. Además de los atributos que comparte con
 * {@link Person}, un cliente tiene un correo electrónico usado para
 * contacto y (opcionalmente) notificaciones.
 * <p>
 * El historial de compras NO se guarda dentro de esta clase, a propósito,
 * para evitar duplicar datos que ya existen en el módulo de ventas. El
 * servicio de ventas es el encargado de consultar el historial de compras
 * del cliente por su id, manteniendo una única fuente de verdad.
 */
public class Customer extends Person {

    private String email;

    public Customer(String id, String name, String phone, String email){
        super(id, name, phone);
        this.email = email;
    }
    
    public String getEmail(){return email;}

    public void setEmail(String email){this.email = email;}
    
    //Sobreescribe el metodo
    @Override
    public String getRoleDescription() {
       return "Customer: " + getName() + " | Email: " + email + ")";
    }
}
