package Lab6;

//liangyu wang
//980025288

import java.util.ArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ExecutorService;

//simulates a bank account, supports withdraw and deposit operations
class Account {
    private final Object lock = new Object();
    //account id
    int id=0;

    //account holder name
    String name="";

    //account balance
    double balance=0;

    //account constructor
    public Account(int id, String name, double balance) {
        this.id=id;
        this.name=name;

        //set initial balance
        this.balance=balance;
    }

    //deposit amount into bank account
    public synchronized void deposit(double amount) {
        synchronized (lock) {
            this.balance += amount;
            System.out.println("$" + amount + " deposited into " + name + "'s account.");
        }
    }

    //withdraw amount from bank account
    public synchronized void withdraw(double amount) {
        synchronized (lock) {
            if (amount <= 0) {
                System.out.println("Invalid withdrawal amount!");
            } else {
                if (amount <= balance) {
                    balance -= amount;
                    System.out.println("$" + amount + " withdrew from " + name + "'s account.");
                } else {
                    System.out.println(name + "'s account has Insufficient balance!");
                }
            }
        }
    }

}

//simulates an ATM transaction
class Transaction implements Runnable {

    //transaction type
    String action="";

    //transaction amount
    double amount=0;

    //transaction account
    Account account;

public Transaction(String action, double amount, Account account){
    this.action=action;
    this.amount=amount;
    this.account=account;
}

//starting the thread
public void run(){
            if (action.equalsIgnoreCase("withdraw")) {
                account.withdraw(amount);
            } else if (action.equalsIgnoreCase("deposit")) {
                account.deposit(amount);
            }
}

}

//test ATM transactions
public class AccountTest {

    public static void main(String[] args) {

        //create bank accounts
        Account a1= new Account(1, "Sumi", 56000);
        Account a2= new Account(2, "Hyon", 23000);
        Account a3= new Account(3, "Udon", 220000);

        //create ATM transactions
        Transaction t1= new Transaction("withdraw", 9000, a1);
        Transaction t2= new Transaction("withdraw", 20000, a2);
        Transaction t3= new Transaction("deposit", 18000, a3);
        Transaction t4= new Transaction("withdraw", 90000, a1);
        Transaction t5= new Transaction("deposit", 18000, a2);
        Transaction t6= new Transaction("withdraw", 230000, a3);

        //add transactions to ArrayList
        ArrayList<Transaction> transactions= new ArrayList<>();
        transactions.add(t1);
        transactions.add(t2);
        transactions.add(t3);
        transactions.add(t4);
        transactions.add(t5);
        transactions.add(t6);

        //create fixed thread pool
        ExecutorService executorService = Executors.newFixedThreadPool(6);

        //execute transactions with thread pool executor
        for(Transaction t: transactions){
            executorService.execute(t);
        }
        executorService.shutdown();
        while (!executorService.isTerminated()) {
        }
        System.out.println("\nTransaction is finished.");

    }
}
