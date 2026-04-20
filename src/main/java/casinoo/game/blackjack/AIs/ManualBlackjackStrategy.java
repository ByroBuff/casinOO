package casinoo.game.blackjack.AIs;

import casinoo.Player;
import casinoo.game.blackjack.Blackjack;
import casinoo.game.blackjack.BlackjackHand;
import casinoo.game.cards.PlayingCard;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class ManualBlackjackStrategy implements BlackjackStrategy {
    private static final Scanner INPUT = new Scanner(System.in);

    @Override
    public void playTurn(Blackjack game, Player player) {
        if (player.getChipValue() <= 0) {
            System.out.println(player.getName() + " has no chips left and cannot bet.");
            return;
        }

        System.out.println();
        System.out.println(player.getName() + ", your chips: " + player.getChipValue());
        String action = readChoice("Choose action (bet/skip): ", Arrays.asList("bet", "skip"));

        if ("skip".equals(action)) {
            System.out.println(player.getName() + " skipped this round.");
            return;
        }

        int stake = readBoundedInt("Enter stake (1 to " + player.getChipValue() + "): ", 1, player.getChipValue());
        boolean placed = game.placeHandBet(player, stake);

        if (!placed) {
            System.out.println("Bet was rejected. Please check balance/rules next round.");
        }
    }

    @Override
    public boolean shouldHit(Blackjack game, Player player, BlackjackHand playerHand, PlayingCard dealerUpCard) {
        if (playerHand.isBust() || playerHand.isBlackjack()) {
            return false;
        }

        System.out.println(player.getName() + " hand: " + playerHand);
        System.out.println("Dealer shows: " + dealerUpCard);
        String choice = readChoice("Choose action (hit/stand): ", Arrays.asList("hit", "stand"));
        return "hit".equals(choice);
    }

    private static int readBoundedInt(String prompt, int min, int max) {
        while (true) {
            int value = readNonNegativeInt(prompt);
            if (value >= min && value <= max) {
                return value;
            }
            System.out.println("Please enter a value between " + min + " and " + max + ".");
        }
    }

    private static int readNonNegativeInt(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = INPUT.nextLine().trim();
            try {
                int value = Integer.parseInt(input);
                if (value >= 0) {
                    return value;
                }
            } catch (NumberFormatException ignored) {
            }
            System.out.println("Please enter a non-negative integer.");
        }
    }

    private static String readChoice(String prompt, List<String> options) {
        while (true) {
            System.out.print(prompt);
            String value = INPUT.nextLine().trim().toLowerCase();
            if (options.contains(value)) {
                return value;
            }
            System.out.println("Invalid choice. Valid options: " + String.join(", ", options));
        }
    }
}
