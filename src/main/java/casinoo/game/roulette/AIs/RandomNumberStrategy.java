package casinoo.game.roulette.AIs;

import casinoo.Player;
import casinoo.game.roulette.Roulette;

import java.util.Random;

public class RandomNumberStrategy implements RouletteStrategy {
    private static final double BET_PERCENTAGE = 0.05;
    private final Random random = new Random();

    @Override
    public void playTurn(Roulette game, Player player) {
        int chips = player.getChipValue();
        if (chips <= 0) return;

        int stake = Math.max(1, (int) Math.floor(chips * BET_PERCENTAGE));
        int number = random.nextInt(37);
        game.placeNumberBet(player, number, stake);
    }
}