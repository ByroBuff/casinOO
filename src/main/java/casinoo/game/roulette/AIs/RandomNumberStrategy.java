package casinoo.game.roulette.AIs;

import casinoo.Player;
import casinoo.game.roulette.Roulette;

import java.util.Random;

public class RandomNumberStrategy implements RouletteStrategy {
    private final Random random = new Random();
    private final int stakePerBet;

    public RandomNumberStrategy(int stakePerBet) {
        this.stakePerBet = stakePerBet;
    }

    @Override
    public void placeBets(Roulette game, Player player) {
        if (player.getChipValue() >= stakePerBet) {
            int number = random.nextInt(37); // 0–36
            game.placeNumberBet(player, number, stakePerBet);
        }
    }
}