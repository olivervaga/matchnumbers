package eu.j0ntech.tenpair.activity;

import org.json.JSONException;
import org.json.JSONObject;

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

import com.crittercism.app.Crittercism;

import eu.j0ntech.tenpair.R;
import eu.j0ntech.tenpair.fragment.LoadDialog;
import eu.j0ntech.tenpair.fragment.LoadDialog.LoadDialogListener;
import eu.j0ntech.tenpair.save.DeleteTask;

public class MainActivity extends FragmentActivity implements LoadDialogListener{

	private Button mStartButton;
	private Button mLoadButton;
	private Button mExitButton;
	
	private LoadDialog mLoadDialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		JSONObject crittercismConfig = new JSONObject();
		try
		{
		    crittercismConfig.put("shouldCollectLogcat", true); // send logcat data for devices with API Level 16 and higher
		    crittercismConfig.put("includeVersionCode", true);
		}
		catch (JSONException je){}
		Crittercism.init(getApplicationContext(), "51a6f610558d6a4774000004", crittercismConfig);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);

		mStartButton = (Button) findViewById(R.id.button_start);
		mLoadButton = (Button) findViewById(R.id.button_load);
		mExitButton = (Button) findViewById(R.id.button_exit);

		mStartButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				startActivity(new Intent(MainActivity.this, GameActivity.class));

			}
		});

		mLoadButton.setOnClickListener(new OnClickListener() {

			@SuppressLint("InlinedApi")
			@Override
			public void onClick(View v) {
				mLoadDialog = new LoadDialog();
				if (Build.VERSION.SDK_INT < Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
					mLoadDialog.setStyle(DialogFragment.STYLE_NORMAL,
							android.R.style.Theme_Dialog);
				} else {
					mLoadDialog.setStyle(DialogFragment.STYLE_NORMAL,
							android.R.style.Theme_DeviceDefault_Dialog);
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
	}

	@Override
	public void onLoad(String filepath) {
		mLoadDialog.dismiss();
		Intent loadIntent = new Intent(this, GameActivity.class);
		loadIntent.putExtra(GameActivity.LOAD_GAME_TAG, true);
		loadIntent.putExtra(GameActivity.LOAD_GAME_PATH, filepath);
		startActivity(loadIntent);
	}

	@Override
	public void onDelete(String filepath) {
		(new DeleteTask(mLoadDialog)).execute(filepath);
	}

}
