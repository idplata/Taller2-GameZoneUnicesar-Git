package com.gamezone.model;

public class Customer extends Person {

    protected String email;

    public Customer(String id, String name, String phone, String email){
        super(id, name, phone);
        this.email = email;
    }
    
    public String getEmail(){return email;}

    public void setEmail(String email){this.email = email;}

    @Override
    public String getRoleDescription() {
       return "Customer: " + getName() + " | Email: " + email + ")";
    }
}
