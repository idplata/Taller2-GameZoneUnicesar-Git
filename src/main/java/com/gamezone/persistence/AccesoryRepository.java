/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.gamezone.persistence;

import com.gamezone.model.Cable;
import com.gamezone.model.Controller;
import com.gamezone.model.Memory;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class AccesoryRepository {
    private static final String DATA_DIRECTORY = "data";
    private static final String CONTROLLER_FILE = DATA_DIRECTORY + "/controller.csv";
    private static final String MEMORY_FILE = DATA_DIRECTORY + "/memory.csv";
    private static final String CABLE_FILE = DATA_DIRECTORY + "/cable.csv";
    private static final String SEPARATOR = ";";
    
        public AccesoryRepository() {
        try {
            Files.createDirectories(Paths.get(DATA_DIRECTORY));
        } catch (IOException e) {
            throw new RuntimeException("Could not create data directory", e);
        }
//        String id, String title, String price, String amount,String wired,String wireless, String type
        public void saveController(List <Controller> controllers){
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(CONTROLLER_FILE))) {
            for (Controller controller : controllers) {
                writer.write(String.join(SEPARATOR,
                        controller.getId(),
                        controller.getTitle(),
                        controller.getPrice(),
                        controller.getAmount(),
                        controller.getType(),
                        controller.getWired(),
                        controller.getWireless()));
                writer.newLine();
            }
        } catch (IOException e) {
            throw new RuntimeException("Error saving controllers", e);
        }
    }
         //CAMBIAR List<Customer> POR LA 
        public List<Controller> loadController() {
        List<Controller> controllers = new ArrayList<>();
        Path path = Paths.get(CONTROLLER_FILE);
        if (!Files.exists(path)) {
            return controllers;
        }
        try (BufferedReader reader = new BufferedReader(new FileReader(CONTROLLER_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.isBlank()) {
                    continue;
                }
                String[] fields = line.split(SEPARATOR, -1);
                controllers.add(new Controller(fields[0], fields[1], fields[2], fields[3],fields[4],fields[5], fields[6]));
            }
        } catch (IOException e) {
            throw new RuntimeException("Error loading customers", e);
        }
        return controllers;
    }
        // COLOCAR LISTA DE CABLE
        public void saveCable(List<Cable>cables) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(CABLE_FILE))) {
            for (Cable cable : cables) {
                writer.write(String.join(SEPARATOR,
                        cable.getId(),
                        cable.getTitle(),
                        cable.getPrice(),
                        cable.getAmount(),
                        cable.getType(),
                        cable.getLenght()));
                writer.newLine();
            }
        } catch (IOException e) {
            throw new RuntimeException("Error saving cables", e);
        }
    }
        //CAMBIAR List<Customer> POR LA
        public List<Cable> loadCable() {
        List<Cable> cables = new ArrayList<>();
        Path path = Paths.get(CABLE_FILE);
        if (!Files.exists(path)) {
            return cables;
        }
        try (BufferedReader reader = new BufferedReader(new FileReader(CABLE_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.isBlank()) {
                    continue;
                }
                String[] fields = line.split(SEPARATOR, -1);
                cables.add(new Cable(fields[0], fields[1], fields[2], fields[3], fields[4],fields[5] ));
            }
        } catch (IOException e) {
            throw new RuntimeException("Error loading cable", e);
        }
        return cables;
    }
        
        public void saveMemory( List<Memory> memories) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(MEMORY_FILE))) {
            for (Memory memory : memories) {
                writer.write(String.join(SEPARATOR,
                        memory.getId(),
                        memory.getTitle(),
                        memory.getPrice(),
                        memory.getAmount(),
                        memory.getType(),
                        memory.getGigabyte()));
                writer.newLine();
            }
        } catch (IOException e) {
            throw new RuntimeException("Error saving memories", e);
        }
    }
        //CAMBIAR List<Customer> POR LA
        public List<Memory> loadMemory() {
        List<Memory> memories = new ArrayList<>();
        Path path = Paths.get(MEMORY_FILE);
        if (!Files.exists(path)) {
            return memories;
        }
        try (BufferedReader reader = new BufferedReader(new FileReader(MEMORY_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.isBlank()) {
                    continue;
                }
                String[] fields = line.split(SEPARATOR, -1);
                memories.add(new Memory(fields[0], fields[1], fields[2], fields[3], fields[4],fields[5]));
            }
        } catch (IOException e) {
            throw new RuntimeException("Error loading cable", e);
        }
        return memories;
    }
        
    }

