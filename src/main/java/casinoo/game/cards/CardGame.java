package casinoo.game.cards;

import casinoo.Game;

import java.util.Objects;

public abstract class CardGame extends Game {
    private final CardShoe shoe;

    protected CardGame(String name, int minPlayers, int maxPlayers, CardShoe shoe) {
        super(name, minPlayers, maxPlayers);
        this.shoe = Objects.requireNonNull(shoe, "shoe cannot be null");
    }

    protected PlayingCard drawCard() {
        return shoe.draw();
    }
}
