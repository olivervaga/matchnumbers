package eu.j0ntech.matchnumberssiim.save;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import android.content.Context;
import android.os.Environment;
import eu.j0ntech.matchnumberssiim.game.GameBoard;
import eu.j0ntech.matchnumberssiim.game.Tile;
import eu.j0ntech.matchnumberssiim.game.GameBoard.BoardChangeListener;
import eu.j0ntech.matchnumberssiim.game.Tile.TileType;

public class Saver {

	public static final String FILE_EXTENSION = ".tps";
	private static final String COLUMN_DENOMINATOR = " ";
	private static final String SCRATCHED_INDICATOR = "*";
	private static final String ROW_DENOMINATOR = "nl";
	
	private Saver() {}

	public static boolean saveGame(GameBoard board, String saveName,
			Context context) throws FileNotFoundException,
			FileAlreadyExistsException {
		String save = createSave(board);
		if (checkExternalStorage()) {
			writeSaveToFile(save, saveName, context);
			return true;
		} else {
			return false;
		}
	}

	public static GameBoard loadGame(String filepath, BoardChangeListener listener) {
		if (!checkExternalStorage())
			return null;
		return readFromSaveFile(filepath, listener);
	}
	
	public static boolean deleteSave(String filePath) {
		if (!checkExternalStorage())
			return false;
		File file = new File(filePath);
		return file.delete();
	}

	public static FileDetail[] getAvailableSaves(Context context) {
		if (!checkExternalStorage())
			return null;
		FileDetail[] result = null;
		File fileDir = context.getExternalFilesDir(null);
		File[] files = fileDir.listFiles(new SaveFilter());
		if (files != null && files.length > 0) {
			result = new FileDetail[files.length];
			for (int i = 0; i < files.length; i++) {
				result[i] = new FileDetail(files[i].getName(),
						files[i].getAbsolutePath());
			}
		}
		return result;
	}

	private static String createSave(GameBoard board) {
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

	private static boolean checkExternalStorage() {

		String state = Environment.getExternalStorageState();

		if (Environment.MEDIA_MOUNTED.equals(state)) {
			// We can read and write the media
			return true;
		} else {
			// Something else is wrong. It may be one of many other states, but
			// all we need
			// to know is we can neither read nor write
			return false;
		}
	}

	private static void writeSaveToFile(String save, String saveName,
			Context context) throws FileNotFoundException, FileAlreadyExistsException {
		File fileDir = context.getExternalFilesDir(null);
		File saveFile = new File(fileDir, saveName + FILE_EXTENSION);
		if (saveFile.exists()) throw new FileAlreadyExistsException();
		PrintWriter writer = null;
		try {
			writer = new PrintWriter(saveFile);
			writer.write(save);
			writer.flush();
		} finally {
			if (writer != null)
				writer.close();
		}
	}

	private static GameBoard readFromSaveFile(String filePath, BoardChangeListener listener) {
		ArrayList<ArrayList<Tile>> result;
		BufferedReader br = null;
		String save = null;
		try {
			br = new BufferedReader(new FileReader(filePath));
			save = br.readLine();
		} catch (Exception e) {
			return null;
		} finally {
			try {
				br.close();
			} catch (IOException e) {
			}
		}
		result = parseSaveData(save);
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
				row.add(new Tile(Byte.parseByte(columns[j].substring(0, 1)), i,
						j, columns[j].contains(SCRATCHED_INDICATOR)));
			}
		}
		return result;
	}

	private static class SaveFilter implements FilenameFilter {

		@Override
		public boolean accept(File dir, String filename) {
			if (filename.endsWith(FILE_EXTENSION))
				return true;
			else
				return false;
		}

	}

}
