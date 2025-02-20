import DB.DatabaseConnection;

import java.sql.*;
import java.util.Scanner;

public class AccountOperations implements _AccountOperations{
    static BankingOperations bankingOperations = new BankingOperations();
    private Connection connect() throws SQLException {
        return DatabaseConnection.getConnection();
    }
    private static void userMenu(Scanner scanner, String accountId) {
        while (true) {
            System.out.println("1. Check Balance\n2. Deposit\n3. Withdraw\n4. Transfer\n5. Logout");
            System.out.println("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    bankingOperations.checkBalance(accountId);
                    break;

                case 2:
                    bankingOperations.deposit(scanner, accountId);
                    break;

                case 3:
                    bankingOperations.withdraw(scanner, accountId);
                    break;

                case 4:
                    bankingOperations.transfer(scanner, accountId);
                    break;

                case 5:
                    System.out.println("Logging out...");
                    return;
                default:
                    System.out.println("Invalid choice");
            }
        }


    }
    @Override
    public void createAccount(Scanner scanner) {
        {
            try (Connection connection = connect()) {
                System.out.println("Enter your user name: ");
                String name = scanner.nextLine();
                System.out.println("Enter your password: ");
                String password = scanner.nextLine();

                String sqlStatement = "INSERT INTO users (user_name,password,balance) VALUES (?,?,0.0)";
                PreparedStatement statement = connection.prepareStatement(sqlStatement, Statement.RETURN_GENERATED_KEYS);
                statement.setString(1, name);
                statement.setString(2, password);
                int affectedRow = statement.executeUpdate();

                if (affectedRow == 0) {
                    System.out.println("Account creation failed");
                    return;
                }
                ResultSet keys = statement.getGeneratedKeys();
                if (keys.next()) {
                    System.out.println("Account created successfully. Your account number is: " + keys.getInt(1));
                }

            } catch (SQLException e) {
                System.out.println("Error connecting to database");
                e.printStackTrace();
            }
        }
    }

    @Override
    public void loginAccount(Scanner scanner) {
        try (Connection connection = connect()) {
            System.out.println("Enter your user name: ");
            String accountId = scanner.nextLine();
            System.out.println("Enter your password: ");
            String password = scanner.nextLine();
            String sqlStatement = "SELECT * FROM users WHERE user_name = ? AND password = ?";
            PreparedStatement statement = connection.prepareStatement(sqlStatement);
            statement.setString(1, accountId);
            statement.setString(2, password);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                System.out.println("Login Successful ...");
                userMenu(scanner, accountId);
            } else {
                System.out.println("Invalid username or password");
            }

        } catch (SQLException e) {
            System.out.println("Error connecting to database");
            e.printStackTrace();
        }
    }
}
