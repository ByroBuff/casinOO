package casinoo.ai;

import casinoo.Game;
import casinoo.Player;

// pattern: strategy
public interface GameStrategy<GameT extends Game> {
    void playTurn(GameT game, Player player);
}