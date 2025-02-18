import java.sql.*;
import java.util.Scanner;

public class BankingSystem {
    private static final String URL = "jdbc:mysql://localhost:3306/banking_system";
    private static final String USER = "root";
    private static final String PASSWORD = "";


    private static Connection connect() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("1. Create Account\n2. Login\n3. Exit");
            System.out.println("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine();
            switch (choice) {
                case 1:
                    createAccount(scanner);
                    break;

                case 2:
                    login(scanner);
                    break;

                case 3:
                    System.out.println("Exiting...");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid choice");

            }
        }
    }

    private static void createAccount(Scanner scanner) {
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

    private static void login(Scanner scanner) {
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

    private static void userMenu(Scanner scanner, String accountId) {
        while (true) {
            System.out.println("1. Check Balance\n2. Deposit\n3. Withdraw\n4. Transfer\n5. Logout");
            System.out.println("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    checkBalance(accountId);
                    break;

                case 2:
                    deposit(scanner, accountId);
                    break;

                case 3:
                    withdraw(scanner, accountId);
                    break;

                case 4:
                    transfer(scanner, accountId);
                    break;

                case 5:
                    System.out.println("Logging out...");
                    return;
                default:
                    System.out.println("Invalid choice");
            }
        }


    }

    private static void transfer(Scanner scanner, String accountId) {
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

    private static void withdraw(Scanner scanner, String accountId) {
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

    private static void deposit(Scanner scanner, String accountId) {
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

    private static void checkBalance(String accountId) {
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






