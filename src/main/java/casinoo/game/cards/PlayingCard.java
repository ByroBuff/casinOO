package casinoo.game.cards;

import java.util.Objects;

public record PlayingCard(CardRank rank, CardSuit suit) {
    public PlayingCard {
        Objects.requireNonNull(rank, "rank cannot be null");
        Objects.requireNonNull(suit, "suit cannot be null");
    }

    @Override
    public String toString() {
        return rank.label() + suit.code();
    }
}
