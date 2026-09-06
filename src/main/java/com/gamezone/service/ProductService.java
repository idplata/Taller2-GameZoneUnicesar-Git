package com.gamezone.service;

import com.gamezone.model.Product;
import com.gamezone.persistence.ProductRepository;
import java.util.List;

/**
 * Service class for product business logic.
 */
public class ProductService {
    
    private ProductRepository repository;
    private List<Product> products;
    
    public ProductService() {
        this.repository = new ProductRepository();
        this.products = repository.load();
    }
    
    public boolean registerProduct(Product product) {
        if (findById(product.getId()) != null) {
            return false;
        }
        products.add(product);
        saveData();
        return true;
    }
    
    public List<Product> getAllProducts() {
        return products;
    }
    
    public Product findById(String id) {
        for (Product p : products) {
            if (p.getId().equals(id)) {
                return p;
            }
        }
        return null;
    }
    
    public boolean updateStock(String id, int newQuantity) {
        Product product = findById(id);
        if (product != null) {
            product.setQuantity(newQuantity);
            saveData();
            return true;
        }
        return false;
    }
    
    public boolean hasEnoughStock(String id, int requestedAmount) {
        Product p = findById(id);
        return p != null && p.getQuantity() >= requestedAmount;
    }
    
    public boolean reduceStock(String id, int amount) {
        Product p = findById(id);
        if (p != null && p.getQuantity() >= amount) {
            p.setQuantity(p.getQuantity() - amount);
            saveData();
            return true;
        }
        return false;
    }
    
    private void saveData() {
        repository.save(products);
    }
}