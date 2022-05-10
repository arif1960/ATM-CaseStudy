
package atm.system;


public class Atm {
    private boolean userAuthenticated;
    private int currentAccountNumber;
    private Screen screen;
    private Keypad keypad;
    private CashDispenser cashDispenser;
    private DepositSlot depositSlot;
    private BankDatabase bankDatabase;
    
    private static final int BalanceInquiry =1;
    private static final int Withdrwal = 2;
    private static final int Deposit = 3;
    private static final int Exit = 4;
    
    public Atm (){
        userAuthenticated  = false;
        currentAccountNumber = 0;
        screen = new Screen();
        keypad = new Keypad();
        cashDispenser = new CashDispenser();
        depositSlot = new DepositSlot();
        bankDatabase = new BankDatabase();
        
    }
    
    //Start ATM
    public void run (){
        while (true){
            while (!userAuthenticated){
                screen.displayMessageLine("\nWelcome");
                authenticateUser();
            }
            performTransactions();
            userAuthenticated = false;
            currentAccountNumber = 0;
            screen.displayMessageLine("\nThank You! Goodbye!");
        }
    }
    private void authenticateUser(){
        screen.displayMessage("\nPlease enter your account number: ");
        int accountNumber = keypad.getInput();
        screen.displayMessage("\n Enter your PIN: ");
        int pin = keypad.getInput();
        userAuthenticated = bankDatabase.authenticateUser(accountNumber, pin);
        
        
        if (userAuthenticated){
            currentAccountNumber = accountNumber;
        }
        else{
            screen.displayMessageLine("Invalid account number or PIN. Please try again");
            
        }
        
    }
        private void performTransactions(){
            Transaction currentTransaction = null;
            boolean userExited = false;
            
            while (!userExited){
                int mainMenuSelection = displayMainMenu();
                
                switch (mainMenuSelection){
                    case 1: 
                    case 2:
                    case 3:
                    
                        currentTransaction = createTransaction(mainMenuSelection);
                        currentTransaction.execute();
                        break;
                    case Exit:
                        screen.displayMessageLine("Exiting the System");
                        userExited = true;
                        break;
                    default:
                        screen.displayMessageLine("You did not enter a valid selection. Try again.");
                        break;
                }
            }
        }
        
        private int displayMainMenu(){
            screen.displayMessageLine( "\nMain menu:" );
            screen.displayMessageLine( "1 - View my balance" );
            screen.displayMessageLine( "2 - Withdraw cash" );
            screen.displayMessageLine( "3 - Deposit funds" );
            screen.displayMessageLine( "4 - Exit\n" );
            screen.displayMessage( "Enter a choice: " );
            return keypad.getInput();
        }
        private Transaction createTransaction(int type){
            Transaction temp = null;
            switch (type)
            {
                case 1:
                    temp = new BalanceInquiry(currentAccountNumber, screen, bankDatabase);
                    break;
                case 2:
                    temp = new Withdrawal (currentAccountNumber, screen, bankDatabase, keypad, cashDispenser);
                    break;
                case 3:
                    temp = new Deposit(currentAccountNumber, screen, bankDatabase, keypad, depositSlot);
                    break;
            }
            return temp;
        }
}
