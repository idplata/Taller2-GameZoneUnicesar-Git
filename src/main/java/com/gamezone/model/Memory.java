package com.gamezone.model;

public class Memory extends Accesory {

    private int gigabytes;
    private String memoryType;

    public Memory(String id, String title, double price, int quantity, int gigabytes, String memoryType) {
        super(id, title, price, quantity);
        this.gigabytes = gigabytes;
        this.memoryType = memoryType;
    }

    public int getGigabytes() {
        return gigabytes;
    }

    public void setGigabytes(int gigabytes) {
        this.gigabytes = gigabytes;
    }

    public String getMemoryType() {
        return memoryType;
    }

    public void setMemoryType(String memoryType) {
        this.memoryType = memoryType;
    }

    @Override
    public String getAccessoryType() {
        return "MEMORY";
    }

    @Override
    public String getDescription() {
        return super.getDescription() + " | Capacity: " + gigabytes + "GB | Type: " + memoryType;
    }
}