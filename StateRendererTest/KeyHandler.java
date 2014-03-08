package ecrosogames.staterenderer;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import ecrosogames.staterenderer.GameManager.GameState;

public class KeyHandler extends KeyAdapter {

	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_ENTER) {
			GameManager manager = GameManager.getInstance();
			if (manager.getGameState() == GameState.PRIMARY) manager.setGameState(GameState.SECONDARY);
			else manager.setGameState(GameState.PRIMARY);
		}
	}
}
