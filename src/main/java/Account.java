public class Account {

    private String cardNumber = "";
    private String pinNumber = "";
    private int balance = 0;

    public Account(String cardNumber, String pinNumber, int balance) {
        this.cardNumber = cardNumber;
        this.pinNumber = pinNumber;
        this.balance = balance;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public String getPinNumber() {
        return pinNumber;
    }

    public int getBalance() {
        return balance;
    }

    public void addMoney(int money) {
        balance += money;
    }

    public void removeMoney(int money) {
        if (money <= balance) {
            balance -= money;
        }
    }
}