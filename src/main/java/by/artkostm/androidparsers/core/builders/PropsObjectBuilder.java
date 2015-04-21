package by.artkostm.androidparsers.core.builders;

import static org.reflections.ReflectionUtils.getAllFields;
import static org.reflections.ReflectionUtils.withAnnotation;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Set;

import static by.artkostm.androidparsers.core.FieldsResolver.*;
import by.artkostm.androidparsers.core.annotations.Property;

public class PropsObjectBuilder<T> implements Builder<T> {
    
    private Class<T> type;
    
    public PropsObjectBuilder(Class<T> type) {
        this.type = type;
    }

    @SuppressWarnings("unchecked")
    @Override
    public T build(Object source) {
        List<String> props = (List<String>)source;
        T instance = null;
        try {
            instance = type.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            throw new RuntimeException("Cannot get new instance by default constructor");
        }
        
        Set<Field> fields = getAllFields(type, withAnnotation(Property.class)); 
        
        for(String prop : props){
            String[] v = prop.split("=");
            if(v.length != 2){
                throw new RuntimeException("Cannot read property " + v[0]);
            }
            String key = v[0].trim();
            String val = v[1];
            Field f = findField(fields, key);
            if(f != null){
                setField(instance, f, val);
            }
        }
        return instance;
    }
    
    private Field findField(Set<Field> fields, String key){
        for(Field f : fields){
            f.setAccessible(true);
            Property p = f.getAnnotation(Property.class);
            if(p.name().equals(key)){
                f.setAccessible(false);
                fields.remove(f);
                return f;
            }
            f.setAccessible(false);
        }
        return null;
    }
    
    private void setField(Object obj, Field f, String val){
        if(!f.isAccessible()){
            f.setAccessible(true);
        }
        try {
            f.set(obj, resolveProperty(f.getType(), val));
        } catch (IllegalArgumentException | IllegalAccessException e) {
            throw new RuntimeException("Cannot resolve value for field " + f.toGenericString());
        }finally{
            f.setAccessible(false);
        }
    }
}
