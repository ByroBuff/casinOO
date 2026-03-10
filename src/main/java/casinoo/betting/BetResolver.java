package casinoo.betting;

public interface BetResolver<OutcomeT> {
    int payoutMultiplier(BetTicket ticket, OutcomeT outcome);
    // return 0 for loss, 2 for even money, 36 for roulette straight-up, etc.
}