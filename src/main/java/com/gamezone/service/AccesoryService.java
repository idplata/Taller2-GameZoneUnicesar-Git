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
