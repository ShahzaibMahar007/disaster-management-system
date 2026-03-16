# Disaster Management System

Comprehensive documentation for the Disaster Management System — a Java desktop application for managing affected families, relief inventory, and reporting for disaster relief operations.

---

## Quick Start

1. Fork the repository and clone your fork.
2. Install MySQL and create the database.
3. Import the schema from `database/sqlcode.sql`.
4. Place required JAR dependencies inside `/lib`.
5. Update database credentials inside `DBConnection.java`.
6. Compile and run the application.

---

## Project Overview

This project is a Java-based desktop application designed to assist disaster relief operations. It allows administrators to register affected families, manage relief inventory, track supplies, and generate reports.

The application uses Java Swing for the graphical interface and MySQL for persistent data storage using JDBC connectivity.

---

## Features

- Secure login interface
- Family registration and management
- Inventory management system
- Restocking functionality
- Inventory viewing dashboard
- Basic report generation
- MySQL database integration

---

## Technology Stack

- Java (JDK 8 or later)
- Java Swing (GUI)
- MySQL Database
- JDBC
- MySQL Connector
- rs2xml library

---

## Repository Structure

```
DISASTER-MANAGEMENT-SYS
│
├── database
│   └── sqlcode.sql
│
├── disasterrelief
│   ├── database
│   │   ├── DBConnection.java
│   │   └── InventoryManager.java
│   │
│   ├── models
│   │   ├── Family.java
│   │   └── InventoryItem.java
│   │
│   └── views
│       ├── InventoryUI.java
│       ├── LoginUI.java
│       ├── MainUI.java
│       ├── manageFamiliesUI.java
│       ├── RegisterFamilyUI.java
│       ├── report.java
│       ├── RestockUI.java
│       └── ViewInventoryUI.java
│
├── icon
│   └── login.png
│
├── lib
│   ├── mysql-connector-j-9.2.0.jar
│   └── rs2xml.jar
│
└── .gitignore
```

---

## Prerequisites

Before running the project ensure the following are installed:

- Java JDK 8 or newer
- MySQL Server
- Git

---

## Database Setup

Create the database:

```sql
CREATE DATABASE disaster_relief;
```

Import the schema:

```
mysql -u root -p disaster_relief < database/sqlcode.sql
```

---

## Configuration

Open:

```
disasterrelief/database/DBConnection.java
```

Update credentials:

```
jdbc:mysql://localhost:3306/disaster_relief
username
password
```

---

## Build & Run

Compile the project:

```
javac -cp "lib/*" -d out $(find . -name "*.java")
```

Run the application:

```
java -cp "out:lib/*" MainUI
```

---

## Usage

### Login

The system starts with a login interface where authorized users can sign in.

### Family Management

Users can:

- Register new families
- View registered families
- Update family information

### Inventory Management

Users can:

- Add inventory items
- Update stock
- View available supplies

### Restocking

Allows administrators to replenish inventory quantities.

### Reports

Generate basic reports for monitoring inventory and registered families.

---

## Data Models

### Family

Represents disaster-affected households.

Fields may include:

- Family ID
- Name
- Address
- Contact information
- Household size

### InventoryItem

Represents supply items stored in relief inventory.

Fields may include:

- Item ID
- Item name
- Quantity
- Description

---

## Dependencies

The project uses the following external libraries located in `/lib`:

- mysql-connector-j
- rs2xml

---

## Contributing

1. Fork the repository.
2. Create a new branch.

```
git checkout -b feature-name
```

3. Commit your changes.

```
git commit -m "Add feature"
```

4. Push to your fork.

```
git push origin feature-name
```

5. Open a Pull Request on GitHub.

---

## Code Style

- Use descriptive variable names
- Separate UI logic from database logic
- Follow Java naming conventions

---

## Security Considerations

- Avoid storing production database credentials in source code.
- Use parameterized SQL queries.

---

## Troubleshooting

**Database connection fails**  
Verify MySQL is running and credentials are correct.

**Application does not compile**  
Ensure required JAR files exist in `/lib` and are included in the classpath.

---

## Authors

Shahzaib Mahar — Project Lead  
Najaf — Developer  
Deepak — Developer

## Contributors

See the full list of contributors on GitHub:
https://github.com/ShahzaibMahar007/disaster-management-system/graphs/contributors

---

## License

This project currently does not include a license file. Add an appropriate open-source license before distributing.

