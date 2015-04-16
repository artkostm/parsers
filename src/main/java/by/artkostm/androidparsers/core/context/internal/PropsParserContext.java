package by.artkostm.androidparsers.core.context.internal;

import by.artkostm.androidparsers.core.context.ParserContext;

public class PropsParserContext implements ParserContext
{
    private final String pkg;
    
    public PropsParserContext(String pkg)
    {
        this.pkg = pkg;
    }

    @Override
    public <T> Unmarshaller<T> getUnmarshaller()
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public <T> Marshaller<T> getMarshaller(Class<?> cls)
    {
        // TODO Auto-generated method stub
        return null;
    }

}
