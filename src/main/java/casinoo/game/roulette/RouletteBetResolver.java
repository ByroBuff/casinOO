package casinoo.game.roulette;

import casinoo.betting.BetResolver;
import casinoo.betting.BetTicket;

public class RouletteBetResolver implements BetResolver<RouletteOutcome> {

    @Override
    public int payoutMultiplier(BetTicket ticket, RouletteOutcome outcome) {
        String market = ticket.market().toUpperCase();
        String selection = ticket.selection().toUpperCase();

        return switch (market) {
            case "COLOR" -> resolveColor(selection, outcome);
            case "NUMBER" -> resolveNumber(selection, outcome);
            case "THIRD" -> resolveThird(selection, outcome);
            case "EVENORODD" -> resolveEvenOdd(selection, outcome);
            default -> 0;
        };
    }

    private int resolveColor(String selection, RouletteOutcome outcome) {
        return switch (selection) {
            case "RED" -> outcome.color() == RouletteColor.RED ? 2 : 0;
            case "BLACK" -> outcome.color() == RouletteColor.BLACK ? 2 : 0;
            default -> 0;
        };
    }

    private int resolveNumber(String selection, RouletteOutcome outcome) {
        try {
            int picked = Integer.parseInt(selection);
            return picked == outcome.value() ? 36 : 0;
        } catch (NumberFormatException ex) {
            return 0;
        }
    }

    private int resolveThird(String selection, RouletteOutcome outcome) {
        try {
            int third = Integer.parseInt(selection);
            if (outcome.value() == 0) return 0; // 0 is not in any third
            int outcomeThird = (outcome.value() - 1) / 12 + 1; // Map 1-12 to 1, 13-24 to 2, 25-36 to 3
            return third == outcomeThird ? 3 : 0;
        } catch (NumberFormatException ex) {
            return 0;
        }
    }

    private int resolveEvenOdd(String selection, RouletteOutcome outcome) {
        try {
            int evenOrOdd = Integer.parseInt(selection);
            if (outcome.value() == 0) return 0; // 0 is not even or odd
            int outcomeEvenOdd = outcome.value() % 2 + 1; // Map evens to 1, odds to 2
            return evenOrOdd == outcomeEvenOdd ? 2 : 0;
        } catch (NumberFormatException ex) {
            return 0;
        }
    }
}