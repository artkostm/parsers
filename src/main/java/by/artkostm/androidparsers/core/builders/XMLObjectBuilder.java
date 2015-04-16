package by.artkostm.androidparsers.core.builders;

import static org.reflections.ReflectionUtils.*;
import static by.artkostm.androidparsers.core.AttributeResolver.*;

import java.lang.reflect.Field;
import java.util.Set;

import org.apache.log4j.Logger;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import by.artkostm.androidparsers.core.annotations.XMLAttribute;
import by.artkostm.androidparsers.core.annotations.XMLElement;

public class XMLObjectBuilder<T> implements Builder<T>{
    
    private static final Logger log = Logger.getRootLogger();

    private final Class<T> type;
    
    public XMLObjectBuilder(Class<T> type) {
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
        
        for(Field f : getAllFields(type, withAnnotation(XMLAttribute.class))){
            f.setAccessible(true);
            try {
                final XMLAttribute atr = f.getAnnotation(XMLAttribute.class);
                f.set(instance, resolveAttribute(f.getType(), nnm, atr.name()));
            } catch (IllegalArgumentException | IllegalAccessException e) {
                log.error("Cannot set value for field "+f.toGenericString());
                throw new RuntimeException("Cannot set value for field "+f.toGenericString());
            }
        }
        return instance;
    }
}
