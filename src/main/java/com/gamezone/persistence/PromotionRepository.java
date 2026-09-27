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
     
        private String toCsvLine(Promotion promotion) {
        if (promotion instanceof PercentageDiscount) {
            PercentageDiscount p = (PercentageDiscount) promotion;
            return String.join(SEPARATOR,
                    TYPE_PERCENTAGE,
                    p.getId(),
                    p.getName(),
                    p.getStartDate().toString(),
                    p.getEndDate().toString(),
                    String.valueOf(p.getPercentage()));
        } else if (promotion instanceof CategoryDiscount) {
            CategoryDiscount c = (CategoryDiscount) promotion;
            return String.join(SEPARATOR,
                    TYPE_CATEGORY,
                    c.getId(),
                    c.getName(),
                    c.getStartDate().toString(),
                    c.getEndDate().toString(),
                    String.valueOf(c.getPercentage()),
                    c.getTargetCategory());
        } else if (promotion instanceof BulkPurchaseDiscount) {
            BulkPurchaseDiscount b = (BulkPurchaseDiscount) promotion;
            return String.join(SEPARATOR,
                    TYPE_BULK,
                    b.getId(),
                    b.getName(),
                    b.getStartDate().toString(),
                    b.getEndDate().toString(),
                    String.valueOf(b.getMinimumQuantity()),
                    String.valueOf(b.getPercentage()));
        }
        throw new IllegalArgumentException(
                "Unknown promotion type: " + promotion.getClass().getSimpleName());
    }
            private Promotion fromCsvLine(String line) {
        String[] fields = line.split(SEPARATOR, -1);
        String type = fields[0];
        String id = fields[1];
        String name = fields[2];
        LocalDate startDate = LocalDate.parse(fields[3]);
        LocalDate endDate = LocalDate.parse(fields[4]);
 
        switch (type) {
            case TYPE_PERCENTAGE:
                double percentage = Double.parseDouble(fields[5]);
                return new PercentageDiscount(id, name, startDate, endDate, percentage);
            case TYPE_CATEGORY:
                double categoryPercentage = Double.parseDouble(fields[5]);
                String targetCategory = fields[6];
                return new CategoryDiscount(id, name, startDate, endDate, categoryPercentage, targetCategory);
            case TYPE_BULK:
                int minimumQuantity = Integer.parseInt(fields[5]);
                double bulkPercentage = Double.parseDouble(fields[6]);
                return new BulkPurchaseDiscount(id, name, startDate, endDate, minimumQuantity, bulkPercentage);
            default:
                throw new IllegalArgumentException("Unknown promotion type in file: " + type);
        }
    }
}
