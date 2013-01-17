package eu.j0ntech.tenpair.game;

import java.util.ArrayList;
import java.util.List;

import android.util.Log;

public class Gameboard {
	
	public static final int COLUMNS = 9;
	
	private final String TAG = "Gameboard";
	
	private int rows = 3;
	
	private ArrayList<ArrayList<NumberSquare>> board;
	
	public Gameboard() {
		board = new ArrayList<ArrayList<NumberSquare>>(rows);
		board.ensureCapacity(rows);
		for (int i = 0; i <= 2; i++) {
			board.add(i, new ArrayList<NumberSquare>(COLUMNS));
		}
		populateDefaultBoard();
	}
	
	public NumberSquare getNumberSquare(int row, int column) {
		return board.get(row).get(column);
	}
	
	public byte getSquareValue(int row, int column) {
		return board.get(row).get(column).getValue();
	}
	
	public int getRows() {
		return rows;
	}
	
	private void populateDefaultBoard() {
		for (int i = 0; i < 9; i++) {
			board.get(0).add(i, new NumberSquare((byte) (i + 1), 0, i));
			if (i % 2 == 0) {
				board.get(1).add(i, new NumberSquare((byte) 1, 1, i));
			} else {
				switch (i) {
					case 1:
						board.get(1).add(i, new NumberSquare((byte) 1, 1, i));
						break;
					case 3:
						board.get(1).add(i, new NumberSquare((byte) 2, 1, i));
						break;
					case 5:
						board.get(1).add(i, new NumberSquare((byte) 3, 1, i));
						break;
					case 7:
						board.get(1).add(i, new NumberSquare((byte) 4, 1, i));
						break;
				}
			}
			if (i % 2 != 0) {
				board.get(2).add(i, new NumberSquare((byte) 1, 2, i));
			} else {
				switch (i) {
					case 0:
						board.get(2).add(i, new NumberSquare((byte) 5, 2, i));
						break;
					case 2:
						board.get(2).add(i, new NumberSquare((byte) 6, 2, i));
						break;
					case 4:
						board.get(2).add(i, new NumberSquare((byte) 7, 2, i));
						break;
					case 6:
						board.get(2).add(i, new NumberSquare((byte) 8, 2, i));
						break;
					case 8:
						board.get(2).add(i, new NumberSquare((byte) 9, 2, i));
						break;
				}
			}
		}
	}
	
	public List<NumberSquare> getAdjacentSquares(int row, int column) {
		ArrayList<NumberSquare> result = new ArrayList<NumberSquare>(4);
		// Top adjacent
		if (row > 0) {
			result.add(getNumberSquare(row - 1, column));
			Log.d(TAG, "Added top adjacent to coordinates (" + (row - 1) + ", " + column +")");
		}
		// Left adjacent
		if (column > 0) {
			result.add(getNumberSquare(row, column - 1));
			Log.d(TAG, "Added left adjacent to coordinates (" + row + ", " + (column - 1) +")");
		}
		else if (column == 0 && row > 0) {
			result.add(getNumberSquare(row - 1, (COLUMNS - 1)));
			Log.d(TAG, "Added left adjacent to coordinates (" + (row - 1) + ", " + (COLUMNS - 1) +")");
		}
		// Bottom adjacent
		if (row < rows - 1) {
			result.add(getNumberSquare(row + 1, column));
			Log.d(TAG, "Added bottom adjacent to coordinates (" + (row + 1) + ", " + column +")");
		}
		// Right adjacent	
		if (column < (COLUMNS - 1)) {
			result.add(getNumberSquare(row, column + 1));
			Log.d(TAG, "Added right adjacent to coordinates (" + row + ", " + (column - 1) +")");
		}
		else if (column == (COLUMNS - 1) && row < rows - 1) {
			result.add(getNumberSquare(row + 1, 0));
			Log.d(TAG, "Added right adjacent to coordinates (" + (row + 1) + ", 0)");
		}
		return result;
	}
	
	public int[][] getAdjacentSquaresArray(int row, int column) {
		List<NumberSquare> list = getAdjacentSquares(row, column);
		Log.d(TAG, "Got " + list.size() + " adjacent squares");
		int[][] result = new int[0][0];
		if (list != null) {
			result = new int[list.size()][2];
			for (int i = 0; i < result.length; i++) {
				result[i][0] = list.get(i).getRow();
				result[i][1] = list.get(i).getColumn();
				Log.d(TAG, "Set square (" + result[i][0] + ", " + result[i][1] + ") as HIGHLIGHTED");
			}
		}
		return result;
	}
	
	public void displayBoard() {
		StringBuilder rowString;
		for (int i = 0; i < rows; i++) {
			rowString = new StringBuilder(9);
			for (int j = 0; j < 9; j++) {
				rowString.append(getSquareValue(i, j));
			}
			Log.d(TAG, rowString.toString());
		}
	}

}
