package casinoo.game.blackjack;

import casinoo.Player;

import java.util.Map;
import java.util.Objects;

public record BlackjackOutcome(Map<Player, BlackjackRoundResult> results, BlackjackHand dealerHand) {
    public BlackjackOutcome {
        Objects.requireNonNull(results, "results cannot be null");
        Objects.requireNonNull(dealerHand, "dealerHand cannot be null");
        results = Map.copyOf(results);
    }

    public BlackjackRoundResult resultFor(Player player) {
        return results.getOrDefault(player, BlackjackRoundResult.LOSS);
    }
}
