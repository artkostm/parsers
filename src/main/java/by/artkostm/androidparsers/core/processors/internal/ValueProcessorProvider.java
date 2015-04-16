package by.artkostm.androidparsers.core.processors.internal;

import java.text.DateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import by.artkostm.androidparsers.core.ValueProcessor;
import by.artkostm.androidparsers.core.processors.BooleanProcessor;
import by.artkostm.androidparsers.core.processors.ByteProcessor;
import by.artkostm.androidparsers.core.processors.CharacterProcessor;
import by.artkostm.androidparsers.core.processors.DateProcessor;
import by.artkostm.androidparsers.core.processors.DoubleProcessor;
import by.artkostm.androidparsers.core.processors.FloatProcessor;
import by.artkostm.androidparsers.core.processors.IntegerProcessor;
import by.artkostm.androidparsers.core.processors.LongProcessor;
import by.artkostm.androidparsers.core.processors.ShortProcessor;
import by.artkostm.androidparsers.core.processors.StringProcessor;

public class ValueProcessorProvider{
    
    private static class ProviderHolder{
        private static final ValueProcessorProvider instance = new ValueProcessorProvider();
    }
    
    public static ValueProcessorProvider getInstance(){
        return ProviderHolder.instance;
    }
    
    private final Map<Class<?>, ValueProcessor<?>> processors = new HashMap<>();
    private final Map<Class<?>, Class<?>> primitiveWrapperTypes = new HashMap<>();
    
    private ValueProcessorProvider()
    {
        fillPrimitiveWrapperTypesMap();
        registerDefaultValueProcessors();
    }
    
    public <E> void registerValueProcessor(Class<E> clazz, ValueProcessor<? extends E> processor) {
        
        if (clazz.isPrimitive()) {
            throw new IllegalArgumentException(
                    "can not register value processor for a primitive type, register it for the wrapper type instead");
        }

        if (processors.containsKey(clazz)) {
            throw new IllegalArgumentException(String.format(
                    "can not register value processor for %s, it is already registered.", clazz));
        }

        processors.put(clazz, processor);
    }
    
    public <E> void removeValueProcessor(Class<E> clazz) {
        
        if (!processors.containsKey(clazz)) {
            throw new IllegalArgumentException(String.format(
                    "can not remove value processor for %s, it is not registered yet.", clazz));
        }

        processors.remove(clazz);
    }
    
    @SuppressWarnings("unchecked")
    public <E> ValueProcessor<E> getValueProcessor(Class<E> clazz) {
        
        if (clazz.isPrimitive()) {
            // this cast is safe
            clazz = (Class<E>) primitiveWrapperTypes.get(clazz);
        }

        if (!processors.containsKey(clazz)) {
            throw new IllegalArgumentException(String.format("no value processor registered for %s.", clazz));
        }

        // this cast is safe due to the registerValueProcessor method
        return ((ValueProcessor<E>) processors.get(clazz));
    }
    
    private void registerDefaultValueProcessors(){
        registerValueProcessor(String.class, new StringProcessor());
        registerValueProcessor(Boolean.class, new BooleanProcessor());
        registerValueProcessor(Byte.class, new ByteProcessor());
        registerValueProcessor(Character.class, new CharacterProcessor());
        registerValueProcessor(Double.class, new DoubleProcessor());
        registerValueProcessor(Float.class, new FloatProcessor());
        registerValueProcessor(Integer.class, new IntegerProcessor());
        registerValueProcessor(Long.class, new LongProcessor());
        registerValueProcessor(Short.class, new ShortProcessor());
        registerValueProcessor(Date.class, new DateProcessor(DateFormat.getDateInstance()));
    }
    
    private void fillPrimitiveWrapperTypesMap(){
        primitiveWrapperTypes.put(boolean.class, Boolean.class);
        primitiveWrapperTypes.put(byte.class, Byte.class);
        primitiveWrapperTypes.put(char.class, Character.class);
        primitiveWrapperTypes.put(double.class, Double.class);
        primitiveWrapperTypes.put(float.class, Float.class);
        primitiveWrapperTypes.put(int.class, Integer.class);
        primitiveWrapperTypes.put(long.class, Long.class);
        primitiveWrapperTypes.put(short.class, Short.class);
    }
}
