package com.gamezone.persistence;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PromotionRepository {
    private static final String DATA_DIRECTORY = "data";
    private static final String PROMOTIONS_FILE = DATA_DIRECTORY + "/promotions.csv";
    private static final String SEPARATOR = ";";
    private static final String TYPE_PERCENTAGE = "PERCENTAGE";
    private static final String TYPE_CATEGORY = "CATEGORY";
    private static final String TYPE_BULK = "BULK";
    
    public PromotionRepository() {
        try {
            Files.createDirectories(Paths.get(DATA_DIRECTORY));
        } catch (IOException e) {
            throw new RuntimeException("Could not create data directory", e);
        }
    }
    
     public void saveAll(List<Promotion> promotions) {
        Path target = Paths.get(PROMOTIONS_FILE);
        Path tempFile = Paths.get(PROMOTIONS_FILE + ".tmp");
 
        try (BufferedWriter writer = new BufferedWriter(
                new OutputStreamWriter(new java.io.FileOutputStream(tempFile.toFile()), StandardCharsets.UTF_8))) {
            for (Promotion promotion : promotions) {
                writer.write(toCsvLine(promotion));
                writer.newLine();
            }
            writer.flush();
            Files.move(tempFile, target, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new RuntimeException("Error saving promotions", e);
        }
    }
     
     public List<Promotion> loadAll() {
        List<Promotion> promotions = new ArrayList<>();
        Path path = Paths.get(PROMOTIONS_FILE);
        if (!Files.exists(path)) {
            return promotions;
        }
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(new java.io.FileInputStream(PROMOTIONS_FILE), StandardCharsets.UTF_8))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.isBlank()) {
                    continue;
                }
                promotions.add(fromCsvLine(line));
            }
        } catch (IOException e) {
            throw new RuntimeException("Error loading promotions", e);
        }
        return promotions;
    }
}
