package by.artkostm.androidparsers.core.reader.internal;

import java.io.Reader;

import by.artkostm.androidparsers.core.reader.CSVEntryFilter;
import by.artkostm.androidparsers.core.reader.CSVEntryParser;
import by.artkostm.androidparsers.core.reader.CSVReader;
import by.artkostm.androidparsers.core.reader.CSVTokenizer;
import by.artkostm.androidparsers.core.reader.CachedCSVReader;
import by.artkostm.androidparsers.core.util.Builder;
import by.artkostm.androidparsers.core.util.CSVStrategy;

/**
 * The Builder that creates the CSVReaderImpl objects.
 *
 * @param <E>
 *            The Type that your rows represent
 */
public class CSVReaderBuilder<E> implements Builder<CSVReader<E>> {

	final Reader reader;
	CSVEntryParser<E> entryParser;
	CSVStrategy strategy = CSVStrategy.DEFAULT;
	CSVEntryFilter<E> entryFilter;
	CSVTokenizer tokenizer = new CSVTokenizerImpl();

	/**
	 * @param reader
	 *            the csv reader
	 */
	public CSVReaderBuilder(Reader reader) {
		this.reader = reader;
	}

	public CSVReaderBuilder<E> strategy(CSVStrategy strategy) {
		this.strategy = strategy;
		return this;
	}

	public CSVReaderBuilder<E> entryParser(CSVEntryParser<E> entryParser) {
		this.entryParser = entryParser;
		return this;
	}

	public CSVReaderBuilder<E> entryFilter(CSVEntryFilter<E> entryFilter) {
		this.entryFilter = entryFilter;
		return this;
	}

	public CSVReaderBuilder<E> tokenizer(CSVTokenizer tokenizer) {
		this.tokenizer = tokenizer;
		return this;
	}

	@Override
	public CSVReader<E> build() {
		if (entryParser == null) {
			throw new IllegalStateException("you have to specify a csv entry parser");
		}

		return new CSVReaderImpl<E>(this);
	}

	public static CSVReader<String[]> newDefaultReader(Reader reader) {
		return new CSVReaderBuilder<String[]>(reader).entryParser(new DefaultCSVEntryParser()).build();
	}

	public static <E> CachedCSVReader<E> cached(CSVReader<E> reader) {
		return new CachedCSVReaderImpl<E>(reader);
	}
}
