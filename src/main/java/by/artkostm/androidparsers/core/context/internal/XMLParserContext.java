package by.artkostm.androidparsers.core.context.internal;

import java.io.File;

import org.w3c.dom.Document;
import org.w3c.dom.Node;

import by.artkostm.androidparsers.core.builders.XMLElementBuilder;
import by.artkostm.androidparsers.core.context.ParserContext;
import by.artkostm.androidparsers.core.enhancer.XMLElementEnhancer;
import by.artkostm.androidparsers.core.util.DOMUtil;

public class XMLParserContext implements ParserContext
{
    private final String pkg;
    
    public XMLParserContext(String pkg)
    {
        this.pkg = pkg;
    }

    @Override
    public <T> Unmarshaller<T> getUnmarshaller()
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
        };
        return m;
    }

}
