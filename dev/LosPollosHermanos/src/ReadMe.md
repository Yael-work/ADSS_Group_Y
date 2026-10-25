# SUPER-TRANSPORT-EMPLOYEES MANAGEMENT SYSTEM

## Team

- Tom Gluzman
- Ethen Krimer
- Yael Durahly
- Denis Coleman


## Description
A Java-based transport management system for handling transports, routes, drivers, trucks, and delivery files.
A comprehensive solution for managing logistics operations, including transport planning, driver assignments, and delivery tracking.
Also, provides functionalities for updating and canceling transports, retrieving delivery files, and managing transport routes.
It includes the employee management module for handling employee-related operations, such as adding, updating, and deleting employees.
The system is designed to be modular and extensible, allowing for easy integration with other systems and services.


## Features
- Manage transports and transport routes
- Assign drivers and trucks
- Update and cancel transports
- Retrieve delivery files
- Set new planned dates and times for transports
- Delete transport routes
- Get drivers and delivery files by ID
- Manage employees (add, update, delete)
- Handle transport planning and execution
- employee management module for handling employee-related operations


## Requirements
- **openjdk**: Java Development Kit (JDK) version 17 or higher
- **Java**: Version 17 or higher
- **Hibernate**: ORM framework for database interactions
- **Junit**: For unit testing
- **Jboss**: Application server for deploying the application
- **xerial/sqlite-jdbc**: SQLite JDBC driver for database connectivity


## instructions
For complete instructions, database explanations, and organized information about our project, 
please refer to the PDF file titled "The Full Guide for Transport and Employees Systems" located 
in the current project folder. We have separated this documentation to keep the README file clean 
and focused on the codebase, while the full description is provided in the referenced guide.

## Setup
### Step 1: Clone the repository
```bash
git clone https://github.com/A-D-Of-Software-Systems-Group-Y/ADSS_Group_Y.git
cd ADSS_Group_Y
```
### Step 2: Install dependencies
```bash



#Build the project

##Start the application


##Structure

ADSS_Group_Y/
├── src/
│   ├── Domain/
│   │   ├── transport/
│   │   │   ├── TransportManager.java
│   │   │   ├── TransportRouteController.java
│   │   │   ├── TransportController.java
│   │   ├── employee/
│   │   │   ├── EmployeeManager.java
│   │   │   ├── EmployeeController.java
│   │   │   ├── Employee.java
│   ├── Helper/
│   │   ├── Helper.java
│   ├── Controllers/
│   │   ├── DeliveryFileController.java
│   │   ├── DriversManager.java
│   ├── Enums/
│   │   ├── TransportStatusEnum.java
│   ├── Models/
│   │   ├── Transport.java
│   │   ├── TransportRoute.java
│   │   ├── Driver.java
│   │   ├── Truck.java
│   │   ├── DeliveryFile.java
├── resources/
│   ├── application.properties
│   ├── database/
│   │   ├── schema.sql
│   │   ├── data.sql
├── tests/
│   ├── TransportManagerTest.java
│   ├── EmployeeManagerTest.java
├── README.md
├──
├── .gitignore


This version improves readability by using consistent formatting, headings, and code blocks for commands.

