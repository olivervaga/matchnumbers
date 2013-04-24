package eu.j0ntech.tenpair.save;

import android.os.AsyncTask;
import eu.j0ntech.tenpair.fragment.LoadDialog;

public class DeleteTask extends AsyncTask<String, Void, Boolean> {

	private LoadDialog mDialog;

	public DeleteTask(LoadDialog dialog) {
		this.mDialog = dialog;
	}

	@Override
	protected void onPreExecute() {
		mDialog.setAsLoading();
	}

	@Override
	protected Boolean doInBackground(String... params) {
		if (params.length == 1) {
			return Saver.deleteSave(params[0]);
		} else
			return false;
	}
	
	@Override
	protected void onPostExecute(Boolean result) {
		mDialog.setAsNormal();
	}

}
