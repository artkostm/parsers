package by.artkostm.androidparsers.core.context.internal;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Document;
import org.w3c.dom.Node;

import by.artkostm.androidparsers.core.builders.XMLElementBuilder;
import by.artkostm.androidparsers.core.context.ParserContext;
import by.artkostm.androidparsers.core.enhancer.XMLElementEnhancer;
import by.artkostm.androidparsers.core.util.DOMUtil;
import by.artkostm.androidparsers.core.util.Startegy;

public class XMLParserContext implements ParserContext
{
    private final String pkg;
    
    public XMLParserContext(String pkg)
    {
        this.pkg = pkg;
    }

    @Override
    public <T> Unmarshaller<T> getUnmarshaller(Class<?>...cls)
    {
        final Unmarshaller<T> u = new Unmarshaller<T>() {
            @SuppressWarnings("unchecked")
            @Override
            public T unmarshal(File file) {
                Node root = DOMUtil.getDocument(file);
                XMLElementEnhancer e = new XMLElementEnhancer(pkg);
                Object t = e.enhance(root);
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
        final Marshaller<T> m = new Marshaller<T>() {
            @Override
            public void marshal(T t, File file) {
                XMLElementBuilder builder = new XMLElementBuilder();
                Document doc = builder.build(t);
                DOMUtil.transformDOM(doc, file);
            }

            @Override
            public void setStrategy(Startegy s){}

            @Override
            public void marshalAll(List<T> t, File file){}
        };
        return m;
    }

}
