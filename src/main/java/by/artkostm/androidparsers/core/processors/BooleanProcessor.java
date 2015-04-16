package by.artkostm.androidparsers.core.processors;

import by.artkostm.androidparsers.core.ValueProcessor;

public class BooleanProcessor implements ValueProcessor<Boolean>{

    @Override
    public Boolean processValue(String value){
        
        return (value == null) ? false : Boolean.parseBoolean(value);
    }
}
