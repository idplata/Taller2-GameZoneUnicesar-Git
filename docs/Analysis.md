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

3. **Polymorphism benefits:** The abstract class provides a common interface (`getRoleDescription()`) that all subclasses must implement, ensuring polymorphic behavior.

**Implication:** `Person` cannot be instantiated directly using `new Person()`. This is intentional and correct because the system should only work with concrete person types (`Customer` or `Seller`).

---

### 3. About Products in the System

**Question:** What characteristics do all products sold by the store have in common, regardless of their type? What characteristics are specific to each type of product?

**Answer:**

**Common characteristics (Product base class):**
- `id`: Unique product identifier
- `title`: Product name/title
- `price`: Sale price of the product
- `quantity`: Available stock quantity

**Specific characteristics:**
- **VideoGame:**
  - `platform`: Gaming platform (PlayStation, Xbox, Nintendo Switch, PC)
  - `genre`: Game genre (Action, Adventure, RPG, Sports, etc.)
  - `ageRating`: Recommended age rating (ESRB classification: E, E10+, T, M, AO)

- **Console:**
  - `brand`: Manufacturer brand (Sony, Microsoft, Nintendo)
  - `model`: Specific model name/number
  - `generation`: Console generation (numeric value: 5, 6, 7, 8, 9)

---

### 4. About Product Descriptions

**Question:** Each product type must be able to present a description that integrates its particular characteristics. How should this behavior be declared in the base class to ensure that all subclasses implement it properly? What OOP mechanism allows this?

**Answer:**

The `getDescription()` method should be declared as **abstract** in the `Product` base class:

```java
public abstract String getDescription();
