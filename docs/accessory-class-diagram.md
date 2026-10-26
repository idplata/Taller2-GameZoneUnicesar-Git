classDiagram
    direction TB

    %% ================= MODEL =================
    class Product {
        <<abstract>>
        -String id
        -String title
        -double price
        -int quantity
        +getDescription()* String
    }
    class Accesory {
        <<abstract>>
        -List~String~ compatibleConsoles
        +getCompatibleConsoles() List~String~
        +setCompatibleConsoles(List~String~ consoles)
        +addCompatibleConsole(String consoleId)
        +isCompatibleWith(String consoleId) boolean
        +getAccessoryType()* String
        +getDescription() String
    }
    class Cable {
        -double length
        -String connectorType
        +getAccessoryType() String
        +getDescription() String
    }
    class Controller {
        -String connectionType
        +getAccessoryType() String
        +getDescription() String
    }
    class Memory {
        -int gigabytes
        -String memoryType
        +getAccessoryType() String
        +getDescription() String
    }
    class Console

    Product <|-- Accesory
    Accesory <|-- Cable
    Accesory <|-- Controller
    Accesory <|-- Memory
    Product <|-- Console
    Accesory ..> Console : compatible with (by console id)

    %% ================= PERSISTENCE =================
    class AccesoryRepository {
        -String FILE_PATH = data/accessories.csv
        +saveAll(List~Accesory~ accesories)
        +loadAll() List~Accesory~
        -toCsvLine(Accesory a) String
        -fromCsvLine(String line) Accesory
    }

    %% ================= SERVICE =================
    class AccesoryService {
        -AccesoryRepository accesoryRepository
        -List~Accesory~ accesories
        +registerController(...) Controller
        +registerCable(...) Cable
        +registerMemory(...) Memory
        +listAllAccessories() List~Accesory~
        +listAccessoriesByType(String type) List~Accesory~
        +findAccessoriesCompatibleWith(String consoleId) List~Accesory~
        +findById(String id) Accesory
        +updateStock(String id, int delta)
    }

    %% ================= UI =================
    class ConsoleMenu {
        +showAccesoryMenu()
    }

    AccesoryService --> AccesoryRepository
    AccesoryService --> Accesory
    AccesoryRepository --> Accesory
    ConsoleMenu --> AccesoryService
