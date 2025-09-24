package cardGames.blackjack;

import cardGames.cards.Card;
import cardGames.cards.Rank;
import cardGames.player.Hand;

public class BlackjackHand extends Hand {
    private int bet = 0;

    @Override
    public int getValue() {
        int totalValue = 0;
        int aceCount = 0;

        for (Card card : cards) {
            if (card.getRank() == Rank.ACE) {
                aceCount++;
            }
            totalValue += card.getRank().getValue();
        }

        while (totalValue > 21 &&  aceCount > 0) {
            totalValue -= 10;
            aceCount--;
        }

        return totalValue;
    }

    public void placeBet(int amount) {
        this.bet = amount;
    }

    public int getBet() {
        return this.bet;
    }

    public Card showFirstCard() {
        return cards.getFirst();
    }
}
