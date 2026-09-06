package com.gamezone.persistence;

import com.gamezone.model.Customer;
import com.gamezone.model.Seller;

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

/**
 * Se encarga de guardar y cargar los datos de {@link Customer} y
 * {@link Seller} desde y hacia archivos CSV planos. Esta es la única
 * clase del módulo de personas autorizada para acceder al sistema de
 * archivos; nunca debe ser invocada directamente desde la capa de
 * interfaz de usuario (según las restricciones de la arquitectura en capas).
 */

public class PersonRepository {

    private static final String DATA_DIRECTORY = "data";
    private static final String CUSTOMERS_FILE = DATA_DIRECTORY + "/customers.csv";
    private static final String SELLERS_FILE = DATA_DIRECTORY + "/sellers.csv";
    private static final String SEPARATOR = ";";

    
     /**
     * Crea el repositorio y se asegura de que exista el directorio de datos.
     */
    public PersonRepository() {
        try {
            Files.createDirectories(Paths.get(DATA_DIRECTORY));
        } catch (IOException e) {
            throw new RuntimeException("Could not create data directory", e);
        }
    }
    
    /**
     * Guarda la lista completa de clientes, sobrescribiendo el archivo anterior.
     *
     * @param customers lista de clientes a guardar
     */
    public void saveCustomers(List<Customer> customers) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(CUSTOMERS_FILE))) {
            for (Customer customer : customers) {
                writer.write(String.join(SEPARATOR,
                        customer.getId(),
                        customer.getName(),
                        customer.getPhone(),
                        customer.getEmail()));
                writer.newLine();
            }
        } catch (IOException e) {
            throw new RuntimeException("Error saving customers", e);
        }
    }

    public List<Customer> loadCustomers() {
        List<Customer> customers = new ArrayList<>();
        Path path = Paths.get(CUSTOMERS_FILE);
        if (!Files.exists(path)) {
            return customers;
        }
        try (BufferedReader reader = new BufferedReader(new FileReader(CUSTOMERS_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.isBlank()) {
                    continue;
                }
                String[] fields = line.split(SEPARATOR, -1);
                customers.add(new Customer(fields[0], fields[1], fields[2], fields[3]));
            }
        } catch (IOException e) {
            throw new RuntimeException("Error loading customers", e);
        }
        return customers;
    }
      /**
     * Guarda la lista completa de vendedores, sobrescribiendo el archivo anterior.
     *
     * @param sellers lista de vendedores a guardar
     */
    public void saveSellers(List<Seller> sellers) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(SELLERS_FILE))) {
            for (Seller seller : sellers) {
                writer.write(String.join(SEPARATOR,
                        seller.getId(),
                        seller.getName(),
                        seller.getPhone(),
                        seller.getEmployeeCode(),
                        seller.getShift()));
                writer.newLine();
            }
        } catch (IOException e) {
            throw new RuntimeException("Error saving sellers", e);
        }
    }
    /**
     * Carga la lista de vendedores desde el archivo de datos. Si el archivo
     * no existe todavía, se devuelve una lista vacía.
     *
     * @return la lista de vendedores persistidos
     */
    public List<Seller> loadSellers() {
        List<Seller> sellers = new ArrayList<>();
        Path path = Paths.get(SELLERS_FILE);
        if (!Files.exists(path)) {
            return sellers;
        }
        try (BufferedReader reader = new BufferedReader(new FileReader(SELLERS_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.isBlank()) {
                    continue;
                }
                String[] fields = line.split(SEPARATOR, -1);
                sellers.add(new Seller(fields[0], fields[1], fields[2], fields[3], fields[4]));
            }
        } catch (IOException e) {
            throw new RuntimeException("Error loading sellers", e);
        }
        return sellers;
    }
}
