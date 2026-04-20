package casinoo;

import casinoo.betting.BetManager;
import casinoo.betting.BetTicket;
import casinoo.ai.StrategyPlayer;
import casinoo.game.blackjack.Blackjack;
import casinoo.game.blackjack.AIs.BlackjackStrategy;
import casinoo.game.blackjack.BlackjackBetResolver;
import casinoo.game.blackjack.BlackjackHand;
import casinoo.game.blackjack.BlackjackOutcome;
import casinoo.game.cards.CardRank;
import casinoo.game.cards.CardShoe;
import casinoo.game.cards.CardSuit;
import casinoo.game.cards.PlayingCard;
import casinoo.game.roulette.Roulette;
import casinoo.game.roulette.RouletteBetResolver;
import casinoo.game.roulette.RouletteColor;
import casinoo.game.roulette.RouletteOutcome;
import org.junit.jupiter.api.Test;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CasinoBehaviorTest {

    @Test
    void buyChipsPlaceBetAndCashOutUpdatesChipFlow() {
        Player player = new Player("Alice", 100);

        assertTrue(player.buyChips(60));
        assertEquals(60, player.getChipValue());

        assertTrue(player.placeBet(25));
        assertEquals(35, player.getChipValue());

        player.cashOut();
        assertEquals(0, player.getChipValue());
    }

    @Test
    void placeBetRejectsInvalidOrTooLargeStake() {
        Player player = new Player("Bob", 50);
        player.buyChips(20);

        assertFalse(player.placeBet(0));
        assertFalse(player.placeBet(-5));
        assertFalse(player.placeBet(21));
        assertEquals(20, player.getChipValue());
    }

    @Test
    void betManagerSettlesWinningBetAndClearsOpenBets() {
        Player player = new Player("Cara", 200);
        player.buyChips(100);

        BetManager<RouletteOutcome> manager = new BetManager<>(new RouletteBetResolver());
        BetTicket ticket = BetTicket.forColor(player, 10, "RED");

        assertTrue(manager.placeBet(ticket));
        assertTrue(manager.hasOpenBets());
        assertEquals(90, player.getChipValue());

        manager.settle(RouletteOutcome.of(3, RouletteColor.RED));

        assertEquals(110, player.getChipValue());
        assertFalse(manager.hasOpenBets());
    }

    @Test
    void roulettePlaceNumberBetValidatesMembershipAndNumberRange() {
        Roulette roulette = new Roulette();
        Player player = new Player("Drew", 100);
        player.buyChips(40);

        assertFalse(roulette.placeNumberBet(player, 7, 10));

        assertTrue(roulette.addPlayer(player));
        assertFalse(roulette.placeNumberBet(player, -1, 10));
        assertFalse(roulette.placeNumberBet(player, 37, 10));
        assertTrue(roulette.placeNumberBet(player, 7, 10));
    }

    @Test
    void rouletteBetResolverPaysOnlyOnExactNumberMatch() {
        RouletteBetResolver resolver = new RouletteBetResolver();
        Player player = new Player("Eve", 100);
        BetTicket numberBet = BetTicket.forNumber(player, 5, 17);

        int winningPayout = resolver.payoutMultiplier(numberBet, RouletteOutcome.of(17, RouletteColor.BLACK));
        int losingPayout = resolver.payoutMultiplier(numberBet, RouletteOutcome.of(16, RouletteColor.RED));

        assertEquals(36, winningPayout);
        assertEquals(0, losingPayout);
    }

    @Test
    void blackjackPlaceHandBetValidatesMembershipAndStake() {
        Blackjack blackjack = new Blackjack();
        Player player = new Player("Finn", 100);
        player.buyChips(50);

        assertFalse(blackjack.placeHandBet(player, 10));

        assertTrue(blackjack.addPlayer(player));
        assertFalse(blackjack.placeHandBet(player, 0));
        assertTrue(blackjack.placeHandBet(player, 10));
    }

    @Test
    void blackjackRoundPaysWinningHandWhenDealerBusts() {
        Player player = new Player("Gale", 200);
        player.buyChips(50);

        Blackjack blackjack = new Blackjack(
                new BetManager<BlackjackOutcome>(new BlackjackBetResolver()),
                new FixedShoe(
                    card(CardRank.KING, CardSuit.SPADES),
                    card(CardRank.NINE, CardSuit.HEARTS),
                    card(CardRank.QUEEN, CardSuit.DIAMONDS),
                    card(CardRank.SEVEN, CardSuit.CLUBS),
                    card(CardRank.EIGHT, CardSuit.CLUBS)
                )
        );

        assertTrue(blackjack.addPlayer(player));
        assertTrue(blackjack.placeHandBet(player, 10));
        assertEquals(40, player.getChipValue());

        blackjack.startGame();

        assertEquals(60, player.getChipValue());
    }

    @Test
    void blackjackRoundReturnsStakeOnPush() {
        Player player = new Player("Hale", 200);
        player.buyChips(50);

        Blackjack blackjack = new Blackjack(
                new BetManager<BlackjackOutcome>(new BlackjackBetResolver()),
                new FixedShoe(
                    card(CardRank.TEN, CardSuit.SPADES),
                    card(CardRank.NINE, CardSuit.HEARTS),
                    card(CardRank.EIGHT, CardSuit.CLUBS),
                    card(CardRank.NINE, CardSuit.DIAMONDS)
                )
        );

        assertTrue(blackjack.addPlayer(player));
        assertTrue(blackjack.placeHandBet(player, 10));
        assertEquals(40, player.getChipValue());

        blackjack.startGame();

        assertEquals(50, player.getChipValue());
    }

    @Test
    void blackjackUsesPlayerStrategyForHitAndStandDecisions() {
        ScriptedBlackjackStrategy strategy = new ScriptedBlackjackStrategy(false);
        StrategyPlayer<Blackjack> player = new StrategyPlayer<>("Ira", 200, strategy);
        player.buyChips(50);

        Blackjack blackjack = new Blackjack(
                new BetManager<BlackjackOutcome>(new BlackjackBetResolver()),
                new FixedShoe(
                        card(CardRank.SIX, CardSuit.SPADES),
                        card(CardRank.TEN, CardSuit.HEARTS),
                        card(CardRank.SIX, CardSuit.DIAMONDS),
                        card(CardRank.SEVEN, CardSuit.CLUBS)
                )
        );

        assertTrue(blackjack.addPlayer(player));
        assertTrue(blackjack.placeHandBet(player, 10));

        blackjack.startGame();

        assertEquals(1, strategy.getDecisionCount());
        assertEquals(40, player.getChipValue());
    }

    private static PlayingCard card(CardRank rank, CardSuit suit) {
        return new PlayingCard(rank, suit);
    }

    private static class FixedShoe implements CardShoe {
        private final Queue<PlayingCard> cards = new ArrayDeque<>();

        private FixedShoe(PlayingCard... cards) {
            this.cards.addAll(java.util.List.of(cards));
        }

        @Override
        public PlayingCard draw() {
            PlayingCard card = cards.poll();
            if (card == null) {
                throw new IllegalStateException("Fixed shoe ran out of cards");
            }
            return card;
        }
    }

    private static class ScriptedBlackjackStrategy implements BlackjackStrategy {
        private final Queue<Boolean> decisions;
        private final List<Integer> seenHandValues = new ArrayList<>();

        private ScriptedBlackjackStrategy(Boolean... decisions) {
            this.decisions = new ArrayDeque<>(List.of(decisions));
        }

        @Override
        public void playTurn(Blackjack game, Player player) {
            // Betting is done directly in the test to isolate the hit/stand decision path.
        }

        @Override
        public boolean shouldHit(Blackjack game, Player player, BlackjackHand playerHand, PlayingCard dealerUpCard) {
            seenHandValues.add(playerHand.value());
            Boolean next = decisions.poll();
            return next != null && next;
        }

        private int getDecisionCount() {
            return seenHandValues.size();
        }
    }
}
