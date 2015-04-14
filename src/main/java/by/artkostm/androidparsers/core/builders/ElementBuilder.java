package by.artkostm.androidparsers.core.builders;

import static org.reflections.ReflectionUtils.getAllFields;
import static org.reflections.ReflectionUtils.withAnnotation;

import java.lang.reflect.Field;
import java.util.Collection;

import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import by.artkostm.androidparsers.core.annotations.DOMAttribute;
import by.artkostm.androidparsers.core.annotations.DOMElement;
import by.artkostm.androidparsers.core.annotations.DOMElements;
import by.artkostm.androidparsers.core.util.DOMUtil;

public class ElementBuilder implements Builder<Document>{

    private static final Logger log = Logger.getRootLogger();
    
    @Override
    public Document build(Object source) {
        Document doc = DOMUtil.createDocument();
        Element element = null;
        try {
            element = createElement(doc, source);
        } catch (IllegalArgumentException | IllegalAccessException e) {
            log.error("Can't create DOM element", e);
            throw new RuntimeException("Can't create DOM element", e);
        }
        doc.appendChild(element);
        return doc;
    }
    
    @SuppressWarnings({ "unchecked", "rawtypes" })
    private Element createElement(Document doc, Object obj) throws IllegalArgumentException, IllegalAccessException{
        Class<?> root = obj.getClass();
        if (!obj.getClass().isAnnotationPresent(DOMElement.class)){
            throw new RuntimeException("Can't marshal object with " + obj.getClass());
        }
        DOMElement ael = root.getAnnotation(DOMElement.class);
        Element node = doc.createElement(ael.name());
        
        for(Field f : getAllFields(root, withAnnotation(DOMAttribute.class))){
            f.setAccessible(true);
            final DOMAttribute attr = f.getAnnotation(DOMAttribute.class);
            node.setAttribute(attr.name(), String.valueOf(f.get(obj)));
        }
        
        for(Field f : getAllFields(root, withAnnotation(DOMElements.class))){
            f.setAccessible(true);
            final Collection c = (Collection) f.get(obj);
            for(Object o : c){
                  node.appendChild(createElement(doc, o));
            }
        }
        return node;
    }
}
