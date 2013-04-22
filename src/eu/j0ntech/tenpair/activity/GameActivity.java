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
import eu.j0ntech.tenpair.TenPairApp;
import eu.j0ntech.tenpair.fragment.PauseDialog;
import eu.j0ntech.tenpair.fragment.PauseDialog.PauseDialogListener;
import eu.j0ntech.tenpair.fragment.SaveNameDialog;
import eu.j0ntech.tenpair.fragment.SaveNameDialog.SaveDialogListener;
import eu.j0ntech.tenpair.game.GameBoard;
import eu.j0ntech.tenpair.save.SaveTask;
import eu.j0ntech.tenpair.view.BoardCanvas;

public class GameActivity extends FragmentActivity implements
		PauseDialogListener, SaveDialogListener {

	private GameBoard mGameBoard;

	private BoardCanvas mCanvas;

	private RelativeLayout mButtonContainer;

	private Button mPauseButton;

	private Button mWriteOutButton;

	private PauseDialog mPauseDialog;
	private SaveNameDialog mSaveDialog;
	
	public static final String LOAD_GAME_TAG = "load_game";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_game);
		
		if (getIntent().getBooleanExtra(LOAD_GAME_TAG, false)) {
			mGameBoard = ((TenPairApp) getApplication()).startLoadedGame();
		} else {
			mGameBoard = ((TenPairApp) getApplication()).startNewGame();			
		}


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
					mPauseDialog.setStyle(DialogFragment.STYLE_NORMAL,
							android.R.style.Theme_Dialog);
				} else {
					mPauseDialog.setStyle(DialogFragment.STYLE_NORMAL,
							android.R.style.Theme_DeviceDefault_Dialog);
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
		if (mPauseDialog.isVisible())
			mPauseDialog.dismiss();
	}

	@Override
	public void onRestartClicked() {
		resetGame();
		if (mPauseDialog.isVisible())
			mPauseDialog.dismiss();
	}

	@SuppressLint("InlinedApi")
	@Override
	public void onSaveClicked() {
		if (mPauseDialog.isVisible())
			mPauseDialog.dismiss();
		mSaveDialog = new SaveNameDialog();
		if (Build.VERSION.SDK_INT < Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
			mSaveDialog.setStyle(DialogFragment.STYLE_NORMAL,
					android.R.style.Theme_Dialog);
		} else {
			mSaveDialog.setStyle(DialogFragment.STYLE_NORMAL,
					android.R.style.Theme_DeviceDefault_Dialog);
		}
		mSaveDialog.setCancelable(false);
		mSaveDialog.show(getSupportFragmentManager(), "save");
	}

	@Override
	public void onExitClicked() {
		finish();
	}

	@Override
	public void onCancel() {
		if (mSaveDialog.isVisible())
			mSaveDialog.dismiss();

	}

	@Override
	public void onSave(String saveName) {
		if (mSaveDialog.isVisible())
			mSaveDialog.dismiss();
		(new SaveTask(this)).execute(saveName);

	}

}
