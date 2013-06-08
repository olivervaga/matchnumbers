package eu.j0ntech.matchnumbers.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

public class ScrollBar extends View {
	
	public static final int COLOR_LINE = Color.BLACK;
	public static final int COLOR_SCROLL = Color.DKGRAY;
	public static final float SCROLL_HEIGHT = 40;
	public static final float SCROLL_WIDTH = 20;
	public static final float LINE_WIDTH = 2;
	
	private Paint mLinePaint;
	private Paint mScrollPaint;
	
	private int height;
	private float position;
	private float scrollOffset;
	
	public ScrollBar(Context context) {
		super(context);
		initScrollBar(context);
	}
	
	public ScrollBar(Context context, AttributeSet attrs) {
		super(context, attrs);
		initScrollBar(context);
	}

	public ScrollBar(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		initScrollBar(context);
		
	}
	
	private void initScrollBar(Context context) {
		mLinePaint = new Paint();
		mScrollPaint = new Paint();
		mLinePaint.setStyle(Paint.Style.STROKE);
		mLinePaint.setColor(COLOR_LINE);
		mLinePaint.setStrokeWidth(LINE_WIDTH);
		mLinePaint.setAntiAlias(true);
		mScrollPaint.setStyle(Paint.Style.STROKE);
		mScrollPaint.setColor(COLOR_SCROLL);
		mScrollPaint.setStrokeWidth(SCROLL_WIDTH);
		mScrollPaint.setAntiAlias(true);
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		canvas.drawLine(6, 0, 6, height, mLinePaint);
		canvas.drawLine(2, scrollOffset, 2, scrollOffset + SCROLL_HEIGHT, mScrollPaint);
	}
	
	public void drawScroll(float offset, float boardHeight) {
		int range = (int) boardHeight - height;
		position = -offset / range;
		scrollOffset = position * (height - SCROLL_HEIGHT);
		invalidate();
	}
	
	public void setHeight(int canvasHeight) {
		height = canvasHeight;
		invalidate();
	}
	
	public interface ScrollListener {
		public void onScroll(float offset);
	}

}
