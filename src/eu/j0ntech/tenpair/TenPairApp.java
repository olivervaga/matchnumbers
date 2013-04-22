package eu.j0ntech.tenpair;

import android.app.Application;
import eu.j0ntech.tenpair.game.GameBoard;

public class TenPairApp extends Application {
	
	private GameBoard board;
	
	public GameBoard startNewGame() {
		this.board = new GameBoard();
		return board;
	}
	
	public GameBoard startLoadedGame() {
		return board;
	}
	
	public void setBoard(GameBoard board) {
		this.board = board;
	}

}
