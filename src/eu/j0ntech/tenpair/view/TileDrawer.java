package eu.j0ntech.tenpair.view;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import eu.j0ntech.tenpair.game.Tile;
import eu.j0ntech.tenpair.game.Tile.TileType;

public class TileDrawer {

	private final static String TAG = "TileDrawer";

	public static final int COLOR_DEFAULT_SQUARE = Color.WHITE;
	public static final int COLOR_SELECTED_SQUARE = Color.rgb(135, 206, 250); // Light blue
	public static final int COLOR_HIGHLIGHTED_SQUARE = Color.GREEN;
	public static final int COLOR_SCRATCHED_SQUARE = Color.RED;
	public static final int COLOR_NUMBER = Color.BLACK;
	public static final int COLOR_BACKGROUND = Color.DKGRAY;

	private static Paint mRectPaint = new Paint();;
	private static Paint mNumberPaint = new Paint();;
	private static Paint mLinePaint = new Paint();;

	static {
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
	}

	public static void draw(BoardCanvas board, Canvas canvas, Tile tile) {
		float tileSize = board.getTileSize();
		float startX = tile.getStartX() - BoardCanvas.SQUARE_PADDING;
		float startY = tile.getStartY() - BoardCanvas.SQUARE_PADDING;

		mNumberPaint.setTextSize(tileSize - BoardCanvas.SQUARE_PADDING);

		switch (tile.getType()) {
		case DEFAULT:
			mRectPaint.setColor(COLOR_DEFAULT_SQUARE);
			break;
		case HIGHLIGHTED:
			mRectPaint.setColor(COLOR_HIGHLIGHTED_SQUARE);
			break;
		case SCRATCHED:
			mRectPaint.setColor(COLOR_DEFAULT_SQUARE);
			break;
		case SELECTED:
			mRectPaint.setColor(COLOR_SELECTED_SQUARE);
			break;
		}
		canvas.drawRect(tile.getStartX(), tile.getStartY(), tile.getEndX(),
				tile.getEndY(), mRectPaint);
		canvas.drawText(
				String.valueOf(tile.getValue()),
				tile.getCenterX(),
				(float) (tile.getCenterY() + (mNumberPaint.getTextSize()) / 2.6),
				mNumberPaint);
		if (tile.getType() == TileType.SCRATCHED) {
			canvas.drawLine(startX + BoardCanvas.SQUARE_PADDING, startY
					+ BoardCanvas.SQUARE_PADDING, startX + tileSize, startY
					+ tileSize, mLinePaint);
			canvas.drawLine(startX + tileSize, startY
					+ BoardCanvas.SQUARE_PADDING, startX
					+ BoardCanvas.SQUARE_PADDING, startY + tileSize, mLinePaint);
		}
	}

}
