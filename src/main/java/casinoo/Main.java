package casinoo;

import casinoo.game.roulette.Roulette;

public class Main {
	public static void main(String[] args) {
        Player p1 = new Player("James", 1000);
        p1.buyChips(1000);
        Roulette roulette = new Roulette();
        roulette.addPlayer(p1);

        for (int i = 0; i <= 100; i++) {
            for (int j = 0; j <= 16; j++) {
                roulette.placeNumberBet(p1, j, 10);
            }
            roulette.startGame();

            System.out.println(p1);
        }
	}
}

