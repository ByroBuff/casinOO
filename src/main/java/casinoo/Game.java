package casinoo;

import java.util.ArrayList;
import java.util.List;

// pattern: State
public abstract class Game {

    private final String name;
    private final int minPlayers;
    private final int maxPlayers;
    private final List<Player> players;
    protected GameState gameState;

    protected Game(String name, int minPlayers, int maxPlayers) {
        this.name = name;
        this.minPlayers = minPlayers;
        this.maxPlayers = maxPlayers;
        this.players = new ArrayList<>();
        this.gameState = GameState.WAITING_FOR_PLAYERS;
    }

    public String toString() {
        return "Game: " + name + ", Players: " + players.size() + "/" + maxPlayers + ", State: " + gameState;
    }

    public String getName() {
        return name;
    }

    public int getMinPlayers() {
        return minPlayers;
    }

    public int getMaxPlayers() {
        return maxPlayers;
    }

    public List<Player> getPlayers() {
        return new ArrayList<>(players);
    }

    public GameState getGameState() {
        return gameState;
    }

    public boolean canAddNewPlayer() {
        return players.size() < maxPlayers && (gameState == GameState.WAITING_FOR_PLAYERS || gameState == GameState.READY_TO_PLAY);
    }

    public boolean addPlayer(Player player) {
        if (!canAddNewPlayer()) {
            return false;
        }
        boolean added = players.add(player);
        if (players.size() >= minPlayers) {
            gameState = GameState.READY_TO_PLAY;
        }
        return added;
    }

    public boolean removePlayer(Player player) {
        if (gameState == GameState.IN_PROGRESS) {
            return false;
        }
        boolean removed = players.remove(player);
        if (players.size() < minPlayers) {
            gameState = GameState.WAITING_FOR_PLAYERS;
        }
        return removed;
    }

    public abstract void startGame();
}
