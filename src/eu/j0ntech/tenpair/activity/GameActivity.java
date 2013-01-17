package eu.j0ntech.tenpair.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import eu.j0ntech.tenpair.R;
import eu.j0ntech.tenpair.game.Gameboard;
import eu.j0ntech.tenpair.view.BoardCanvas;

public class GameActivity extends Activity {
	
	private Gameboard mGameboard;
	
	private BoardCanvas mCanvas;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_game);
		
		mCanvas = (BoardCanvas) findViewById(R.id.boardcanvas);
		
		mGameboard = new Gameboard();
//		mGameboard.displayBoard();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_game, menu);
		return true;
	}
	
	public Gameboard getGameboard() {
		return mGameboard;
	}

}
