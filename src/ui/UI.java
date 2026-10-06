package ui;

import irrgarten.Directions;
import irrgarten.GameState;

/**
 *
 * @author Usuario
 */
public interface UI {
    public void showGame(GameState gameState);
    
    public Directions nextMove();
}
