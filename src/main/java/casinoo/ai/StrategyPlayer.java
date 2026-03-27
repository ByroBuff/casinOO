package casinoo.ai;

import casinoo.Game;
import casinoo.Player;

import java.util.Objects;

public class StrategyPlayer<GameT extends Game> extends Player {
    private GameStrategy<GameT> strategy;

    public StrategyPlayer(String name, int initialBalance, GameStrategy<GameT> strategy) {
        super(name, initialBalance);
        this.strategy = Objects.requireNonNull(strategy, "strategy cannot be null");
    }

    public GameStrategy<GameT> getStrategy() {
        return strategy;
    }

    public void setStrategy(GameStrategy<GameT> strategy) {
        this.strategy = Objects.requireNonNull(strategy, "strategy cannot be null");
    }

    public void playTurn(GameT game) {
        strategy.playTurn(game, this);
    }
}