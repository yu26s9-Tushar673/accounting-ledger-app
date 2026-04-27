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
                    --- Home Screen ---
                    D) Add Deposit
                    P) Make Payment (Debit)
                    L) Ledger - Ledger Display screen
                    X) Exit - exit the application
                    -------------------
                    """
            );
            String command = promptForString("Enter command letter: ").toLowerCase();

            switch (command)
            {
                case "d":
                    addDeposit();
                    break;
                case "p":
                    makePayment();
                    break;
                case "l":
                    displayLedgerOptions();
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

    private static void displayLedgerOptions()
    {


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
