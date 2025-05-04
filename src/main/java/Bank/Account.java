package main.java.Bank;

import main.java.Operations.Transaction;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Account {

    private final long accountNumber;

    private BigDecimal balance;

    private ExecutorService executorService;

    private final Lock lock = new ReentrantLock();

    private final BlockingQueue<Transaction> transactionQueue = new LinkedBlockingQueue<>();

    private final AtomicBoolean isProcessing = new AtomicBoolean(false);

    protected Account(long accountNumber, BigDecimal balance) {
        this.accountNumber = accountNumber;
        this.balance = balance;
    }

    public long getAccountNumber() {
        return accountNumber;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public void setExecutorService(ExecutorService executorService) {
        this.executorService = executorService;
    }

    public void lock() {
        lock.lock();
    }

    public void unlock() {
        lock.unlock();
    }

    public void submitTransaction(Transaction transaction) {
        transactionQueue.add(transaction);
        if(isProcessing.compareAndSet(false, true)) {
            executorService.submit(this::processTransactions);
        }
    }

    private void processTransactions() {
        while(true) {
            Transaction tx = transactionQueue.poll();
            if(tx == null) break;
            tx.process();
        }
        isProcessing.set(false);
        if(!transactionQueue.isEmpty() && isProcessing.compareAndSet(false, true)) {
            executorService.submit(this::processTransactions);
        }
    }

    @Override
    public String toString() {
        return "Account{" +
                "accountNumber=" + accountNumber +
                ", balance=" + balance +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Account account = (Account) o;
        return accountNumber == account.accountNumber && Objects.equals(balance, account.balance);
    }

    @Override
    public int hashCode() {
        return Objects.hash(accountNumber, balance);
    }
}
