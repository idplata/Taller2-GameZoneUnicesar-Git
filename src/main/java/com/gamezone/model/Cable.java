package com.gamezone.model;

public class Cable extends Accesory {

    private double length;
    private String connectorType;

    public Cable(String id, String title, double price, int quantity, double length, String connectorType) {
        super(id, title, price, quantity);
        this.length = length;
        this.connectorType = connectorType;
    }

    public double getLength() {
        return length;
    }

    public void setLength(double length) {
        this.length = length;
    }

    public String getConnectorType() {
        return connectorType;
    }

    public void setConnectorType(String connectorType) {
        this.connectorType = connectorType;
    }

    @Override
    public String getAccessoryType() {
        return "CABLE";
    }

    @Override
    public String getDescription() {
        return super.getDescription() + " | Length: " + length + "m | Connector: " + connectorType;
    }
}