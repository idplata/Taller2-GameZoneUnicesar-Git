package com.gamezone.persistence;

import com.gamezone.model.Product;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Handles file-based persistence for products.
 */
public class ProductRepository {
    
    private static final String FILE_PATH = "data/products.dat";
    
    public void save(List<Product> products) {
        File directory = new File("data");
        if (!directory.exists()) {
            directory.mkdirs();
        }
        
        try (ObjectOutputStream oos = new ObjectOutputStream(
                new FileOutputStream(FILE_PATH))) {
            oos.writeObject(products);
        } catch (IOException e) {
            System.err.println("Error saving products: " + e.getMessage());
        }
    }
    
    @SuppressWarnings("unchecked")
    public List<Product> load() {
        File file = new File(FILE_PATH);
        if (!file.exists()) {
            return new ArrayList<>();
        }
        
        try (ObjectInputStream ois = new ObjectInputStream(
                new FileInputStream(FILE_PATH))) {
            return (List<Product>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error loading products: " + e.getMessage());
            return new ArrayList<>();
        }
    }
}