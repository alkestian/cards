package cardGames.player;

public class Chips {
    private int chips;

    public Chips(int chips) {
        this.chips = chips;
    }

    public boolean bet(int bet) {
        if (bet > chips) {
            return false;
        }
        if (bet <= 0) {
            return false;
        }
        chips = chips - bet;
        return true;
    }

    public void payout(int bet) {
        chips = chips + 2 * bet;
    }

    public void returnBet(int bet) {
        chips = chips + bet;
    }

    public int size(){
        return chips;
    }
}
