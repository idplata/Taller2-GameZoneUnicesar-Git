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
