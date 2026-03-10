package casinoo.betting;

import casinoo.Player;

public record BetTicket(
        Player player,
        int stake,
        String market,
        String selection
) {};