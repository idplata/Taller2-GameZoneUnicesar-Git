package com.gamezone.model;

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

    @Override
    public String getRoleDescription() {
        return "Seller (employee code: " + employeeCode + ", shift: " + shift + ")";
    }
}
