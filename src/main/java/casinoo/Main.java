package casinoo;

import casinoo.game.roulette.AIs.*;
import casinoo.game.roulette.*;
import casinoo.betting.*;

public class Main {
	public static void main(String[] args) {
        Player p1 = new Player("James", 10000);
        p1.buyChips(10000);
        BetManager<RouletteOutcome> rouletteBetManager = new BetManager<>(new RouletteBetResolver());
        Roulette roulette = new Roulette(rouletteBetManager);
        roulette.addPlayer(p1);

        RandomColorStrategy randColorStrat = new RandomColorStrategy(10);

        int initialBet = 100;
        int currentBet = initialBet;
        boolean lastRoundWon = true;

        for (int i = 0; i <= 100; i++) {
            int balanceBeforeBet = p1.getChipValue();
            roulette.placeColorBet(p1, "RED", currentBet);
            roulette.startGame();

            if (p1.getChipValue() > balanceBeforeBet) {
                // Win: reset to initial bet
                currentBet = initialBet;
                lastRoundWon = true;
            } else {
                // Loss: double the bet
                currentBet *= 2;
                lastRoundWon = false;
            }

            System.out.println(p1);
        }
	}
}

