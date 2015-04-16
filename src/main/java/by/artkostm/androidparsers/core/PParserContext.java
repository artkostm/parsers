package by.artkostm.androidparsers.core;

import java.io.File;

import org.w3c.dom.Document;
import org.w3c.dom.Node;

import by.artkostm.androidparsers.core.builders.XMLElementBuilder;
import by.artkostm.androidparsers.core.enhancer.XMLElementEnhancer;
import by.artkostm.androidparsers.core.util.DOMUtil;

/**
 * 
 * @author Artsiom
 *
 */
public final class PParserContext {
    
    private PParserContext() {}
    
    private String pkg;
    
    public PParserContext setPackage(String pkg){
        this.pkg = pkg;
        return this;
    }
    
    private static class ContextHolder{
        private final static PParserContext instance = new PParserContext();
    }
    
    public static PParserContext getInstance(String pkg){
        ContextHolder.instance.pkg = pkg;
        return ContextHolder.instance;
    }
    
    public static PParserContext getInstance(){
        return ContextHolder.instance;
    }
    
    public static interface Marshaller<T>{
        public void marshal(T t, File file);
    }
    
    public static interface Unmarshaller<T>{
        public T unmarshal(File file);
    }
    
    public <T> Unmarshaller<T> getUnmarshaller(){
        final Unmarshaller<T> u = new Unmarshaller<T>() {
            @SuppressWarnings("unchecked")
            @Override
            public T unmarshal(File file) {
                Node root = DOMUtil.getDocument(file);
                XMLElementEnhancer e = new XMLElementEnhancer(pkg);
                Object t = e.enhance(root);
                return (T)t;
            }
        };
        return u;
    }
    
    public <T> Marshaller<T> getMarshaller(Class<?> cls){
        final Marshaller<T> m = new Marshaller<T>() {
            @Override
            public void marshal(T t, File file) {
                XMLElementBuilder builder = new XMLElementBuilder();
                Document doc = builder.build(t);
                DOMUtil.transformDOM(doc, file);
            }
        };
        return m;
    }
}
