package casinoo.game.blackjack.AIs;

import casinoo.Player;
import casinoo.ai.GameStrategy;
import casinoo.game.blackjack.Blackjack;
import casinoo.game.blackjack.BlackjackHand;
import casinoo.game.cards.PlayingCard;

public interface BlackjackStrategy extends GameStrategy<Blackjack> {
	// pattern: Template
	default boolean shouldHit(Blackjack game, Player player, BlackjackHand playerHand, PlayingCard dealerUpCard) {
		return playerHand.value() < 17;
	}
}
