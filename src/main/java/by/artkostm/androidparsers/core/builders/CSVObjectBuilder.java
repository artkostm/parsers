package by.artkostm.androidparsers.core.builders;

import static by.artkostm.androidparsers.core.FieldsResolver.resolveProperty;

import java.lang.reflect.Field;

import by.artkostm.androidparsers.core.annotations.csv.MapToColumn;

public class CSVObjectBuilder<T> implements Builder<T>{
    
    private Class<T> clazz;
    
    public CSVObjectBuilder(Class<T> clazz){
        this.clazz = clazz;
    }

    @Override
    public T build(Object source)
    {
        T entry = newClassIntance();
        fillObject(entry, (String[])source);
        return entry;
    }
    
    private T newClassIntance() {
        T entry;
        try {
            entry = clazz.newInstance();
        } catch (InstantiationException ie) {
            throw new RuntimeException(String.format("can not instantiate class %s", clazz.getName()), ie);
        } catch (IllegalAccessException iae) {
            throw new RuntimeException(String.format("can not access class %s", clazz.getName()), iae);
        }

        return entry;
    }
    
    private void fillObject(T entry, String[] data) {
        for (Field field : entry.getClass().getDeclaredFields()) {
            MapToColumn mapAnnotation = field.getAnnotation(MapToColumn.class);
            if (mapAnnotation != null) {
                int column = mapAnnotation.column();
                Class<?> type;
                if (mapAnnotation.type().equals(MapToColumn.Default.class)) {
                    type = field.getType();
                } else {
                    type = mapAnnotation.type();
                }
                boolean wasAccessible = field.isAccessible();
                field.setAccessible(true);
                Object value = resolveProperty(type, data[column]);
                try {
                    field.set(entry, value);
                } catch (IllegalArgumentException iae) {
                    throw new RuntimeException(String.format("can not set value %s for type %s", value, type), iae);
                } catch (IllegalAccessException iae) {
                    throw new RuntimeException(String.format("can not access field %s", field), iae);
                }
                field.setAccessible(wasAccessible);
            }
        }
    }

}
