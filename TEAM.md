# Team Information - GameZone Unicesar

## Team Members

| Full Name | Student ID | Role | Module | GitHub Username |
|-----------|------------|------|--------|-----------------|
| Irina Danaeth Plata Suarez | [INSERT ID] | Technical Leader | Sales and Integration | idplata |
| Diego Alejandro Amaya Verdecia | [INSERT ID] | Developer 1 | Products | [username] |
| Angel Miguel Pirela Bueno | [INSERT ID] | Developer 2 | People | [username] |

## Roles and Responsibilities

### Technical Leader - Irina Danaeth Plata Suarez

**Module:** Sales and Integration

**Responsibilities:**
- Repository management and initial configuration
- Team coordination and work distribution
- Pull Request review and approval
- Integration of all modules into develop branch
- General project documentation
- Implementation of Sales module (domain, persistence, service)
- Implementation of UI console menu
- Implementation of Main application class

### Developer 1 - Diego Alejandro Amaya Verdecia

**Module:** Products

**Responsibilities:**
- Implementation of Product hierarchy (abstract base class + derived classes)
- Implementation of Product persistence (ProductRepository)
- Implementation of Product business logic (ProductService)
- Ensure correct inheritance and polymorphism
- JavaDoc documentation in English for all assigned classes

### Developer 2 - Angel Miguel Pirela Bueno

**Module:** People

**Responsibilities:**
- Implementation of Person hierarchy (abstract base class + derived classes)
- Implementation of Person persistence (PersonRepository)
- Implementation of Person business logic (PersonService)
- Ensure correct inheritance and polymorphism
- JavaDoc documentation in English for all assigned classes

## Class Distribution

### Developer 1 (Products Module) - 5 Classes
1. `com.gamezone.model.Product` - Abstract base class
2. `com.gamezone.model.VideoGame` - Derived class
3. `com.gamezone.model.Console` - Derived class
4. `com.gamezone.persistence.ProductRepository` - Persistence layer
5. `com.gamezone.service.ProductService` - Service layer

### Developer 2 (People Module) - 5 Classes
1. `com.gamezone.model.Person` - Abstract base class
2. `com.gamezone.model.Customer` - Derived class
3. `com.gamezone.model.Seller` - Derived class
4. `com.gamezone.persistence.PersonRepository` - Persistence layer
5. `com.gamezone.service.PersonService` - Service layer

### Technical Leader (Sales and Integration Module) - 5 Classes
1. `com.gamezone.model.Sale` - Domain class
2. `com.gamezone.persistence.SaleRepository` - Persistence layer
3. `com.gamezone.service.SaleService` - Service layer
4. `com.gamezone.ui.main` - User interface
5. `com.gamezone.Main` - Main application entry point

## Feature Branches

| Developer | Feature Branch | Purpose |
|-----------|---------------|---------|
| Diego Alejandro Amaya Verdecia | `feature/product-module` | Product module implementation |
| Angel Miguel Pirela Bueno | `feature/person-module` | Person module implementation |
| Irina Danaeth Plata Suarez | `feature/sale-module` | Sale and integration module implementation |

## Committed Activities

### Technical Leader - Irina Danaeth Plata Suarez

1. Create GitHub repository with initial configuration (README, .gitignore, license)
2. Configure branches (main and develop) with branch protection
3. Configure Maven project with pom.xml and package structure
4. Create TEAM.md with team information and role distribution
5. Implement Sale domain class with attributes, constructor, and basic methods
6. Implement calculateprice() method in Sale class
7. Implement SaleRepository persistence class
8. Implement SaleService with business rules (minimum one product, stock validation, inventory update)
9. Implement basic UI structure (main menu)
10. Implement UI submenus for all three modules
11. Implement Main class with dependency injection and initial data loading
12. Review and integrate Pull Requests from developers
13. Create final README.md with build and run instructions
14. Create docs/ai-usage/leader-ai-log.md documenting AI usage

### Developer 1 - Diego Alejandro Amaya Verdecia

1. Create `feature/product-module` branch from develop
2. Implement Product abstract base class with common attributes and constructor
3. Declare abstract getDescription() method for subclasses
4. Implement VideoGame derived class with specific attributes
5. Implement Console derived class with specific attributes
6. Implement ProductRepository with save() and load() methods
7. Implement ProductService with register, list, and stock update methods
8. Document all classes with JavaDoc in English
9. Create Pull Request for module integration
10. Create docs/ai-usage/developer1-ai-log.md documenting AI usage

### Developer 2 - Angel Miguel Pirela Bueno

1. Create `feature/person-module` branch from develop
2. Implement Person abstract base class with common attributes and constructor
3. Declare abstract getRoleDescription() method for subclasses
4. Implement Customer derived class with specific attributes
5. Implement Seller derived class with specific attributes
6. Implement PersonRepository with save and load methods (CSV format)
7. Implement PersonService with register and list methods
8. Document all classes with JavaDoc in English
9. Create Pull Request for module integration
10. Create docs/ai-usage/developer2-ai-log.md documenting AI usage

## Workflow Guidelines

### Git Flow

- **main**: Stable production version (protected)
- **develop**: Integration branch (protected)
- **feature/***: Feature branches from develop

### Commit Convention

Using Conventional Commits in English:

- `feat:` - New feature
- `fix:` - Bug fix
- `docs:` - Documentation changes
- `refactor:` - Code refactoring
- `chore:` - Maintenance tasks

### Pull Request Process

1. Developer creates feature branch from develop
2. Developer commits and pushes changes
3. Developer opens Pull Request to develop
4. Another team member reviews and approves
5. Technical Leader merges PR
6. Feature branch is deleted after merge

### Commit Guidelines

- Atomic commits (one logical change per commit)
- Push immediately after each commit
- Minimum 12 commits per team member
- Minimum 6 Pull Requests per team

## AI Usage Policy

Each team member maintains a personal AI usage log in `docs/ai-usage/`:

- `leader-ai-log.md` - Irina Danaeth Plata Suarez
- `developer1-ai-log.md` - Diego Alejandro Amaya Verdecia
- `developer2-ai-log.md` - Angel Miguel Pirela Bueno

**Permitted AI Uses:**
- Conceptual doubts about OOP, layered architecture, or Git
- Explanation of compiler errors or runtime exceptions
- Code review suggestions
- Java or Maven implementation questions
- Naming suggestions in English
- Git command usage

**Prohibited AI Uses:**
- Generating complete system design
- Answering analysis questions directly
- Writing complete classes for copy-paste
- Writing README.md, TEAM.md, or documentation without intervention
- Any use that replaces student analysis and understanding

## Contact Information

| Role | Name | Email |
|------|------|-------|
| Technical Leader | Irina Danaeth Plata Suarez |
| Developer 1 | Diego Alejandro Amaya Verdecia |
| Developer 2 | Angel Miguel Pirela Bueno |

## Course Information

**Course:** Programming III  
**Instructor:** Ing. Esp. Alfredo Bautista  
**Email:** adbautista@unicesar.edu.co  
**Institution:** Universidad Popular del Cesar
