package by.artkostm.androidparsers.core.enhancer;

import static org.reflections.ReflectionUtils.getAllFields;
import static org.reflections.ReflectionUtils.withAnnotation;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.scanners.TypeAnnotationsScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import by.artkostm.androidparsers.core.annotations.DOMElement;
import by.artkostm.androidparsers.core.annotations.DOMElements;
import by.artkostm.androidparsers.core.builders.ObjectBuilder;

public class ElementEnhancer {
    
    private static final Logger log = Logger.getRootLogger();
    
    private Reflections reflections;
    
    private Set<Class<?>> classes;
    
    public ElementEnhancer(String pkg) {
        reflections = new Reflections(new ConfigurationBuilder()
                                                .setUrls(ClasspathHelper.forPackage(pkg))
                                                .setScanners(new SubTypesScanner(), 
                                                        new TypeAnnotationsScanner()));
        
        classes = reflections.getTypesAnnotatedWith(DOMElement.class);
    }
    
    @SuppressWarnings("rawtypes")
    public Object enhance(Node node){
        Class<?> croot = findRootElement(node);
        ObjectBuilder builder = new ObjectBuilder<>(croot);
        Object root = builder.build(node);
        NodeList nl = node.getChildNodes();
        if (nl.getLength() > 0){
            for(int i = 0; i < nl.getLength(); i++){
                if(nl.item(i) instanceof Element){
                    Object el = enhance(nl.item(i));
                    processElement(croot, root, el);
                }
            }
        }
        return root;
    }
    
    @SuppressWarnings({ "unchecked", "rawtypes" })
    private void processElement(Class<?> classParent, Object parent, Object child){
        Set<Field> fields = getAllFields(classParent, withAnnotation(DOMElements.class));
        if (fields.size() != 1){
            return;
        }
        Field f = (Field)fields.toArray()[0];
        f.setAccessible(true);
        try {
            DOMElements els = f.getAnnotation(DOMElements.class);
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
                log.error("Cannot add element to collection");
                throw new RuntimeException("Cannot add element to collection");
            }
            Collection list = (Collection)f.get(parent);
            list.add(child);
        } catch (IllegalArgumentException | IllegalAccessException | SecurityException e) {
            log.error("Cannot set value for generic type "+f.toGenericString(), e);
            throw new RuntimeException("Cannot set value for generic type "+f.toGenericString());
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
            if(c.getSimpleName().equals(node.getNodeName())){
                return c;
            }
        }
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
