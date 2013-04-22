package eu.j0ntech.tenpair.fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import eu.j0ntech.tenpair.R;

public class SaveNameDialog extends DialogFragment {

	private SaveDialogListener mListener;

	private EditText mSaveName;

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		View v = getActivity().getLayoutInflater().inflate(
				R.layout.dialog_save, null);
		mSaveName = (EditText) v.findViewById(R.id.save_name);
		builder.setView(v);
		builder.setTitle(getString(R.string.dialog_save))
				.setPositiveButton(R.string.button_save,
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								// This is handled in OnShowListener
							}
						})
				.setNegativeButton(R.string.button_cancel,
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								mListener.onCancel();

							}
						}).setCancelable(false);
		final AlertDialog nameDialog = builder.create();
		nameDialog.setOnShowListener(new DialogInterface.OnShowListener() {
			
			@Override
			public void onShow(DialogInterface dialog) {
				Button positive = nameDialog.getButton(AlertDialog.BUTTON_POSITIVE);
				positive.setOnClickListener(new View.OnClickListener() {
					
					@Override
					public void onClick(View v) {
						String saveName = mSaveName.getText().toString();
						if (saveName.length() > 0) {
							mListener.onSave(saveName);
							nameDialog.dismiss();
						} else
							Toast.makeText(getActivity(),
									getString(R.string.error_name),
									Toast.LENGTH_LONG).show();
						
					}
				});
				
			}
		});
		return nameDialog;
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		mListener = (SaveDialogListener) activity;
	}

	public interface SaveDialogListener {
		public void onCancel();

		public void onSave(String saveName);
	}

}
