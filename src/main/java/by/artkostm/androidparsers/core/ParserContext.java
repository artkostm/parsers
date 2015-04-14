package by.artkostm.androidparsers.core;

import java.io.File;

import org.w3c.dom.Document;
import org.w3c.dom.Node;

import by.artkostm.androidparsers.core.builders.ElementBuilder;
import by.artkostm.androidparsers.core.enhancer.ElementEnhancer;
import by.artkostm.androidparsers.core.util.DOMUtil;

/**
 * 
 * @author Artsiom
 *
 */
public final class ParserContext {
    
    private ParserContext() {}
    
    private String pkg;
    
    public ParserContext setPackage(String pkg){
        this.pkg = pkg;
        return this;
    }
    
    private static class ContextHolder{
        private final static ParserContext instance = new ParserContext();
    }
    
    public static ParserContext getInstance(String pkg){
        ContextHolder.instance.pkg = pkg;
        return ContextHolder.instance;
    }
    
    public static ParserContext getInstance(){
        return ContextHolder.instance;
    }
    
    public static interface Marshaller<T>{
        public void marshal(T t, File file);
    }
    
    public static interface Unmarshaller<T>{
        public T unmarshal(File file);
    }
    
    public <T> Unmarshaller<T> getUnmarshaller(){
        Unmarshaller<T> u = new Unmarshaller<T>() {
            @SuppressWarnings("unchecked")
            @Override
            public T unmarshal(File file) {
                Node root = DOMUtil.getDocument(file);
                ElementEnhancer e = new ElementEnhancer(pkg);
                Object t = e.enhance(root);
                return (T)t;
            }
        };
        return u;
    }
    
    public <T> Marshaller<T> getMarshaller(Class<?> cls){
        Marshaller<T> m = new Marshaller<T>() {
            @Override
            public void marshal(T t, File file) {
                ElementBuilder builder = new ElementBuilder();
                Document doc = builder.build(t);
                DOMUtil.transformDOM(doc, file);
            }
        };
        return m;
    }
}
