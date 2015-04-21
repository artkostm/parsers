package by.artkostm.androidparsers.core.util;

import java.util.ArrayList;
import java.util.List;

/**
 * Provides some useful functions.
 */
public class CSVUtil {
	private CSVUtil() {
	}
	
	public static String implode(String[] data, String delimiter) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < data.length; i++) {
			if (i != 0) {
				sb.append(delimiter);
			}

			sb.append(data[i]);
		}

		return sb.toString();
	}

	public static String[] split(String str, char separatorChar, boolean preserveAllTokens) {
		if (str == null) {
			return null;
		}
		int len = str.length();
		if (len == 0) {
			return new String[0];
		}
		List<String> list = new ArrayList<String>();
		int i = 0, start = 0;
		boolean match = false;
		boolean lastMatch = false;
		while (i < len) {
			if (str.charAt(i) == separatorChar) {
				if (match || preserveAllTokens) {
					list.add(str.substring(start, i));
					match = false;
					lastMatch = true;
				}
				start = ++i;
				continue;
			}
			lastMatch = false;
			match = true;
			i++;
		}
		if (match || preserveAllTokens && lastMatch) {
			list.add(str.substring(start, i));
		}
		return list.toArray(new String[list.size()]);
	}
}
