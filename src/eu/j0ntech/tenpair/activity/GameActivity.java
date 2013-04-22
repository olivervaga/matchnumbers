package eu.j0ntech.tenpair.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import eu.j0ntech.tenpair.R;
import eu.j0ntech.tenpair.game.GameBoard;
import eu.j0ntech.tenpair.view.BoardCanvas;

public class GameActivity extends Activity {
	
	private GameBoard mGameboard;
	
	private BoardCanvas mCanvas;
	
	private Button mRestartButton;
	
	private Button mWriteOutButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_game);
		
		mCanvas = (BoardCanvas) findViewById(R.id.boardcanvas);
		
		mGameboard = new GameBoard();
		
		mRestartButton = (Button) findViewById(R.id.restart);
		mWriteOutButton = (Button) findViewById(R.id.writeout);
		
		mRestartButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
			}
		});
		
		mWriteOutButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				mGameboard.addUnunusedTiles();
				mCanvas.invalidate();	
			}
		});
//		mGameboard.displayBoard();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_game, menu);
		return true;
	}
	
	public GameBoard getGameboard() {
		return mGameboard;
	}

}
