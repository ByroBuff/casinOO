package casinoo;

public class Main {
	public static void main(String[] args) {
        Player p1 = new Player("James", 100);
        p1.buyChips(100);
        Player p2 = new Player("Steven", 1000);
        p2.buyChips(100);
        Roulette roulette = new Roulette();
        roulette.addPlayer(p1);
        roulette.addPlayer(p2);

            System.out.println(p1);
            System.out.println(p2);

        roulette.placeBet(p1, Roulette.BetType.RED, 0, 10);
        roulette.placeBet(p2, Roulette.BetType.NUMBER, 17, 5);

            System.out.println(p1);
                System.out.println(p2);

        roulette.startGame();

        System.out.println(p1);
        System.out.println(p2);
	}
}

