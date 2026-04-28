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
    static void main() throws IOException {
        ArrayList<Transaction> ledgerLog  = getLedgerLog();
        Collections.sort(ledgerLog, Comparator.comparing(Transaction::getDate).thenComparing(Transaction::getTime).reversed());

        do {
            System.out.print("""
                    \n
                    ------ Home Screen ------
                    D) Add Deposit
                    P) Make Payment (Debit)
                    L) Ledger - Ledger Display screen
                    X) Exit - exit the application
                    -------------------------
                    """
            );
            String command = promptForString("Enter one of the given command letters: ").toLowerCase();
            switch (command)
            {
                case "d":
                    ledgerLog = addDeposit(ledgerLog);
                    break;
                case "p":
                    ledgerLog = makePayment(ledgerLog);
                    break;
                case "l":
                    displayLedgerOptions(ledgerLog);
                    break;
                case "x":
                    System.out.println("Thank you, Goodbye!");
                    return;
            }
        } while (true);
    }

    // Add a deposit Transaction to the ledger log
    private static ArrayList<Transaction> addDeposit(ArrayList<Transaction> ledger) throws IOException {
        String description = promptForString("Enter deposit Description: ");
        String vendor = promptForString("Enter deposit Vendor: ");
        float amount = promptForFloat("Enter deposit Amount: ");
        if (amount <= 0) {
            while (amount <= 0)
            {   amount = promptForFloat("Deposit must be positive! Enter a positive value: ");  }
        }

        LocalDate date = LocalDate.now();
        LocalTime time = LocalTime.now().withNano(0);

        BufferedWriter bufWriter = new BufferedWriter(new FileWriter("transactions.csv", true));
        bufWriter.write(date + "|" + time + "|" + description + "|" + vendor + "|" + amount);
        bufWriter.newLine();
        bufWriter.close();

        ledger.add(new Transaction(date, time, description, vendor, amount));
        Collections.sort(ledger, Comparator.comparing(Transaction::getDate).thenComparing(Transaction::getTime).reversed());

        return ledger;

    }

    // Add a payment Transaction to the ledger log
    private static ArrayList<Transaction> makePayment(ArrayList<Transaction> ledger) throws IOException {
        String description = promptForString("Enter payment Description: ");
        String vendor = promptForString("Enter payment Vendor: ");
        float amount = promptForFloat("Enter payment Amount: ");
        if (amount >= 0) {
            while (amount >= 0)
            {   amount = promptForFloat("Payment must be negative! Enter a negative value: ");  }
        }

        LocalDate date = LocalDate.now();
        LocalTime time = LocalTime.now().withNano(0);

        BufferedWriter bufWriter = new BufferedWriter(new FileWriter("transactions.csv", true));
        bufWriter.write(date + "|" + time + "|" + description + "|" + vendor + "|" + amount);
        bufWriter.newLine();
        bufWriter.close();

        ledger.add(new Transaction(date, time, description, vendor, amount));
        Collections.sort(ledger, Comparator.comparing(Transaction::getDate).thenComparing(Transaction::getTime).reversed());

        return ledger;
    }

    // Displays ledger screen options.
    private static void displayLedgerOptions(ArrayList<Transaction> ledger)
    {
        String command = "";
        while (!command.equalsIgnoreCase("H")) {
            System.out.print("""
                    \n
                    ----- Ledger Screen -----
                    A) All - Display All Entries
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
                    displayAllTrans(ledger);
                    break;
                case "d":
                    displayDeposits(ledger);
                    break;
                case "p":
                    displayPayments(ledger);
                    break;
                case "r":
                    reportsScreen(ledger);
                    break;
                case "h":
                    System.out.println("Returning to Home Screen....");
                    break;
                default:
                    System.out.println("Invalid command! Please choose from the given command letters: ");
            }
        }
    }

    // Displays all transactions through the Ledger Screen.
    private static void displayAllTrans(ArrayList<Transaction> ledger)
    {
        System.out.println("\n--------------- All Transactions ---------------");
        for (int i = 0; i < ledger.size(); i++) {
            Transaction t = ledger.get(i);
            t.displayTransaction();
        }
    }

    // Displays all deposits through the Ledger Screen.
    private static void displayDeposits(ArrayList<Transaction> ledger)
    {
        System.out.println("\n----------------- All Deposits -----------------");
        for (int i = 0; i < ledger.size(); i++) {
            Transaction t = ledger.get(i);
            if (t.getPrice() > 0)
            {   t.displayTransaction(); }
        }
    }

    // Displays all payments through the Ledger Screen.
    private static void displayPayments(ArrayList<Transaction> ledger)
    {
        System.out.println("\n----------------- All Payments -----------------");
        for (int i = 0; i < ledger.size(); i++) {
            Transaction t = ledger.get(i);
            if (t.getPrice() < 0)
            {   t.displayTransaction(); }
        }
    }

    // Displays Report Screen and options.
    private static void reportsScreen(ArrayList<Transaction> ledger)
    {
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
                    0) Back - Go back to ledger screen
                    ---------------------------------
                    """
            );
            command = promptForInt("Enter command #: ");
            switch (command) {
                case 1:
                    monthToDate(ledger);
                    break;
                case 2:
                    previousMonth(ledger);
                    break;
                case 3:
                    yearToDate(ledger);
                    break;
                case 4:
                    previousYear(ledger);
                    break;
                case 5:
                    searchByVendor(ledger);
                    break;
                case 0:
                    System.out.println("Returning to Ledger Screen....");
                    break;
                default:
                    System.out.println("Invalid command! Please choose from the given command numbers: ");
            }
        }
    }

    // Allows user to receive a custom Ledger report based on specified vendor search.
    private static void searchByVendor(ArrayList<Transaction> ledger)
    {
        String vendor = promptForString("Enter Vendor : ");
        for (int i = 0; i < ledger.size(); i++) {
            Transaction t = ledger.get(i);
            if (t.getVendor().toLowerCase().contains(vendor.toLowerCase()))
            {   t.displayTransaction(); }
        }
    }

    // Displays a Ledger report on Transactions made in the previous year.
    private static void previousYear(ArrayList<Transaction> ledger)
    {
        LocalDate today = LocalDate.now();
        int prevYear = today.getYear() - 1;
        System.out.println("\n---- Previous Year ----");
        for (int i = 0; i < ledger.size(); i++) {
            Transaction t = ledger.get(i);
            if (t.getDate().getYear() == prevYear)
            {   t.displayTransaction(); }
        }
    }

    // Displays a Ledger report on Transactions made from Year-To-Date.
    private static void yearToDate(ArrayList<Transaction> ledger)
    {
        LocalDate today = LocalDate.now();
        LocalDate ytdStart = today.withDayOfYear(1);
        System.out.println("\n----- Year-To-Date -----");
        for (int i = 0; i < ledger.size(); i++) {
            Transaction t = ledger.get(i);
            if (!t.getDate().isBefore(ytdStart) && !t.getDate().isAfter(today))
            {   t.displayTransaction(); }
        }
    }

    // Displays a Ledger report on Transactions made in the previous month.
    private static void previousMonth(ArrayList<Transaction> ledger)
    {
        LocalDate firstOfMonth = LocalDate.now().withDayOfMonth(1);
        LocalDate prevMonthStart = firstOfMonth.minusMonths(1);
        LocalDate prevMonthEnd = firstOfMonth.minusDays(1);
        System.out.println("\n---- Previous Month ----");
        for (int i = 0; i < ledger.size(); i++) {
            Transaction t = ledger.get(i);
            if(!t.getDate().isBefore(prevMonthStart) && !t.getDate().isAfter(prevMonthEnd))
            {   t.displayTransaction(); }
        }
    }

    // Displays a Ledger report on Transactions made from Month-To-Date.
    private static void monthToDate(ArrayList<Transaction> ledger)
    {
        LocalDate today = LocalDate.now();
        LocalDate start = today.withDayOfMonth(1);
        System.out.println("\n----- Month-To-Date -----");
        for (int i = 0; i < ledger.size(); i++) {
            Transaction t = ledger.get(i);
            if(!t.getDate().isBefore(start) && !t.getDate().isAfter(today))
            {   t.displayTransaction(); }
        }
    }

    // Loads transaction data into the Ledger Log
    private static ArrayList<Transaction> getLedgerLog() throws IOException
    {
        ArrayList<Transaction> ledger = new ArrayList<Transaction>();
        BufferedReader bufReader = new BufferedReader(new FileReader("transactions.csv"));
        String input;
        bufReader.readLine(); // Skips Header
        while((input = bufReader.readLine()) != null)
        {
            String[] info = input.split("\\|");
            Transaction oneTransaction = new Transaction(LocalDate.parse(info[0]), LocalTime.parse(info[1]), info[2],info[3], Float.parseFloat(info[4]));
            ledger.add(oneTransaction);
        }
        bufReader.close();
        return ledger;
    }
}