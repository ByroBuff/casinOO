package casinoo.game.blackjack;

import casinoo.game.cards.CardHand;
import casinoo.game.cards.CardRank;
import casinoo.game.cards.PlayingCard;

public class BlackjackHand extends CardHand {

    public int value() {
        int total = 0;
        int aceCount = 0;

        for (PlayingCard card : getCards()) {
            CardRank rank = card.rank();
            if (rank == CardRank.ACE) {
                total += 11;
                aceCount++;
            } else if (rank.strength() >= CardRank.TEN.strength()) {
                total += 10;
            } else {
                total += rank.strength();
            }
        }

        while (total > 21 && aceCount > 0) {
            total -= 10;
            aceCount--;
        }

        return total;
    }

    public boolean isBust() {
        return value() > 21;
    }

    public boolean isBlackjack() {
        return size() == 2 && value() == 21;
    }

    @Override
    public String toString() {
        return getCards() + " (value=" + value() + ")";
    }
}
