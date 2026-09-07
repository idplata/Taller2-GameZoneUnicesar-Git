flowchart TB
    subgraph UI [Capa de Interfaz de Usuario]
        Main[Main]
    end

    subgraph Service [Capa de Servicios]
        PersonService[PersonService]
        ProductService[ProductService]
        SaleService[SaleService]
    end

    subgraph Persistence [Capa de Persistencia]
        PersonRepository[PersonRepository]
        ProductRepository[ProductRepository]
        SaleRepository[SaleRepository]
    end

    subgraph Model [Capa de Modelo]
        Person[Person]
        Customer[Customer]
        Seller[Seller]
        Product[Product]
        VideoGame[VideoGame]
        Console[Console]
        Sale[Sale]
    end

    UI --> Service
    Service --> Persistence
    Service --> Model
    Persistence --> Model

    linkStyle 0 stroke:#2196F3,stroke-width:2px;
    linkStyle 1 stroke:#4CAF50,stroke-width:2px;
    linkStyle 2 stroke:#FF9800,stroke-width:2px;
    linkStyle 3 stroke:#F44336,stroke-width:2px;