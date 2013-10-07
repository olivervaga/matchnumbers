package eu.j0ntech.matchnumbers.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import eu.j0ntech.matchnumbers.R;
import eu.j0ntech.matchnumbers.application.MatchNumbersApplication;
import eu.j0ntech.matchnumbers.fragment.LoadDialog;
import eu.j0ntech.matchnumbers.fragment.LoadDialog.LoadDialogListener;
import eu.j0ntech.matchnumbers.save.DeleteTask;

public class MainActivity extends FragmentActivity implements LoadDialogListener {

	private Button mContinueButton;
	private Button mStartButton;
	private Button mLoadButton;
	private Button mExitButton;
	private Button mTutorialButton;

	private LoadDialog mLoadDialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);

		mContinueButton = (Button) findViewById(R.id.button_continue);
		mStartButton = (Button) findViewById(R.id.button_start);
		mLoadButton = (Button) findViewById(R.id.button_load);
		mExitButton = (Button) findViewById(R.id.button_exit);
		mTutorialButton = (Button) findViewById(R.id.button_tutorial);

		mContinueButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent continueIntent = new Intent(MainActivity.this, GameActivity.class);
				continueIntent.putExtra(MatchNumbersApplication.CONTINUE_GAME_TAG, true);
				startActivity(continueIntent);
			}
		});

		mStartButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent newIntent = new Intent(MainActivity.this, GameActivity.class);
				newIntent.putExtra(MatchNumbersApplication.NEW_GAME_TAG, true);
				startActivity(newIntent);
			}
		});

		mLoadButton.setOnClickListener(new OnClickListener() {

			@SuppressLint("InlinedApi")
			@Override
			public void onClick(View v) {
				mLoadDialog = new LoadDialog();
				if (Build.VERSION.SDK_INT < Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
					mLoadDialog.setStyle(DialogFragment.STYLE_NORMAL, android.R.style.Theme_Dialog);
				} else {
					mLoadDialog.setStyle(DialogFragment.STYLE_NORMAL, android.R.style.Theme_DeviceDefault_Dialog);
				}
				mLoadDialog.setCancelable(true);
				mLoadDialog.show(getSupportFragmentManager(), "load");
			}
		});

		mExitButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});

		mTutorialButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				startActivity(new Intent(MainActivity.this, TutorialActivity.class));

			}
		});
	}

	@Override
	protected void onResume() {
		super.onResume();
		if (!((MatchNumbersApplication) getApplication()).getContinue()) {
			mContinueButton.setVisibility(View.INVISIBLE);
		} else {
			mContinueButton.setVisibility(View.VISIBLE);
		}
	}

	@Override
	public void onLoad(String filepath) {
		mLoadDialog.dismiss();
		Intent loadIntent = new Intent(this, GameActivity.class);
		loadIntent.putExtra(MatchNumbersApplication.LOAD_GAME_TAG, true);
		loadIntent.putExtra(MatchNumbersApplication.LOAD_GAME_PATH, filepath);
		startActivity(loadIntent);
	}

	@Override
	public void onDelete(String filepath) {
		(new DeleteTask(mLoadDialog)).execute(filepath);
	}

}
