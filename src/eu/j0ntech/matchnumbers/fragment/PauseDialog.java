package eu.j0ntech.matchnumbers.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import eu.j0ntech.tenpair.R;

public class PauseDialog extends DialogFragment {

	private Button mResumeButton;
	private Button mRestartButton;
	private Button mSaveButton;
	private Button mExitButton;

	private PauseDialogListener mListener;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		getDialog().setTitle(getString(R.string.menu_settings));
		View v = inflater.inflate(R.layout.dialog_pause, container, false);

		mResumeButton = (Button) v.findViewById(R.id.button_resume);
		mRestartButton = (Button) v.findViewById(R.id.button_restart);
		mSaveButton = (Button) v.findViewById(R.id.button_save);
		mExitButton = (Button) v.findViewById(R.id.button_exit);

		mResumeButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				mListener.onResumeClicked();
			}
		});

		mSaveButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				mListener.onSaveClicked();
			}
		});

		mExitButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				mListener.onExitClicked();
			}
		});
		
		mRestartButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				mListener.onRestartClicked();
			}
		});

		return v;
	}
	
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		mListener = (PauseDialogListener) activity;
	}

	public interface PauseDialogListener {
		public void onResumeClicked();

		public void onRestartClicked();

		public void onSaveClicked();

		public void onExitClicked();
	}

}
