package by.artkostm.androidparsers.core.processors;

import by.artkostm.androidparsers.core.ValueProcessor;

public class DoubleProcessor implements ValueProcessor<Double>{

    @Override
    public Double processValue(String value){

        return (value == null) ? 0 : Double.parseDouble(value);
    }
}
