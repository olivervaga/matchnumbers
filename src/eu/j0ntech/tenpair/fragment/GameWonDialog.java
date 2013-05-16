package eu.j0ntech.tenpair.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.TextView;
import eu.j0ntech.tenpair.R;

public class GameWonDialog extends DialogFragment {
	
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		
		builder.setTitle(R.string.title_game_won);
		TextView tv = new TextView(getActivity());
		tv.setText(R.string.text_game_over);
		tv.setTextSize(20);
		tv.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
		tv.setGravity(Gravity.CENTER);
		builder.setView(tv);
		builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// Dialog is dismissed
			}
		});
		
		return builder.create();
	}

}
