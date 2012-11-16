package hu.velorum.ConCopy.backend.statics;

import android.database.Cursor;

/**
 * @author alien_roger
 * @created 27.10.12
 * @modified 27.10.12
 */
public class DBDataManager {
	private final static String TAG = "DBDataManager";

	private static final String ORDER_BY = "ORDER BY";
	private static final String GROUP_BY = "GROUP BY";
	public static final String SLASH_ = "/";
	public static final String OR_ = " OR ";
	public static final String LIKE_ = " LIKE ";
	public static final String AND_ = " AND ";
	public static final String MORE_ = " > ";
	public static final String EQUALS_ = " = ";
	public static final String EQUALS_ARG_ = "=?";

	public static String[] arguments1 = new String[1];
	public static String[] arguments2 = new String[2];
	public static String[] arguments3 = new String[3];


	public static String concatArguments(String... arguments) {
		StringBuilder selection = new StringBuilder();

		String separator = StaticData.SYMBOL_EMPTY;
		for (String argument : arguments) {
			selection.append(separator);
			separator = AND_;
			selection.append(argument);
			selection.append(EQUALS_ARG_);
		}
		return selection.toString();
	}


	public static String getString(Cursor cursor, String column) {
		return cursor.getString(cursor.getColumnIndexOrThrow(column));
	}

	public static int getInt(Cursor cursor, String column) {
		return cursor.getInt(cursor.getColumnIndexOrThrow(column));
	}

	public static long getLong(Cursor cursor, String column) {
		return cursor.getLong(cursor.getColumnIndexOrThrow(column));
	}

}
