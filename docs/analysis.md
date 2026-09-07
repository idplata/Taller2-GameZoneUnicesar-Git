# GameZone Unicesar - System Analysis

## 1. People and their attributes

All people in the system share the attributes name, identification, and
phone number. Customers additionally have an email address and a purchase
history. Sellers have an employee code and a work shift.

The system can represent these common characteristics through an abstract
Person class, with Customer and Seller as specialized classes. This avoids
duplicating the common attributes and represents the different roles through
inheritance.

## 2. Generic person

A generic Person should not be instantiated directly because the system only
needs specific types of people: customers and sellers.

Therefore, Person should be declared as an abstract class. This allows it to
define common attributes and behavior while forcing the system to create
specific subclasses.

## 3. Product characteristics

All products share an identifier, title, price, and available inventory
quantity.

Video games have additional characteristics: platform, genre, and recommended
age rating. Consoles have brand, model, and generation.

The common attributes belong to the Product base class, while the specific
attributes belong to VideoGame and Console.

## 4. Product description

The Product class should declare an abstract description method. Each product
type has different characteristics, so each subclass must provide its own
implementation of the description.

This uses abstraction and polymorphism because the base class defines the
required behavior while each subclass implements that behavior in its own way.

## 5. Sale relationships

A Sale is associated with one Customer and one Seller, and it contains one or
more Products.

These are associations because a sale relates existing domain objects rather
than representing an inheritance relationship.

The products involved in the sale must be represented as part of the sale
because a sale records the products acquired by the customer.

## 6. Sale total

The Sale class should be responsible for calculating its own total because
the total is a property of a sale.

The calculation consists of adding the prices of the products included in the
sale. Keeping this behavior in Sale maintains the responsibility related to
the sale within the corresponding domain object.

## 7. Minimum one product

A sale cannot be registered without at least one product.

This business rule should be validated when the sale is registered, especially
in the SaleService, because the service is responsible for enforcing business
rules.

The service must reject an attempt to register a sale whose product collection
is empty.

## 8. Inventory update

When a sale is registered, the system must verify that sufficient stock exists
for every product involved.

After validation, the available quantity of each product is decreased by the
quantity sold.

The SaleService coordinates this operation because it applies the sale rules,
checks stock, updates the inventory, and uses the persistence layer to save
the resulting information.

## 9. Four-layer architecture

The system is organized into four layers:

- Model: contains the domain classes such as Person, Customer, Seller, Product,
  VideoGame, Console, and Sale.
- Persistence: contains the classes responsible for saving and loading
  information from files.
- Service: contains the business rules and coordinates operations involving
  the domain and persistence.
- User Interface: contains the console menu used by the user to interact with
  the system.

A class belongs to a layer according to its main responsibility. Domain
concepts belong to Model, file operations belong to Persistence, business
rules belong to Service, and interaction with the user belongs to User
Interface.

## 10. Persistence separation

File management should not be placed inside domain classes because domain
classes should represent the business concepts and their behavior rather than
the technical details of storing information.

Separating persistence improves organization and maintainability. It also
prevents the Model layer from depending on files or other infrastructure
details.

## 11. Layer dependencies

The allowed dependency direction is:

UI -> Service -> Persistence -> Model

The Service layer can also depend directly on the Model because it applies
business rules to domain objects.

The Model does not depend on any other layer. The UI must not access
Persistence directly; it must use Services. Persistence depends on Model so
that stored information can be represented using domain objects.

This structure provides separation of responsibilities and keeps the
architecture organized into independent layers.
