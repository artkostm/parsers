package by.artkostm.androidparsers.core.builders;

import static org.reflections.ReflectionUtils.*;
import static by.artkostm.androidparsers.core.AttributeResolver.*;

import java.lang.reflect.Field;

import org.apache.log4j.Logger;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

import by.artkostm.androidparsers.core.annotations.DOMAttribute;

public class ObjectBuilder<T> implements Builder<T>{
    
    private static final Logger log = Logger.getRootLogger();

    private final Class<T> type;
    
    public ObjectBuilder(Class<T> type) {
        this.type = type;
    }
    
    @SuppressWarnings("unchecked")
    @Override
    public T build(Object obj){
        Node node = (Node)obj;
        
        final NamedNodeMap nnm = node.getAttributes();
        
        T instance = null;
        try {
            instance = type.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            log.error("Cannot get new instance by default constructor");
            throw new RuntimeException("Cannot get new instance by default constructor");
        }
        
        for(Field f : getAllFields(type, withAnnotation(DOMAttribute.class))){
            f.setAccessible(true);
            try {
                final DOMAttribute atr = f.getAnnotation(DOMAttribute.class);
                f.set(instance, resolveWithType(f.getType(), nnm, atr.name()));
            } catch (IllegalArgumentException | IllegalAccessException e) {
                log.error("Cannot set value for field "+f.toGenericString());
                throw new RuntimeException("Cannot set value for field "+f.toGenericString());
            }
        }
        return instance;
    }
}
