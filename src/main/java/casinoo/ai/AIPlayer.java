package casinoo.ai;

import casinoo.Game;

public class AIPlayer<GameT extends Game> extends StrategyPlayer<GameT> {
    public AIPlayer(String name, int initialBalance, GameStrategy<GameT> strategy) {
        super(name, initialBalance, strategy);
    }
}