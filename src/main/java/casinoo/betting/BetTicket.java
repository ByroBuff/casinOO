package casinoo.betting;

import casinoo.Player;

public record BetTicket(
        Player player,
        int stake,
        String market,
        String selection
) {
    public static BetTicket forColor(Player player, int stake, String color) {
        return new BetTicket(player, stake, "COLOR", color);
    }

    public static BetTicket forNumber(Player player, int stake, int number) {
        return new BetTicket(player, stake, "NUMBER", Integer.toString(number));
    }

    public String toString() {
        return String.format("BetTicket(player=%s, stake=%d, market=%s, selection=%s)",
                player.getName(), stake, market, selection);
    }
};