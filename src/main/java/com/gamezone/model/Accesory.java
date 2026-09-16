package com.gamezone.model;

import java.util.ArrayList;
import java.util.List;

public abstract class Accesory extends Product {

    private List<String> compatibleConsoles;

    public Accesory(String id, String title, double price, int quantity) {
        super(id, title, price, quantity);
        this.compatibleConsoles = new ArrayList<>();
    }

    public List<String> getCompatibleConsoles() {
        return compatibleConsoles;
    }

    public void setCompatibleConsoles(List<String> compatibleConsoles) {
        this.compatibleConsoles = compatibleConsoles;
    }

    public void addCompatibleConsole(String consoleId) {
        if (!compatibleConsoles.contains(consoleId)) {
            compatibleConsoles.add(consoleId);
        }
    }

    public boolean isCompatibleWith(String consoleId) {
        return compatibleConsoles.contains(consoleId);
    }

    public abstract String getAccessoryType();

    @Override
    public String getDescription() {
        return "Id: " + getId() +
               " | Title: " + getTitle() +
               " | Price: $" + getPrice() +
               " | Stock: " + getQuantity() +
               " | Compatible consoles: " + String.join(",", compatibleConsoles);
    }
}