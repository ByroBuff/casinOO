package casinoo;

import java.util.ArrayList;
import java.util.List;

public class Roulette extends Game {

    private static final String GAME_NAME = "Roulette";
    private static final int MIN_PLAYERS = 1;
    private static final int MAX_PLAYERS = 8;
    private static final int[] RED_NUMBERS = {
            1, 3, 5, 7, 9, 12, 14, 16, 18,
            19, 21, 23, 25, 27, 30, 32, 34, 36
    };
    private final List<Bet> currentBets = new ArrayList<>();

    public Roulette() {
        super(GAME_NAME, MIN_PLAYERS, MAX_PLAYERS);
    }

    private enum Color {
        RED, BLACK, GREEN;
    }

    public enum BetType {
        RED, BLACK, NUMBER;
    }

    private record Bet(Player player, BetType type, int number, int amount) {
        public String toString() {
            String betString = "";
            if (type == BetType.NUMBER) {
                betString = Integer.toString(number);
            } else {
                betString = type.toString();
            }
            return player.getName() + " bet " + amount + " on " + betString;
        }
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

    public boolean placeBet(Player player, BetType type, int number, int amount) {
        if (!getPlayers().contains(player)) return false;
        if (!player.placeBet(amount)) return false;  // checks chips
        currentBets.add(new Bet(player, type, number, amount));
        return true;
    }

    private void resolveBets(RouletteResult result) {
        for (Bet bet : currentBets) {
            int payout = calculatePayout(bet, result);
            if (payout > 0) {
                bet.player().payout(payout);
            }
        }
    }

    private int calculatePayout(Bet bet, RouletteResult result) {
        return switch (bet.type()) {
            case RED    -> (result.color() == Color.RED)   ? bet.amount() * 2  : 0;
            case BLACK  -> (result.color() == Color.BLACK) ? bet.amount() * 2  : 0;
            case NUMBER -> (result.value() == bet.number()) ? bet.amount() * 36 : 0;
        };
    }

    @Override
    public void startGame() {
        if (currentBets.isEmpty()) {
            System.out.println("No bets placed.");
            return;
        }
        gameState = GameState.IN_PROGRESS;
        RouletteResult result = spin();
        System.out.println(result);
        resolveBets(result);
        currentBets.clear();
        gameState = GameState.READY_TO_PLAY;
    }
}
