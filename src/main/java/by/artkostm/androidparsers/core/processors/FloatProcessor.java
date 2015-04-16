package by.artkostm.androidparsers.core.processors;

import by.artkostm.androidparsers.core.ValueProcessor;

public class FloatProcessor implements ValueProcessor<Float>{

    @Override
    public Float processValue(String value){

        return (value == null) ? 0F : Float.parseFloat(value);
    }
}
