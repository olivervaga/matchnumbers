package eu.j0ntech.tenpair.game;

/**
 * A data class for holding info about a number square
 * @author Oliver Vaga
 *
 */
public class NumberSquare {
	
	// Numeric value of the square
	private byte value;
	
	// Left X coordinate
	private float startX;
	
	// Right X coordinate
	private float endX;
	
	// Top Y coordinate
	private float startY;
	
	// Bottom Y coordinate
	private float endY;
	
	// Horizontal center coordinate
	private float centerX;
	
	// Vertical center coordinate
	private float centerY;
	
	// Row on the game board
	private int row;
	
	// Column on the game board
	private int column;
	
	// The square is currently selected
	private boolean selected = false;
	
	// The square is highlighted (available squares next to the selected square)
	private boolean highlighted = false;
	
	// The square has been played (no more selectable or highlightable)
	private boolean scratched = false;
	
	// Constructor with just the numeric value (byte)
	public NumberSquare(byte value) {
		this.value = value;
	}
	
	// Constructor with numeric value and place on the game board
	public NumberSquare(byte value, int row, int column) {
		this.value = value;
		this.row = row;
		this.column = column;
	}

	public byte getValue() {
		return value;
	}

	public void setValue(byte value) {
		this.value = value;
	}
	
	// Sets the coordinates and calculates center
	public void setCoordinates(float left, float top, float right, float bottom) {
		startX = left;
		startY = top;
		endX = right;
		endY = bottom;
		calculateCenter();
	}

	public float getStartX() {
		return startX;
	}

	public void setStartX(float startX) {
		this.startX = startX;
		calculateCenter();
	}

	public float getEndX() {
		return endX;
	}

	public void setEndX(float endX) {
		this.endX = endX;
		calculateCenter();
	}

	public float getStartY() {
		return startY;
	}

	public void setStartY(float startY) {
		this.startY = startY;
		calculateCenter();
	}

	public float getEndY() {
		return endY;
	}

	public void setEndY(float endY) {
		this.endY = endY;
		calculateCenter();
	}

	public float getCenterX() {
		return centerX;
	}

	public void setCenterX(float centerX) {
		this.centerX = centerX;
		calculateCenter();
	}

	public float getCenterY() {
		return centerY;
	}

	public void setCenterY(float centerY) {
		this.centerY = centerY;
		calculateCenter();
	}
	
	public int getRow() {
		return row;
	}

	public void setRow(int row) {
		this.row = row;
	}

	public int getColumn() {
		return column;
	}

	public void setColumn(int column) {
		this.column = column;
	}

	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}

	public boolean isHighlighted() {
		return highlighted;
	}

	public void setHighlighted(boolean highlighted) {
		this.highlighted = highlighted;
	}

	public boolean isScratched() {
		return scratched;
	}

	public void setScratched(boolean scratched) {
		this.scratched = scratched;
	}

	// Calculates center of square
	private void calculateCenter() {
		centerX = startX + (endX - startX) / 2;
		centerY = startY + (endY - startY) / 2;
	}

}
