package com.pluralsight.ui;

import com.pluralsight.models.Transaction;
import static com.pluralsight.ui.Console.*;
import java.io.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class AccountingLedgerApp
{
    /** Declare a class level static variable for ArrayList<Transaction> here, then every method in this class has access to it */
    private static ArrayList<Transaction> ledgerLog;

    /** Main method */
    static void main() {
        ledgerLog  = getLedgerLog();
        Collections.sort(ledgerLog, Comparator.comparing(Transaction::getDate).thenComparing(Transaction::getTime).reversed());
        mainMenu();
    }

    /** Displays Main Screen Menu */
    private static void mainMenu() {
        do {
            System.out.printf("""
                    \n
                    ------ Home Screen ------
                    Current Balance: $%.2f
                    D) Add Deposit
                    P) Make Payment (Debit)
                    L) Ledger - Ledger Display screen
                    X) Exit - exit the application
                    -------------------------
                    """, getBalance()
            );
            String command = promptForString("Enter one of the given command letters: ").toLowerCase();
            switch (command)
            {
                case "d":
                    addDeposit();
                    break;
                case "p":
                    makePayment();
                    break;
                case "l":
                    ledgerMenu();
                    break;
                case "x":
                    System.out.println("Thank you, Goodbye!");
                    return;
                default:
                    System.out.print("Invalid command! Please choose from the given command letters: ");
            }
        } while (true);
    }

    /** Returns Current Balance of Account */
    private static float getBalance() {
        float balance = 0;
        for (int i = 0; i < ledgerLog.size(); i++)
        {   balance += ledgerLog.get(i).getPrice(); }
        return balance;
    }

    /** Add a deposit Transaction to the ledger log */
    private static void addDeposit() {


        String description = promptForString("Enter deposit Description: ");
        String vendor = promptForString("Enter deposit Vendor: ");
        float amount = promptForFloat("Enter deposit Amount: ");
        if (amount <= 0) {
            while (amount <= 0)
            {   amount = promptForFloat("Deposit must be positive! Enter a positive value: ");  }
        }

        LocalDate date = LocalDate.now();
        LocalTime time = LocalTime.now().withNano(0);

        try (BufferedWriter bufWriter = new BufferedWriter(new FileWriter("transactions.csv", true))) {
            bufWriter.write(date + "|" + time + "|" + description + "|" + vendor + "|" + amount);
            bufWriter.newLine();
            bufWriter.close();

            ledgerLog.add(new Transaction(date, time, description, vendor, amount));
            Collections.sort(ledgerLog, Comparator.comparing(Transaction::getDate).thenComparing(Transaction::getTime).reversed());
        } catch (IOException e) {
            System.out.println("---- Error! ----- Deposit not saved ----");
        }

    }

    /** Add a payment Transaction to the ledger log */
    private static void makePayment() {
        String description = promptForString("Enter payment Description: ");
        String vendor = promptForString("Enter payment Vendor: ");
        float amount = promptForFloat("Enter payment Amount: ");
        if (amount >= 0) {
            while (amount >= 0)
            {   amount = promptForFloat("Payment must be negative! Enter a negative value: ");  }
        }

        LocalDate date = LocalDate.now();
        LocalTime time = LocalTime.now().withNano(0);

        try ( BufferedWriter bufWriter = new BufferedWriter(new FileWriter("transactions.csv", true))) {
            bufWriter.write(date + "|" + time + "|" + description + "|" + vendor + "|" + amount);
            bufWriter.newLine();
            bufWriter.close();

            ledgerLog.add(new Transaction(date, time, description, vendor, amount));
            Collections.sort(ledgerLog, Comparator.comparing(Transaction::getDate).thenComparing(Transaction::getTime).reversed());
        } catch (IOException e) {
            System.out.println("---- Error! ----- Payment not saved ----");
        }



    }

    /** Displays ledger Screen Menu. */
    private static void ledgerMenu() {
        String command = "";
        while (!command.equalsIgnoreCase("H")) {
            System.out.print("""
                    \n
                    ----- Ledger Screen -----
                    A) All - Display All Transactions
                    D) Deposits - Display All Deposits Only
                    P) Payments - Display All Payments Only
                    R) Reports - Custom Display Options
                    H) Home - Go back to home page
                    -------------------------
                    """
            );
            command = promptForString("Enter one of the given command letters: ").toLowerCase();
            switch (command) {
                case "a":
                    displayAllTrans();
                    break;
                case "d":
                    displayDeposits();
                    break;
                case "p":
                    displayPayments();
                    break;
                case "r":
                    reportsMenu();
                    break;
                case "h":
                    System.out.println("Returning to Home Screen....");
                    break;
                default:
                    System.out.print("Invalid command! Please choose from the given command letters: ");
            }
        }
    }

    /** Displays all transactions through the Ledger Screen. */
    private static void displayAllTrans() {
        System.out.println("\n--------------- All Transactions ---------------");
        for (int i = 0; i < ledgerLog.size(); i++)
        {   System.out.println(ledgerLog.get(i).formattedTransaction());    }
    }

    /** Displays all deposits through the Ledger Screen. */
    private static void displayDeposits() {
        System.out.println("\n----------------- All Deposits ------------------");
        for (int i = 0; i < ledgerLog.size(); i++) {
            if (ledgerLog.get(i).getPrice() > 0)
            {   System.out.println(ledgerLog.get(i).formattedTransaction()); }
        }
    }

    /** Displays all payments through the Ledger Screen. */
    private static void displayPayments() {
        System.out.println("\n----------------- All Payments ------------------");
        for (int i = 0; i < ledgerLog.size(); i++) {
            if (ledgerLog.get(i).getPrice() < 0)
            {   System.out.println(ledgerLog.get(i).formattedTransaction()); }
        }
    }

    /** Displays Report Screen Menu. */
    private static void reportsMenu() {
        int command = -1;
        while (command != 0) {
            System.out.print("""
                    \n
                    ----- Report Options Screen -----
                    1) Month-To-Date Report
                    2) Previous Month Report
                    3) Year-To-Date Report
                    4) Previous Year Report
                    5) Search By Vendor
                    6) Custom Search Report
                    0) Back - Go back to ledger screen
                    ---------------------------------
                    """
            );
            command = promptForInt("Enter command #: ");
            switch (command) {
                case 1:
                    monthToDate();
                    break;
                case 2:
                    previousMonth();
                    break;
                case 3:
                    yearToDate();
                    break;
                case 4:
                    previousYear();
                    break;
                case 5:
                    searchByVendor();
                    break;
                case 6:
                    customSearch();
                    break;
                case 0:
                    System.out.println("Returning to Ledger Screen....");
                    break;
                default:
                    System.out.println("Invalid command! Please choose from the given command numbers: ");
            }
        }
    }

    /** Allows user to get a custom Ledger Report based on chosen fields */
    private static void customSearch() {
        System.out.println("Enter a value to filter by that field, or press Enter to skip:");
        LocalDate startDate = promptForDate("Start Date (yyyy-MM-dd): ");
        LocalDate endDate = promptForDate("End Date (yyyy-MM-dd): ");
        String description = promptForString("Description: ");
        String vendor = promptForString("Vendor: ");
        Float min = promptNullableFloat("Enter minimum amount value: ");
        Float max = promptNullableFloat("Enter maximum amount value: ");

        ArrayList<Transaction> results = new ArrayList<>();
        for (int i = 0; i < ledgerLog.size(); i++) {
            Transaction t = ledgerLog.get(i);
            if (startDate != null && t.getDate().isBefore(startDate)) { continue; }
            if (endDate != null && t.getDate().isAfter(endDate)) { continue; }
            if (!description.isEmpty() && !t.getDescription().toLowerCase().contains(description.toLowerCase())) { continue; }
            if (!vendor.isEmpty() && !t.getVendor().toLowerCase().contains(vendor.toLowerCase())) { continue; }
            if (min != null && t.getPrice() < min) { continue; }
            if (max != null && t.getPrice() >  max) { continue; }
            results.add(t);
        }
        System.out.println("Search Filter Results:");
        if (results.isEmpty()) {
            System.out.println("No transactions found matching your search filters.");
        } else if (results.size() == 1) {
            Transaction oneTransaction = results.getFirst();
            System.out.println("One matching transaction found.....\n" + oneTransaction.formattedTransaction());
        } else {
            System.out.println(results.size() + " matching transactions found..... ");
            for (int i = 0; i < results.size(); i++)
            {   System.out.println(results.get(i).formattedTransaction());  }
        }
    }

    /** Allows user to receive a custom Ledger report based on specified vendor search. */
    private static void searchByVendor() {
        String vendor = promptForString("Enter Vendor : ");
        for (int i = 0; i < ledgerLog.size(); i++) {
            if (ledgerLog.get(i).getVendor().toLowerCase().contains(vendor.toLowerCase()))
            {   System.out.println(ledgerLog.get(i).formattedTransaction());   }
        }
    }

    /** Displays a Ledger report on Transactions made in the previous year. */
    private static void previousYear() {
        LocalDate today = LocalDate.now();
        int prevYear = today.getYear() - 1;
        System.out.println("\n---- Previous Year ----");
        for (int i = 0; i < ledgerLog.size(); i++) {
            if (ledgerLog.get(i).getDate().getYear() == prevYear)
            {   System.out.println(ledgerLog.get(i).formattedTransaction()); }
        }
    }

    /** Displays a Ledger report on Transactions made from Year-To-Date. */
    private static void yearToDate() {
        LocalDate today = LocalDate.now();
        LocalDate ytdStart = today.withDayOfYear(1);
        System.out.println("\n----- Year-To-Date -----");
        for (int i = 0; i < ledgerLog.size(); i++) {
            if (!ledgerLog.get(i).getDate().isBefore(ytdStart) && !ledgerLog.get(i).getDate().isAfter(today))
            {   System.out.println(ledgerLog.get(i).formattedTransaction()); }
        }
    }

    /** Displays a Ledger report on Transactions made in the previous month. */
    private static void previousMonth() {
        LocalDate firstOfMonth = LocalDate.now().withDayOfMonth(1);
        LocalDate prevMonthStart = firstOfMonth.minusMonths(1);
        LocalDate prevMonthEnd = firstOfMonth.minusDays(1);
        System.out.println("\n---- Previous Month ----");
        for (int i = 0; i < ledgerLog.size(); i++) {
            if(!ledgerLog.get(i).getDate().isBefore(prevMonthStart) && !ledgerLog.get(i).getDate().isAfter(prevMonthEnd))
            {   System.out.println(ledgerLog.get(i).formattedTransaction()); }
        }
    }

    /** Displays a Ledger report on Transactions made from Month-To-Date. */
    private static void monthToDate() {
        LocalDate today = LocalDate.now();
        LocalDate start = today.withDayOfMonth(1);
        System.out.println("\n----- Month-To-Date -----");
        for (int i = 0; i < ledgerLog.size(); i++) {
            if(!ledgerLog.get(i).getDate().isBefore(start) && !ledgerLog.get(i).getDate().isAfter(today))
            {   System.out.println(ledgerLog.get(i).formattedTransaction()); }
        }
    }

    /** Loads transaction data into the Ledger Log */
    private static ArrayList<Transaction> getLedgerLog() {
        ArrayList<Transaction> ledger = new ArrayList<Transaction>();

        try (BufferedReader bufReader = new BufferedReader(new FileReader("transactions.csv"))) {
            String input;
            bufReader.readLine(); // Skips Header
            while((input = bufReader.readLine()) != null)
            {
                String[] info = input.split("\\|");
                Transaction oneTransaction = new Transaction(LocalDate.parse(info[0]), LocalTime.parse(info[1]), info[2],info[3], Float.parseFloat(info[4]));
                ledger.add(oneTransaction);
            }
        } catch (IOException e) {
            System.out.println("""
                    ----- Error -----
                    File could not be found.
                    Make sure the file exists and is closed.""");
        }
        return ledger;
    }
}