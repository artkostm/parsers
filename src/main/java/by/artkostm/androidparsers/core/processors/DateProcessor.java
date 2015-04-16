package by.artkostm.androidparsers.core.processors;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.Date;

import by.artkostm.androidparsers.core.ValueProcessor;

public class DateProcessor implements ValueProcessor<Date>{
    
    private final DateFormat sdf;
    
    public DateProcessor(DateFormat format){
        sdf = format;
    }

    @Override
    public Date processValue(String value){

        Date result = null;
        try {
            result = sdf.parse(value);
        } catch (ParseException pe) {
            throw new IllegalArgumentException(value + " can not be parsed as a date", pe);
        }

        return result;
    }
}
