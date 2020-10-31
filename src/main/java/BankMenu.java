import java.util.Scanner;

public class BankMenu {

    private Bank bank;
    private Scanner scanner;

    public BankMenu(String databaseFileName, Scanner scanner) {
        this.bank = new Bank(new BankDataBase(databaseFileName));
        this.scanner = scanner;
    }


    public void start() {

        while (true) {

            System.out.println("1. Create an account");
            System.out.println("2. Log into account");
            System.out.println("0. Exit");
            System.out.println();

            switch (scanner.nextLine()) {

                case "1":

                    Account newAccount = bank.createAccount();
                    System.out.println("Your card has been created");
                    System.out.println("Your card number:");
                    System.out.println(newAccount.getCardNumber());
                    System.out.println("Your card PIN:");
                    System.out.println(newAccount.getPinNumber());
                    System.out.println();

                    break;
                case "2":

                    System.out.println("Enter your card number:");
                    String inputCardNumber = scanner.nextLine();
                    System.out.println("Enter your PIN:");
                    String inputPinNumber = scanner.nextLine();
                    System.out.println();

                    Account loggedAccount = bank.logIn(inputCardNumber, inputPinNumber);

                    if (loggedAccount != null) {
                        System.out.println("You have successfully logged in!");
                        System.out.println();
                        loggedMenu(loggedAccount);
                    } else {
                        System.out.println("Wrong card number or PIN!");
                        System.out.println();
                    }

                    break;
                case "0":

                    System.out.println("Bye!");
                    System.out.println();

                    System.exit(0);
                default:

                    System.out.println("Wrong input, try again");
                    System.out.println();
            }
        }
    }

    private void loggedMenu(Account loggedAccount) {

        boolean logIn = true;
        while (logIn) {

            System.out.println("1. Balance");
            System.out.println("2. Add income");
            System.out.println("3. Do transfer");
            System.out.println("4. Close account");
            System.out.println("5. Log out");
            System.out.println("0. Exit");
            System.out.println();

            switch (scanner.nextLine()) {

                case "1":

                    System.out.println("Balance: " + loggedAccount.getBalance());
                    System.out.println();
                    break;
                case "2":

                    System.out.println("Enter income");
                    int income = Integer.valueOf(scanner.nextLine());
                    bank.addBalance(loggedAccount, income);
                    System.out.println("Income was added!");
                    System.out.println();

                    break;
                case "3":

                    System.out.println("Transfer");
                    System.out.println("Enter card number:");
                    String transferCardNumber = scanner.nextLine();

                    if (transferCardNumber.equals(loggedAccount.getCardNumber())) {
                        System.out.println("You can't transfer money to the same account!");
                    } else if (bank.accountCheck(transferCardNumber)) {

                        System.out.println("Enter how much money you want to transfer:");
                        int moneyToTransfer = Integer.valueOf(scanner.nextLine());
                        if (moneyToTransfer > loggedAccount.getBalance()) {
                            System.out.println("Not enough money!");
                        } else {
                            bank.transfer(loggedAccount, transferCardNumber, moneyToTransfer);
                            System.out.println("Success!");
                        }
                    }
                    System.out.println();

                    break;
                case "4":

                    if (bank.closeAccount(loggedAccount)) {
                        System.out.println("The account has been closed!");
                    }
                    System.out.println();
                    logIn = false;

                    break;
                case "5":

                    System.out.println("You have successfully logged out!");
                    System.out.println();
                    logIn = false;

                    break;
                case "0":

                    System.out.println("Bye!");
                    System.out.println();

                    System.exit(0);
                default:

                    System.out.println("Wrong input, try again");
                    System.out.println();

            }
        }
    }
}