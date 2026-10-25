/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.gamezone.ui;
import java.util.*;
import com.gamezone.model.*;
import com.gamezone.persistence.*;
import com.gamezone.service.*;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;



/**
 *
 * @author irind
 */
public class main {
    private static PersonService personService;
    private static ProductService productService;
    private static SaleService saleService;
    private static AccesoryService accesoryService;
    private static Scanner input;
    private static WarrantyService warrantyService;
    private static ReturnService returnService;
    private static PromotionService promotionService;
public static void main(String[] args) {
        ProductRepository productrepo = new ProductRepository();
        PersonRepository personrepo = new PersonRepository();
        SaleRepository salerepo = new SaleRepository();
        AccesoryRepository accesoryrepo = new AccesoryRepository();
        WarrantyRepository warrantyrepo = new WarrantyRepository();
        PromotionRepository promotionrepo = new PromotionRepository();
        ReturnRepository returnrepo = new ReturnRepository(saleService,productService);
        

        productService = new ProductService();
        personService = new PersonService(personrepo);
        accesoryService = new AccesoryService(accesoryrepo);
        promotionService = new PromotionService(promotionrepo);
        warrantyService = new WarrantyService(warrantyrepo, salerepo, productService);
        saleService = new SaleService(salerepo, productService, personService, accesoryService, promotionService, warrantyService);
        returnService = new ReturnService(returnrepo, saleService, productService);
        
        input = new Scanner(System.in);
        showMainMenu();
    }
    
