# Accounting Ledger Application

## Description
A Java Command-Line Interface Application that allows users to track their financial transactions, view preset and custom ledger reports. Transactions and written to and read from a csv file named 'transactions.csv'.

## Technologies Used
- Java 17+
- IntelliJ IDEA

## Features
- Add deposits and payments
- View all transactions, view all deposits only, view all payments only.
- View current account balance on home screen
- Generate Ledger Reports:
  - Month-To-Date
  - Previous Month
  - Year-To-Date
  - Previous Year
  - Search by Vendor
  - Custom Search with multiple search filters
- Transactions loaded from and saved to 'transactions.csv' file.

## How To Run
1. Clone the repository:
    - Open Command-line Interface (Terminal/ Git Bash).
    - cd/mkdir to desired directory.
    - Run: git clone https://github.com/yu26s9-Tushar673/accounting-ledger-app.git
   
2. Open the project in IntelliJ IDEA.
3. Run 'AccountingLedgerApp.java' found in: <img width="322" height="170" alt="image" src="https://github.com/user-attachments/assets/553b3c68-654c-441f-855d-c2502f4ad4c8" />

## How To Use
1. On the **Home Screen**
   - Add a Deposit or Payment.
   - View the Ledger Screen.
   - Exit the application.

     <img width="203" height="128" alt="image" src="https://github.com/user-attachments/assets/94bc0b4b-7fca-46dd-958e-6a4036b8fa3f" />

2. On the **Ledger Screen**
   - View All transactions, view all deposits, or view all payments.
   - View the Reports Screen.
   - Return to the Home Screen.

     <img width="204" height="125" alt="image" src="https://github.com/user-attachments/assets/f99b926b-ecd2-4a5a-adf9-2a0c70e30e60" />

3. On the **Reports Screen**
   - Choose a preset report, or a **Custom Search Report** to filter the report by date, vendor, description, and amount range.
   - Return to the Ledger Screen.
  
     <img width="190" height="154" alt="image" src="https://github.com/user-attachments/assets/6f299529-2352-4949-9f0a-5f99ce39c080" />

## Future Improvements
 - Add a 'Account' feature.
    - When the program starts, prompt user to enter username and password, use the input to either create a new file to write/read from or and open existing file which represents individualized accounts.
 - Add a GUI
