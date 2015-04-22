package by.artkostm.androidparsers.core.builders;

import static org.reflections.ReflectionUtils.getAllFields;
import static org.reflections.ReflectionUtils.withAnnotation;

import java.lang.reflect.Field;
import java.util.Collection;

import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import by.artkostm.androidparsers.core.annotations.xml.XMLAttribute;
import by.artkostm.androidparsers.core.annotations.xml.XMLElement;
import by.artkostm.androidparsers.core.annotations.xml.XMLElements;
import by.artkostm.androidparsers.core.util.DOMUtil;

public class XMLElementBuilder implements Builder<Document>{

    private static final Logger log = Logger.getRootLogger();
    
    @Override
    public Document build(Object source) {
        Document doc = DOMUtil.createDocument();
        Element element = null;
        try {
            element = createElement(doc, source, false, null);
        } catch (IllegalArgumentException | IllegalAccessException e) {
            log.error("Can't create DOM element", e);
            throw new RuntimeException("Can't create DOM element", e);
        }
        doc.appendChild(element);
        return doc;
    }
    
    @SuppressWarnings({ "unchecked", "rawtypes" })
    private Element createElement(Document doc, Object obj, boolean isComposite, String name) throws IllegalArgumentException, IllegalAccessException{
        Class<?> root = obj.getClass();
        if (!obj.getClass().isAnnotationPresent(XMLElement.class)){
            throw new RuntimeException("Can't marshal object with " + obj.getClass());
        }
        Element node = null;
        if(isComposite){
            node = doc.createElement(name);
        }else{
            XMLElement ael = root.getAnnotation(XMLElement.class);
            node = doc.createElement(ael.name());
        }
        
        for(Field f : getAllFields(root, withAnnotation(XMLAttribute.class))){
            f.setAccessible(true);
            final XMLAttribute attr = f.getAnnotation(XMLAttribute.class);
            node.setAttribute(attr.name(), String.valueOf(f.get(obj)));
        }
        
        for(Field f : getAllFields(root, withAnnotation(XMLElement.class))){
            f.setAccessible(true);
            final XMLElement attr = f.getAnnotation(XMLElement.class);
            if(!attr.type().equals(XMLElement.Default.class)){
                node.appendChild(createElement(doc, f.get(obj), true, attr.name()));
            }
        }
        
        for(Field f : getAllFields(root, withAnnotation(XMLElements.class))){
            f.setAccessible(true);
            final Collection c = (Collection) f.get(obj);
            for(Object o : c){
                  node.appendChild(createElement(doc, o, false, null));
            }
        }
        return node;
    }
}
