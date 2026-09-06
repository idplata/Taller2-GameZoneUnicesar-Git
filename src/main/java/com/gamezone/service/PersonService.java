package com.gamezone.service;

import com.gamezone.model.Customer;
import com.gamezone.model.Seller;
import com.gamezone.persistence.PersonRepository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
/**
 * Contiene las reglas de negocio del módulo de personas: registro de
 * clientes, precarga de vendedores, y acceso de lectura a ambas listas.
 * Esta es la única clase del módulo autorizada para invocar al
 * {@link PersonRepository}; la capa de interfaz de usuario siempre debe
 * pasar por este servicio en lugar de acceder directamente a la persistencia.
 */
public class PersonService {

    private final PersonRepository repository;
    private final List<Customer> customers;
    private final List<Seller> sellers;
   /**
     * Crea el servicio, cargando los datos previamente persistidos y
     * precargando tres vendedores por defecto la primera vez que se
     * ejecuta la aplicación (cuando aún no existe el archivo de vendedores).
     *
     * @param repository la clase de persistencia usada para leer/escribir datos de personas
     */
    public PersonService(PersonRepository repository) {
        this.repository = repository;
        this.customers = repository.loadCustomers();
        this.sellers = repository.loadSellers();

        if (this.sellers.isEmpty()) {
            System.out.println("This repository is empty");
        }
    }
     /**
     * Registra un nuevo cliente, validando primero que el id no esté ya
     * en uso, y luego persiste la lista actualizada.
     * 
     * @param id    identificación única del cliente
     * @param name  nombre completo del cliente
     * @param phone número de teléfono de contacto
     * @param email correo electrónico de contacto
     * @return el cliente recién registrado
     * @throws IllegalArgumentException si ya existe un cliente con el mismo id
     */
    public Customer registerCustomer(String id, String name, String phone, String email) {
        if (findCustomerById(id).isPresent()) {
            throw new IllegalArgumentException("A customer with id " + id + " already exists");
        }
        Customer customer = new Customer(id, name, phone, email);
        customers.add(customer);
        repository.saveCustomers(customers);
        return customer;
    }

    public List<Customer> listCustomers() {
        return Collections.unmodifiableList(customers);
    }
 

    public List<Seller> listSellers() {
        return Collections.unmodifiableList(sellers);
    }
   /**
     * Busca un cliente por su identificación única.
     *
     * @param id la identificación del cliente a buscar
     * @return un {@link Optional} con el cliente si se encontró
     */
    public Optional<Customer> findCustomerById(String id) {
        return customers.stream()
                .filter(customer -> customer.getId().equals(id))
                .findFirst();
    }
       /**
     * Busca un vendedor por su identificación única.
     *
     * @param id la identificación del vendedor a buscar
     * @return un {@link Optional} con el vendedor si se encontró
     */

        public Optional<Seller> findSellerById(String id) {
        return sellers.stream()
                .filter(seller -> seller.getId().equals(id))
                .findFirst();
    }
}