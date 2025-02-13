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
        try (Connection connection = connect()){
            System.out.println("Enter your user name: ");
            String accountName = scanner.nextLine();
            System.out.println("Enter your password: ");
            String password = scanner.nextLine();
            String sqlStatement = "SELECT * FROM users WHERE user_name = ? AND password = ?";
            PreparedStatement statement = connection.prepareStatement(sqlStatement);
            statement.setString(1, accountName);
            statement.setString(2, password);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()){
                System.out.println("Login Successful ...");
                //TODO: Implement the banking operations
            }else {
                System.out.println("Invalid username or password");
            }

        }catch (SQLException e) {
            System.out.println("Error connecting to database");
            e.printStackTrace();
        }
    }
}



