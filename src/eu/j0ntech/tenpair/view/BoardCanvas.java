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

	public final int COLOR_DEFAULT_SQUARE = Color.WHITE;
	public final int COLOR_SELECTED_SQUARE = Color.rgb(135, 206, 250);
	public final int COLOR_HIGHLIGHTED_SQUARE = Color.GREEN;
	public final int COLOR_SCRATCHED_SQUARE = Color.RED;
	public final int COLOR_NUMBER = Color.BLACK;
	public final int COLOR_BACKGROUND = Color.DKGRAY;

	private final String TAG = "BoardCanvas";

	private GestureDetector mGestureDetector;

	private GameActivity mParent;
	private Paint mBackPaint;

	private int resolutionX;
	private int resolutionY;

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
		board.displayBoard();
		mBackPaint.setStyle(Paint.Style.FILL);
		mBackPaint.setColor(COLOR_BACKGROUND);
		canvas.drawPaint(mBackPaint);

		for (int j = 0; j < board.getRows(); j++) {
			float startY = j * tileSize;
			for (int i = 0; i < GameBoard.COLUMNS; i++) {
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
			Log.d(TAG, "Clearing highlights");
			GameBoard board = mParent.getGameboard();
			for (int i = 0; i < curHighlights.length; i++) {
				board.getTile(curHighlights[i][0], curHighlights[i][1])
						.setType(TileType.DEFAULT);
			}
		}
	}

	/**
	 * Sets squares in the curHighlights array as highlighted
	 */
	private void setHighlights() {
		if (curHighlights != null) {
			Log.d(TAG, "Setting highlights");
			GameBoard board = mParent.getGameboard();
			for (int i = 0; i < curHighlights.length; i++) {
				board.getTile(curHighlights[i][0], curHighlights[i][1])
						.setType(TileType.HIGHLIGHTED);
				// Log.d(TAG, "Set square (" + curHighlights[i][0] + ", " +
				// curHighlights[i][1] + ") as HIGHLIGHTED");
			}
		}
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
		public boolean onDown(MotionEvent event) {
			Log.d(TAG, "Got motionevent");
			GameBoard board = mParent.getGameboard();
			int squareRow = getSquareRow(event);
			int squareColumn = getSquareColumn(event);
			if ((squareRow >= 0 && squareRow < board.getRows())
					&& (squareColumn >= 0 && squareColumn < GameBoard.COLUMNS)) {
				if (board.getTile(squareRow, squareColumn).getType() == TileType.SCRATCHED) {
					invalidate();
					return true;
				}
				if (squareRow == lastSelectedRow
						&& squareColumn == lastSelectedColumn
						&& board.getTile(lastSelectedRow, lastSelectedColumn)
								.getType() == TileType.SELECTED) {
					board.getTile(squareRow, squareColumn).setType(
							TileType.DEFAULT);
					clearHighlights();
					invalidate();
					return true;
				}
				if (board.getTile(lastSelectedRow, lastSelectedColumn)
						.getType() != TileType.SCRATCHED)
					board.getTile(lastSelectedRow, lastSelectedColumn).setType(
							TileType.DEFAULT);
				clearHighlights();
				lastSelectedRow = squareRow;
				lastSelectedColumn = squareColumn;
				board.getTile(squareRow, squareColumn).setType(
						TileType.SELECTED);
				curHighlights = board.getAdjacentTilesArray(squareRow,
						squareColumn);
				setHighlights();
				invalidate();
				return true;
			}
			return false;
		}

		@Override
		public void onLongPress(MotionEvent event) {
			GameBoard board = mParent.getGameboard();
			int squareRow = getSquareRow(event);
			int squareColumn = getSquareColumn(event);
			if ((squareRow >= 0 && squareRow < board.getRows())
					&& (squareColumn >= 0 && squareColumn < GameBoard.COLUMNS)) {
				board.getTile(squareRow, squareColumn)
						.setType(
								board.getTile(squareRow, squareColumn)
										.getType() != TileType.SCRATCHED ? TileType.SCRATCHED
										: TileType.DEFAULT);
			}
			invalidate();
		}

		@Override
		public boolean onScroll(MotionEvent e1, MotionEvent e2,
				float distanceX, float distanceY) {
			// TODO Scrolling
			return super.onScroll(e1, e2, distanceX, distanceY);
		}

	}

}
