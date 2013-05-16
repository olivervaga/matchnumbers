package eu.j0ntech.tenpair.game;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import eu.j0ntech.tenpair.activity.GameActivity;
import eu.j0ntech.tenpair.game.Tile.TileType;

/**
 * Class for holding all the game-related data and logic
 * 
 * @author Oliver Vaga
 * 
 */
public class GameBoard {

	/**
	 * Maximum number of columns on the board
	 */
	public static final int COLUMNS = 9;

	private final int INITIAL_ROWS = 3;

	/**
	 * Representation of the game board, a two-dimensional ArrayList containing
	 * all the NumberSquares
	 */
	private ArrayList<ArrayList<Tile>> board;

	private int currentTiles = 0;

	private int currentScratched = 0;

	private BoardChangeListener mBoardChangeListener;

	/**
	 * Creates a new GameBoard and populates it with the default values
	 */
	public GameBoard(BoardChangeListener listener) {
		board = new ArrayList<ArrayList<Tile>>(INITIAL_ROWS);
		for (int i = 0; i <= 2; i++) {
			board.add(i, new ArrayList<Tile>(COLUMNS));
		}
		mBoardChangeListener = listener;
		populateDefaultBoard();
		mBoardChangeListener.onBoardChanged(getRemainingCount());
	}

	public GameBoard(BoardChangeListener listener,
			ArrayList<ArrayList<Tile>> savedState) {
		board = savedState;
		currentTiles = (board.size() - 1) * 9
				+ board.get(board.size() - 1).size();
		for (ArrayList<Tile> row : board)
			for (Tile t : row)
				if (t.getType() == TileType.SCRATCHED)
					currentScratched++;
		mBoardChangeListener = listener;
		((GameActivity) listener).runOnUiThread(new Runnable() {

			@Override
			public void run() {
				mBoardChangeListener.onBoardChanged(getRemainingCount());
				// TODO Auto-generated method stub
			}
		});
	}

	public Tile getTile(int row, int column) {
		return board.get(row).get(column);
	}

	public byte getTileValue(int row, int column) {
		return board.get(row).get(column).getValue();
	}

	/**
	 * Populates the board with the default starting values for the game
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
		currentTiles = INITIAL_ROWS * COLUMNS;
	}

	/**
	 * Finds all the legally highlightable adjacent tiles (scratched tiles can't
	 * be highlighted) BUGGY
	 * 
	 * @param row
	 *            Row of the selected tile
	 * @param column
	 *            Column of the selected tile
	 * @return An ArrayList of highlighted Tile objects
	 */
	public List<Tile> getAdjacentTiles(int row, int column) {
		ArrayList<Tile> result = new ArrayList<Tile>(4);
		// Top adjacent
		for (int i = row - 1; i >= 0; i--) {
			if (getTile(i, column).getType() != TileType.SCRATCHED) {
				result.add(getTile(i, column));
				break;
			}
		}
		// Left adjacent
		leftOuter: if (column == 0) {
			for (int i = row - 1; i >= 0; i--) {
				for (int j = COLUMNS - 1; j >= 0; j--) {
					if (getTile(i, j).getType() != TileType.SCRATCHED) {
						result.add(getTile(i, j));
						break leftOuter;
					}
				}
			}
		} else {
			int initialRow = row;
			for (int i = row; i >= 0; i--) {
				int startColumn;
				if (i < initialRow)
					startColumn = COLUMNS - 1;
				else
					startColumn = column - 1;
				for (int j = startColumn; j >= 0; j--) {
					if (getTile(i, j).getType() != TileType.SCRATCHED) {
						result.add(getTile(i, j));
						break leftOuter;
					}
				}
			}
		}
		// Bottom adjacent
		for (int i = row + 1; i < board.size(); i++) {
			if (column >= board.get(i).size())
				break;
			if (getTile(i, column).getType() != TileType.SCRATCHED) {
				result.add(getTile(i, column));
				break;
			}
		}
		// Right adjacent
		rightOuter: if (column + 1 == COLUMNS) {
			for (int i = row + 1; i < board.size(); i++) {
				for (int j = 0; j < board.get(i).size(); j++) {
					if (getTile(i, j).getType() != TileType.SCRATCHED) {
						result.add(getTile(i, j));
						break rightOuter;
					}
				}
			}
		} else {
			int initialRow = row;
			for (int i = row; i < board.size(); i++) {
				int startColumn;
				if (i > initialRow)
					startColumn = 0;
				else
					startColumn = column + 1;
				for (int j = startColumn; j < board.get(i).size(); j++) {
					if (getTile(i, j).getType() != TileType.SCRATCHED) {
						result.add(getTile(i, j));
						break rightOuter;
					}
				}
			}
		}
		return result;
	}

