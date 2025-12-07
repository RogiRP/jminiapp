package com.jminiapp.examples.creditcard;

import com.jminiapp.core.api.JMiniApp;
import com.jminiapp.core.api.JMiniAppConfig;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CreditCardApp extends JMiniApp {

    private List<Expense> expenses;

    private Scanner scanner;

    private boolean running;

    public CreditCardApp(JMiniAppConfig config) {
        super(config);
    }

    @Override
    protected void initialize() {
        System.out.println("\n=== " + appName + " ===");
        System.out.println("Credit card expense tracker initialized.\n");

        scanner = new Scanner(System.in);
        running = true;

        try {
            context.importData("json");
        } catch (IOException e) {
            System.out.println("No existing JSON file found or error importing data. Starting fresh.");
        }

        List<Expense> data = context.getData();
        if (data != null && !data.isEmpty()) {
            expenses = data;
            System.out.println("Loaded " + expenses.size() + " existing expenses from context.");
        } else {
            expenses = new ArrayList<>();
            System.out.println("No existing expenses found. Starting with an empty list.");
        }
    }


    @Override
    protected void run() {
        while (running) {
            showMenu();
            handleUserInput();
        }
    }

    @Override
    protected void shutdown() {
        context.setData(expenses);

        if (scanner != null) {
            scanner.close();
        }

        System.out.println("\nState saved into context.");
        System.out.println("Thank you for using " + appName + "!");
    }

    // ============================================================
    // Menu and User Interaction
    // ============================================================

    private void showMenu() {
        System.out.println("\n--- Main Menu ---");
        System.out.println("1. Add expense");
        System.out.println("2. List all expenses");
        System.out.println("3. Show monthly total");
        System.out.println("4. Export data to JSON");
        System.out.println("5. Import data from JSON");
        System.out.println("6. Exit");
        System.out.print("Choose an option: ");
    }

    private void handleUserInput() {
        String option = scanner.nextLine().trim();

        switch (option) {
            case "1":
                addExpense();
                break;
            case "2":
                listExpenses();
                break;
            case "3":
                showMonthlyTotal();
                break;
            case "4":
                exportToJSON();
                break;
            case "5":
                importFromJSON();
                break;
            case "6":
                running = false;
                System.out.println("\nExiting application...");
                break;
            default:
                System.out.println("Invalid option. Please try again.");
        }
    }

    /**
     * Adds a new expense to the list.
     *
     * Asks the user for:
     * - Description
     * - Amount
     * - Date (YYYY-MM-DD)
     * - Billing month (YYYY-MM)
     *
     * Then creates a new Expense and stores it in the in-memory list.
     */
    private void addExpense() {
        System.out.println("\n--- Add Expense ---");

        System.out.print("Description: ");
        String description = scanner.nextLine().trim();

        if (description.isEmpty()) {
            System.out.println("Description cannot be empty. Expense not added.");
            return;
        }

        System.out.print("Amount: ");
        String amountInput = scanner.nextLine().trim();
        double amount;

        try {
            amount = Double.parseDouble(amountInput);
        } catch (NumberFormatException e) {
            System.out.println("Invalid amount. Please enter a valid number. Expense not added.");
            return;
        }

        System.out.print("Purchase date (YYYY-MM-DD): ");
        String date = scanner.nextLine().trim();

        System.out.print("Billing month (YYYY-MM): ");
        String billingMonth = scanner.nextLine().trim();

        Expense expense = new Expense(description, amount, date, billingMonth);
        expenses.add(expense);

        System.out.println("Expense added successfully:");
        System.out.println(formatExpense(expense));
    }

    /**
     * Lists all expenses currently stored in memory.
     */
    private void listExpenses() {
        System.out.println("\n--- List of Expenses ---");

        if (expenses.isEmpty()) {
            System.out.println("No expenses recorded.");
            return;
        }

        int index = 1;
        for (Expense expense : expenses) {
            System.out.println(index + ") " + formatExpense(expense));
            index++;
        }
    }

    /**
     * Computes and displays the total expenses for a given billing month.
     */
    private void showMonthlyTotal() {
        System.out.println("\n--- Monthly Total ---");
        System.out.print("Enter billing month (YYYY-MM): ");
        String billingMonth = scanner.nextLine().trim();

        double total = 0.0;
        int count = 0;

        for (Expense expense : expenses) {
            if (billingMonth.equals(expense.getBillingMonth())) {
                total += expense.getAmount();
                count++;
            }
        }

        System.out.println("Number of expenses for " + billingMonth + ": " + count);
        System.out.println("Total amount for " + billingMonth + ": " + String.format("%.2f", total));
    }

    /**
     * Helper method to format an Expense as a single readable line.
     */
    private String formatExpense(Expense expense) {
        return "[ID: " + expense.getId() + "] " +
                "[" + expense.getBillingMonth() + "] " +
                expense.getDescription() + " | " +
                "Date: " + expense.getDate() + " | " +
                "Amount: " + String.format("%.2f", expense.getAmount());
    }

    /**
     * Exports the current list of expenses to a JSON file.
     * Filename used: {appName}.json
     */
    private void exportToJSON() {
        System.out.println("\n--- Export Data to JSON ---");

        try {
            context.setData(expenses);
            context.exportData("json");
            System.out.println("Data exported successfully to " + appName + ".json");
        } catch (IOException e) {
            System.out.println("Error exporting data: " + e.getMessage());
        }
    }

    /**
     * Imports expenses from a JSON file.
     * Filename used: {appName}.json
     *
     * This replaces the current in-memory list with the imported data.
     */
    private void importFromJSON() {
        System.out.println("\n--- Import Data from JSON ---");

        try {
            context.importData("json");
            List<Expense> data = context.getData();

            if (data != null && !data.isEmpty()) {
                expenses = data;
                System.out.println("Data imported successfully from " + appName + ".json");
                System.out.println("Total expenses loaded: " + expenses.size());
            } else {
                System.out.println("No data found in file. The in-memory list remains unchanged.");
            }
        } catch (IOException e) {
            System.out.println("Error importing data: " + e.getMessage());
        }
    }
}
