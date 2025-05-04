package main.java.Main;

import main.java.Bank.Account;
import main.java.Bank.Bank;
import main.java.Operations.Transaction;
import main.java.Operations.TransferTransaction;

import java.math.BigDecimal;

public class Main2 {

    public static void main(String[] args) {
        Bank bank = new Bank(32);
        Account account1 = bank.createAccount(new BigDecimal("100"));
        Account account2 = bank.createAccount(new BigDecimal("520"));

        // Sent sequential transaction per account, but concurrent between accounts.
        account1.submitTransaction(bank.deposit(account1, new BigDecimal("50")));
        account1.submitTransaction(bank.withdraw(account1, new BigDecimal("58")));
        account1.submitTransaction(bank.deposit(account1, new BigDecimal("50")));
        account1.submitTransaction(bank.deposit(account1, new BigDecimal("100")));
        account1.submitTransaction(bank.deposit(account1, new BigDecimal("150")));
        account1.submitTransaction(bank.deposit(account1, new BigDecimal("250")));
        account1.submitTransaction(bank.deposit(account1, new BigDecimal("350")));
        account1.submitTransaction(bank.deposit(account1, new BigDecimal("650")));
        account1.submitTransaction(bank.deposit(account1, new BigDecimal("850")));
        account1.submitTransaction(bank.deposit(account1, new BigDecimal("900")));
        account1.submitTransaction(bank.deposit(account1, new BigDecimal("1000")));
        account1.submitTransaction(bank.withdraw(account1, new BigDecimal("1050")));

        account2.submitTransaction(bank.deposit(account2, new BigDecimal("900")));
        account2.submitTransaction(bank.deposit(account2, new BigDecimal("50")));
        account2.submitTransaction(bank.withdraw(account2, new BigDecimal("100050")));

        Transaction transfer = new TransferTransaction(account1, account2, new BigDecimal("100"));
        account1.submitTransaction(transfer);

        bank.executorShutdown();
    }
}
