package eu.j0ntech.matchnumbers.save;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import android.content.Context;
import android.content.SharedPreferences;
import eu.j0ntech.matchnumbers.game.GameBoard;
import eu.j0ntech.matchnumbers.game.GameBoard.BoardChangeListener;
import eu.j0ntech.matchnumbers.game.Tile;
import eu.j0ntech.matchnumbers.game.Tile.TileType;

public class Saver {

	public static final String FILE_EXTENSION = ".tps";
	private static final String COLUMN_DENOMINATOR = " ";
	private static final String SCRATCHED_INDICATOR = "*";
	private static final String ROW_DENOMINATOR = "nl";
	private static final String SAVE_PREFIX = "data_";

	private Saver() {
	}

	public static void saveGame(GameBoard board, String saveName, Context context) {
		String save = createSave(board);
		SharedPreferences prefs = context.getSharedPreferences(context.getPackageName(), Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = prefs.edit();
		editor.putString(SAVE_PREFIX + saveName, save);
		editor.commit();
	}

	public static GameBoard loadGame(String saveName, BoardChangeListener listener, Context context) {
		SharedPreferences prefs = context.getSharedPreferences(context.getPackageName(), Context.MODE_PRIVATE);
		return readFromPreferences(prefs.getString(saveName, null), listener);
	}

	public static void deleteSave(String saveName, Context context) {
		SharedPreferences prefs = context.getSharedPreferences(context.getPackageName(), Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = prefs.edit();
		editor.remove(saveName);
		editor.commit();
	}

	public static List<SaveDetail> getAvailableSaves(Context context) {
		ArrayList<SaveDetail> result = new ArrayList<SaveDetail>();
		SharedPreferences prefs = context.getSharedPreferences(context.getPackageName(), Context.MODE_PRIVATE);
		Set<String> prefsKeySet = prefs.getAll().keySet();
		for (String key : prefsKeySet)
			if (key.startsWith(SAVE_PREFIX)) {
				result.add(new SaveDetail(key.substring(SAVE_PREFIX.length()), key));
			}
		if (result.size() == 0)
			result = null;
		return result;
	}

	public static String createSave(GameBoard board) {
		StringBuilder result = new StringBuilder();
		for (ArrayList<Tile> row : board.getInternalBoard()) {
			for (Tile column : row) {
				result.append(column.getValue());
				if (column.getType() == TileType.SCRATCHED)
					result.append(SCRATCHED_INDICATOR);
				result.append(COLUMN_DENOMINATOR);
			}
			result.append(ROW_DENOMINATOR);
		}
		return result.toString();
	}

	public static GameBoard readFromPreferences(String saveString, BoardChangeListener listener) {
		ArrayList<ArrayList<Tile>> result;
		result = parseSaveData(saveString);
		return new GameBoard(listener, result);
	}

	private static ArrayList<ArrayList<Tile>> parseSaveData(String saveData) {
		ArrayList<ArrayList<Tile>> result = new ArrayList<ArrayList<Tile>>();
		String[] rows = saveData.split(ROW_DENOMINATOR);
		for (int i = 0; i < rows.length; i++) {
			ArrayList<Tile> row = new ArrayList<Tile>();
			result.add(row);
			String[] columns = rows[i].split(COLUMN_DENOMINATOR);
			for (int j = 0; j < columns.length; j++) {
				row.add(new Tile(Byte.parseByte(columns[j].substring(0, 1)), i, j, columns[j]
						.contains(SCRATCHED_INDICATOR)));
			}
		}
		return result;
	}
}
