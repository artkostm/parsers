package by.artkostm.androidparsers.core;

import java.lang.reflect.InvocationTargetException;

import org.apache.log4j.Logger;
import org.w3c.dom.DOMException;
import org.w3c.dom.NamedNodeMap;

public final class AttributeResolver {
    
    private static final Logger log = Logger.getRootLogger();
    
    private AttributeResolver() {}
    
    private static final String NONE = "";
    
    public static String resolveStringValue(NamedNodeMap nnm, String attributeName){

        return (nnm.getNamedItem(attributeName) == null) ? NONE : nnm.getNamedItem(attributeName).getNodeValue();
    }
    
    public static Integer resolveIntValue(NamedNodeMap nnm, String attributeName){

        return (nnm.getNamedItem(attributeName) == null) ? 0 : Integer.parseInt(nnm.getNamedItem(attributeName).getNodeValue());
    }
    
    public static Double resolveDoubleValue(NamedNodeMap nnm, String attributeName){

        return (nnm.getNamedItem(attributeName) == null) ? 0 : Double.parseDouble(nnm.getNamedItem(attributeName).getNodeValue());
    }
    
    public static Boolean resolveBoolValue(NamedNodeMap nnm, String attributeName){

        return (nnm.getNamedItem(attributeName) == null) ? false : Boolean.parseBoolean(nnm.getNamedItem(attributeName).getNodeValue());
    }
    
    @SuppressWarnings("unchecked")
    public static <T> T resolveWithType(Class<T> type, NamedNodeMap nnm, String attributeName){
        
        if (type.getName().equals(Double.class.getName())){
            return (T)resolveDoubleValue(nnm, attributeName);
        }
        
        if (type.getName().equals(Integer.class.getName())){
            return (T)resolveIntValue(nnm, attributeName);
        }
        
        if (type.getName().equals(Boolean.class.getName())){
            return (T)resolveBoolValue(nnm, attributeName);
        }
        
        try {
            return (T)((nnm.getNamedItem(attributeName) == null) ? type.newInstance() : 
                type.getConstructor(new Class[] {String.class }).newInstance(nnm.getNamedItem(attributeName).getNodeValue()));
        } catch (InstantiationException e) {
            log.error(e,e);
        } catch (IllegalAccessException e) {
            log.error(e,e);
        } catch (DOMException e) {
            log.error(e,e);
        } catch (IllegalArgumentException e) {
            log.error(e,e);
        } catch (InvocationTargetException e) {
            log.error(e,e);
        } catch (NoSuchMethodException e) {
            log.error(e,e);
        } catch (SecurityException e) {
            log.error(e,e);
        }
        return null;
    }
}
