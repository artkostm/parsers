package by.artkostm.androidparsers.core.processors;

import by.artkostm.androidparsers.core.ValueProcessor;

public class ByteProcessor implements ValueProcessor<Byte>{

    @Override
    public Byte processValue(String value){

        return (value == null ? 0 : Byte.parseByte(value));
    }

}
