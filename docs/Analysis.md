# Analysis - GameZone Unicesar

## Team: GameZone Unicesar
## Course: Programming III
## Instructor: Ing. Esp. Alfredo Bautista

---

## Analysis Questions and Answers

### 1. About People in the System

**Question:** What attributes are common to all people who interact with the store, and which are specific to each type of person? How is this distinction reflected in a class hierarchy?

**Answer:**

All people who interact with the store share basic personal information:
- **Common attributes (Person base class):**
  - `id`: Unique identifier for the person
  - `name`: Full name of the person
  - `phone`: Contact phone number

- **Specific attributes:**
  - **Customer:** `email` - Electronic mail for communication and marketing
  - **Seller:** `employeeCode` - Internal employee identification; `shift` - Work shift assigned (Morning, Afternoon, Evening)

This distinction is reflected through an inheritance hierarchy where `Person` is an abstract base class containing the common attributes and methods. `Customer` and `Seller` extend `Person` and add their specific attributes. This design promotes code reuse, reduces duplication, and ensures that all person types have a consistent interface while allowing each subtype to define its own specialized behavior.

---

### 2. About the Generic Person Class

**Question:** Should there be a class that represents a "generic person" without specifying their role? Why or why not? What implication does this decision have on the ability to instantiate that class?

**Answer:**

There should NOT be a class that represents a generic person without specifying a role. The `Person` class should be declared as **abstract** for the following reasons:

1. **Business logic accuracy:** In the store's context, every person has a specific role - they are either a customer (buying products) or a seller (attending sales). A generic person without a role does not exist in the business domain.

2. **Prevents incomplete objects:** Making `Person` abstract prevents the creation of incomplete objects that lack role-specific information. A person without a role cannot perform any meaningful operation in the system.

3. **Polymorphism benefits:** The abstract class provides a common interface (getRoleDescription()) that all subclasses must implement, ensuring polymorphic behavior.

**Implication:** `Person` cannot be instantiated directly using `new Person()`. This is intentional and correct because the system should only work with concrete person types (`Customer` or `Seller`).

---

### 3. About Products in the System

**Question:** What characteristics do all products sold by the store have in common, regardless of their type? What characteristics are specific to each type of product?

**Answer:**

**Common characteristics (Product base class):**
- id: Unique product identifier
- title: Product name/title
- price: Sale price of the product
- quantity: Available stock quantity

**Specific characteristics:**
VideoGame:
  - platform: Gaming platform (PlayStation, Xbox, Nintendo Switch, PC)
  - genre: Game genre (Action, Adventure, RPG, Sports, etc.)
  - ageRating: Recommended age rating (ESRB classification: E, E10+, T, M, AO)

- Console:
  - brand: Manufacturer brand (Sony, Microsoft, Nintendo)
  - model: Specific model name/number
  - generation: Console generation (numeric value: 5, 6, 7, 8, 9)

---

 4. About Product Descriptions

Question: Each product type must be able to present a description that integrates its particular characteristics. How should this behavior be declared in the base class to ensure that all subclasses implement it properly? What OOP mechanism allows this?

Answer:

The getDescription() method should be declared as abstract in the Product base class:

java
public abstract String getDescription();


ANALYSIS AND DESIGN CHANGES
1. Should the accesories fused in the products hierarchy that already exists or should you as a team make a completely new independent hierarchy?

Answer: The easiest choice as a developer is to extend the hierarchy that already exits. That way we could reuse code that already exists, for example, if we talk about quantity, we could reuse the same exact function that we used to get the exact number of products in the first version of the system. In other words we could implement polymorphism, reuse code and work with database persistence if the case is so.

2.  What attributes are common to al the three types of accesories and which one of those are the specific of each type of accesory? How is this distinction reflected in a class hierarchy?

Answer:
All of the three types of accesories share the next attributes:

1.Id
2.Title
3.Price
4.Quantity

Specific attributes:
  - Controller: connection type (Wireless, Wire)
  - Cable: Length, Connector Type (HDMI, USB, optic)
  - Memory: Storage capacity, memory type(SD, microSD)

This distinction is reflected through an inheritance hierarchy where Accesory is an abstract base class containing the common attributes and methods. Controller, Cable and Memory extend Accesory and add their specific attributes. This design promotes code reuse, reduces duplication, and ensures that all person types have a consistent interface while allowing each subtype to define its own specialized behavior.


3. When we talk about compatibility between an accesory and a console, we defined as a relation between both of the entities. How do we represent this relation in design and persistence? Is compatibility an attribute of accesory, console or both of them?

Answer: 
The relationship should be represented with association in the model, on both sides.

In the accesory side each Controller has a list with compatibles consolers so they know which accesories are compatible which the exact type of console the customer wants to buy or any different management process that requires the system.

In the console side, every console could have the option to maintain a list of compatibles accesories for convenience, even though this can be computed the other way around.

4.Which modifications are necessary in the class SaleService for sales to include accesories without breaking the existing behavior with videogames and consoles?

Answer:

This are the things we should add or change in the SaleService class to include the accesories:

    1.registerSale must accepts a list of Product objects.
    2.Validates that at least one item exists
    3.Validates stock availability for each item
    4.Calculates total using product.getPrice()
    5.Updates inventory searching in the function ProductService.updateStock()

There must not be that much changes, since accesories extend products we are just going to new to change or determinate which service manages each proudct type, there also must be a way to validate the stock of the specific type of product

5. In which layer of the arquitecture of the system should you ubicate the new classes of the accesories module? justify your decision based on the responsabilities of each layer.

Answer: each one of the new clases should be ubicated in an specific layer in the structure of the system, since we cannot create a new class that DOES affect the output and the ui and not change the module, service and persistence for showing, saving and creating new methods that affects all the data of the new class.
