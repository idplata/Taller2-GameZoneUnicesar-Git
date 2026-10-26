# AI Usage Log - Technical Leader

## Team Member: Irina Danaeth Plata Suarez
## Role: Technical Leader
## Module: Sales and Integration
## Repository: Taller2-GameZoneUnicesar-Git

---

## Purpose of This Log

This document records my legitimate use of Artificial Intelligence tools during the development of the GameZone Unicesar project. All AI usage documented here complies with the permitted uses established in the course guidelines. Each entry includes the tool used, the nature of the consultation, and how the information was applied to the project.

---

## AI Usage Record

### Session 1: Project Setup and Git Configuration
**Date:** September 5, 2026
**Tool:** ChatGPT

**Consultation:**
I had doubts about how to properly configure a multi-module Maven project with the four-layer architecture structure specified in the assignment. I asked about the correct pom.xml structure and package organization.

**Response Received:**
The AI explained the Maven project structure, the groupId and artifactId conventions, and how to organize source folders.

**Application to Project:**
I used this information to create the initial pom.xml file and to establish the `com.gamezone` package structure with the four subpackages (model, persistence, service, ui).

**Human Contribution:**
I wrote the actual pom.xml file and adapted the structure to our specific needs, including the exec-maven-plugin for running the application.

---

### Session 2: Implementing the Sale Class
**Date:** September 5, 2026
**Tool:** ChatGPT

**Consultation:**
I needed to implement the `LocalDateTime` functionality for the sale date and wasn't sure about the correct import and usage. I also wanted to confirm best practices for implementing `Serializable` for persistence.

**Response Received:**
The AI provided examples of how to use `LocalDateTime.now()` and explained serialization best practices, including using `serialVersionUID`.

**Application to Project:**
I implemented the `Sale` class with the `LocalDateTime` for automatic date assignment and added the `serialVersionUID`. I also applied the AI's advice on returning defensive copies of the product list.

**Human Contribution:**
I wrote the complete `Sale` class, including all getters, setters, and the `calculatePrice()` method. I made the design decision to return `new ArrayList<>(products)` to protect the internal state.

---

### Session 3: Writing Analysis Document
**Date:** September 6, 2026
**Tool:** ChatGPT

**Consultation:**
I had conceptual doubts about what information should be included in the `analysis.md` document. I asked for the general structure and format of a software analysis document.

**Response Received:**
The AI provided a general outline for an analysis document, including sections for each question with explanations.

**Application to Project:**
I used this outline to structure the `analysis.md` document and created all the sections. I wrote the actual content from scratch.

**Human Contribution:**
I wrote all the answers in English with my own analysis and justifications. The AI only helped with the structure and format guidance.

---

### Session 4: Mermaid Diagram Syntax
**Date:** September 6, 2026
**Tool:** ChatGPT

**Consultation:**
I encountered issues with Mermaid syntax for displaying abstract classes and methods in class diagrams. I asked how to properly denote abstract classes (`<<abstract>>`) and abstract methods.

**Response Received:**
The AI explained the correct Mermaid syntax for class diagrams, including how to mark classes as abstract and how to show relationships.

**Application to Project:**
I used this information to create the `hierarchy-diagram.md` and `class-diagram.md` files, ensuring all abstract classes and methods were correctly marked.

**Human Contribution:**
I created the complete diagrams from scratch, determining which classes and relationships to include based on our specific project design.

---

### Session 5: Git Merge Conflict Resolution
**Date:** September 7, 2026
**Tool:** ChatGPT

**Consultation:**
I encountered errors when trying to push to the develop branch because the remote had changes I didn't have locally. I asked how to resolve this situation safely.

**Response Received:**
The AI explained the `git pull` workflow, the difference between merge and rebase, and recommended using `git stash` to save local changes before pulling.

**Application to Project:**
I applied this workflow to successfully integrate remote changes and resolve the divergence between local and remote branches.

**Human Contribution:**
I executed the commands, resolved the specific merge conflicts manually, and pushed the final code to the repository.

---

### Session 6: Reviewing Pull Requests
**Date:** September 7, 2026
**Tool:** ChatGPT

**Consultation:**
I asked for a checklist of things to review when evaluating Pull Requests for a layered architecture project.

**Response Received:**
The AI provided a code review checklist covering aspects like layer separation, method naming conventions, JavaDoc completeness, and adherence to the project structure.

