package eu.j0ntech.tenpair.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import eu.j0ntech.tenpair.activity.GameActivity;
import eu.j0ntech.tenpair.game.Gameboard;
import eu.j0ntech.tenpair.game.NumberSquare;

public class BoardCanvas extends View {
	
	private final int COLOR_DEFAULT_SQUARE = Color.WHITE;
	private final int COLOR_SELECTED_SQUARE = Color.rgb(135,206,250); // Light blue
	private final int COLOR_HIGHLIGHTED_SQUARE = Color.GREEN;
	private final int COLOR_NUMBER = Color.BLACK;
	private final int COLOR_BACKGROUND = Color.DKGRAY;
	
	private final String TAG = "BoardCanvas";
	
	private GameActivity mParent;
	private Paint mRectPaint;
	private Paint mNumberPaint;
	private Paint mLinePaint;
	
	private int resolutionX;
	private int resolutionY;
	
	private int lastSelectedRow;
	private int lastSelectedColumn;
	private int[][] curHighlights;
	
	private static int SQUARE_PADDING = 5;
	
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
	
	private void initCanvas(Context context) {
		mParent = (GameActivity) context;
		WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
		DisplayMetrics displayMetrics = new DisplayMetrics();
		wm.getDefaultDisplay().getMetrics(displayMetrics);
		resolutionX = displayMetrics.widthPixels;
		Log.d(TAG, "Pixels X: " + resolutionX);
		resolutionY = displayMetrics.heightPixels;
		tileSize = (resolutionX) / 9;
		Log.d(TAG, "Tile size: " + tileSize);
		mRectPaint = new Paint();
		mNumberPaint = new Paint();
		mLinePaint = new Paint();
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		Gameboard board = mParent.getGameboard();
		mRectPaint.setStyle(Paint.Style.FILL);
		mRectPaint.setColor(COLOR_BACKGROUND);
		canvas.drawPaint(mRectPaint);
		
		mRectPaint.setStyle(Paint.Style.FILL);
		mRectPaint.setAntiAlias(true);
		
		mLinePaint.setStyle(Paint.Style.STROKE);
		mLinePaint.setColor(COLOR_NUMBER);
		mLinePaint.setStrokeWidth(3f);
		mLinePaint.setAntiAlias(true);
		
		mNumberPaint.setStyle(Paint.Style.STROKE);
		mNumberPaint.setColor(COLOR_NUMBER);
		mNumberPaint.setAntiAlias(true);
		mNumberPaint.setTextAlign(Paint.Align.CENTER);
		mNumberPaint.setTextSize(tileSize - SQUARE_PADDING);
		for (int j = 0; j < board.getRows(); j++) {
			float startY = j * tileSize;
			for (int i = 0; i < Gameboard.COLUMNS; i++) {
				float startX = i * tileSize;
				NumberSquare tempSquare = board.getNumberSquare(j, i);
				if (tempSquare.isSelected()) {
					mRectPaint.setColor(COLOR_SELECTED_SQUARE);
//					Log.d(TAG, "Square is SELECTED");
				} else if (tempSquare.isHighlighted()){
					mRectPaint.setColor(COLOR_HIGHLIGHTED_SQUARE);
//					Log.d(TAG, "Square is HIGHLIGHTED");
				} else {
					mRectPaint.setColor(COLOR_DEFAULT_SQUARE);
//					Log.d(TAG, "Square is DEFAULT");
				}
				tempSquare.setCoordinates(startX + SQUARE_PADDING, 
						startY + SQUARE_PADDING,
						startX + tileSize,
						startY + tileSize);
				canvas.drawRect(tempSquare.getStartX(), tempSquare.getStartY(),
					      tempSquare.getEndX(), tempSquare.getEndY(), mRectPaint);
				canvas.drawText(String.valueOf(tempSquare.getValue()), tempSquare.getCenterX(), (float) (tempSquare.getCenterY()
						+ (mNumberPaint.getTextSize()) / 2.6), mNumberPaint);
				if (tempSquare.isScratched()) {
					canvas.drawLine(startX + SQUARE_PADDING, startY + SQUARE_PADDING, startX + tileSize,
							startY + tileSize, mLinePaint);
					canvas.drawLine(startX + tileSize, startY + SQUARE_PADDING,
							startX + SQUARE_PADDING, startY + tileSize, mLinePaint);
				}
			}	
		}
		
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if (event.getActionMasked() == MotionEvent.ACTION_DOWN) {
			Gameboard board = mParent.getGameboard();
			int squareRow = (int) (event.getY() / tileSize);
			int squareColumn = (int) (event.getX() / tileSize);
			if ((squareRow >= 0 && squareRow < board.getRows())
					&& (squareColumn >= 0 && squareColumn < Gameboard.COLUMNS)) {
				if (squareRow == lastSelectedRow && squareColumn == lastSelectedColumn
						&& board.getNumberSquare(lastSelectedRow, lastSelectedColumn).isSelected()) {
					board.getNumberSquare(squareRow, squareColumn).setSelected(false);
					clearHighlights();
					invalidate();
					return true;
				}
				board.getNumberSquare(lastSelectedRow, lastSelectedColumn).setSelected(false);
				clearHighlights();
				lastSelectedRow = squareRow;
				lastSelectedColumn = squareColumn;
				board.getNumberSquare(squareRow, squareColumn).setSelected(true);
				curHighlights = board.getAdjacentSquaresArray(squareRow, squareColumn);
				setHighlights();
				invalidate();
				return true;
			} else {
				return false;
			}			
		}
		else return false;
	}
	
	private void clearHighlights() {
		if (curHighlights != null) {
			Gameboard board = mParent.getGameboard();
			for (int i = 0; i < curHighlights.length; i++) {
				board.getNumberSquare(curHighlights[i][0], curHighlights[i][1]).setHighlighted(false);
			}			
		}
	}
	
	private void setHighlights() {
		if (curHighlights != null) {
			Gameboard board = mParent.getGameboard();
			for (int i = 0; i < curHighlights.length; i++) {
				board.getNumberSquare(curHighlights[i][0], curHighlights[i][1]).setHighlighted(true);
				Log.d(TAG, "Set square (" + curHighlights[i][0] + ", " + curHighlights[i][1] + ") as HIGHLIGHTED");
			}			
		}
	}

}
