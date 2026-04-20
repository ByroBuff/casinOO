package casinoo.game.cards;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Random;

public class ShuffledCardShoe implements CardShoe {
    private final int deckCount;
    private final Random random;
    private final List<PlayingCard> cards = new ArrayList<>();

    public ShuffledCardShoe() {
        this(4, new Random());
    }

    public ShuffledCardShoe(int deckCount) {
        this(deckCount, new Random());
    }

    public ShuffledCardShoe(int deckCount, Random random) {
        if (deckCount <= 0) {
            throw new IllegalArgumentException("deckCount must be positive");
        }
        this.deckCount = deckCount;
        this.random = Objects.requireNonNull(random, "random cannot be null");
    }

    @Override
    public PlayingCard draw() {
        if (cards.isEmpty()) {
            refillAndShuffle();
        }
        return cards.remove(cards.size() - 1);
    }

    private void refillAndShuffle() {
        cards.clear();
        for (int i = 0; i < deckCount; i++) {
            for (CardSuit suit : CardSuit.values()) {
                for (CardRank rank : CardRank.values()) {
                    cards.add(new PlayingCard(rank, suit));
                }
            }
        }
        Collections.shuffle(cards, random);
    }
}
