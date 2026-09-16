/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.gamezone.ui;
import java.util.*;
import com.gamezone.model.*;
import com.gamezone.persistence.*;
import com.gamezone.service.*;

/**
 *
 * @author irind
 */
public class main {
    private static PersonService personService;
    private static ProductService productService;
    private static SaleService saleService;
    private static Scanner input;

    public static void main(String[] args) {
            ProductRepository productrepo = new ProductRepository();
            PersonRepository personrepo = new PersonRepository();
            SaleRepository salerepo = new SaleRepository();
            personService = new PersonService(personrepo);
            productService = new ProductService();
            saleService = new SaleService(salerepo, productService, personService);
            input = new Scanner(System.in);
            
            ShowMainMenu();
            
            
    }
    
    public static boolean d = true;
    public static void ShowMainMenu(){
            
                while (d == true){
                   System.out.println("║        GAMEZONE UNICESAR             ║");
                   System.out.println("--- PERSON MANAGEMENT ---");
                   System.out.println("  1. Register Customer");
                   System.out.println("  2. List Customers");
                   System.out.println("  3. List Sellers\n");
                   System.out.println("--- PRODUCT MANAGEMENT ---");
                   System.out.println("  4. Register Video Game");
                   System.out.println("  5. Register Console");
                   System.out.println("  6. List Products\n");
                   System.out.println("--- SALE MANAGEMENT ---");
                   System.out.println("  7. Register Sale");
                   System.out.println("  8. View All Sales");
                   System.out.println("  9. View Costumer History");
                   System.out.println(" 10. View Seller History\n");
                   System.out.println(" 0. EXIT ");
                   System.out.println("\n SELECT AN OPTION PLEASE");
                   
                    try {
                        int option = input.nextInt();
                        input.nextLine();

                        switch (option) {
                            case 1:
                            registerCustomer();
                            break;
                        case 2:
                            listcustomers();
                            break;
                        case 3:
                            listsellers();
                            break;
                        case 4:
                            registervideogame();
                            break;
                        case 5:
                            registerconsole();
                            break;
                        case 6:
                            listproducts();
                            break;
                        case 7:
                            registersale();
                            break;
                        case 8:
                            viewallsales();
                            break;
                        case 9:
                            viewcustomerhistory();
                            break;
                        case 10:
                            viewsellerhistory();
                            break;
                        case 0:
                            System.out.println("¡GRACIAS POR USAR GAMEZONE UNICESAR!");
                            System.out.println("ADIOS!!");
                            d = false;
                            input.close();
                            return;
                        default:
                            System.out.println("Invalid option. Try again.");
                        }} catch(InputMismatchException e) {
                        System.out.println("Please enter a valid number.");
                       input.nextLine();
            
                    }
       }}
    
    private static void registerCustomer(){
    
        System.out.println("||---REGISTER CUSTOMER---||");
        System.out.println("ID: ");
        String id = input.nextLine();
        System.out.println("NAME: ");
        String name = input.nextLine();
        System.out.println("PHONE NUMBER: ");
        String phone = input.nextLine();
        System.out.println("EMAIL: ");
        String email = input.nextLine();
        
        try{
        
            Customer c = personService.registerCustomer(id, name, phone, email);
            System.out.println("CUSTOMER REGISTERED :" + c.getName());
        }catch(IllegalArgumentException e){
            System.out.println("ERROR: " + e.getMessage());
            
        }
        
    
    }
    
    private static void listcustomers(){
    
       System.out.println("--- CUSTOMERS ---");
       System.out.println("Searching on personService.listCustomers()...");
    
        List<Customer> customers = personService.listCustomers();
    
        System.out.println("There are " + customers.size() + " customers");
    
        if (customers.isEmpty()) {
            System.out.println("No customers registered.");
            System.out.println("Try registering a customer first (option 1).");
        } else {
            for (Customer c : customers) {
            System.out.println(c.getRoleDescription());
            }
        }
    
    }
    
    
    private static void listsellers(){ 
       System.out.println("--- SELLERS ---");
       System.out.println("Searching sellers on personService.listSellers()...");
    
       List<Seller> sellers = personService.listSellers();
    
       System.out.println("There are" + sellers.size() + " sellers");
    
        if (sellers.isEmpty()) {
            System.out.println("No sellers registered.");
        } else {
            for (Seller s : sellers) {
                System.out.println("Sellers:  "+s.getRoleDescription());
            }   
        }
    }
    
    
    private static void registervideogame(){
        System.out.println("--- REGISTER VIDEO GAME ---");
        System.out.print("ID: ");
        String id = input.nextLine();
        System.out.print("Title: ");
        String title = input.nextLine();
        System.out.print("Price: ");
        double price = input.nextDouble();
        System.out.print("Quantity: ");
        int quantity = input.nextInt();
        System.out.print("Platform: ");
        String platform = input.nextLine();
        System.out.print("Genre: ");
        String genre = input.nextLine();
        System.out.print("Age Rating: ");
        String ageRating = input.nextLine();
    
        try{
          VideoGame game = new VideoGame(id, title, price, quantity, platform, genre, ageRating);
          if (productService.registerProduct(game)) {
              System.out.println("Video game registered successfully!");
          } else {
              System.out.println("Product with ID " + id + " already exists.");
           }
        }catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    
    }
    
