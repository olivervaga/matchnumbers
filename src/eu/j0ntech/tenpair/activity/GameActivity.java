package eu.j0ntech.tenpair.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.RelativeLayout;
import eu.j0ntech.tenpair.R;
import eu.j0ntech.tenpair.game.GameBoard;
import eu.j0ntech.tenpair.view.BoardCanvas;

public class GameActivity extends Activity {

	private GameBoard mGameBoard;

	private BoardCanvas mCanvas;
	
	private RelativeLayout mButtonContainer;

	private Button mRestartButton;

	private Button mWriteOutButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_game);

		mGameBoard = new GameBoard();

		mCanvas = (BoardCanvas) findViewById(R.id.boardcanvas);
		
		mButtonContainer = (RelativeLayout) findViewById(R.id.button_container);

		mRestartButton = (Button) findViewById(R.id.restart);
		mWriteOutButton = (Button) findViewById(R.id.writeout);

		mRestartButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				mGameBoard = new GameBoard();
				mCanvas.recalculateBoardSize();
				mCanvas.resetCanvas();
			}
		});

		mWriteOutButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				mGameBoard.addUnunusedTiles();
				mCanvas.recalculateBoardSize();
				mCanvas.invalidate();
			}
		});
		mCanvas.recalculateBoardSize();
		// mGameboard.displayBoard();
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_game, menu);
		return true;
	}

	
	public int getButtonContainerHeight() {
		return mButtonContainer.getHeight();
	}
	public GameBoard getGameboard() {
		return mGameBoard;
	}

}
