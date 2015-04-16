package by.artkostm.androidparsers.core;

import java.util.HashMap;
import java.util.Map;

import by.artkostm.androidparsers.core.context.ParserContext;
import by.artkostm.androidparsers.core.context.internal.CSVParserContext;
import by.artkostm.androidparsers.core.context.internal.PropsParserContext;
import by.artkostm.androidparsers.core.context.internal.XMLParserContext;

public abstract class ParserContextFactory{
    
    public static enum Type{
        XML, CSV, PROPS, CUSTOM();
        
        private String customType;
        
        private Type(){}
        
        private Type(String type){
            customType = type;
        }

        public String getCustomType()
        {
            return customType;
        }
    }
    
    private static class FactoryHolder{
        private final static ParserContextFactory instance = new ParserContextFactoryImpl();
    }
    
    public static ParserContextFactory getInstance(){
        return FactoryHolder.instance;
    }
    
    public abstract ParserContext getParserContext(Type type, String pkg);
    
    public abstract void registerCustomParserContext(Type type, ParserContext context);
    
    private static final class ParserContextFactoryImpl extends ParserContextFactory{
        @SuppressWarnings("unused")
        private static Map<Type, ParserContext> customParsers = new HashMap<ParserContextFactory.Type, ParserContext>();
        
        @Override
        public ParserContext getParserContext(Type type, String pkg)
        {
            switch (type)
            {
                case XML:
                    return new XMLParserContext(pkg);
                case CSV:
                    return new CSVParserContext(pkg);
                case PROPS:
                    return new PropsParserContext(pkg);
                case CUSTOM:
                    return null;
                default:
                    return null;
            }
        }

        @Override
        public void registerCustomParserContext(Type type, ParserContext context){}
    }
}
