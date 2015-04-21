package by.artkostm.androidparsers.core.reader.internal;

import by.artkostm.androidparsers.core.reader.CSVEntryParser;

/**
 * A default implementation of the CSVEntryParser.
 * This entry parser just returns the String[] array that it received.
 */
public class DefaultCSVEntryParser implements CSVEntryParser<String[]> {

	/**
	 * returns the input...
	 */
	@Override
	public String[] parseEntry(String... data) {
		return data;
	}
}
