package by.artkostm.androidparsers.core.processors;

import by.artkostm.androidparsers.core.ValueProcessor;

public class IntegerProcessor implements ValueProcessor<Integer>{

    @Override
    public Integer processValue(String value){

        return (value == null) ? 0 : Integer.parseInt(value);
    }
}
