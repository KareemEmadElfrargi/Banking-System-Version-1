import java.util.Scanner;

public interface BankOperations {
    void deposit(Scanner scanner, String userName);
    void withdraw(Scanner scanner, String userName);
    void checkBalance(String userName);
    void transfer(Scanner scanner, String userName);
}
