package eu.j0ntech.tenpair.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
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
	
	private GameActivity mParent;
	private Paint mRectPaint;
	private Paint mNumberPaint;
	
	private int resolutionX;
	private int resolutionY;
	
	private int lastSelectedRow;
	private int lastSelectedColumn;
	
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
		System.out.println("Pixels X: " + resolutionX);
		resolutionY = displayMetrics.heightPixels;
		tileSize = (resolutionX) / 9;
		System.out.println("Tile size: " + tileSize);
		mRectPaint = new Paint();
		mNumberPaint = new Paint();
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
				} else {
					mRectPaint.setColor(COLOR_DEFAULT_SQUARE);
				}
				tempSquare.setCoordinates(startX + SQUARE_PADDING, 
						startY + SQUARE_PADDING,
						startX + tileSize,
						startY + tileSize);
				canvas.drawRect(tempSquare.getStartX(), tempSquare.getStartY(),
					      tempSquare.getEndX(), tempSquare.getEndY(), mRectPaint);
				canvas.drawText(String.valueOf(tempSquare.getValue()), tempSquare.getCenterX(), (float) (tempSquare.getCenterY()
						+ (mNumberPaint.getTextSize()) / 2.6), mNumberPaint);
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
					invalidate();
					return true;
				}
				board.getNumberSquare(lastSelectedRow, lastSelectedColumn).setSelected(false);
				lastSelectedRow = squareRow;
				lastSelectedColumn = squareColumn;
				board.getNumberSquare(squareRow, squareColumn).setSelected(true);
				invalidate();
				return true;
			} else {
				return false;
			}			
		}
		else return false;
	}

}
