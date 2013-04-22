package eu.j0ntech.tenpair.save;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;
import eu.j0ntech.tenpair.R;
import eu.j0ntech.tenpair.activity.GameActivity;

public class SaveTask extends AsyncTask<String, Void, Boolean> {
	
	GameActivity mParent;
	
	ProgressDialog mProgressDialog;
	
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
	protected Boolean doInBackground(String... params) {
		if (params.length == 1) {
			Saver.saveGame(mParent.getGameboard(), params[0], mParent);
			return true;
		} else return false;
	}
	
	@Override
	protected void onPostExecute(Boolean result) {
		mProgressDialog.dismiss();
		if (result)
			Toast.makeText(mParent, mParent.getString(R.string.confirm_save), Toast.LENGTH_SHORT).show();
		else
			Toast.makeText(mParent, mParent.getString(R.string.error_save), Toast.LENGTH_SHORT).show();
	}


}
