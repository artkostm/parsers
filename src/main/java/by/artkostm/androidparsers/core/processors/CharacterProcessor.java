package by.artkostm.androidparsers.core.processors;

import by.artkostm.androidparsers.core.ValueProcessor;

public class CharacterProcessor implements ValueProcessor<Character>{

    @Override
    public Character processValue(String value){

        if (value == null || value.length() != 1) {
            throw new IllegalArgumentException(String.format("%s is not a valud character, it's length must be 1", value));
        }

        return value.charAt(0);
    }
}