**Application to Project:**
I used this checklist to guide my reviews of the product and person modules, ensuring they followed our established standards.

**Human Contribution:**
I performed the actual code reviews, identified specific issues in each PR, and provided constructive feedback to the team members.

---

### Session 7: Creating the README File
**Date:** September 7, 2026
**Tool:** ChatGPT

**Consultation:**
I asked for a general structure and examples of what a good README.md for a Java project should include.

**Response Received:**
The AI provided a standard README structure with sections for Description, Team, Technologies, Architecture, Installation, and Usage.

**Application to Project:**
I used this as a template to create our project's README.md.

**Human Contribution:**
I wrote all the specific content about our project, including team details, actual installation commands, and accurate descriptions of our features and architecture. The AI only provided the structural outline.

### Session 8: Integrating Accessory Category into Discounts (A1)
**Date:** October 26, 2026
**Tool:** Claude

**Consultation:**
I needed to adapt the `CategoryDiscount` logic so that it would accept accessories (`ACCESSORY`) as a valid target category, given that the new accessory module was now integrated.

**Response Received:**
The AI suggested adding a validation method (`isAllowedCategory`) to check for the three valid categories and provided an example of the conditional logic inside the `calculateDiscount` method.

**Application to Project:**
I applied the validation structure to ensure the system rejects invalid categories. I also updated the console menu to allow the user to type "ACCESSORY" when registering a promotion by category.

**Human Contribution:**
I adapted the provided logic to my existing `CategoryDiscount` and `PromotionService` classes. I manually tested the flow to ensure the discount was applied correctly only to accessories, keeping the rest of the sale total intact.

---

### Session 9: Fixing the Discounted Refund Calculation (A5)
**Date:** October 26, 2026
**Tool:** Claude

**Consultation:**
When a sale had a promotion applied, the return system was refunding the full list price of the item instead of the discounted price. I asked for a mathematical approach to fix this in the `Return` class.

**Response Received:**
The AI explained how to calculate a proportional discount per item using the formula `price × (1 - totalDiscount / subtotal)`. It also suggested helper methods to structure the receipt breakdown.

**Application to Project:**
I used the proportional formula to ensure the customer is refunded exactly what they paid. I updated the `generateReturnReceipt` to display the item's list price, its proportional discount, and the final refunded amount.

**Human Contribution:**
I implemented the math inside the `Return` entity, ensuring the division by zero was handled if a subtotal was 0. I adapted the receipt strings to match the existing Spanish output format required by the UI.

---

### Session 10: Unifying the Sale Registration Flow (A3)
**Date:** October 26, 2026
**Tool:** Claude

**Consultation:**
The `SaleService.registerSale` method was getting too complex to handle stock validation, promotions, and warranties all at once without leaving the inventory in a broken state if an error occurred.

**Response Received:**
The AI proposed a step-by-step transactional flow: validate everything first, discount the stock, and use a `try-catch` block. If any step after discounting fails, it triggers a `restoreStock` rollback.

**Application to Project:**
I adapted this structure into the `SaleService`. I also updated the `Sale` entity to store the extended warranty cost and reflect it in the final total and the `generateReceipt()` method.

**Human Contribution:**
I wrote the complete logic for grouping items using `Map<String, Integer>` to avoid issues with duplicated products in the cart. I manually integrated the UI flow so the system asks the user for extended warranties before finalizing the sale.

---

### Session 11: Resolving Compilation and Serialization Errors
**Date:** October 26, 2026
**Tool:** Claude

**Consultation:**
The project was failing to compile, and even after compiling, saving sales to the `.dat` file was throwing a `NotSerializableException`. I asked for the common causes of these errors.

**Response Received:**
The AI pointed out that class names must perfectly match the file names (e.g., `Basicwarranty.java` vs `BasicWarranty.java`) and explained that all objects inside a serialized class must also implement `Serializable`.

**Application to Project:**
I renamed the warranty file to fix the compilation issue and made the `Person` class implement `Serializable` so that `Sale` objects (which contain `Customer` and `Seller`) could be saved correctly.

**Human Contribution:**
I debugged the NetBeans output to find the exact lines causing the issue. I applied the serialization fix and successfully verified that the `sales.dat` file was being written and read without data loss.
