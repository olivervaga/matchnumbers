package eu.j0ntech.tenpair.view;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import eu.j0ntech.tenpair.game.Tile;
import eu.j0ntech.tenpair.game.Tile.TileType;

public class TileDrawer {

	public static final int COLOR_DEFAULT_SQUARE = Color.WHITE;
	public static final int COLOR_SELECTED_SQUARE = Color.rgb(135, 206, 250); // Light blue
	public static final int COLOR_HIGHLIGHTED_SQUARE = Color.rgb(72, 209, 204); // Turquoise
	public static final int COLOR_NUMBER = Color.BLACK;
	public static final int COLOR_BACKGROUND = Color.DKGRAY;

	private Paint mRectPaint = new Paint();
	private Paint mNumberPaint = new Paint();
	private Paint mLinePaint = new Paint();

	private float tileSize;
	private int tile3rd;
	private int tile2_3rds;

	public TileDrawer(BoardView boardView) {

		tileSize = boardView.getTileSize();
		tile3rd = (int) (tileSize / 3);
		tile2_3rds = (int) ((tileSize / 3) * 2);

		mRectPaint.setStyle(Paint.Style.FILL);
		mRectPaint.setAntiAlias(true);

		mLinePaint.setStyle(Paint.Style.STROKE);
		mLinePaint.setColor(COLOR_NUMBER);
		mLinePaint.setStrokeWidth(6f);
		mLinePaint.setAntiAlias(true);

		mNumberPaint.setStyle(Paint.Style.STROKE);
		mNumberPaint.setColor(COLOR_NUMBER);
		mNumberPaint.setAntiAlias(true);
		mNumberPaint.setTextAlign(Paint.Align.CENTER);
	}

	public void draw(Tile tile, Canvas boardCanvas, float offset) {
		float startX = tile.getStartX() - BoardView.SQUARE_PADDING;
		float startY = tile.getStartY() - BoardView.SQUARE_PADDING;

		mNumberPaint.setTextSize(tileSize - BoardView.SQUARE_PADDING);

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
		boardCanvas.drawRect(tile.getStartX(), tile.getStartY() + offset,
				tile.getEndX(), tile.getEndY() + offset, mRectPaint);
		boardCanvas
				.drawText(
						String.valueOf(tile.getValue()),
						tile.getCenterX(),
						(float) (tile.getCenterY()
								+ (mNumberPaint.getTextSize()) / 2.6 + offset),
						mNumberPaint);
		if (tile.getType() == TileType.SCRATCHED) {
			boardCanvas.drawLine(startX + BoardView.SQUARE_PADDING, startY
					+ BoardView.SQUARE_PADDING + offset, startX + tileSize,
					startY + tile3rd + offset, mLinePaint);

			boardCanvas.drawLine(startX + tileSize, startY + tile3rd + offset,
					startX + BoardView.SQUARE_PADDING, startY + tile2_3rds
							+ offset, mLinePaint);

			boardCanvas.drawLine(startX + BoardView.SQUARE_PADDING, startY
					+ tile2_3rds + offset, startX + tileSize, startY + tileSize
					+ offset, mLinePaint);

			boardCanvas.drawLine(startX + tileSize, startY
					+ BoardView.SQUARE_PADDING + offset, startX
					+ BoardView.SQUARE_PADDING, startY + tile3rd + offset,
					mLinePaint);

			boardCanvas.drawLine(startX + BoardView.SQUARE_PADDING, startY
					+ tile3rd + offset, startX + tileSize, startY + tile2_3rds
					+ offset, mLinePaint);

			boardCanvas.drawLine(startX + tileSize, startY + tile2_3rds
					+ offset, startX + BoardView.SQUARE_PADDING, startY
					+ tileSize + offset, mLinePaint);
		}
	}

}
