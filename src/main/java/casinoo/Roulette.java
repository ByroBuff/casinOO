package casinoo;

public class Roulette extends Game {

    private static final String GAME_NAME = "Roulette";
    private static final int MIN_PLAYERS = 1;
    private static final int MAX_PLAYERS = 8;
    private static final int[] RED_NUMBERS = {
            1, 3, 5, 7, 9, 12, 14, 16, 18,
            19, 21, 23, 25, 27, 30, 32, 34, 36
    };

    public Roulette() {
        super(GAME_NAME, MIN_PLAYERS, MAX_PLAYERS);
    }

    private enum Color {
        RED, BLACK, GREEN;
    }

    private record RouletteResult(int value, Color color) {

        public String toString() {
            return "RouletteResult(value=" + value + ", color=" + color + ")";
        }
    }

    private RouletteResult spin() {
        int value = (int) (Math.random() * 37); // 0-36
        Color color = determineColor(value);
        return new RouletteResult(value, color);
    }

    private Color determineColor(int value) {
        if (value == 0) {
            return Color.GREEN;
        }
        for (int red : RED_NUMBERS) {
            if (red == value) {
                return Color.RED;
            }
        }
        return Color.BLACK;
    }

    @Override
    public void startGame() {
        gameState = GameState.IN_PROGRESS;
        RouletteResult result = spin();
        System.out.println(result);
        gameState = GameState.READY_TO_PLAY;
    }
}
