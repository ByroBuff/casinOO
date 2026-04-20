package casinoo.game.blackjack;

import casinoo.betting.BetResolver;
import casinoo.betting.BetTicket;

public class BlackjackBetResolver implements BetResolver<BlackjackOutcome> {
    @Override
    public int payoutMultiplier(BetTicket ticket, BlackjackOutcome outcome) {
        if (!"HAND".equalsIgnoreCase(ticket.market())) {
            return 0;
        }

        BlackjackRoundResult result = outcome.resultFor(ticket.player());

        return switch (result) {
            case BLACKJACK, WIN -> 2;
            case PUSH -> 1;
            case LOSS, BUST -> 0;
        };
    }
}
