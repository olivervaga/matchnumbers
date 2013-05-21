package eu.j0ntech.tenpair.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;
import eu.j0ntech.tenpair.R;
import eu.j0ntech.tenpair.activity.GameActivity;
import eu.j0ntech.tenpair.game.GameBoard;
import eu.j0ntech.tenpair.game.Tile;
import eu.j0ntech.tenpair.game.Tile.TileType;

/**
 * The canvas that displays the game area
 * 
 * @author Oliver Vaga
 * 
 */
public class BoardView extends View {

	public final int COLOR_BACKGROUND = Color.DKGRAY;

	private GestureDetector mGestureDetector;

	private GameActivity mParent;
	
	private TileDrawer mTileDrawer;
	private BackgroundDrawer mBackgroundDrawer;

	private int resolutionX;
	private int canvasHeight;
	private float offset = 0;

	private boolean tileSelected = false;
	private int lastSelectedRow;
	private int lastSelectedColumn;
	private int[][] curHighlights;

	public static int SQUARE_PADDING = 5;

	private float tileSize;
	private float boardSize;

	public BoardView(Context context) {
		super(context);
		initCanvas(context);
	}

	public BoardView(Context context, AttributeSet attrs) {
		super(context, attrs);
		initCanvas(context);

	}

	public BoardView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		initCanvas(context);

	}

	/**
	 * Initializes Paint objects, the GestureDetector and detects the screen
	 * size
	 * 
	 * @param context
	 *            Parent activity
	 */
	private void initCanvas(Context context) {
		mParent = (GameActivity) context;
		mGestureDetector = new GestureDetector(context, new GestureListener());
		WindowManager wm = (WindowManager) context
				.getSystemService(Context.WINDOW_SERVICE);
		DisplayMetrics displayMetrics = new DisplayMetrics();
		wm.getDefaultDisplay().getMetrics(displayMetrics);
		resolutionX = displayMetrics.widthPixels;
		tileSize = (resolutionX) / GameBoard.COLUMNS - 1;
		mTileDrawer = new TileDrawer(this);
		mBackgroundDrawer = new BackgroundDrawer(this);
	}

	public void resetCanvas() {
		offset = 0;
		tileSelected = false;
		curHighlights = null;
		invalidate();
	}

	/**
	 * Draws the squares, numbers, colors things according to status
	 */
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		GameBoard board = mParent.getGameBoard();
		// board.displayBoard();
		mBackgroundDrawer.draw(canvas, board.getRows(), offset);

		for (int j = 0; j < board.getRows(); j++) {
			float startY = j * tileSize;
			for (int i = 0; i < board.getRowSize(j); i++) {
				float startX = i * tileSize;
				Tile tempTile = board.getTile(j, i);
				tempTile.setCoordinates(startX + SQUARE_PADDING, startY
						+ SQUARE_PADDING, startX + tileSize, startY + tileSize);
				mTileDrawer.draw(tempTile, canvas, offset);
			}
		}

	}

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		canvasHeight = h;
	}

	/**
	 * Sends the touch event to the GestureDetector
	 */
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		return mGestureDetector.onTouchEvent(event);
	}

	/**
	 * Sets squares in the curHighlights array as default
	 */
	private void clearHighlights() {
		if (curHighlights != null) {
			GameBoard board = mParent.getGameBoard();
			for (int i = 0; i < curHighlights.length; i++) {
				board.getTile(curHighlights[i][0], curHighlights[i][1])
						.setTypeSafely(TileType.DEFAULT);
			}
		}
	}

	/**
	 * Sets squares in the curHighlights array as highlighted
	 */
	private void setHighlights() {
		if (curHighlights != null) {
			GameBoard board = mParent.getGameBoard();
			for (int i = 0; i < curHighlights.length; i++) {
				board.getTile(curHighlights[i][0], curHighlights[i][1])
						.setTypeSafely(TileType.HIGHLIGHTED);
			}
		}
	}

	private boolean isTouchEventWithinBoard(MotionEvent event, GameBoard board) {
		int squareRow = getTileRow(event);
		int squareColumn = getTileColumn(event);
		if ((squareRow >= 0 && squareRow < board.getRows())
				&& (squareColumn >= 0 && squareColumn < GameBoard.COLUMNS))
			return true;
		else
			return false;
	}

	private void clearSelection(GameBoard board) {
		tileSelected = false;
		board.getTile(lastSelectedRow, lastSelectedColumn).setTypeSafely(
				TileType.DEFAULT);
		clearHighlights();
	}

	private void selectTile(GameBoard board, Tile targetTile) {
		tileSelected = true;
		lastSelectedRow = targetTile.getRow();
		lastSelectedColumn = targetTile.getColumn();
		board.getTile(lastSelectedRow, lastSelectedColumn).setTypeSafely(
				TileType.SELECTED);
		curHighlights = board.getAdjacentTilesArray(lastSelectedRow,
				lastSelectedColumn);
		setHighlights();
	}

	public float getTileSize() {
		return tileSize;
	}

	private int getTileRow(MotionEvent event) {
		return (int) ((event.getY() - offset) / tileSize);
	}

	private int getTileColumn(MotionEvent event) {
		return (int) (event.getX() / tileSize);
	}

	public void recalculateBoardSize() {
		boardSize = mParent.getGameBoard().getRows()
				* tileSize + SQUARE_PADDING + 2;
	}

	public int getCanvasHeight() {
		return canvasHeight;
	}

	public void setCanvasHeight(int canvasHeight) {
		this.canvasHeight = canvasHeight;
	}

	/**
	 * The GestureListener class that consumes to touch events
	 * 
	 * @author Oliver Vaga
	 * 
	 */
	private class GestureListener extends
			GestureDetector.SimpleOnGestureListener {

		@Override
		public boolean onDown(MotionEvent e) {
			return true;
		}

		@Override
		public boolean onSingleTapUp(MotionEvent event) {
			GameBoard board = mParent.getGameBoard();
			int squareRow = getTileRow(event);
			int squareColumn = getTileColumn(event);

			if (!isTouchEventWithinBoard(event, board)) {
				return true;
			}
			Tile targetTile = board.getTile(squareRow, squareColumn);
			if (targetTile.getType() == TileType.SCRATCHED)
				return true;

			if (!tileSelected) {
				selectTile(board, targetTile);
				invalidate();
				return true;
			} else {
				if (lastSelectedColumn == squareColumn
						&& lastSelectedRow == squareRow) {
					clearSelection(board);
					invalidate();
					return true;
				} else {
					if (targetTile.getType() == TileType.DEFAULT) {
						clearSelection(board);
						selectTile(board, targetTile);
						invalidate();
						return true;
					} else {
						if (board.validateMove(board.getTile(lastSelectedRow,
								lastSelectedColumn), targetTile)) {
							board.makeMove(board.getTile(lastSelectedRow,
									lastSelectedColumn), targetTile);
							clearSelection(board);
							invalidate();
							return true;
						} else {
							ToastUtil.showToast(R.string.error_illegal_move, Toast.LENGTH_SHORT, mParent);
						}
					}
				}
			}
			return false;
		}

		@Override
		public boolean onScroll(MotionEvent e1, MotionEvent e2,
				float distanceX, float distanceY) {
			if (boardSize < BoardView.this.getHeight()) {
				return true;
			}
			offset -= distanceY;
			if (offset > 0)
				offset = 0;
			if ((offset - canvasHeight) <= -boardSize)
				offset = canvasHeight - boardSize;
			invalidate();
			return true;
		}
	}
}
