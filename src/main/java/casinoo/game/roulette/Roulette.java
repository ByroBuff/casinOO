package casinoo.game.roulette;

import casinoo.Game;
import casinoo.GameState;
import casinoo.Player;
import casinoo.betting.BetManager;
import casinoo.betting.BetTicket;

public class Roulette extends Game {

    private static final int[] RED_NUMBERS = {
            1, 3, 5, 7, 9, 12, 14, 16, 18,
            19, 21, 23, 25, 27, 30, 32, 34, 36
    };
    private final BetManager<RouletteOutcome> betManager;

    public Roulette() {
        this(new BetManager<>(new RouletteBetResolver()));
    }

    public Roulette(BetManager<RouletteOutcome> betManager) {
        super("Roulette", 1, 8);
        this.betManager = betManager;
    }

    public boolean placeColorBet(Player player, String color, int stake) {
        if (!getPlayers().contains(player)) return false;
        return betManager.placeBet(BetTicket.forColor(player, stake, color));
    }

    public boolean placeNumberBet(Player player, int number, int stake) {
        if (!getPlayers().contains(player)) return false;
        if (number < 0 || number > 36) return false;
        return betManager.placeBet(BetTicket.forNumber(player, stake, number));
    }

    @Override
    public void startGame() {
        if (!betManager.hasOpenBets()) {
            System.out.println("No bets placed.");
            return;
        }

        betManager.printBets();

        gameState = GameState.IN_PROGRESS;
        RouletteOutcome outcome = spin();
        System.out.println(outcome);
        betManager.settle(outcome);
        gameState = GameState.READY_TO_PLAY;
    }

    private RouletteOutcome spin() {
        int value = (int) (Math.random() * 37);
        return RouletteOutcome.of(value, determineColor(value));
    }

    private RouletteColor determineColor(int value) {
        if (value == 0) return RouletteColor.GREEN;
        for (int red : RED_NUMBERS) {
            if (red == value) return RouletteColor.RED;
        }
        return RouletteColor.BLACK;
    }
}