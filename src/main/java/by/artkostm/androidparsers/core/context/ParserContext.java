package by.artkostm.androidparsers.core.context;

import java.io.File;

public interface ParserContext{
    public static interface Marshaller<T>{
        public void marshal(T t, File file);
    }
    
    public static interface Unmarshaller<T>{
        public T unmarshal(File file);
    }

    public <T> Unmarshaller<T> getUnmarshaller(Class<?> ... cls);
    public <T> Marshaller<T> getMarshaller(Class<?> cls);
}
