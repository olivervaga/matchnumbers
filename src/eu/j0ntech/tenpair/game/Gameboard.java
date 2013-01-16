package eu.j0ntech.tenpair.game;

import java.util.ArrayList;

public class Gameboard {
	
	private static final int COLUMNS = 9;
	
	private static final byte SCRATCHED_NUMBER = 0;
	
	private static final byte EMPTY_SPACE = -1;
	
	private int rows = 3;
	
	private ArrayList<ArrayList<Byte>> board;
	
	public Gameboard() {
		board = new ArrayList<ArrayList<Byte>>(rows);
		board.ensureCapacity(rows);
		System.out.println(board.size());
		for (int i = 0; i <= 2; i++) {
			board.add(i, new ArrayList<Byte>(COLUMNS));
			System.out.println("Set row " + i);
		}
		populateDefaultBoard();
	}
	
	public int getNumber(int row, int column) {
		return (int) board.get(row).get(column);
	}
	
	private void populateDefaultBoard() {
		for (int i = 0; i < 9; i++) {
			board.get(0).add(i, (byte) (i + 1));
			if (i % 2 == 0) {
				board.get(1).add(i, (byte) 1);
			} else {
				switch (i) {
					case 1:
						board.get(1).add(i, (byte) 1);
						break;
					case 3:
						board.get(1).add(i, (byte) 2);
						break;
					case 5:
						board.get(1).add(i, (byte) 3);
						break;
					case 7:
						board.get(1).add(i, (byte) 4);
						break;
				}
			}
			if (i % 2 != 0) {
				board.get(2).add(i, (byte) 1);
			} else {
				switch (i) {
					case 0:
						board.get(2).add(i, (byte) 5);
						break;
					case 2:
						board.get(2).add(i, (byte) 6);
						break;
					case 4:
						board.get(2).add(i, (byte) 7);
						break;
					case 6:
						board.get(2).add(i, (byte) 8);
						break;
					case 8:
						board.get(2).add(i, (byte) 9);
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
				rowString.append(getNumber(i, j));
			}
			System.out.println(rowString.toString());
		}
	}

}