	/**
	 * Finds all the legally highlightable adjacent tiles (scratched tiles can't
	 * be highlighted) BUGGY
	 * 
	 * @param row
	 *            Row of the selected tile
	 * @param column
	 *            Column of the selected tile
	 * @return A two-dimensional integer array with the rows and columns of
	 *         highlighted tiles
	 */
	public int[][] getAdjacentTilesArray(int row, int column) {
		List<Tile> list = getAdjacentTiles(row, column);
		int[][] result = new int[0][0];
		if (list != null) {
			result = new int[list.size()][2];
			for (int i = 0; i < result.length; i++) {
				result[i][0] = list.get(i).getRow();
				result[i][1] = list.get(i).getColumn();
			}
		}
		return result;
	}

	public void addUnunusedTiles() {
		int newTiles = currentTiles - currentScratched;
		int currentRows = board.size();
		ArrayList<Tile> unusedTiles = new ArrayList<Tile>();
		ArrayList<Tile> row;
		for (int i = 0; i < board.size(); i++) {
			row = board.get(i);
			for (int j = 0; j < row.size(); j++) {
				if (row.get(j).getType() != TileType.SCRATCHED) {
					unusedTiles.add(row.get(j));
				}
			}
		}
		int addRows;
		if (((currentTiles % COLUMNS == 0) && (newTiles % COLUMNS > 0))
				|| ((currentTiles % COLUMNS) + (newTiles % COLUMNS)) > COLUMNS)
			addRows = newTiles / COLUMNS + 1;
		else
			addRows = newTiles / COLUMNS;
		for (int i = 0; i < addRows; i++) {
			board.add(new ArrayList<Tile>(COLUMNS));
		}
		Iterator<Tile> iterator = unusedTiles.iterator();
		for (int i = currentRows - 1; i < currentRows + addRows; i++) {
			int startColumn;
			if (board.get(i).size() != 0)
				startColumn = board.get(i).size();
			else
				startColumn = 0;
			for (int j = startColumn; j < COLUMNS; j++) {
				if (!iterator.hasNext())
					break;
				Tile tempTile = new Tile(iterator.next().getValue(), i, j);
				board.get(i).add(tempTile);
				currentTiles++;
			}
		}
		mBoardChangeListener.onBoardChanged(getRemainingCount());
	}

	public boolean validateMove(Tile tile1, Tile tile2) {
		if (tile1.getValue() == tile2.getValue()
				|| tile1.getValue() + tile2.getValue() == 10)
			return true;
		else
			return false;
	}

	public void makeMove(Tile tile1, Tile tile2) {
		tile1.setType(TileType.SCRATCHED);
		tile2.setType(TileType.SCRATCHED);
		currentScratched += 2;
		mBoardChangeListener.onBoardChanged(getRemainingCount());
		if (isGameWon())
			mBoardChangeListener.onGameWon();
	}

	public boolean isGameWon() {
		return (currentTiles - currentScratched) == 0 ? true : false;
	}

	public int getRows() {
		return board.size();
	}

	public int getRowSize(int row) {
		return board.get(row).size();
	}

	public ArrayList<ArrayList<Tile>> getInternalBoard() {
		return board;
	}

	public int getRemainingCount() {
		return currentTiles - currentScratched;
	}

	/**
	 * Displays the current game board state in LogCat (for debugging purposes),
	 * scratched squares have trailing asterisk
	 */
	public void displayBoard() {
		StringBuilder rowString;
		for (int i = 0; i < board.size(); i++) {
			rowString = new StringBuilder(9);
			for (int j = 0; j < getRowSize(i); j++) {
				rowString.append(getTileValue(i, j));
				if (getTile(i, j).getType() == TileType.SCRATCHED)
					rowString.append("*");
				else
					rowString.append(" ");
				rowString.append(" ");
			}
		}
	}

	public interface BoardChangeListener {
		public void onBoardChanged(int newCount);

		public void onGameWon();
	}
}
