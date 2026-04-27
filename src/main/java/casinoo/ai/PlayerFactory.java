package casinoo.ai;

import casinoo.Game;
import java.util.Objects;

public final class PlayerFactory {
    private PlayerFactory() {}

    public static <GameT extends Game> StrategyPlayer<GameT> createStrategyPlayer(
            String name, int initialBalance, GameStrategy<GameT> strategy) {
        return new StrategyPlayer<>(name, initialBalance, Objects.requireNonNull(strategy, "strategy"));
    }

    public static <GameT extends Game> AIPlayer<GameT> createAIPlayer(
            String name, int initialBalance, GameStrategy<GameT> strategy) {
        return new AIPlayer<>(name, initialBalance, Objects.requireNonNull(strategy, "strategy"));
    }

    public static <GameT extends Game> Builder<GameT> builder() {
        return new Builder<>();
    }

    public static final class Builder<GameT extends Game> {
        private String name = "AI";
        private int initialBalance = 100;
        private GameStrategy<GameT> strategy;

        public Builder<GameT> name(String name) {
            this.name = name;
            return this;
        }

        public Builder<GameT> initialBalance(int initialBalance) {
            this.initialBalance = initialBalance;
            return this;
        }

        public Builder<GameT> strategy(GameStrategy<GameT> strategy) {
            this.strategy = strategy;
            return this;
        }

        public StrategyPlayer<GameT> buildStrategyPlayer() {
            return new StrategyPlayer<>(name, initialBalance, Objects.requireNonNull(strategy, "strategy"));
        }

        public AIPlayer<GameT> buildAIPlayer() {
            return new AIPlayer<>(name, initialBalance, Objects.requireNonNull(strategy, "strategy"));
        }
    }
}
