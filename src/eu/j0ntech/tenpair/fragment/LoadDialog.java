package eu.j0ntech.tenpair.fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import eu.j0ntech.tenpair.R;
import eu.j0ntech.tenpair.save.FileDetail;
import eu.j0ntech.tenpair.save.Saver;

public class LoadDialog extends DialogFragment {
	
	private int selectedItemIndex;
	
	private LoadDialogListener mListener;
	
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		final FileDetail[] saves = Saver.getAvailableSaves(getActivity());
		String[] saveNames = new String[saves.length];
		for (int i = 0; i < saves.length; i++) 
			saveNames[i] = saves[i].getName();
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		builder.setTitle(getString(R.string.dialog_load))
		.setItems(saveNames, new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				mListener.onLoad(saves[which].getPath());
			}
		});
//		.setPositiveButton(getString(R.string.button_load), new DialogInterface.OnClickListener() {
//			
//			@Override
//			public void onClick(DialogInterface dialog, int which) {
//				mListener.onLoad(saves[selectedItemIndex].getPath());
//				
//			}
//		})
//		.setNegativeButton(getString(R.string.button_delete), new DialogInterface.OnClickListener() {
//			
//			@Override
//			public void onClick(DialogInterface dialog, int which) {
//				mListener.onDelete(saves[selectedItemIndex].getPath());
//				
//			}
//		});
		return builder.create();
	}
	
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		mListener = (LoadDialogListener) activity;
	}
	
	public interface LoadDialogListener {
		public void onLoad(String filepath);
		public void onDelete(String filepath);
	}

}
