package eu.j0ntech.tenpair.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;
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
public class BoardCanvas extends View {

	public final int COLOR_BACKGROUND = Color.DKGRAY;

	private final String TAG = "BoardCanvas";

	private GestureDetector mGestureDetector;

	private GameActivity mParent;
	private Paint mBackPaint;

	private int resolutionX;
	private int resolutionY;

	private boolean tileSelected = false;
	private int lastSelectedRow;
	private int lastSelectedColumn;
	private int[][] curHighlights;

	public static int SQUARE_PADDING = 5;

	private float tileSize;

	public BoardCanvas(Context context) {
		super(context);
		initCanvas(context);
	}

	public BoardCanvas(Context context, AttributeSet attrs) {
		super(context, attrs);
		initCanvas(context);

	}

	public BoardCanvas(Context context, AttributeSet attrs, int defStyle) {
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
		Log.d(TAG, "Pixels X: " + resolutionX);
		resolutionY = displayMetrics.heightPixels;
		tileSize = (resolutionX) / 9;
		Log.d(TAG, "Tile size: " + tileSize);
		mBackPaint = new Paint();
	}

	/**
	 * Draws the squares, numbers, colors things according to status
	 */
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		GameBoard board = mParent.getGameboard();
		// board.displayBoard();
		mBackPaint.setStyle(Paint.Style.FILL);
		mBackPaint.setColor(COLOR_BACKGROUND);
		canvas.drawPaint(mBackPaint);

		for (int j = 0; j < board.getRows(); j++) {
			float startY = j * tileSize;
			for (int i = 0; i < board.getRowSize(j); i++) {
				float startX = i * tileSize;
				Tile tempTile = board.getTile(j, i);
				tempTile.setCoordinates(startX + SQUARE_PADDING, startY
						+ SQUARE_PADDING, startX + tileSize, startY + tileSize);
				TileDrawer.draw(this, canvas, tempTile);
			}
		}

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
			GameBoard board = mParent.getGameboard();
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
			GameBoard board = mParent.getGameboard();
			for (int i = 0; i < curHighlights.length; i++) {
				board.getTile(curHighlights[i][0], curHighlights[i][1])
						.setTypeSafely(TileType.HIGHLIGHTED);
			}
		}
	}

	private boolean isTouchEventWithinBoard(MotionEvent event, GameBoard board) {
		int squareRow = getSquareRow(event);
		int squareColumn = getSquareColumn(event);
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

	private int getSquareRow(MotionEvent event) {
		return (int) (event.getY() / tileSize);
	}

	private int getSquareColumn(MotionEvent event) {
		return (int) (event.getX() / tileSize);
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
			Log.d(TAG, "Got motionevent");

			GameBoard board = mParent.getGameboard();
			int squareRow = getSquareRow(event);
			int squareColumn = getSquareColumn(event);

			if (!isTouchEventWithinBoard(event, board))
				return true;
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
						Log.d(TAG, "Tile selected, touched highlighted tile");
						if (board.validateMove(board.getTile(lastSelectedRow,
								lastSelectedColumn), targetTile)) {
							board.makeMove(board.getTile(lastSelectedRow,
									lastSelectedColumn), targetTile);
							clearSelection(board);
							invalidate();
							return true;
						} else {
							Toast.makeText(mParent, "Illegal move", Toast.LENGTH_SHORT).show();
						}
					}
				}
			}
			return false;
		}

		@Override
		public boolean onScroll(MotionEvent e1, MotionEvent e2,
				float distanceX, float distanceY) {
			// TODO Scrolling
			return super.onScroll(e1, e2, distanceX, distanceY);
		}

	}

}
