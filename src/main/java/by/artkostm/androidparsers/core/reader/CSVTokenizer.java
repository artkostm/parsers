package by.artkostm.androidparsers.core.reader;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;

import by.artkostm.androidparsers.core.util.CSVStrategy;


/**
 * The CSVTokenizer specifies the behaviour how the CSVReaderImpl parses each line into a List of Strings.
 */
public interface CSVTokenizer {
    
	public List<String> tokenizeLine(String line, CSVStrategy strategy, BufferedReader reader) throws IOException;
}
