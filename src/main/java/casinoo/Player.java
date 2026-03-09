package casinoo;

public class Player {
    private final String name;
    private final BankAccount bankAccount;
    private int chipValue;

    public Player(String name, int initialBalance) {
        this.name = name;
        this.bankAccount = new BankAccount(initialBalance);
    }

    public String toString() {
        return "Player(name=" + getName() + ", chips=" + getChipValue() + ")";
    }

    public String getName() {
        return name;
    }

    public int getChipValue() {
        return chipValue;
    }

    public boolean placeBet(int amount) {
        if (amount <= 0 || amount > chipValue) return false;
        chipValue -= amount;
        return true;
    }

    public void payout(int amount) {
        chipValue += amount;
    }

    public boolean buyChips(int amount) {
        if (amount > this.bankAccount.getBalance() || amount < 1) {
            return false;
        }

        this.bankAccount.withdraw(amount);
        this.chipValue += amount;
        return true;
    }

    public void cashOut() {
        this.bankAccount.deposit(this.chipValue);
        this.chipValue = 0;
    }
}
