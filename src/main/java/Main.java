import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        String databaseFileName = "";

        for (int i = 0; i < args.length; i++) {
            if ("-fileName".equals(args[i])) {
                databaseFileName = args[i + 1];
            }
        }

        BankMenu bankMenu = new BankMenu(databaseFileName, scanner);
        bankMenu.start();
    }
}