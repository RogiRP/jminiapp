---
sidebar_position: 1
---

# Credit Card Tracker Example Application

A simple credit card expense tracking application.

**Features:**
- Add, list, and summarize credit card expenses  
- Monthly total computation  
- Persistent state stored 
- JSON import/export  
- Interactive menu system

**Source Code:** `examples/creditcardtracker`

---

### Key Concepts Demonstrated

- Application lifecycle (`initialize`, `run`, `shutdown`)
- In-memory state management through a list of domain objects (`Expense`)
- JSON format adapter implementation (`ExpenseJSONAdapter`)
- Use of `JMiniAppRunner` to bootstrap the mini-application

---

### Quick Start
```bash
cd examples/creditcardtracker
mvn clean install
mvn exec:java
```

---

### Code Highlights

## State Model:

```java
public class Expense {

    private String id;
    private String description;
    private double amount;
    private String date;
    private String billingMonth;

    public Expense() {}

    public Expense(String description, double amount, String date, String billingMonth) {
        this.id = UUID.randomUUID().toString();
        this.description = description;
        this.amount = amount;
        this.date = date;
        this.billingMonth = billingMonth;
    }

    public String toString() {
        return "[ID: " + id + "] [" + billingMonth + "] " + description +
               " | Date: " + date + " | Amount: " + amount;
    }
}
```

This model contains all data required to store and compute credit card expenses, including a unique ID and a billing month field used for monthly summaries.

---

## JSON Adapter:

```java
public class ExpenseJSONAdapter implements JSONAdapter<Expense> {

    @Override
    public Class<Expense> getstateClass() {
        return Expense.class;
    }
}
```

This adapter enables JSON serialization and deserialization of expense objects.  
Once registered, the framework automatically handles reading and writing the JSON file:

```
CreditCardTracker.json
```

---

## Application:

```java
public class CreditCardApp extends JMiniApp {

    private List<Expense> expenses;
    private Scanner scanner;
    private boolean running;

    public CreditCardApp(JMiniAppConfig config) {
        super(config);
    }

    @Override
    protected void initialize() {
        scanner = new Scanner(System.in);
        running = true;

        try {
            context.importData("json");
            List<Expense> data = context.getData();
            expenses = (data != null) ? data : new ArrayList<>();
            System.out.println("Loaded " + expenses.size() + " stored expenses.");
        } catch (Exception e) {
            expenses = new ArrayList<>();
            System.out.println("No previous data found. Starting with an empty list.");
        }
    }

    @Override
    protected void run() {
        while (running) {
            System.out.println("\n--- Credit Card Tracker ---");
            System.out.println("1. Add Expense");
            System.out.println("2. List Expenses");
            System.out.println("3. Show Monthly Total");
            System.out.println("4. Export Data to JSON");
            System.out.println("5. Import Data from JSON");
            System.out.println("6. Exit");
            System.out.print("\nChoose an option: ");

            String choice = scanner.nextLine();
            switch (choice) {
                case "1": addExpense(); break;
                case "2": listExpenses(); break;
                case "3": showMonthlyTotal(); break;
                case "4": exportData(); break;
                case "5": importData(); break;
                case "6": running = false; break;
                default: System.out.println("Invalid option.");
            }
        }
    }

    @Override
    protected void shutdown() {
        context.setData(expenses);
        System.out.println("Expenses saved. Goodbye!");
    }

    // Methods addExpense(), listExpenses(), showMonthlyTotal(),
    // exportData(), importData() implemented in source.
}
```

The app demonstrates:

- Lifecycle flow  
- Context-based state persistence  
- A text-based menu loop  
- Basic domain operations  

---

## Bootstrap:

```java
public class CreditCardAppRunner {

    public static void main(String[] args) {
        JMiniAppRunner
            .forApp(CreditCardApp.class)
            .withState(Expense.class)
            .withAdapters(new ExpenseJSONAdapter())
            .named("CreditCardTracker")
            .run(args);
    }
}
```

The `.named("CreditCardTracker")` setting determines the filename used for JSON storage:

```
CreditCardTracker.json
```

---

This example demonstrates the core concepts of JMiniApp: lifecycle management, domain modeling, state handling, JSON adapter integration, and user interaction.  
The framework takes care of the infrastructure, allowing the developer to focus on application behavior.
