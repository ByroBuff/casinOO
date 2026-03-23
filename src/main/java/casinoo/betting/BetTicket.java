package casinoo.betting;

import casinoo.Player;

public record BetTicket(
        Player player,
        int stake,
        String market,
        String selection
) {
    public String toString() {
        return String.format("BetTicket(player=%s, stake=%d, market=%s, selection=%s)",
                player.getName(), stake, market, selection);
    }
};