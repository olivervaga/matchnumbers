package eu.j0ntech.tenpair.activity;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.RelativeLayout;
import eu.j0ntech.tenpair.R;
import eu.j0ntech.tenpair.fragment.PauseDialog;
import eu.j0ntech.tenpair.fragment.PauseDialog.PauseDialogListener;
import eu.j0ntech.tenpair.game.GameBoard;
import eu.j0ntech.tenpair.view.BoardCanvas;

public class GameActivity extends FragmentActivity implements PauseDialogListener {

	private GameBoard mGameBoard;

	private BoardCanvas mCanvas;
	
	private RelativeLayout mButtonContainer;

	private Button mPauseButton;

	private Button mWriteOutButton;
	
	private PauseDialog mPauseDialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_game);

		mGameBoard = new GameBoard();

		mCanvas = (BoardCanvas) findViewById(R.id.boardcanvas);
		
		mButtonContainer = (RelativeLayout) findViewById(R.id.button_container);

		mPauseButton = (Button) findViewById(R.id.pause);
		mWriteOutButton = (Button) findViewById(R.id.writeout);

		mPauseButton.setOnClickListener(new View.OnClickListener() {

			@SuppressLint("InlinedApi")
			@Override
			public void onClick(View v) {
				mPauseDialog = new PauseDialog();
				if (Build.VERSION.SDK_INT < Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
					mPauseDialog.setStyle(DialogFragment.STYLE_NORMAL, android.R.style.Theme_Dialog);
				} else {
					mPauseDialog.setStyle(DialogFragment.STYLE_NORMAL, android.R.style.Theme_DeviceDefault_Dialog);
				}
				mPauseDialog.setCancelable(false);
				mPauseDialog.show(getSupportFragmentManager(), "pause");
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
	
	private void resetGame() {
		mGameBoard = new GameBoard();
		mCanvas.recalculateBoardSize();
		mCanvas.resetCanvas();
	}
	
	public int getButtonContainerHeight() {
		return mButtonContainer.getHeight();
	}
	public GameBoard getGameboard() {
		return mGameBoard;
	}

	@Override
	public void onResumeClicked() {
		if (mPauseDialog.isVisible()) mPauseDialog.dismiss();
	}
	
	@Override
	public void onRestartClicked() {
		resetGame();
		if (mPauseDialog.isVisible()) mPauseDialog.dismiss();		
	}

	@Override
	public void onSaveClicked() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onExitClicked() {
		finish();
	}

}
