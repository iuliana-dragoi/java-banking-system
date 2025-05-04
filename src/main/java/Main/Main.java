package main.java.Main;

import main.java.Bank.Account;
import main.java.Bank.Bank;
import main.java.Operations.Transaction;

import java.math.BigDecimal;

public class Main {

    public static void main(String[] args) {

        Bank bank = new Bank(4);

        Account account1 = bank.createAccount(new BigDecimal("500"));
        Account account2 = bank.createAccount(new BigDecimal("500"));

        Transaction deposit = bank.deposit(account1, new BigDecimal("500"));
        account1.submitTransaction(deposit);

        Transaction transfer = bank.transfer(account1, account2, new BigDecimal("500"));
        account1.submitTransaction(transfer);

        Transaction withdraw = bank.withdraw(account1, new BigDecimal("1000"));
        account1.submitTransaction(withdraw);

        bank.executorShutdown();

        System.out.println();
        System.out.println(account1.toString());
        System.out.println(account2.toString());
    }
}
