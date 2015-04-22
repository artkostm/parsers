package by.artkostm.androidparsers.core.context.internal;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import by.artkostm.androidparsers.core.context.ParserContext;
import by.artkostm.androidparsers.core.enhancer.Enhancer;
import by.artkostm.androidparsers.core.enhancer.PropertyEnhancer;
import by.artkostm.androidparsers.core.util.Startegy;

public class PropsParserContext implements ParserContext
{
    @SuppressWarnings("unused")
    private final String pkg;
    
    public PropsParserContext(String pkg)
    {
        this.pkg = pkg;
    }

    @Override
    public <T> Unmarshaller<T> getUnmarshaller(final Class<?>...cls)
    {
        final Unmarshaller<T> u = new Unmarshaller<T>() {
            @SuppressWarnings("unchecked")
            @Override
            public T unmarshal(File file) {
                Object t = null;
                Enhancer e = new PropertyEnhancer(cls[0]);
                t = e.enhance(file);
                return (T)t;
            }

            @Override
            public void setStrategy(Startegy s){}

            @Override
            public List<T> unmarshalAll(File file){
                List<T> list = new ArrayList<>();
                list.add(unmarshal(file));
                return list;
            }
        };
        return u;
    }

    @Override
    public <T> Marshaller<T> getMarshaller(Class<?> cls)
    {
        // TODO Auto-generated method stub
        return null;
    }

}
