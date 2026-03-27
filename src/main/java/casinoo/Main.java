package casinoo;

import casinoo.ai.StrategyPlayer;
import casinoo.game.roulette.AIs.*;
import casinoo.game.roulette.Roulette;

import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Roulette roulette = new Roulette();

        StrategyPlayer<Roulette> bot = new StrategyPlayer<>("James (AI)", 10_000, new RandomThirdStrategy());
        bot.buyChips(10_000);

         StrategyPlayer<Roulette> human = new StrategyPlayer<>("Alex (Human)", 10_000, new ManualRouletteStrategy());
         human.buyChips(10_000);

        roulette.addPlayer(bot);
         roulette.addPlayer(human);

        int totalRounds = readPositiveInt(scanner, "How many rounds do you want to play? ");

        for (int round = 1; round <= totalRounds; round++) {
            System.out.println();
            System.out.println("=== Round " + round + " ===");

            for (Player player : roulette.getPlayers()) {
                if (player instanceof StrategyPlayer<?>) {
                    @SuppressWarnings("unchecked")
                    StrategyPlayer<Roulette> strategyPlayer = (StrategyPlayer<Roulette>) player;
                    strategyPlayer.playTurn(roulette);
                    continue;
                }

                System.out.println(player.getName() + " has no strategy and will skip this round.");
            }

            roulette.startGame();
            printRoundSummary(roulette.getPlayers());
        }

        System.out.println();
        System.out.println("Final chip counts:");
        printRoundSummary(roulette.getPlayers());
    }

    private static int readPositiveInt(Scanner scanner, String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            try {
                int value = Integer.parseInt(input);
                if (value > 0) {
                    return value;
                }
            } catch (NumberFormatException ignored) {
            }
            System.out.println("Please enter a positive integer.");
        }
    }

    private static void printRoundSummary(List<Player> players) {
        for (Player player : players) {
            System.out.println(player.getName() + " -> chips: " + player.getChipValue());
        }
    }
}
