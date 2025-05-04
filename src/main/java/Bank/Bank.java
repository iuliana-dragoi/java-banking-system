package main.java.Bank;

import java.util.List;
import java.util.UUID;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import main.java.Operations.DepositTransaction;
import main.java.Operations.Transaction;
import main.java.Operations.TransferTransaction;
import main.java.Operations.WithdrawTransaction;

public class Bank {

    private List<Account> accountList = new ArrayList<>();

    private final ExecutorService executorService;

    public Bank(int threads) {
        executorService = Executors.newFixedThreadPool(threads);
    }

    public ExecutorService getExecutorService() {
        return executorService;
    }

    public Account createAccount(BigDecimal balance) {
        long accountNumber = UUID.randomUUID().getMostSignificantBits() & Long.MAX_VALUE;
        Account account = new Account(accountNumber, balance);
        account.setExecutorService(executorService);
        addAccount(account);
        return account;
    }

    private void addAccount(Account account) {
        this.accountList.add(account);
    }

    public Transaction deposit(Account account, BigDecimal amount) {
        return new DepositTransaction(account, amount);
    }

    public Transaction withdraw(Account account, BigDecimal amount) {
        return new WithdrawTransaction(account, amount);
    }

    public Transaction transfer(Account from, Account to, BigDecimal amount) {
        return new TransferTransaction(from, to, amount);
    }

    public void executorShutdown() {
        executorService.shutdown(); // Do not accept new tasks.
        try {
            // Finish what you already have.
            while (!executorService.awaitTermination(1, TimeUnit.SECONDS)) {
                System.out.println("Waiting for transactions to finish...");
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
