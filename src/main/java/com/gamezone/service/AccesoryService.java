package com.gamezone.service;

import com.gamezone.model.Accesory;
import com.gamezone.model.Cable;
import com.gamezone.model.Controller;
import com.gamezone.model.Memory;
import com.gamezone.persistence.AccesoryRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

public class AccesoryService {

    private final AccesoryRepository accesoryRepository;
    private List<Accesory> accesories;

    public AccesoryService(AccesoryRepository accesoryRepository) {
        this.accesoryRepository = accesoryRepository;
        this.accesories = new ArrayList<>(accesoryRepository.loadAll());
    }

    public Controller registerController(String id, String title, double price, int quantity,
                                          String connectionType, List<String> compatibleConsoles) {
        Controller controller = new Controller(id, title, price, quantity, connectionType);
        if (compatibleConsoles != null) {
            controller.setCompatibleConsoles(new ArrayList<>(compatibleConsoles));
        }
        accesories.add(controller);
        persist();
        return controller;
    }

    public Cable registerCable(String id, String title, double price, int quantity,
                                double length, String connectorType) {
        Cable cable = new Cable(id, title, price, quantity, length, connectorType);
        accesories.add(cable);
        persist();
        return cable;
    }

    public Memory registerMemory(String id, String title, double price, int quantity,
                                  int gigabytes, String memoryType, List<String> compatibleConsoles) {
        Memory memory = new Memory(id, title, price, quantity, gigabytes, memoryType);
        if (compatibleConsoles != null) {
            memory.setCompatibleConsoles(new ArrayList<>(compatibleConsoles));
        }
        accesories.add(memory);
        persist();
        return memory;
    }

    public List<Accesory> listAllAccessories() {
        return new ArrayList<>(accesories);
    }

    public List<Accesory> listAccessoriesByType(String type) {
        List<Accesory> result = new ArrayList<>();
        for (Accesory accesory : accesories) {
            if (accesory.getAccessoryType().equalsIgnoreCase(type)) {
                result.add(accesory);
            }
        }
        return result;
    }

    public List<Accesory> findAccessoriesCompatibleWith(String consoleId) {
        List<Accesory> result = new ArrayList<>();
        for (Accesory accesory : accesories) {
            if (accesory.isCompatibleWith(consoleId)) {
                result.add(accesory);
            }
        }
        return result;
    }

    public Accesory findById(String id) {
        Optional<Accesory> found = accesories.stream()
                .filter(a -> a.getId().equals(id))
                .findFirst();
        return found.orElseThrow(() ->
                new NoSuchElementException("No accessory found with id: " + id));
    }

    public void updateStock(String accesoryId, int quantity) {
        Accesory accesory = findById(accesoryId);
        int newQuantity = accesory.getQuantity() + quantity;
        if (newQuantity < 0) {
            throw new IllegalStateException("Insufficient stock for accessory: " + accesoryId);
        }
        accesory.setQuantity(newQuantity);
        persist();
    }

    private void persist() {
        accesoryRepository.saveAll(accesories);
    }
}