package eu.j0ntech.matchnumberssiim.view;

import android.view.Gravity;
import android.widget.Toast;
import eu.j0ntech.matchnumberssiim.activity.GameActivity;

public class ToastUtil {
	
	public static void showToast(String message, int length, GameActivity parent) {
		Toast toast = Toast.makeText(parent, null, length);
		toast.setGravity(Gravity.TOP, 0, parent.getButtonContainerHeight());
		toast.setText(message);
		toast.show();
	}
	
	public static void showToast(int message, int length, GameActivity parent) {
		Toast toast = Toast.makeText(parent, null, length);
		toast.setGravity(Gravity.TOP, 0, parent.getButtonContainerHeight());
		toast.setText(message);
		toast.show();
	}

}
