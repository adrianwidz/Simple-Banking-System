import java.util.Random;

public class Bank {

    private final Random random = new Random();
    BankDataBase bankDataBase;

    public Bank(BankDataBase bankDataBase) {
        this.bankDataBase = bankDataBase;
    }

    public Account createAccount() {

        StringBuilder builder = new StringBuilder();
        String cardNumber = "400000";
        String accountNumber = "";
        int checksum;

        builder.append(accountNumber);
        while (builder.length() < 9) {
            builder.append(random.nextInt(10));
        }
        accountNumber = builder.toString();
        cardNumber = cardNumber + accountNumber;

        int controlNumber = 0;
        int luhnAlgorithm = 0;
        for (int i = 0; i < cardNumber.length(); i++) {
            luhnAlgorithm = Integer.valueOf(cardNumber.charAt(i) + "");
            if (i % 2 == 0) {
                luhnAlgorithm *= 2;
            }

            if (luhnAlgorithm > 9) {
                luhnAlgorithm -= 9;
            }
            controlNumber += luhnAlgorithm;
        }

        if (controlNumber % 10 == 0) {
            checksum = 0;
        } else {
            checksum = 10 - controlNumber % 10;
        }

        cardNumber = cardNumber + checksum;

        builder = new StringBuilder();
        while (builder.length() < 4) {
            builder.append(random.nextInt(10));
        }
        String pinNumber = builder.toString();

        Account account = new Account(cardNumber, pinNumber, 0);
        bankDataBase.insert(account.getCardNumber(), account.getPinNumber(), account.getBalance());
        return account;
    }

    public Account logIn(String loginCardNumber, String loginPinNumber) {

        Account loginAccount = bankDataBase.getAccount(loginCardNumber);

        if (!(loginAccount == null) && loginAccount.getPinNumber().equals(loginPinNumber)) {
            return loginAccount;
        } else {
            return null;
        }
    }

    public boolean accountCheck(String cardNumber) {

        boolean check = true;
        int controlNumber = 0;
        int luhnAlgorithm = 0;
        for (int i = 0; i < cardNumber.length() - 1; i++) {
            luhnAlgorithm = Integer.valueOf(cardNumber.charAt(i) + "");
            if (i % 2 == 0) {
                luhnAlgorithm *= 2;
            }

            if (luhnAlgorithm > 9) {
                luhnAlgorithm -= 9;
            }
            controlNumber += luhnAlgorithm;
        }
        controlNumber += Integer.valueOf(cardNumber.charAt(cardNumber.length() - 1) + "");

        if (bankDataBase.getAccount(cardNumber) == null) {

            System.out.println("Such a card does not exist.");

            check = false;
        }

        if (controlNumber % 10 != 0) {

            System.out.println("Probably you made mistake in the card number. Please try again!");

            check = false;
        }

        return check;
    }

    public boolean transfer(Account source, String targetCardNumber, int money) {

        Account target = bankDataBase.getAccount(targetCardNumber);
        if (source.getBalance() >= money) {
            source.removeMoney(money);
            target.addMoney(money);
            bankDataBase.updateAccount(source);
            bankDataBase.updateAccount(target);

            return true;
        } else {
            return false;
        }
    }

    public void addBalance(Account account, int money) {
        account.addMoney(money);
        bankDataBase.updateAccount(account);
    }

    public boolean closeAccount(Account account) {
        if (bankDataBase.deleteAccount(account) > -1) {
            return true;
        } else {
            return false;
        }
    }
}