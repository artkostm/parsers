package by.artkostm.androidparsers.core.reader;

import java.io.Closeable;
import java.io.IOException;
import java.util.List;

/**
 * The CSVReader reads and parses csv data.
 *
 * @param <E> the type of the records.
 */
public interface CSVReader<E> extends Iterable<E>, Closeable {

	public List<E> readAll() throws IOException;

	public E readNext() throws IOException;

	public List<String> readHeader() throws IOException;
}
