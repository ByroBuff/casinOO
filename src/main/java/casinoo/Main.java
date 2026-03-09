package casinoo;

public class Main {
	public static void main(String[] args) {
        Player p1 = new Player("James", 100);
        p1.buyChips(100);
        Player p2 = new Player("Steven", 1000);
        p2.buyChips(100);
        Game roulette = new Roulette();
        roulette.addPlayer(p1);
        roulette.addPlayer(p2);

        for (Player player : roulette.getPlayers()) {
            System.out.println(player.toString());
        }
        System.out.println(roulette.toString());

        roulette.startGame();
	}
}

