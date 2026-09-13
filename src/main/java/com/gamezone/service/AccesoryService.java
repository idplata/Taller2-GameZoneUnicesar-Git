/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.gamezone.service;
import com.gamezone.model.Cable;
import com.gamezone.model.Controller;
import com.gamezone.model.Memory;
import com.gamezone.persistence.AccesoryRepository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class AccesoryService {
    
    
    private final AccesoryRepository accessoryRepository;
    private List<Accessory> accessories;
 
    public AccessoryService(AccesoryRepository accessoryRepository) {
        this.accessoryRepository = accessoryRepository;
        this.accessories = new ArrayList<>(accessoryRepository.loadAll());
    }
    public Controller registerController(String id, String title, double price, int stock,
                                          String connectionType, List<String> compatibleConsoleIds) {
        Controller controller = new Controller(id, title, price, stock, connectionType);
        if (compatibleConsoleIds != null) {
            controller.setCompatibleConsoleIds(new ArrayList<>(compatibleConsoleIds));
        }
        accessories.add(controller);
        persist();
        return controller;
    }
     public Cable registerCable(String id, String title, double price, int stock,
                                double lengthMeters, String connectorType) {
        Cable cable = new Cable(id, title, price, stock, lengthMeters, connectorType);
        accessories.add(cable);
        persist();
        return cable;
    }
     public Memory registerMemory(String id, String title, double price, int stock,
                                  int capacityGb, String memoryType, List<String> compatibleConsoleIds) {
        Memory memory = new Memory(id, title, price, stock, capacityGb, memoryType);
        if (compatibleConsoleIds != null) {
            memory.setCompatibleConsoleIds(new ArrayList<>(compatibleConsoleIds));
        }
        accessories.add(memory);
        persist();
        return memory;
    }
      public List<Accessory> listAllAccessories() {
        return new ArrayList<>(accessories);
    }
 
    public List<Accessory> listAccessoriesByType(String type) {
        List<Accessory> result = new ArrayList<>();
        for (Accessory accessory : accessories) {
            if (accessory.getTypeDiscriminator().equalsIgnoreCase(type)) {
                result.add(accessory);
            }
        }
        return result;
    }
       public void updateStock(String accessoryId, int quantity) {
        Accessory accessory = findById(accessoryId);
        int newStock = accessory.getStock() + quantity;
        if (newStock < 0) {
            throw new IllegalStateException("Insufficient stock for accessory: " + accessoryId);
        }
        accessory.setStock(newStock);
        persist();
    }
 
    private void persist() {
        accessoryRepository.saveAll(accessories);
    }
}
