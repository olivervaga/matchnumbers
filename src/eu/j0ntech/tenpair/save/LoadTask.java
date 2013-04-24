package eu.j0ntech.tenpair.save;

import android.content.Context;
import android.os.AsyncTask;
import android.view.Gravity;
import android.widget.Toast;
import eu.j0ntech.tenpair.R;
import eu.j0ntech.tenpair.activity.GameActivity;
import eu.j0ntech.tenpair.game.GameBoard;

public class LoadTask extends AsyncTask<String, Void, GameBoard> {
	
	GameActivity mParent;
	
	public LoadTask(Context context) {
		mParent = (GameActivity) context;
	}
	
	@Override
	protected void onPreExecute() {
		mParent.showAsLoading();
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
		if (result != null) 
			mParent.setGameBoard(result);
		else {
			Toast toast = Toast.makeText(mParent, R.string.error_load, Toast.LENGTH_SHORT);
			toast.setGravity(Gravity.TOP, 0, mParent.getButtonContainerHeight());
			toast.show();
		}
		mParent.showAsNormal();
	}


}
