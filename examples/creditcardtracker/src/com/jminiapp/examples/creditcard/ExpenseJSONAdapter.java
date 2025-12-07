package com.jminiapp.examples.creditcard;

import com.jminiapp.core.adapters.JSONAdapter;

public class ExpenseJSONAdapter implements JSONAdapter<Expense> {

    @Override
    public Class<Expense> getstateClass() {
        return Expense.class;
    }
}
