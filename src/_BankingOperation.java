import java.util.Scanner;

interface _BankingOperation {
    void transfer(Scanner scanner, String accountId);
    void withdraw(Scanner scanner, String accountId);
    void deposit(Scanner scanner, String accountId);
    void checkBalance(String accountId);
}

