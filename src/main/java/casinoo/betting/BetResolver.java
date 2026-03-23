package casinoo.betting;

public interface BetResolver<OutcomeT> {
    int payoutMultiplier(BetTicket ticket, OutcomeT outcome);
}