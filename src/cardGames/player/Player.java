package cardGames.player;

public class Player {
    public final String name;
    private final Chips chips;
    private Hand hand;

    public Player(String name, int chips) {
        this.name = name;
        this.chips = new Chips(chips);
    }

    public Player(int playerNumber, int chips) {
        this.chips = new Chips(chips);
        this.name = "Player " + playerNumber;
    }

    public void setHand(Hand hand) {
        this.hand = hand;
    }

    public Hand getHand() {
        return this.hand;
    }

    public int getTotalChips() {
        return this.chips.getTotal();
    }

    @Override
    public String toString(){
        return String.format("%s has %d chips.\n", name, chips.getTotal());
    }

}


