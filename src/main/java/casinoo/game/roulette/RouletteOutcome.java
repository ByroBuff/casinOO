package casinoo.game.roulette;

public record RouletteOutcome(int value, RouletteColor color) {
    @Override
    public String toString() {
        return "RouletteOutcome(value=" + value + ", color=" + color + ")";
    }
}