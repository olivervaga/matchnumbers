package eu.j0ntech.matchnumbers.application;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

public class MatchNumbersApplication extends Application {

	private Boolean hasContinue;

	private String continueData;
	
	public static final String LOAD_GAME_TAG = "load_game";
	public static final String CONTINUE_GAME_TAG = "continue_game";
	public static final String LOAD_GAME_PATH = "load_game_path";
	public static final String CONTINUE_DATA = "continue_data";
	public static final String NEW_GAME_TAG = "new_game";

	public void setContinue(boolean hasContinue) {
		SharedPreferences prefs = this.getSharedPreferences(getPackageName(), Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = prefs.edit();
		editor.putBoolean(CONTINUE_GAME_TAG, hasContinue);
		editor.commit();
		this.hasContinue = hasContinue;
	}

	public boolean getContinue() {
		if (hasContinue == null) {
			SharedPreferences prefs = this.getSharedPreferences(getPackageName(), Context.MODE_PRIVATE);
			hasContinue = prefs.getBoolean(CONTINUE_GAME_TAG, false);
		}
		return hasContinue;
	}

	public void setContinueData(String continueData) {
		SharedPreferences prefs = this.getSharedPreferences(getPackageName(), Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = prefs.edit();
		editor.putString(CONTINUE_DATA, continueData);
		editor.commit();
		this.continueData = continueData;
	}

	public String getContinueData() {
		if (continueData == null) {
			SharedPreferences prefs = this.getSharedPreferences(getPackageName(), Context.MODE_PRIVATE);
			continueData = prefs.getString(CONTINUE_DATA, null);
		}
		return continueData;
	}

}
