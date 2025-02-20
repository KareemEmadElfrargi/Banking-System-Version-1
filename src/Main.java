import DB.DatabaseConnection;

import java.sql.*;
import java.util.Scanner;

public class Main {


    public static void main(String[] args) {
        LoginScreen();
    }

    private static void LoginScreen() {
        AccountOperations accountOperations = new AccountOperations();

        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("1. Create Account\n2. Login\n3. Exit");
            System.out.println("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine();
            switch (choice) {
                case 1:
                    accountOperations.createAccount(scanner);
                    break;

                case 2:
                    accountOperations.loginAccount(scanner);
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

}
