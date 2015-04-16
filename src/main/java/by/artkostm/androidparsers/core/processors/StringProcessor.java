package by.artkostm.androidparsers.core.processors;

import by.artkostm.androidparsers.core.ValueProcessor;

public class StringProcessor implements ValueProcessor<String>{

    @Override
    public String processValue(String value){
        
        return (value == null ? "" : value);
    }
}
