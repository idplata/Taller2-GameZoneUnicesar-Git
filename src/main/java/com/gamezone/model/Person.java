package com.gamezone.model;

/**
 * Clase abstracta base que representa a cualquier persona que interactúa
 * con la tienda GameZone Unicesar. Centraliza los atributos y el
 * comportamiento compartidos por todos los tipos de persona (clientes,
 * vendedores, etc.).
 * <p>
 * Esta clase no puede instanciarse directamente: una persona en el
 * sistema siempre debe tener un rol específico (un {@link Customer} o
 * un {@link Seller}). Cada subclase es responsable de describir su
 * propio rol a través de {@link #getRoleDescription()}.
 */
public abstract class Person{

    protected String id;
    protected String name;
    protected String phone;    
   
    public Person(String id, String name, String phone){
        this.id = id;
        this.name = name;
        this.phone = phone;
    }

    public String getId(){return id;}

    public void setId(String id){this.id = id;}

    public String getName(){return name;}

    public void setName(String name){this.name = name;}

    public String getPhone(){return phone;}

    public void setPhone(String phone){this.phone = phone;}
     /**
     * Devuelve una descripción corta del rol específico que cumple esta
     * persona en la tienda (cliente, vendedor, etc.). Cada subclase debe
     * dar su propia implementación, ya que la clase base no sabe qué rol
     * representa.
     *
     * @return una descripción legible del rol de la persona
     */

    public abstract String getRoleDescription();
}