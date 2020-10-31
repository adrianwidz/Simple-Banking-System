import org.sqlite.SQLiteDataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class BankDataBase {

    private String databaseUrl;
    private SQLiteDataSource dataSource;

    public BankDataBase(String databaseFileName) {
        this.databaseUrl = "jdbc:sqlite:" + databaseFileName;
        dataSource = new SQLiteDataSource();
        dataSource.setUrl(databaseUrl);

        try (Connection con = dataSource.getConnection()){

            try (Statement statement = con.createStatement()){

                statement.execute("CREATE TABLE IF NOT EXISTS card(" +
                        "id INTEGER PRIMARY KEY," +
                        "number TEXT," +
                        "pin TEXT," +
                        "balance INTEGER DEFAULT 0)");

            } catch (SQLException e) {
                e.printStackTrace();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void insert(String cardNumber, String pinNumber, int balance) {

        try (Connection con = dataSource.getConnection()){

            try (Statement statement = con.createStatement()){

                statement.executeUpdate("INSERT INTO card (number, pin, balance) VALUES " +
                        "('" + cardNumber + "', '" + pinNumber + "', " + balance + " );");

            } catch (SQLException e) {
                e.printStackTrace();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Account getAccount(String cardNumber) {

        Account account = null;

        try (Connection con = dataSource.getConnection()){

            try (Statement statement = con.createStatement()){

                try (ResultSet accounts = statement.executeQuery("SELECT * FROM card")) {

                    while (accounts.next()) {

                        if (cardNumber.equals(accounts.getString("number"))) {

                            account = new Account(accounts.getString("number"),
                                    accounts.getString("pin"),
                                    accounts.getInt("balance"));
                        }
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return account;
    }

    public void updateAccount(Account accountToUpdate) {

        try (Connection con = dataSource.getConnection()){

            try (Statement statement = con.createStatement()){

                statement.executeUpdate("UPDATE card SET balance = " + accountToUpdate.getBalance() +
                        " WHERE number = " + accountToUpdate.getCardNumber() +
                        " AND pin = " + accountToUpdate.getPinNumber());

            } catch (SQLException e) {
                e.printStackTrace();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public int deleteAccount(Account accountToDelete) {

        try (Connection con = dataSource.getConnection()){

            try (Statement statement = con.createStatement()){

                return statement.executeUpdate("DELETE FROM card WHERE number = " + accountToDelete.getCardNumber() +
                        " AND pin = " + accountToDelete.getPinNumber());

            } catch (SQLException e) {
                e.printStackTrace();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return -1;
    }
}