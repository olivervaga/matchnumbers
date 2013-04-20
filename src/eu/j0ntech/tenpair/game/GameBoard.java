package eu.j0ntech.tenpair.game;

import java.util.ArrayList;
import java.util.List;

import android.util.Log;

/**
 * Class for holding all the game-related data
 * and logic
 * @author Oliver Vaga
 *
 */
public class GameBoard {
	
	private final String TAG = "Gameboard";
	
	/**
	 * Maximum number of columns on the board
	 */
	public static final int COLUMNS = 9;
	
	/**
	 * Current number of rows on the board
	 */
	private int rows = 3;
	
	/**
	 * Representation of the game board, a two-dimensional ArrayList
	 * containing all the NumberSquares
	 */
	private ArrayList<ArrayList<Tile>> board;
	
	/**
	 * Creates a new GameBoard and populates it with the default values
	 */
	public GameBoard() {
		board = new ArrayList<ArrayList<Tile>>(rows);
		board.ensureCapacity(rows);
		for (int i = 0; i <= 2; i++) {
			board.add(i, new ArrayList<Tile>(COLUMNS));
		}
		populateDefaultBoard();
	}
	
	public Tile getNumberSquare(int row, int column) {
		return board.get(row).get(column);
	}
	
	public byte getSquareValue(int row, int column) {
		return board.get(row).get(column).getValue();
	}
	
	public int getRows() {
		return rows;
	}
	
	/**
	 * Populates the board with the default starting values
	 * for the game
	 */
	private void populateDefaultBoard() {
		for (int i = 0; i < 9; i++) {
			board.get(0).add(i, new Tile((byte) (i + 1), 0, i));
			if (i % 2 == 0) {
				board.get(1).add(i, new Tile((byte) 1, 1, i));
			} else {
				switch (i) {
					case 1:
						board.get(1).add(i, new Tile((byte) 1, 1, i));
						break;
					case 3:
						board.get(1).add(i, new Tile((byte) 2, 1, i));
						break;
					case 5:
						board.get(1).add(i, new Tile((byte) 3, 1, i));
						break;
					case 7:
						board.get(1).add(i, new Tile((byte) 4, 1, i));
						break;
				}
			}
			if (i % 2 != 0) {
				board.get(2).add(i, new Tile((byte) 1, 2, i));
			} else {
				switch (i) {
					case 0:
						board.get(2).add(i, new Tile((byte) 5, 2, i));
						break;
					case 2:
						board.get(2).add(i, new Tile((byte) 6, 2, i));
						break;
					case 4:
						board.get(2).add(i, new Tile((byte) 7, 2, i));
						break;
					case 6:
						board.get(2).add(i, new Tile((byte) 8, 2, i));
						break;
					case 8:
						board.get(2).add(i, new Tile((byte) 9, 2, i));
						break;
				}
			}
		}
	}
	
	/**
	 * Finds all the legally highlightable adjacent squares
	 * (scratched squares can't be highlighted) BUGGY
	 * @param row Row of the selected square
	 * @param column Column of the selected square
	 * @return An ArrayList of highlighted NumberSquare objects
	 */
	public List<Tile> getAdjacentSquares(int row, int column) {
		ArrayList<Tile> result = new ArrayList<Tile>(4);
		// Top adjacent
		for (int i = row - 1; i >= 0; i--) {
			if (!getNumberSquare(i, column).isScratched()) {
				result.add(getNumberSquare(i,column));
				break;
			}
		}
		// Left adjacent
		leftOuter:
		if (column == 0) {
			for (int i = row - 1; i >= 0; i--) {
				for (int j = COLUMNS - 1; j >= 0; j--) {
					Log.d(TAG, String.valueOf(getNumberSquare(row, column).isScratched()));
					if (!getNumberSquare(row, column).isScratched()) {
						result.add(getNumberSquare(i, j));
						break leftOuter;
					}
				}
			}
		} else {
			for (int i = row; i >= 0; i--) {
				for (int j = column - 1; j >= 0; j--) {
					Log.d(TAG, String.valueOf(getNumberSquare(row, column).isScratched()));
					if (!getNumberSquare(row, column).isScratched()) {
						result.add(getNumberSquare(i, j));
						break leftOuter;
					}
				}
			}			
		}
		// Bottom adjacent
		for (int i = row + 1; i < rows; i++) {
			if (!getNumberSquare(i, column).isScratched()) {
				result.add(getNumberSquare(i,column));
				break;
			}
		}
		// Right adjacent
		rightOuter:
		if (column + 1 == COLUMNS) {
			for (int i = row + 1; i < rows; i++) {
				for (int j = 0; j < COLUMNS; j++) {
					if (!getNumberSquare(row, column).isScratched()) {
						result.add(getNumberSquare(i, j));
						break rightOuter;
					}
				}
			}
		} else {
			for (int i = row; i < rows; i++) {
				for (int j = column + 1; j < COLUMNS; j++) {
					if (!getNumberSquare(row, column).isScratched()) {
						result.add(getNumberSquare(i, j));
						break rightOuter;
					}
				}
			}			
		}
		return result;
	}
	
	/**
	 * Finds all the legally highlightable adjacent squares
	 * (scratched squares can't be highlighted) BUGGY
	 * @param row Row of the selected square
	 * @param column Column of the selected square
	 * @return A two-dimensional integer array with the
	 * rows and columns of highlighted squares
	 */
	public int[][] getAdjacentSquaresArray(int row, int column) {
		List<Tile> list = getAdjacentSquares(row, column);
		Log.d(TAG, "Got " + list.size() + " adjacent squares");
		int[][] result = new int[0][0];
		if (list != null) {
			result = new int[list.size()][2];
			for (int i = 0; i < result.length; i++) {
				result[i][0] = list.get(i).getRow();
				result[i][1] = list.get(i).getColumn();
//				Log.d(TAG, "Set square (" + result[i][0] + ", " + result[i][1] + ") as HIGHLIGHTED");
			}
		}
		return result;
	}
	
	/**
	 * Displays the current game board state
	 * in LogCat (for debugging purposes),
	 * scratched squares have trailing asterisk
	 */
	public void displayBoard() {
		StringBuilder rowString;
		for (int i = 0; i < rows; i++) {
			rowString = new StringBuilder(9);
			for (int j = 0; j < 9; j++) {
				rowString.append(getSquareValue(i, j));
				if (getNumberSquare(i, j).isScratched()) rowString.append("*");
				else rowString.append(" ");
				rowString.append(" ");
			}
			Log.d(TAG, rowString.toString());
		}
	}

}