    private static void registerconsole(){
        System.out.println("--- REGISTER CONSOLE ---");
        System.out.print("ID: ");
        String id = input.nextLine();
        System.out.print("Title: ");
        String title = input.nextLine();
        System.out.print("Price: ");
        double price = input.nextDouble();
        System.out.print("Quantity: ");
        int quantity = input.nextInt();
        System.out.print("Brand: ");
        String brand = input.nextLine();
        System.out.print("Model: ");
        String model = input.nextLine();
        System.out.print("Generation: ");
        int generation = input.nextInt();
        
         try {
            Console console = new Console(id, title,price, quantity, brand, model, generation);
            if (productService.registerProduct(console)) {
                System.out.println("Console registered successfully!");
            } else {
                System.out.println("Product with ID " + id + " already exists.");
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
    private static void listproducts(){
        System.out.println("--- PRODUCTS ---");
        System.out.println("Searching in productService.getAllProducts()...");
    
        List<Product> products = productService.getAllProducts();
    
        System.out.println("There are " + products.size() + " products");
    
        if (products.isEmpty()) {
            System.out.println("No products in inventory.");
            System.out.println("Try registering a product first (options 4 or 5).");
        } else {
            for (Product p : products) {
                System.out.println(p.getDescription());
            }
        }
    }
    private static void registersale(){ 
        System.out.println("--- REGISTER SALE ---");
        System.out.print("Customer ID: ");
        String customerId = input.nextLine();
        Customer customer = personService.findCustomerById(customerId)
                .orElse(null);
        if (customer == null) {
            System.out.println("Customer not found.");
            return;
        }
        System.out.println("  Customer: " + customer.getName());
        System.out.print("Seller ID: ");
        String sellerId = input.nextLine();
        Seller seller = personService.findSellerById(sellerId)
                .orElse(null);
        if (seller == null) {
            System.out.println("Seller not found.");
            return;
        }
        System.out.println("  Seller: " + seller.getName());
        
        List<Product> selectedProducts = new ArrayList<>();
        while (true) {
            System.out.print("Product ID (or 'done' to finish): ");
            String productId = input.nextLine();
            if (productId.equalsIgnoreCase("done")) {
                break;
            }
            Product p = productService.findById(productId);
            if (p == null) {
                System.out.println("Product not found.");
            } else {
                selectedProducts.add(p);
                System.out.println("  Added: " + p.getTitle());
            }
        }
        
        if (selectedProducts.isEmpty()) {
            System.out.println("A sale must have at least one product.");
            return;
        }
        
        String saleId = "S-" + System.currentTimeMillis();
        Sale sale = new Sale(saleId, customer, seller, selectedProducts);
        
        try {
            saleService.registerSale(sale);
            System.out.println("Sale registered successfully!");
            System.out.println("  Total: $" + sale.calculateprice());
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }}
    private static void viewallsales(){
    
        System.out.println("--- ALL SALES ---");
        List<Sale> sales = saleService.getAllSales();
        if (sales.isEmpty()) {
            System.out.println("   No sales registered.");
        } else {
            for (Sale s : sales) {
                System.out.println("   Sale #" + s.getid()+ 
                                 "Date: " + s.getdate() +
                                 "Customer: " + s.getcostumer().getName() +
                                 "Total: $" + s.calculateprice());
                System.out.println("      Products:");
                for (Product p : s.getproducts()) {
                    System.out.println("         - " + p.getTitle() + " ($" + p.getPrice() + ")");
                }
            }
            System.out.println("   Total sales: " + sales.size());
            System.out.println("   Total revenue: $" + saleService.getTotalRevenue());
        }
    
    }
    private static void viewcustomerhistory(){
    
    System.out.println("--- CUSTOMER PURCHASE HISTORY ---");
        System.out.print("Customer ID: ");
        String customerId = input.nextLine();
        
        try {
            List<Sale> sales = saleService.getCustomerHistory(customerId);
            if (sales.isEmpty()) {
                System.out.println("No purchases found for this customer.");
            } else {
                Customer customer = personService.findCustomerById(customerId).orElse(null);
                System.out.println("  Customer: " + (customer != null ? customer.getName() : customerId));
                System.out.println("  Total purchases: " + sales.size());
                double totalSpent = 0;
                for (Sale s : sales) {
                    System.out.println("    " + s.getdate() + " | $" + s.calculateprice());
                    totalSpent += s.calculateprice();
                }
                System.out.println("  Total spent: $" + totalSpent);
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    
    
    }
    private static void viewsellerhistory(){ 
        System.out.println("--- SELLER SALES HISTORY ---");
        System.out.print("Seller ID: ");
        String sellerId = input.nextLine();
        
        try {
            List<Sale> sales = saleService.getSellerHistory(sellerId);
            if (sales.isEmpty()) {
                System.out.println("No sales found for this seller.");
            } else {
                Seller seller = personService.findSellerById(sellerId).orElse(null);
                System.out.println("  Seller: " + (seller != null ? seller.getName() : sellerId));
                System.out.println("  Total sales attended: " + sales.size());
                double totalSold = 0;
                for (Sale s : sales) {
                    System.out.println("    " + s.getdate()+ " | $" + s.calculateprice()+ 
                                     " | Customer: " + s.getcostumer().getName());
                    totalSold += s.calculateprice();
                }
                System.out.println("  Total value sold: $" + totalSold);
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
