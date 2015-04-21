package by.artkostm.androidparsers.core.enhancer;

import java.util.List;

import by.artkostm.androidparsers.core.builders.Builder;
import by.artkostm.androidparsers.core.builders.PropsObjectBuilder;

public class PropertyEnhancer implements Enhancer{
    
    private Class<?> type;
    
    public PropertyEnhancer(Class<?> type) {
        this.type = type;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Object enhance(Object source) {
        List<String> props = (List<String>)source;
        Builder<?> builder = new PropsObjectBuilder<>(type);
        Object obj = builder.build(props);
        return obj;
    }
}
