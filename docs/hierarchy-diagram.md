classDiagram
    direction TB
    
    class Person {
        <<abstract>>
    }
    class Customer
    class Seller
    
    Person <|-- Customer : extends
    Person <|-- Seller : extends
    
    class Product {
        <<abstract>>
    }
    class VideoGame
    class Console
    
    Product <|-- VideoGame : extends
    Product <|-- Console : extends
    
    class Sale
    class PersonRepository
    class ProductRepository
    class SaleRepository
    class PersonService
    class ProductService
    class SaleService
    class Main
    
    note for Person "Abstract Class"
    note for Product "Abstract Class"
    note for Customer "Concrete Class"
    note for Seller "Concrete Class"
    note for VideoGame "Concrete Class"
    note for Console "Concrete Class"