package by.artkostm.androidparsers.core.enhancer;

import static org.reflections.ReflectionUtils.getAllFields;
import static org.reflections.ReflectionUtils.withAnnotation;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.reflections.ReflectionUtils;
import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.scanners.TypeAnnotationsScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import by.artkostm.androidparsers.core.annotations.XMLElement;
import by.artkostm.androidparsers.core.annotations.XMLElements;
import by.artkostm.androidparsers.core.builders.XMLObjectBuilder;

public class XMLElementEnhancer implements Enhancer{
    
    private static final Logger log = Logger.getRootLogger();
    
    private Reflections reflections;
    
    private Set<Class<?>> classes;
    
    public XMLElementEnhancer(String pkg) {
        reflections = new Reflections(new ConfigurationBuilder()
                                                .setUrls(ClasspathHelper.forPackage(pkg))
                                                .setScanners(new SubTypesScanner(), 
                                                        new TypeAnnotationsScanner()));
        
        classes = reflections.getTypesAnnotatedWith(XMLElement.class);
    }
    
    @SuppressWarnings("rawtypes")
    @Override
    public Object enhance(Object source){
        Node node = (Node) source;
        Class<?> croot = findRootElement(node);
        XMLObjectBuilder builder = new XMLObjectBuilder<>(croot);
        Object root = builder.build(node);
        NodeList nl = node.getChildNodes();
        if (nl.getLength() > 0){
            for(int i = 0; i < nl.getLength(); i++){
                if(nl.item(i) instanceof Element){
                    Field ref = null;
                    if(isElement(croot, nl.item(i).getNodeName(), ref)){
                        Object el = enhance(nl.item(i));
                        processElement(croot, root, el, ref);
                    }else{
                        Object el = enhance(nl.item(i));
                        processElements(croot, root, el);
                    }
                }
            }
        }
        return root;
    }
    
    @SuppressWarnings("unchecked")
    private boolean isElement(Class<?> classParent, String childName, Field ref){
        Set<Field> fields = getAllFields(classParent, withAnnotation(XMLElement.class));
        for(Field f : fields){
            XMLElement el = f.getAnnotation(XMLElement.class);
            if(childName.equals(el.name())){
                ref = f;
                return true;
            }
        }
        return false;
    }
    
    private void processElement(Class<?> classParent, Object parent, Object child, Field ref){
        ref.setAccessible(true);
        try {
            ref.set(parent, child);
        } catch (IllegalArgumentException | SecurityException | IllegalAccessException e) {
            log.error("Cannot set value for object  "+ref.toGenericString(), e);
            throw new RuntimeException("Cannot set value for object "+ref.toGenericString());
        }
        finally{
            ref.setAccessible(false);
        }
    }
    
    @SuppressWarnings({ "unchecked", "rawtypes" })
    private void processElements(Class<?> classParent, Object parent, Object child){
        Set<Field> fieldsForElements = getAllFields(classParent, withAnnotation(XMLElements.class));
        if (fieldsForElements.size() != 1){
            return;
        }
        Field f = (Field)fieldsForElements.toArray()[0];
        f.setAccessible(true);
        try {
            XMLElements els = f.getAnnotation(XMLElements.class);
            Class<?> base = els.baseType();
            List<Class<?>> parents = new ArrayList<>();
            getParentClasses(child.getClass(), parents);
            boolean extended = base == child.getClass();
            for (Class<?> p : parents){
                if (p == base){
                    extended = true;
                }
            }
            if (!extended){
                log.warn("Cannot add element to collection");
                return;
            }
            Collection list = (Collection)f.get(parent);
            list.add(child);
        } catch (IllegalArgumentException | IllegalAccessException | SecurityException e) {
            log.error("Cannot set value for generic type "+f.toGenericString(), e);
            throw new RuntimeException("Cannot set value for generic type "+f.toGenericString());
        }
        finally{
            f.setAccessible(false);
        }
    }
    
    /**
     * To find root element in current node
     * 
     * @param node
     * @return class of found element if exists
     */
    private Class<?> findRootElement(Node node){
        for(Class<?> c : classes){
            XMLElement el = c.getAnnotation(XMLElement.class);
            if(el == null){
                log.error("No class definitiof found for "+node.getNodeName()+" element");
                throw new RuntimeException("No class definitiof found for "+node.getNodeName()+" element");
            }
            if(el.name().equals(node.getNodeName())){
                return c;
            }
        }
        /**
         * !!!
         * !!!
         * !!!
         * Check fields marked with XMLElement annotation !!!!
         * !!!
         * !!!
         * !!!
         */
        log.error("No class definitiof found for "+node.getNodeName()+" element");
        throw new RuntimeException("No class definitiof found for "+node.getNodeName()+" element");
    }
    
    /**
     * 
     * @param cls
     * @param list collection of the all super classes
     * @return the most superclass excluding Object class
     */
    public static Class<?> getParentClasses(Class<?> cls, List<Class<?>> list){
        if(cls.getSuperclass() == null || cls.getSuperclass() == Object.class){
            return cls;
        }
        list.add(cls.getSuperclass());
        return getParentClasses(cls.getSuperclass(), list);
    }
}
