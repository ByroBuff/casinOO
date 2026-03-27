package casinoo.game.roulette.AIs;

import casinoo.Player;
import casinoo.game.roulette.Roulette;

import java.util.Random;

public class RandomThirdStrategy implements RouletteStrategy {
    private static final double BET_PERCENTAGE = 0.05;
    private final Random random = new Random();

    @Override
    public void playTurn(Roulette game, Player player) {
        int chips = player.getChipValue();
        if (chips <= 0) return;

        int stake = Math.max(1, (int) Math.floor(chips * BET_PERCENTAGE)) / 12;
        int number = random.nextInt(3);
        int max = 36 - (number * 12);
        int min = max - 12;
        for (int i = 0; i < 12; i++) {
            int betNumber = min + i;
            if (betNumber > 36) break;
            game.placeNumberBet(player, betNumber, stake);
        }
    }
}