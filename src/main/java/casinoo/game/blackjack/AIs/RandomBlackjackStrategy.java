package casinoo.game.blackjack.AIs;

import casinoo.Player;
import casinoo.game.blackjack.Blackjack;
import casinoo.game.blackjack.BlackjackHand;
import casinoo.game.cards.PlayingCard;

import java.util.Random;

public class RandomBlackjackStrategy implements BlackjackStrategy {
    private static final double BET_PERCENTAGE = 0.10;
    private static final double SKIP_CHANCE = 0.15;
    private final Random random = new Random();

    @Override
    public void playTurn(Blackjack game, Player player) {
        int chips = player.getChipValue();
        if (chips <= 0) {
            return;
        }

        if (random.nextDouble() < SKIP_CHANCE) {
            return;
        }

        int stake = Math.max(1, (int) Math.floor(chips * BET_PERCENTAGE));
        game.placeHandBet(player, stake);
    }

    @Override
    public boolean shouldHit(Blackjack game, Player player, BlackjackHand playerHand, PlayingCard dealerUpCard) {
        int handValue = playerHand.value();
        if (handValue <= 11) {
            return true;
        }
        if (handValue >= 17) {
            return false;
        }

        return random.nextDouble() < 0.6;
    }
}
