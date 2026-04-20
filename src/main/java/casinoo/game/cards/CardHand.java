package casinoo.game.cards;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CardHand {
    private final List<PlayingCard> cards = new ArrayList<>();

    public void addCard(PlayingCard card) {
        cards.add(card);
    }

    public List<PlayingCard> getCards() {
        return List.copyOf(cards);
    }

    public Optional<PlayingCard> firstCard() {
        if (cards.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(cards.get(0));
    }

    public int size() {
        return cards.size();
    }

    @Override
    public String toString() {
        return cards.toString();
    }
}
