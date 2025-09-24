package cardGames.blackjack;

import cardGames.cards.Card;
import cardGames.cards.Rank;
import cardGames.player.Hand;

public class BlackjackHand extends Hand {
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

    @Override
    public Card showFirstCard() {
        return cards.getFirst();
    }
}
