package by.artkostm.androidparsers.core.processors;

import by.artkostm.androidparsers.core.ValueProcessor;

public class LongProcessor implements ValueProcessor<Long>{

    @Override
    public Long processValue(String value){

        return (value == null) ? 0L : Long.parseLong(value);
    }
}
