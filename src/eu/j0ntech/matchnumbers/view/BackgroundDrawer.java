package eu.j0ntech.matchnumbers.view;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import eu.j0ntech.matchnumbers.game.GameBoard;

public class BackgroundDrawer {

	public final int COLOR_BACKGROUND = Color.WHITE;
	public final int COLOR_LINE = Color.rgb(51, 181, 229);

	private final int LINE_WIDTH = BoardView.SQUARE_PADDING - 2;
	private final int LINE_PADDING = 2;

	private Paint mLinePaint = new Paint();
	private Paint mBackPaint = new Paint();

	private float tileSize;

	public BackgroundDrawer(BoardView boardView) {
		tileSize = boardView.getTileSize();

		mLinePaint.setStyle(Paint.Style.STROKE);
		mLinePaint.setColor(COLOR_LINE);
		mLinePaint.setStrokeWidth(LINE_WIDTH);
		mLinePaint.setAntiAlias(true);

		mBackPaint.setStyle(Paint.Style.FILL);
		mBackPaint.setColor(COLOR_BACKGROUND);
		mBackPaint.setAntiAlias(true);
	}

	public void draw(Canvas boardCanvas, int rows, float offset) {
		boardCanvas.drawPaint(mBackPaint);
		for (int i = 0; i <= GameBoard.COLUMNS; i++) {
			boardCanvas.drawLine(i * tileSize + LINE_PADDING, 0, i * tileSize + LINE_PADDING, rows * tileSize
					+ LINE_PADDING + 4, mLinePaint);
		}
		int screenWidth = (int) (tileSize + BoardView.SQUARE_PADDING) * GameBoard.COLUMNS;
		for (int i = 0; i <= rows; i++) {
			boardCanvas.drawLine(0, i * tileSize + LINE_PADDING + offset, screenWidth, i * tileSize + LINE_PADDING
					+ offset, mLinePaint);
		}
	}

}
