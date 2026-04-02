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

    public static BetTicket forThird(Player player, int stake, int third) {
        return new BetTicket(player, stake, "THIRD", Integer.toString(third));
    }

    public static BetTicket forEvenOrOdd(Player player, int stake, int evenOrOdd) {
        return new BetTicket(player, stake, "EVENORODD", Integer.toString(evenOrOdd));
    }



    public String toString() {
        return String.format("BetTicket(player=%s, stake=%d, market=%s, selection=%s)",
                player.getName(), stake, market, selection);
    }
};