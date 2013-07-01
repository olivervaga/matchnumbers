package eu.j0ntech.matchnumberssiim.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.widget.ImageView;
import eu.j0ntech.matchnumberssiim.R;

public class GameWonDialog extends DialogFragment {
	
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		
		builder.setTitle(R.string.title_game_won);
		ImageView iv = new ImageView(getActivity());
		iv.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.naked_congrats));
		builder.setView(iv);
		builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				getActivity().finish();
			}
		});
		
		return builder.create();
	}

}
