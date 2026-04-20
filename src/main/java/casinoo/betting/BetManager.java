package casinoo.betting;

import java.util.ArrayList;
import java.util.List;

public class BetManager<OutcomeT> {
    private final List<BetTicket> openBets = new ArrayList<>();
    private final BetResolver<OutcomeT> resolver;

    public BetManager(BetResolver<OutcomeT> resolver) {
        this.resolver = resolver;
    }

    public boolean placeBet(BetTicket ticket) {
        if (ticket.stake() <= 0) return false;
        if (!ticket.player().placeBet(ticket.stake())) return false; // check if player has enough chips
        openBets.add(ticket);
        return true;
    }

    public void printBets() {
        for (BetTicket betTicket : openBets) {
            System.out.println(betTicket.toString());
        }
    }

    public void settle(OutcomeT outcome) {
        for (BetTicket bet : openBets) {
            int multiplier = resolver.payoutMultiplier(bet, outcome);
            if (multiplier > 0) {
                bet.player().payout(bet.stake() * multiplier);
            }
        }
        openBets.clear();
    }

    public boolean hasOpenBets() {
        return !openBets.isEmpty();
    }

    public List<BetTicket> getOpenBets() {
        return List.copyOf(openBets);
    }
}