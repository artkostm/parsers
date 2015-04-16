package by.artkostm.androidparsers.core.processors;

import by.artkostm.androidparsers.core.ValueProcessor;

public class ShortProcessor implements ValueProcessor<Short>{

    @Override
    public Short processValue(String value){

        return (value == null ? 0 : Short.parseShort(value));
    }
}
