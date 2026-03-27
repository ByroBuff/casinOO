package casinoo.game.roulette.AIs;

import casinoo.Player;
import casinoo.game.roulette.Roulette;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class ManualRouletteStrategy implements RouletteStrategy {
    private static final Scanner INPUT = new Scanner(System.in);

    @Override
    public void playTurn(Roulette game, Player player) {
        if (player.getChipValue() <= 0) {
            System.out.println(player.getName() + " has no chips left and cannot bet.");
            return;
        }

        System.out.println();
        System.out.println(player.getName() + ", your chips: " + player.getChipValue());
        String betType = readChoice("Choose bet type (color/number/third or skip): ", Arrays.asList("color", "number", "third", "skip"));

        if ("skip".equals(betType)) {
            System.out.println(player.getName() + " skipped this round.");
            return;
        }

        int stake = readBoundedInt("Enter stake (1 to " + player.getChipValue() + "): ", 1, player.getChipValue());

        boolean placed;
        if ("color".equals(betType)) {
            String color = readChoice("Choose color (red/black): ", Arrays.asList("red", "black")).toUpperCase();
            placed = game.placeColorBet(player, color, stake);
        } else if ("third".equals(betType)) {
            int third = readBoundedInt("Choose third (1-3): ", 1, 3);
            placed = game.placeThirdBet(player, third, stake);
        } else {
            int number = readBoundedInt("Pick a roulette number (0 to 36): ", 0, 36);
            placed = game.placeNumberBet(player, number, stake);
        }

        if (!placed) {
            System.out.println("Bet was rejected. Please check balance/rules next round.");
        }
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