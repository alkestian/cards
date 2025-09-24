package cardGames.player;

public class Chips {
    private int total;

    public Chips(int startingTotal) {
        this.total = startingTotal;
    }

    public void add(int amount) {
        if (amount > 0) {
            this.total += amount;
        }
    }

    public boolean remove(int amount) {
        if (amount <= 0 || amount > this.total) {
            return false;
        }
        this.total -= amount;
        return true;
    }

    public int getTotal() {
        return total;
    }
}