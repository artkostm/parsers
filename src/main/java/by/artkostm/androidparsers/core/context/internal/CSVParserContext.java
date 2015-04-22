package by.artkostm.androidparsers.core.context.internal;

import java.io.File;
import java.util.List;

import by.artkostm.androidparsers.core.context.ParserContext;
import by.artkostm.androidparsers.core.enhancer.CSVEnhancer;
import by.artkostm.androidparsers.core.util.CSVStrategy;
import by.artkostm.androidparsers.core.util.Startegy;

public class CSVParserContext implements ParserContext{
    @SuppressWarnings("unused")
    private final String pkg;
    
    public CSVParserContext(String pkg){
        this.pkg = pkg;
    }

    @Override
    public <T> Unmarshaller<T> getUnmarshaller(final Class<?>...cls){
        final Unmarshaller<T> u = new Unmarshaller<T>() {
            private CSVStrategy strategy;
            @SuppressWarnings("unchecked")
            @Override
            public T unmarshal(File file) {
                CSVEnhancer enhancer = new CSVEnhancer(cls[0], strategy);
                List<T> list = (List<T>) enhancer.enhance(file);
                return list.get(0);
            }

            @Override
            public void setStrategy(Startegy s){
                strategy = (CSVStrategy)s;
            }

            @SuppressWarnings("unchecked")
            @Override
            public List<T> unmarshalAll(File file){
                CSVEnhancer enhancer = new CSVEnhancer(cls[0], strategy);
                List<T> list = (List<T>) enhancer.enhance(file);
                return list;
            }
        };
        return u;
    }

    @Override
    public <T> Marshaller<T> getMarshaller(Class<?> cls){
        // TODO Auto-generated method stub
        return null;
    }

}
