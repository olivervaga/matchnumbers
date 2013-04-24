package eu.j0ntech.tenpair.view;

import android.R.color;
import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

public class LoadTextView extends TextView {
	
	private static final int SELECTED_COLOR = color.holo_blue_light;
	private static final int DEFAULT_COLOR = color.transparent;

	public LoadTextView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}
	
	public LoadTextView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
	
	public LoadTextView(Context context) {
		super(context);
	}
	
	@Override
	public void setSelected(boolean selected) {
		if (selected)
			this.setBackgroundColor(getResources().getColor(SELECTED_COLOR));
		else
			this.setBackgroundColor(getResources().getColor(DEFAULT_COLOR));
		super.setSelected(selected);
	}

}
