package eu.j0ntech.tenpair.game;

import java.util.ArrayList;

public class Gameboard {
	
	public static final int COLUMNS = 9;
	
	private static final byte SCRATCHED_NUMBER = 0;
	
	private static final byte EMPTY_SPACE = -1;
	
	private int rows = 3;
	
	private ArrayList<ArrayList<NumberSquare>> board;
	
	public Gameboard() {
		board = new ArrayList<ArrayList<NumberSquare>>(rows);
		board.ensureCapacity(rows);
		System.out.println(board.size());
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
			board.get(0).add(i, new NumberSquare((byte) (i + 1)));
			if (i % 2 == 0) {
				board.get(1).add(i, new NumberSquare((byte) 1));
			} else {
				switch (i) {
					case 1:
						board.get(1).add(i, new NumberSquare((byte) 1));
						break;
					case 3:
						board.get(1).add(i, new NumberSquare((byte) 2));
						break;
					case 5:
						board.get(1).add(i, new NumberSquare((byte) 3));
						break;
					case 7:
						board.get(1).add(i, new NumberSquare((byte) 4));
						break;
				}
			}
			if (i % 2 != 0) {
				board.get(2).add(i, new NumberSquare((byte) 1));
			} else {
				switch (i) {
					case 0:
						board.get(2).add(i, new NumberSquare((byte) 5));
						break;
					case 2:
						board.get(2).add(i, new NumberSquare((byte) 6));
						break;
					case 4:
						board.get(2).add(i, new NumberSquare((byte) 7));
						break;
					case 6:
						board.get(2).add(i, new NumberSquare((byte) 8));
						break;
					case 8:
						board.get(2).add(i, new NumberSquare((byte) 9));
						break;
				}
			}
		}
	}
	
	public void setNumberAsScratched(int row, int column) {
		//TODO
	}
	
	public void displayBoard() {
		StringBuilder rowString;
		for (int i = 0; i < rows; i++) {
			rowString = new StringBuilder(9);
			for (int j = 0; j < 9; j++) {
				rowString.append(getSquareValue(i, j));
			}
			System.out.println(rowString.toString());
		}
	}

}
