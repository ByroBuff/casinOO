package casinoo;

public class Guest {
    private final String name;
    private double balance;

    public Guest(String name, double initialBalance) {
        this.name = name;
        this.balance = initialBalance;
    }

    public String getName() {
        return name;
    }

    public double getBalance() {
        return balance;
    }

    public void addBalance(double amount) {
        balance += amount;
    }

    public void subtractBalance(double amount) {
        balance -= amount;
    }

    public boolean canBet(double amount) {
        return balance >= amount;
    }
}
