package casinoo.game.cards;

public enum CardSuit {
    CLUBS("C"),
    DIAMONDS("D"),
    HEARTS("H"),
    SPADES("S");

    private final String code;

    CardSuit(String code) {
        this.code = code;
    }

    public String code() {
        return code;
    }
}
