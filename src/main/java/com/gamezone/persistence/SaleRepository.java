/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.gamezone.persistence;
import com.gamezone.model.Sale;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author irind
 */

public class SaleRepository {
    private static final String FILE_PATH = "data/sales.dat";
    private List<Sale> sales;
    
    
      private void persist(){
    
        File directory = new File("data");
            if(!directory.exists()){
                directory.mkdirs();
            }
            
            try(ObjectOutputStream oos = new ObjectOutputStream( new FileOutputStream(FILE_PATH))){
            
                oos.writeObject(sales);
            
            }catch(IOException e){
            
                System.err.println("Error Saving Sales: " + e.getMessage());
            }
    }
    
    public SaleRepository() {
        this.sales = new ArrayList<>();
        load();
    }
    
    public void savesales(Sale sale){
        sales.add(sale); 
        persist();
    }
    
    public List<Sale> findAllSales(){return new ArrayList<>(sales);}
    
    public List<Sale> FindSaleByCustomer(String customerId){
        
        List<Sale> result = new ArrayList<>();
        for(Sale s: sales){
            
            if (s.getcostumer().getId().equals(customerId)){
            
                result.add(s);
                
            }
            
        }
        return result;
    }
    
  
    
    public List<Sale> FindBySeller(String SellerId){
    
        List<Sale> result = new ArrayList<>();
        
        for(Sale s: sales){
        
            if(s.getseller().getId().equals(SellerId)){
            
                result.add(s);
            }
        }
        return result;
    }
    
    @SuppressWarnings("Unchecked")
        private void load() {
            File file = new File(FILE_PATH);
            if (!file.exists()) {
                sales = new ArrayList<>();
                return;
            }
        
        try (ObjectInputStream ois = new ObjectInputStream(
                new FileInputStream(FILE_PATH))) {
                sales = (List<Sale>) ois.readObject();
            } catch (IOException | ClassNotFoundException e) {
                System.err.println("Error loading sales: " + e.getMessage());
                sales = new ArrayList<>();
        }
    }
    
}







