package cardGames.player;

import cardGames.cards.Card;

import java.util.ArrayList;
import java.util.List;

public abstract class Hand {
    protected final List<Card> cards;
    protected int bet;

    public Hand() {
        this.cards = new ArrayList<>();
    }

    public void addCard(Card card) {
        this.cards.add(card);
    }

    public abstract void placeBet(int amount);

    public abstract int getBet();

    public abstract Card showFirstCard();

    public abstract void doubleDown();

    public abstract int getValue();

    @Override
    public String toString(){
        return String.format("%s (Value: %d)", cards, getValue());
    }
}
