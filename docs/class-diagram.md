classDiagram
    direction TB

    %% ========================
    %% MODEL 
    %% ========================
    class Product {
        <<abstract>>
        - String id
        - String title
        - double price
        - int quantity
        + Product(String id, String title, double price, int quantity)
        + String getId()
        + String getTitle()
        + double getPrice()
        + int getQuantity()
        + void setTitle(String title)
        + void setPrice(double price)
        + void setQuantity(int quantity)
        + abstract String getDescription()
    }

    class VideoGame {
        - String platform
        - String genre
        - String ageRating
        + VideoGame(String id, String title, double price, int quantity, String platform, String genre, String ageRating)
        + String getPlatform()
        + String getGenre()
        + String getAgeRating()
        + String getDescription()
    }

    class Console {
        - String brand
        - String model
        - int generation
        + Console(String id, String title, double price, int quantity, String brand, String model, int generation)
        + String getBrand()
        + String getModel()
        + int getGeneration()
        + String getDescription()
    }

    class Person {
        <<abstract>>
        # String id
        # String name
        # String phone
        + Person(String id, String name, String phone)
        + String getId()
        + void setId(String id)
        + String getName()
        + void setName(String name)
        + String getPhone()
        + void setPhone(String phone)
        + abstract String getRoleDescription()
    }

    class Customer {
        - String email
        + Customer(String id, String name, String phone, String email)
        + String getEmail()
        + void setEmail(String email)
        + String getRoleDescription()
    }

    class Seller {
        - String employeeCode
        - String shift
        + Seller(String id, String name, String phone, String employeeCode, String shift)
        + String getEmployeeCode()
        + void setEmployeeCode(String employeeCode)
        + String getShift()
        + void setShift(String shift)
        + String getRoleDescription()
    }

    class Sale {
        - LocalDateTime date
        - Customer customer
        - Seller seller
        - String saleid
        - List products
        + Sale(String saleid, Customer customer, Seller seller, List products)
        + String getid()
        + Customer getcostumer()
        + Seller getseller()
        + List getproducts()
        + LocalDateTime getdate()
        + void setid(String saleid)
        + void setcustomer(Customer customer)
        + void setseller(Seller seller)
        + void setproducts(List products)
        + void setdate(LocalDateTime date)
        + double calculateprice()
        + String toString()
    }

    %% ==========================
    %% PERSISTENCE
    %% ==========================
    class PersonRepository {
        - static final String DATA_DIRECTORY
        - static final String CUSTOMERS_FILE
        - static final String SELLERS_FILE
        - static final String SEPARATOR
        + PersonRepository()
        + void saveCustomers(List customers)
        + List loadCustomers()
        + void saveSellers(List sellers)
        + List loadSellers()
    }

    class ProductRepository {
        - static final String FILE_PATH
        + void save(List products)
        + List load()
    }

    class SaleRepository {
        - static final String FILE_PATH
        - List sales
        + SaleRepository()
        + void savesales(Sale sale)
        + List findAllSales()
        + List FindSaleByCustomer(String customerId)
        + List FindBySeller(String SellerId)
        - void persist()
        - void load()
    }

    %% ======================
    %% SERVICE
    %% ======================
    class PersonService {
        - final PersonRepository repository
        - final List customers
        - final List sellers
        + PersonService(PersonRepository repository)
        + Customer registerCustomer(String id, String name, String phone, String email)
        + List listCustomers()
        + List listSellers()
        + Optional findCustomerById(String id)
        + Optional findSellerById(String id)
        - void preloadDefaultSellers()
    }

    class ProductService {
        - ProductRepository repository
        - List products
        + ProductService()
        + boolean registerProduct(Product product)
        + List getAllProducts()
        + Product findById(String id)
        + boolean updateStock(String id, int newQuantity)
        + boolean hasEnoughStock(String id, int requestedAmount)
        + boolean reduceStock(String id, int amount)
        - void saveData()
    }

    class SaleService {
        - SaleRepository saleRepository
        - ProductService productService
        - PersonService personService
        + SaleService(SaleRepository saleRepository, ProductService productService, PersonService personService)
        + void registerSale(Sale sale)
        + List getAllSales()
        + List getCustomerHistory(String customerId)
        + List getSellerHistory(String SellerId)
        + double getTotalRevenue()
    }

    %% =========================
    %% UI
    %% =========================
    class Main {
        - static PersonService personService
        - static ProductService productService
        - static SaleService saleService
        - static Scanner input
        - static boolean d
        + static void main(String[] args)
        + static void ShowMainMenu()
        - static void registerCustomer()
        - static void listcustomers()
        - static void listsellers()
        - static void registervideogame()
        - static void registerconsole()
        - static void listproducts()
        - static void registersale()
        - static void viewallsales()
        - static void viewcustomerhistory()
        - static void viewsellerhistory()
    }

    %% =====================================
    %% CLASS RELATIONSHIP
    %% =====================================
    Sale *-- Customer : contains
    Sale *-- Seller : contains
    Sale *-- Product : contains

    PersonRepository ..> Customer : depends on
    PersonRepository ..> Seller : depends on
    ProductRepository ..> Product : depends on

    PersonService ..> PersonRepository : depends on
    PersonService ..> Customer : depends on
    PersonService ..> Seller : depends on
    ProductService ..> ProductRepository : depends on
    ProductService ..> Product : depends on
    SaleService ..> SaleRepository : depends on
    SaleService ..> ProductService : depends on
    SaleService ..> PersonService : depends on
    SaleService ..> Sale : depends on

    Main ..> PersonService : depends on
    Main ..> ProductService : depends on
    Main ..> SaleService : depends on
    Main ..> Sale : depends on
    Main ..> Customer : depends on
    Main ..> Seller : depends on
    Main ..> Product : depends on

    %% =========================================
    %% HIERARCHY
    %% =========================================
    Product <|-- Console : extends s42
    Person <|-- Seller : extends s43