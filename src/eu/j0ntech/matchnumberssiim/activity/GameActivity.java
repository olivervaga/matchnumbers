package eu.j0ntech.matchnumberssiim.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import eu.j0ntech.matchnumberssiim.R;
import eu.j0ntech.matchnumberssiim.fragment.GameWonDialog;
import eu.j0ntech.matchnumberssiim.fragment.PauseDialog;
import eu.j0ntech.matchnumberssiim.fragment.PauseDialog.PauseDialogListener;
import eu.j0ntech.matchnumberssiim.fragment.SaveNameDialog;
import eu.j0ntech.matchnumberssiim.fragment.SaveNameDialog.SaveDialogListener;
import eu.j0ntech.matchnumberssiim.game.GameBoard;
import eu.j0ntech.matchnumberssiim.game.GameBoard.BoardChangeListener;
import eu.j0ntech.matchnumberssiim.save.LoadTask;
import eu.j0ntech.matchnumberssiim.save.SaveTask;
import eu.j0ntech.matchnumberssiim.view.BoardView;
import eu.j0ntech.matchnumberssiim.view.ScrollBar;
import eu.j0ntech.matchnumberssiim.view.ScrollBar.ScrollListener;

public class GameActivity extends FragmentActivity implements
		PauseDialogListener, SaveDialogListener, BoardChangeListener, ScrollListener {

	private GameBoard mGameBoard;

	private BoardView mCanvas;
	private ScrollBar mScrollBar;

	private RelativeLayout mButtonContainer;

	private Button mPauseButton;
	private Button mDealButton;
	private Button mRemoveRowsButton;
	
	private ProgressBar mLoadingIndicator;

	private PauseDialog mPauseDialog;
	private SaveNameDialog mSaveDialog;

	private TextView mRemainingCount;
	
	public static final String LOAD_GAME_TAG = "load_game";
	public static final String LOAD_GAME_PATH = "load_game_path";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_game);
		Intent incoming = getIntent();

		mCanvas = (BoardView) findViewById(R.id.boardcanvas);
		
		mScrollBar = (ScrollBar) findViewById(R.id.scrollbar);

		mButtonContainer = (RelativeLayout) findViewById(R.id.button_container);

		mPauseButton = (Button) findViewById(R.id.pause);
		mDealButton = (Button) findViewById(R.id.writeout);
		mRemoveRowsButton = (Button) findViewById(R.id.button_remove_scratched);
		
		mLoadingIndicator = (ProgressBar) findViewById(R.id.loading_indicator);

		mRemainingCount = (TextView) findViewById(R.id.number_remaining);

		if (incoming.getBooleanExtra(LOAD_GAME_TAG, false)) {
			(new LoadTask(this)).execute(incoming
					.getStringExtra(LOAD_GAME_PATH));
		} else {
			mGameBoard = new GameBoard(this);
			mCanvas.recalculateBoardSize();
		}

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
				mPauseDialog.setCancelable(true);
				mPauseDialog.show(getSupportFragmentManager(), "pause");
			}
		});

		mDealButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				mGameBoard.addUnunusedTiles();
				mCanvas.recalculateBoardSize();
				mScrollBar.setHeight(mCanvas.getHeight());
				mCanvas.invalidate();
			}
		});
		
		mRemoveRowsButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				int removedRows = mGameBoard.removeScratchedRows();
				mCanvas.recalculateBoardSize();
				onRowsRemoved(removedRows);
				mCanvas.invalidate();
			}
		});
		// mGameboard.displayBoard();
	}

	private void resetGame() {
		mGameBoard = new GameBoard(this);
		mCanvas.recalculateBoardSize();
		mCanvas.resetCanvas();
	}

	public int getButtonContainerHeight() {
		return mButtonContainer.getHeight();
	}
	
	public int getBoardHeight() {
		return mCanvas.getHeight();
	}

	public GameBoard getGameBoard() {
		return mGameBoard;
	}

	public void setGameBoard(GameBoard board) {
		mGameBoard = board;
	}
	
	public void setScrollBar(int height) {
		mScrollBar.setHeight(height);
	}

	public void showAsLoading() {
		mCanvas.setVisibility(View.INVISIBLE);
		mLoadingIndicator.setVisibility(View.VISIBLE);
	}

	public void showAsNormal() {
		mCanvas.recalculateBoardSize();
		mCanvas.setVisibility(View.VISIBLE);
		mLoadingIndicator.setVisibility(View.INVISIBLE);
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
		mSaveDialog.setCancelable(true);
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

	@Override
	public void onBoardChanged(int newCount) {
		mRemainingCount.setText(String.valueOf(newCount));
	}
	
	@Override
	public void onRowsRemoved(int removedRows) {
		if (removedRows > 0)
			mCanvas.resetScroll(removedRows);
	}

	@SuppressLint("InlinedApi")
	@Override
	public void onGameWon() {
		GameWonDialog gameWonDialog = new GameWonDialog();
		if (Build.VERSION.SDK_INT < Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
			gameWonDialog.setStyle(DialogFragment.STYLE_NORMAL,
					android.R.style.Theme_Dialog);
		} else {
			gameWonDialog.setStyle(DialogFragment.STYLE_NORMAL,
					android.R.style.Theme_DeviceDefault_Dialog);
		}
		gameWonDialog.show(getSupportFragmentManager(), "game over");
	}

	@Override
	public void onScroll(float offset) {
		mScrollBar.drawScroll(offset, mCanvas.getBoardSize());	
	}


}
