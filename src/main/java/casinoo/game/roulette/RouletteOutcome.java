package casinoo.game.roulette;

public record RouletteOutcome(int value, RouletteColor color) {
    public static RouletteOutcome of(int value, RouletteColor color) {
        return new RouletteOutcome(value, color);
    }

    @Override
    public String toString() {
        return "RouletteOutcome(value=" + value + ", color=" + color + ")";
    }
}