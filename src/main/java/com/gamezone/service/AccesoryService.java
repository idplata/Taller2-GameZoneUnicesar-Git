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
    
    private final AccesoryRepository repository;
    private final List<Cable> cables;
    private final List<Controller>controllers ;
    private final List<Memory> memories;
    
     public AccesoryService(AccesoryRepository repository) {
        this.repository = repository;
        this.cables = repository.loadCable();
        this.memories = repository.loadMemory();
        this.controllers = repository.loadController();
    }
