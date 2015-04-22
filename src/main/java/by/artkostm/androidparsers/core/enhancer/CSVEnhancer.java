package by.artkostm.androidparsers.core.enhancer;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import by.artkostm.androidparsers.core.builders.Builder;
import by.artkostm.androidparsers.core.builders.CSVObjectBuilder;
import by.artkostm.androidparsers.core.reader.CSVTokenizer;
import by.artkostm.androidparsers.core.reader.internal.CSVTokenizerImpl;
import by.artkostm.androidparsers.core.util.CSVStrategy;

public class CSVEnhancer implements Enhancer{

    private Class<?> type;
    private CSVStrategy strategy = CSVStrategy.UK_DEFAULT;
    private BufferedReader reader;
    private final CSVTokenizer tokenizer;

    private boolean firstLineRead = false;
    
    public CSVEnhancer(Class<?> type, CSVStrategy s){
        this.type = type;
        if(s != null){
            strategy = s;
        }
        tokenizer = new CSVTokenizerImpl();
    }
    
    @Override
    public Object enhance(Object source)
    {
        try{
            this.reader = new BufferedReader(new FileReader((File)source));
        }catch (FileNotFoundException e){
            throw new RuntimeException("Can't read a file");
        }
        Builder<?> builder = new CSVObjectBuilder<>(type);
        String[] data = null;
        List<Object> list = new ArrayList<>();
        try{
            while((data = readNext()) != null){
                Object obj = builder.build(data);
                list.add(obj);
            }
        }catch (IOException e){
            throw new RuntimeException("Can't read a file");
        }
        close();
        return list;
    }
    
    private String[] readNext() throws IOException {
        if (strategy.isSkipHeader() && !firstLineRead) {
            reader.readLine();
        }
        List<String> data = null;
        boolean validEntry = false;
        do {
            String line = readLine();
            if (line == null) {
                return null;
            }

            if (line.trim().length() == 0 && strategy.isIgnoreEmptyLines()) {
                continue;
            }

            if (isCommentLine(line)) {
                continue;
            }

            data = tokenizer.tokenizeLine(line, strategy, reader);

            validEntry = true;
        } while (!validEntry);

        firstLineRead = true;

        return (String[])data.toArray(new String[]{});
    }

    @SuppressWarnings("unused")
    private String[] readHeader() throws IOException {
        if (firstLineRead) {
            throw new IllegalStateException("can not read header, readHeader() must be the first call on this reader");
        }

        String line = readLine();
        if (line == null) {
            throw new IllegalStateException("reached EOF while reading the header");
        }

        List<String> header = tokenizer.tokenizeLine(line, strategy, reader);
        return (String[])header.toArray(new String[]{});
    }
    
    private String readLine() throws IOException {
        String line = reader.readLine();
        firstLineRead = true;
        return line;
    }
    
    private void close(){
        try{
            if(reader != null) reader.close();
        }catch (IOException e){}
    }

    private boolean isCommentLine(String line) {
        return line.startsWith(String.valueOf(strategy.getCommentIndicator()));
    }
}
