package eu.j0ntech.tenpair.save;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import eu.j0ntech.tenpair.R;
import eu.j0ntech.tenpair.TenPairApp;
import eu.j0ntech.tenpair.activity.GameActivity;
import eu.j0ntech.tenpair.activity.MainActivity;
import eu.j0ntech.tenpair.game.GameBoard;

public class LoadTask extends AsyncTask<String, Void, GameBoard> {
	
	MainActivity mParent;
	
	ProgressDialog mProgressDialog;
	
	public LoadTask(Context context) {
		mParent = (MainActivity) context;
	}
	
	@Override
	protected void onPreExecute() {
		mProgressDialog = new ProgressDialog(mParent);
		mProgressDialog.setMessage(mParent.getString(R.string.dialog_loading));
		mProgressDialog.setCancelable(false);
		mProgressDialog.setIndeterminate(true);
		mProgressDialog.show();	
	}

	@Override
	protected GameBoard doInBackground(String... params) {
		if (params != null && params.length == 1) {
			return Saver.loadGame(params[0]);
		}
		return null;
	}
	
	@Override
	protected void onPostExecute(GameBoard result) {
		((TenPairApp) mParent.getApplication()).setBoard(result);
		mProgressDialog.dismiss();
		Intent gameIntent = new Intent(mParent, GameActivity.class);
		gameIntent.putExtra(GameActivity.LOAD_GAME_TAG, true);
		mParent.startActivity(gameIntent);
	}


}
