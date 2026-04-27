package casinoo;

import casinoo.ai.PlayerFactory;
import casinoo.ai.StrategyPlayer;
import casinoo.game.blackjack.Blackjack;
import casinoo.game.blackjack.AIs.ManualBlackjackStrategy;
import casinoo.game.blackjack.AIs.RandomBlackjackStrategy;
import casinoo.game.roulette.AIs.*;
import casinoo.game.roulette.Roulette;

import java.util.List;
import java.util.Scanner;

// pattern: singleton, facade
public class Casino {
    private static final Casino INSTANCE = new Casino();

    private Casino() {}

    public static Casino getInstance() {
        return INSTANCE;
    }

    public void runInteractive() {
        try (Scanner scanner = new Scanner(System.in)) {
            String selectedGame = readChoice(scanner, "Choose game (roulette/blackjack): ", List.of("roulette", "blackjack"));

            if ("blackjack".equals(selectedGame)) {
                runBlackjack(scanner);
                return;
            }

            runRoulette(scanner);
        }
    }

    private void runRoulette(Scanner scanner) {
        Roulette roulette = new Roulette();

        StrategyPlayer<Roulette> bot = PlayerFactory.createAIPlayer("James (AI)", 10_000, new RandomThirdStrategy());
        bot.buyChips(10_000);

        StrategyPlayer<Roulette> human = PlayerFactory.createStrategyPlayer("Alex (Human)", 10_000, new ManualRouletteStrategy());
        human.buyChips(10_000);

        roulette.addPlayer(bot);
        roulette.addPlayer(human);

        runRounds(scanner, roulette);
    }

    private void runBlackjack(Scanner scanner) {
        Blackjack blackjack = new Blackjack();

        StrategyPlayer<Blackjack> bot = PlayerFactory.createAIPlayer("James (AI)", 10_000, new RandomBlackjackStrategy());
        bot.buyChips(10_000);

        StrategyPlayer<Blackjack> human = PlayerFactory.createStrategyPlayer("Alex (Human)", 10_000, new ManualBlackjackStrategy());
        human.buyChips(10_000);

        blackjack.addPlayer(bot);
        blackjack.addPlayer(human);

        runRounds(scanner, blackjack);
    }

    private <GameT extends Game> void runRounds(Scanner scanner, GameT game) {
        int totalRounds = readPositiveInt(scanner, "How many rounds do you want to play? ");

        for (int round = 1; round <= totalRounds; round++) {
            System.out.println();
            System.out.println("=== " + game.getName() + " Round " + round + " ===");

            playStrategyTurns(game);
            game.startGame();
            printRoundSummary(game.getPlayers());
        }

        System.out.println();
        System.out.println("Final chip counts:");
        printRoundSummary(game.getPlayers());
    }

    private <GameT extends Game> void playStrategyTurns(GameT game) {
        for (Player player : game.getPlayers()) {
            if (player instanceof StrategyPlayer<?>) {
                @SuppressWarnings("unchecked")
                StrategyPlayer<GameT> strategyPlayer = (StrategyPlayer<GameT>) player;
                strategyPlayer.playTurn(game);
                continue;
            }

            System.out.println(player.getName() + " has no strategy and will skip this round.");
        }
    }

    private int readPositiveInt(Scanner scanner, String prompt) {
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

    private String readChoice(Scanner scanner, String prompt, List<String> choices) {
        while (true) {
            System.out.print(prompt);
            String value = scanner.nextLine().trim().toLowerCase();
            if (choices.contains(value)) {
                return value;
            }
            System.out.println("Please choose one of: " + String.join(", ", choices));
        }
    }

    private void printRoundSummary(List<Player> players) {
        for (Player player : players) {
            System.out.println(player.getName() + " -> chips: " + player.getChipValue());
        }
    }
}
