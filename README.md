# Multi-Module Maven Project for AthenEase

Εχουμε βαλει πολλα Maven μεσα σε ενα μεγαλο Maven (ναι πρεπει να μαθετε Maven)
Καθε λειτουργικοτητα της εφαρμογης εχει το δικο της (αυτονομο) Maven Module οπου γραφει τον κωδικα της κανονικα στο src/main/java/com/athensease/

## Repository Structure
Η Δομή του Repository ειναι:

athensease/
├── pom.xml
├── interface/
│   ├── pom.xml
├── optimization/
│   ├── pom.xml
├── data-retrieval/
│   ├── pom.xml

## Modules
- **athensease**: Manages dependencies and build configuration for the project.
- **data-retrieval**: Handles fetching and parsing data from external APIs.
- **optimization**: Solves optimization problem with optaplanner library.
- **interface**: Handles User Interaction with the program.

## Prerequisites
- Java 11 or higher
- Apache Maven 3.6+

## Building the Project
Πως να ξεκινησετε

1. Clone the repository:
   ```bash
   git clone https://github.com/username/repo-name.git
2. Navigate to the project directory:
    ```bash
    cd athensease
3. Build the project:
    ```bash
    mvn clean install

