package laptopcompare.in;

import java.util.InputMismatchException; // For handling non-integer input
import java.util.Scanner; // For user input

// Manages a mock bank account for purchases
class BankAccount {
    private double balance; // Current balance in the account

    /**
     * Constructor for BankAccount. Initializes with a default balance.
     */
    public BankAccount() {
        this.balance = 1000.00; // Initial balance
        System.out.println("           Initial bank balance: $" + this.balance);
    }

    /**
     * Deducts a specified amount from the balance if sufficient funds are available.
     * Prompts to add funds if insufficient.
     * @param amount The amount to deduct.
     */
    public void deductBalance(double amount) {
        if (balance >= amount) {
            balance -= amount;
            System.out.println("             Purchase successful! Remaining balance: $" + String.format("%.2f", balance));
        } else {
            System.out.println("             Insufficient Balance! You need to add money.");
            promptForFunds(); // Offer to add funds
        }
    }

    /**
     * Adds a specified amount to the balance.
     * @param amount The amount to add.
     */
    public void addFunds(double amount) {
        if (amount > 0) {
            balance += amount;
            System.out.println("             Money added successfully. New balance: $" + String.format("%.2f", balance));
        } else {
            System.out.println("             Amount to add must be positive.");
        }
    }

    /**
     * Gets the current balance.
     * @return The current balance.
     */
    public double getBalance() {
        return balance;
    }

    /**
     * Private helper method to prompt the user to add funds.
     */
    private void promptForFunds() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("             Would you like to add funds? (yes/no): ");
        String addFundsChoice = scanner.nextLine();

        if (addFundsChoice.equalsIgnoreCase("yes")) {
            System.out.print("             Enter amount to add: $");
            try {
                double amount = scanner.nextDouble();
                addFunds(amount);
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a numerical amount.");
            } finally {
                scanner.nextLine(); // Consume newline, whether input was valid or not
            }
        }
    }
}
