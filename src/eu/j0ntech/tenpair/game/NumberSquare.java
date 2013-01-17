package eu.j0ntech.tenpair.game;

public class NumberSquare {
	
	private byte value;
	
	private float startX;
	
	private float endX;
	
	private float startY;
	
	private float endY;
	
	private float centerX;
	
	private float centerY;
	
	public NumberSquare(byte value) {
		this.value = value;
	}

	public byte getValue() {
		return value;
	}

	public void setValue(byte value) {
		this.value = value;
	}
	
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
	
	private void calculateCenter() {
		centerX = (endX - startX) / 2;
		centerY = (endY - startY) / 2;
	}

}
