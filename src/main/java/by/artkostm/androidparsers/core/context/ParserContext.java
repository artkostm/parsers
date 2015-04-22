package by.artkostm.androidparsers.core.context;

import java.io.File;
import java.util.List;

import by.artkostm.androidparsers.core.util.Startegy;

public interface ParserContext{
    public static interface Marshaller<T>{
        public void marshal(T t, File file);
        public void marshalAll(List<T> t, File file);
        public void setStrategy(Startegy s);
    }
    
    public static interface Unmarshaller<T>{
        public T unmarshal(File file);
        public List<T> unmarshalAll(File file);
        public void setStrategy(Startegy s);
    }

    public <T> Unmarshaller<T> getUnmarshaller(Class<?> ... cls);
    public <T> Marshaller<T> getMarshaller(Class<?> cls);
}
