package casinoo.game.roulette.AIs;

import casinoo.Player;
import casinoo.game.roulette.Roulette;

import java.util.Random;

public class RandomColorStrategy implements RouletteStrategy {
    private final Random random = new Random();
    private final int stakePerBet;

    public RandomColorStrategy(int stakePerBet) {
        this.stakePerBet = stakePerBet;
    }

    @Override
    public void placeBets(Roulette game, Player player) {
        if (player.getChipValue() >= stakePerBet) {
            boolean betOnRed = random.nextBoolean();
            game.placeColorBet(player, betOnRed ? "RED" : "BLACK", stakePerBet);
        }
    }
}