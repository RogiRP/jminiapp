# Credit Card Tracker Example

A simple credit card expense tracking application demonstrating the JMiniApp framework.

## Overview

This example shows how to create a basic mini-app using JMiniApp core that manages a list of credit card expenses. Users can add expenses, list them, and compute monthly totals through an interactive menu.

The application also demonstrates import/export functionality using the framework’s JSON adapter support and shows how application state is stored and retrieved automatically through the JMiniApp context.

## Features

- Add Expense  
- List Expenses  
- Monthly Total Calculation  
- Export to JSON  
- Import from JSON  
- Persistent Application State

## Project Structure

```text
credit-card-tracker/
├── pom.xml
├── README.md
└── src/com/jminiapp/examples/creditcard/
    ├── Expense.java
    ├── ExpenseJSONAdapter.java
    ├── CreditCardApp.java
    └── CreditCardAppRunner.java
```
## Key Components

### Expense Model
A representation of a credit card expense with:
- id (UUID)
- description
- amount
- date (YYYY-MM-DD)
- billingMonth (YYYY-MM)

### ExpenseJSONAdapter
Enables the JMiniApp framework to serialize and deserialize expense data via JSON import/export.

### CreditCardApp
Main application logic implementing:
- initialize()
- run()
- shutdown()

Includes menu options:
1. Add Expense  
2. List Expenses  
3. Show Monthly Total  
4. Export to JSON  
5. Import from JSON  
6. Exit  

### CreditCardAppRunner
Bootstraps the example using:
- forApp()
- withState()
- withAdapters()
- named()
- run()

## Building and Running

### Build
mvn clean install

### Run
mvn -pl examples/credit-card-tracker exec:java

## Example JSON Output

```text
[
  {
    "id": "sample-id",
    "description": "Groceries",
    "amount": 820.5,
    "date": "2025-01-18",
    "billingMonth": "2025-01"
  }
]
```


## Author
Roger Ruz
