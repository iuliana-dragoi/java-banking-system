package main.java.Operations;

import main.java.Bank.Account;

import java.math.BigDecimal;

public class DepositTransaction implements Transaction {

    private final Account account;

    private final BigDecimal amount;

    public DepositTransaction(Account account, BigDecimal amount) {
        this.account = account;
        this.amount = amount;
    }

    @Override
    public void process() {
        BigDecimal result = account.getBalance().add(amount);
        account.setBalance(result);
        if(debug) System.out.println("Amount deposited: " + amount.toPlainString() + " in account: " + account.getAccountNumber() + ". Current balance: " + account.getBalance().toPlainString());
    }
}
