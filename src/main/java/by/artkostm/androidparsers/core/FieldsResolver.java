package by.artkostm.androidparsers.core;

import org.w3c.dom.NamedNodeMap;

import by.artkostm.androidparsers.core.processors.internal.ValueProcessorProvider;

public final class FieldsResolver {
    
    private FieldsResolver() {}
    
    public static <T> T resolveAttribute(Class<T> type, NamedNodeMap nnm, String attributeName){
        final ValueProcessorProvider provider = ValueProcessorProvider.getInstance();
        ValueProcessor<T> processor = provider.getValueProcessor(type);
        try{
            if(nnm.getNamedItem(attributeName) == null){
                return processor.processValue(null);
            }else{
                return processor.processValue(nnm.getNamedItem(attributeName).getNodeValue());
            }
        }catch(IllegalArgumentException e){
            return null;
        }
    }
    
    public static <T> T resolveProperty(Class<T> type, String propValue){
        final ValueProcessorProvider provider = ValueProcessorProvider.getInstance();
        ValueProcessor<T> processor = provider.getValueProcessor(type);
        try{
            return processor.processValue(propValue);
        }catch(IllegalArgumentException e){
            return null;
        }
    }
}
