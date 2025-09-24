package cardGames.cards;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Deck {
    private final List<Card> cards;

    public Deck() {
        cards = new ArrayList<>();
        for (Rank rank : Rank.values()) {
            for (Suit suit : Suit.values()) {
                cards.add(new Card(rank, suit));
            }
        }
    }

    public void shuffle() {
        Collections.shuffle(cards);
    }

    public void refillDeck(List<Card> dealtCards) {
        cards.addAll(dealtCards);
    }

    public Card deal(){
        if (cards.isEmpty()) {
            return null;
        }
        return cards.removeFirst();
    }

    public int size(){
        return cards.size();
    }
}
