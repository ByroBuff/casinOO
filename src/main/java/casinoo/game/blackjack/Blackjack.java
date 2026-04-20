package casinoo.game.blackjack;

import casinoo.GameState;
import casinoo.Player;
import casinoo.ai.StrategyPlayer;
import casinoo.betting.BetManager;
import casinoo.betting.BetTicket;
import casinoo.game.blackjack.AIs.BlackjackStrategy;
import casinoo.game.cards.CardGame;
import casinoo.game.cards.CardShoe;
import casinoo.game.cards.PlayingCard;
import casinoo.game.cards.ShuffledCardShoe;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class Blackjack extends CardGame {
    private final BetManager<BlackjackOutcome> betManager;

    public Blackjack() {
        this(new BetManager<>(new BlackjackBetResolver()), new ShuffledCardShoe());
    }

    public Blackjack(BetManager<BlackjackOutcome> betManager, CardShoe shoe) {
        super("Blackjack", 1, 7, shoe);
        this.betManager = Objects.requireNonNull(betManager, "betManager cannot be null");
    }

    public boolean placeHandBet(Player player, int stake) {
        if (!getPlayers().contains(player)) {
            return false;
        }
        return betManager.placeBet(new BetTicket(player, stake, "HAND", "MAIN"));
    }

    @Override
    public void startGame() {
        if (!betManager.hasOpenBets()) {
            System.out.println("No bets placed.");
            return;
        }

        betManager.printBets();
        gameState = GameState.IN_PROGRESS;

        List<Player> registeredPlayers = getPlayers();
        List<Player> players = betManager.getOpenBets().stream()
                .map(BetTicket::player)
                .filter(registeredPlayers::contains)
                .distinct()
                .toList();

        if (players.isEmpty()) {
            System.out.println("No valid betting players found.");
            gameState = GameState.READY_TO_PLAY;
            return;
        }

        Map<Player, BlackjackHand> playerHands = new LinkedHashMap<>();
        for (Player player : players) {
            playerHands.put(player, new BlackjackHand());
        }
        BlackjackHand dealerHand = new BlackjackHand();

        dealInitialCards(playerHands, dealerHand);
        playPlayerHands(playerHands, dealerHand);
        playDealerHand(playerHands, dealerHand);

        BlackjackOutcome outcome = resolveOutcome(playerHands, dealerHand);
        printRoundResults(playerHands, dealerHand, outcome);
        betManager.settle(outcome);

        gameState = GameState.READY_TO_PLAY;
    }

    private void dealInitialCards(Map<Player, BlackjackHand> playerHands, BlackjackHand dealerHand) {
        for (int i = 0; i < 2; i++) {
            for (BlackjackHand hand : playerHands.values()) {
                hand.addCard(drawCard());
            }
            dealerHand.addCard(drawCard());
        }
    }

    private void playPlayerHands(Map<Player, BlackjackHand> playerHands, BlackjackHand dealerHand) {
        PlayingCard dealerUpCard = dealerHand.firstCard()
                .orElseThrow(() -> new IllegalStateException("Dealer should have at least one card"));

        for (Map.Entry<Player, BlackjackHand> entry : playerHands.entrySet()) {
            Player player = entry.getKey();
            BlackjackHand hand = entry.getValue();
            BlackjackStrategy strategy = resolveStrategy(player);

            while (!hand.isBust() && !hand.isBlackjack()) {
                boolean shouldHit = strategy == null
                        ? hand.value() < 17
                        : strategy.shouldHit(this, player, hand, dealerUpCard);

                if (!shouldHit) {
                    break;
                }

                hand.addCard(drawCard());
            }
        }
    }

    private void playDealerHand(Map<Player, BlackjackHand> playerHands, BlackjackHand dealerHand) {
        boolean anyActivePlayer = playerHands.values().stream().anyMatch(hand -> !hand.isBust());
        if (!anyActivePlayer) {
            return;
        }

        while (dealerHand.value() < 17) {
            dealerHand.addCard(drawCard());
        }
    }

    private BlackjackStrategy resolveStrategy(Player player) {
        if (!(player instanceof StrategyPlayer<?> strategyPlayer)) {
            return null;
        }

        Object strategy = strategyPlayer.getStrategy();
        if (strategy instanceof BlackjackStrategy blackjackStrategy) {
            return blackjackStrategy;
        }
        return null;
    }

    private BlackjackOutcome resolveOutcome(Map<Player, BlackjackHand> playerHands, BlackjackHand dealerHand) {
        Map<Player, BlackjackRoundResult> results = new LinkedHashMap<>();
        boolean dealerBust = dealerHand.isBust();
        boolean dealerBlackjack = dealerHand.isBlackjack();

        for (Map.Entry<Player, BlackjackHand> entry : playerHands.entrySet()) {
            BlackjackHand hand = entry.getValue();
            BlackjackRoundResult result;

            if (hand.isBust()) {
                result = BlackjackRoundResult.BUST;
            } else if (hand.isBlackjack() && !dealerBlackjack) {
                result = BlackjackRoundResult.BLACKJACK;
            } else if (dealerBlackjack && !hand.isBlackjack()) {
                result = BlackjackRoundResult.LOSS;
            } else if (dealerBust) {
                result = BlackjackRoundResult.WIN;
            } else {
                result = compareByScore(hand.value(), dealerHand.value());
            }

            results.put(entry.getKey(), result);
        }

        return new BlackjackOutcome(results, dealerHand);
    }

    private BlackjackRoundResult compareByScore(int playerScore, int dealerScore) {
        if (playerScore > dealerScore) {
            return BlackjackRoundResult.WIN;
        }
        if (playerScore < dealerScore) {
            return BlackjackRoundResult.LOSS;
        }
        return BlackjackRoundResult.PUSH;
    }

    private void printRoundResults(Map<Player, BlackjackHand> playerHands, BlackjackHand dealerHand, BlackjackOutcome outcome) {
        System.out.println("Dealer -> " + dealerHand);
        for (Map.Entry<Player, BlackjackHand> entry : playerHands.entrySet()) {
            Player player = entry.getKey();
            BlackjackHand hand = entry.getValue();
            System.out.println(player.getName() + " -> " + hand + ", result=" + outcome.resultFor(player));
        }
    }
}
