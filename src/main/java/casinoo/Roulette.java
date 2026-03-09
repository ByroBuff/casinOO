package casinoo;

public class Roulette extends Game {

    private static final String GAME_NAME = "Roulette";
    private static final int MIN_PLAYERS = 1;
    private static final int MAX_PLAYERS = 8;

    public Roulette() {
        super(GAME_NAME, MIN_PLAYERS, MAX_PLAYERS);
    }

    @Override
    public void startGame() {
        if (getGameState() != GameState.READY_TO_PLAY) {
            throw new IllegalStateException("Cannot start game. Not enough players.");
        }
        
    }
}
