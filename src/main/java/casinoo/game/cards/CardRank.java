package casinoo.game.cards;

public enum CardRank {
    TWO("2", 2),
    THREE("3", 3),
    FOUR("4", 4),
    FIVE("5", 5),
    SIX("6", 6),
    SEVEN("7", 7),
    EIGHT("8", 8),
    NINE("9", 9),
    TEN("10", 10),
    JACK("J", 11),
    QUEEN("Q", 12),
    KING("K", 13),
    ACE("A", 14);

    private final String label;
    private final int strength;

    CardRank(String label, int strength) {
        this.label = label;
        this.strength = strength;
    }

    public String label() {
        return label;
    }

    public int strength() {
        return strength;
    }
}
