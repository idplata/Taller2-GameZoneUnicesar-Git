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
    private static final String FILE_PATH = DATA_DIRECTORY + "/accessories.csv";
    private static final String SEPARATOR = ";";
    private static final String CONSOLE_LIST_DELIMITER = "\\|";
    private static final String CONSOLE_LIST_JOINER = "|";
 
    public AccesoryRepository() {
        try {
            Files.createDirectories(Paths.get(DATA_DIRECTORY));
        } catch (IOException e) {
            throw new RuntimeException("Could not create data directory", e);
        }
    }
     public void saveAll(List<Accessory> accessories) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (Accessory accessory : accessories) {
                writer.write(toCsvLine(accessory));
                writer.newLine();
            }
        } catch (IOException e) {
            throw new RuntimeException("Error saving accessories", e);
        }
    }
 
     return accessories;
    }
    public List<Accessory> loadAll() {
        List<Accessory> accessories = new ArrayList<>();
        Path path = Paths.get(FILE_PATH);
        if (!Files.exists(path)) {
            return accessories;
        }
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.isBlank()) {
                    continue;
                }
                Accessory accessory = fromCsvLine(line);
                if (accessory != null) {
                    accessories.add(accessory);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Error loading accessories", e);
        }
        return accessories;
    }
    private String toCsvLine(Accessory accessory) {
        String consoleIds = String.join(CONSOLE_LIST_JOINER, accessory.getCompatibleConsoleIds());
        StringBuilder line = new StringBuilder();
        line.append(accessory.getTypeDiscriminator()).append(SEPARATOR)
                .append(accessory.getId()).append(SEPARATOR)
                .append(accessory.getTitle()).append(SEPARATOR)
                .append(accessory.getPrice()).append(SEPARATOR)
                .append(accessory.getStock()).append(SEPARATOR)
                .append(consoleIds).append(SEPARATOR);
 
        if (accessory instanceof Controller controller) {
            line.append(controller.getConnectionType());
        } else if (accessory instanceof Cable cable) {
            line.append(cable.getLengthMeters()).append(SEPARATOR).append(cable.getConnectorType());
        } else if (accessory instanceof Memory memory) {
            line.append(memory.getCapacityGb()).append(SEPARATOR).append(memory.getMemoryType());
        }
 
        return line.toString();
    }
     private Accessory fromCsvLine(String line) {
        String[] fields = line.split(SEPARATOR, -1);
 
        String type = fields[0];
        String id = fields[1];
        String title = fields[2];
        double price = Double.parseDouble(fields[3]);
        int stock = Integer.parseInt(fields[4]);
        String consoleIdsRaw = fields[5];
 
        List<String> compatibleConsoleIds = new ArrayList<>();
        if (!consoleIdsRaw.isBlank()) {
            for (String consoleId : consoleIdsRaw.split(CONSOLE_LIST_DELIMITER)) {
                compatibleConsoleIds.add(consoleId);
            }
        }
