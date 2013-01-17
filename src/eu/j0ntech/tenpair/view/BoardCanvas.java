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
	
	private GameActivity mParent;
	private Paint mRectPaint;
	private Paint mNumberPaint;
	
	private int resolutionX;
	private int resolutionY;
	
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
		mRectPaint.setStyle(Paint.Style.FILL);
		mRectPaint.setColor(Color.DKGRAY);
		canvas.drawPaint(mRectPaint);
		
		mRectPaint.setStyle(Paint.Style.FILL);
		mRectPaint.setColor(Color.WHITE);
		mRectPaint.setAntiAlias(true);
		mNumberPaint.setStyle(Paint.Style.STROKE);
		mNumberPaint.setColor(Color.BLACK);
		mNumberPaint.setAntiAlias(true);
		mNumberPaint.setTextAlign(Paint.Align.CENTER);
		mNumberPaint.setTextSize(tileSize - 5);
		float startX;
		float startY;
		NumberSquare tempSquare;
		for (int j = 0; j < mParent.getGameboard().getRows(); j++) {
			startY = j * tileSize;
			for (int i = 0; i < Gameboard.COLUMNS; i++) {
				startX = i * tileSize;
				System.out.println("X = " + startX + ", Y = " + startY);
				mParent.getGameboard().getNumberSquare(j, i).setCoordinates(startX + SQUARE_PADDING, 
						startY + SQUARE_PADDING,
						startX + tileSize,
						startY + SQUARE_PADDING + tileSize);
				tempSquare = mParent.getGameboard().getNumberSquare(j, i);
				canvas.drawRect(startX + SQUARE_PADDING, startY + SQUARE_PADDING, startX + tileSize,
						startY + tileSize, mRectPaint);
				System.out.println("Drawing \"" + tempSquare.getValue() + "\" at position ("  + tempSquare.getCenterX() + ", " + tempSquare.getCenterY() + "), memloc = " + tempSquare.toString());
				canvas.drawText(String.valueOf(tempSquare.getValue()), tempSquare.getCenterX(), tempSquare.getCenterY(), mNumberPaint);
			}	
		}
		
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if (event.getActionMasked() == MotionEvent.ACTION_DOWN) {
			System.out.println(event.getX() + ", " + event.getY());
			return true;			
		}
		else return false;
	}

}
