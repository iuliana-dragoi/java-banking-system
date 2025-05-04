package main.java.Operations;

import main.java.Bank.Account;

import java.math.BigDecimal;

public class TransferTransaction implements Transaction {

    private final Account from;

    private final Account to;

    private final BigDecimal amount;

    public TransferTransaction(Account from, Account to, BigDecimal amount) {
        this.from = from;
        this.to = to;
        this.amount = amount;
    }

    @Override
    public void process() {
        Account first = from.getAccountNumber() < to.getAccountNumber() ? from : to;
        Account second = to.getAccountNumber() < from.getAccountNumber() ? to : from;

        first.lock();
        second.lock();
        try {
            if(first.getBalance().compareTo(amount) >= 0) {
                first.setBalance(first.getBalance().subtract(amount));
                second.setBalance(to.getBalance().add(amount));
                if(debug) System.out.println("Transferred " + amount.toPlainString() + " from Account " + first.getAccountNumber() + " to Account " + second.getAccountNumber());
            }
            else {
                if(debug) System.out.println("Insufficient funds for transfer from Account " + first.getAccountNumber());
            }
        }
        finally {
            second.unlock();
            first.unlock();
        }
    }
}
