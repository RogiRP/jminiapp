package com.jminiapp.examples.creditcard;

import com.jminiapp.core.engine.JMiniAppRunner;

/**
 * Entry point for the CreditCardApp mini-application.
 *
 * This class uses the framework "frozen spot" JMiniAppRunner to:
 * - Register the app class to be executed
 * - Register the state class (Expense)
 * - Register the JSON adapter
 * - Set the application name (used for default filenames)
 */
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
