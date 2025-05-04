package main.java.Main;

import main.java.Bank.Account;
import main.java.Bank.Bank;
import main.java.Operations.DepositTransaction;
import main.java.Operations.Transaction;
import main.java.Operations.WithdrawTransaction;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Main3 {

    public static void main(String[] args) {
        long startTime = System.nanoTime();

        run();

        long endTime = System.nanoTime();

        long durationInNano = endTime - startTime;
        double durationInMillis = durationInNano / 1_000_000.0;
        double durationInSeconds = durationInNano / 1_000_000_000.0;

        System.out.println("Total execution time: " + durationInSeconds + " s");
    }

    public static void run() {
        Bank bank = new Bank(2);
        // 10 transactions per account
        // 2 -> 4.9157714 s
        // 4 -> 5.3486423 s
        // 8 -> 5.9408153 s
        // 16 -> 6.8732655 s

        // 100 transactions per account
        // 2 -> 32.0705634 s
        // 4 -> 30.8661729 s
        // 16 -> 31.2918587 s
        // 32 -> 31.5351625 s

        int numberOfAccounts = 1_000_000;
        int transactionsPerAccount = 10;

        List<Account> accounts = new ArrayList<>();
        // Step 1: Create 100 accounts
        for (int i = 0; i < numberOfAccounts; i++) {
            Account account = bank.createAccount(new BigDecimal("1000")); // initial balance
            accounts.add(account);
        }

        // Step 2: Submit 10 transactions per account
        for (Account account : accounts) {
            for (int t = 0; t < transactionsPerAccount; t++) {
                BigDecimal amount = BigDecimal.valueOf((int) (Math.random() * 500 + 1)); // random 1–500
                Transaction tx;
                if (Math.random() < 0.5) {
                    tx = new DepositTransaction(account, amount);
                } else {
                    tx = new WithdrawTransaction(account, amount);
                }
                account.submitTransaction(tx);
            }
        }

        bank.executorShutdown();
    }
}
