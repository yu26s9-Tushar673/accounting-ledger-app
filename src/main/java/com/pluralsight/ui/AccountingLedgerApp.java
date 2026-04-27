package com.pluralsight.ui;

import com.pluralsight.models.Transaction;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import static com.pluralsight.ui.Console.promptForInt;
import static com.pluralsight.ui.Console.promptForString;

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
                    addDeposit();
                    break;
                case "p":
                    makePayment();
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

    private static void addDeposit()
    {

    }

    private static void makePayment()
    {

    }

    private static void displayLedgerOptions(ArrayList<Transaction> ledger)
    {
        System.out.print("""
                    \n----- Ledger Screen -----
                    A) All - Display All Entries
                    D) Deposits - Display All Deposits Only
                    P) Payments - Display All Payments Only
                    R) Reports - Custom Display Options
                    H) Home - Go back to home page
                    -------------------------
                    """
        );
        String command = promptForString("Enter one of the given command letters: ").toLowerCase();

        switch (command)
        {
            case "a":
                for (int i = 0; i < ledger.size(); i++) {
                    Transaction t = ledger.get(i);
                    t.displayTransaction();
                }
                break;
            case "d":
                for (int i = 0; i < ledger.size(); i++) {
                    Transaction t = ledger.get(i);
                    if (t.getPrice() > 0)
                    {   t.displayTransaction();    }
                }
                break;
            case "p":
                for (int i = 0; i < ledger.size(); i++) {
                    Transaction t = ledger.get(i);
                    if (t.getPrice() < 0)
                    {   t.displayTransaction();    }
                }
                break;
            case "r":
                reportsScreen(ledger);
                break;
            case "h":
                System.out.println("Returning to Home Screen....");
                break;
        }


    }

    private static void reportsScreen(ArrayList<Transaction> ledger)
    {
        System.out.print("""
                    \n----- Report Options Screen -----
                    1) Month-To-Date Report
                    2) Previous Month Report
                    3) Year-To-Date Report
                    4) Previous Year Report
                    5) Search By Vendor
                    0) Back - Go back to ledger screen
                    ---------------------------------
                    """
        );
        int command = promptForInt("Enter command #: ");

        switch (command)
        {
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
                System.out.println("Returning to Ledger Screen");
                break;
        }
    }

    private static void searchByVendor(ArrayList<Transaction> ledger)
    {
        String vendor = promptForString("Enter Vendor : ");

        for (int i = 0; i < ledger.size(); i++) {
            Transaction t = ledger.get(i);
            if (t.getVendor().toLowerCase().contains(vendor.toLowerCase()))
            {   t.displayTransaction(); }
        }
    }

    private static void previousYear(ArrayList<Transaction> ledger)
    {
        LocalDate today = LocalDate.now();
        int prevYear = today.getYear() - 1;

        for (int i = 0; i < ledger.size(); i++) {
            Transaction t = ledger.get(i);
            if (t.getDate().getYear() == prevYear)
            {   t.displayTransaction(); }
        }
    }

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
