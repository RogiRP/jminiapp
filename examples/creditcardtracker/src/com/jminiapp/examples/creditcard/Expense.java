package com.jminiapp.examples.creditcard;

import java.util.UUID;

public class Expense {

    private String id;

    /**
     * Description of the expense.
     * Example: "Netflix", "Groceries", "Uber".
     */
    private String description;

    /**
     * Monetary amount of the expense.
     */
    private double amount;

    private String date;

    /**
     * Billing month to which this expense belongs.
     *
     * Example: "2025-12".
     * This is used to compute the monthly total.
     */
    private String billingMonth;

    public Expense() {
    }

    public Expense(String description, double amount, String date, String billingMonth) {
        this.id = UUID.randomUUID().toString();
        this.description = description;
        this.amount = amount;
        this.date = date;
        this.billingMonth = billingMonth;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getBillingMonth() {
        return billingMonth;
    }

    public void setBillingMonth(String billingMonth) {
        this.billingMonth = billingMonth;
    }

    @Override
    public String toString() {
        return "Expense{" +
                "id='" + id + '\'' +
                ", description='" + description + '\'' +
                ", amount=" + amount +
                ", date='" + date + '\'' +
                ", billingMonth='" + billingMonth + '\'' +
                '}';
    }
}
