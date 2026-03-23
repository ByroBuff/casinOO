package casinoo;

import casinoo.betting.BetManager;
import casinoo.betting.BetTicket;
import casinoo.game.roulette.Roulette;
import casinoo.game.roulette.RouletteBetResolver;
import casinoo.game.roulette.RouletteColor;
import casinoo.game.roulette.RouletteOutcome;
import org.junit.jupiter.api.Test;

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
}