    public static boolean d = true;
    public static void showMainMenu(){
            
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
                   System.out.println("--- ACCESORY MANAGEMENT ----");
                   System.out.println("  7. Accesory Menu");
                   System.out.println("--- SALE MANAGEMENT ---");
                   System.out.println("  8. Register Sale");
                   System.out.println("  9. View All Sales");
                   System.out.println(" 10. View Costumer History");
                   System.out.println(" 11. View Seller History");
                   System.out.println(" 12. View Sale Details");
                   System.out.println("--- PROMOTIONS ---");
                   System.out.println(" 13. Promotions management");
                   System.out.println("--- RETURNS ---");
                   System.out.println(" 14. Returns management");
                   System.out.println(" 15. Monthly balance");
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
                            showAccesoryMenu();
                            break;
                        case 8:
                            registersale();
                            break;
                        case 9:
                            viewallsales();
                            break;
                        case 10:
                            viewcustomerhistory();
                            break;
                        case 11:
                            viewsellerhistory();
                            break;
                        case 12:
                            viewSaleDetail();
                            break;
                        case 13:
                            showPromotionMenu();
                            break;
                        case 14:
                            showReturnMenu();
                            break;
                        case 15:
                            showMonthlyBalance();
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
    private static void showAccesoryMenu(){
    
        boolean getout = true;
        while(getout){
        System.out.println("--- ACCESORY MANAGEMENT---");
        System.out.println("  1. Register Cable");
        System.out.println("  2. Register Controller");
        System.out.println("  3. Register Memory");
        System.out.println("  4. List Accesories");
        System.out.println("  5. List Accesories By Type");
        System.out.println("  6. Find Accesories Compatible with Console");
        try {
            
            int option = input.nextInt();
            switch(option){
            
                case 1:
                    registerCable();
                    break;
                case 2:
                    registerController();
                    break;
                case 3: 
                    registerMemory();
                    break;
                case 4:
                    listAccesories();
                    break;
                case 5:
                    listAccesoriesByType();
                    break;
                case 6:
                    FindCompatibleAccesories();
                    break;
                case 0:
                    getout = false;
                    break;
                default:
                    System.out.println("Invalid Option");      }
        }catch(InputMismatchException e){
        
             System.out.println("Please enter a valid option.");
            input.nextLine();

        }
        
        }
    }
    
    private static void registerCable(){
        System.out.println("--- REGISTER CABLE ---");
        System.out.print("ID: ");
        String id = input.next();
        System.out.print("Title: ");
        String title = input.next();
        System.out.print("Price: ");
        double price = input.nextDouble();
        System.out.print("Quantity: ");
        int quantity = input.nextInt();
        System.out.println("Length:");
        double length = input.nextDouble();
        System.out.println("Connector Type (HDMI/USB/Optical: ");
        String connectorType = input.next();
        
       try{
       
       Cable cable = accesoryService.registerCable(id, title, price, quantity, length, connectorType);
           System.out.println("Cable registered: " + cable.getTitle());
       
       }catch(Exception e){
           System.out.println("Error: " + e.getMessage());
       }
      
    }

    private static void registerController(){
        System.out.println("--- REGISTER CONTROLLER ---");
        System.out.print("ID: ");
        String id = input.next();
        System.out.print("Title: ");
        String title = input.next();
        System.out.print("Price: ");
        double price = input.nextDouble();
        System.out.print("Quantity: ");
        int quantity = input.nextInt();
        System.out.println("Length:");
        double length = input.nextDouble();
        System.out.println("Connector Type (Wired/Wireless: ");
        String connectorType = input.next();
    
        List<String> compatibleConsoles = selectCompatibleConsoles();
        
        try{
            Controller controller = accesoryService.registerController(id, title, price, quantity, connectorType, compatibleConsoles);
            System.out.println("Controller registered: " + controller.getTitle());
        }catch(Exception e){
            System.out.println("Error: " + e.getMessage());
        }
    }
    
    private static void registerMemory(){
        System.out.println("--- REGISTER MEMORY ---");
        System.out.print("ID: ");
        String id = input.next();
        System.out.print("Title: ");
        String title = input.next();
        System.out.print("Price: ");
        double price = input.nextDouble();
        System.out.print("Quantity: ");
        int quantity = input.nextInt();
        System.out.println("Gigabytes: ");
        int gigabytes = input.nextInt();
        System.out.println("Memory Type:");
        String memoryType = input.next();
        List<String> compatibleConsoles = selectCompatibleConsoles();
        try{
        
            Memory memory = accesoryService.registerMemory(id, title, price, quantity, gigabytes, memoryType, compatibleConsoles);
            System.out.println("Memory registered: " + memory.getTitle());
        }catch(Exception e){
        System.out.println("Error" + e.getMessage());}
    
    }
    
    private static List<String> selectCompatibleConsoles() {
        
        List<String> compatibleConsoles = new ArrayList<>();
        System.out.println("Select compatible consoles ('done' to finish):");
    
        List<Product> products = productService.getAllProducts();
        boolean hasConsoles = false;
            for (Product p : products) {
                if (p instanceof Console) {
                    System.out.println("   Console ID: " + p.getId() + " | " + p.getTitle());
                    hasConsoles = true;
                }
            }
    
        if (!hasConsoles) {
            System.out.println("   No consoles registered yet.");
            return compatibleConsoles;
        }
    
        while (true) {
            
            System.out.print("Console ID (or 'done'): ");
            String consoleId = input.next();
            
             if (consoleId.equalsIgnoreCase("done")) {
                break;
                }
             
            Product p = productService.findById(consoleId);
            
            if (p instanceof Console) {
                
                compatibleConsoles.add(consoleId);
                System.out.println("   Added: " + p.getTitle());
            } else {
            System.out.println("   Console not found.");
            }
    }
    
            return compatibleConsoles;
}
    
    
   private static void listAccesories(){
   
       System.out.println("--- ALL ACCESORIES ---");
       List<Accesory> accesories = accesoryService.listAllAccessories();
       
       if (accesories.isEmpty()){
           
           System.out.println("No accesories registered");
           
       }else{
       
           for(Accesory a:accesories){
           
               System.out.println(" " + a.getDescription());
           }
       
       }
   
   } 
   
   private static void listAccesoriesByType(){
   
       System.out.println("Enter the type (CONTROLLER/CABLE/MEMORY): ");
       String type= input.next();
       
      List<Accesory> accesories = accesoryService.listAccessoriesByType(type);
      
      if (accesories.isEmpty()){
      
          System.out.println("No accesories found of type: " + type);
      } else {
      
          System.out.println("--- ACCESORIES OF TYPE " + type +"---");
          for(Accesory a: accesories){
          
              System.out.println(" " + a.getDescription());
          }
      }
   
   }
   
   private static void FindCompatibleAccesories(){
       System.out.println( "ENTER CONSOLE ID: " );
       String consoleId = input.next();
       
       Product console = productService.findById(consoleId);
       
       if(!(console instanceof Console)){
        System.out.println("Console not found");
        return;
        }
       
       List<Accesory> accesories = accesoryService.findAccessoriesCompatibleWith(consoleId);
       
       if(accesories.isEmpty()){
       
           System.out.println("There are no compatible accesories with: " + console.getTitle());
           
       }else{
       
           System.out.println("---COMPATIBLE WITH: " + console.getTitle()+"---");
           for(Accesory a: accesories){
           
               System.out.println(" " + a.getDescription());
           }
       }
   
   }
    private static void registerCustomer(){
    
        System.out.println("||---REGISTER CUSTOMER---||");
        System.out.println("ID: ");
        String id = input.next();
        System.out.println("NAME: ");
        String name = input.next();
        System.out.println("PHONE NUMBER: ");
        String phone = input.next();
        System.out.println("EMAIL: ");
        String email = input.next();
        
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
        String id = input.next();
        System.out.print("Title: ");
        String title = input.next();
        System.out.print("Price: ");
        double price = input.nextDouble();
        System.out.print("Quantity: ");
        int quantity = input.nextInt();
        System.out.print("Platform: ");
        String platform = input.next();
        System.out.print("Genre: ");
        String genre = input.next();
        System.out.print("Age Rating: ");
        String ageRating = input.next();
    
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
        String id = input.next();
        System.out.print("Title: ");
        String title = input.next();
        System.out.print("Price: ");
        double price = input.nextDouble();
        System.out.print("Quantity: ");
        int quantity = input.nextInt();
        System.out.print("Brand: ");
        String brand = input.next();
        System.out.print("Model: ");
        String model = input.next();
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
        String customerId = input.next();
        Customer customer = personService.findCustomerById(customerId)
                .orElse(null);
        if (customer == null) {
            System.out.println("Customer not found.");
            return;
        }
        System.out.println("  Customer: " + customer.getName());
        System.out.print("Seller ID: ");
        String sellerId = input.next();
        Seller seller = personService.findSellerById(sellerId)
                .orElse(null);
        if (seller == null) {
            System.out.println("Seller not found.");
            return;
        }
        System.out.println("  Seller: " + seller.getName());
        
        List<Product> selectedProducts = new ArrayList<>();
        while (true) {
            System.out.print("Product ID/ Accesory ID(or 'done' to finish): ");
            String productId = input.next();
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
            
            try {Accesory a = accesoryService.findById(productId);
                if (a != null) {
                    selectedProducts.add(a);
                    System.out.println("  Added accessory: " + a.getTitle());
            }
            } catch (Exception e) {
                    System.out.println("  Item not found.");
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
            System.out.println(sale.generateReceipt());
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
                System.out.println("   Sale #" + s.getId()+ 
                                 "Date: " + s.getDate() +
                                 "Customer: " + s.getCustomer().getName() +
                                 "Total: $" + s.getTotal());
                System.out.println("      Products:");
                for (Product p : s.getProducts()) {
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
        String customerId = input.next();
        
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
                    System.out.println("    " + s.getDate() + " | $" + s.getTotal());
                    totalSpent += s.getTotal();
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
        String sellerId = input.next();
        
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

                    System.out.println("    " + s.getDate()+ " | $" + s.getTotal()+ 
                                     " | Customer: " + s.getCustomer().getName());
                    totalSold += s.getTotal();
                }
                System.out.println("  Total value sold: $" + totalSold);
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
   
    private static void viewSaleDetail(){
        System.out.println("--- SALE DETAIL ---");
        System.out.print("Sale ID: ");
        String saleId = input.next();
        input.nextLine();

        Sale sale = saleService.findSaleById(saleId.trim()).orElse(null);
        if (sale == null) {
            System.out.println("No sale was found with the id " + saleId + ".");
            return;
        }
        System.out.println("Date     : " + sale.getDate());
        System.out.println(sale.generateReceipt());
    }

    private static void showPromotionMenu(){
        boolean back = false;
        while (!back) {
            System.out.println("\n--- PROMOTIONS MANAGEMENT ---");
            System.out.println("  1. Register percentage promotion");
            System.out.println("  2. Register category promotion");
            System.out.println("  3. Register bulk purchase promotion");
            System.out.println("  4. List all promotions");
            System.out.println("  5. List active promotions");
            System.out.println("  0. Back to main menu");
            String text = readLine("Select an option: ");
            int option;
            try {
                option = Integer.parseInt(text);
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
                continue;
            }
            switch (option) {
                case 1:
                    registerPercentagePromotion();
                    break;
                case 2:
                    registerCategoryPromotion();
                    break;
                case 3:
                    registerBulkPromotion();
                    break;
                case 4:
                    listPromotions(promotionService.listAllPromotions(),
                        "ALL PROMOTIONS", "There are no registered promotions.");
                    break;
                case 5:
                    listPromotions(promotionService.listActivePromotions(),
                        "ACTIVE PROMOTIONS", "There are no active promotions today.");
                    break;
                case 0:
                    back = true;
                    break;
                default:
                    System.out.println("Invalid option.");
            }
        }
    }

    private static void registerPercentagePromotion(){
        System.out.println("--- REGISTER PERCENTAGE PROMOTION ---");
        try {
            String id = readLine("ID: ");
            String name = readLine("Name: ");
            LocalDate start = readDate("Start date (YYYY-MM-DD): ");
            LocalDate end = readDate("End date (YYYY-MM-DD): ");
            double percentage = readPercentage();
            promotionService.registerPercentageDiscount(id, name, start, end, percentage);
            System.out.println("Promotion registered successfully.");
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void registerCategoryPromotion(){
        System.out.println("--- REGISTER CATEGORY PROMOTION ---");
        try {
            String id = readLine("ID: ");
            String name = readLine("Name: ");
            LocalDate start = readDate("Start date (YYYY-MM-DD): ");
            LocalDate end = readDate("End date (YYYY-MM-DD): ");
            double percentage = readPercentage();
            String category = readLine("Category (VIDEOGAME or CONSOLE): ").toUpperCase();
            if (!category.equals("VIDEOGAME") && !category.equals("CONSOLE")) {
                throw new IllegalArgumentException("The category must be VIDEOGAME or CONSOLE.");
            }
        promotionService.registerCategoryDiscount(id, name, start, end, percentage, category);
        System.out.println("Promotion registered successfully.");
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }  

    private static void registerBulkPromotion(){
        System.out.println("--- REGISTER BULK PURCHASE PROMOTION ---");
        try {
            String id = readLine("ID: ");
            String name = readLine("Name: ");
            LocalDate start = readDate("Start date (YYYY-MM-DD): ");
            LocalDate end = readDate("End date (YYYY-MM-DD): ");
            int minimum;
            try {
                minimum = Integer.parseInt(readLine("Minimum number of products: "));
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("The minimum quantity must be a whole number.");
            }
            if (minimum < 1) {
                throw new IllegalArgumentException("The minimum quantity must be at least 1.");
            }
            double percentage = readPercentage();
            promotionService.registerBulkPurchaseDiscount(id, name, start, end, minimum, percentage);
            System.out.println("Promotion registered successfully.");
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void listPromotions(List<Promotion> promotions, String title, String emptyMessage){
        System.out.println("--- " + title + " ---");
        if (promotions.isEmpty()) {
            System.out.println("  " + emptyMessage);
            return;
        }
        LocalDate today = LocalDate.now();
        for (Promotion p : promotions) {
            System.out.println("  [" + p.getId() + "] " + p.getName()
                    + " | " + describePromotion(p)
                    + " | " + p.getStartDate() + " to " + p.getEndDate()
                    + " | " + (p.isActive(today) ? "ACTIVE" : "NOT ACTIVE"));
        }
    }
        
        private static String describePromotion(Promotion p){
            if (p instanceof PercentageDiscount) {
                return "Percentage " + ((PercentageDiscount) p).getPercentage() + "% off the total";
            } else if (p instanceof CategoryDiscount) {
                CategoryDiscount c = (CategoryDiscount) p;
                return "Category " + c.getTargetCategory() + " " + c.getPercentage() + "%";
            } else if (p instanceof BulkPurchaseDiscount) {
                BulkPurchaseDiscount b = (BulkPurchaseDiscount) p;
                return "Wholesale: from " + b.getMinimumQuantity() + " products, " + b.getPercentage() + "%";
            }
            return p.getPromotionType();
        }
        
        private static String readLine(String prompt){
            System.out.print(prompt);
            return input.nextLine().trim();
        }

        private static LocalDate readDate(String prompt){
            try {
                return LocalDate.parse(readLine(prompt));
            } catch (DateTimeParseException e) {
                throw new IllegalArgumentException("Invalid date. Use the format YYYY-MM-DD (for example 2026-10-23).");
            }
        }
        
        private static double readPercentage(){
            double percentage;
            try {
                percentage = Double.parseDouble(readLine("Discount percentage (1-100): ").replace(',', '.'));
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("The percentage must be a number.");
            }
            if (percentage <= 0 || percentage > 100) {
                throw new IllegalArgumentException("The percentage must be between 0 and 100.");
            }
            return percentage;
        }      
        
        private static void showMonthlyBalance(){
            System.out.println("--- MONTHLY BALANCE ---");
            try {
                int month = Integer.parseInt(readLine("Month (1-12): "));
                int year = Integer.parseInt(readLine("Year (for example 2026): "));
                if (month < 1 || month > 12) {
                    System.out.println("The month must be between 1 and 12.");
                    return;
                }
                double totalSales = saleService.getMonthlySalesTotal(month, year);
                double netBalance = returnService.generateMonthlyBalance(month, year);
                double totalReturns = totalSales - netBalance;
                System.out.println("Total sales            : $" + String.format("%.2f", totalSales));
                System.out.println("Total returns          : $" + String.format("%.2f", totalReturns));
                System.out.println("NET BALANCE            : $" + String.format("%.2f", netBalance));
            } catch (NumberFormatException e) {
            System.out.println("Please enter valid numbers for the month and year.");
            }
        }
        private static void showReturnMenu(){
            boolean back = false;
            while (!back) {
                System.out.println("\n--- RETURNS MANAGEMENT ---");
                System.out.println("  1. Register a return");
                System.out.println("  2. View all returns");
                System.out.println("  3. View returns by customer");
                System.out.println("  4. View returns by sale");
                System.out.println("  5. View monthly balance");
                System.out.println("  0. Back to main menu");
                String text = readLine("Select an option: ");
                int option;
                try {
                    option = Integer.parseInt(text);
                } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
                continue;
            }
            switch (option) {
                case 1:
                    registerReturn();
                    break;
                case 2:
                    printReturns(returnService.viewAllReturns(),
                            "ALL RETURNS", "There are no registered returns.");
                    break;
                case 3:
                    String customerId = readLine("Customer ID: ");
                    printReturns(returnService.viewReturnsByCustomer(customerId),
                        "RETURNS FOR CUSTOMER " + customerId,
                        "This customer has no returns.");
                    break;
                case 4:
                    String saleId = readLine("Sale ID: ");
                    printReturns(returnService.viewReturnsBySale(saleId),
                        "RETURNS FOR SALE " + saleId,
                        "This sale has no returns.");
                    break;
                case 5:
                    showMonthlyBalance();
                    break;
                case 0:
                    back = true;
                    break;
                default:
                    System.out.println("Invalid option.");
            }
        }
    }

    private static void registerReturn(){
        System.out.println("--- REGISTER RETURN ---");
        String saleId = readLine("Sale ID: ");
        Sale sale = saleService.findSaleById(saleId).orElse(null);
        if (sale == null) {
            System.out.println("No sale exists with the id " + saleId + ".");
            return;
        }
        if (!sale.canBeReturned()) {
            System.out.println("Sale " + saleId + " has already exceeded the 30-day return period.");
            return;
        }
        System.out.println("Products in the sale:");
        for (Product p : sale.getProducts()) {
            System.out.println("  - [" + p.getId() + "] " + p.getTitle() + " ($" + p.getPrice() + ")");
        }
        String line = readLine("IDs of the products to return (separated by commas): ");
        List<String> productIds = new ArrayList<>();
        for (String part : line.split(",")) {
            String productId = part.trim();
            if (!productId.isEmpty()) {
                productIds.add(productId);
            }
        }
        if (productIds.isEmpty()) {
            System.out.println("You must indicate at least one product.");
            return;
        }
        String reason = readLine("Reason for the return: ");
        try {
            Return returnItem = returnService.registerReturn(saleId, productIds, reason);
            System.out.println("Return registered successfully.");
            System.out.println(returnItem.generateReturnReceipt());
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void printReturns(List<Return> returns, String title, String emptyMessage){
        System.out.println("--- " + title + " ---");
        if (returns.isEmpty()) {
            System.out.println("  " + emptyMessage);
            return;
        }
        for (Return r : returns) {
            System.out.println("  [" + r.getId() + "] " + r.getDate()
                + " | Sale " + r.getSale().getId()
                + " | " + r.getReturnedProducts().size() + " product(s)"
                + " | Refund $" + String.format("%.2f", r.getRefundAmount())
                + " | Reason: " + r.getReason());
        }
    }
        
}
    


