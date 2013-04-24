package eu.j0ntech.tenpair.save;

import java.io.FileNotFoundException;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.view.Gravity;
import android.widget.Toast;
import eu.j0ntech.tenpair.R;
import eu.j0ntech.tenpair.activity.GameActivity;

public class SaveTask extends AsyncTask<String, Void, Integer> {
	
	GameActivity mParent;
	
	ProgressDialog mProgressDialog;
	
	AlertDialog mOverwriteDialog;
	
	public SaveTask(Context context) {
		mParent = (GameActivity) context;
	}
	
	@Override
	protected void onPreExecute() {
		mProgressDialog = new ProgressDialog(mParent);
		mProgressDialog.setMessage(mParent.getString(R.string.dialog_saving));
		mProgressDialog.setCancelable(false);
		mProgressDialog.setIndeterminate(true);
		mProgressDialog.show();
		
	}

	@Override
	protected Integer doInBackground(String... params) {
		if (params.length == 1) {
			boolean result;
			try {
				result = Saver.saveGame(mParent.getGameboard(), params[0], mParent);
			} catch (FileNotFoundException e) {
				return SaveResult.MEDIA_UNAVAILABLE;
			} catch (FileAlreadyExistsException e) {
				return SaveResult.FILE_EXISTS;
			}
			if (!result) return SaveResult.MEDIA_UNAVAILABLE;
			else return SaveResult.OK;
		} else return SaveResult.MEDIA_UNAVAILABLE;
	}
	
	@Override
	protected void onPostExecute(Integer result) {
		Toast toast = Toast.makeText(mParent, null, Toast.LENGTH_SHORT);
		toast.setGravity(Gravity.TOP, 0, mParent.getButtonContainerHeight());
		mProgressDialog.dismiss();
		switch (result) {
		case SaveResult.FILE_EXISTS:
			toast.setText(R.string.error_name_duplicate);
			toast.show();
			mParent.onSaveClicked();
			break;
		case SaveResult.MEDIA_UNAVAILABLE:
			toast.setText(R.string.error_save);
			toast.show();
			break;
		case SaveResult.OK:
			toast.setText(R.string.confirm_save);
			toast.show();
			break;
		}
	}
	
	public interface SaveResult {
		
		public static final int OK = 0;
		public static final int MEDIA_UNAVAILABLE = 1;
		public static final int FILE_EXISTS = 2;
	}


}
