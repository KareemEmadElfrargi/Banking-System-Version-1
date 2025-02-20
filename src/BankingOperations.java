import DB.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class BankingOperations implements _BankingOperation {

    private Connection connect() throws SQLException {
        return DatabaseConnection.getConnection();
    }
    @Override
    public void transfer(Scanner scanner, String accountId) {
        {
            try (Connection connection = connect()) {
                System.out.println("Enter the account number to transfer:");
                String toAccount = scanner.nextLine();
                System.out.println("Enter the amount to transfer:");
                double amount = scanner.nextDouble();

                String sqlStatement = "UPDATE users SET balance = balance - ? WHERE user_name = ?";
                PreparedStatement statement = connection.prepareStatement(sqlStatement);
                statement.setDouble(1, amount);
                statement.setString(2, accountId);
                int executeUpdate = statement.executeUpdate();
                String sqlStatement2 = "UPDATE users SET balance = balance + ? WHERE user_name = ?";
                PreparedStatement statement2 = connection.prepareStatement(sqlStatement2);
                statement2.setDouble(1, amount);
                statement2.setString(2, toAccount);
                int executeUpdate2 = statement2.executeUpdate();
                if (executeUpdate > 0 && executeUpdate2 > 0) {
                    System.out.println("You transferred [ " + amount + " ] successfully to account number: " + toAccount);
                } else {
                    System.out.println("Transfer failed");
                }

                System.out.println("Transfer successful");

            } catch (SQLException e) {
                System.out.println("Error connecting to database");
                e.printStackTrace();

            }
        }
    }

    @Override
    public void withdraw(Scanner scanner, String accountId) {
        try (Connection connection = connect()) {
            System.out.println("Enter the amount to withdraw:");
            double amount = scanner.nextDouble();
            String sqlStatement = "UPDATE users SET balance = balance - ? WHERE user_name = ?";
            PreparedStatement statement = connection.prepareStatement(sqlStatement);
            statement.setDouble(1, amount);
            statement.setString(2, accountId);
            int executeUpdate = statement.executeUpdate();
            if (executeUpdate > 0) {
                System.out.println("You Withdrawn [ " + amount + " ] successfully");
            } else {
                System.out.println("Withdraw failed");
            }

        } catch (SQLException e) {
            System.out.println("Error connecting to database");
            e.printStackTrace();
        }
    }
    @Override
    public void deposit(Scanner scanner, String accountId) {
        try (Connection connection = connect()) {
            System.out.println("Enter the amount to deposit:");
            double amount = scanner.nextDouble();
            String sqlStatement = "UPDATE users SET balance = balance + ? WHERE user_name = ?";
            PreparedStatement statement = connection.prepareStatement(sqlStatement);
            statement.setDouble(1, amount);
            statement.setString(2, accountId);
            int executeUpdate = statement.executeUpdate();
            if (executeUpdate > 0) {
                System.out.println("You Amounted [ " + amount + " ] successfully");
            } else {
                System.out.println("Deposit failed");
            }

        } catch (SQLException e) {
            System.out.println("Error connecting to database");
            e.printStackTrace();
        }
    }

    @Override
    public void checkBalance(String accountId) {
        try (Connection connection = connect()) {
            String sqlStatement = "SELECT balance FROM users WHERE user_name = ?";
            PreparedStatement statement = connection.prepareStatement(sqlStatement);
            statement.setString(1, accountId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                System.out.println("Your balance is: " + resultSet.getDouble("balance"));
            }

        } catch (SQLException e) {
            System.out.println("Error connecting to database");
            e.printStackTrace();
        }
    }
}
