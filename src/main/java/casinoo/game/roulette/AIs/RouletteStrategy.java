package casinoo.game.roulette.AIs;

import casinoo.Player;
import casinoo.game.roulette.Roulette;

public interface RouletteStrategy {
    void placeBets(Roulette game, Player player);
}