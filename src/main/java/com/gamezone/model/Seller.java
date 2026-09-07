package com.gamezone.model;
/**
 * Representa a un vendedor (empleado) de la tienda GameZone Unicesar:
 * una persona que atiende clientes y registra ventas. Además de los
 * atributos que comparte con {@link Person}, un vendedor tiene un
 * código de empleado y un turno de trabajo asignado.
 * <p>
 * Los vendedores se precargan en el sistema en la primera ejecución y
 * no están pensados para registrarse desde la interfaz de usuario.
 */
public class Seller extends Person {

    private String employeeCode;
    private String shift;

    public Seller(String id, String name, String phone, String employeeCode, String shift) {
        super(id, name, phone);
        this.employeeCode = employeeCode;
        this.shift = shift;
    }
    
    public String getEmployeeCode(){return employeeCode;}

    public void setEmployeeCode(String employeeCode){this.employeeCode = employeeCode;}

    public String getShift(){return shift;}

    public void setShift(String shift){this.shift = shift;}

    //Sobreescribe el metodo
    @Override
    public String getRoleDescription() {
        return "Seller (employee code: " + employeeCode + ", shift: " + shift + ")";
    }
}
