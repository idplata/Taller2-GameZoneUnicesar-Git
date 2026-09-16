package com.gamezone.model;

public class Controller extends Accesory {

    private String connectionType;

    public Controller(String id, String title, double price, int quantity, String connectionType) {
        super(id, title, price, quantity);
        this.connectionType = connectionType;
    }

    public String getConnectionType() {
        return connectionType;
    }

    public void setConnectionType(String connectionType) {
        this.connectionType = connectionType;
    }

    @Override
    public String getAccessoryType() {
        return "CONTROLLER";
    }

    @Override
    public String getDescription() {
        return super.getDescription() + " | Connection type: " + connectionType;
    }
}